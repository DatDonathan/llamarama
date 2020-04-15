package at.jojokobi.llamarama.characters;

import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Collidable;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.Weapon;

public abstract class AbstractMultiShotBehavior implements FireBehavior {
	
	private double speed;
	
	public AbstractMultiShotBehavior(double speed) {
		super();
		this.speed = speed;
	}

	@Override
	public int shoot(GameObject obj, CharacterComponent comp, WeaponType type, Weapon weapon, Level level) {
		int used;
		int amount = Math.min(determineAmount(comp), weapon.getBullets());
		for (used = 0; used < amount; used++) {
			Vector3D motion = comp.getDirection().toVector();
			Vector3D pos = new Vector3D(obj.getX() + obj.getWidth()/2 - 0.25, obj.getY() + obj.getHeight()/2 - 0.25, obj.getZ() + obj.getLength()/2 - 0.25);
			pos.add(obj.getWidth()/2 * Math.signum(motion.getX()), obj.getHeight()/2 * Math.signum(motion.getY()), obj.getLength()/2 * Math.signum(motion.getZ()));
			spread(pos, motion, used);
			
			GameObject bullet = createBullet(type.getDamage(), comp, pos, obj.getArea(), motion, speed);
			level.spawn(bullet);
		}
		return used;
	}
	
	@Override
	public boolean willHit(GameObject obj, CharacterComponent comp, GameObject target, Level level) {
		List<Collidable> objs = null;
		final double size = getSize();
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
	
	public abstract GameObject createBullet (int damage, CharacterComponent shooter, Vector3D pos, String area, Vector3D motion, double speed);
	
	protected abstract int determineAmount (CharacterComponent comp);
	
	protected abstract void spread (Vector3D pos, Vector3D motion, int number);
	
	protected abstract double getSize ();

}
