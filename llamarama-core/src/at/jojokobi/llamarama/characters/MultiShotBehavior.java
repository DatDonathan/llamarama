package at.jojokobi.llamarama.characters;

import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Collidable;
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
		this(amount, 37.5);
	}

	@Override
	public int shoot(GameObject obj, CharacterComponent comp, WeaponType type, Weapon weapon, Level level) {
		int used;
		for (used = 0; used < Math.min(amount, weapon.getBullets()); used++) {
			Vector3D motion = comp.getDirection().getMotion();
			motion.add(Math.random() * 0.4 - 0.2, Math.random() * 0.4 - 0.2, Math.random() * 0.4 - 0.2);
			Vector3D pos = new Vector3D(obj.getX() + obj.getWidth()/2 + obj.getWidth()/2 * motion.getX() - 0.25, obj.getY() + obj.getHeight()/2 + obj.getHeight()/2 * motion.getY() - 0.25, obj.getZ() + obj.getLength()/2 + obj.getLength()/2 * motion.getZ() - 0.25);
			
			Bullet bullet = new Bullet(pos.getX(), pos.getY(), pos.getZ(), obj.getArea(), comp, type.getDamage(), motion, speed);
			level.spawn(bullet);
		}
		return used;
	}
	
	@Override
	public boolean willHit(GameObject obj, CharacterComponent comp, GameObject target, Level level) {
		List<Collidable> objs = null;
		final double size = 0.5;
		Vector3D pos = obj.getPositionVector().add(obj.getSize().multiply(0.5));
		Vector3D targetPos = target.getPositionVector().add(target.getSize().multiply(0.5));
		Vector3D dst = targetPos.clone().subtract(pos);

		boolean hit = true;
		switch(comp.getDirection()) {
		case DOWN:
			objs = level.getCollidablesInArea(pos.getX() - size/2, pos.getY() - size/2, pos.getZ(), size, size, dst.getZ(), obj.getArea());
			hit = dst.getZ() >= 0;
			break;
		case LEFT:
			objs = level.getCollidablesInArea(pos.getX() + dst.getX(), pos.getY() - size/2, pos.getZ(), -dst.getX(), size, size, obj.getArea());
			hit = dst.getX() <= 0;
			break;
		case RIGHT:
			objs = level.getCollidablesInArea(pos.getX(), pos.getY() - size/2, pos.getZ(), dst.getX(), size, size, obj.getArea());
			hit = dst.getX() >= 0;
			break;
		case UP:
			objs = level.getCollidablesInArea(pos.getX() - size/2, pos.getY() - size/2, pos.getZ() + dst.getZ(), size, size, -dst.getZ(), obj.getArea());
			hit = dst.getZ() <= 0;
			break;
		}
		objs.remove(obj);
		hit = hit && objs.contains(target);
		objs.remove(target);
		return hit && obj.getArea().equals(target.getArea()) && objs.stream().allMatch(o -> !o.isSolid());
	}

}
