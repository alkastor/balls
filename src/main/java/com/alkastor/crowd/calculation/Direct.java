package com.alkastor.crowd.calculation;

import com.alkastor.crowd.model.Ball;
import com.alkastor.crowd.model.Cell;

import java.awt.*;
import java.util.Random;

public final class Direct {
    public static Random rnd = new Random();
    public static boolean is_gdirect = false;
    public static boolean is_speed = false;
    public static boolean is_grid = false;

    public static void clear_v_direct(Cell[][] cells, int nx, int ny) {
        int ix, iy, iir;
        double ttt, fi;
        for (iir = 1; iir < 10; iir++)
            ttt = rnd.nextDouble();
        for (ix = 0; ix < nx; ix++) {
            for (iy = 0; iy < ny; iy++) {
                cells[ix][iy].n_direct = 1;
                ttt = rnd.nextDouble();
                fi = 2 * Math.PI * ttt;
                cells[ix][iy].vx_direct = Math.cos(fi) * Math.pow(10, -6);
                cells[ix][iy].vy_direct = Math.sin(fi) * Math.pow(10, -6);
            }
        }
    }

    public static void sum_v_direct(Cell[][] cells, Ball[] balls, int nx, int ny) {
        int ix, iy;
        int m;
        int jp = 0;
        for (ix = 0; ix < nx; ix++) {
            for (iy = 0; iy < ny; iy++) {
                if (cells[ix][iy].numBalls > 0) {
                    for (m = 0; m < cells[ix][iy].numBalls; m++) {
                        jp = cells[ix][iy].balls[m];
                        cells[ix][iy].n_direct++;
                        cells[ix][iy].vx_direct += balls[jp].vx;
                        cells[ix][iy].vy_direct += balls[jp].vy;
                    }
                }
            }
        }
    }

    public static void drawDirect(Graphics g, Cell[][] cells, int nx, int ny, double kx, double ky) {
        double vxd, vyd, vv, ttt = 0;
        int ix, iy, ixd, iyd, xx, yy, by = 0, bx = 0;
        for (int i = 0; i < nx; i++)
            for (int j = 0; j < ny; j++) {
                if (cells[i][j].n_direct > 10) {
                    ix = (int) Math.round(kx * i + bx);
                    iy = (int) Math.round(ky * j + by);
                    xx = ix;
                    yy = iy;
                    if (is_gdirect) {
                        vv = Math.sqrt(Math.pow(cells[i][j].vx_direct, 2) + Math.pow(cells[i][j].vy_direct, 2));
                        ttt = vv / cells[i][j].n_direct;
                        vxd = cells[i][j].vx_direct / vv;
                        vyd = cells[i][j].vy_direct / vv;
                    } else {
                        vxd = cells[i][j].vx_direct / cells[i][j].n_direct;
                        vyd = cells[i][j].vy_direct / cells[i][j].n_direct;
                    }
                    if (ttt > Math.pow(10, -4)) {
                        ixd = (int) Math.round(kx * (i + vxd) + bx);
                        iyd = (int) Math.round(ky * (j + vyd) + by);
                        g.setColor(Color.BLUE);
                        g.drawLine(xx, yy, ixd, iyd);
                        xx = ixd;
                        yy = iyd;
                    }
                }
            }
    }
}
