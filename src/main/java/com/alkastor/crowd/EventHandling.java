package com.alkastor.crowd;

import com.alkastor.crowd.model.Ball;

public interface EventHandling {
    void handleCollisionExPartsOutside(Ball ic);

    void handleCollisionExPartsInside(Ball ic);

    void handleCollisionKernels(Ball ic);

    void handleneCrossing(Ball ic);
}
