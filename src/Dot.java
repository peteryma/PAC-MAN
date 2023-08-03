/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;

/**
 * A consumable dot object.
 */
public class Dot extends GameObjPacman {
    public static final int SIZE = 5;
    
    /**
    * Dot constructor
    */
    public Dot(int xPos, int yPos, Color color, int size) {
        super(xPos, yPos, size, size, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.getColor());
        g.fill3DRect(this.getPx() * SIZE * 3 + SIZE, this.getPy() * SIZE * 3 + SIZE, 
        		this.getWidth(), this.getHeight(), true);
    } 
}