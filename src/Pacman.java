/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;

/**
 * The pacman object that the user controls, initializes at specific position of the board.
 */
public class Pacman extends GameObjPacman {
    public static final int SIZE = 30;
    public static final int INIT_POS_X = 19;
    public static final int INIT_POS_Y = 33;
    
    /**
    * Pacman constructor
    */
    public Pacman(Color color) {
        super(INIT_POS_X, INIT_POS_Y, SIZE, SIZE, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.getColor());
        g.fillOval(this.getPx() * SIZE / 2, this.getPy() * SIZE / 2, this.getWidth(), 
        		this.getHeight());
    }
}