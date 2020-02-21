package at.jojokobi.llamarama.entities;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelBoundsComponent;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;

public class Bullet extends GameObject {
//	
//	public static final Image SPIT = new Image(Bullet.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "spit.png"));
//	public static final RenderModel SPIT_MODEL = new Image2DModel(SPIT);
	
	private CharacterComponent shooter;
	private int damage;

	public Bullet(double x, double y, double z, String area, CharacterComponent shooter, int damage, Vector3D motion, double speed) {
		super(x, y, z, area, "bullet.spit");
		this.shooter = shooter;
		this.damage = damage;
		motion.normalize().multiply(speed);
		setxMotion(motion.getX());
		setyMotion(motion.getY());
		setzMotion(motion.getZ());
		
		setWidth(0.5);
		setHeight(0.5);
		setLength(0.5);
	}
	
	public Bullet() {
		this(0, 0, 0, "", null, 0, new Vector3D(), 1200);
	}
	
	@Override
	public void hostUpdate(Level level, UpdateEvent event) {
		super.hostUpdate(level, event);
		LevelBoundsComponent component = level.getComponent(LevelBoundsComponent.class);
		if (component != null && component.nearBounds(this)) {
			delete(level);
		}
		else {
			for (GameObject collided : level.getObjectsInArea(getX() - 1, getY() - 1, getZ() - 1, getWidth() + 2, getHeight() + 2, getLength() + 2, getArea())) {
				CharacterComponent comp = collided.getComponent(CharacterComponent.class);
				if (comp != null && comp != shooter && comp.isAlive()) {
					comp.damage(damage);
					if (!comp.isAlive()) {
						shooter.setKills(shooter.getKills() + 1);
					}
					delete(level);
				}
				else if (comp == null && collided.isSolid()) {
					delete(level);
				}
			}
		}
	}

	public CharacterComponent getShooter() {
		return shooter;
	}

	public void setShooter(CharacterComponent shooter) {
		this.shooter = shooter;
	}

}
