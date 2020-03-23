package at.jojokobi.llamarama.characters;

import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.Weapon;

public class MeleeAttackBehavior implements FireBehavior{

	@Override
	public int shoot(GameObject obj, CharacterComponent comp, WeaponType type, Weapon weapon, Level level) {
		List<GameObject> targets = obj.getCollided(level);
		for (GameObject target : targets) {
			CharacterComponent ch = target.getComponent(CharacterComponent.class);
			if (target != obj) {
				ch.damage(level, comp, type.getDamage(), DamageCause.PUNCH);
				if (!ch.isAlive()) {
					comp.setKills(comp.getKills());
				}
			}
		}
		return 0;
	}

	@Override
	public boolean willHit(GameObject obj, CharacterComponent comp, GameObject target, Level level) {
		return obj.isColliding(target);
	}

}
