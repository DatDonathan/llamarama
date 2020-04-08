package at.jojokobi.llamarama.entities;

import java.util.HashSet;
import java.util.Set;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;

public class Hook extends GameObject{
	
	private GameObject shooter;
	private Vector3D motion;
	private double distance;
	private double currDistance;
	private double speed;
	
	private Set<GameObject> attached = new HashSet<>();

	public Hook(double x, double y, double z, String area, GameObject shooter, Vector3D motion, double speed, double distance) {
		super(x, y, z, area, "bullet.hook");
		motion.normalize().multiply(speed);
		this.motion = motion;
		this.speed = speed;
		this.distance = distance;
		this.shooter = shooter;
		
		setCollideSolid(true);
	}
	

	@Override
	public void hostUpdate(Level level, UpdateEvent event) {
		super.hostUpdate(level, event);
		//Move or turn
		currDistance += getMotion().multiply(event.getDelta()).length();
		if (currDistance < distance) {
			setxMotion(motion.getX());
			setyMotion(motion.getY());
			setzMotion(motion.getZ());
		}
		else {
			Vector3D motion = shooter.getPositionVector().subtract(getPositionVector()).normalize().multiply(speed);
			setxMotion(motion.getX());
			setyMotion(motion.getY());
			setzMotion(motion.getZ());
		}
		//Delete when hitting player
		if (currDistance >= distance && (isColliding(shooter) || currDistance >= distance * 4)) {
			delete(level);
		}
		//Attach objects
		for (GameObject obj : getCollided(level)) {
			if (!obj.isSolid() && obj != shooter) {
				attached.add(obj);
				if (currDistance < distance) {
					currDistance = distance;
				}
			}
		}
		//Move Attached objects
		for (GameObject obj : attached) {
			obj.setX(getX());
			obj.setY(getY());
			obj.setZ(getZ());
			obj.setArea(getArea());
		}
	}

}
