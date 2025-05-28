package world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.function.Consumer;

public class Night {
	private static final float FINAL_OPACITY = 0.5f;

	public static GameObject create(Vector2 windowDimensions, float cycleLength) {
		GameObject night = new GameObject(Vector2.ZERO, windowDimensions,
				new RectangleRenderable(Color.BLACK));
		night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		night.setTag("night");


		Transition<Float> transition = new Transition<Float>(night, night.renderer()::setOpaqueness,
				0f, FINAL_OPACITY, Transition.CUBIC_INTERPOLATOR_FLOAT,
				cycleLength / 2, Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
				null);
		return night;
	}
}
