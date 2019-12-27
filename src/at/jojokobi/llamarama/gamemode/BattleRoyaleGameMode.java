package at.jojokobi.llamarama.gamemode;

import java.util.Map;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.levels.GameLevel.GameComponent;

public class BattleRoyaleGameMode implements GameMode {

	@Override
	public boolean canStartGame(Level level, Map<Long, CharacterType> players, GameComponent comp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canEndGame(Level level, GameComponent comp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void startGame(Level level, GameComponent comp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Level level, GameComponent comp, double delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endGame(Level level, GameComponent comp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Winner determineWinner(Level level, GameComponent comp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addComponents(Level level) {
		// TODO Auto-generated method stub
		
	}

}
