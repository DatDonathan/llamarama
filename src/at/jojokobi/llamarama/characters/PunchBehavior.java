package at.jojokobi.llamarama.characters;

import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.Weapon;

public class PunchBehavior implements FireBehavior {
	
	@Override
	public int shoot(GameObject obj, CharacterComponent comp, WeaponType type, Weapon weapon, Level level) {
		List<GameObject> targets = obj.getCollided(level);
		for (GameObject target : targets) {
			CharacterComponent ch = target.getComponent(CharacterComponent.class);
			if (target != obj) {
				ch.damage(type.getDamage()); 
				if (!ch.isAlive()) {
					comp.setKills(comp.getKills());
				}
				Vector2D motion = comp.getDirection().getMotion().multiply(900);
				target.setxMotion(motion.getX());
				target.setzMotion(motion.getY());
			}
		}
		return 0;
	}

}
