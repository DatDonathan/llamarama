package at.jojokobi.llamarama.entities;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;

public interface PathFinder {
	
	public Vector3D findMotion (Vector3D goal, double speed, Level level, GameObject obj, CharacterComponent ch);
	
}
