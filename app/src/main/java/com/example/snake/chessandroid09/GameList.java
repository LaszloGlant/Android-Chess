package com.example.snake.chessandroid09;

/**
 * @author Laszlo Glant, Brian Wong
 *         GameList Activity screen
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GameList extends AppCompatActivity {
    private Button backbutton;
    private Button playbackbutton;
    private Spinner sortSpinner;
    private ListView list;
    private boolean sortbyTitle = false;
    ArrayAdapter<RecordedGame> nAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up buttons
        this.playbackbutton = (Button) this.findViewById(R.id.bReplayChess);
        this.playbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplayGameActivity.moveList = MainActivity.myGames.get(ReplayGameActivity.gameIndex).moves;
                startActivity(new Intent(getApplicationContext(),
                        ReplayGameActivity.class));
            }
        });

        this.sortSpinner = (Spinner) this.findViewById(R.id.sortSpinner);
        ArrayAdapter sAdapter = ArrayAdapter.createFromResource(this,
                R.array.sortBy, android.R.layout.simple_spinner_item);
        sortSpinner.setAdapter(sAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                TextView text = (TextView) view;
                if (position == 0) {
                    // Sort by Date

//                    for (int i = 0; i < MainActivity.myGames.size(); i++) {
//                        System.out.println(i + "-" + MainActivity.myGames.get(i).title);
//                    }

                    Sort.sortByDate(MainActivity.myGames);
                    nAdapter.notifyDataSetChanged();
//                    for (int i = 0; i < MainActivity.myGames.size(); i++) {
//                        System.out.println(i + "-" + MainActivity.myGames.get(i).title);
//                    }

                    //list.setAdapter(new ArrayAdapter<RecordedGame>(this, android.R.layout.simple_list_item_1, MainActivity.myGames));

                    Toast.makeText(getApplicationContext(), "Clicked sort by date", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    // Sort by Title

//                    for (int i = 0; i < MainActivity.myGames.size(); i++) {
//                        System.out.println(i + "-" + MainActivity.myGames.get(i).title);
//                    }

                    Sort.sortByTitle(MainActivity.myGames);
                    nAdapter.notifyDataSetChanged();
//                    for (int i = 0; i < MainActivity.myGames.size(); i++) {
//                        System.out.println(i + "-" + MainActivity.myGames.get(i).title);
//                    }

                    //list.setAdapter(new ArrayAdapter<RecordedGame>(this, android.R.layout.simple_list_item_1, MainActivity.myGames));

                    Toast.makeText(getApplicationContext(), "Clicked sort by title", Toast.LENGTH_SHORT).show();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // doesn't happen
            }
        });

        this.list = (ListView) findViewById(R.id.listView);
        nAdapter = new ArrayAdapter<RecordedGame>(this, android.R.layout.simple_list_item_1, MainActivity.myGames);
        list.setAdapter(nAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                ReplayGameActivity.gameIndex = arg2; //click on selected will load the game to be replayed
            }
        });
    }

    public void goToPlay1(View v) {
        finish();
        Toast.makeText(getApplicationContext(), "Hit play chess menu item", Toast.LENGTH_SHORT).show();
    }

    public void goToReplay(View v) {
        startActivity(new Intent(getApplicationContext(), ReplayGameActivity.class));
        Toast.makeText(getApplicationContext(), "Hit replay chess menu item", Toast.LENGTH_SHORT).show();
    }
}