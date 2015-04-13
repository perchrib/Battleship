package no.gruppe10a.battleship;

/**
 * Created by Zlash on 08/04/15.
 */
public class BattleshipFactory extends ShipFactory{


    @Override
    protected Ship createShip(String name) {
        Battleship newShip = new BattleShip(name);

        return newShip;
    }
}
