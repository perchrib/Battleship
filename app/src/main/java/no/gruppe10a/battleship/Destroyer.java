package no.gruppe10a.battleship;

//import android.media.Image;

/**
 * Created by Eirik on 07/04/15.
 */
public class Destroyer extends Ship {

    public Destroyer(int id, int size) {
        super(id, size);
        super.setName("Destroyer");
        super.setSize(size);
        //super.setImg(null);
        super.setHp(size);
    }


}
