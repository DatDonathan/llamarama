package at.jojokobi.llamarama.characters;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.Bullet;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.Weapon;

public class MultiShotBehavior implements FireBehavior {
	
	private int amount;
	private double speed;
	
	
	
	public MultiShotBehavior(int amount, double speed) {
		super();
		this.amount = amount;
		this.speed = speed;
	}

	public MultiShotBehavior(int amount) {
		this(amount, 1200);
	}

	@Override
	public int shoot(GameObject obj, CharacterComponent comp, WeaponType type, Weapon weapon, Level level) {
		int used;
		for (used = 0; used < Math.min(amount, weapon.getBullets()); used++) {
			Vector3D motion = comp.getDirection().getMotion();
			motion.add(Math.random() * 0.4 - 0.2, Math.random() * 0.4 - 0.2, Math.random() * 0.4 - 0.2);
			Vector3D pos = new Vector3D(obj.getX() + obj.getWidth()/2 + obj.getWidth()/2 * motion.getX(), obj.getY() + obj.getHeight()/2 + obj.getHeight()/2 * motion.getY(), obj.getZ() + obj.getLength()/2 + obj.getLength()/2 * motion.getZ());
			
			Bullet bullet = new Bullet(pos.getX(), pos.getY(), pos.getZ(), obj.getArea(), comp, type.getDamage(), motion, speed);
			level.spawn(bullet);
		}
		return used;
	}

}
