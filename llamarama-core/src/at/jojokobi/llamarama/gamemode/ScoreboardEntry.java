package at.jojokobi.llamarama.gamemode;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.style.Color;
import at.jojokobi.llamarama.savegame.GameUser;

public interface ScoreboardEntry {
	
	public String getName();
	
	public int getKills();
	
	public int getDeaths();
	
	public int getScore();
	
	public Color getColor ();
	
	public boolean isUser (GameUser user, Level level);

}
