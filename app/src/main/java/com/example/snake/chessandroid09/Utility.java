package com.example.snake.chessandroid09;

import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


/**
 *
 * @author Brian Wong, Laszlo Glant
 * Contains the IO and other helper methods
 *
 */
public class Utility {

    /**
     * Outputs the database to the user.ser file
     * @param users all the users in the database
     */
    public static void output(ArrayList<RecordedGame> users) {
        try
        {
            File gameSave = new File("games.ser");
            FileOutputStream fileOut = new FileOutputStream(gameSave);

            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(users);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in games.ser");
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }
    /**
     * Loads the database from the user.ser file
     * @return The database of users
     */
    public static ArrayList<RecordedGame> input() {

        ArrayList<RecordedGame> recordedGames= null;
        try
        {

            FileInputStream fileIn = new FileInputStream("users.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            recordedGames = (ArrayList<RecordedGame>) in.readObject();
            in.close();
            fileIn.close();
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
}