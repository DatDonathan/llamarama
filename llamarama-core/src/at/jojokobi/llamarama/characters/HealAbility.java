package at.jojokobi.llamarama.characters;

import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class HealAbility implements Ability {

	@Override
	public void update(Level level, GameObject object, double delta, CharacterComponent character) {
		
	}

	@Override
	public boolean use(Level level, GameObject object, double delta, CharacterComponent character) {
		character.heal(30);
		character.knockOut(5.0);
		return true;
	}

	@Override
	public double getUsePriority(Level level, GameObject object, CharacterComponent character) {
		return 1 - (double) character.getHp()/character.getCharacter().getMaxHp();
	}

	@Override
	public void render(Level level, GameObject object, CharacterComponent character, List<RenderData> data,
			Camera cam) {
		
	}

	@Override
	public double getCooldown() {
		return 9.0;
	}

}
