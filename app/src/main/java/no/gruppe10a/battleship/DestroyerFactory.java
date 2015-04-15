package no.gruppe10a.battleship;

/**
 * Created by Zlash on 13/04/15.
 */
public class DestroyerFactory extends ShipFactory {


    public DestroyerFactory(int shipSize) {
        super(shipSize);
    }
    @Override
    protected Ship createShip(int id, int size) {
        Destroyer newShip = new Destroyer(id, size);

        return newShip;
    }
}
