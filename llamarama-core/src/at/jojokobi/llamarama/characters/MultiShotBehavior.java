package at.jojokobi.llamarama.characters;


import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.bullets.Bullet;

public class MultiShotBehavior extends AbstractMultiShotBehavior {
	
	private int amount;
	private double spread = 0.4;
	
	public MultiShotBehavior(int amount, double speed) {
		super(speed);
		this.amount = amount;
	}

	public MultiShotBehavior(int amount) {
		this(amount, 37.5);
	}
	
	protected int determineAmount (CharacterComponent comp) {
		return amount;
	}

	@Override
	protected void spread(Vector3D pos, Vector3D motion, int number) {
//		double rotation = Math.toRadians((ThreadLocalRandom.current().nextGaussian() - 0.5) * 2 * spread);
//		System.out.println(Math.toDegrees(rotation));
//		double x = motion.getX();
//		motion.setX(x * Math.cos(rotation) + motion.getZ() * Math.sin(rotation));
//		motion.setZ(x * Math.sin(rotation) + motion.getZ() * Math.cos(rotation));
		motion.add(Math.random() * spread - spread/2, 0, Math.random() * spread - spread/2);
	}

	@Override
	public GameObject createBullet(int damage, CharacterComponent shooter, Vector3D pos, String area, Vector3D motion,
			double speed) {
		return new Bullet(pos.getX(), pos.getY(), pos.getZ(), area, shooter, damage, motion, speed);
	}

	@Override
	protected double getSize() {
		return 0.5;
	}

}
