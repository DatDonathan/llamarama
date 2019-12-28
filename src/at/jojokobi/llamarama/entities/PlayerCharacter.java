package at.jojokobi.llamarama.entities;

import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.PlayerComponent;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.ControlConstants;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.characters.CharacterTypeProvider;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlayerCharacter extends CharacterInstance {

	public PlayerCharacter(double x, double y, double z, String area, long client, CharacterType character) {
		super(x, y, z, area, character);
		addComponent(new PlayerComponent(client));
	}
	
	public PlayerCharacter() {
		this(0, 0, 0, "", 0, CharacterTypeProvider.getCharacterTypes().get("Corporal"));
	}
	
	@Override
	public void hostUpdate(Level level, LevelHandler handler, Camera camera, double delta) {
		super.hostUpdate(level, handler, camera, delta);
		CharacterComponent comp = getComponent(CharacterComponent.class);
		Input input =  handler.getInput(getComponent(PlayerComponent.class).getClient());
		Vector2D axis = input.getAxis(ControlConstants.MOVEMENT).normalize();
		axis.multiply(comp.getCharacter().getSpeed());
		setxMotion(axis.getX());
		setzMotion(axis.getY());
		//Shoot
		if (input.getButton(ControlConstants.ATTACK)) {
			comp.attack(this, level);
		}
		if (input.getButton(ControlConstants.SWAP_WEAPON)) {
			comp.swapWeapon();
		}
	}
	
	@Override
	public void clientUpdate(Level level, LevelHandler handler, Camera camera, double delta) {
		super.clientUpdate(level, handler, camera, delta);
		if (level.getClientId() == getComponent(PlayerComponent.class).getClient()) {
			camera.setFollow(level.getId(this));
		}
	}
	
	@Override
	public void render(GraphicsContext ctx, Camera cam, Level level) {
		if (getComponent(CharacterComponent.class).isAlive() || getComponent(PlayerComponent.class).getClient() == level.getClientId()) {
			super.render(ctx, cam, level);
			ctx.setStroke(Color.RED);
			Vector2D pos = cam.toScreenPosition(new Vector3D(getX(), getY(), getZ()));
			ctx.strokeRect(pos.getX(), pos.getY(), getWidth(), getHeight());
		}
	}

}
