package com.example.snake.chessandroid09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 *
 * @author Brian Wong, Laszlo Glant
 * Contains the sort by title and sort by date methods
 *
 */
public class Sort {

    public static void sortByTitle(ArrayList<RecordedGame> myGames) {
        Collections.sort(myGames, new Comparator<RecordedGame>() {
            public int compare(RecordedGame a, RecordedGame b) {
                return a.title.compareTo(b.title);
            }
        });
    }

    public static void sortByDate(ArrayList<RecordedGame> myGames) {
        Collections.sort(myGames, new Comparator<RecordedGame>() {
            public int compare(RecordedGame a, RecordedGame b) {
                return a.cal.compareTo(b.cal);
            }
        });
    }
}
