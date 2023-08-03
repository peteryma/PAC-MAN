
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.Color;

/**
 * An abstract ghost object in the game that extends GameObjPacman2.
 * 
 * Each ghost has a different color and different movement patterns, whose
 * method is abstract. They have a normal dangerous form and an edible blue
 * form.
 */
public abstract class Ghosts extends GameObjPacman {
	private boolean edible;
	private int spawnTimer;
	public final int SPAWN_TIME;

	/**
	 * Ghost constructor
	 */
	public Ghosts(int px, int py, int width, int height, Color color, boolean edible, 
			int spawnTime) {
		super(px, py, width, height, color);
		this.edible = edible;
		this.spawnTimer = 0;
		this.SPAWN_TIME = spawnTime;
	}

	/***
	 * GETTERS
	 **********************************************************************************/
	public boolean getEdible() {
		return this.edible;
	}

	public int getSpawnTimer() {
		return spawnTimer;
	}

	/***
	 * SETTERS
	 **********************************************************************************/
	public void setEdible(boolean edible) {
		this.edible = edible;
	}

	public void getSpawnTimer(int spawnTimer) {
		this.spawnTimer = spawnTimer;
	}

	/***
	 * UPDATES AND OTHER METHODS
	 ****************************************************************/
	// abstract method that returns direction of specific ghost movement
	public abstract Direction movementPattern(GameObjPacman gop);

	// make the ghost killable
	public void makeKillable() {
		this.setEdible(true);
	}

	// make the ghost unkillable
	public void makeUnKillable() {
		this.setEdible(false);
	}

	// spawn the ghost in the starting position
	public void spawn() {
		this.setPx(19);
		this.setPy(15);
	}

	// Kill the ghost and reset its state
	public void respawn() {
		this.setPx(19);
		this.setPy(19);
		this.setEdible(false);
		this.spawnTimer = 0;
	}

	// increment spawn timer
	public void incSpawnTimer() {
		this.spawnTimer++;
	}

}