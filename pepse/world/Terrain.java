package world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import util.ColorSupplier;

import java.awt.*;
import java.util.List;

public class Terrain {
	private final Vector2 windowDimensions;
	private final int seed;
	private float groundHeightAtX0;
	private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);

	public Terrain(Vector2 windowDimensions, int seed) {
		this.windowDimensions = windowDimensions;
		this.seed = seed;
		this.groundHeightAtX0 = windowDimensions.y() * (float)2/3;
	}

	public float getGroundHeightAtX(float x) {
		return groundHeightAtX0;
	} // TODO: make more interesting

	public List<Block> createInRange(int minX, int maxX) {
		RectangleRenderable renderable = new RectangleRenderable(
				ColorSupplier.approximateColor(BASE_GROUND_COLOR));
		Block block = new Block()

	}
}
