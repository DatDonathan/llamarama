package at.jojokobi.llamarama.characters;

import at.jojokobi.donatengine.util.Vector3D;

public enum Direction {
	RIGHT, LEFT, UP, DOWN;
	
	public Vector3D toVector () {
		Vector3D vec = new Vector3D();
		switch (this) {
		case DOWN:
			vec.setZ(1);
			break;
		case LEFT:
			vec.setX(-1);
			break;
		case RIGHT:
			vec.setX(1);
			break;
		case UP:
			vec.setZ(-1);
			break;
		}
		return vec;
	}
	
	public static Direction fromVector (Vector3D motion) {
		Direction dir = null;
		if (motion.getX() < 0) {
			dir = Direction.LEFT;
		}
		else if (motion.getX() > 0) {
			dir = Direction.RIGHT;
		}
		else if (motion.getZ() < 0) {
			dir = Direction.UP;
		}
		else if (motion.getZ() > 0) {
			dir = Direction.DOWN;
		}
		return dir;
	}
	
	public Direction opposite () {
		Direction opp = null;
		switch (this) {
		case DOWN:
			opp = UP;
			break;
		case LEFT:
			opp = RIGHT;
			break;
		case RIGHT:
			opp = LEFT;
			break;
		case UP:
			opp = DOWN;
			break;
		}
		return opp;
	}
	
}
