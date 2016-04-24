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

        int r = -1;
        int c = -1;

        if (id == R.id.b00) {
            r = 0;
            c = 0;
        }
        if (id == R.id.b01) {
            r = 0;
            c = 1;
        }
        if (id == R.id.b02) {
            r = 0;
            c = 2;
        }
        if (id == R.id.b03) {
            r = 0;
            c = 3;
        }
        if (id == R.id.b04) {
            r = 0;
            c = 4;
        }
        if (id == R.id.b05) {
            r = 0;
            c = 5;
        }
        if (id == R.id.b06) {
            r = 0;
            c = 6;
        }
        if (id == R.id.b07) {
            r = 0;
            c = 7;
        }
        if (id == R.id.b10) {
            r = 1;
            c = 0;
        }
        if (id == R.id.b11) {
            r = 1;
            c = 1;
        }
        if (id == R.id.b12) {
            r = 1;
            c = 2;
        }
        if (id == R.id.b13) {
            r = 1;
            c = 3;
        }
        if (id == R.id.b14) {
            r = 1;
            c = 4;
        }
        if (id == R.id.b15) {
            r = 1;
            c = 5;
        }
        if (id == R.id.b16) {
            r = 1;
            c = 6;
        }
        if (id == R.id.b17) {
            r = 1;
            c = 7;
        }
        if (id == R.id.b20) {
            r = 2;
            c = 0;
        }
        if (id == R.id.b21) {
            r = 2;
            c = 1;
        }
        if (id == R.id.b22) {
            r = 2;
            c = 2;
        }
        if (id == R.id.b23) {
            r = 2;
            c = 3;
        }
        if (id == R.id.b24) {
            r = 2;
            c = 4;
        }
        if (id == R.id.b25) {
            r = 2;
            c = 5;
        }
        if (id == R.id.b26) {
            r = 2;
            c = 6;
        }
        if (id == R.id.b27) {
            r = 2;
            c = 7;
        }
        if (id == R.id.b30) {
            r = 3;
            c = 0;
        }
        if (id == R.id.b31) {
            r = 3;
            c = 1;
        }
        if (id == R.id.b32) {
            r = 3;
            c = 2;
        }
        if (id == R.id.b33) {
            r = 3;
            c = 3;
        }
        if (id == R.id.b34) {
            r = 3;
            c = 4;
        }
        if (id == R.id.b35) {
            r = 3;
            c = 5;
        }
        if (id == R.id.b36) {
            r = 3;
            c = 6;
        }
        if (id == R.id.b37) {
            r = 3;
            c = 7;
        }
        if (id == R.id.b40) {
            r = 4;
            c = 0;
        }
        if (id == R.id.b41) {
            r = 4;
            c = 1;
        }
        if (id == R.id.b42) {
            r = 4;
            c = 2;
        }
        if (id == R.id.b43) {
            r = 4;
            c = 3;
        }
        if (id == R.id.b44) {
            r = 4;
            c = 4;
        }
        if (id == R.id.b45) {
            r = 4;
            c = 5;
        }
        if (id == R.id.b46) {
            r = 4;
            c = 6;
        }
        if (id == R.id.b47) {
            r = 4;
            c = 7;
        }
        if (id == R.id.b50) {
            r = 5;
            c = 0;
        }
        if (id == R.id.b51) {
            r = 5;
            c = 2;
        }
        if (id == R.id.b52) {
            r = 5;
            c = 2;
        }
        if (id == R.id.b53) {
            r = 5;
            c = 3;
        }
        if (id == R.id.b54) {
            r = 5;
            c = 4;
        }
        if (id == R.id.b55) {
            r = 5;
            c = 5;
        }
        if (id == R.id.b56) {
            r = 5;
            c = 6;
        }
        if (id == R.id.b57) {
            r = 5;
            c = 7;
        }
        if (id == R.id.b60) {
            r = 6;
            c = 0;
        }
        if (id == R.id.b61) {
            r = 6;
            c = 1;
        }
        if (id == R.id.b62) {
            r = 6;
            c = 2;
        }
        if (id == R.id.b63) {
            r = 6;
            c = 3;
        }
        if (id == R.id.b64) {
            r = 6;
            c = 4;
        }
        if (id == R.id.b65) {
            r = 6;
            c = 5;
        }
        if (id == R.id.b66) {
            r = 6;
            c = 6;
        }
        if (id == R.id.b67) {
            r = 6;
            c = 7;
        }
        if (id == R.id.b70) {
            r = 7;
            c = 0;
        }
        if (id == R.id.b71) {
            r = 7;
            c = 1;
        }
        if (id == R.id.b72) {
            r = 7;
            c = 2;
        }
        if (id == R.id.b73) {
            r = 7;
            c = 3;
        }
        if (id == R.id.b74) {
            r = 7;
            c = 4;
        }
        if (id == R.id.b75) {
            r = 7;
            c = 5;
        }
        if (id == R.id.b76) {
            r = 7;
            c = 6;
        }
        if (id == R.id.b77) {
            r = 7;
            c = 6;
        }

        if (numHits % 2 == 0) {
            // hitting source
        } else {
            // hitting destination
        }

        numHits++;
    }
}
