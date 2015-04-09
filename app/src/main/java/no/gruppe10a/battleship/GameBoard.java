package no.gruppe10a.battleship;

import android.media.Image;

import java.util.ArrayList;

/**
 * Created by Eirik on 09/04/15.
 */
public class GameBoard {
    Ship [][] board;
    ArrayList<Ship> ships;
    ArrayList<Ship> destroyedShips;

    public GameBoard() {
        board = new Ship [10][10];
        ships = new ArrayList<Ship>();
        destroyedShips = new ArrayList<Ship>();
        for (int i = 0; i < board.length; i ++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = null;
            }

        }
    }

    // player adds ships to board and cant add more than the limit
    public boolean addShip(int [][] pos) {

        if (ships.size() < 5) {
            switch (pos.length) {
                case 2:
                    Destroyer destroyer = new Destroyer();
                    ships.add(destroyer);
                    for (int i = 0; i < pos.length; i++) {
                        board[i][i] = destroyer;
                    }
                    return true;
                case 3:
                    Submarine submarine = new Submarine();
                    ships.add(submarine);
                    for (int i = 0; i < pos.length; i ++) {
                        board[i][i] = submarine;
                    }
                    return true;
                case 4:
                    Battleship battleship = new Battleship();
                    ships.add(battleship);
                    for (int i = 0; i < pos.length; i++) {
                        board[i][i] = battleship;
                    }
                    return true;
                case 5:
                    Carrier carrier = new Carrier();
                    ships.add(carrier);
                    for (int i = 0; i < pos.length; i++) {
                        board[i][i] = carrier;
                    }
                    return true;
            }
        }
        return false;
    }

    // returns true if a part of a ship is hit and false if a DestroyedPart is hit or water is hit
    public int ShipHit(int [] pos) {
        if (board[pos[0]][pos[1]] == null) {
            return 0;
        } else if (board[pos[0]][pos[1]] instanceof DestroyedPart) {
                return 1;
        } else {
            board[pos[0]][pos[1]].TakeDamage();
            if (board[pos[0]][pos[1]].isDestroyed()) {
                ships.remove(board[pos[0]][pos[1]]);
                destroyedShips.add(board[pos[0]][pos[1]]);
            }
            board[pos[0]][pos[1]] = new DestroyedPart();
            return 2;
        }
    }
}
