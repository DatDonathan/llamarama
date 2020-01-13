package at.jojokobi.llamarama.entities.ai;

import java.util.Random;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class AdvancedPathFinder implements PathFinder {
	
	private boolean xPriority = false;
	private boolean clockwise = new Random().nextBoolean();
	private double changeTimer = Math.random() * 15;

	@Override
	public Vector3D findMotion(Vector3D goal, double speed, Level level, double delta, GameObject obj, CharacterComponent ch) {
		Vector3D motion = new Vector3D();
		// Direction
		if (xPriority) {
			if (goal.getX() - obj.getX() < -2) {
				motion.setX(-1);
			} else if (goal.getX() - obj.getX() > 2) {
				motion.setX(1);
			} else if (goal.getZ() - obj.getZ() > 2) {
				motion.setZ(1);
			} else if (goal.getZ() - obj.getZ() < -2) {
				motion.setZ(-1);
			}
		} else {
			if (goal.getZ() - obj.getZ() > 2) {
				motion.setZ(1);
			} else if (goal.getZ() - obj.getZ() < -2) {
				motion.setZ(-1);
			}
			else if (goal.getX() - obj.getX() < -2) {
				motion.setX(-1);
			} else if (goal.getX() - obj.getX() > 2) {
				motion.setX(1);
			}
		}
		// Rotate if walls are nearby
		int count = 0;
		while (obj.getObjectsInDirection(level, motion, speed, GameObject.class).stream().anyMatch(o -> o.isSolid()) && count < 3) {
			if (clockwise) {
				double x = motion.getX();
				motion.setX(motion.getZ());
				motion.setZ(-x);
			}
			else {
				double x = motion.getX();
				motion.setX(-motion.getZ());
				motion.setZ(x);
			}
			if (Math.abs(motion.getX()) > 0) {
				xPriority = false;
			}
			else {
				xPriority = true;
			}
			count++;
		}

		motion.normalize().multiply(speed);
		changeTimer -= delta;
		if (changeTimer <= 0) {
			clockwise = !clockwise;
			changeTimer = Math.random() * 15;
		}
		
		return motion;
	}

}
