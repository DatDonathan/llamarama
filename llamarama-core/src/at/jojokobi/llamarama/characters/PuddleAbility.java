package at.jojokobi.llamarama.characters;

import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.Puddle;

public class PuddleAbility implements Ability {

	@Override
	public void update(Level level, GameObject object, double delta, CharacterComponent character) {
		
	}

	@Override
	public boolean use(Level level, GameObject object, double delta, CharacterComponent character) {
		Puddle puddle = new Puddle(0, 0, 0, object.getArea(), character, 15);
		Vector3D pos = character.getDirection().getMotion();
		pos.setX(pos.getX() * (puddle.getWidth()/2 + puddle.getWidth()/2));
		pos.setZ(pos.getZ() * (puddle.getLength()/2 + puddle.getLength()/2));
		pos.add(object.getPositionVector()).add(object.getSize().multiply(0.5)).add(puddle.getSize().multiply(-0.5));
		pos.setY(object.getY());
		puddle.setX(pos.getX());
		puddle.setY(pos.getY());
		puddle.setZ(pos.getZ());
		level.spawn(puddle);
		return true;
	}

	@Override
	public boolean shouldUse(Level level, GameObject object, CharacterComponent character) {
		//TODO Ability using for AIs
		return false;
	}

	@Override
	public void render(Level level, GameObject object, CharacterComponent character, List<RenderData> data,
			Camera cam) {
		
	}

	@Override
	public double getCooldown() {
		return 0.4;
	}

}
