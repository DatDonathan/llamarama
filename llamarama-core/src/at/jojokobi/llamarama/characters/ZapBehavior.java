package at.jojokobi.llamarama.characters;

import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.bullets.Zap;

public class ZapBehavior extends AbstractMultiShotBehavior {
	
	public ZapBehavior(double speed) {
		super(speed);
	}
	
	public ZapBehavior() {
		this(37.5);
	}

	@Override
	public GameObject createBullet(int damage, CharacterComponent shooter, Vector3D pos, String area, Vector3D motion,
			double speed) {
		return new Zap(pos.getX(), pos.getY(), pos.getZ(), area, shooter, damage, motion, speed);
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
		return 0.5;
	}

}
