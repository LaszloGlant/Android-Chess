package com.example.snake.chessandroid09;

import java.util.ArrayList;

/**
 * Created by Brian on 4/25/2016.
 */
public class RecordedGame {
    String title;
    int month;
    int day;
    int year;
    ArrayList<Pair> moves = new ArrayList<Pair>();

    public String toString() {
        return title + " " + month + "/" + day + "/" + year;
    }


}
