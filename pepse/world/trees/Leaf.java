package world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import util.ColorSupplier;

import java.awt.*;
import java.util.Random;

public class Leaf{
    private static final int greenRange = 30;
    private static final int ORIGINAL_GREEN_COLOR = 90;
    private static final int R_AND_B_VALUE = 60;
    private static Random rand = new Random();
    private static final Vector2 MAX_LEAF_DIMENSION = new Vector2(30,30);
    private static final Vector2 MIN_LEAF_DIMENSION = new Vector2(25,30);
    private static final float WIND_TRANSITION_TIME = 3f;
    private static final float SIZE_TRANSITION_TIME = 4f;
    private static final float RANDOM_RANGE_FACTOR = 4f;
    private static final float LEFTMOST_ANGLE = -10;
    private static final float RIGHT_MOST_ANGLE = 20;
    private static final String LEAF_TAG = "leaf";


    public static GameObject createLeaf(Vector2 topLeftCorner) {
        int greenValue = ORIGINAL_GREEN_COLOR+rand.nextInt(greenRange);
        Color randomGreen = new Color(R_AND_B_VALUE, greenValue,R_AND_B_VALUE);
        RectangleRenderable leafRenderable = new RectangleRenderable(ColorSupplier.approximateColor(randomGreen));
        GameObject leaf = new GameObject(topLeftCorner, MAX_LEAF_DIMENSION, leafRenderable);
        leaf.setTag(LEAF_TAG);
        new ScheduledTask(leaf, rand.nextFloat() * RANDOM_RANGE_FACTOR, false, () -> windyTransition(leaf));
        new ScheduledTask(leaf, rand.nextFloat() * RANDOM_RANGE_FACTOR, false, () -> leafSizeTransition(leaf));

        windyTransition(leaf);
        leafSizeTransition(leaf);
        return leaf;
    }

    private static void leafSizeTransition(GameObject leaf) {
        Transition<Vector2> transition = new Transition<Vector2>(leaf,
                leaf::setDimensions,
                MIN_LEAF_DIMENSION,
                MAX_LEAF_DIMENSION,
                Transition.CUBIC_INTERPOLATOR_VECTOR,
                SIZE_TRANSITION_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
                );
    }

    private static void windyTransition(GameObject leaf) {
        Transition<Float> transition = new Transition<Float>(leaf,
                leaf.renderer()::setRenderableAngle,
                LEFTMOST_ANGLE,
                RIGHT_MOST_ANGLE,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                WIND_TRANSITION_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }

    public static Vector2 GetLeafDimensions(){
        return MAX_LEAF_DIMENSION;
    }
}
