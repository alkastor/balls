package com.alkastor.crowd.model;

import java.awt.*;


public class Ball {

    public Ball() {
        sosedi = new int[20];
        numSosed = 0;
        t = 1e10;
        t_loc = 0;
    }


    public void addSosed(int arg0) {
        sosedi[numSosed] = arg0;
        numSosed++;
    }

    public void delSosed(int arg0) {
        for (int i0 = 0; i0 < numSosed; i0++) {
            if (sosedi[i0] == arg0) {
                numSosed--;
                sosedi[i0] = sosedi[numSosed];
                return;
            }
        }
    }

    public boolean isSosed(int jc) {
        for (int ii = 0; ii < numSosed; ii++) {
            if (sosedi[ii] == jc) {
                return true;
            }
        }
        return false;
    }

    public void move() {
        double dt;
        dt = t - t_loc;
        x += dt * vx;
        y += dt * vy;
        t_loc = t;
        t = 1e10;
    }

    public double getCurX(double t0) {
        return x + t0 * vx;
    }

    public double getCurY(double t0) {
        return y + t0 * vy;
    }

    public double getEnergy() {
        double v = Math.sqrt(vx * vx + vy * vy);
        return (massa * v * v) / 2;
    }

    public String toString() {
        return t + "";
    }

    public int round;
    public int heap_id;
    public double x, y;
    public double vx, vy, rspeed;
    public double massa, rmassa;
    public double r, r0, r1;
    public int i, j;
    public int id;
    public int clas;
    public int sort;
    public int type;
    public double t, t_loc;
    public int event;
    public int next;
    public int valent;
    public int numSosed;
    public int[] sosedi;
    public Color color;
    public Ball prevB;
    public Ball nextB;
    public boolean on_stack;
    public boolean fixed;
}
