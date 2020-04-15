package at.jojokobi.llamarama.entities.bullets;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelBoundsComponent;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.characters.DamageCause;
import at.jojokobi.llamarama.entities.CharacterComponent;

public abstract class AbstractBullet extends CollisionDamager {

	public AbstractBullet(double x, double y, double z, String area, String renderTag, DamageCause cause, CharacterComponent shooter, int damage, Vector3D motion, double speed) {
		super(x, y, z, area, renderTag, shooter, damage, cause);
		motion.normalize().multiply(speed);
		setxMotion(motion.getX());
		setyMotion(motion.getY());
		setzMotion(motion.getZ());
		
		setCollideSolid(false);
	}

	@Override
	public void hostUpdate(Level level, UpdateEvent event) {
		super.hostUpdate(level, event);
		LevelBoundsComponent component = level.getComponent(LevelBoundsComponent.class);
		if ((component != null && component.nearBounds(this)) || level.getTileSystem().getTilesInAbsoluteArea(getX() - 0.001, getY() - 0.001, getZ() - 0.001, getWidth() + 0.002, getHeight() + 0.002, getLength() + 0.002, getArea()).stream().anyMatch(t -> t.getTile().isSolid()) || level.getObjectsInArea(getX() - 0.001, getY() - 0.001, getZ() - 0.001, getWidth() + 0.002, getHeight() + 0.002, getLength() + 0.002, getArea()).stream().anyMatch(o -> {
			BulletInteractorComponent comp = o.getComponent(BulletInteractorComponent.class);
			return (comp == null && o.isSolid()) || (comp != null && comp.block(this));
		})) {
			delete(level);
		}
	}

}
