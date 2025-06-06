package world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import util.ColorSupplier;

import java.awt.*;
import java.util.Random;

public class Leaf{
    private static Color baseLeafColor = new Color(60,90,64);
    private static final int greenRange = 30;
    private static Random rand = new Random();
    private static final Vector2 leafDimensions = new Vector2(30,30);

    public static GameObject createLeaf(Vector2 topLeftCorner) {
        int greenValue = 90+rand.nextInt(greenRange);
        Color randomGreen = new Color(60, greenValue,64);
        RectangleRenderable leafRenderable = new RectangleRenderable(ColorSupplier.approximateColor(randomGreen));
        GameObject leaf = new GameObject(topLeftCorner, leafDimensions, leafRenderable);
        leaf.setTag("leaf");
        return leaf;
    }
    public static Vector2 GetLeafDimensions(){
        return leafDimensions;
    }



}
