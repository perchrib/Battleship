package no.gruppe10a.battleship;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Zlash on 13/04/15.
 */
public class BattleshipMain {


    public static void main(String [] args) {

        Scanner sc = new Scanner(System.in);

        FactoryProducer factoryProducer = new FactoryProducer();

        Player player1 = new Player("Player 1", new GameBoard(factoryProducer));
        Player player2 = new Player("Player 2", new GameBoard(factoryProducer));

        GameBoard boardP1 = player1.getGameBoard();
        GameBoard boardP2 = player2.getGameBoard();


        print("-----Player 1 initial board:----");
        boardP1.printBoard();

        print("-----Player 2 inital board:-----");
        boardP2.printBoard();


    }

    private static void print(String s) {
        System.out.println(s);
        System.out.println();
    }






}
