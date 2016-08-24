package com.alkastor.crowd.model;

public interface ICreateModel {
    Ball[] GetBalls(Cell[][] cells);

    Ball[] GetMaze(Cell[][] cells);
}
