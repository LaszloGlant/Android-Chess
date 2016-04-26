package com.example.snake.chessandroid09;

import java.io.Serializable;

/**
 * Created by Brian on 4/25/2016.
 */
public class Pair implements Serializable{
    int r1;
    int c1;
    int r2;
    int c2;

    public Pair(int r1, int c1, int r2, int c2) {
        this.r1 = r1;
        this.c1 = c1;
        this.r2 = r2;
        this.c2 = c2;
    }
}
