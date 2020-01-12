package at.jojokobi.llamarama.entities.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class AttackTask implements CharacterTask {
	
	private Predicate<GameObject> shouldAttack;
	private double range;
	
	
	public AttackTask(Predicate<GameObject> shouldAttack, double range) {
		super();
		this.shouldAttack = shouldAttack;
		this.range = range;
	}

	@Override
	public boolean canApply(Level level, GameObject obj, CharacterComponent ch) {
		return !findTargets(level, obj, ch, obj.getPosition().add(obj.getSize().multiply(0.5)), obj.getArea()).isEmpty();
	}

	@Override
	public Vector3D apply(Level level, GameObject obj, CharacterComponent ch, double delta) {
		ch.attack(obj, level);
		return null;
	}

	@Override
	public void activate(Level level, GameObject obj, CharacterComponent ch) {
		
	}

	@Override
	public void deactivate(Level level, GameObject obj, CharacterComponent ch) {
		
	}
	
	private List<GameObject> findTargets (Level level, GameObject thiz, CharacterComponent comp, Vector3D pos, String area) {
		List<GameObject> objs = new ArrayList<>();
		for (GameObject obj : level.getObjectsInArea(pos.getX() - range, pos.getY() - range, pos.getZ() - range, range * 2, range * 2, range * 2, area)) {
			if (shouldAttack.test(obj) && obj != thiz && comp.getCurrentWeapon().getKey().getFireBehavior().willHit(thiz, comp, obj, level)) {
				objs.add(obj);
			}
		}
		System.out.println(objs);
		return objs;
	}

}
