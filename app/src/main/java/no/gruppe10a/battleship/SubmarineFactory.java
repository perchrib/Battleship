package no.gruppe10a.battleship;

/**
 * Created by Zlash on 08/04/15.
 */
public class SubmarineFactory extends ShipFactory {


    @Override
    protected Ship createShip(String name) {
        Submarine newShip = new SubMarine(name);

        return newShip;
    }
}
