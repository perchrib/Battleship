package no.gruppe10a.battleship;

/**
 * Created by Zlash on 07/04/15.
 */
public abstract class ShipFactory {

    //Creates a ship
    protected abstract Ship createShip(String shipType);

    public Ship orderShip(String shipType) {
        Ship newShip = createShip(shipType);

        return Ship;
    }

}
