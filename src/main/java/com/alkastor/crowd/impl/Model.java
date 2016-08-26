package com.alkastor.crowd.impl;

import com.alkastor.crowd.CreateModel;
import com.alkastor.crowd.EventHandling;
import com.alkastor.crowd.EventTime;
import com.alkastor.crowd.model.Ball;
import com.alkastor.crowd.model.Cell;
import com.alkastor.crowd.model.Heap;

import java.util.Random;

public class Model {

    public int nx;
    public int ny;
    public int n;

    public Ball[] balls;
    public Cell[][] cells;
    public double t;
    private double t_stack;
    private double delta_t_stack;
    private int[] stack_parts;
    private int k_stack;

    private EventHandling eventHandling;
    private EventTime eventTime;
    private CreateModel createModel;
    private Heap heap;
    private Random rm = new Random();

    private Model(Builder builder) {
        this.nx = builder.nx;
        this.ny = builder.ny;
        this.n = builder.n;

        init();
    }

    private void init() {
        createModel = new CreateModelImpl(Model.this);
        eventHandling = new EventHandlingImpl(Model.this);
        eventTime = new EventTimeImpl(Model.this);
        initGrid();
        stack_parts = new int[n];
        for (int i =0; i < n; i++) {
            stack_parts[i] = i;
        }
        k_stack = n;
    }

//    public Model(int n, int nx, int ny) {
//        this.n = n;
//        this.nx = nx;
//        this.ny = ny;
//        createModel = new CreateModelImpl(Model.this);
//        eventHandling = new EventHandlingImpl(Model.this);
//        eventTime = new EventTimeImpl(Model.this);
//        initGrid();
//        stack_parts = new int[n];
//        for (int i =0; i < n; i++) {
//            stack_parts[i] = i;
//        }
//        k_stack = n;
//    }

    public void initGrid() {
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
        for (int i = 1; i < n; i++) {
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
        boolean ind_from = false;
        int ic = heap.getMin();
        int ip;
        if (k_stack > 0) {
            if (/*balls[ic].on_stack &&*/ balls[ic].t >= t_stack) {
                ind_from = true;
                ip = stack_parts[k_stack - 1];
                from_stack(ip);
                eventTime.getEventTime(balls[ip]);
                heap.insertBall(ip);
                t_stack = balls[ic].t + delta_t_stack;

                step(balls[ip]);
            }
        }


        if (!ind_from) {
            step(balls[ic]);
        }
//        if (Direct.is_speed) {
//            correct_speed(ic);
//        }
    }

    private void step(Ball ball) {
        if (ball.event == 1) {
            eventHandling.handleCollisionExPartsOutside(ball);
        } else if (ball.event == 2) {
            eventHandling.handleCollisionExPartsInside(ball);
        } else if (ball.event == 3) {
            eventHandling.handleCollisionKernels(ball);
        } else if (ball.event == 11) {
            eventHandling.handleCrossing(ball);
        }
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
        stack_parts[k_stack++] = ib;
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
        k_stack--;
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
        balls[ib].t_loc = t;
        balls[ib].t = 10e20;
        cells[balls[ib].i][balls[ib].j].addBall(ib);
    }

    public double getT() {
        return t;
    }

    public static class Builder {
        private final int nx;
        private final int ny;
        private final int n;

        public Builder(int nx, int ny, int n) {
            this.nx = nx;
            this.ny = ny;
            this.n = n;
        }

        public Model build() {
            return new Model(this);
        }
    }
}
