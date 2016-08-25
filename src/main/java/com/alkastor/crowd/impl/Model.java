package com.alkastor.crowd.impl;

import com.alkastor.crowd.CreateModel;
import com.alkastor.crowd.EventHandling;
import com.alkastor.crowd.EventTime;
import com.alkastor.crowd.calculation.Direct;
import com.alkastor.crowd.model.Ball;
import com.alkastor.crowd.model.Cell;
import com.alkastor.crowd.model.Heap;

import java.util.Random;

public class Model {

    public int nx;
    public int ny;
    public Ball[] balls;
    public Cell[][] cells;

    public int N;
    public double T;
    public double t_stack;

    private EventHandling eventHandling;
    private EventTime eventTime;
    private CreateModel createModel;
    private Heap heap;
    private Random rm = new Random();

    public Model() {
        createModel = new CreateModelImpl(Model.this);
        eventHandling = new EventHandlingImpl(Model.this);
        eventTime = new EventTimeImpl(Model.this);
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
        balls = createModel.GetBalls(cells);
        heap = new Heap(Model.this);
        for (int i = 1; i < N; i++) {
            eventTime.getEventTime(balls[i]);
            heap.insertBall(i);
        }
    }

    public void reCalculs(Ball ic) {
        eventTime.getEventTime(ic);
        heap.insertBall(ic.next);
        heap.insertBall(ic.id);
    }

    public void reCalculs(Ball ic, Ball jc) {
        reCalculs(ic);
        reCalculs(jc);
    }

    public void simulate() {
        int id = heap.getMin();
        if (balls[id].event == 1) {
            eventHandling.handleCollisionExPartsOutside(balls[id]);
        } else if (balls[id].event == 2) {
            eventHandling.handleCollisionExPartsInside(balls[id]);
        } else if (balls[id].event == 3) {
            eventHandling.handleCollisionKernels(balls[id]);
        } else if (balls[id].event == 11) {
            eventHandling.handleCrossing(balls[id]);
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
        balls[ib].numNeighbor = 0;
        balls[ib].t_loc = T;
        balls[ib].t = 10e20;
        cells[balls[ib].i][balls[ib].j].addBall(ib);
    }
}
