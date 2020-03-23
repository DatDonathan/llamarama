package at.jojokobi.llamarama.entities.ai;

import java.util.Random;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelBoundsComponent;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class AdvancedPathFinder implements PathFinder {
	
	private boolean xPriority = false;
	private boolean clockwise = new Random().nextBoolean();
	private double changeTimer = Math.random() * 10 + 5;

	@Override
	public Vector3D findMotion(Vector3D goal, double speed, Level level, double delta, GameObject obj, CharacterComponent ch) {
		Vector3D motion = new Vector3D();
		// Direction
		if (xPriority) {
			if (goal.getX() - obj.getX() < -0.1) {
				motion.setX(-1);
			} else if (goal.getX() - obj.getX() > 0.1) {
				motion.setX(1);
			} else if (goal.getZ() - obj.getZ() > 0.1) {
				motion.setZ(1);
			} else if (goal.getZ() - obj.getZ() < -0.1) {
				motion.setZ(-1);
			}
		} else {
			if (goal.getZ() - obj.getZ() > 0.1) {
				motion.setZ(1);
			} else if (goal.getZ() - obj.getZ() < -0.1) {
				motion.setZ(-1);
			}
			else if (goal.getX() - obj.getX() < -0.1) {
				motion.setX(-1);
			} else if (goal.getX() - obj.getX() > 0.1) {
				motion.setX(1);
			}
		}
		// Rotate if walls are nearby
		int count = 0;
		while (!canMove(level, obj, motion, speed) && count < 3) {
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
			changeTimer = Math.random() * 10 + 5;
		}
		
		return motion;
	}
	
	private boolean canMove (Level level, GameObject obj, Vector3D motion, double speed) {
		boolean canMove = true;
		LevelBoundsComponent bounds = level.getComponent(LevelBoundsComponent.class);
		if (bounds != null) {
			canMove = !bounds.nearBounds(obj);
		}
		return canMove && obj.getCollidablesInDirection(level, motion, speed).stream().allMatch(o -> !o.isSolid());
	}

}
