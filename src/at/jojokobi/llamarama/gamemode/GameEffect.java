package at.jojokobi.llamarama.gamemode;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.llamarama.gamemode.GameLevel.GameComponent;

public interface GameEffect {
	
	public void update (Level level, GameComponent comp, double delta);

}
