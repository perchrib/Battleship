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
    private ShipPart [][] attackedBoard;

    /** The ships on this board (not removed from list when destroyed) */
    private ArrayList<Ship> ships;

    /** The ships that have been destroyed on this board. Added to list when destroyed */
    private ArrayList<Ship> destroyedShips;

    /** Ship factories */
    private static DestroyerFactory destroyerFactory;
    private static SubmarineFactory submarineFactory;
    private static BattleshipFactory battleshipFactory;
    private static CarrierFactory carrierFactory;

    /** FOR DEBUGGING - Color code to easily recognize ships when printing to console*/
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";


    public GameBoard(FactoryProducer factoryProducer) {
        board = new ShipPart [Constants.ROW_SIZE][Constants.COLUMN_SIZE];
        ships = new ArrayList<Ship>();

        destroyedShips = new ArrayList<Ship>();


        this.destroyerFactory = (DestroyerFactory) factoryProducer.createFactory("DESTROYER");
        this.submarineFactory = (SubmarineFactory) factoryProducer.createFactory("SUBMARINE");
        this.battleshipFactory = (BattleshipFactory) factoryProducer.createFactory("BATTLESHIP");
        this.carrierFactory = (CarrierFactory) factoryProducer.createFactory("CARRIER");

        this.initializeBoard();

    }


    /**
     * Checks if the player owning this board has lost by checking if all ships are destroyed
     * @return  true if player has lost and false otherwise
     */
    public boolean hasLost() {
        return (this.ships.size() == this.destroyedShips.size());
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

        for(int i = 0; i < nofSubmarines; i++) {
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
        }
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
        this.placeShipsRandom();
    }

    /**
     * Places the ships randomly on the board
     * INCOMPLETE
     */
    private void placeShipsRandom() {
        Random r = new Random();
        int min = 0; //Lowest index possible for row and column
        boolean horizontal;

        for(Ship s : this.ships) {
            int randRow = -1;
            int randCol = -1;
            horizontal = r.nextBoolean();

            ArrayList<int[]> shipPartCoords = this.calcShipPartCoords(s, randRow, randCol, horizontal);

            while(!checkIfValidShipPlacement(shipPartCoords)) {
                randRow = r.nextInt(Constants.ROW_SIZE);
                randCol = r.nextInt(Constants.COLUMN_SIZE);
                shipPartCoords = this.calcShipPartCoords(s, randRow, randCol, horizontal);
                horizontal = r.nextBoolean();
            }

            //Counter for iteration through shipPartCoords
            int k = 0;
            for(ShipPart part : s.getShipParts()) {
                int[] coords = shipPartCoords.get(k);
                int rowCoord = coords[0];
                int colCoord = coords[1];

                this.board[rowCoord][colCoord] = part;
                k++;
            }

        }

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

                else if(this.board[row][col] instanceof ShipPart) {
                    if (this.board[row][col].getOwnerShip().getName().equalsIgnoreCase("Destroyer")) {
                        System.out.print(ANSI_BLUE + "D " + ANSI_RESET);
                    }

                    else if (this.board[row][col].getOwnerShip().getName().equalsIgnoreCase("Submarine")) {
                        System.out.print(ANSI_BLUE + "S " + ANSI_RESET);
                    }

                    else if (this.board[row][col].getOwnerShip().getName().equalsIgnoreCase("Battleship")) {
                        System.out.print(ANSI_BLUE + "B " + ANSI_RESET);
                    }

                    else if (this.board[row][col].getOwnerShip().getName().equalsIgnoreCase("Carrier")) {
                        System.out.print(ANSI_BLUE + "C " + ANSI_RESET);
                    }
                }
            }
            System.out.println();
        }
        System.out.println();

    }

  /*  *//**
     * Attack a square on this board
     * @param row   Attacking row number
     * @param col   Attacking col number
     *//*
    public void attackSquare(int row, int col) {
        if(isShipHit(row, col)) {
            this.attackedBoard[row][col] = this.board[row][col];
        }

    }
    */

    /**
     * Checks if a ship has been hit
     * @param row   Row number on board
     * @param col   Column number on board
     * @return      If it is a miss, hit or if it is a lethal hit destroying the ship, using enum HitType
     */
    private HitType isShipHit(int row, int col) {

        // Return false if coordinates are out of bounds
        if(!isWithinBounds(row, col)) return HitType.MISS;

        if(this.board[row][col] instanceof ShipPart) {
            ShipPart attackedPart = this.board[row][col];
            Ship attackedShip = attackedPart.getOwnerShip();
            attackedPart.destroy();
            if(attackedShip.isDestroyed()) {
                this.destroyedShips.add(attackedShip);
                return HitType.SHIP_DESTROYED;
            }
            return HitType.HIT;
        }

        return HitType.MISS;
    }

    /**
     * Checks if specified row and column numbers are out of bounds
     * @param row   Row number in board
     * @param col   Column number board
     * @return
     */
    private boolean isWithinBounds(int row, int col) {
        if (row < 0 || row > Constants.ROW_SIZE-1 || col < 0 || col > Constants.COLUMN_SIZE) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the ship can be placed starting in the square specified by the coordinates
     */
    private boolean checkIfValidShipPlacement(ArrayList<int[]> shipPartCoords) {

        for(int [] c : shipPartCoords) {
            int cRow = c[0];
            int cCol = c[1];

            // Check if ship part is being placed outside board
            if(cRow < 0 || cRow > Constants.ROW_SIZE-1 ||
                    cCol < 0 || cCol > Constants.COLUMN_SIZE-1)
                return false;

            // Check if ship part is being placed on another ship's ship part
            else if (this.board[cRow][cCol] instanceof ShipPart) {
                return false;
            }

            // Check if ship part is being placed next to another ship's ship part
            else if (
                    //Right, Left, Down, Up
                    (cRow+1 >= 0 && cRow+1 < Constants.ROW_SIZE && this.board[cRow+1][cCol] instanceof ShipPart) ||
                    (cRow-1 >= 0 && cRow-1 < Constants.ROW_SIZE && this.board[cRow-1][cCol] instanceof ShipPart) ||
                    (cCol+1 >= 0 && cCol+1 < Constants.COLUMN_SIZE && this.board[cRow][cCol+1] instanceof ShipPart) ||
                    (cCol-1 >= 0 && cCol-1 < Constants.COLUMN_SIZE && this.board[cRow][cCol-1] instanceof ShipPart) ||

                    // Diagonals
                    (cCol-1 >= 0 && cCol-1 < Constants.COLUMN_SIZE && cRow-1 >= 0 && cRow-1 < Constants.COLUMN_SIZE && this.board[cRow-1][cCol-1] instanceof ShipPart) ||
                    (cCol-1 >= 0 && cCol-1 < Constants.COLUMN_SIZE && cRow+1 >= 0 && cRow+1 < Constants.COLUMN_SIZE && this.board[cRow+1][cCol-1] instanceof ShipPart) ||
                    (cCol+1 >= 0 && cCol+1 < Constants.COLUMN_SIZE && cRow-1 >= 0 && cRow-1 < Constants.COLUMN_SIZE && this.board[cRow-1][cCol+1] instanceof ShipPart) ||
                    (cCol+1 >= 0 && cCol+1 < Constants.COLUMN_SIZE && cRow+1 >= 0 && cRow+1 < Constants.COLUMN_SIZE && this.board[cRow+1][cCol+1] instanceof ShipPart)
                    )
            {
                return false;
            }

        }
        return true;
    }

    /**
     * Calculates where ship parts will be placed, based on an initial coordinate
     * @param s             The ship owning the ship parts
     * @param row           Initial placement row of the first ship part
     * @param col           Initial placement column of the first ship part
     * @param horizontal    True if ship is to be placed horizontally
     * @return              Returns an ArrayList consisting of arrays with two coordinates for each ship part
     */
    private ArrayList<int[]> calcShipPartCoords(Ship s, int row, int col, boolean horizontal) {

        //List of potential placement coordinates for the ship parts
        ArrayList<int[]> shipPartCoords = new ArrayList<int[]>();

        //Individual ship part coordinates

        if (horizontal) {
            for(int i = 0; i < s.getShipParts().size(); i++) {
                int [] coords = new int[2];
                coords[0] = row;
                coords[1] = col+i;
                shipPartCoords.add(coords);
            }
        }

        else if (!horizontal) {
            for(int i = 0; i < s.getShipParts().size(); i++) {
                int [] coords = new int[2];
                coords[0] = row+i;
                coords[1] = col;
                shipPartCoords.add(coords);
            }
        }

        return shipPartCoords;
    }





}
