package at.jojokobi.llamarama.entities;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.characters.DamageCause;

public class Toy extends AbstractBullet {
	
	private double strength = 1.8;
	private double maxDst = 8.0;
	private Vector3D start;

	public Toy(double x, double y, double z, String area, CharacterComponent shooter, int damage, Vector3D motion, double speed, double maxDst) {
		super(x, y, z, area, "bullet.toy", DamageCause.SHOT, shooter, damage, motion, speed);
		this.maxDst = maxDst;
	}
	
	public Toy() {
		this(0, 0, 0, "", null, 0, new Vector3D(), 37.5, 8);
	}
	
	@Override
	public void onSpawn(Level level) {
		super.onSpawn(level);
		start = getPositionVector();
	}
	
	@Override
	public void hostUpdate(Level level, UpdateEvent event) {
		super.hostUpdate(level, event);
		Vector3D dst = getPositionVector().subtract(start);
		if (maxDst <= dst.length()) {
			delete(level);
		}
	}

	@Override
	protected void damage(Level level, DamageCause cause, GameObject obj, CharacterComponent comp) {
		super.damage(level, cause, obj, comp);
		comp.attemptKnockOut(strength);
	}

}
