package at.jojokobi.llamarama.entities.ai;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class DirectPathFinder implements PathFinder {

	@Override
	public Vector3D findMotion(Vector3D goal, double speed, Level level, double delta, GameObject obj, CharacterComponent ch) {
		Vector3D pos = obj.getPosition();
		Vector3D dir = goal.clone().subtract(pos);
		dir.setY(0);
		if (Math.abs(dir.getZ()) > 1 && obj.getObjectsInDirection(level, dir.clone().add(-dir.getX(), 0, 0), Math.abs(dir.getZ()), GameObject.class).stream().allMatch(o -> !o.isSolid())) {
			dir.setX(0);
		}
		else if (Math.abs(dir.getX()) > 1) {
			dir.setZ(0);
		}
		else {
			dir.setX(0);
			dir.setZ(0);
		}
		dir.normalize().multiply(speed);
		
		return dir;
	}

}