package com.example.snake.chessandroid09;

/**
 * Board.java creates the 2D array of Piece instances that serves as the game board.
 * Board.java also initializes the game board with all 32 pieces in their starting positions.
 * Methods that check if a square is occupied or if a path is clear are in Board.java.
 * @author Brian Wong, Laszlo Glant
 *
 */
public class Board {
    /**
     * set every tile in the board to blank, needed later
     * @param board 2D array of Pieces
     */
    public static void initWhite(Piece[][] board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Piece(' ', ' ', -1, -1);
            }
        }
    }

    /**
     * initialize white and black Pieces on the board
     * @param board 2D array of Pieces
     */
    public static void initBoard(Piece[][] board) {

        // white pawns
        board[6][0] = new Piece('w', 'p', 0, -1);
        board[6][1] = new Piece('w', 'p', 0, -1);
        board[6][2] = new Piece('w', 'p', 0, -1);
        board[6][3] = new Piece('w', 'p', 0, -1);
        board[6][4] = new Piece('w', 'p', 0, -1);
        board[6][5] = new Piece('w', 'p', 0, -1);
        board[6][6] = new Piece('w', 'p', 0, -1);
        board[6][7] = new Piece('w', 'p', 0, -1);

        // white non-pawns
        board[7][0] = new Piece('w', 'R', 0, -1);
        board[7][1] = new Piece('w', 'N', 0, -1);
        board[7][2] = new Piece('w', 'B', 0, -1);
        board[7][3] = new Piece('w', 'Q', 0, -1);
        board[7][4] = new Piece('w', 'K', 0, -1);
        board[7][5] = new Piece('w', 'B', 0, -1);
        board[7][6] = new Piece('w', 'N', 0, -1);
        board[7][7] = new Piece('w', 'R', 0, -1);

        // black pawns
        board[1][0] = new Piece('b', 'p', 0, -1);
        board[1][1] = new Piece('b', 'p', 0, -1);
        board[1][2] = new Piece('b', 'p', 0, -1);
        board[1][3] = new Piece('b', 'p', 0, -1);
        board[1][4] = new Piece('b', 'p', 0, -1);
        board[1][5] = new Piece('b', 'p', 0, -1);
        board[1][6] = new Piece('b', 'p', 0, -1);
        board[1][7] = new Piece('b', 'p', 0, -1);

        // black non-pawns
        board[0][0] = new Piece('b', 'R', 0, -1);
        board[0][1] = new Piece('b', 'N', 0, -1);
        board[0][2] = new Piece('b', 'B', 0, -1);
        board[0][3] = new Piece('b', 'Q', 0, -1);
        board[0][4] = new Piece('b', 'K', 0, -1);
        board[0][5] = new Piece('b', 'B', 0, -1);
        board[0][6] = new Piece('b', 'N', 0, -1);
        board[0][7] = new Piece('b', 'R', 0, -1);
    }

    /**
     * Checks if there is a piece at entry (r,c)
     * @param board 2D array of Pieces
     * @param r row
     * @param c column
     * @return true if there is a piece at entry (r, c)
     */
    public static boolean isOccupied(Piece[][] board, int r, int c) {
        if (board[r][c].color == 'w') {
            // square at (r, c) is occupied by a piece
            return true;
        }
        else if (board[r][c].color == 'b') {
            // square at (r, c) is occupied by a piece
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if there are no obstacles in way from source to destination (not including destination or source)
     * @param board 2D array of Pieces
     * @param r1 source row
     * @param c1 source column
     * @param r2 destination row
     * @param c2 destination column
     * @return false if obstacles in way, true if make to end and nothing in way
     */
    public static boolean isClear(Piece[][] board, int r1, int c1, int r2, int c2) {
        int i = 0;

        if (r1 == r2) {
            // same rows, moving piece horizontally
            if (c1 > c2) {
                // moving piece horizontally to the left
                for (int c = c1; c > c2; c--, i++) {
                    if (i == 0) {
                        continue;
                    }
                    if (isOccupied(board, r1, c)) {
                        return false;
                    }
                }
            }

            if (c1 < c2) {
                // moving piece horizontally to the right
                for (int c = c1; c < c2; c++, i++) {
                    if (i == 0) {
                        continue;
                    }
                    if (isOccupied(board, r1, c)) {
                        return false;
                    }
                }
            }
        }

        if (c1 == c2) {
            // same columns, moving piece vertically

            if (r1 > r2) {
                // moving piece upwards
                for (int r = r1; r > r2; r--, i++) {
                    if (i == 0) {
                        continue;
                    }
                    if (isOccupied(board, r, c1)) {
                        return false;
                    }
                }
            }

            if (r1 < r2) {
                // moving piece downwards
                for (int r = r1; r < r2; r++, i++) {
                    if (i == 0) {
                        continue;
                    }
                    if (isOccupied(board, r, c1)) {
                        return false;
                    }
                }
            }
        }

        int dr = Math.abs(r1 - r2);		// change in rows
        int dc = Math.abs(c1 - c2);		// change in columns



        if (dr == dc) {
            // moving diagonally

            if (r1 > r2) {
                // move upwards
                if (c1 > c2) {
                    // upwards and to the left (NW)
                    for (int r = r1, c = c1; r > r2; r--, c--, i++) {
                        if (i == 0) {
                            continue;
                        }
                        if (isOccupied(board, r, c)) {
                            return false;
                        }
                    }
                }

                if (c1 < c2) {
                    // upwards and to the right (NE)
                    for (int r = r1, c = c1; r > r2; r--, c++, i++) {
                        if (i == 0) {
                            continue;
                        }
                        if (isOccupied(board, r, c)) {
                            return false;
                        }
                    }
                }
            }

            if (r1 < r2) {
                // move downwards
                if (c1 > c2) {
                    // downwards and to the left (SW)
                    for (int r = r1, c = c1; r < r2; r++, c--, i++) {
                        if (i == 0) {
                            continue;
                        }
                        if (isOccupied(board, r, c)) {
                            return false;
                        }
                    }
                }

                if (c1 < c2) {
                    // downwards and to the right (SE)
                    for (int r = r1, c = c1; r < r2; r++, c++, i++) {
                        if (i == 0) {
                            continue;
                        }
                        if (isOccupied(board, r, c)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * prints out the board as well as 8-1 on the right and a-h on the bottom of the board
     * @param board 2D array of Pieces, serves as board for the game
     */
    public static void displayBoard(Piece[][] board) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                System.out.print(board[r][c].toString() + " ");
            }
            System.out.println(8 - r);
        }
        System.out.println(" a  b  c  d  e  f  g  h");
    }

}
