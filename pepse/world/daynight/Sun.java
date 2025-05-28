package world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sun {
	private static final float TERRAIN_HEIGHT_FACTOR = 2/3f;

	public static GameObject create(Vector2 windowDimension, float cycleLength){
		Vector2 windowCenter = new Vector2(windowDimension.x()/2, windowDimension.y()/2);
		GameObject sun = new GameObject(windowCenter, new Vector2(50,50), new OvalRenderable(Color.YELLOW));
		sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		sun.setTag("sun");

		//Transition
		Vector2 initialSunCenter = windowCenter;
		Vector2 cycleCenter = new Vector2(windowDimension.x()/2,windowDimension.y()*TERRAIN_HEIGHT_FACTOR );
		Transition<Float> transition = new Transition<Float>(sun, (Float angle) -> sun.setCenter
				(initialSunCenter.subtract(cycleCenter)
						.rotated(angle)
						.add(cycleCenter)), 0f, 360f,
				Transition.LINEAR_INTERPOLATOR_FLOAT,
				cycleLength,
				Transition.TransitionType.TRANSITION_LOOP,
				null);
		return sun;
	}
}
