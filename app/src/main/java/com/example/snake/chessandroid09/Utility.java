package com.example.snake.chessandroid09;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;


/**
 *
 * @author Brian Wong, Laszlo Glant
 * Contains the IO and other helper methods
 *
 */
public class Utility {

    Context context;
    /**
     * Outputs the database to the games.ser file
     * @param games all the users in the database
     */
    public void output(ArrayList<RecordedGame> games) {
        try
        {
            //File gameSave = new File("games.ser");
            //FileOutputStream fileOut = new FileOutputStream(gameSave);
            FileOutputStream fileOut = context.openFileOutput("games.ser", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(games);
            out.flush();
            out.close();
            //fileOut.close();
            System.out.printf("Serialized data is saved in games.ser");
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    /**
     * Loads the database from the games.ser file
     * @return The database of games
     */
    public ArrayList<RecordedGame> input() {

        ArrayList<RecordedGame> recordedGames= null;
        try
        {
            //File gameSave = new File("games.ser");
            //FileInputStream fileIn = new FileInputStream(gameSave);
            FileInputStream fileIn = context.openFileInput("games.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            recordedGames = (ArrayList<RecordedGame>) in.readObject();
            in.close();
            //fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
            return recordedGames;
        }catch(ClassNotFoundException c)
        {
            c.printStackTrace();
            return recordedGames;
        }
        return recordedGames;
    }

//    /**
//     * given the master list of games, store them to disk
//     * @param games master list of games
//     */
//    public void output(ArrayList<RecordedGame> games) {
//        try {
//            File o = new File("output.txt");
//            o.createNewFile();
//
//            PrintWriter out = new PrintWriter(new FileWriter(o, false), true);
//            for (int i = 0; i < games.size(); i++) {
//                out.println(outString(games.get(i)));
//            }
//            out.close();
//            return;
//        } catch (Exception e) {
//            return;
//        }
//    }
//
//    /**
//     * read in the master list from games from disk and load them up
//     * @param empty blank array list to be loaded up with data
//     * @param o output.txt
//     */
//    public void input(ArrayList<RecordedGame> empty, File o) {
//        try {
//            String line = null;
//            FileReader fileReader = new FileReader(o);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//
//            while ((line = bufferedReader.readLine()) != null) {
//                stickIn1Line(empty, line);
//            }
//        } catch (Exception e) {
//            System.out.println("input exception");
//        }
//    }
}