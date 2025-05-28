package world;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Avatar extends GameObject {
	private static final float VELOCITY_X = 400;
	private static final float VELOCITY_Y = -650;
	private static final float GRAVITY = 600;
	private UserInputListener inputListener;
	private ImageReader imageReader;
	private float energy = 100; // Energy level for jumping
	private AnimationRenderable idlingRenderable;
	private AnimationRenderable runningRenderable;

	public Avatar(Vector2 topLeftCorner, UserInputListener inputListener, ImageReader imageReader) {
		super(topLeftCorner, new Vector2(50, 50), imageReader.readImage("assets/idle_0.png", true));
		physics().preventIntersectionsFromDirection(Vector2.ZERO);
		setTag("avatar");
		this.inputListener = inputListener;
		this.imageReader = imageReader;
		setAnimationRenderables();
		renderer().setRenderable(idlingRenderable);
		transform().setAccelerationY(GRAVITY);
	}

	private void setAnimationRenderables() {
		String[] idleFrames = {
			"assets/idle_0.png", "assets/idle_1.png", "assets/idle_2.png", "assets/idle_3.png"
		};
		idlingRenderable = new AnimationRenderable(idleFrames, imageReader, true, 0.1f);
		Renderable[] runningFrames = new Renderable[6];
		runningFrames[0] = imageReader.readImage("assets/run_0.png", true);
		runningFrames[1] = imageReader.readImage("assets/run_1.png", true);
		runningFrames[2] = imageReader.readImage("assets/run_2.png", true);
		runningFrames[3] = imageReader.readImage("assets/run_3.png", true);
		runningFrames[4] = imageReader.readImage("assets/run_4.png", true);
		runningFrames[5] = imageReader.readImage("assets/run_5.png", true);
		runningRenderable = new AnimationRenderable(runningFrames, 0.1f);

	}

	public int getEnergy() {
		return (int) energy; // Return energy as an integer
	}

	public void addEnergy(float amount) {
		energy += amount;
		if (energy > 100) {
			energy = 100; // Cap energy at 100
		}
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		float xVel = 0;
		if(inputListener.isKeyPressed(KeyEvent.VK_LEFT) && energy > 0) {
			xVel -= VELOCITY_X;
			energy -= 0.5f; // Decrease energy for moving left
			renderer().setRenderable(runningRenderable);
			renderer().setIsFlippedHorizontally(true);


		}
		if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && energy > 0) {
			xVel += VELOCITY_X;
			energy -= 0.5f; // Decrease energy for moving right
			renderer().setRenderable(runningRenderable);
			renderer().setIsFlippedHorizontally(false);

		}

		transform().setVelocityX(xVel);
		if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 && energy >= 10 ) {
			transform().setVelocityY(VELOCITY_Y);
			energy -= 10; // Decrease energy for jumping
		}
		if (getVelocity().x() == 0 && getVelocity().y() == 0 && energy < 100) {
			energy += 1;
			renderer().setRenderable(idlingRenderable);
		}
	}
	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		super.onCollisionEnter(other, collision);
		System.out.println("Avatar collided with: " + other.getTag());
		if(other.getTag().equals("ground")){
			this.transform().setVelocityY(0);
		}
	}
}
