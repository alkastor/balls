package com.alkastor.crowd.model;

import java.awt.*;


public class Ball {

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
    public int numNeighbor;
    public int[] neighbors;
    public Color color;
    public Ball prevB;
    public Ball nextB;
    public boolean on_stack;
    public boolean fixed;


    public Ball() {
        neighbors = new int[20];
        t = 1e10;
    }

    public void addNeighbor(int arg0) {
        neighbors[numNeighbor] = arg0;
        numNeighbor++;
    }

    public void delNeighbor(int arg0) {
        for (int i = 0; i < numNeighbor; i++) {
            if (neighbors[i] == arg0) {
                neighbors[i] = neighbors[numNeighbor--];
                return;
            }
        }
    }

    public boolean isNeighbor(int jc) {
        for (int i = 0; i < numNeighbor; i++) {
            if (neighbors[i] == jc) {
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
}
