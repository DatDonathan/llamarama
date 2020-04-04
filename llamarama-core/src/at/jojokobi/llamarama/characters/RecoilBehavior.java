package at.jojokobi.llamarama.characters;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.Weapon;

public class RecoilBehavior implements FireBehavior{
	
	private FireBehavior behavior;
	private double strength;

	public RecoilBehavior(FireBehavior behavior, double strength) {
		super();
		this.behavior = behavior;
		this.strength = strength;
	}

	@Override
	public int shoot(GameObject obj, CharacterComponent comp, WeaponType type, Weapon weapon, Level level) {
		Vector3D motion = comp.getDirection().getMotion().multiply(-comp.getCharacter().getSpeed() * (1 + strength));
		obj.setxMotion(motion.getX());
		obj.setyMotion(motion.getY());
		obj.setzMotion(motion.getZ());
		return behavior.shoot(obj, comp, type, weapon, level);
	}

	@Override
	public boolean willHit(GameObject obj, CharacterComponent comp, GameObject target, Level level) {
		return behavior.willHit(obj, comp, target, level);
	}

}
