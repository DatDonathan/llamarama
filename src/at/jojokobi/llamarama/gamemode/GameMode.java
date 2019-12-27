package at.jojokobi.llamarama.gamemode;

import java.util.Map;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.levels.GameLevel.GameComponent;

public interface GameMode {

	public boolean canStartGame (Level level, Map<Long, CharacterType> players, GameComponent comp);
	
	public boolean canEndGame (Level level, GameComponent comp);
	
	public void update ();
	
	public Winner determineWinner (Level level, GameComponent comp);
	
}
