package at.jojokobi.llamarama.characters;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.Bullet;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.Weapon;

public class SingleShotBehavior implements FireBehavior {

	@Override
	public int shoot(GameObject obj, CharacterComponent comp, WeaponType type, Weapon weapon, Level level) {
		if (weapon.getBullets() > 0) {
			Vector3D motion = comp.getDirection().getMotion();
			Vector3D pos = new Vector3D(obj.getX() + obj.getWidth()/2 + obj.getWidth()/2 * motion.getX(), obj.getY() + obj.getHeight()/2 + obj.getHeight()/2 * motion.getY(), obj.getZ() + obj.getLength()/2 + obj.getLength()/2 * motion.getZ());
			
			level.spawn(new Bullet(pos.getX(), pos.getY(), pos.getZ(), obj.getArea(), comp, type.getDamage(), motion));
			return 1;
		}
		return 0;
	}

}
