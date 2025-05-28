package world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Avatar extends GameObject {
	private static final float VELOCITY_X = 400;
	private static final float VELOCITY_Y = -650;
	private UserInputListener inputListener;
	private float energy = 100; // Energy level for jumping

	public Avatar(Vector2 topLeftCorner, UserInputListener inputListener, ImageReader imageReader) {
		super(topLeftCorner, new Vector2(50, 50), null);
		ImageRenderable avatarRenderable = imageReader.readImage("assets/idle_0.png", true);
		this.renderer().setRenderable(avatarRenderable);
		setTag("Avatar");
		this.inputListener = inputListener;
	}

	public int getEnergy() {
		return (int) energy; // Return energy as an integer
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		float xVel = 0;
		if(inputListener.isKeyPressed(KeyEvent.VK_LEFT) && energy > 0) {
			xVel -= VELOCITY_X;
			energy -= 0.5f; // Decrease energy for moving left
		}
		if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && energy > 0) {
			xVel += VELOCITY_X;
			energy -= 0.5f; // Decrease energy for moving right
		}

		transform().setVelocityX(xVel);
		if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 && energy >= 10 ) {
			transform().setVelocityY(VELOCITY_Y);
			energy -= 10; // Decrease energy for jumping
		}
		if (getVelocity().x() == 0 && getVelocity().y() == 0 && energy < 100) {
			energy += 1;
		}
	}
}
