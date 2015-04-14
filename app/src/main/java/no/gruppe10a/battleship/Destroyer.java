package no.gruppe10a.battleship;

import android.media.Image;

/**
 * Created by Eirik on 07/04/15.
 */
public class Destroyer extends Ship {

    public Destroyer(int id) {
        super(id);
        super.setName("Destroyer");
        super.setSize(Constants.SIZE_OF_DESTROYER);
        super.setImg(null);
        super.setHp(2);
    }


}
