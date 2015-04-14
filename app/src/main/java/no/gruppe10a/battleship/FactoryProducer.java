package no.gruppe10a.battleship;

/**
 * Created by Zlash on 08/04/15.
 */
public class FactoryProducer {

    public ShipFactory createFactory(String factoryType) {
        if(factoryType.equalsIgnoreCase("DESTROYER")) {
            return new DestroyerFactory();
        }

        else if(factoryType.equalsIgnoreCase("SUBMARINE")) {
            return new SubmarineFactory();
        }

        else if (factoryType.equalsIgnoreCase("BATTLESHIP")) {
            return new BattleshipFactory();
        }

        else if (factoryType.equalsIgnoreCase("CARRIER")) {
            return new CarrierFactory();
        }

        return null;
    }
}
