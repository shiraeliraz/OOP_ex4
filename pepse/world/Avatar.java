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

	public Avatar(Vector2 topLeftCorner, UserInputListener inputListener, ImageReader imageReader) {
		super(topLeftCorner, new Vector2(50, 50), null);
		ImageRenderable avatarRenderable = imageReader.readImage("assets/idle_0.png", true);
		this.renderer().setRenderable(avatarRenderable);
		setTag("Avatar");
		this.inputListener = inputListener;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		float xVel = 0;
		if(inputListener.isKeyPressed(KeyEvent.VK_LEFT))
			xVel -= VELOCITY_X;
		if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
			xVel += VELOCITY_X;
		transform().setVelocityX(xVel);
		if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0)
			transform().setVelocityY(VELOCITY_Y);
	}
}
