package no.gruppe10a.battleship;

/**
 * Created by Zlash on 08/04/15.
 */

public abstract class ShipFactory {

    //Subclasses will define what kind of ship to be created
    protected abstract Ship createShip(String name);

    //Orders a new ship. Shiporders processed the same way for all factories
    public Ship orderShip(String name) {
        Ship newShip = this.createShip(name);

        return newShip;
    }
}
