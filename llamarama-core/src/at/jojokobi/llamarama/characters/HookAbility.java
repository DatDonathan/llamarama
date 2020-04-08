package at.jojokobi.llamarama.characters;

import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.Hook;

public class HookAbility implements Ability {

	@Override
	public void update(Level level, GameObject object, double delta, CharacterComponent character) {
		
	}

	@Override
	public boolean use(Level level, GameObject object, double delta, CharacterComponent character) {
		Hook hook = new Hook(0, 0, 0, object.getArea(), object, character.getDirection().getMotion(), 37.5, 20);
		Vector3D pos = object.getPositionVector().add(object.getSize().multiply(0.5)).subtract(hook.getSize().multiply(0.5));
		hook.setX(pos.getX());
		hook.setY(pos.getY());
		hook.setZ(pos.getZ());
		level.spawn(hook);
		return true;
	}

	@Override
	public boolean shouldUse(Level level, GameObject object, CharacterComponent character) {
		return false;
	}

	@Override
	public void render(Level level, GameObject object, CharacterComponent character, List<RenderData> data,
			Camera cam) {
		
	}

	@Override
	public double getCooldown() {
		return 1.0;
	}

}
