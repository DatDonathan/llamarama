package at.jojokobi.llamarama.entities;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelBoundsComponent;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.characters.DamageCause;

public abstract class AbstractBullet extends CollisionDamager {

	public AbstractBullet(double x, double y, double z, String area, String renderTag, DamageCause cause, CharacterComponent shooter, int damage, Vector3D motion, double speed) {
		super(x, y, z, area, renderTag, shooter, damage, cause);
		motion.normalize().multiply(speed);
		setxMotion(motion.getX());
		setyMotion(motion.getY());
		setzMotion(motion.getZ());
		
		setCollideSolid(true);
	}

	@Override
	public void hostUpdate(Level level, UpdateEvent event) {
		super.hostUpdate(level, event);
		LevelBoundsComponent component = level.getComponent(LevelBoundsComponent.class);
		if ((component != null && component.nearBounds(this)) || !level.getSolidInArea(getX() - 0.001, getY() - 0.001, getZ() - 0.001, getWidth() + 0.002, getHeight() + 0.002, getLength() + 0.002, getArea()).isEmpty()) {
			delete(level);
		}
	}

}
