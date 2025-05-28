import danogl.GameManager;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import world.Sky;

public class PepseGameManager extends GameManager {

	@Override
	public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
		super.initializeGame(imageReader, soundReader, inputListener, windowController);
		this.gameObjects().addGameObject(Sky.create(windowController.getWindowDimensions()));
	}


	public static void main (String[] args) {
		PepseGameManager gameManager = new PepseGameManager();
		gameManager.run();
	}
}
