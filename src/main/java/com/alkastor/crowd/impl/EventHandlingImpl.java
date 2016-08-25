package com.alkastor.crowd.impl;

import com.alkastor.crowd.EventHandling;
import com.alkastor.crowd.calculation.StaticEventHandling;
import com.alkastor.crowd.model.Ball;

import java.util.Random;

public class EventHandlingImpl implements EventHandling {

    private Model model;
    private Random rn = new Random();

    public EventHandlingImpl(Model model) {
        this.model = model;
    }

    public void handleCollisionExPartsOutside(Ball ic) {
        Ball jc = model.balls[ic.next];
        if (jc.event == 1 && jc.next == ic.id) {
            model.T = ic.t;
            ic.move();
            jc.move();

            StaticEventHandling.elasticReflection(ic, jc);
        }
        model.reCalculs(ic, jc);
    }

    public void handleCollisionExPartsInside(Ball ic) {
        Ball jc = model.balls[ic.next];
        if (jc.event == 2 && jc.next == ic.id) {
            model.T = ic.t;
            ic.move();
            jc.move();
            StaticEventHandling.elasticReflection(ic, jc);
        }
        model.reCalculs(ic, jc);
    }

    public void handleCollisionKernels(Ball ic) {
        Ball jc = model.balls[ic.next];
        if (jc.event == 3 && jc.next == ic.id) {
            model.T = ic.t;
            ic.move();
            jc.move();
            StaticEventHandling.elasticReflection(ic, jc);
        }
        model.reCalculs(ic, jc);
    }

    public void handleneCrossing(Ball ic) {
        int inew, jnew;
        model.T = ic.t;
        ic.move();
        inew = ic.i;
        jnew = ic.j;
        if (ic.next == 1) {
            inew--;
            if (inew < 0) {
                inew = 0;
                ic.vx = -ic.vx;
            }
        }
        if (ic.next == 2) {
            inew++;
            if (inew >= model.nx) {
                inew--;
                ic.vx = -ic.vx;
            }
        }
        if (ic.next == 3) {
            jnew--;
            if (jnew < 0) {
                jnew = 0;
                ic.vy = -ic.vy;
            }
        }
        if (ic.next == 4) {
            jnew++;
            if (jnew >= model.ny) {
                if (rn.nextDouble() > 0) {
                    ic.y = 0;
                    jnew = 0;
                } else {
                    jnew--;
                    ic.vy = -ic.vy;
                }
            }
        }
        model.cells[ic.i][ic.j].delBall(ic.id);
        model.cells[inew][jnew].addBall(ic.id);
        ic.i = inew;
        ic.j = jnew;
        model.reCalculs(ic);
    }
}
