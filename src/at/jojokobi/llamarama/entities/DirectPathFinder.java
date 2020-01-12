package at.jojokobi.llamarama.entities;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;

public class DirectPathFinder implements PathFinder {

	@Override
	public Vector3D findMotion(Vector3D goal, double speed, Level level, GameObject obj, CharacterComponent ch) {
		Vector3D pos = obj.getPosition().add((Vector3D) obj.getSize().multiply(0.5));
		Vector3D dir = goal.clone().subtract(pos);
		dir.setY(0);
		if (Math.abs(dir.getZ()) > 5 && obj.getObjectsInDirection(level, dir.clone().add(-dir.getX(), 0, 0), Math.abs(dir.getZ()), GameObject.class).stream().allMatch(o -> !o.isSolid())) {
			dir.setX(0);
		}
		else if (Math.abs(dir.getX()) > 5) {
			dir.setZ(0);
		}
		dir.normalize().multiply(speed);
		
		return dir;
	}

}