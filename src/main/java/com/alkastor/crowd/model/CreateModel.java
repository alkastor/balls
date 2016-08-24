package com.alkastor.crowd.model;

import java.awt.*;
import java.util.Random;

public class CreateModel implements ICreateModel {

    public CreateModel(Model model) {
        this.model = model;
        Direct.clear_v_direct(model.cells, model.nx, model.ny);
    }

    int i = 1;
    double roO2;
    double roH2;
    Ball[] balls;
    Ball[] fballs;
    Random rm = new Random();

    public Ball[] GetBalls(Cell[][] cells) {
        model.N++;
        balls = new Ball[model.N + 1];
        balls[0] = new Ball();
        balls[0].t = 1e20;
        balls[0].id = 0;
        balls[0].round = (int) 1e10;
        double x = 0.5;
        double y = 0.5;
        double a, c, fi, fj;
        ///////
        for (int i = 1; i < model.N; i++) {
            balls[i] = new Ball();
            balls[i].id = i;
            balls[i].x = x;
            balls[i].y = y;
            balls[i].i = (int) x;
            balls[i].j = (int) y;
            balls[i].massa = 1;
            balls[i].type = 2;
            balls[i].r1 = 0.45;
            balls[i].next = 0;
            balls[i].t_loc = 0;
            balls[i].t = i;
            balls[i].event = 0;
            a = rm.nextDouble();
            c = rm.nextDouble();
            fi = a * 2 * Math.PI;
            fj = c * 2 * Math.PI;
            balls[i].vx = Math.sin(fi) * Math.cos(fj) * (1 / Math.sqrt(balls[i].massa));
            balls[i].vy = Math.cos(fi) * Math.cos(fj) * (1 / Math.sqrt(balls[i].massa));
            balls[i].numSosed = 0;
            balls[i].color = Color.orange;
            cells[balls[i].i][balls[i].j].addBall(i);
            x += 1;
            if ((int) x == model.nx) {
                x = 0.5;
                y += 1;
            }
        }
        return balls;
    }

    private boolean check(int i, int j, Cell[][] cells) {
        return cells[i][j].numBalls > 0;
    }

    Model model;

    public Ball[] GetMaze(Cell[][] cells) {
        balls = GetBalls(cells);
        double x = 0.5;
        double y = 30;
        int kk = 0;
        boolean b = false;
        for (int i = model.N - 20; i < model.N; i++) {
            balls[i] = new Ball();
            balls[i].id = i;
            balls[i].x = x;
            balls[i].y = y;
            balls[i].i = (int) x;
            balls[i].j = (int) y;
            balls[i].massa = 10e20;
            balls[i].type = 2;
            balls[i].r1 = 0.7;
            balls[i].next = 0;
            balls[i].t_loc = 0;
            balls[i].t = 10e20;
            balls[i].event = 0;
            balls[i].vx = 0;
            balls[i].vy = 0;
            balls[i].numSosed = 0;
            balls[i].color = Color.green;
            balls[i].fixed = true;
            cells[balls[i].i][balls[i].j].addBall(i);
            x++;
            kk++;
            if ((int) x >= model.nx) {
                x = 0.5;
            }
            if (kk == 80) {
                y += 7;
                kk = 0;
                b = !b;
                if (b)
                    x = model.nx - 80;
            }
            if ((int) y >= model.ny) {
                y = 1;
            }
        }
        return balls;
    }
}
