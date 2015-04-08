package no.gruppe10a.battleship;

import android.media.Image;

/**
 * Created by Eirik on 07/04/15.
 */
public abstract class Ship {
    private String name;
    private int size;
    private Image img;

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setImg(Image img) {
        this.img = img;
    }

}
