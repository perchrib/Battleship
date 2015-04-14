package no.gruppe10a.battleship;

import java.util.ArrayList;

/**
 * Created by Eirik on 09/04/15.
 */
public class GameBoard {
    private Ship [][] board;
    private ArrayList<Ship> ships;
    private ArrayList<Ship> destroyedShips;

    /** Ship factories */
    private static DestroyerFactory destroyerFactory;
    private static SubmarineFactory submarineFactory;
    private static BattleshipFactory battleshipFactory;
    private static CarrierFactory carrierFactory;


    public GameBoard(FactoryProducer factoryProducer) {
        board = new Ship [Constants.ROW_SIZE][Constants.COLUMN_SIZE];
        ships = new ArrayList<Ship>();
        //destroyedShips = new ArrayList<Ship>();

        this.destroyerFactory = (DestroyerFactory) factoryProducer.createFactory("DESTROYER");
        this.submarineFactory = (SubmarineFactory) factoryProducer.createFactory("SUBMARINE");
        this.battleshipFactory = (BattleshipFactory) factoryProducer.createFactory("BATTLESHIP");
        this.carrierFactory = (CarrierFactory) factoryProducer.createFactory("CARRIER");

        this.initializeShips();


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
    }

    public boolean lostGame() {
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


}
