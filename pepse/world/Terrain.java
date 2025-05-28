package world;

import danogl.collisions.Layer;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import util.ColorSupplier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Terrain {
	private final Vector2 windowDimensions;
	private final int seed;
	private float groundHeightAtX0;
	private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
	private static final int TERRAIN_DEPTH = 20;
	private static final int NOISE_FACTOR = 220;

	public Terrain(Vector2 windowDimensions, int seed) {
		this.windowDimensions = windowDimensions;
		this.seed = seed;
		this.groundHeightAtX0 = windowDimensions.y() * (float)2/3;
	}

	public float groundHeightAt(float x) {
		NoiseGenerator noise = new NoiseGenerator(seed, (int)groundHeightAtX0 );
		return groundHeightAtX0 + (float) noise.noise(x, NOISE_FACTOR);
	}

	public List<Block> createInRange(int minX, int maxX) {
		RectangleRenderable renderable = new RectangleRenderable(
				ColorSupplier.approximateColor(BASE_GROUND_COLOR));
		int blockSize = Block.SIZE;
		int roundedMinX = (int)(Math.ceil((float)minX / blockSize))*blockSize;
		int roundedMaxX = (int)(Math.ceil((float)maxX / blockSize))*blockSize;
		List<Block> blocks = new ArrayList<>();
		for (int x = roundedMinX; x < roundedMaxX; x+=blockSize) {
			float height  = (int)(Math.floor((float) groundHeightAt(x))/blockSize)*blockSize;
			Vector2 topLeftCorner = new Vector2(x, height);
			Block startingBlock = new Block(topLeftCorner, renderable);
			blocks.add(startingBlock);
			startingBlock.setTag("ground");
			for (int i =0; i < TERRAIN_DEPTH; i ++) {
				topLeftCorner = topLeftCorner.add(new Vector2(0, blockSize));
				Block block = new Block(topLeftCorner, renderable);
				blocks.add(block);
				block.setTag("ground");
			}
		}return blocks;
	}
}
