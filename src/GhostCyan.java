
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.Color;
import java.awt.Graphics;

/**
 * A cyan ghost that follows a set path circling around the court.
 */
public class GhostCyan extends Ghosts {
	public static final int SIZE = 30;
	public static final int INIT_POS_X = 19;
	public static final int INIT_POS_Y = 19;
	public static final Color COLOR = Color.CYAN;
	public static final int SPAWN_TIME = 10;
	private int tracker;

	// Cyan ghost constructor
	public GhostCyan() {
		super(INIT_POS_X, INIT_POS_Y, SIZE, SIZE, COLOR, false, SPAWN_TIME);
		tracker = 0;
	}

	// Return directions for set path
	public Direction movementPattern(GameObjPacman gop) {
		if (this.getPx() == 19 && this.getPy() == 15) {
			tracker = 0;
		}

		tracker++;

		if (tracker <= 6) {
			return Direction.LEFT;
		} else if (tracker <= 11) {
			return Direction.DOWN;
		} else if (tracker <= 15) {
			return Direction.LEFT;
		} else if (tracker <= 28) {
			return Direction.UP;
		} else if (tracker <= 48) {
			return Direction.RIGHT;
		} else if (tracker <= 74) {
			return Direction.DOWN;
		} else if (tracker <= 94) {
			return Direction.LEFT;
		} else if (tracker <= 106) {
			return Direction.UP;
		} else {
			tracker -= 92;
			return Direction.UP;
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