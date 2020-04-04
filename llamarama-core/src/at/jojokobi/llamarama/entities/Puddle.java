package at.jojokobi.llamarama.entities;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.llamarama.characters.DamageCause;

public class Puddle extends CollisionDamager {
	
	private double timeToLive = 8.0;

	public Puddle(double x, double y, double z, String area, CharacterComponent shooter, int damage) {
		super(x, y, z, area, "bullet.puddle", shooter, damage, DamageCause.PUDDLE);
	}
	
	public Puddle() {
		this(0, 0, 0, "", null, 0);
	}
	
	@Override
	public void hostUpdate(Level level, UpdateEvent event) {
		super.hostUpdate(level, event);
		timeToLive -= event.getDelta();
		if (timeToLive < 0) {
			delete(level);
		}
	}

}
