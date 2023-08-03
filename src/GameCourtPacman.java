
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
@SuppressWarnings("serial")
public class GameCourtPacman extends JPanel {

	// the state of the game logic
	private Pacman pacman; // the yellow pacman, keyboard control
	private GameObjPacman[][] walls; // 2D array containing all walls in game
	private Ghosts[] fourGhosts; // array of the four ghosts
	private int dotsLeft; // number of dots left
	private int score; // total score
	private boolean killModeActivated; // whether killMode is on or off
	private int killTime; // time remaining in killMode
	private int livesLeft; // number of lives remaining
	private String user; // name of the user
	private boolean playing = false; // whether the game is running
	private JLabel status; // Current status text, i.e. "Running..."

	// Game constants in pixels
	public static final int COURT_WIDTH = 600;
	public static final int COURT_HEIGHT = 630;
	public static final int COURT_DIMENSION_X = 40;
	public static final int COURT_DIMENSION_Y = 42;
	public static final int DEFAULT_KILL_TIME = 100;

	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 105;

	public GameCourtPacman(JLabel status) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.BLACK);

		// The timer is an object which triggers an action periodically with the given
		// INTERVAL. We register an ActionListener with this timer, whose
		// actionPerformed() method
		// is called each time the timer triggers. We define a helper method called
		// tick() that
		// actually does everything that should be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key events are handled by its key
		// listener.
		setFocusable(true);

		// This key listener allows the Pacman to move as long as an arrow key is
		// pressed, by changing the Pacman's velocity accordingly. (The tick method
		// below actually
		// moves the pacman.)
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT && !collideLeftWall(pacman)) {
					pacman.setDirection(Direction.LEFT);
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !collideRightWall(pacman)) {
					pacman.setDirection(Direction.RIGHT);
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN && !collideDownWall(pacman)) {
					pacman.setDirection(Direction.DOWN);
				} else if (e.getKeyCode() == KeyEvent.VK_UP && !collideUpWall(pacman)) {
					pacman.setDirection(Direction.UP);
				}
			}
		});

		this.status = status;
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset(String inputName) {
		pacman = new Pacman(Color.YELLOW);
		walls = new GameObjPacman[COURT_DIMENSION_X][COURT_DIMENSION_Y];
		fourGhosts = new Ghosts[] { new GhostRed(), new GhostPink(), new GhostCyan(), new GhostOrange() };
		dotsLeft = 780;
		score = 0;
		killModeActivated = false;
		killTime = DEFAULT_KILL_TIME;
		livesLeft = 2;
		user = inputName;

		setCourt();

		playing = true;
		status.setText("Score: 0");

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing) {
			// Move pacman
			Direction pacmandDirection = pacman.getDirection();
			if (pacmandDirection == Direction.LEFT) {
				if (pacman.getPx() == 0 && pacman.getPy() == 20) {
					pacman.setPx(38);
				} else if (!collideLeftWall(pacman)) {
					pacman.moveLeft();
				}
			}

			if (pacmandDirection == Direction.RIGHT) {
				if (pacman.getPx() == 38 && pacman.getPy() == 20) {
					pacman.setPx(0);
				} else if (!collideRightWall(pacman)) {
					pacman.moveRight();
				}
			}

			if (pacmandDirection == Direction.UP && !collideUpWall(pacman)) {
				pacman.moveUp();
			}

			if (pacmandDirection == Direction.DOWN && !collideDownWall(pacman)) {
				pacman.moveDown();
			}

			// Move ghosts
			for (Ghosts g : fourGhosts) {
				Direction ghostDirection = g.movementPattern(pacman);
				if (ghostDirection == Direction.LEFT) {
					if (g.getPx() == 0 && g.getPy() == 20) {
						g.setPx(38);
					} else if (!collideLeftWall(g)) {
						g.moveLeft();
					}
				}

				if (ghostDirection == Direction.RIGHT) {
					if (g.getPx() == 38 && g.getPy() == 20) {
						g.setPx(0);
					} else if (!collideRightWall(g)) {
						g.moveRight();
					}
				}

				if (ghostDirection == Direction.UP && !collideUpWall(g)) {
					g.moveUp();
				}

				if (ghostDirection == Direction.DOWN && !collideDownWall(g)) {
					g.moveDown();
				}

				if (g.getSpawnTimer() == g.SPAWN_TIME) {
					g.spawn();
				}

				// check for ghost eating
				if (pacman.anyCollision(g) && g.getEdible()) {
					g.respawn();
					score += 750;
				}

				// check for death conditions
				if (pacman.anyCollision(g) && !g.getEdible()) {
					if (livesLeft == 0) {
						playing = false;
					} else {
						livesLeft--;
						nextLife();
					}
				}

				// increment spawn timer
				g.incSpawnTimer();
			}

			// Check for dot eating
			eat();

			// Update score and lives left
			status.setText("Lives Left: " + livesLeft + "         " + "Score: " + score);

			// KillMode countdown
			if (killModeActivated) {
				killTime--;

				if (killTime == 0) {
					killModeActivated = false;
					killTime = DEFAULT_KILL_TIME;

					for (Ghosts g : fourGhosts) {
						g.makeUnKillable();
					}
				}
			}

			// check for the game end conditions
			if (!playing) {
				status.setText("You lose!		" + "Score: " + score);
				PacmanFileIterator.writeStringsToFile(this.user);
				PacmanFileIterator.writeStringsToFile("" + score);
			} else if (dotsLeft == 0) {
				playing = false;
				status.setText("You win!		" + "Score: " + score);
				PacmanFileIterator.writeStringsToFile(this.user);
				PacmanFileIterator.writeStringsToFile("" + score);
			}

			// update the display
			repaint();
		}
	}

	/**
	 * This helper method initializes the set of walls on the GameCourt. Each
	 * quadrant of the map is symmetrical, so we only need to define the positions
	 * of one quadrant and then mirror it across the other quadrants.
	 */
	private void setCourt() {
		for (int i = 0; i <= 19; i++) {
			for (int j = 0; j <= 20; j++) {
				if (i >= 0 && i <= 19 && j >= 0 && j <= 0) {
					mirrorWalls(i, j);
				} else if (i >= 0 && i <= 0 && j >= 0 && j <= 14) {
					mirrorWalls(i, j);
				} else if (i >= 19 && i <= 19 && j >= 0 && j <= 6) {
					mirrorWalls(i, j);
				} else if (i >= 3 && i <= 8 && j >= 3 && j <= 6) {
					mirrorWalls(i, j);
				} else if (i >= 11 && i <= 16 && j >= 3 && j <= 6) {
					mirrorWalls(i, j);
				} else if (i >= 3 && i <= 8 && j >= 9 && j <= 10) {
					mirrorWalls(i, j);
				} else if (i >= 15 && i <= 19 && j >= 9 && j <= 10) {
					mirrorWalls(i, j);
				} else if (i >= 11 && i <= 12 && j >= 9 && j <= 19) {
					mirrorWalls(i, j);
				} else if (i >= 19 && i <= 19 && j >= 9 && j <= 14) {
					mirrorWalls(i, j);
				} else if (i >= 13 && i <= 16 && j >= 13 && j <= 14) {
					mirrorWalls(i, j);
				} else if (i >= 0 && i <= 8 && j >= 13 && j <= 14) {
					mirrorWalls(i, j);
				} else if (i >= 7 && i <= 8 && j >= 13 && j <= 18) {
					mirrorWalls(i, j);
				} else if (i >= 0 && i <= 8 && j >= 18 && j <= 19) {
					mirrorWalls(i, j);
				} else if (i >= 15 && i <= 19 && j >= 17 && j <= 18) {
					mirrorWalls(i, j);
				} else if (i >= 15 && i <= 16 && j >= 17 && j <= 20) {
					mirrorWalls(i, j);
				} else if ((i == 1 || i == 2) && j == 4) {
					mirrorDots(i, j, Color.GREEN, 10);
				} else {
					if ((i > 6 || (j < 15 || j > 17)) && ((i < 17 || i > 19) || (j < 19 || j > 20))) {
						mirrorDots(i, j, Color.PINK, 5);
					}
				}
			}
		}

		walls[19][18] = new Wall(19, 18, Color.WHITE);
		walls[20][18] = new Wall(20, 18, Color.WHITE);
		walls[19][17] = new Wall(19, 17, Color.WHITE);
		walls[20][17] = new Wall(20, 17, Color.WHITE);
	}

	// Helper method for setWalls() to mirror walls across four quadrants
	private void mirrorWalls(int i, int j) {
		walls[i][j] = new Wall(i, j, Color.BLUE);
		walls[COURT_DIMENSION_X - i - 1][j] = new Wall(COURT_DIMENSION_X - i - 1, j, Color.BLUE);
		walls[i][COURT_DIMENSION_Y - j - 1] = new Wall(i, COURT_DIMENSION_Y - j - 1, Color.BLUE);
		walls[COURT_DIMENSION_X - i - 1][COURT_DIMENSION_Y - j - 1] = new Wall(COURT_DIMENSION_X - i - 1,
				COURT_DIMENSION_Y - j - 1, Color.BLUE);
	}

	// Helper method for setWalls() to mirror dots across four quadrants. Input
	// color and if it is a
	// kill dot or not
	private void mirrorDots(int i, int j, Color color, int size) {
		walls[i][j] = new Dot(i, j, color, size);
		walls[COURT_DIMENSION_X - i - 1][j] = new Dot(COURT_DIMENSION_X - i - 1, j, color, size);
		walls[i][COURT_DIMENSION_Y - j - 1] = new Dot(i, COURT_DIMENSION_Y - j - 1, color, size);
		walls[COURT_DIMENSION_X - i - 1][COURT_DIMENSION_Y - j - 1] = new Dot(COURT_DIMENSION_X - i - 1,
				COURT_DIMENSION_Y - j - 1, color, size);
	}

	// Check if block exists to left of GameObjPacman2
	boolean collideLeftWall(GameObjPacman gop) {
		if (gop.getPx() == 0 && gop.getPy() == 20) {
			return false;
		} else if (walls[gop.getPx() - 1][gop.getPy()] instanceof Wall
				|| walls[gop.getPx() - 1][gop.getPy() + 1] instanceof Wall) {
			return true;
		} else {
			return false;
		}
	}

	// Check if block exists to right of GameObjPacman2
	boolean collideRightWall(GameObjPacman gop) {
		if (gop.getPx() == 38 && gop.getPy() == 20) {
			return false;
		} else if (walls[gop.getPx() + 2][gop.getPy()] instanceof Wall
				|| walls[gop.getPx() + 2][gop.getPy() + 1] instanceof Wall) {
			return true;
		} else {
			return false;
		}
	}

	// Check if block exists down of GameObjPacman2
	boolean collideDownWall(GameObjPacman gop) {
		if (walls[gop.getPx()][gop.getPy() + 2] instanceof Wall
				|| walls[gop.getPx() + 1][gop.getPy() + 2] instanceof Wall) {
			return true;
		} else {
			return false;
		}
	}

	// Check if block exists up of GameObjPacman2
	boolean collideUpWall(GameObjPacman gop) {
		if (walls[gop.getPx()][gop.getPy() - 1] instanceof Wall
				|| walls[gop.getPx() + 1][gop.getPy() - 1] instanceof Wall) {
			return true;
		} else {
			return false;
		}
	}

	// Helper method to activate killMode
	void killMode() {
		this.killTime = DEFAULT_KILL_TIME;
		for (Ghosts g : fourGhosts) {
			g.makeKillable();
			this.killModeActivated = true;
		}
	}

	// Helper method to check if kill dot was consumed to activate killMode
	boolean consumeKillDot() {
		return ((pacman.getPx() == 1) && (pacman.getPy() == 4 || pacman.getPy() == 3))
				|| ((pacman.getPx() == 37) && (pacman.getPy() == 4 || pacman.getPy() == 3))
				|| ((pacman.getPx() == 1) && (pacman.getPy() == 37 || pacman.getPy() == 36))
				|| ((pacman.getPx() == 37) && (pacman.getPy() == 37 || pacman.getPy() == 36));
	}

	// Delete dots that pacman eats and activate killMode if killDots eaten
	void eat() {
		int x = pacman.getPx();
		int y = pacman.getPy();
		if (walls[x][y] != null) {
			walls[x][y] = null;
			dotsLeft--;
			score += 5;
			if (consumeKillDot()) {
				killMode();
			}
		}

		if (walls[x + 1][y] != null) {
			walls[x + 1][y] = null;
			dotsLeft--;
			score += 5;
			if (consumeKillDot()) {
				killMode();
			}
		}

		if (walls[x][y + 1] != null) {
			walls[x][y + 1] = null;
			dotsLeft--;
			score += 5;
			if (consumeKillDot()) {
				killMode();
			}
		}

		if (walls[x + 1][y + 1] != null) {
			walls[x + 1][y + 1] = null;
			dotsLeft--;
			score += 5;
			if (consumeKillDot()) {
				killMode();
			}
		}
	}

	// Resets pacman and ghosts, using next life
	void nextLife() {
		pacman = new Pacman(Color.YELLOW);
		fourGhosts = new Ghosts[] { new GhostRed(), new GhostPink(), new GhostCyan(), 
				new GhostOrange() };
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		pacman.draw(g);
		for (Ghosts gh : fourGhosts) {
			gh.draw(g);
		}

		for (int i = 0; i < walls.length; i++) {
			for (int j = 0; j < walls[i].length; j++) {
				GameObjPacman currentWall = walls[i][j];
				if (currentWall != null) {
					currentWall.draw(g);
				}
			}
		}
	}

	// Add walls method for testing
	void addWallsTest(Wall[][] w) {
		this.walls = w;
	}

	// Add pacman method for testing
	void addPacmanTest(Pacman p) {
		this.pacman = p;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}