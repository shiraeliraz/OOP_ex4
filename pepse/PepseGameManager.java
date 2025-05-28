import danogl.GameManager;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import world.Block;
import world.Sky;
import world.Terrain;

import java.util.List;

public class PepseGameManager extends GameManager {

	@Override
	public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
		super.initializeGame(imageReader, soundReader, inputListener, windowController);
		this.gameObjects().addGameObject(Sky.create(windowController.getWindowDimensions()));
		Terrain terrain = new Terrain(windowController.getWindowDimensions(), 0);
		List<Block> blocks = terrain.createInRange(-50, 120);
		for (Block block : blocks) {
			this.gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
		}
	}


	public static void main (String[] args) {
		PepseGameManager gameManager = new PepseGameManager();
		gameManager.run();
	}
}
