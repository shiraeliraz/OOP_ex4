package world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class SunHalo extends GameObject{
	private static final Color SOFT_YELLOW= new Color(255, 255, 0, 50);
	private static GameObject sun;
	private static GameObject sunHalo;

	 // Size of the halo
	public SunHalo(Vector2 topLeftCorner, Vector2 dimensions, OvalRenderable renderable, GameObject sun) {
		super(topLeftCorner, dimensions, renderable);
		this.sun = sun;
		this.sunHalo = sunHalo;
	}

	@Override
	public void update(float deltaTime) {
		setCenter(sun.getCenter());
	}

	public static GameObject create(GameObject sun) {
		Vector2 haloSize = sun.getDimensions().mult(1.5f);
		GameObject sunHalo = new SunHalo(Vector2.ZERO, haloSize, new OvalRenderable(SOFT_YELLOW), sun);
		sunHalo.setCenter(sun.getCenter());
		sunHalo.setTag("sunHalo");
		sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		return sunHalo;
	}
}
