package no.gruppe10a.battleship;

/**
 * Created by Zlash on 07/04/15.
 */
public abstract class ShipFactory {

    //Creates a ship
    protected abstract Ship createShip(String shipType, String shipName);

    public Ship orderShip(String shipType, String shipName) {
        Ship newShip = createShip(shipType, shipName);

        return Ship;
    }

}
