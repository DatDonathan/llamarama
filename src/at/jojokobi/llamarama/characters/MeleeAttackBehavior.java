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
				ch.setHp(ch.getHp() - type.getDamage());
				if (ch.getHp() <= 0) {
					comp.setKills(comp.getKills());
				}
			}
		}
		return 0;
	}

}
