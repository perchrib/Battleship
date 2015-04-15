package no.gruppe10a.battleship;

/**
 * Created by Eirik on 08/04/15.
 */
public class Battleship extends Ship {


    public Battleship(int id, int size) {
        super(id, size);
        super.setName("Battleship");
        super.setSize(size);
        //super.setImg(null);
        super.setHp(size);
    }

}
