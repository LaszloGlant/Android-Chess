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
import android.widget.Toast;

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
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        switch (id){

            case R.id.play:

                Toast.makeText(getApplicationContext(), "Hit Play menu item", Toast.LENGTH_SHORT).show();

                break;

            case R.id.save:

                Toast.makeText(getApplicationContext(), "Hit Save menu item", Toast.LENGTH_SHORT).show();

                break;

            case R.id.replay:

                Toast.makeText(getApplicationContext(), "Hit Replay menu item", Toast.LENGTH_SHORT).show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void undo(View v){
        Button message = (Button) findViewById(R.id.message);
        message.setText("Have undone last move");
    }

    public void ai(View v){
        Button message = (Button) findViewById(R.id.message);
        message.setText("AI has made a move for " + charToStr(currP));
    }

    public void draw(View v){
        Button message = (Button) findViewById(R.id.message);
        message.setText("Draw");
    }

    public void resign(View v){
        Button message = (Button) findViewById(R.id.message);
        message.setText("Resign: " + charToStr(oppP) + " wins!");
    }

    public void hit(View v) {
        int id = v.getId();
        Button message = (Button) findViewById(R.id.message);

        int r = setRC(id)[0];
        int c = setRC(id)[1];


        System.out.println("Have clicked on square at " + r + "," + c);
        if (numHits % 2 == 0) {
            // hitting source

            if (Board.isOccupied(board, r, c) == false) {
                // clicking on a square with no piece on it
                message.setText("Click on a " + charToStr(currP) + " piece");
                return;
            }

            if (board[r][c].color == currP) {

            } else {
                // wrong color, shouldn't be moving this piece
                message.setText("You can't move a " + charToStr(oppP) + " piece");
                return;
            }

            prevR = r;
            prevC = c;

            currImage = getImage(r, c);
            message.setText("Now select destination for " + board[r][c].toString());
        } else {
            // hitting destination

            if (move(currP, prevR, prevC, r, c, turn) > 0) {
                // assuming move is good passed this point, update real board

                turn++;
                message.setText("Good move, " + charToStr(currP) + "! Now " + charToStr(oppP) + "'s turn");

                if (turn % 2 == 0) {
                    // white/blue's turn
                    currP = 'w';
                    oppP = 'b';
                } else {
                    // black/red's turn
                    currP = 'b';
                    oppP = 'w';
                }
            } else {
                // move was invalid, return
                message.setText("Bad move, re-select destination for " + board[r][c].toString());
                return;
            }

        }

        numHits++;
        System.out.println("numHits: " + numHits);
    }

    /**
     * move a piece from (r1, c1) to (r2, c2), execute move if valid, don't execute if invalid
     * @param p w for white's turn, b for black's turn
     * @param r1 initial row
     * @param c1 initial column
     * @param r2 final row
     * @param c2 final column
     * @param i current turn (ex. 0 for white's first turn, 1 for black's first turn, 2 for white's second turn, etc)
     * @return negative number if move is bad, positive number if move is good
     */
    public int move(char p, int r1, int c1, int r2, int c2, int i) {
        Button message = (Button) findViewById(R.id.message);

        ImageButton srcButton = (ImageButton) findViewById(makeButtonId(r1, c1));
        ImageButton destButton = (ImageButton) findViewById(makeButtonId(r2, c2));
        System.out.println("destination is " + r2 + "," + c2);

        // if not moving piece at all, error
        if (r1 == r2 && c1 == c2) {
            message.setText("Can't move piece to same square");
            return -10;
        }

        // make sure no own piece at destination
        if (Board.isOccupied(board, r2, c2)) {
            // destination is occupied
            if (board[r2][c2].color == p) {
                message.setText("Destination is occupied by own piece");
                return -12;
            }
        }

        Piece one = board[r1][c1];

        // checks on piece at initial location
        int isValid;
        boolean promoted = false;

        if (one.name == 'p') {
            // piece is pawn, move pawn

            if (Move.canEnPassant(board, p, r1, c1, r2, c2, i)) {
                // all conditions for en passant meet and destination is correct

                // move to destination
                board[r2][c2] = new Piece(one.color, one.name, one.numMoves + 1, i);
                destButton.setImageResource(currImage);

                // delete old position piece
                board[r1][c1] = new Piece(' ', ' ', 0, -1);
                srcButton.setImageResource(R.drawable.blank);
                return 3;
            }
            isValid = Move.movePawn(board, p, r1, c1, r2, c2, i);

            if (isValid > 0) {
                // move was valid, see if can upgrade pawn
                // if reach end (r = 0 for white, r = 7 for black), promote to promo (Q by default)
                if (p == 'w') {
                    if (r2 == 0) {
                        promoted = true;
                    }
                }

                if (p == 'b') {
                    if (r2 == 7) {
                        promoted = true;
                    }
                }
            }

        } else if (one.name == 'N') {
            // piece is knight, move knight
            isValid = Move.moveKnight(board, p, r1, c1, r2, c2, i);
        } else if (one.name == 'B') {
            // piece is bishop, move bishop
            isValid = Move.moveBishop(board, p, r1, c1, r2, c2, i);
        } else if (one.name == 'R') {
            // piece is rook, move rook
            isValid = Move.moveRook(board, p, r1, c1, r2, c2, i);
        } else if (one.name == 'Q') {
            // piece is queen, move queen
            isValid = Move.moveQueen(board, p, r1, c1, r2, c2, i);
        } else if (one.name == 'K') {
            // piece is king, move king

            if (Move.canCastle(board, p, r1, c1, r2, c2, i) > 0) {

                if (c2 > c1) {
                    // rightwards, kingside

                    // check conditions good
                    board[r2][c2] = new Piece(one.color, one.name, one.numMoves + 1, i);
                    destButton.setImageResource(currImage);

                    board[r1][c1] = new Piece(' ', ' ', 0, -1);
                    srcButton.setImageResource(R.drawable.blank);

                    board[r1][5] = new Piece(p, 'R', board[r1][7].numMoves + 1, board[r1][7].lastMoved);
                    ImageButton buttonR = (ImageButton) findViewById(makeButtonId(r1, 5));
                    if (p == 'w') {
                        buttonR.setImageResource(R.drawable.brook);
                    } else {
                        buttonR.setImageResource(R.drawable.rrook);
                    }

                    board[r1][7] = new Piece(' ', ' ', 0, -1);
                    ImageButton srcButton2 = (ImageButton) findViewById(makeButtonId(r1, 7));
                    srcButton2.setImageResource(R.drawable.blank);
                } else {
                    // check conditions good
                    board[r2][c2] = new Piece(one.color, one.name, one.numMoves + 1, i);
                    destButton.setImageResource(currImage);

                    board[r1][c1] = new Piece(' ', ' ', 0, -1);
                    srcButton.setImageResource(R.drawable.blank);

                    board[r1][3] = new Piece(p, 'R', board[r1][0].numMoves + 1, board[r1][0].lastMoved);
                    ImageButton buttonR = (ImageButton) findViewById(makeButtonId(r1, 3));
                    if (p == 'w') {
                        buttonR.setImageResource(R.drawable.brook);
                    } else {
                        buttonR.setImageResource(R.drawable.rrook);
                    }

                    board[r1][0] = new Piece(' ', ' ', 0, -1);
                    ImageButton srcButton2 = (ImageButton) findViewById(makeButtonId(r1, 0));
                    srcButton2.setImageResource(R.drawable.blank);
                }


                if (p == 'w'){
                    Piece.whiteKing[0]=r2;
                    Piece.whiteKing[1]=c2;
                }
                else {
                    Piece.blackKing[0]=r2;
                    Piece.blackKing[1]=c2;
                }

                return 2;
            }

            isValid = Move.moveKing(board, p, r1, c1, r2, c2, i);
            if (isValid>0){
                if (p == 'w'){
                    Piece.whiteKing[0]=r2;
                    Piece.whiteKing[1]=c2;
                }
                else {
                    Piece.blackKing[0]=r2;
                    Piece.blackKing[1]=c2;
                }
            }
        } else {
            // shouldn't happen, only 6 pieces
            return -1;
        }

        if (isValid < 0) {
            // not ok move
            message.setText("Illegal move");
            return -1;
        }

        if (promoted == true) {
            // Piece was a pawn and reached far end of board, change name of P to Q
            board[r2][c2] = new Piece(one.color, 'Q', one.numMoves + 1, i);
            if (one.color == 'w') {
                destButton.setImageResource(R.drawable.bqueen);
            } else {
                destButton.setImageResource(R.drawable.rqueen);
            }


            board[r1][c1] = new Piece(' ', ' ', 0, -1);
            srcButton.setImageResource(R.drawable.blank);
        } else {
            // normally move piece and delete old
            board[r2][c2] = new Piece(one.color, one.name, one.numMoves + 1, i);
            destButton.setImageResource(currImage);

            board[r1][c1] = new Piece(' ', ' ', 0, -1);
            srcButton.setImageResource(R.drawable.blank);
        }
        promoted = false;
        return isValid;
    }

    public int[] setRC(int id) {
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

        int[] arr = {r, c};
        return arr;
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
