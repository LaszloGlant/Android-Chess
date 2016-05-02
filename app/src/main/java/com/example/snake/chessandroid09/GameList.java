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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;

public class GameList extends AppCompatActivity {
    private Button backbutton;
    private Button playbackbutton;
    private Spinner sortSpinner;
    private ListView list;
    private boolean sortbyTitle = false;
    ArrayAdapter<RecordedGame> nAdapter;
    int target = 0;

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
/*                    // Sort by Date
                    Collections.sort(MainActivity.gamesList,
                            new Comparator<Node>() {

                                @Override
                                public int compare(Node lhs, Node rhs) {
                                    // TODO Auto-generated method stub
                                    return lhs.cal.compareTo(rhs.cal);
                                }

                            });

                    nAdapter.notifyDataSetChanged();
*/
                    Toast.makeText(getApplicationContext(), "Clicked sort by date", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    // Sort by Title
//                    Collections.sort(MainActivity.gamesList);
//                    nAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Clicked sort by title", Toast.LENGTH_SHORT).show();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // doesn't happen
            }

        });

        this.list = (ListView) findViewById(R.id.listView);
        nAdapter = new ArrayAdapter<RecordedGame>(this,
                android.R.layout.simple_list_item_1, MainActivity.myGames);
        list.setAdapter(nAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                ReplayGameActivity.gameIndex = arg2; //click on selected will load the game to be replayed
            }

        });

    }

    public void goToPlay1(View v) {
        finish();
        //Toast.makeText(getApplicationContext(), "Going to play chess", Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
        Toast.makeText(getApplicationContext(), "Hit play chess menu item", Toast.LENGTH_SHORT).show();
    }

    public void goToReplay(View v) {
        startActivity(new Intent(getApplicationContext(), ReplayGameActivity.class));
        Toast.makeText(getApplicationContext(), "Hit replay chess menu item", Toast.LENGTH_SHORT).show();
    }
}
