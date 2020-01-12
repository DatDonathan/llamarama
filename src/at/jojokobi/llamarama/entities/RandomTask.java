package at.jojokobi.llamarama.entities;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;

public class RandomTask implements CharacterTask {
	
	private double interval = 3.0;
	private double distance = 1024.0;
	private double timer = 0;
	private Vector3D goal;

	@Override
	public boolean canApply(Level level, GameObject obj, CharacterComponent ch) {
		return true;
	}

	@Override
	public Vector3D apply(Level level, GameObject obj, CharacterComponent ch, double delta) {
		if (timer <= 0) {
			timer = interval;
			goal = obj.getPosition().add((Math.random() - 0.5) * distance * 2, (Math.random() - 0.5) * distance * 2, (Math.random() - 0.5) * distance * 2);
		}
		timer -= delta;
 		return goal.clone();
	}

	@Override
	public void activate(Level level, GameObject obj, CharacterComponent ch) {
		
	}

	@Override
	public void deactivate(Level level, GameObject obj, CharacterComponent ch) {
		
	}

}
