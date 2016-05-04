package com.example.snake.chessandroid09;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Created by Brian on 4/25/2016.
 * Altered by Laszlo Glant on 4/25/2016
 */
public class RecordedGame implements Serializable {
    String title;
    String cal;
    ArrayList<Pair> moves = new ArrayList<Pair>();

    public RecordedGame() {
        this.title = "";

        this.moves = null;
    }

    public RecordedGame(String title, String cal, ArrayList<Pair> moves) {
        this.title = title;
        this.cal = cal;
        this.moves = moves;
    }

    public String toString() {
        return this.title + " : " + this.cal;
    }
}
