package no.gruppe10a.battleship;


import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Eirik on 09/04/15.
 */
public class GameBoard {
    /** The actual board the ships (specifically the ship parts) are placed on */
    private ShipPart [][] board;

    /** The board visible to the enemy player */
    private Ship [][] attackedBoard;

    /** The ships on this board (not removed from list when destroyed) */
    private ArrayList<Ship> ships;

    /** The ships that have been destroyed on this board. Added to list when destroyed */
    private ArrayList<Ship> destroyedShips;

    /** Ship factories */
    private static DestroyerFactory destroyerFactory;
    private static SubmarineFactory submarineFactory;
    private static BattleshipFactory battleshipFactory;
    private static CarrierFactory carrierFactory;


    public GameBoard(FactoryProducer factoryProducer) {
        board = new ShipPart [Constants.ROW_SIZE][Constants.COLUMN_SIZE];
        ships = new ArrayList<Ship>();
        //destroyedShips = new ArrayList<Ship>();

        this.destroyerFactory = (DestroyerFactory) factoryProducer.createFactory("DESTROYER");
        this.submarineFactory = (SubmarineFactory) factoryProducer.createFactory("SUBMARINE");
        this.battleshipFactory = (BattleshipFactory) factoryProducer.createFactory("BATTLESHIP");
        this.carrierFactory = (CarrierFactory) factoryProducer.createFactory("CARRIER");

        this.initializeBoard();

    }

 /*   // player adds ships to board and cant add more than the limit
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
    }*/

/*    // returns true if a part of a ship is hit and false if a DestroyedPart is hit or water is hit
    public int ShipHit(int [] pos) {
        if (board[pos[0]][pos[1]] == null) {
            return 0;
        } else if (board[pos[0]][pos[1]] instanceof ShipPart) {
                return 1;
        } else {
            board[pos[0]][pos[1]].takeDamage();
            if (board[pos[0]][pos[1]].isDestroyed()) {
                ships.remove(board[pos[0]][pos[1]]);
                destroyedShips.add(board[pos[0]][pos[1]]);
            }
            board[pos[0]][pos[1]] = new ShipPart();
            return 2;
        }
    }*/

    /**
     * Checks if the player owning this board has lost by checking if all ships are destroyed
     * @return  true if player has lost and false otherwise
     */
    public boolean hasLost() {
        return ships.isEmpty();
    }

    /**
     * Initializes and adds ships to the board's list of ships
     */
    private void initializeShips() {
        int nofDestroyers = Constants.NUMBER_OF_DESTROYERS;
        int nofSubmarines = Constants.NUMBER_OF_SUBMARINES;
        int nofBattleships = Constants.NUMBER_OF_BATTLESHIPS;
        int nofCarriers = Constants.NUMBER_OF_CARRIERS;

        int shipCounter = 1;
        for(int i = 0; i < nofDestroyers; i++) {
            this.ships.add(this.destroyerFactory.orderShip(shipCounter));
            shipCounter++;
        }

        /*for(int i = 0; i < nofSubmarines; i++) {
            this.ships.add(this.submarineFactory.orderShip(shipCounter));
            shipCounter++;
        }

        for(int i = 0; i < nofBattleships; i++) {
            this.ships.add(this.battleshipFactory.orderShip(shipCounter));
            shipCounter++;
        }

        for(int i = 0; i < nofCarriers; i++) {
            this.ships.add(this.carrierFactory.orderShip(shipCounter));
            shipCounter++;
        }*/

    }


    /**
     * Initializes board and adds ships to it
     */
    private void initializeBoard() {
        for (int i = 0; i < board.length; i ++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = null;
            }
        }
        this.initializeShips();
        this.placeShipsFixed();
    }

    /**
     * Places the ships randomly on the board
     * INCOMPLETE
     */
    private void placeShipsRandom() {
        Random r = new Random();
        int min = 0; //Lowest index possible for row and column
        boolean horizontal = true;

        for(Ship s : this.ships) {
            int randRow = -1;
            int randCol = -1;

            while(!checkIfValidShipPlacement(s, randRow, randCol, horizontal)) {
                randRow = r.nextInt(Constants.ROW_SIZE - min) + min;
                randCol = r.nextInt(Constants.COLUMN_SIZE - min) + min;
            }

            for(ShipPart.)


        }
    }


    private void placeShipsFixed() {
        this.board[0][0] = this.ships.get(0);
        this.board[9][9] = this.ships.get(1);
    }


    /**
     * FOR DEBUGGING
     * For printing in console to test and debug before integrating with graphics
     */
    public void printBoard() {
        for(int row = 0; row < this.board.length; row++) {
            for(int col = 0; col < this.board[row].length; col++) {
                if(this.board[row][col] == null) {
                    System.out.print(0 + " ");
                }
                else {
                    System.out.print("S ");
                }
            }
            System.out.println();
        }
        System.out.println();

        /*//Print ships in ships list
        for(Ship s : this.ships) {
            System.out.println(s);
        }*/
    }


    /**
     * Attacks a square on this board. Marks the attacked board
     * @param x row number in board array
     * @param y column number in board array
     * @return  true if ship is hit and false otherwise
     */
    public boolean attackSquare(int x, int y) {
        return true;
    }


    /**
     * Checks if the ship can be placed starting in the square specified by the coordinates
     * @param s             The ship to be placed
     * @param row         The x-coordinate of one end of the ship
     * @param col         The y-coordinate of one end of the ship
     * @param horizontal    True if ship is to be placed horizontally
     * @return              True if valid placement, and false otherwise
     */
    private boolean checkIfValidShipPlacement(Ship s, int row, int col, boolean horizontal) {
        if (row == -1 || col == -1) {
            return false;
        }

        //List of potential placement coordinates for the ship parts
        ArrayList<int[]> shipPartCoords = new ArrayList<int[]>();

        //Individual ship part coordinates
        int [] coords = new int[2];

        if (horizontal == true) {
            for(int i = 0; i < s.getShipParts().size(); i++) {
                coords[0] = row;
                coords[1] = col+i;
                shipPartCoords.add(coords);
            }
        }

        else if (horizontal == false) {
            for(int i = 0; i < s.getShipParts().size(); i++) {
                coords[0] = row+i;
                coords[1] = col;
                shipPartCoords.add(coords);
            }
        }

        for(int [] c : shipPartCoords) {
            int cRow = c[0];
            int cCol = c[1];

            if(cRow < 0 || cRow > Constants.ROW_SIZE-1 ||
                    cCol < 0 || cCol > Constants.COLUMN_SIZE-1 ||
                    this.board[cRow][cCol] == null ||
                    this.board[cRow][cCol] instanceof ShipPart) {
                return false;
            }

        }

        return true;
    }




}
