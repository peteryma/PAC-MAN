/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;

/**
 * A wall object, pacman and ghosts cannot pass.
 */
public class Wall extends GameObjPacman {
    public static final int SIZE = 15;

    /**
    * Wall constuctor
    */
    public Wall(int xPos, int yPos, Color color) {
        super(xPos, yPos, SIZE, SIZE, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.getColor());
        g.fill3DRect(this.getPx() * SIZE, this.getPy() * SIZE, this.getWidth(), this.getHeight(), 
        		true);
    } 
}