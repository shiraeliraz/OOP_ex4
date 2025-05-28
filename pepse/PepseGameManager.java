import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import world.Block;
import world.Sky;
import world.Terrain;
import world.daynight.Night;

import java.util.List;

public class PepseGameManager extends GameManager {

	@Override
	public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
		super.initializeGame(imageReader, soundReader, inputListener, windowController);
		// add sky
		this.gameObjects().addGameObject(Sky.create(windowController.getWindowDimensions()), Layer.BACKGROUND);
		// create blocks
		Terrain terrain = new Terrain(windowController.getWindowDimensions(), 1);
		List<Block> blocks = terrain.createInRange(0, (int)windowController.getWindowDimensions().x());
		for (Block block : blocks) {
			this.gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
		}
		// create night
		gameObjects().addGameObject(Night.create(windowController.getWindowDimensions(), 30), Layer.BACKGROUND);
	}





	public static void main (String[] args) {
		PepseGameManager gameManager = new PepseGameManager();
		gameManager.run();
	}
}
