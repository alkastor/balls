package com.alkastor.crowd.model;

import java.util.Random;

public class EventHeadling implements IEventHendling {

    public EventHeadling(Model model) {
        this.model = model;
    }

    public void HendlineCollisionExPartsOutside(Ball ic) {
        Ball jc = model.balls[ic.next];
        if (jc.event == 1 && jc.next == ic.id) {
            model.T = ic.t;
            ic.move();
            jc.move();

            StaticEventHandling.ElasticReflection(ic, jc);
        }
        model.reCalculs(ic, jc);
    }

    public void HendlineCollisionExPartsInside(Ball ic) {
        Ball jc = model.balls[ic.next];
        if (jc.event == 2 && jc.next == ic.id) {
            model.T = ic.t;
            ic.move();
            jc.move();
            StaticEventHandling.ElasticReflection(ic, jc);
        }
        model.reCalculs(ic, jc);
    }

    public void HendlineCollisionKernels(Ball ic) {
        Ball jc = model.balls[ic.next];
        if (jc.event == 3 && jc.next == ic.id) {
            model.T = ic.t;
            ic.move();
            jc.move();
            StaticEventHandling.ElasticReflection(ic, jc);
        }
        model.reCalculs(ic, jc);
    }

    public void HendlineCrossing(Ball ic) {
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

    private Model model;
    private Random rn = new Random();
}
