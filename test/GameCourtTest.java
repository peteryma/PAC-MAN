import static org.junit.Assert.*;
import java.awt.Color;
import javax.swing.JLabel;
import org.junit.Test;

/** 
 * Tests for GameCourtPacman
 */

public class GameCourtTest {

    @Test
    public void testCollisions() {
        final JLabel status = new JLabel("test");
    	GameCourtPacman2 gc = new GameCourtPacman2(status);
    	
    	// Set up simple 3x3 board with walls
        Wall2[][] walls = new Wall2[4][4];
        for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (i != 1 && j != 1 || i != 1 && j != 2 ||
						i != 2 && j != 1 || i != 2 && j != 2) {
					walls[i][j] = new Wall2(i, j, Color.BLUE);
				}
			}
        }
        gc.addWallsTest(walls);
        
        // Initialize pacman at center
        Pacman2 pacman = new Pacman2(Color.YELLOW);
        pacman.setPx(1);
        pacman.setPy(1);
        gc.addPacmanTest(pacman);
        
        // Collisions should be present on all sides
        assertTrue(gc.collideUpWall(pacman));
        assertTrue(gc.collideDownWall(pacman));
        assertTrue(gc.collideLeftWall(pacman));
        assertTrue(gc.collideRightWall(pacman));
    }
    
    @Test
    public void testNoCollisions() {
        final JLabel status = new JLabel("test");
    	GameCourtPacman2 gc = new GameCourtPacman2(status);
    	
    	// Set up simple 3x3 board with no walls
        gc.addWallsTest(new Wall2[4][4]);
        
        // Initialize pacman at center
        Pacman2 pacman = new Pacman2(Color.YELLOW);
        pacman.setPx(1);
        pacman.setPy(1);
        gc.addPacmanTest(pacman);
        
        // Collisions should not be present on all sides
        assertFalse(gc.collideUpWall(pacman));
        assertFalse(gc.collideDownWall(pacman));
        assertFalse(gc.collideLeftWall(pacman));
        assertFalse(gc.collideRightWall(pacman));
    }
}
