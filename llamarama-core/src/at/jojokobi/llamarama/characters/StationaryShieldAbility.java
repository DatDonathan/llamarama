package at.jojokobi.llamarama.characters;

import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.StationaryShield;
import at.jojokobi.llamarama.entities.bullets.AbstractBullet;

public class StationaryShieldAbility implements Ability{

	@Override
	public void update(Level level, GameObject object, double delta, CharacterComponent character) {
		
	}

	@Override
	public boolean use(Level level, GameObject object, double delta, CharacterComponent character) {
		StationaryShield shield = new StationaryShield(0, 0, 0, object.getArea(), character.getDirection());
		Vector3D pos = character.getDirection().toVector();
		pos.setX(pos.getX() * (object.getWidth()/2 + shield.getWidth() * 0.5));
		pos.setZ(pos.getZ() * (object.getLength()/2 + shield.getLength() * 0.5));
		pos.add(object.getPositionVector()).add(object.getSize().multiply(0.5)).add(shield.getSize().multiply(-0.5));
		pos.setY(object.getY());
		shield.setX(pos.getX());
		shield.setY(pos.getY());
		shield.setZ(pos.getZ());
		level.spawn(shield);
		return true;
	}

	@Override
	public double getUsePriority(Level level, GameObject object, CharacterComponent character) {
		List<AbstractBullet> bullets = object.getObjectsInDirection(level, character.getDirection().toVector(), 128, AbstractBullet.class);
		return !bullets.isEmpty() && bullets.stream().allMatch(o -> o.getShooter() != character) ? 1 : 0;
	}

	@Override
	public void render(Level level, GameObject object, CharacterComponent character, List<RenderData> data,
			Camera cam) {
		
	}

	@Override
	public double getCooldown() {
		return 5.0;
	}
	
}
