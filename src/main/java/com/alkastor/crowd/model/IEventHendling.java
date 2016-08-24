package com.alkastor.crowd.model;

public interface IEventHendling {
    void HendlineCollisionExPartsOutside(Ball ic);

    void HendlineCollisionExPartsInside(Ball ic);

    void HendlineCollisionKernels(Ball ic);

    void HendlineCrossing(Ball ic);
}
