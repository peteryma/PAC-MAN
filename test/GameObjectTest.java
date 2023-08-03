import static org.junit.Assert.*;
import java.awt.Color;
import org.junit.Test;

/** 
 *  Tests for GameObjectPacman
 */

public class GameObjectTest {

    @Test
    public void testCollisions() {
    	GameObjPacman2 obj1 = new Pacman2(Color.YELLOW);
    	GameObjPacman2 obj2 = new Pacman2(Color.YELLOW);
    	
    	// Exact overlap
    	assertTrue(obj1.anyCollision(obj2));
    	
    	// All partial overlap
    	obj1.moveLeft();
    	assertTrue(obj1.anyCollision(obj2));
    	obj1.moveUp();
    	assertTrue(obj1.anyCollision(obj2));
    	obj1.moveRight();
    	assertTrue(obj1.anyCollision(obj2));
    	obj1.moveRight();
    	assertTrue(obj1.anyCollision(obj2));
    	obj1.moveDown();
    	assertTrue(obj1.anyCollision(obj2));
    	obj1.moveLeft();
    	assertTrue(obj1.anyCollision(obj2));
    	obj1.moveLeft();
    	assertTrue(obj1.anyCollision(obj2));
    }
    
    @Test
    public void testNoCollisions() {
    	GameObjPacman2 obj1 = new Pacman2(Color.YELLOW);
    	GameObjPacman2 obj2 = new Pacman2(Color.YELLOW);
    	obj2.moveLeft();
    	obj2.moveLeft();

    	assertFalse(obj1.anyCollision(obj2));
    }
    
    @Test
    public void testRedGhostMvt() {
    	GameObjPacman2 pacman = new Pacman2(Color.YELLOW);
    	Ghosts redGhost = new GhostRed();
    	
    	redGhost.setPx(20);
		Direction direction = redGhost.movementPattern(pacman);
    	assertTrue(direction == Direction.LEFT || direction == Direction.DOWN);
    	
    	redGhost.setPx(18);
    	redGhost.setPy(34);
		Direction direction2 = redGhost.movementPattern(pacman);
    	assertTrue(direction2 == Direction.RIGHT || direction2 == Direction.UP);
    }
    
    @Test
    public void testRespawn() {
    	Ghosts ghost = new GhostOrange();
    	assertEquals(19, ghost.getPx());
    	assertEquals(19, ghost.getPy());
    	
    	ghost.spawn();
    	assertEquals(19, ghost.getPx());
    	assertEquals(15, ghost.getPy());
    	
    	ghost.setPx(0);
    	ghost.setPy(0);
    	ghost.makeKillable();
    	assertTrue(ghost.getEdible());

    	ghost.respawn();
    	assertEquals(19, ghost.getPx());
    	assertEquals(19, ghost.getPy());
    	assertFalse(ghost.getEdible());
    }
    
    @Test
    public void testMakeKillable() {
    	Ghosts redGhost = new GhostRed();
    	assertEquals(false, redGhost.getEdible());
    	
    	redGhost.makeKillable();
    	assertEquals(true, redGhost.getEdible());
    	
    	redGhost.makeUnKillable();
    	assertEquals(false, redGhost.getEdible());
    }
    
    @Test
    public void testSpawnandRespawn() {
    	Ghosts redGhost = new GhostRed();
    	redGhost.moveDown();
    	redGhost.moveLeft();
    	
    	assertEquals(18, redGhost.getPx());
    	assertEquals(16, redGhost.getPy());
    	
    	redGhost.respawn();
    	assertEquals(19, redGhost.getPx());
    	assertEquals(19, redGhost.getPy());
    	
    	redGhost.spawn();
    	assertEquals(19, redGhost.getPx());
    	assertEquals(15, redGhost.getPy());
    }
    
}
