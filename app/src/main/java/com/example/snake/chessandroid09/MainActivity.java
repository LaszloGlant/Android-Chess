package com.example.snake.chessandroid09;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    int numHits = 0;
    int turn = 0;
    int pbIndex = 0;

    int currImage;

    char currP = 'w';
    char oppP = 'b';

    int prevR;
    int prevC;

    boolean isOver = false;
    boolean haveJustUndone = false;

    Piece[][] board = new Piece[8][8];
    Piece[][] boardCopy = new Piece[8][8];

    ArrayList<RecordedGame> myGames = new ArrayList<RecordedGame>();
    ArrayList<Pair> savedPairs = new ArrayList<Pair>();

    /*
Remaining Tasks:
- Get check to identify correctly (completed as well as checkmate with AI)
- Disallow move that ends turn with self in check (completed)
- Disallow AI to put self in check (completed)
- If in check and hit AI button, get out of check (completed)
- Get Utility to stop giving errors (might abandon and re-write with txt file input, use s.concat("abc") to append a string to end of another string)
- Get game to stop crashing when hit Draw/Resign (completed)
- prompt user for draw
- prompt user for piece to promote pawn to
- take last move off of savedPairs when undo a move
- prompt user for game title
- list recorded games
- sort games
- playback a game one move at a time


Chess
Port the terminal-based Chess program to Android: a chess app that lets two people play chess with each other on the phone. You may reuse any code from your chess assignment that you like.
You have to implement all the moves for all the pieces, determination of check, checkmate, and illegal moves (including any that puts the mover's King in check), but you are not required
to implement stalemate.

Your app should have a Home activity that lets you choose from the following three other activities:
Playing chess (120 pts)
•  30 pts Two humans can use your app to play a game of Chess.
•  20 pts Your app must draw the board with icons and correctly shaded squares.
•  20 pts Players must move their pieces using touch input - either dragging a piece or touching first the piece's original square and then its destination.
•  10 pts Provide an 'undo' button that will undo the last move (but no farther).
•  10 pts Provide an 'AI' button that will choose a move for the current player. Choosing randomly from the set of legal moves is sufficient.
•  20 pts Provide functional 'draw' and 'resign' buttons.
•  10 pts When the game is over, report the outcome.

Recording games (50 pts)
•  20 pts Record all games as they're being played.
•  10 pts At the conclusion of a game, offer to store it and prompt the user for a game title.
•  20 pts List all recorded games, sorted by both date and by title (user can select which view to choose).

Game playback (30 pts)
•  A button that allows the user to play a selected game. The selected game should be playable one move at a time, per player.

*/

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

        /*
        File g = new File("games.ser");
        if (g.exists()) {
            myGames = Utility.input();
        } else {
            Utility.output(myGames);
        }
        */

        Board.initWhite(board);
        Board.initBoard(board);

        copy(board, boardCopy);

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
        switch (id) {
            case R.id.play:

                Toast.makeText(getApplicationContext(), "You are in play chess mode!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main:
                Toast.makeText(getApplicationContext(), "THIS MENU IS PROBALY NOT NEEDED! WILL SEE!", Toast.LENGTH_LONG).show();
                break;
            case R.id.replay:
                startActivity(new Intent(getApplicationContext(), ReplayGameActivity.class));
                Toast.makeText(getApplicationContext(), "Hit Replay menu item", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * copy contents from board, put in board2
     *
     * @param board  2D array of Pieces
     * @param board2 secondary 2D array of Pieces
     */
    public void copy(Piece[][] board, Piece[][] board2) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board2[i][j] = board[i][j];
            }
        }
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

    public void undo(View v) {
        if (isOver) {
            return;
        }

        if (haveJustUndone) {
            // don't go back any farther
            return;
        }

        if (turn == 0) {
            return;
        }

        Button message = (Button) findViewById(R.id.message);

        copy(boardCopy, board);
        Board.displayBoard(board);
        drawBoard();

        if (savedPairs.size() > 0) {
            // take last element out from savedPairs
            savedPairs.remove(savedPairs.size() - 1);
        }

        updateTurn();

        message.setText("Have undone last move, " + charToStr(currP) + " to play");
        haveJustUndone = true;
    }

    public void ai(View v) {
        if (isOver) {
            return;
        }

        Button message = (Button) findViewById(R.id.message);

        copy(board, boardCopy);

        int statusAI = AI(board, currP, turn);     // make AI move

        if (statusAI < 0) {
            // no legal move for AI to do
            message.setText("Checkmate for " + charToStr(oppP));
            isOver = true;
            return;
        }

        if (Conditions.isCheck(board, oppP, turn)) {
            // have put opponent in check
            if (oppP == 'w') {
                if (Conditions.isCheckmate(board, 'w', Piece.whiteKing[0], Piece.whiteKing[1], turn)) {
                    // checkmate
                    message.setText("Checkmate, Black wins");
                    gameOver();
                } else {
                    message.setText("White in Check");
                }
            } else {
                if (Conditions.isCheckmate(board, 'b', Piece.blackKing[0], Piece.blackKing[1], turn)) {
                    // checkmate
                    message.setText("Checkmate, White wins");
                    gameOver();
                } else {
                    message.setText("Black in Check");
                }
            }
        } else {
            message.setText("AI has made a move for " + charToStr(currP));
        }

        updateTurn();

        if (numHits % 2 != 0) {
            // odd number, need to increment so next src selection not messed up
            numHits++;
        }
    }

    public void draw(View v) {
        if (isOver) {
            return;
        }

        Button message = (Button) findViewById(R.id.message);

        gameOver();

        //Utility.output(myGames);

        message.setText("Draw");
    }

    public void resign(View v) {
        if (isOver) {
            return;
        }

        Button message = (Button) findViewById(R.id.message);

        gameOver();

        //Utility.output(myGames);

        message.setText("Resign: " + charToStr(oppP) + " wins!");
    }

    public void gameOver() {
        isOver = true;

        Calendar c = new GregorianCalendar();
        c.set(Calendar.MILLISECOND, 0);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        myGames.add(new RecordedGame("myGame", year, month, day, savedPairs));

        output(myGames);
    }

    public void hit(View v) {
        if (isOver) {
            // game is over, just return so that user cannot move any pieces
            return;
        }

        int id = v.getId();
        Button message = (Button) findViewById(R.id.message);

        int r = setRC(id)[0];
        int c = setRC(id)[1];

        if (numHits % 2 == 0) {
            // hitting source

            if (Board.isOccupied(board, r, c) == false) {
                // clicking on a square with no piece on it
                message.setText("Click on a " + charToStr(currP) + " piece");
                return;
            }

            if (board[r][c].color != currP) {
                // wrong color, shouldn't be moving this piece
                message.setText("You can't move a " + charToStr(oppP) + " piece");
                return;
            }

            prevR = r;
            prevC = c;

            currImage = getImage(r, c);
            message.setText("Now select destination for " + board[r][c].toString() + " at " + toCoord(r, c));

        } else {
            // hitting destination

            copy(board, boardCopy);

            // move only good if valid and do not end with king in check

            int okMove = move(currP, prevR, prevC, r, c, turn);

            /*
            if move is invalid, print invalid and take action
            if move is good, but puts self in check, print that and take action
            if move is good, do what normally should do, execute move
            then at end update turn and increment numHits like would do normally
             */

            if (okMove < 0) {
                // move was invalid
                numHits++;
                return;
            } else if (Conditions.isCheck(board, currP, turn)) {
                // move was valid, but put self in check, not good, have to take that move back

                copy(boardCopy, board);
                drawBoard();
                message.setText("You can't end your turn in check, try again");
                numHits++;
                return;
            } else {
                // move was good

                // check if put opponent in check or not
                if (Conditions.isCheck(board, oppP, turn)) {
                    // in check
                    if (oppP == 'w') {
                        if (Conditions.isCheckmate(board, 'w', Piece.whiteKing[0], Piece.whiteKing[1], turn)) {
                            // checkmate
                            message.setText("Checkmate, Black wins");
                            gameOver();
                        } else {
                            message.setText("White in Check");
                        }
                    } else {
                        if (Conditions.isCheckmate(board, 'b', Piece.blackKing[0], Piece.blackKing[1], turn)) {
                            // checkmate
                            message.setText("Checkmate, White wins");
                            gameOver();
                        } else {
                            message.setText("Black in Check");
                        }
                    }
                }

            }

            // do at end of every good move

            // update saved pairs array list
            savedPairs.add(new Pair(prevR, prevC, r, c));

            // advance turn to next player
            updateTurn();

            Board.displayBoard(board);
        }

        // do at end of each turn, whether hitting src or dest
        numHits++;

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

    public int move(char p, int r1, int c1, int r2, int c2, int i) {
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
        message.setText("Have moved " + board[r2][c2] + " from " + toCoord(r1, c1) + " to " + toCoord(r2, c2));
        return isValid;
    }

    /**
     * AI should do any 1 legal move for player p
     *
     * @param board 2D array of pieces
     * @param p     w or b
     * @param i     turn number
     * @return positive number if sucessfully did a legal move, negative number if nothing legal to do
     */
    public int AI(Piece[][] board, char p, int i) {
        copy(board, boardCopy);
        for (int r1 = 0; r1 < 8; r1++) {
            for (int c1 = 0; c1 < 8; c1++) {
                if (board[r1][c1].color == p) {
                    // own piece

                    for (int r2 = 0; r2 < 8; r2++) {
                        for (int c2 = 0; c2 < 8; c2++) {

                            if (Move.movePiece(board, p, r1, c1, r2, c2, i)) {
                                // one of our pieces has this legal move (r1, c1) to (r2, c2), execute that move
                                int ret = move(p, r1, c1, r2, c2, i);

                                if (Conditions.isCheck(board, p, i)) {
                                    // this move will put self in check, take back
                                    copy(boardCopy, board);
                                    drawBoard();

                                    // skip this move, still looking for something legal to do
                                    continue;
                                } else {
                                    // not in check, this move is good
                                    savedPairs.add(new Pair(r1, c1, r2, c2));
                                    return 1;
                                }

                            } else {
                                // not a legal move, don't attempt to do this
                                continue;
                            }
                        }
                    }

                } else {
                    // not own piece, don't attempt to move this one
                    continue;
                }
            }
        }
        return -1;
    }

    public void playBack(RecordedGame rg) {
        for (int i = 0; i < rg.moves.size(); i++) {
            int r1 = rg.moves.get(i).r1;
            int c1 = rg.moves.get(i).c1;
            int r2 = rg.moves.get(i).r2;
            int c2 = rg.moves.get(i).c2;

            char p;
            if (i % 2 == 0) {
                p = 'w';
            } else {
                p = 'b';
            }

            int ret = move(p, r1, c1, r2, c2, i);
        }
    }

    /**
     * given the master list of games, store them to disk
     * @param games master list of games
     */
    public void output(ArrayList<RecordedGame> games) {
        try {
            File o = new File("output.txt");
            o.createNewFile();

            PrintWriter out = new PrintWriter(new FileWriter(o, false), true);
            for (int i = 0; i < games.size(); i++) {
                out.println(outString(games.get(i)));
            }
            out.close();
            return;
        } catch (Exception e) {
            return;
        }
    }

    /**
     * read in the master list from games from disk and load them up
     * @param empty blank array list to be loaded up with data
     * @param o output.txt
     */
    public void input(ArrayList<RecordedGame> empty, File o) {
        try {
            String line = null;
            FileReader fileReader = new FileReader(o);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                stickIn1Line(empty, line);
            }
        } catch (Exception e) {
            System.out.println("input exception");
        }
    }

    /**
     * given one line (ex. myGame,2016,4,25,e2 e4), add the appropriate info to empty
     * @param empty blank array list to be loaded up with data
     * @param line 1 line of text from output.txt
     */
    public void stickIn1Line(ArrayList<RecordedGame> empty, String line) {

    }

    public String toCoord(int r, int c) {
        char[] lets = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        int[] nums = {8, 7, 6, 5, 4, 3, 2, 1};
        return lets[c] + "" + nums[r];
    }

    public String toMovement(int r1, int c1, int r2, int c2) {
        return toCoord(r1, c1) + "-" + toCoord(r2, c2);
    }

    public String savedPairsStr(ArrayList<Pair> sp) {
        String ret = "";
        for (int i = 0; i < sp.size(); i++) {
            int r1 = sp.get(i).r1;
            int c1 = sp.get(i).c1;
            int r2 = sp.get(i).r2;
            int c2 = sp.get(i).c2;
            ret.concat(toCoord(r1, c1) + " " + toCoord(r2, c2) + ",");
        }
        return ret;
    }


    public String outString(RecordedGame rg) {
        return rg.title + "," + rg.year + "," + rg.month + "," + savedPairsStr(rg.moves);
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

    public void updateTurn() {
        turn++;

        if (turn % 2 == 0) {
            // white/blue's turn
            currP = 'w';
            oppP = 'b';
        } else {
            // black/red's turn
            currP = 'b';
            oppP = 'w';
        }

        Button ai = (Button) findViewById(R.id.bAI);
        Button resign = (Button) findViewById(R.id.bForward);

        ai.setText("AI (" + charToStr(currP) + ")");
        resign.setText("Resign (" + charToStr(currP) + ")");

        haveJustUndone = false;

    }

    public void printPairs() {
        for (int i = 0; i < savedPairs.size(); i++) {
            int r1 = savedPairs.get(i).r1;
            int c1 = savedPairs.get(i).c1;
            int r2 = savedPairs.get(i).r2;
            int c2 = savedPairs.get(i).c2;
            System.out.println(toCoord(r1, c1) + " " + toCoord(r2, c2));
        }
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
            c = 1;
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


}
