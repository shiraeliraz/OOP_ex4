import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import world.Avatar;

import java.awt.*;

public class EnergyUI extends GameObject {

	private final Avatar avatar;

	public EnergyUI(Vector2 topLeftCorner, Vector2 dimensions, Avatar avatar) {
		super(topLeftCorner, dimensions, null);
		this.avatar = avatar;
	}

	public static GameObject create(Avatar avatar) {
		Vector2 topLeftCorner = new Vector2(2, 2);
		Vector2 dimensions = new Vector2(50, 50);
		EnergyUI energyUI = new EnergyUI(topLeftCorner, dimensions, avatar);
		return energyUI;
	}

	private void displayNumber(int energy) {
		TextRenderable textRenderable = new TextRenderable(Integer.toString(energy));
		textRenderable.setString(Integer.toString(energy));
		textRenderable.setColor(Color.decode("FC5D92"));
		GameObject numberObject = new GameObject(new Vector2(2, 2),
				new Vector2(50, 50), textRenderable);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		displayNumber(avatar.getEnergy());
	}
}
