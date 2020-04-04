package at.jojokobi.llamarama.entities;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelBoundsComponent;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.characters.DamageCause;

public class Bomb extends AbstractBullet {
	
	private double radius = 4;

	public Bomb(double x, double y, double z, String area, CharacterComponent shooter, int damage, Vector3D motion, double speed) {
		super(x, y, z, area, "bullet.bomb", DamageCause.BOMB, shooter, damage, motion, speed);
	}
	
	public Bomb() {
		this(0, 0, 0, "", null, 0, new Vector3D(), 37.5);
	}
	
	@Override
	public void hostUpdate(Level level, UpdateEvent event) {
		LevelBoundsComponent component = level.getComponent(LevelBoundsComponent.class);
		if ((component != null && component.nearBounds(this)) || !level.getSolidInArea(getX() - 0.001, getY() - 0.001, getZ() - 0.001, getWidth() + 0.002, getHeight() + 0.002, getLength() + 0.002, getArea()).isEmpty()) {
			damage(level, getCause(), null, null);
		}
		super.hostUpdate(level, event);
	}

	@Override
	protected void damage(Level level, DamageCause cause, GameObject obj, CharacterComponent comp) {
		Vector3D center = getPositionVector().add(getSize().multiply(0.5));
		for (GameObject o : level.getObjectsInArea(center.getX() - radius, center.getY() - radius, center.getZ() - radius, radius * 2, radius * 2, radius * 2, getArea())) {
			CharacterComponent c = o.getComponent(CharacterComponent.class);
			if (c != null) {
				c.damage(level, getShooter(), getDamage(), cause);
			}
		}
	}

}
