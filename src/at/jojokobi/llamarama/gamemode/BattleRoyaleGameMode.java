package at.jojokobi.llamarama.gamemode;

import java.util.Map;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.levels.GameLevel.GameComponent;

public class BattleRoyaleGameMode implements GameMode {
	
	private int minPlayers = 8;
	private double maxLobbyTime = 60.0;
	

	public BattleRoyaleGameMode(int minPlayers, double maxLobbyTime) {
		super();
		this.minPlayers = minPlayers;
		this.maxLobbyTime = maxLobbyTime;
	}

	@Override
	public boolean canStartGame(Level level, Map<Long, CharacterType> players, GameComponent comp) {
		return players.size() >= minPlayers || comp.getTime() > maxLobbyTime;
	}

	@Override
	public boolean canEndGame(Level level, GameComponent comp) {
		int count = 0;
		for (GameObject obj : level.getObjectsWithComponent(CharacterComponent.class)) {
			if (obj.getComponent(CharacterComponent.class).isAlive()) {
				count++;
			}
		}
		return count <= 1;
	}

	@Override
	public void startGame(Level level, GameComponent comp) {
		for (GameObject obj : level.getObjectsWithComponent(CharacterComponent.class)) {
			obj.getX();
			//TODO spread players
		}
	}

	@Override
	public void update(Level level, GameComponent comp, double delta) {
		
	}

	@Override
	public void endGame(Level level, GameComponent comp) {
		
	}

	@Override
	public Winner determineWinner(Level level, GameComponent comp) {
		for (GameObject obj : level.getObjectsWithComponent(CharacterComponent.class)) {
			if (obj.getComponent(CharacterComponent.class).isAlive()) {
				return new SingleWinner(obj.getComponent(CharacterComponent.class));
			}
		}
		return null;
	}

	@Override
	public void addComponents(Level level) {
		
	}

}
