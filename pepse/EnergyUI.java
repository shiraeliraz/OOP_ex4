import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import world.Avatar;

import java.awt.*;

public class EnergyUI extends GameObject {

	private final Avatar avatar;
	private TextRenderable textRenderable;

	public EnergyUI(Vector2 topLeftCorner, Vector2 dimensions, Avatar avatar) {
		super(topLeftCorner, dimensions, null);
		this.avatar = avatar;
		this.textRenderable = new TextRenderable(Integer.toString(avatar.getEnergy()));
		this.textRenderable.setColor(Color.decode("#FC5D92"));
		this.renderer().setRenderable(textRenderable);
	}

	public static GameObject create(Avatar avatar) {
		Vector2 topLeftCorner = new Vector2(5, 15);
		Vector2 dimensions = new Vector2(40, 40);
		EnergyUI energyUI = new EnergyUI(topLeftCorner, dimensions, avatar);
		return energyUI;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		textRenderable.setString(Integer.toString(avatar.getEnergy()));
	}
}
