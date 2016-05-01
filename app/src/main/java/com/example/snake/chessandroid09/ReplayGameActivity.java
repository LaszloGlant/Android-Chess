package com.example.snake.chessandroid09;

import android.app.AlertDialog;
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
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Brian Wong, Laszlo Glant
 * Replay Game Activity screen
 *
 */
public class ReplayGameActivity extends AppCompatActivity {

    int pbIndex = 0;
    int gameIndex = 0;
    int currImage;

    Piece[][] board = new Piece[8][8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replay_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        switch (id) {
            case R.id.play:
                finish();
                Board.initWhite(board);
                Board.initBoard(board);
                //MainActivity.copy(board, boardCopy);
                drawBoard();

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

    public void hit(View v) {
        Toast.makeText(getApplicationContext(), "You can't manipulate the board when you're in replay game mode", Toast.LENGTH_SHORT).show();
    }

    public void recordedGames(View v) {
        Toast.makeText(getApplicationContext(), "This should open the list of recorded games!", Toast.LENGTH_SHORT).show();

    }

    public void forward(View v) {
        Button message = (Button) findViewById(R.id.message);
        if (MainActivity.myGames.size() == 0){
            return;
        }

        if (pbIndex < MainActivity.myGames.get(gameIndex).moves.size()) {

        } else {
            message.setText("Game Over");
            return;
        }

        System.out.println("number of moves this game: " + MainActivity.myGames.get(gameIndex).moves.size());

        int r1 = MainActivity.myGames.get(gameIndex).moves.get(pbIndex).r1;
        int c1 = MainActivity.myGames.get(gameIndex).moves.get(pbIndex).c1;
        int r2 = MainActivity.myGames.get(gameIndex).moves.get(pbIndex).r2;
        int c2 = MainActivity.myGames.get(gameIndex).moves.get(pbIndex).c2;

        char p = 'w';

        if (pbIndex % 2 == 0) {
            // even, white's turn
            p = 'w';
        } else {
            p = 'b';
        }

        int ret = execute(p, r1, c1, r2, c2, pbIndex);
        pbIndex++;

        message.setText("Move " + pbIndex);
    }

    /**
     * move a piece from (r1, c1) to (r2, c2), execute move if valid, don't execute if invalid
     *
     * @param p  w for white's turn, b for black's turn
     * @param r1 initial row
     * @param c1 initial column
     * @param r2 final row
     * @param c2 final column
     * @param i  current turn (ex. 0 for white's first turn, 1 for black's first turn, 2 for white's second turn, etc)
     * @return negative number if move is bad, positive number if move is good
     */
    public int execute(char p, int r1, int c1, int r2, int c2, int i) {
        System.out.printf("%d %d %d %d\n", r1, c1, r2, c2);
        Button message = (Button) findViewById(R.id.message);

        currImage = getImage(r1, c1);

        ImageButton srcButton = (ImageButton) findViewById(makeButtonId(r1, c1));
        ImageButton destButton = (ImageButton) findViewById(makeButtonId(r2, c2));

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

                if (c2 > c1) {
                    // moving to the right
                    ImageButton captured = (ImageButton) findViewById(makeButtonId(r1, c1 + 1));
                    captured.setImageResource(R.drawable.blank);
                } else {
                    // moving to the left
                    ImageButton captured = (ImageButton) findViewById(makeButtonId(r1, c1 - 1));
                    captured.setImageResource(R.drawable.blank);
                }

                message.setText(charToStr(p) + " has performed en passant");
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


                if (p == 'w') {
                    Piece.whiteKing[0] = r2;
                    Piece.whiteKing[1] = c2;
                } else {
                    Piece.blackKing[0] = r2;
                    Piece.blackKing[1] = c2;
                }

                return 2;
            }

            isValid = Move.moveKing(board, p, r1, c1, r2, c2, i);
            if (isValid > 0) {
                if (p == 'w') {
                    Piece.whiteKing[0] = r2;
                    Piece.whiteKing[1] = c2;
                } else {
                    Piece.blackKing[0] = r2;
                    Piece.blackKing[1] = c2;
                }
            }
        } else {
            // shouldn't happen, only 6 pieces
            return -1;
        }

        if (isValid < 0) {
            // not ok move
            message.setText(board[r1][c1].toString() + " from " + toCoord(r1, c1) + " to " + toCoord(r2, c2) + " is invalid");
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
        //message.setText("Have moved " + board[r2][c2] + " from " + toCoord(r1, c1) + " to " + toCoord(r2, c2));
        return isValid;
    }

    public String toCoord(int r, int c) {
        char[] lets = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        int[] nums = {8, 7, 6, 5, 4, 3, 2, 1};
        return lets[c] + "" + nums[r];
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
            return R.drawable.blank;
        }
        if (board[r][c].toString().equals("##")) {
            return R.drawable.blank;
        }

        // shouldn't get down to here
        return R.drawable.rbishop;
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
            return R.id.b40;
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

        // shouldn't get to down here anyway
        return R.id.b00;
    }

    public void drawBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int image = getImage(i, j);
                ImageButton b = (ImageButton) findViewById(makeButtonId(i, j));
                b.setImageResource(image);
            }
        }
    }
}
