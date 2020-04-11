package at.jojokobi.llamarama.characters;

import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.Weapon;

public class PunchBehavior implements FireBehavior {
	
	@Override
	public int shoot(GameObject obj, CharacterComponent comp, WeaponType type, Weapon weapon, Level level) {
		List<GameObject> targets = obj.getObjectsInDirection(level, comp.getDirection().toVector(), 1, GameObject.class);
		for (GameObject target : targets) {
			CharacterComponent ch = target.getComponent(CharacterComponent.class);
			if (ch != null && target != obj) {
				ch.damage(level, comp, type.getDamage(), DamageCause.PUNCH);
				ch.attemptKnockOut(3.5);
				Vector3D motion = comp.getDirection().toVector().multiply(30);
				target.setxMotion(motion.getX());
				target.setzMotion(motion.getZ());
			}
		}
		return 0;
	}
	
	@Override
	public boolean willHit(GameObject obj, CharacterComponent comp, GameObject target, Level level) {
		return obj.getObjectsInDirection(level, comp.getDirection().toVector(), 1, GameObject.class).contains(target);
	}

}
