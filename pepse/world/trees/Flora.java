package world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Vector2;
import world.Block;
import world.Terrain;

import java.util.Random;

public class Flora {
    private static final float TREE_SPAWN_PROBABILITY = 0.05f;
    private final Terrain terrain;
    private final GameObjectCollection gameObjects;
    private final Random rand = new Random();

    public Flora(GameObjectCollection gameObjects, Terrain terrain) {
        this.terrain = terrain;
        this.gameObjects = gameObjects;
    }

    public void createInRange(int minX, int maxX) {
        int blockSize = Block.SIZE;
        for (int x = minX; x < maxX; x += blockSize) {
            if (rand.nextFloat() < TREE_SPAWN_PROBABILITY) {
                float terrainHeight = terrain.groundHeightAt(x);

                // Bark
                GameObject bark = Tree.createBark(Vector2.ZERO);
                Vector2 barkDimensions = bark.getDimensions();
                Vector2 treeTopLeft =
                        new Vector2(x - barkDimensions.x() / 2f, terrainHeight - barkDimensions.y());
                bark.setTopLeftCorner(treeTopLeft);

                gameObjects.addGameObject(bark, Layer.STATIC_OBJECTS);

                // Leaves
                GameObject[][] leaves = Tree.createTreeTop(treeTopLeft);
                for (GameObject[] row : leaves) {
                    for (GameObject leaf : row) {
                        if (leaf != null) {
                            gameObjects.addGameObject(leaf, Layer.FOREGROUND);
                        }
                    }
                }
            }
        }
    }
}
