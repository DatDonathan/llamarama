package at.jojokobi.llamarama.characters;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.llamarama.entities.CharacterComponent;
import javafx.scene.canvas.GraphicsContext;

public interface Ability {

	public void update (Level level, GameObject object, double delta, CharacterComponent character);
	
	public boolean use (Level level, GameObject object, double delta, CharacterComponent character);
	
	public boolean shouldUse (Level level, GameObject object, CharacterComponent character);
	
	public void render (Level level, GameObject object, CharacterComponent character, GraphicsContext ctx, Camera cam);
	
	public double getCooldown ();
	
}
