package at.jojokobi.llamarama.maps;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.util.Vector3D;

public interface GameMap {
	
	public void generate (Level level, Vector3D pos, String area);
	
	public Vector3D getSize ();

}
