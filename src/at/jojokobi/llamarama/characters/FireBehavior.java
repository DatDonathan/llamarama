package at.jojokobi.llamarama.characters;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.Weapon;

public interface FireBehavior {
	
	public int shoot (GameObject obj, CharacterComponent comp, WeaponType type, Weapon weapon, Level level);
	
}
