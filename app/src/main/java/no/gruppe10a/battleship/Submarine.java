package no.gruppe10a.battleship;


/**
 * Created by Eirik on 08/04/15.
 */
public class Submarine extends Ship {

    public Submarine(int id, int size) {
        super(id, size);
        super.setName("Submarine");
        super.setSize(size);
        //super.setImg(null);
        super.setHp(size);
    }
}
