package at.jojokobi.llamarama.entities.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class FollowTask implements CharacterTask {
	
	private Predicate<GameObject> shouldFollow;
	private double range;
	private boolean clearOnDeactivate;
	private GameObject follow;
	

	public FollowTask(Predicate<GameObject> shouldFollow, double range, boolean clearOnDeactivate) {
		super();
		this.shouldFollow = shouldFollow;
		this.range = range;
		this.clearOnDeactivate = clearOnDeactivate;
	}

	@Override
	public boolean canApply(Level level, GameObject obj, CharacterComponent ch) {
		List<GameObject> targets = findTargets(level, obj, obj.getPosition(), obj.getArea());
		return follow != null ? targets.contains(follow) || !targets.isEmpty() : !targets.isEmpty();
	}

	@Override
	public Vector3D apply(Level level, GameObject obj, CharacterComponent ch, double delta) {
		return follow.getPosition().add(follow.getSize().multiply(0.5)).subtract(obj.getSize().multiply(0.5));
	}

	@Override
	public void activate(Level level, GameObject obj, CharacterComponent ch) {
		List<GameObject> targets = findTargets(level, obj, obj.getPosition(), obj.getArea());
		if (follow == null || !targets.contains(follow)) {
			follow = targets.get(new Random().nextInt(targets.size()));
		}
	}

	@Override
	public void deactivate(Level level, GameObject obj, CharacterComponent ch) {
		if (clearOnDeactivate) {
			follow = null;
		}
	}
	
	private List<GameObject> findTargets (Level level, GameObject thiz, Vector3D pos, String area) {
		List<GameObject> objs = new ArrayList<>();
		for (GameObject obj : level.getObjectsInArea(pos.getX() - range, pos.getY() - range, pos.getZ() - range, range * 2, range * 2, range * 2, area)) {
			if (shouldFollow.test(obj) && obj != thiz) {
				objs.add(obj);
			}
		}
		return objs;
	}

	public GameObject getFollow() {
		return follow;
	}
	
}
