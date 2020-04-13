package at.jojokobi.llamarama.characters;

import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.llamarama.entities.CharacterComponent;

public interface Ability {

	public void update (Level level, GameObject object, double delta, CharacterComponent character);
	
	public boolean use (Level level, GameObject object, double delta, CharacterComponent character);
	
	public double getUsePriority (Level level, GameObject object, CharacterComponent character);
	
	public void render (Level level, GameObject object, CharacterComponent character, List<RenderData> data, Camera cam);
	
	public double getCooldown ();
	
}
