package no.gruppe10a.battleship;

/**
 * Created by Zlash on 08/04/15.
 */
public class BattleshipFactory extends ShipFactory{


    public BattleshipFactory(int shipSize) {
        super(shipSize);
    }
    @Override
    protected Ship createShip(int id, int size) {
        Battleship newShip = new Battleship(id, size);

        return newShip;
    }
}
