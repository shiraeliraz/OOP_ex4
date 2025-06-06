package world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import util.ColorSupplier;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

public class Cloud implements AvatarObserver{
	private static final Color BASE_CLOUD_COLOR = new Color(255, 255, 255);

	private static List<List<Integer>> cloudPlaces = List.of(
			List.of(0, 0, 0, 0, 1, 1, 0, 0, 0, 0),
			List.of(0, 0, 1, 1, 1, 1, 1, 1, 0, 0),
			List.of(0, 1, 1, 1, 1, 1, 1, 1, 1, 0),
			List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
			List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
			List.of(0, 1, 1, 1, 1, 1, 1, 1, 1, 0),
			List.of(0, 0, 0, 1, 1, 1, 1, 0, 0, 0)
	);

	private static final float CLOUD_MOVEMENT_TIME = 15f;
	private static final float GRAVITY = 150f;
	private Consumer<GameObject> addBlocksToGameObjectCollection;
	private Consumer<GameObject> removeBlocksFromGameObjectCollection;
	private final Vector2 windowDimensions;
	List<Block> cloudBlocks;
	Vector2 RAIN_SIZE = new Vector2(4, 10);
	Random random = new Random();

	public Cloud(Consumer<GameObject> addBlocksToGameObjectCollection,
				 Consumer<GameObject> removeBlocksFromGameObjectCollection,
				 Vector2 windowDimensions, int seed) {
		this.addBlocksToGameObjectCollection = addBlocksToGameObjectCollection;
		this.removeBlocksFromGameObjectCollection = removeBlocksFromGameObjectCollection;
		this.windowDimensions = windowDimensions;
		random.setSeed(seed);
		cloudBlocks = createCloud();
		for (Block block : cloudBlocks) {
			addBlocksToGameObjectCollection.accept(block);
		}
	}

	public List<Block> createCloud() {
		Vector2 topLeftCorner = new Vector2(-300, windowDimensions.y() * 0.25f);
		List<Block> cloudBlocks = new ArrayList<>();

		for (int row = 0; row < cloudPlaces.size(); row++) {
			for (int col = 0; col < cloudPlaces.get(row).size(); col++) {
				if (cloudPlaces.get(row).get(col) == 1) {
					Vector2 blockPosition = new Vector2(
							topLeftCorner.x() + col * Block.SIZE,
							topLeftCorner.y() + row * Block.SIZE
					);

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
		float endX = windowDimensions.x() + 300;
		float startX = -300;
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

	public void onAvatarJumped() {
		for (Block block : cloudBlocks) {
			float randomVal = random.nextFloat();
			if (randomVal > 0.75f) {
				createRainDrop(block);
			}
		}
	}

	private void createRainDrop(Block block) {
		Color rainColor = new Color(100, 150, 255, 200);
		RectangleRenderable rainRenderable =
				new RectangleRenderable(ColorSupplier.approximateColor(rainColor));

		Vector2 worldCloudPosition = getWorldPosition(block);
		Vector2 rainStartPosition = new Vector2(
				worldCloudPosition.x() - RAIN_SIZE.x() / 2,
				worldCloudPosition.y() + Block.SIZE
		);

		Block rainBlock = new Block(rainStartPosition, rainRenderable);
		rainBlock.setTag("rain");
		rainBlock.setCoordinateSpace(CoordinateSpace.WORLD_COORDINATES); // זזים עם העולם!
		rainBlock.setDimensions(RAIN_SIZE);
		rainBlock.transform().setAccelerationY(GRAVITY);
		addBlocksToGameObjectCollection.accept(rainBlock);

		new Transition<Float>(rainBlock,
				(Float delta) -> rainBlock.renderer().setOpaqueness(delta),
				1f, 0f,
				Transition.LINEAR_INTERPOLATOR_FLOAT,
				3f,
				Transition.TransitionType.TRANSITION_ONCE,
				() -> removeBlocksFromGameObjectCollection.accept(rainBlock));
	}

	private Vector2 getWorldPosition(Block cloudBlock) {
		Vector2 cameraPos = cloudBlock.getCenter();
		return cameraPos;
	}
}