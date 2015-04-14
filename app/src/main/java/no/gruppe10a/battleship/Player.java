package no.gruppe10a.battleship;

/**
 * Created by Zlash on 13/04/15.
 */
public class Player {
    private String name;
    private GameBoard gameBoard;

    public Player(String name, GameBoard gameBoard) {
        this.name = name;
        this.gameBoard = gameBoard;
    }

    public String getName() {
        return name;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setName(String name) {
        this.name = name;
    }


}
