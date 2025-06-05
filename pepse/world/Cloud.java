package world;

import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import util.ColorSupplier;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Cloud {
	private static final Color BASE_CLOUD_COLOR = new Color(255, 255, 255);
	private static List<List<Integer>> cloudPlaces = List.of(
			List.of(0, 1, 1, 0, 0, 0),
			List.of(1, 1, 1, 0, 1, 0),
			List.of(1, 1, 1, 1, 1, 1),
			List.of(1, 1, 1, 1, 1, 1),
			List.of(0, 1, 1, 1, 0, 0),
			List.of(0, 0, 0, 0, 0, 0)
	);
	private static final float CLOUD_MOVEMENT_TIME = 10f; // seconds

	public static List<Block> createCloud(Vector2 windowDimensions) {
		Vector2 topLeftCorner = new Vector2(-200, windowDimensions.y() * 0.3f);
		List<Block> cloudBlocks = new ArrayList<>();
		for (int row = 0; row < cloudPlaces.size(); row++) {
			for (int col = 0; col < cloudPlaces.get(row).size(); col++) {
				if (cloudPlaces.get(row).get(col) == 1) {
					Vector2 blockPosition = new Vector2(topLeftCorner.x() + col * Block.SIZE,
							topLeftCorner.y() + row * Block.SIZE);
					RectangleRenderable cloudRenderable =
							new RectangleRenderable(ColorSupplier.approximateColor(BASE_CLOUD_COLOR));
					Block cloudBlock = new Block(blockPosition, cloudRenderable);
					cloudBlock.setTag("cloud");
					cloudBlock.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

					cloudBlocks.add(cloudBlock);
				}
			}
		}
		createCloudMovement(cloudBlocks, windowDimensions);
		return cloudBlocks;
	}

	private static void createCloudMovement(List<Block> cloudBlocks, Vector2 windowDimensions) {
		float endX = windowDimensions.x() + 200;
		float startX = -200;
		float distance = endX - startX;

		for (Block block : cloudBlocks) {
			float blockOffsetX = block.getTopLeftCorner().x() - startX;

			new Transition<Float>(
					block,
					(Float deltaX) -> {
						Vector2 currentPos = block.getTopLeftCorner();
						block.setTopLeftCorner(new Vector2(startX + deltaX + blockOffsetX, currentPos.y()));
					},
					0f,
					distance,
					Transition.LINEAR_INTERPOLATOR_FLOAT,
					CLOUD_MOVEMENT_TIME,
					Transition.TransitionType.TRANSITION_LOOP,
					null
			);
		}
	}
}
