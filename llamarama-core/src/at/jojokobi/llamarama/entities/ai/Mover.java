package at.jojokobi.llamarama.entities.ai;

import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;

public interface Mover {
	
	public void move (GameObject obj, Vector3D motion);

}
