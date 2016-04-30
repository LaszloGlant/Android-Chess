package com.example.snake.chessandroid09;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ReplayGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replay_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.play:
                finish();

                Toast.makeText(getApplicationContext(), "Returning to play chess mode!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main:
                Toast.makeText(getApplicationContext(), "THIS MENU IS PROBALY NOT NEEDED! WILL SEE!", Toast.LENGTH_LONG).show();
                break;
            case R.id.replay:

                Toast.makeText(getApplicationContext(), "You are in replay mode!", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void recordedGames(View v) {
        Toast.makeText(getApplicationContext(), "This should open the list of recorded games!", Toast.LENGTH_SHORT).show();

    }

    public void playback(View v) {
        Toast.makeText(getApplicationContext(), "This should select the game to be played back", Toast.LENGTH_SHORT).show();
    }

    public void backward(View v) {
        Toast.makeText(getApplicationContext(), "You hit backward!", Toast.LENGTH_SHORT).show();
    }

    public void forward(View v) {
        Toast.makeText(getApplicationContext(), "You hit forward!", Toast.LENGTH_SHORT).show();
    }
}
