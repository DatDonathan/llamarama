package at.jojokobi.llamarama.characters;

import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.Bomb;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class BombBehavior extends AbstractMultiShotBehavior {
	
	public BombBehavior(double speed) {
		super(speed);
	}
	
	public BombBehavior() {
		this(37.5);
	}

	@Override
	public GameObject createBullet(int damage, CharacterComponent shooter, Vector3D pos, String area, Vector3D motion,
			double speed) {
		return new Bomb(pos.getX(), pos.getY(), pos.getZ(), area, shooter, damage, motion, speed);
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

}
