package com.alkastor.crowd.calculation;

import com.alkastor.crowd.model.Ball;
import com.alkastor.crowd.model.Event;

public final class StaticEventTime {
    private static double x1, x2, y1, y2;
    private static double vx1, vx2, vy1, vy2;
    private static double dt;
    private static double dx, dy, dvx, dvy;
    private static double A;
    private static double B;
    private static double C;
    private static double D;
    private static double dt1, dt2;
    private static double sqrt_D;

    public static void CollisionWithCellTime(Ball ic) {
        double dt = 1e10;
        int mt = 0;
        double dtmin = 1e10;
        ic.event = 11;
        if (ic.vx < 0) {
            dt = (ic.i - ic.x) / ic.vx;
            mt = 1;
        }
        if (ic.vx > 0) {
            dt = (ic.i + 1 - ic.x) / ic.vx;
            mt = 2;
        }
        if (dtmin > dt) {
            dtmin = dt;
            ic.next = mt;
        }
        if (ic.vy < 0) {
            dt = (ic.j - ic.y) / ic.vy;
            mt = 3;
        }
        if (ic.vy > 0) {
            dt = (ic.j + 1 - ic.y) / ic.vy;
            mt = 4;
        }
        if (dtmin > dt) {
            dtmin = dt;
            ic.next = mt;
        }
        ic.t = ic.t_loc + dtmin;
    }

    private static void setParams(Ball ic, Ball jc, double L) {
        dt = jc.t_loc - ic.t_loc;
        x1 = ic.x + ic.vx * dt;
        y1 = ic.y + ic.vy * dt;
        vx1 = ic.vx;
        vy1 = ic.vy;
        x2 = jc.x;
        y2 = jc.y;
        vx2 = jc.vx;
        vy2 = jc.vy;
        dx = x2 - x1;
        dy = y2 - y1;
        dvx = vx2 - vx1;
        dvy = vy2 - vy1;
        A = dvx * dvx + dvy * dvy;
        B = (dx * dvx + dy * dvy);
        C = dx * dx + dy * dy - L * L;
    }

    public static void CollisionWithSosed(Ball ic, Ball jc, double L) {
        setParams(ic, jc, L);
        double sigma = -B;
        if (sigma > 0) {
            CollisionWithBallInsideTime(ic, jc, L);
        } else {
            CollisionWithKernelsTime(ic, jc, L);
        }
    }

    public static Event CollisionWithBallOutsideTime(Ball ic, Ball jc, double L) {
        Event ev = new Event();
        ev.type = 1;
        ev.jc = jc.id;
        ev.ic = ic.id;
        ev.t = 1e10;
        double t_event;
        setParams(ic, jc, L);
        if (C < 0) {
            C = 1e-6;
        }
        D = B * B - A * C;
        if (D >= 0) {
            sqrt_D = Math.sqrt(D);
            if (-B > sqrt_D) {
                dt1 = (-B - sqrt_D) / A;
                if (dt1 >= 0) {
                    t_event = jc.t_loc + dt1;
                    if (t_event <= ic.t && t_event <= jc.t) {
                        /*ic.t = t_event;
						ic.event = 1;
						ic.next = jc.id;
						jc.t = t_event;
						jc.event = 1;
						jc.next = ic.id;*/
                        ev.t = t_event;
                    }
                }
            }
        }
        return ev;
    }

    public static Event CollisionWithKernelsTime(Ball ic, Ball jc, double L) {
        Event ev = new Event();
        ev.type = 1;
        ev.jc = jc.id;
        ev.ic = ic.id;
        ev.t = 1e10;
        double t_event;
        setParams(ic, jc, L);
        if (C < 0) {
            C = 1e-6;
        }
        D = B * B - A * C;
        if (D < 0) {
            return ev;
        }
        sqrt_D = Math.sqrt(D);
        if (-B > sqrt_D) {
            dt1 = (-B - sqrt_D) / A;
            if (dt1 >= 0) {
                t_event = jc.t_loc + dt1;
                if (t_event <= ic.t && t_event <= jc.t) {
					/*ic.t = t_event;
					ic.event = 3;
					ic.next = jc.id;
					jc.t = t_event;
					jc.event = 3;
					jc.next = ic.id;*/
                    ev.t = t_event;
                }
            }
        }
        return ev;
    }

    public static Event CollisionWithBallInsideTime(Ball ic, Ball jc, double L) {
        Event ev = new Event();
        ev.type = 1;
        ev.jc = jc.id;
        ev.ic = ic.id;
        ev.t = 1e10;
        double t_event;
        setParams(ic, jc, L);
        if (C >= 0) {
            C = -1e-6;
        }
        D = B * B - A * C;
        if (D >= 0) {
            sqrt_D = Math.sqrt(D);
            dt2 = Math.abs(-B + sqrt_D) / A;
            t_event = jc.t_loc + dt2;
            if (t_event <= ic.t && t_event <= jc.t) {
				/*ic.t = t_event;
				ic.event = 2;
				ic.next = jc.id;
				jc.t = t_event;
				jc.event = 2;
				jc.next = ic.id;*/
                ev.t = t_event;
            }
        }
        return ev;
    }
}