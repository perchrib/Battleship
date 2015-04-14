package no.gruppe10a.battleship;

/**
 * Created by Zlash on 13/04/15.
 */
public class Main {


    public static void main(String [] args) {

        FactoryProducer factoryProducer = new FactoryProducer();

        Player player1 = new Player("Player 1", new GameBoard(factoryProducer));
        Player player2 = new Player("Player 2", new GameBoard(factoryProducer));


    }

}
