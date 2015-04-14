package no.gruppe10a.battleship;

import android.media.Image;

import java.util.ArrayList;

/**
 * Created by Eirik on 07/04/15.
 */
public abstract class Ship {
    private int id;
    private String name;
    private int size;
    private Image img;
    private int hp;
    private ArrayList<ShipPart> shipPartList;

    public Ship(int id) {


    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void takeDamage() {
        hp = hp-1;
    }

    public boolean isDestroyed() {
        if (hp <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.name + ", Id:" + this.id;
    }

}
