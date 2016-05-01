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
                if (a.year == b.year && a.month == b.month && a.day == b.day) {
                    // dates are exactly the same, return 0
                    return 0;
                }

                if (a.year > b.year) {
                    // first date is bigger
                    return -1;
                }

                if (a.year < b.year) {
                    // second date is bigger
                    return 1;
                }

                if (a.month > b.month) {
                    // years are same, but month1 is bigger, so first date is bigger
                    return -1;
                }

                if (a.month < b.month) {
                    // years are same, but month2 is bigger, so second date is bigger
                    return 1;
                }

                if (a.day > b.day) {
                    // years and months are same, but day1 is bigger
                    return -1;
                }

                if (a.day < b.day) {
                    // years and months are same, but day2 is bigger
                    return 1;
                }

                // shouldn't get down to here
                return 0;
            }
        });
    }
}
