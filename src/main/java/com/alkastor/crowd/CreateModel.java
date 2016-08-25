package com.alkastor.crowd;

import com.alkastor.crowd.model.Ball;
import com.alkastor.crowd.model.Cell;

public interface CreateModel {
    Ball[] GetBalls(Cell[][] cells);

    Ball[] GetMaze(Cell[][] cells);
}
