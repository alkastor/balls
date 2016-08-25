package com.alkastor.crowd.model;

import com.alkastor.crowd.impl.Model;

public class Heap {
    public Heap(Model model) {
        this.model = model;
        nstart = 1;
        while (nstart < model.N)
            nstart *= 2;
        nstart--;
        nt = 2 * nstart + 1;
        tree = new int[nt];
        for (int i = 0; i < nt; i++) {
            tree[i] = 0;
        }
        for (int i = nstart; i < model.N + nstart; i++) {
            tree[i] = i - nstart;
        }
    }

    public void InsertBall(int i) {
        i += nstart;
        int i_sosed;
        int per;
        do {
            if (i % 2 == 0) {
                i_sosed = i - 1;
                per = i_sosed / 2;
            } else {
                i_sosed = i + 1;
                per = i / 2;
            }
            if (model.balls[tree[i]].t <= model.balls[tree[i_sosed]].t)
                tree[per] = tree[i];
            else
                tree[per] = tree[i_sosed];
            i = per;
        }
        while (i > 0);
    }

    public int GetMin() {
        return tree[0];
    }

    private Model model;
    private int[] tree;
    private int nstart;
    private int nt;
}
