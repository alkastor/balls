package com.alkastor.crowd.impl;

import com.alkastor.crowd.EventTime;
import com.alkastor.crowd.calculation.StaticEventTime;
import com.alkastor.crowd.model.Ball;
import com.alkastor.crowd.model.Event;

public class EventTimeImpl implements EventTime {
    private Event ev = new Event();
    private Event t;
    private Model model;

    public EventTimeImpl(Model model) {
        this.model = model;
    }

    public void getEventTime(Ball ic) {
        ev.t = 1e10;
        StaticEventTime.CollisionWithCellTime(ic);
        int imax, imin, jmin, jmax;
        imin = ic.i - 1;
        if (imin < 0) imin = 0;
        imax = ic.i + 1;
        if (imax == model.nx) imax = model.nx - 1;
        jmin = ic.j - 1;
        if (jmin < 0) jmin = 0;
        jmax = ic.j + 1;
        if (jmax == model.ny) jmax = model.ny - 1;
        for (int ii = imin; ii <= imax; ii++) {
            for (int jj = jmin; jj <= jmax; jj++) {
                for (int m = 0; m < model.cells[ii][jj].numBalls; m++) {
                    int i = ic.id;
                    int j = model.cells[ii][jj].balls[m];
                    if (i != j) {
                        if (model.balls[i].t_loc <= model.balls[j].t_loc) {
                            withNotSosed(ic, model.balls[j]);
                            if (ev.t >= t.t) {
                                ev = t;
                            }
                        } else {
                            withNotSosed(model.balls[j], ic);
                            if (ev.t >= t.t) {
                                ev = t;
                            }
                        }
                    }
                }
            }
        }
        if (ev.t <= model.balls[ev.ic].t && ev.t <= model.balls[ev.jc].t) {
            model.balls[ev.ic].t = ev.t;
            model.balls[ev.ic].next = ev.jc;
            model.balls[ev.ic].event = ev.type;
            model.balls[ev.jc].t = ev.t;
            model.balls[ev.jc].next = ev.ic;
            model.balls[ev.jc].event = ev.type;
        }
    }

    private void withNotSosed(Ball ic, Ball jc) {
        t = StaticEventTime.CollisionWithBallOutsideTime(ic, jc, ic.r1 + jc.r1);
    }
}