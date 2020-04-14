package at.jojokobi.llamarama.entities.bullets;

import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.characters.DamageCause;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class Zap extends AbstractBullet {
//	
//	public static final Image SPIT = new Image(Bullet.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "spit.png"));
//	public static final RenderModel SPIT_MODEL = new Image2DModel(SPIT);
	
	public Zap() {
		this(0, 0, 0, "", null, 0, new Vector3D(), 37.5);
	}

	public Zap(double x, double y, double z, String area, CharacterComponent shooter, int damage, Vector3D motion, double speed) {
		super(x, y, z, area, "bullet.zap", DamageCause.SHOT, shooter, damage, motion, speed);
		setWidth(0.5);
		setHeight(0.5);
		setLength(0.5);
	}

}
