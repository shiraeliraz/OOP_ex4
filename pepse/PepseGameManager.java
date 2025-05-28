import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import world.Avatar;
import world.Block;
import world.Sky;
import world.Terrain;
import world.daynight.Night;
import world.daynight.Sun;
import world.daynight.SunHalo;

import java.util.List;

public class PepseGameManager extends GameManager {
	private static final int CYCLE_LENGTH = 30;
	private static final int SEED = 45678;
	private Terrain terrain;

	@Override
	public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
		super.initializeGame(imageReader, soundReader, inputListener, windowController);
		// add sky
		this.gameObjects().addGameObject(Sky.create(windowController.getWindowDimensions()), Layer.BACKGROUND);
		// create blocks
		createTerrainAndBlocks(windowController);
		// create night
		gameObjects().addGameObject(Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH), Layer.BACKGROUND);

		//create sun
		GameObject sun = Sun.create(windowController.getWindowDimensions(), CYCLE_LENGTH);

		// create sun halo
		gameObjects().addGameObject(SunHalo.create(sun), Layer.BACKGROUND);

		// add sun to the game
		gameObjects().addGameObject(sun, Layer.BACKGROUND);

		// add avatar
		createAvatar(imageReader, inputListener, windowController);

	}

	private void createAvatar(ImageReader imageReader, UserInputListener inputListener, WindowController windowController) {
		Avatar avatar = new Avatar(Vector2.ZERO, inputListener, imageReader);
		gameObjects().addGameObject(avatar);
		float xVal = windowController.getWindowDimensions().x() / 2;
		avatar.setCenter(new Vector2(xVal, terrain.groundHeightAt(xVal) - avatar.getDimensions().y() / 2));
	}

	private void createTerrainAndBlocks(WindowController windowController) {
		terrain = new Terrain(windowController.getWindowDimensions(), SEED	);
		List<Block> blocks = terrain.createInRange(0, (int) windowController.getWindowDimensions().x());
		for (Block block : blocks) {
			this.gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
		}
	}


	public static void main (String[] args) {
		PepseGameManager gameManager = new PepseGameManager();
		gameManager.run();
	}
}
