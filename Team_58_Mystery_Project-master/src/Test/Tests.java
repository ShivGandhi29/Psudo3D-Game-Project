package Test;

import gameworld.Board;
import gameworld.Door;
import gameworld.GameObjects;
import gameworld.HiddenDoor;
import gameworld.HiddenKey;
import gameworld.InteractableObjects;
import gameworld.Key;
import gameworld.Level;
import gameworld.MainGameWorld;
import gameworld.Player;
import gameworld.RedDoor;
import gameworld.Teleporter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import renderer.Camera;
import renderer.Screen;
import renderer.Texture;


/**
 * A class of tests to make sure the game classes are working
 * correctly and interact with certain objects and methods as
 * they should
 * @author MichaelColeman
 *
 */
public class Tests {

    @Test
	public void testGameWorld(){
		MainGameWorld world = new MainGameWorld(null);
		Board board = new Board(Level.LEVEL_ONE);
		Player player = new Player(board.getLvl().getPosX(),board.getLvl().getPosY());
		assertEquals(world.getBoard(),board);
		assertEquals(world.getPlayer(),player);
	}

	@Test
	public void cameraTestLeft(){
		Camera cam = new Camera(0, 0, 0, 0, 0, 0);
		int[][] map =
			{
					{4,4},
					{4,4}
			};
		cam.left = true;
		cam.update(map);
		Assert.assertTrue("Not equals", cam.xDir -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.xPlane -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.xPos -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.yDir -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.yPlane -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.yPos -  0.0 == 0);

	}

	@Test
	public void cameraTestRight(){
		Camera cam = new Camera(0, 0, 0, 0, 0, 0);
		int[][] map =
			{
					{4,4},
					{4,4}
			};
		cam.right = true;
		cam.update(map);
		Assert.assertTrue("Not equals", cam.xDir -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.xPlane -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.xPos -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.yDir -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.yPlane -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.yPos -  0.0 == 0);

	}

	@Test
	public void CameraTestBack(){
		Camera cam = new Camera(0, 0, 0, 0, 0, 0);
		int[][] map =
			{
					{4,4},
					{4,4}
			};
		cam.back = true;
		cam.update(map);
		Assert.assertTrue("Not equals", cam.xDir -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.xPlane -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.xPos -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.yDir -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.yPlane -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.yPos -  0.0 == 0);
	}

	@Test
	public void cameraTestFoward(){
		Camera cam = new Camera(0, 0, 0, 0, 0, 0);
		int[][] map =
			{
					{4,4},
					{4,4}
			};
		cam.forward = true;
		cam.update(map);
		Assert.assertTrue("Not equals", cam.xDir -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.xPlane -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.xPos -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.yDir -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.yPlane -  0.0 == 0);
		Assert.assertTrue("Not equals", cam.yPos -  0.0 == 0);
	}

	@Test
	public void screenTest(){
		Screen screen = new Screen(null, 0, 0, null, 0, 0);
		Camera cam = new Camera(0, 0, 0, 0, 0, 0);
		int[] map =
			{4};
		screen.update(cam, map);
		assertEquals(screen.height,0);
	}

	@Test
	public void testingMGameWorld() {
		List<Teleporter> otherTeleporters = new ArrayList<Teleporter>();

		MainGameWorld testG = new MainGameWorld(Level.LEVEL_TWO);
		List<InteractableObjects> testBag = new ArrayList<>();
		List<GameObjects> testObjects = new ArrayList<>();
		Board testBoard = new Board(Level.LEVEL_TWO);
		Player testPlayer = new Player(2, 2);
		otherTeleporters.add(new Teleporter(2,2));

		testObjects.add(new Door("testDoor",1,1));
		testObjects.add(new	Key("testKey",1,1));
		testObjects.add(new HiddenDoor("testHDoor",1,1));
		testObjects.add(new HiddenKey("testHKey",1,1));

		testBoard.setLvl(Level.LEVEL_THREE);
		testG.deepCopy(otherTeleporters.get(0));
		testG.initialize();
		testG.getTeleportList();

		testG.interact();
		testG.resetMap();
		testG.checkTeleporter();
		testG.getPlayer();
		testG.setBoard(testBoard);
		testG.setPlayer(testPlayer);
		testG = new MainGameWorld(Level.LEVEL_THREE);
		testG.initialize();
	}
	@Test
	public void testingPlayerClass() {
		List<Teleporter> otherTeleporters = new ArrayList<Teleporter>();
		List<GameObjects> testObjects = new ArrayList<GameObjects>();
		List<InteractableObjects> testBag = new ArrayList<>();
		int[][] map = new Board(Level.LEVEL_ONE).getMap();
		Player testPlayer = new Player(2.99, 2.99);
		Camera camera = new Camera(0, 0, 0, 0, 0, 0);
		Door testDoor = new Door("yh",5,5);
		testObjects.add(new Door("testDoor", 1, 1));
		testObjects.add(new Door("testDoor", 1, 1,true));
		testObjects.add(new HiddenDoor("testDoor", 1, 1));
		testObjects.add(new HiddenKey("testDoor", 1, 1));
		testObjects.add(new Key("testDoor", 1, 1));
		testObjects.add(new RedDoor("testDoor", 1, 1));
		otherTeleporters.add(new Teleporter(2,2));
		testPlayer.isOnTeleport(otherTeleporters);
		testPlayer.interactOn(testObjects, map);
		testPlayer.resetKeys();
		testPlayer.getCam();
		testPlayer.setPositionXY(1.5, 1.5);
		testPlayer.setCamera(camera);
		testPlayer.getPositionX();
		testPlayer.getPositionY();
		testDoor.isFinalDoor();

	}
	@Test
	public void testingRenderer() {
		Screen testScreen = new Screen(null, 0, 0, null, 0, 0);
		Camera cam = new Camera(0, 0, 0, 0, 0, 0);
		Texture testTex = new Texture("src/resources/wall1.png", 128);
		int[] pixels = new int[] {};
		testScreen.update(cam, pixels);
		testScreen.setWidth(2);
		testScreen.setHeight(2);
		cam.getXDir();
		cam.getXPlane();
		cam.getYDir();
		cam.getYPlane();
	}
	@Test
	public void testingMapEdit() {

	}


}