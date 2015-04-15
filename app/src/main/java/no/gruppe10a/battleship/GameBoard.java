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

    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";


    public GameBoard(FactoryProducer factoryProducer) {
        board = new ShipPart [Constants.ROW_SIZE][Constants.COLUMN_SIZE];
        ships = new ArrayList<Ship>();
        //destroyedShips = new ArrayList<Ship>();

        System.out.println("1. Constructor");

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
        System.out.println("3. Init ships");
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
        System.out.println("4. Done init ships");

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
        System.out.println("2. Initialize board");
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
        System.out.println("5. Start placing random");
        Random r = new Random();
        int min = 0; //Lowest index possible for row and column
        boolean horizontal = true;

        for(Ship s : this.ships) {
            int randRow = -1;
            int randCol = -1;

            ArrayList<int[]> shipPartCoords = this.calcShipPartCoords(s, randRow, randCol, horizontal);

            while(!checkIfValidShipPlacement(shipPartCoords)) {
                randRow = r.nextInt(Constants.ROW_SIZE - min) + min;
                randCol = r.nextInt(Constants.COLUMN_SIZE - min) + min;
                shipPartCoords = this.calcShipPartCoords(s, randRow, randCol, horizontal);
            }

            //DEBUG CODE
            System.out.println("-- Produced shipCoords");

            for (int[] coord : shipPartCoords) {
                System.out.println("Row:" + coord[0] + ", Col:" + coord[1]);
            }


            //Counter for iteration through shipPartCoords
            int k = 0;
            for(ShipPart part : s.getShipParts()) {
                int[] coords = shipPartCoords.get(k);
                System.out.println("Row:" + coords[0] + ", Col:" + coords[1]);
                int rowCoord = coords[0];
                int colCoord = coords[1];

                this.board[rowCoord][colCoord] = part;
                k++;
            }

        }
        System.out.println("-- Done placing random");
    }



    /**
     * FOR DEBUGGING
     * For printing in console to test and debug before integrating with graphics
     */
    public void printBoard() {
        System.out.println("PRintsboard");
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

        /*//Print ships in ships list
        for(Ship s : this.ships) {
            System.out.println(s);
        }*/
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
     * @return      If it is a miss, hit or if it is a leathal hit destroying the ship
     */
    private HitType isShipHit(int row, int col) {

        // Return false if coordinates are out of bounds
        if(!isWithinBounds(row, col)) return HitType.MISS;

        if(this.board[row][col] instanceof ShipPart) {
            ShipPart attackedPart = this.board[row][col];
            attackedPart.destroy();
            if()
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
                //DEBUGGING
                System.out.println("TRYING TO PLACE ON OTHER SHIP");
                return false;
            }

            // Check if ship part is being placed next to another ship's ship part
            else if (
                    (cRow+1 >= 0 && cRow+1 < Constants.ROW_SIZE && this.board[cRow+1][cCol] instanceof ShipPart) ||
                    (cRow-1 >= 0 && cRow-1 < Constants.ROW_SIZE && this.board[cRow-1][cCol] instanceof ShipPart) ||
                    (cCol+1 >= 0 && cCol+1 < Constants.COLUMN_SIZE && this.board[cRow][cCol+1] instanceof ShipPart) ||
                    (cCol-1 >= 0 && cCol-1 < Constants.COLUMN_SIZE && this.board[cRow][cCol-1] instanceof ShipPart) ||
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
        System.out.println("6. Starting calculating ship part coords");

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
