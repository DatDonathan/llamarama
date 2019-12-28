package at.jojokobi.llamarama.characters;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.Bullet;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.Weapon;

public class MultiShotBehavior implements FireBehavior {
	
	private int amount;
	
	public MultiShotBehavior(int amount) {
		super();
		this.amount = amount;
	}

	@Override
	public int shoot(GameObject obj, CharacterComponent comp, WeaponType type, Weapon weapon, Level level) {
		int used;
		for (used = 0; used < Math.min(amount, weapon.getBullets()); used++) {
			Vector3D motion = comp.getDirection().getMotion();
			motion.add(Math.random() * 0.4 - 0.2, Math.random() * 0.4 - 0.2, Math.random() * 0.4 - 0.2);
			motion.normalize();
			Vector3D pos = new Vector3D(obj.getX() + obj.getWidth()/2 + obj.getWidth()/2 * motion.getX(), obj.getY() + obj.getHeight()/2 + obj.getHeight()/2 * motion.getY(), obj.getZ() + obj.getLength()/2 + obj.getLength()/2 * motion.getZ());
			
			Bullet bullet = new Bullet(pos.getX(), pos.getY(), pos.getZ(), obj.getArea(), comp, type.getDamage(), motion);
			level.spawn(bullet);
		}
		return used;
	}

}
