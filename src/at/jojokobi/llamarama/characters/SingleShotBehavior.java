package at.jojokobi.llamarama.characters;

import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.Bullet;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.Weapon;

public class SingleShotBehavior implements FireBehavior {
	
	private double speed;


	public SingleShotBehavior(double speed) {
		super();
		this.speed = speed;
	}
	
	public SingleShotBehavior() {
		this(1200);
	}

	@Override
	public int shoot(GameObject obj, CharacterComponent comp, WeaponType type, Weapon weapon, Level level) {
		if (weapon.getBullets() > 0) {
			Vector3D motion = comp.getDirection().getMotion();
			Vector3D pos = new Vector3D(obj.getX() + obj.getWidth()/2 + obj.getWidth()/2 * motion.getX() - 8, obj.getY() + obj.getHeight()/2 + obj.getHeight()/2 * motion.getY() - 8, obj.getZ() + obj.getLength()/2 + obj.getLength()/2 * motion.getZ() - 8);
			
			level.spawn(new Bullet(pos.getX(), pos.getY(), pos.getZ(), obj.getArea(), comp, type.getDamage(), motion, speed));
			return 1;
		}
		return 0;
	}
	
	@Override
	public boolean willHit(GameObject obj, CharacterComponent comp, GameObject target, Level level) {
		List<GameObject> objs = null;
		final double size = 16;
		Vector3D pos = obj.getPosition().add(obj.getSize().multiply(0.5));
		Vector3D targetPos = target.getPosition().add(target.getSize().multiply(0.5));
		Vector3D dst = targetPos.clone().subtract(pos);

		boolean hit = true;
		switch(comp.getDirection()) {
		case DOWN:
			objs = level.getObjectsInArea(pos.getX() - size/2, pos.getY() - size/2, pos.getZ(), size, size, dst.getZ(), obj.getArea());
			hit = dst.getZ() >= 0;
			break;
		case LEFT:
			objs = level.getObjectsInArea(pos.getX() + dst.getX(), pos.getY() - size/2, pos.getZ(), -dst.getX(), size, size, obj.getArea());
			hit = dst.getX() <= 0;
			break;
		case RIGHT:
			objs = level.getObjectsInArea(pos.getX(), pos.getY() - size/2, pos.getZ(), dst.getX(), size, size, obj.getArea());
			hit = dst.getX() >= 0;
			break;
		case UP:
			objs = level.getObjectsInArea(pos.getX() - size/2, pos.getY() - size/2, pos.getZ() + dst.getZ(), size, size, -dst.getZ(), obj.getArea());
			hit = dst.getZ() <= 0;
			break;
		}
		objs.remove(obj);
		hit = hit && objs.contains(target);
		objs.remove(target);
		return hit && obj.getArea().equals(target.getArea()) && objs.stream().allMatch(o -> !o.isSolid());
	}

}
