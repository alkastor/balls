package com.alkastor.crowd.model;

import java.util.Random;


public class Model {

    public Model() {
        CreateBalls = new CreateModel(Model.this);
        EventRespos = new EventHeadling(Model.this);
        EventSearch = new EventTime(Model.this);
        T = 0;
    }

    public void initialize() {
        if (nx == 0 || ny == 0)
            throw new Error();
        cells = new Cell[nx][ny];
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                cells[i][j] = new Cell();
            }
        }
        balls = CreateBalls.GetBalls(cells);
        heap = new Heap(Model.this);
        for (int i = 1; i < N; i++) {
            EventSearch.getEventTime(balls[i]);
            heap.InsertBall(i);
        }
        /*for(int i = 1; i<N; i++)
		{
			EventSearch.getEventTime(balls[i]);
			heap.InsertBall(i);
		}*/
    }

    public void reCalculs(Ball ic) {
        EventSearch.getEventTime(ic);
        heap.InsertBall(ic.next);
        heap.InsertBall(ic.id);
    }

    public void reCalculs(Ball ic, Ball jc) {
        reCalculs(ic);
        reCalculs(jc);
    }

    public void simulate() {
        int id = heap.GetMin();
        if (balls[id].event == 1) {
            EventRespos.HendlineCollisionExPartsOutside(balls[id]);
        } else if (balls[id].event == 2) {
            EventRespos.HendlineCollisionExPartsInside(balls[id]);
        } else if (balls[id].event == 3) {
            EventRespos.HendlineCollisionKernels(balls[id]);
        } else if (balls[id].event == 11) {
            EventRespos.HendlineCrossing(balls[id]);
        }
        if (Direct.is_speed)
            correct_speed(id);
    }

    public void correct_speed(int ib) {
        double ttt;
        if (!balls[ib].fixed) {
            ttt = rm.nextDouble() / Math.sqrt(Math.pow(cells[balls[ib].i][balls[ib].j].vx_direct, 2) + Math.pow(cells[balls[ib].i][balls[ib].j].vy_direct, 2));
            balls[ib].vx += cells[balls[ib].i][balls[ib].j].vx_direct * ttt;
            balls[ib].vy += cells[balls[ib].i][balls[ib].j].vy_direct * ttt;
        }
    }

    public void to_stack(int ib) {
        int mm;
        boolean ind = false;
        balls[ib].t_loc = 0;
        balls[ib].t = 10e20;
        balls[ib].on_stack = true;
        mm = 0;
        while (ind || (mm >= cells[balls[ib].i][balls[ib].j].numBalls)) {
            ind = cells[balls[ib].i][balls[ib].j].balls[mm] == ib;
            if (ind) {
                cells[balls[ib].i][balls[ib].j].balls[mm] = cells[balls[ib].i][balls[ib].j].balls[cells[balls[ib].i][balls[ib].j].numBalls];
                cells[balls[ib].i][balls[ib].j].numBalls--;
                if (cells[balls[ib].i][balls[ib].j].numBalls < 0)
                    cells[balls[ib].i][balls[ib].j].numBalls = 0;
            }
        }
    }

    public void from_stack(int ib) {
        double a, c, fi, fj, x, y;
        balls[ib].on_stack = false;
        a = rm.nextDouble();
        c = rm.nextDouble();
        fi = a * 2 * Math.PI;
        fj = c * 2 * Math.PI;
        x = rm.nextInt(5);
        y = rm.nextInt(5);
        balls[ib].x = x;
        balls[ib].y = y;
        balls[ib].i = (int) x;
        balls[ib].j = (int) y;
        balls[ib].vx = Math.sin(fi) * Math.cos(fj) * (1 / Math.sqrt(balls[ib].massa));
        balls[ib].vy = Math.cos(fi) * Math.cos(fj) * (1 / Math.sqrt(balls[ib].massa));
        balls[ib].numSosed = 0;
        balls[ib].t_loc = T;
        balls[ib].t = 10e20;
        cells[balls[ib].i][balls[ib].j].addBall(ib);
    }

    public int nx, ny, nz;
    public Ball[] balls;
    public Cell[][] cells;

    public int N;
    public double T;
    public double t_stack;

    private IEventHendling EventRespos;
    private IEventTime EventSearch;
    private ICreateModel CreateBalls;
    private Heap heap;
    private Random rm = new Random();
}
