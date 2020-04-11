package at.jojokobi.llamarama.entities;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.llamarama.characters.DamageCause;

public interface Damagable {
	
	public boolean isAlive ();
	
	public int getHp ();
	
	public void heal (int amount);
	
	public void damage (Level level, CharacterComponent damager, int amount, DamageCause cause);
	
	public void attemptKnockOut (double strength);

}
