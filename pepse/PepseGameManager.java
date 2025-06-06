import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.collisions.LayerManager;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import world.*;
import world.daynight.Night;
import world.daynight.Sun;
import world.daynight.SunHalo;
import world.trees.Flora;
import world.trees.Tree;

import javax.swing.tree.TreeCellRenderer;
import java.util.List;

public class PepseGameManager extends GameManager {
    private static final int CYCLE_LENGTH = 30;
    private static final int SEED = 45678;
    private Terrain terrain;
    private Avatar avatar;

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        // Enable collision detection between layers
        gameObjects().layers().shouldLayersCollide(Layer.STATIC_OBJECTS, Layer.DEFAULT, true);

        // add sky
        this.gameObjects().addGameObject(Sky.create(windowController.getWindowDimensions()), Layer.BACKGROUND);

        // create blocks and terrain first
        createTerrainAndBlocks(windowController);

        // create night
        gameObjects().addGameObject(Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH), Layer.BACKGROUND);

        //create sun
        GameObject sun = Sun.create(windowController.getWindowDimensions(), CYCLE_LENGTH);

        // create sun halo
        gameObjects().addGameObject(SunHalo.create(sun), Layer.BACKGROUND);

        // add sun to the game
        gameObjects().addGameObject(sun, Layer.BACKGROUND);

        // add avatar after terrain is created
        createAvatar(imageReader, inputListener, windowController);

        // add energy UI
        GameObject energyUI = EnergyUI.create(avatar);
        gameObjects().addGameObject(energyUI, Layer.UI);

        // create cloud
        addCloudToGame(windowController);
        // Add trees to game
        addTrees(windowController);

    }
    private void assignLayerCollision(){
        LayerManager layerManager = gameObjects().layers();
        layerManager.shouldLayersCollide(Layer.STATIC_OBJECTS, Layer.FOREGROUND, false);
        layerManager.shouldLayersCollide(Layer.STATIC_OBJECTS, Layer.DEFAULT, true);
    }

    private void addTrees(WindowController windowController) {
        Flora flora = new Flora(gameObjects(), terrain);
        flora.createInRange(0, (int) windowController.getWindowDimensions().x());
    }

    private void addCloudToGame(WindowController windowController) {
        List<Block> cloud = Cloud.createCloud(windowController.getWindowDimensions());
        for (Block block : cloud) {
            gameObjects().addGameObject(block, Layer.BACKGROUND);
        }
    }

    private void createAvatar(ImageReader imageReader, UserInputListener inputListener, WindowController windowController) {
        float xVal = windowController.getWindowDimensions().x() / 2;
        float yVal = terrain.groundHeightAt(xVal) - 55; // Position above ground (avatar height + small buffer)

        avatar = new Avatar(new Vector2(xVal - 25, yVal), inputListener, imageReader); // Center the avatar

        gameObjects().addGameObject(avatar, Layer.DEFAULT);
    }

    private void createTerrainAndBlocks(WindowController windowController) {
        terrain = new Terrain(windowController.getWindowDimensions(), SEED);
        List<Block> blocks = terrain.createInRange(0, (int) windowController.getWindowDimensions().x());
        for (Block block : blocks) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
    }

    public static void main(String[] args) {
        PepseGameManager gameManager = new PepseGameManager();
        gameManager.run();
    }
}