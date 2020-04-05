package at.jojokobi.llamarama.entities.ai;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class SwapWeaponTask implements CharacterTask {
	
	private long nextTime = System.currentTimeMillis();

	@Override
	public boolean canApply(Level level, GameObject obj, CharacterComponent ch) {
		return System.currentTimeMillis() <= nextTime;
	}

	@Override
	public Vector3D apply(Level level, GameObject obj, CharacterComponent ch, double delta) {
		nextTime = System.currentTimeMillis() + (int) (Math.random() * 8000) + 2000;
		ch.swapWeapon();
		return null;
	}

	@Override
	public void activate(Level level, GameObject obj, CharacterComponent ch) {
		
	}

	@Override
	public void deactivate(Level level, GameObject obj, CharacterComponent ch) {
		
	}

}
