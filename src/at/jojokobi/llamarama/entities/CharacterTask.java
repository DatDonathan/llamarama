package at.jojokobi.llamarama.entities;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;

public interface CharacterTask {
	
	public boolean canApply (Level level, GameObject obj, CharacterComponent ch);
	
	public Vector3D apply (Level level, GameObject obj, CharacterComponent ch, double delta);
	
	public void activate (Level level, GameObject obj, CharacterComponent ch);
	
	public void deactivate (Level level, GameObject obj, CharacterComponent ch);

}
