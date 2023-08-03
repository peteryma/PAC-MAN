
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.Color;
import java.awt.Graphics;

/**
 * An object in the pacman game.
 *
 * Game objects exist in the game court. They have a position, size and
 * direction.
 */
public abstract class GameObjPacman {
	// Current position of the object (in terms of 2D array position)
	private int px;
	private int py;

	// Size of object, in pixels
	private int width;
	private int height;
	
	// Color of object
	private Color color;

	// the direction an object is moving in
	private Direction direction;

	/**
	 * Constructor
	 */
	public GameObjPacman(int px, int py, int width, int height, Color color) {
		this.px = px;
		this.py = py;
		this.width = width;
		this.height = height;
		this.setColor(color);
	}

	/***
	 * GETTERS
	 **********************************************************************************/
	public int getPx() {
		return this.px;
	}

	public int getPy() {
		return this.py;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public Direction getDirection() {
		return this.direction;
	}
	
	public Color getColor() {
		return color;
	}

	/***
	 * SETTERS
	 **********************************************************************************/
	public void setPx(int px) {
		this.px = px;
	}

	public void setPy(int py) {
		this.py = py;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	/***
	 * UPDATES AND OTHER METHODS
	 ****************************************************************/

	/**
	 * Moves the object in the left direction by its velocity. Ensures that the
	 * object does not go outside its bounds by clipping.
	 */
	public void moveLeft() {
		this.px--;
	}

	/**
	 * Moves the object in the right direction by its velocity. Ensures that the
	 * object does not go outside its bounds by clipping.
	 */
	public void moveRight() {
		this.px++;
	}

	/**
	 * Moves the object in the up direction by its velocity. Ensures that the object
	 * does not go outside its bounds by clipping.
	 */
	public void moveUp() {
		this.py--;
	}

	/**
	 * Moves the object in the down direction by its velocity. Ensures that the
	 * object does not go outside its bounds by clipping.
	 */
	public void moveDown() {
		this.py++;
	}

	// Check if a 2x2 object has collided (overlaps) with another 2x2 object
	public boolean anyCollision(GameObjPacman that) {
		return this.collideExact(that) || this.collideDown(that) || this.collideLeft(that) 
				|| this.collideUp(that) || this.collideRight(that);
	}

	// Check if a 2x2 object overlaps another 2x2 object completely.
	private boolean collideExact(GameObjPacman that) {
		return this.getPx() == that.getPx() && this.getPy() == that.getPy();
	}

	// Check if a 2x2 object overlaps left of another 2x2 object.
	private boolean collideLeft(GameObjPacman that) {
		if (this.getPx() == that.getPx() + 1 && (this.getPy() == that.getPy() 
				|| this.getPy() == that.getPy() + 1 || this.getPy() + 1 == that.getPy())) {
			return true;
		}
		return false;
	}

	// Check if a 2x2 object overlaps right of another 2x2 object.
	private boolean collideRight(GameObjPacman that) {
		if (this.getPx() + 1 == that.getPx() && (this.getPy() == that.getPy() 
				|| this.getPy() == that.getPy() + 1 || this.getPy() + 1 == that.getPy())) {
			return true;
		}
		return false;
	}

	// Check if a 2x2 object overlaps up of another 2x2 object.
	private boolean collideUp(GameObjPacman that) {
		if (this.getPy() == that.getPy() + 1 && (this.getPx() == that.getPx()
				|| this.getPx() == that.getPx() + 1 || this.getPx() + 1 == that.getPx())) {
			return true;
		}
		return false;
	}

	// Check if a 2x2 object overlaps down of another 2x2 object.
	private boolean collideDown(GameObjPacman that) {
		if (this.getPy() + 1 == that.getPy() && (this.getPx() == that.getPx()
				|| this.getPx() == that.getPx() + 1 || this.getPx() + 1 == that.getPx())) {
			return true;
		}
		return false;
	}

	/**
	 * Default draw method that provides how the object should be drawn in the GUI.
	 * This method does not draw anything. Subclass should override this method
	 * based on how their object should appear.
	 * 
	 * @param g The <code>Graphics</code> context used for drawing the object.
	 *          Remember graphics contexts that we used in OCaml, it gives the
	 *          context in which the object should be drawn (a canvas, a frame,
	 *          etc.)
	 */
	public abstract void draw(Graphics g);
}