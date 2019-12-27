package at.jojokobi.llamarama.entities;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.rendering.Image2DModel;
import at.jojokobi.donatengine.rendering.RenderModel;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.LlamaramaApplication;
import javafx.scene.image.Image;

public class Bullet extends GameObject {
	
	public static final Image SPIT = new Image(Bullet.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "spit.png"));
	public static final RenderModel SPIT_MODEL = new Image2DModel(SPIT);
	
	private CharacterComponent shooter;
	private int damage;

	public Bullet(double x, double y, double z, String area, CharacterComponent shooter, int damage, Vector3D motion) {
		super(x, y, z, area, SPIT_MODEL);
		this.shooter = shooter;
		this.damage = damage;
		motion.multiply(1200);
		setxMotion(motion.getX());
		setyMotion(motion.getY());
		setzMotion(motion.getZ());
	}
	
	public Bullet() {
		this(0, 0, 0, "", null, 0, new Vector3D());
	}
	
	@Override
	public void hostUpdate(Level level, LevelHandler handler, Camera camera, double delta) {
		super.hostUpdate(level, handler, camera, delta);
		for (GameObject collided : getCollided(level)) {
			CharacterComponent comp = collided.getComponent(CharacterComponent.class);
			if (comp != null && comp != shooter && comp.isAlive()) {
				comp.setHp(comp.getHp() - damage);
				if (!comp.isAlive()) {
					shooter.setKills(shooter.getKills() + 1);
				}
				delete(level);
			}
		}
	}

}
