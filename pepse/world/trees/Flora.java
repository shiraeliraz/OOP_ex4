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
    private static final float HALF_FACTOR = 2f;
    private static final int MAX_FRUIT = 10;


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
                        new Vector2(x - barkDimensions.x() / HALF_FACTOR, terrainHeight - barkDimensions.y());
                bark.setTopLeftCorner(treeTopLeft);

                gameObjects.addGameObject(bark, Layer.STATIC_OBJECTS);

                // Leaves
                GameObject[][] leaves = Tree.createTreeTop(treeTopLeft);
                for (GameObject[] row : leaves) {
                    for (GameObject leaf : row) {
                        if (leaf != null) {
                            gameObjects.addGameObject(leaf, Layer.DEFAULT);
                        }
                    }
                }
                //Fruit
                int fruitCount = rand.nextInt(MAX_FRUIT);
                float leafWidth = Leaf.GetLeafDimensions().x();
                float leafHeight = Leaf.GetLeafDimensions().y();
                int leafCols = leaves[0].length;
                int leafRows = leaves.length;
                float canopyTopLeftX = treeTopLeft.x() - (Leaf.GetLeafDimensions().x() * (leafCols / HALF_FACTOR));
                float canopyTopLeftY = treeTopLeft.y() - (Leaf.GetLeafDimensions().y() * (leafRows / HALF_FACTOR));

                for (int i = 0; i < fruitCount; i++) {
                    float fruitX = canopyTopLeftX + rand.nextFloat() * (leafCols * leafWidth);
                    float fruitY = canopyTopLeftY + rand.nextFloat() * (leafRows * leafHeight);
                    Vector2 fruitTopLeft = new Vector2(fruitX, fruitY);
                    Fruit fruit = new Fruit(fruitTopLeft,Vector2.ZERO, null);
                    gameObjects.addGameObject(fruit, Layer.FOREGROUND);
                }
            }
        }
    }
}
