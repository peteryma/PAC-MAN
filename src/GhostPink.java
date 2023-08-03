
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.Color;
import java.awt.Graphics;

/**
 * A pink ghost that will target and move towards the direction where pacman is moving.
 */
public class GhostPink extends Ghosts {
	public static final int SIZE = 30;
	public static final int INIT_POS_X = 19;
	public static final int INIT_POS_Y = 19;
	public static final Color COLOR = Color.PINK;
	public static final int SPAWN_TIME = 20;

	// Pink ghost constructor
	public GhostPink() {
		super(INIT_POS_X, INIT_POS_Y, SIZE, SIZE, COLOR, false, SPAWN_TIME);
	}

	// Return direction that is same as pacman's direction
	public Direction movementPattern(GameObjPacman gop) {
		return gop.getDirection();
	}

	public void draw(Graphics g) {
		if (super.getEdible()) {
			g.setColor(Color.BLUE);
		} else {
			g.setColor(COLOR);
		}

		g.fillOval(this.getPx() * SIZE / 2, this.getPy() * SIZE / 2, this.getWidth(), 
				this.getHeight());
	}
}