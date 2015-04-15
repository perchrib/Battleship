
package no.gruppe10a.battleship;

/**
 * Created by Zlash on 08/04/15.
 */

public abstract class ShipFactory {

    private int shipSize;

    public ShipFactory(int shipSize) {
        this.shipSize = shipSize;
    }

    //Subclasses will define what kind of ship to be created
    protected abstract Ship createShip(int id, int size);

    //Orders a new ship. Shiporders processed the same way for all factories
    public Ship orderShip(int id) {
        Ship newShip = this.createShip(id, this.shipSize);

        return newShip;
    }

    public int getShipSize() {
        return shipSize;
    }
}
