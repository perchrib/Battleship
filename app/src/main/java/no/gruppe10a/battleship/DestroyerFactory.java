package no.gruppe10a.battleship;

/**
 * Created by Zlash on 08/04/15.
 */
public class DestroyerFactory extends ShipFactory{

    //Creates a new destroyer
    @Override
    protected Ship createShip(String name) {
        Destroyer newShip = new Destroyer(name);

        return newShip;
    }
}
