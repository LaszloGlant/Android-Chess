package com.example.snake.chessandroid09;

import java.io.Serializable;

/**
 *
 * @author Brian Wong, Laszlo Glant
 * A Pair is a set of 2 coordinates (ex. e2 e4), but in integer format
 *
 */
public class Pair{
    int r1;
    int c1;
    int r2;
    int c2;

    public Pair() {
        this.r1 = -1;
        this.c1 = -1;
        this.r2 = -1;
        this.c2 = -1;
    }

    public Pair(int r1, int c1, int r2, int c2) {
        this.r1 = r1;
        this.c1 = c1;
        this.r2 = r2;
        this.c2 = c2;
    }

    public String toString() {
        return r1 + "" + c1 + " " + r2 + "" + c2;
    }
}
