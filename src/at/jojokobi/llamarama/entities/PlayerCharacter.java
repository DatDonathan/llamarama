package at.jojokobi.llamarama.entities;

import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.PlayerComponent;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.llamarama.ControlConstants;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.characters.CharacterTypeProvider;
import javafx.scene.canvas.GraphicsContext;

public class PlayerCharacter extends CharacterInstance {
	
	private double swapCooldown = 0.0;

	public PlayerCharacter(double x, double y, double z, String area, long client, CharacterType character, String name) {
		super(x, y, z, area, character, name);
		addComponent(new PlayerComponent(client));
	}
	
	public PlayerCharacter() {
		this(0, 0, 0, "", 0, CharacterTypeProvider.getCharacterTypes().get("Corporal"), "Corporal");
	}
	
	@Override
	public void hostUpdate(Level level, LevelHandler handler, Camera camera, double delta) {
		super.hostUpdate(level, handler, camera, delta);
		CharacterComponent comp = getComponent(CharacterComponent.class);
		Input input =  handler.getInput(getComponent(PlayerComponent.class).getClient());
		//Move
		Vector2D axis = input.getAxis(ControlConstants.MOVEMENT).normalize();
		double speed = comp.getCharacter().getSpeed();
		axis.multiply(speed);
		if (getxMotion() >= -speed && getxMotion() <= speed) {
			setxMotion(axis.getX());
		}
		if (getzMotion() >= -speed && getzMotion() <= speed) {
			setzMotion(axis.getY());
		}
		//Shoot
		if (input.getButton(ControlConstants.ATTACK)) {
			comp.attack(this, level);
		}
		//Swap Weapon
		if (input.getButton(ControlConstants.SWAP_WEAPON) && swapCooldown <= 0) {
			comp.swapWeapon();
			swapCooldown = 0.25;
		}
		//Use ability
		if (input.getButton(ControlConstants.USE_ABILITY)) {
			comp.setUsingAbility(true);
		}
		else {
			comp.setUsingAbility(false);
		}
		swapCooldown -= delta;
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
		}
	}

}
