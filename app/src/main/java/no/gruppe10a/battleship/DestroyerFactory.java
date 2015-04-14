package no.gruppe10a.battleship;

/**
 * Created by Zlash on 13/04/15.
 */
public class DestroyerFactory extends ShipFactory {

    @Override
    protected Ship createShip(int id) {
        Destroyer newShip = new Destroyer(id);

        return newShip;
    }
}
