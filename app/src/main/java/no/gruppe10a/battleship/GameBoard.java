package no.gruppe10a.battleship;

import android.media.Image;

/**
 * Created by Eirik on 09/04/15.
 */
public class GameBoard {
    Image [][] board;

    public GameBoard() {
        board = new Image [10][10];
        for (int i = 0; i < board.length; i ++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = null;
            }

        }
    }

    public void addShip(int [][] pos) {
        switch (pos.length) {
            case 2:
                

        }
    }
}
