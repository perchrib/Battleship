package no.gruppe10a.battleship;

//import android.media.Image;

import java.util.ArrayList;

/**
 * Created by Eirik on 07/04/15.
 */
public abstract class Ship {
    private int shipId;
    private String name;
    private int size;
   /* private Image img;*/
    private int hp;

    /** List of parts the ship consists of. Number of parts will equal to size of ship */
    private ArrayList<ShipPart> shipParts;

    public Ship(int id, int size) {
        this.shipId = id;
        this.size = size;
        this.shipParts = new ArrayList<ShipPart>();

        int shipPartCounter = 1;
        for(int i = 0; i < this.size; i++) {
            this.shipParts.add(new ShipPart(shipPartCounter, this));
            shipPartCounter++;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /*public void setImg(Image img) {
        this.img = img;
    }*/

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void takeDamage() {
        this.hp = this.hp-1;
    }

    public boolean isDestroyed() {
        if (this.hp <= 0) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return this.name + ", Id:" + this.shipId;
    }


    public ArrayList<ShipPart> getShipParts() {
        return this.shipParts;
    }

    public String getName() {
        return name;
    }

}
