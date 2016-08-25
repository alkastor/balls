package com.alkastor.crowd.model;

public class Cell {

    public int numBalls;
    public int[] balls;
    public double vx_direct;
    public double vy_direct;
    public double n_direct;
    public boolean hole;

    public Cell() {
        balls = new int[20];
    }

    public void addBall(int arg0) {
        balls[numBalls++] = arg0;
    }

    public void delBall(int arg0) {
        for (int i = 0; i < numBalls; i++) {
            if (balls[i] == arg0) {
                balls[i] = balls[--numBalls];
                return;
            }
        }
    }
}
