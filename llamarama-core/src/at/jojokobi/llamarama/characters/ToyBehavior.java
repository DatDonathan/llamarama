package at.jojokobi.llamarama.characters;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.bullets.Toy;

public class ToyBehavior extends AbstractMultiShotBehavior {
	
	private double maxDst = 8;
	
	public ToyBehavior(double speed) {
		super(speed);
	}
	
	public ToyBehavior() {
		this(37.5);
	}

	@Override
	public GameObject createBullet(int damage, CharacterComponent shooter, Vector3D pos, String area, Vector3D motion,
			double speed) {
		return new Toy(pos.getX(), pos.getY(), pos.getZ(), area, shooter, damage, motion, speed, maxDst);
	}

	@Override
	protected int determineAmount(CharacterComponent comp) {
		return 1;
	}

	@Override
	protected void spread(Vector3D pos, Vector3D motion, int number) {
		
	}
	
	@Override
	protected double getSize() {
		return 1;
	}
	
	@Override
	public boolean willHit(GameObject obj, CharacterComponent comp, GameObject target, Level level) {
		Vector3D dst = target.getPositionVector().subtract(obj.getPositionVector());
		return super.willHit(obj, comp, target, level) && dst.length() < maxDst;
	}

}
