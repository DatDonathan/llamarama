package at.jojokobi.llamarama.entities;

import java.util.List;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.FollowCameraComponent;
import at.jojokobi.donatengine.objects.PlayerComponent;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.llamarama.ControlConstants;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.characters.CharacterTypeProvider;

public class PlayerCharacter extends CharacterInstance {
	
	private double swapCooldown = 0.0;

	public PlayerCharacter(double x, double y, double z, String area, long client, CharacterType character, String name) {
		super(x, y, z, area, character, name);
		PlayerComponent comp = new PlayerComponent(client);
		addComponent(comp);
		addComponent(new FollowCameraComponent(comp, 1/3.0));
	}
	
	public PlayerCharacter() {
		this(0, 0, 0, "", 0, CharacterTypeProvider.getCharacterTypes().get("Corporal"), "Corporal");
	}
	
	@Override
	public void hostUpdate(Level level, UpdateEvent event) {
		super.hostUpdate(level, event);
		CharacterComponent comp = getComponent(CharacterComponent.class);
		Input input =  event.getInput().getInput(getComponent(PlayerComponent.class).getClient());
		//Move
		Vector2D axis = input.getAxis(ControlConstants.MOVEMENT);
		double speed = comp.getCharacter().getSpeed();
		axis.normalize().multiply(speed);
		
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
			comp.setUseAbility(true);
		}
		else {
			comp.setUseAbility(false);
		}
		swapCooldown -= event.getDelta();
	}
	
	@Override
	public void clientUpdate(Level level, UpdateEvent event) {
		super.clientUpdate(level, event);
	}
	
	@Override
	public void render(List<RenderData> data, Camera cam, Level level) {
		CharacterComponent comp = getComponent(CharacterComponent.class);
//		PlayerComponent player = getComponent(PlayerComponent.class);
		if (comp.isAlive()) {
			super.render(data, cam, level);
		}
		/*
		if (comp.isAlive() || player.getClient() == level.getClientId()) {
			if (!getComponent(CharacterComponent.class).isAlive() && getComponent(PlayerComponent.class).getClient() == level.getClientId()) {
				ctx.setGlobalAlpha(0.5);
			}
			super.render(ctx, cam, level);
			if (!getComponent(CharacterComponent.class).isAlive() && getComponent(PlayerComponent.class).getClient() == level.getClientId()) {
				ctx.setGlobalAlpha(1);
			}
		}*/
		//TODO: Invisible when dead
	}


}
