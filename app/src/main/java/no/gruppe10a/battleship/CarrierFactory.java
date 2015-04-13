package no.gruppe10a.battleship;

/**
 * Created by Zlash on 08/04/15.
 */
public class CarrierFactory extends ShipFactory {

    @Override
    protected Ship createShip(String name) {
        Carrier newShip = new Carrier(name);

        return newShip;
    }

}
