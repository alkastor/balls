package com.alkastor.crowd.calculation;

import com.alkastor.crowd.model.Ball;

public final class StaticEventHandling {

    public static void ElasticReflection(Ball ic, Ball jc) {
        setParam(ic, jc);
        double d_lambd, d_lambd_ic, d_lambd_jc;
        d_lambd = 2 * dvr / ((ic.massa + jc.massa) * drr);
        d_lambd_ic = jc.massa * d_lambd;
        ic.vx += d_lambd_ic * dx;
        ic.vy += d_lambd_ic * dy;
        d_lambd_jc = ic.massa * d_lambd;
        jc.vx -= d_lambd_jc * dx;
        jc.vy -= d_lambd_jc * dy;
    }

    public static boolean ReflectionWithBarrierOutside(Ball ic, Ball jc, double u1, double u2, double b2) {
        setParam(ic, jc);
        double Umax;
        Umax = Math.max(u1, u2) + b2;
        double M;
        M = (ic.massa * jc.massa) / (ic.massa + jc.massa);
        double D2m;
        D2m = dvr * dvr + 2 * drr * ((u2 - Umax) / M);
        if (D2m > 0) {
            double D21;
            D21 = dvr * dvr + 2 * drr * ((u2 - u1) / M);
            double div;
            div = (dvr + Math.sqrt(D21)) / drr;
            ic.vx += (jc.massa / (ic.massa + jc.massa)) * div * dx;
            ic.vy += (jc.massa / (ic.massa + jc.massa)) * div * dy;
            jc.vx -= (ic.massa / (ic.massa + jc.massa)) * div * dx;
            jc.vy -= (ic.massa / (ic.massa + jc.massa)) * div * dy;
            return true;
        } else {
            ElasticReflection(ic, jc);
        }
        return false;
    }

    public static boolean ReflectionWithBarrierInside(Ball ic, Ball jc, double u1, double u2, double b2) {
        setParam(ic, jc);
        double Umax;
        Umax = Math.max(u1, u2) + b2;
        double M;
        M = (ic.massa * jc.massa) / (ic.massa + jc.massa);
        double D2p;
        D2p = dvr * dvr + 2 * drr * ((u1 - Umax) / M);
        if (D2p > 0) {
            double D12;
            D12 = dvr * dvr + 2 * drr * ((u1 - u2) / M);
            double div;
            div = (dvr - Math.sqrt(D12)) / drr;
            ic.vx += (jc.massa / (ic.massa + jc.massa)) * div * dx;
            ic.vy += (jc.massa / (ic.massa + jc.massa)) * div * dy;
            jc.vx -= (ic.massa / (ic.massa + jc.massa)) * div * dx;
            jc.vy -= (ic.massa / (ic.massa + jc.massa)) * div * dy;
            return true;
        } else {
            ElasticReflection(ic, jc);
        }
        return false;
    }

    private static void setParam(Ball ic, Ball jc) {
        dx = jc.x - ic.x;
        dy = jc.y - ic.y;
        dvx = jc.vx - ic.vx;
        dvy = jc.vy - ic.vy;
        drr = dx * dx + dy * dy;
        dvr = dx * dvx + dy * dvy;
    }

    @SuppressWarnings("unused")
    private static boolean isSosed2(Ball ic, Ball jc) {
        for (int i = 0; i < ic.numSosed; i++) {
            for (int j = 0; j < jc.numSosed; j++) {
                if (ic.sosedi[i] == jc.sosedi[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    private static double dx, dy, dvx, dvy;
    private static double drr, dvr;
}
