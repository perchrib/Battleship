package no.gruppe10a.battleship;

/**
 * Created by Zlash on 08/04/15.
 */
public class CarrierFactory extends ShipFactory {

    public CarrierFactory(int shipSize) {
        super(shipSize);
    }

    @Override
    protected Ship createShip(int id, int size) {
        Carrier newShip = new Carrier(id, size);

        return newShip;
    }

}
