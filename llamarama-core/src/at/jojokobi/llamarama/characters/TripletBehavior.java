package at.jojokobi.llamarama.characters;

import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.Bullet;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class TripletBehavior extends AbstractMultiShotBehavior{
	
	private int amount;

	public TripletBehavior(int amount, double speed) {
		super(speed);
		this.amount = amount;
	}

	public TripletBehavior(int amount) {
		this(amount, 37.5);
	}

	@Override
	public GameObject createBullet(int damage, CharacterComponent shooter, Vector3D pos, String area, Vector3D motion,
			double speed) {
		return new Bullet(pos.getX(), pos.getY(), pos.getZ(), area, shooter, damage, motion, speed);
	}

	@Override
	protected int determineAmount(CharacterComponent comp) {
		return (int) Math.ceil((double) comp.getHp()/comp.getCharacter().getMaxHp() * amount);
	}

	@Override
	protected void spread(Vector3D pos, Vector3D motion, int number) {
		if (motion.getZ() != 0) {
			pos.add(Math.random() - 0.5, 0, 0);
		}
		else if (motion.getX() != 0) {
			pos.add(0, 0, Math.random() - 0.5);
		}
	}

	@Override
	protected double getSize() {
		return 0.5;
	}
	
	

}
