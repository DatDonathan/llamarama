package at.jojokobi.llamarama.characters;

public class WeaponType {

	private double fireDelay;
	private int damage;
	private int maxBullets;
	private FireBehavior fireBehavior;
	
	public WeaponType(double fireDelay, int damage, int maxBullets, FireBehavior fireBehavior) {
		super();
		this.fireDelay = fireDelay;
		this.damage = damage;
		this.maxBullets = maxBullets;
		this.fireBehavior = fireBehavior;
	}

	public double getFireDelay() {
		return fireDelay;
	}

	public int getDamage() {
		return damage;
	}

	public int getMaxBullets() {
		return maxBullets;
	}

	public FireBehavior getFireBehavior() {
		return fireBehavior;
	}
	
}