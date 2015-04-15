package no.gruppe10a.battleship;


/**
 * Created by Zlash on 08/04/15.
 */
public class SubmarineFactory extends ShipFactory {

    public SubmarineFactory(int shipSize) {
        super(shipSize);
    }


    @Override
    protected Ship createShip(int id, int size) {
        Submarine newShip = new Submarine(id, size);

        return newShip;
    }
}
