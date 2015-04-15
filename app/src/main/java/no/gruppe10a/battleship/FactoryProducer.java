package no.gruppe10a.battleship;

/**
 * Created by Zlash on 08/04/15.
 */
public class FactoryProducer {

    public ShipFactory createFactory(String factoryType) {
        if(factoryType.equalsIgnoreCase("DESTROYER")) {
            return new DestroyerFactory(Constants.SIZE_OF_DESTROYER);
        }

        else if(factoryType.equalsIgnoreCase("SUBMARINE")) {
            return new SubmarineFactory(Constants.SIZE_OF_SUBMARINE);
        }

        else if (factoryType.equalsIgnoreCase("BATTLESHIP")) {
            return new BattleshipFactory(Constants.SIZE_OF_BATTLESHIP);
        }

        else if (factoryType.equalsIgnoreCase("CARRIER")) {
            return new CarrierFactory(Constants.SIZE_OF_CARRIER);
        }

        return null;
    }
}
