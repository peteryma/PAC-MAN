
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.Color;
import java.awt.Graphics;

/**
 * An orange ghost that follows a random path.
 */
public class GhostOrange extends Ghosts {
	public static final int SIZE = 30;
	public static final int INIT_POS_X = 19;
	public static final int INIT_POS_Y = 19;
	public static final Color COLOR = Color.ORANGE;
	private int tracker;
	private Direction direction;
	public static final int SPAWN_TIME = 30;

	// Orange ghost constructor
	public GhostOrange() {
		super(INIT_POS_X, INIT_POS_Y, SIZE, SIZE, COLOR, false, SPAWN_TIME);
		tracker = 0;
	}
	
	// Return new random direction every 3 directions called
	public Direction movementPattern(GameObjPacman gop) {
		if (tracker == 3) {
			tracker = 0;
			double random = Math.random();
			if (random < 0.25) {
				direction = Direction.LEFT;
				return direction;
			} else if (random < 0.5) {
				direction = Direction.RIGHT;
				return direction;
			} else if (random < 0.75) {
				direction = Direction.UP;
				return direction;
			} else {
				direction = Direction.DOWN;
				return direction;
			}
		} else {
			tracker++;
			return direction;
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
