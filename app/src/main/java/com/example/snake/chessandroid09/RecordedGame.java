package com.example.snake.chessandroid09;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Brian on 4/25/2016.
 * Altered by Laszlo Glant on 4/25/2016
 */
public class RecordedGame implements Serializable {
    String title;
    int year, month, day;
    ArrayList<Pair> moves = new ArrayList<Pair>();

    public String toString() {
        return title + " " + year + "/" + month + "/" + day;
    }

    public RecordedGame(String title, int year, int month, int day, ArrayList<Pair> moves) {
        this.title = title;
        this.year = year;
        this.month = month;
        this.day = day;
        this.moves = moves;
    }
}
