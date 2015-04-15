package no.gruppe10a.battleship;

/**
 * Created by Eirik on 08/04/15.
 */
public class Carrier extends Ship {

    public Carrier(int id, int size) {
        super(id, size);
        super.setName("Carrier");
        super.setSize(size);
        //super.setImg(null);
        super.setHp(size);
    }
}
