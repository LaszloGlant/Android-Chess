package com.example.snake.chessandroid09;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    public int numHits = 0;
    public int currImage = R.drawable.rbishop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void hit(View v) {
        int id = v.getId();
        ImageButton currButton = (ImageButton) v;

        if (id == R.id.b00) {
            System.out.println("Button 00");
        }
        if (id == R.id.b01) {
            if (numHits % 2 == 0){
                currButton.setImageResource(R.drawable.bpawn);
                numHits++;
            }
            else{
                currButton.setImageResource(R.drawable.bknight);
                numHits++;
            }

        }
        if (id == R.id.b02) {

        }
        if (id == R.id.b03) {

        }
        if (id == R.id.b04) {

        }
        if (id == R.id.b05) {

        }
        if (id == R.id.b06) {

        }
        if (id == R.id.b07) {

        }
        if (id == R.id.b10) {

        }
        if (id == R.id.b11) {

        }
        if (id == R.id.b12) {

        }
        if (id == R.id.b13) {

        }
        if (id == R.id.b14) {

        }
        if (id == R.id.b15) {

        }
        if (id == R.id.b16) {

        }
        if (id == R.id.b17) {

        }
        if (id == R.id.b20) {

        }
        if (id == R.id.b21) {

        }
        if (id == R.id.b22) {

        }
        if (id == R.id.b23) {

        }
        if (id == R.id.b24) {

        }
        if (id == R.id.b25) {

        }
        if (id == R.id.b26) {

        }
        if (id == R.id.b27) {

        }
        if (id == R.id.b30) {

        }
        if (id == R.id.b31) {

        }
        if (id == R.id.b32) {

        }
        if (id == R.id.b33) {

        }
        if (id == R.id.b34) {

        }
        if (id == R.id.b35) {

        }
        if (id == R.id.b36) {

        }
        if (id == R.id.b37) {

        }
        if (id == R.id.b40) {

        }
        if (id == R.id.b41) {

        }
        if (id == R.id.b42) {

        }
        if (id == R.id.b43) {

        }
        if (id == R.id.b44) {

        }
        if (id == R.id.b45) {

        }
        if (id == R.id.b46) {

        }
        if (id == R.id.b47) {

        }
        if (id == R.id.b50) {

        }
        if (id == R.id.b51) {

        }
        if (id == R.id.b52) {

        }
        if (id == R.id.b53) {

        }
        if (id == R.id.b54) {

        }
        if (id == R.id.b55) {

        }
        if (id == R.id.b56) {

        }
        if (id == R.id.b57) {

        }
        if (id == R.id.b60) {

        }
        if (id == R.id.b61) {

        }
        if (id == R.id.b62) {

        }
        if (id == R.id.b63) {

        }
        if (id == R.id.b64) {

        }
        if (id == R.id.b65) {

        }
        if (id == R.id.b66) {

        }
        if (id == R.id.b67) {

        }
        if (id == R.id.b60) {

        }
        if (id == R.id.b71) {

        }
        if (id == R.id.b72) {

        }
        if (id == R.id.b73) {

        }
        if (id == R.id.b74) {

        }
        if (id == R.id.b75) {

        }
        if (id == R.id.b76) {

        }
        if (id == R.id.b77) {

        }
    }
}
