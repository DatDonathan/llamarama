package at.jojokobi.llamarama.gamemode;

import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.serialization.binary.BinarySerializable;
import at.jojokobi.llamarama.gamemode.GameLevel.GameComponent;
import at.jojokobi.llamarama.gamemode.GameLevel.PlayerInformation;
import at.jojokobi.llamarama.maps.GameMap;

public interface GameMode extends BinarySerializable {

	public boolean canStartGame (Level level, Map<Long,  PlayerInformation> players, GameComponent comp);
	
	public boolean canEndGame (Level level, GameComponent comp);
	
	public void startGame (Level level, GameComponent comp);
	
	public void update (Level level, GameComponent comp, double delta);
	
	public void endGame (Level level, GameComponent comp);
	
	public List<ScoreboardEntry> getScoreboardEntries(Level level, GameComponent comp);
	
	public Winner determineWinner (Level level, GameComponent comp);
	
	public int getMaxPlayers ();
	
	public List<GameEffect> createEffects ();
	
	public List<GameMap> getPossibleMaps ();
	
	public String getName();
	
}
