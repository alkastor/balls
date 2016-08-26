package com.alkastor.crowd.model;

import com.alkastor.crowd.impl.Model;

public class Heap {
    private Model model;
    private int[] tree;
    private int n_start;

    public Heap(Model model) {
        this.model = model;
        n_start = 1;
        while (n_start < model.n)
            n_start *= 2;
        n_start--;
        int nt = 2 * n_start + 1;
        tree = new int[nt];
        for (int i = 0; i < nt; i++) {
            tree[i] = 0;
        }
        for (int i = n_start; i < model.n + n_start; i++) {
            tree[i] = i - n_start;
        }
    }

    public void insertBall(int i) {
        i += n_start;
        int i_neighbor;
        int per;
        do {
            if (i % 2 == 0) {
                i_neighbor = i - 1;
                per = i_neighbor / 2;
            } else {
                i_neighbor = i + 1;
                per = i / 2;
            }
            if (model.balls[tree[i]].t <= model.balls[tree[i_neighbor]].t)
                tree[per] = tree[i];
            else
                tree[per] = tree[i_neighbor];
            i = per;
        }
        while (i > 0);
    }

    public int getMin() {
        return tree[0];
    }
}
