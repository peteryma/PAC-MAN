
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.Color;
import java.awt.Graphics;

/**
 * A red ghost that will target pacman and move towards it.
 */
public class GhostRed extends Ghosts {
	public static final int SIZE = 30;
	public static final int INIT_POS_X = 19;
	public static final int INIT_POS_Y = 15;
	public static final int SPAWN_TIME = 10;
	public static final Color COLOR = Color.RED;

	// Red ghost constructor
	public GhostRed() {
		super(INIT_POS_X, INIT_POS_Y, SIZE, SIZE, COLOR, false, SPAWN_TIME);
	}

	// Calculate x and y direction to pacman and randomly pick one to move in
	public Direction movementPattern(GameObjPacman gop) {
		int xDistance = this.getPx() - gop.getPx();
		int yDistance = this.getPy() - gop.getPy();

		Direction dir1 = null;
		Direction dir2 = null;

		if (xDistance < 0) {
			dir1 = Direction.RIGHT;
		} else if (xDistance > 0) {
			dir1 = Direction.LEFT;
		}

		if (yDistance < 0) {
			dir2 = Direction.DOWN;
		} else if (yDistance > 0) {
			dir2 = Direction.UP;
		}

		if (Math.random() < 0.5) {
			return dir1;
		} else {
			return dir2;
		}

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