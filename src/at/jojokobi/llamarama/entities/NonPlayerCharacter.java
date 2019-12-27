package at.jojokobi.llamarama.entities;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.characters.CharacterTypeProvider;
import javafx.scene.canvas.GraphicsContext;

public class NonPlayerCharacter extends CharacterInstance {

	public NonPlayerCharacter(double x, double y, double z, String area, CharacterType character) {
		super(x, y, z, area, character);
	}
	
	public NonPlayerCharacter() {
		this(0, 0, 0, "", CharacterTypeProvider.getCharacterTypes().get("Corporal"));
	}
	
	@Override
	public void render(GraphicsContext ctx, Camera cam, Level level) {
		if (getComponent(CharacterComponent.class).isAlive()) {
			super.render(ctx, cam, level);
		}
	}
	
}
