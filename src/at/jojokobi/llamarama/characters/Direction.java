package at.jojokobi.llamarama.characters;

import at.jojokobi.donatengine.util.Vector3D;

public enum Direction {
	RIGHT, LEFT, UP, DOWN;
	
	public Vector3D getMotion () {
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
}
