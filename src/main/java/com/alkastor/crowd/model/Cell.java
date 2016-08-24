package com.alkastor.crowd.model;

public class Cell {

    public Cell() {
        numBalls = 0;
        balls = new int[20];
    }

    public void addBall(int arg0) {
        balls[numBalls] = arg0;
        numBalls++;
    }

    public void delBall(int arg0) {
        for (int i0 = 0; i0 < numBalls; i0++) {
            if (balls[i0] == arg0) {
                numBalls--;
                balls[i0] = balls[numBalls];
                return;
            }
        }
    }

    public int numBalls;
    public int[] balls;
    public double vx_direct;
    public double vy_direct;
    public double n_direct;
    public boolean hole;

}
