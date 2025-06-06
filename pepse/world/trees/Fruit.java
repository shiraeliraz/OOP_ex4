package world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import util.ColorSupplier;
import world.Avatar;

import java.awt.*;

public class Fruit extends GameObject {
    private static final Color FRUIT_COLOR = new Color(181,38,71);
    private static final OvalRenderable fruitRenderable =
            new OvalRenderable(ColorSupplier.approximateColor(FRUIT_COLOR));
    private static final float FRUIT_SIZE = 20f;
    private static final String FRUIT_TAG = "fruit";
    private static final float WAIT_TIME_FOR_FRUIT_REVIVAL = 30f;
    private static final int FRUIT_BOOST = 10;

    public Fruit(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        renderer().setRenderable(fruitRenderable);
        this.setDimensions(new Vector2(FRUIT_SIZE, FRUIT_SIZE));
        setTag(FRUIT_TAG);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        System.out.println("fruit collided with "+other.getTag());
        if (other.getTag().equals("avatar")){
            if (other instanceof Avatar) {
                Avatar avatar = (Avatar) other;
                avatar.addEnergy(FRUIT_BOOST);
            }
            this.setDimensions(Vector2.ZERO);
             new ScheduledTask(
                    this,
                    WAIT_TIME_FOR_FRUIT_REVIVAL,
                    false,
                    this::restoreSize
            );
        }
    }
    private void restoreSize(){
        this.setDimensions(new Vector2(FRUIT_SIZE, FRUIT_SIZE));
    }
}
