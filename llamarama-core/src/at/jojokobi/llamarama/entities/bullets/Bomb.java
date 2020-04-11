package at.jojokobi.llamarama.entities.bullets;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.characters.DamageCause;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.DamageableComponent;

public class Bomb extends AbstractBullet {
	
	private double radius = 4;

	public Bomb(double x, double y, double z, String area, CharacterComponent shooter, int damage, Vector3D motion, double speed) {
		super(x, y, z, area, "bullet.bomb", DamageCause.BOMB, shooter, damage, motion, speed);
	}
	
	public Bomb() {
		this(0, 0, 0, "", null, 0, new Vector3D(), 37.5);
	}

	@Override
	protected void damage(Level level, DamageCause cause, GameObject obj, DamageableComponent comp) {
		Vector3D center = getPositionVector().add(getSize().multiply(0.5));
		for (GameObject o : level.getObjectsInArea(center.getX() - radius, center.getY() - radius, center.getZ() - radius, radius * 2, radius * 2, radius * 2, getArea())) {
			CharacterComponent c = o.getComponent(CharacterComponent.class);
			if (c != null) {
				c.damage(level, getShooter(), getDamage(), cause);
			}
		}
	}

}
