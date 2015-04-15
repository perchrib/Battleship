package no.gruppe10a.battleship;

/**
 * Created by Eirik on 09/04/15.
 */
public class ShipPart {

    /** Id number of part to seperate parts belonging to same ship */
    private int partId;

    /** The ship this part belongs to*/
    private Ship ownerShip;

    public ShipPart(int id, Ship ownerShip) {
        this.partId = id;
        this.ownerShip = ownerShip;
    }


}
