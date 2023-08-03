/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import javax.swing.*;

/**
 * Pacman Game Main class that specifies the frame and widgets of the GUI.
 */
public class Game implements Runnable {
	public void run() {
		// Top-level frame in which game components live
		final JFrame frame = new JFrame("Pac-Man");
		frame.setLocation(300, 300);

		// Create new PacmanFileIterator for reading and writing of high scores into a .txt file
		PacmanFileIterator pacmanFile = new PacmanFileIterator();

		// Instructions window
		final String MESSAGE = "The classic game of Pac-Man! Use the arrow keys to move around, "
				+ "eating all the \ndots and avoiding the colored ghosts. Careful! The red ghost "
				+ "will follow you, \nthe pink ghost will move in the direction you are moving, "
				+ "the blue ghost \nfollows a set path circling the map, and the orange ghost "
				+ "moves randomly. \nEat the green powerups to make the ghosts eatable for a "
				+ "short period of time. \nGood luck! \n\nHigh Scores: \n" 
				+ highScores(pacmanFile);
		
		JOptionPane.showMessageDialog(frame, MESSAGE, "Pac-Man Instructions", 
				JOptionPane.PLAIN_MESSAGE);

		// Username input
		String inputName = JOptionPane.showInputDialog(frame, "Please enter a username.", 
				"Username", JOptionPane.PLAIN_MESSAGE);
		if (inputName == null || inputName.isEmpty()) {
			inputName = "User" + ((int) (Math.random() * 10000));
		}

		// Status panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.SOUTH);
		final JLabel status = new JLabel("Running...");
		status_panel.add(status);

		// Main playing area
		final GameCourtPacman court = new GameCourtPacman(status);
		frame.add(court, BorderLayout.CENTER);

		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Start game
		court.reset(inputName);
	}

	// Handle display of high scores
	private String highScores(PacmanFileIterator pacmanFile) {
		try {
			Map<String, Integer> pacmanScores = new TreeMap<String, Integer>();

			while (pacmanFile.hasNext()) {
				String name = pacmanFile.next();
				int score = -1;

				try {
					score = Integer.parseInt(pacmanFile.next());
					if (score >= 0) {
						pacmanScores.put(name, score);
					} else {
						throw new NoSuchElementException();
					}
				} catch (Exception e) {
				}
			}

			return orderScores(pacmanScores);
		} catch (IllegalArgumentException e) {
			return "High scores not found";
		}

	}

	// Arrange scores in correct order highest to lowest
	private static String orderScores(Map<String, Integer> scores) {
		int topFive = 1;
		String returnString = "";

		while (scores.size() != 0 && topFive != 6) {
			String highestName = null;
			int highestScore = -1;
			for (Map.Entry<String, Integer> entry : scores.entrySet()) {
				int currentScore = entry.getValue();
				if (currentScore >= highestScore) {
					highestScore = currentScore;
					highestName = entry.getKey();
				}
			}

			scores.remove(highestName);
			returnString = returnString + topFive + ". " + highestName + ": " + highestScore + "\n";
			topFive++;
		}

		return returnString;
	}

	/**
	 * Main method run to start and run the game. Initializes the GUI elements
	 * specified in Game and runs it. IMPORTANT: Do NOT delete! You MUST include
	 * this in your final submission.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}