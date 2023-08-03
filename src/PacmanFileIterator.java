import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.*;

/**
 * PacmanFileIterator allows reading and writing of high scores into a .txt
 * file, one line at a time, with the name on one line, and the corresponding
 * score on the following line.
 */
public class PacmanFileIterator implements Iterator<String> {
	private BufferedReader br;
	private List<String> fileLineIterator;
	private Iterator<String> iter;
	private String nextLine;
	static final String PACMAN_HIGH_SCORES = "PacmanHighScores.txt";

	/**
	 * Creates a PacmanFileIterator for the file located at filePath.
	 *
	 * If an IOException is thrown by the BufferedReader or FileReader, then set
	 * next to null.
	 *
	 * @param filePath - the path to the CSV file to be turned to an Iterator
	 * @throws IllegalArgumentException if filePath is null or if the file doesn't
	 *                                  exist
	 */
	public PacmanFileIterator() throws IllegalArgumentException {
		try {
			this.br = new BufferedReader(new FileReader(PACMAN_HIGH_SCORES));
			this.fileLineIterator = new LinkedList<String>();

			// Add read lines to list
			String brLine = this.br.readLine();
			nextLine = brLine;
			while (brLine != null) {
				this.fileLineIterator.add(brLine);
				brLine = this.br.readLine();
			}

			this.iter = fileLineIterator.iterator();

		} catch (FileNotFoundException e1) {
			throw new IllegalArgumentException();
		} catch (IOException e2) {
			nextLine = null;
		} finally {
			if (this.br != null) {
				try {
					this.br.close();
				} catch (IOException e3) {
					throw new IllegalArgumentException();
				}
			}
		}

	}

	/**
	 * Returns true if there are lines left to read in the file, and false
	 * otherwise.
	 * 
	 * If there are no more lines left, this method should close the BuffereReader.
	 *
	 * @return a boolean indicating whether the FileLineIterator can produce another
	 *         line from the file
	 */
	@Override
	public boolean hasNext() {
		boolean linesLeft = iter.hasNext();
		if (!linesLeft) {
			try {
				this.br.close();
			} catch (IOException e) {
			}
		}
		return linesLeft;
	}

	/**
	 * Returns the next line from the file, or throws a NoSuchElementException if
	 * there are no more strings left to return (i.e. hasNext() is false).
	 * 
	 * This method also advances the iterator in preparation for another invocation.
	 * If an IOException is thrown during this process, the subsequent call should
	 * return null.
	 *
	 * @return the next line in the file
	 * @throws NoSuchElementException if there is no more data in the file
	 */
	@Override
	public String next() throws NoSuchElementException {
		if (hasNext()) {
			// advance iterator
			try {
				nextLine = iter.next();
			} catch (Exception e) {
				nextLine = null;
			}
		} else {
			throw new NoSuchElementException();
		}

		return nextLine;
	}

	/**
	 * Given a List of Strings, prints those Strings to a file (one String per line
	 * in the file). This method uses BufferedWriter, the flip side to
	 * BufferedReader.
	 *
	 * You may assume none of the arguments or strings passed in will be null.
	 *
	 * If the process of writing the data triggers an IOException, you should catch
	 * it and stop writing. (You can also print an error message to the terminal,
	 * but we will not test that behavior.)
	 * 
	 * @param stringsToWrite - A List of Strings to write to the file
	 * @param filePath       - the string containing the path to the file where the
	 *                       Strings should be written
	 * @param append         - a boolean indicating whether the new Strings should be
	 *                       appended to the current file or should overwrite its
	 *                       previous contents
	 */
	public static void writeStringsToFile(String stringToWrite) {
		try {
			File file = Paths.get(PACMAN_HIGH_SCORES).toFile();
			Writer out = new FileWriter(file, true);
			BufferedWriter br = new BufferedWriter(out);

			br.write(stringToWrite);
			br.newLine();

			br.flush();
			br.close();
		} catch (Exception e) {
			return;
		}

	}

}
