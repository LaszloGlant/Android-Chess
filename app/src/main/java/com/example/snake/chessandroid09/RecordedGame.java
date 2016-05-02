package com.example.snake.chessandroid09;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Brian on 4/25/2016.
 * Altered by Laszlo Glant on 4/25/2016
 */
public class RecordedGame implements Serializable {
    String title;
    Calendar cal;
    ArrayList<Pair> moves = new ArrayList<Pair>();

    public RecordedGame() {
        this.title = "";
        cal = Calendar.getInstance();
        this.moves = null;
    }

    public RecordedGame(String title, ArrayList<Pair> moves) {
        this.title = title;
        cal = Calendar.getInstance();
        this.moves = moves;
    }

    public String toString() {
        return this.title + " : " + this.cal.getTime();
    }
}
