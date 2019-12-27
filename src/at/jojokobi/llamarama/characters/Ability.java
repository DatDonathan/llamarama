package at.jojokobi.llamarama.characters;

import at.jojokobi.donatengine.objects.Camera;
import javafx.scene.canvas.GraphicsContext;

public interface Ability {

	public void update (Character character);
	
	public void use (Character character);
	
	public boolean shouldUse (Character character);
	
	public void render (Character character, GraphicsContext ctx, Camera cam);
	
	public double getCooldown ();
	
}
