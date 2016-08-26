package com.alkastor.crowd.gui;

import com.alkastor.crowd.calculation.Direct;
import com.alkastor.crowd.impl.Model;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Compp extends JComponent {

    private Model model;

    public Compp(Model model) {
        this.model = model;
    }

    public void grid(Graphics g, int kx, int ky) {
        for (int i = 0; i <= model.nx; i++)
            g.drawLine(i * kx, 0, i * kx, model.ny * ky);
        for (int i = 0; i <= model.ny; i++)
            g.drawLine(0, i * ky, kx * model.nx, i * ky);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Image buf = createImage(getWidth(), getHeight());
        Graphics gbuf = buf.getGraphics();
        double s = 0;
        s = (getWidth() < getHeight()) ? getWidth() : getHeight();
        int kx = (int) s / model.nx;
        int ky = (int) s / model.ny;
        kx = (kx > ky) ? kx : ky;
        ky = kx;
        gbuf.clearRect(0, 0, getWidth(), getHeight());
        for (int i = 1; i < model.n; i++) {
            double x = model.balls[i].getCurX(model.getT() - model.balls[i].t_loc);
            double y = model.balls[i].getCurY(model.getT() - model.balls[i].t_loc);
            gbuf.setColor(model.balls[i].color);
            gbuf.fillOval((int) (x * kx - model.balls[i].r1 * kx), (int) (y * ky - model.balls[i].r1 * ky), (int) (model.balls[i].r1 * kx * 2), (int) (model.balls[i].r1 * ky * 2));
        }
        Direct.drawDirect(gbuf, model.cells, model.nx, model.ny, kx, ky);
        if (Direct.is_grid) {
            gbuf.setColor(Color.gray);
            grid(gbuf, kx, ky);
        }
        g.drawImage(buf, 0, 0, null);
    }
}
