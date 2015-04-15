package no.gruppe10a.battleship;

/**
 * Created by Eirik on 09/04/15.
 */
public class ShipPart {

    /** Id number of part to seperate parts belonging to same ship */
    private int partId;
    private boolean isDestroyed;

    /** The ship this part belongs to*/
    private Ship ownerShip;

    public ShipPart(int id, Ship ownerShip) {
        this.partId = id;
        this.ownerShip = ownerShip;
        this.isDestroyed = false;
    }

    public Ship getOwnerShip() {
        return ownerShip;
    }

    public void destroy() {
        this.isDestroyed = true;
    }

    private void notifyOwner() {
        this.ownerShip.takeDamage();
    }
}
