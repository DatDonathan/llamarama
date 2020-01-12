package at.jojokobi.llamarama.entities.ai;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;

public interface PathFinder {
	
	public Vector3D findMotion (Vector3D goal, double speed, Level level, GameObject obj, CharacterComponent ch);
	
}
