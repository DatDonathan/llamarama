package at.jojokobi.llamarama.entities.bullets;

public interface BulletInteractor {
	
	public boolean block (CollisionDamager bullet);
	
	public boolean canBeDamaged (CollisionDamager bullet);
	
}
