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

    int numHits = 0;
    int turn = 0;
    int currImage = R.drawable.rbishop;
    char currP = 'w';
    char oppP = 'b';

    int prevR;
    int prevC;

    Piece[][] board = new Piece[8][8];

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
        Board.initWhite(board);
        Board.initBoard(board);
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

    public void undo(View v){
        System.out.println("hit undo");
    }

    public void ai(View v){
        System.out.println("hit ai");
    }

    public void draw(View v){//maybe current player
        System.out.println("hit draw");
    }

    public void resign(View v){//maybe current player
        System.out.println("hit resign");
    }

    public void hit(View v) {
        int id = v.getId();
        ImageButton currButton = (ImageButton) v;

        Button message = (Button) findViewById(R.id.message);

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
            c = 7;
        }

        if (turn % 2 == 0) {
            // white/blue's turn
            currP = 'w';
            oppP = 'b';
        } else {
            // black/red's turn
            currP = 'b';
            oppP = 'w';
        }

        System.out.println("Have clicked on square at " + r + "," + c);
        if (numHits % 2 == 0) {
            // hitting source

            if (Board.isOccupied(board, r, c) == false) {
                // clicking on a square with no piece on it
                message.setText("Don't click on empty square");

                return;
            }

            if (board[r][c].color == currP) {

            } else {
                // wrong color, shouldn't be moving this piece
                message.setText("Move your own piece");
                return;
            }

            prevR = r;
            prevC = c;

            currImage = getImage(r, c);
            message.setText("Now select destination");
        } else {
            // hitting destination

            // set destination square to currImage
            ((ImageButton) v).setImageResource(currImage);

            // set src square to blank
            ImageButton srcButton = (ImageButton) findViewById(makeButtonId(prevR, prevC));

            //srcButton.setVisibility(View.INVISIBLE);
            srcButton.setImageResource(R.drawable.blank);

            ImageButton destButton = (ImageButton) findViewById(makeButtonId(r, c));
            //destButton.setVisibility(View.VISIBLE);

            Piece one = board[prevR][prevC];
            board[r][c] = new Piece(one.color, one.name, one.numMoves + 1, numHits);
            board[prevR][prevC] = new Piece(' ', ' ', 0, -1);
            turn++;
            message.setText("Good move, " + charToStr(currP) + "! Now " + charToStr(oppP) + "'s turn");
        }

        numHits++;
        System.out.println("numHits: " + numHits);
    }

    public String charToStr(char p) {
        if (p == 'w') {
            return "White";
        } else if (p == 'b') {
            return "Black";
        } else {
            // can't happen
            return "Bogus";
        }
    }

    public int getImage(int r, int c) {
        if (board[r][c].toString().equals("bp")) {
            return R.drawable.rpawn;
        }
        if (board[r][c].toString().equals("bN")) {
            return R.drawable.rknight;
        }
        if (board[r][c].toString().equals("bB")) {
            return R.drawable.rbishop;
        }
        if (board[r][c].toString().equals("bR")) {
            return R.drawable.rrook;
        }
        if (board[r][c].toString().equals("bQ")) {
            return R.drawable.rqueen;
        }
        if (board[r][c].toString().equals("bK")) {
            return R.drawable.rking;
        }
        if (board[r][c].toString().equals("wp")) {
            return R.drawable.bpawn;
        }
        if (board[r][c].toString().equals("wN")) {
            return R.drawable.bknight;
        }
        if (board[r][c].toString().equals("wB")) {
            return R.drawable.bbishop;
        }
        if (board[r][c].toString().equals("wR")) {
            return R.drawable.brook;
        }
        if (board[r][c].toString().equals("wQ")) {
            return R.drawable.bqueen;
        }
        if (board[r][c].toString().equals("wK")) {
            return R.drawable.bking;
        }
        if (board[r][c].toString().equals("  ")) {
            return R.drawable.selrknight;
        }
        if (board[r][c].toString().equals("##")) {
            return R.drawable.selrknight;
        }

        // shouldn't get down to here
        return R.drawable.rbishop;
    }

    public int makeButtonId(int r, int c) {
        if (r == 0 && c == 0) {
            return R.id.b00;
        }
        if (r == 0 && c == 1) {
            return R.id.b01;
        }
        if (r == 0 && c == 2) {
            return R.id.b02;
        }
        if (r == 0 && c == 3) {
            return R.id.b03;
        }
        if (r == 0 && c == 4) {
            return R.id.b04;
        }
        if (r == 0 && c == 5) {
            return R.id.b05;
        }
        if (r == 0 && c == 6) {
            return R.id.b06;
        }
        if (r == 0 && c == 7) {
            return R.id.b07;
        }
        if (r == 1 && c == 0) {
            return R.id.b10;
        }
        if (r == 1 && c == 1) {
            return R.id.b11;
        }
        if (r == 1 && c == 2) {
            return R.id.b12;
        }
        if (r == 1 && c == 3) {
            return R.id.b13;
        }
        if (r == 1 && c == 4) {
            return R.id.b14;
        }
        if (r == 1 && c == 5) {
            return R.id.b15;
        }
        if (r == 1 && c == 6) {
            return R.id.b16;
        }
        if (r == 1 && c == 7) {
            return R.id.b17;
        }
        if (r == 2 && c == 0) {
            return R.id.b20;
        }
        if (r == 2 && c == 1) {
            return R.id.b21;
        }
        if (r == 2 && c == 2) {
            return R.id.b22;
        }
        if (r == 2 && c == 3) {
            return R.id.b23;
        }
        if (r == 2 && c == 4) {
            return R.id.b24;
        }
        if (r == 2 && c == 5) {
            return R.id.b25;
        }
        if (r == 2 && c == 6) {
            return R.id.b26;
        }
        if (r == 2 && c == 7) {
            return R.id.b27;
        }
        if (r == 3 && c == 0) {
            return R.id.b30;
        }
        if (r == 3 && c == 1) {
            return R.id.b31;
        }
        if (r == 3 && c == 2) {
            return R.id.b32;
        }
        if (r == 3 && c == 3) {
            return R.id.b33;
        }
        if (r == 3 && c == 4) {
            return R.id.b34;
        }
        if (r == 3 && c == 5) {
            return R.id.b35;
        }
        if (r == 3 && c == 6) {
            return R.id.b36;
        }
        if (r == 3 && c == 7) {
            return R.id.b37;
        }
        if (r == 4 && c == 0) {
            return R.id.b34;
        }
        if (r == 4 && c == 1) {
            return R.id.b41;
        }
        if (r == 4 && c == 2) {
            return R.id.b42;
        }
        if (r == 4 && c == 3) {
            return R.id.b43;
        }
        if (r == 4 && c == 4) {
            return R.id.b44;
        }
        if (r == 4 && c == 5) {
            return R.id.b45;
        }
        if (r == 4 && c == 6) {
            return R.id.b46;
        }
        if (r == 4 && c == 7) {
            return R.id.b47;
        }
        if (r == 5 && c == 0) {
            return R.id.b50;
        }
        if (r == 5 && c == 1) {
            return R.id.b51;
        }
        if (r == 5 && c == 2) {
            return R.id.b52;
        }
        if (r == 5 && c == 3) {
            return R.id.b53;
        }
        if (r == 5 && c == 4) {
            return R.id.b54;
        }
        if (r == 5 && c == 5) {
            return R.id.b55;
        }
        if (r == 5 && c == 6) {
            return R.id.b56;
        }
        if (r == 5 && c == 7) {
            return R.id.b57;
        }
        if (r == 6 && c == 0) {
            return R.id.b60;
        }
        if (r == 6 && c == 1) {
            return R.id.b61;
        }
        if (r == 6 && c == 2) {
            return R.id.b62;
        }
        if (r == 6 && c == 3) {
            return R.id.b63;
        }
        if (r == 6 && c == 4) {
            return R.id.b64;
        }
        if (r == 6 && c == 5) {
            return R.id.b65;
        }
        if (r == 6 && c == 6) {
            return R.id.b66;
        }
        if (r == 6 && c == 7) {
            return R.id.b67;
        }
        if (r == 7 && c == 0) {
            return R.id.b70;
        }
        if (r == 7 && c == 1) {
            return R.id.b71;
        }
        if (r == 7 && c == 2) {
            return R.id.b72;
        }
        if (r == 7 && c == 3) {
            return R.id.b73;
        }
        if (r == 7 && c == 4) {
            return R.id.b74;
        }
        if (r == 7 && c == 5) {
            return R.id.b75;
        }
        if (r == 7 && c == 6) {
            return R.id.b76;
        }
        if (r == 7 && c == 7) {
            return R.id.b77;
        }

        // shouldn't get to down here anway
        return R.id.b00;
    }
}
