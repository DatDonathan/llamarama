package at.jojokobi.llamarama.gamemode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelBoundsComponent;
import at.jojokobi.donatengine.level.TileMapParser;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.gamemode.GameLevel.GameComponent;
import at.jojokobi.llamarama.gamemode.GameLevel.PlayerInformation;
import at.jojokobi.llamarama.items.HealingGrass;
import at.jojokobi.llamarama.items.SpitBucket;
import at.jojokobi.llamarama.maps.CSVLoadedMap;
import at.jojokobi.llamarama.maps.GameMap;

public class EndlessGameMode implements GameMode {
	
	private int minPlayers = 8;
	private double maxLobbyTime = 60.0;
	

	public EndlessGameMode(int minPlayers, double maxLobbyTime) {
		super();
		this.minPlayers = minPlayers;
		this.maxLobbyTime = maxLobbyTime;
	}

	@Override
	public boolean canStartGame(Level level, Map<Long, PlayerInformation> players, GameComponent comp) {
		return players.size() >= minPlayers || comp.getTime() > maxLobbyTime;
	}

	@Override
	public boolean canEndGame(Level level, GameComponent comp) {
		return comp.getTime() >= 3 * 60;
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
		LevelBoundsComponent bounds = level.getComponent(LevelBoundsComponent.class);
		for (GameObject obj : level.getObjectsWithComponent(CharacterComponent.class)) {
			CharacterComponent ch = obj.getComponent(CharacterComponent.class);
			if (!ch.isAlive()) {
				ch.revive();
				obj.setX(bounds.getPos().getX() + Math.random() * bounds.getSize().getX());
				obj.setZ(bounds.getPos().getZ() + Math.random() * bounds.getSize().getZ());
				ch.setKills(ch.getKills() - 1);
			}
		}
	}

	@Override
	public void endGame(Level level, GameComponent comp) {
		
	}

	@Override
	public Winner determineWinner(Level level, GameComponent comp) {
		List<GameObject> objs = level.getObjectsWithComponent(CharacterComponent.class);
		objs.sort((l, r) ->  {
			CharacterComponent cl = l.getComponent(CharacterComponent.class);
			CharacterComponent cr = r.getComponent(CharacterComponent.class);
			return Integer.compare(cr.getKills() , cl.getKills());
		});
		return new SingleWinner(objs.get(0).getComponent(CharacterComponent.class));
	}

	@Override
	public List<GameMap> getPossibleMaps() {
		return Arrays.asList(new CSVLoadedMap(TileMapParser.loadTilemap(getClass().getResourceAsStream("/assets/maps/online.csv"), 128)));
	}

	@Override
	public List<GameEffect> createEffects() {
		return Arrays.asList(new ItemSpawnerEffect(Arrays.asList(HealingGrass::new, SpitBucket::new), 10, 0.0005, 32));
	}

	@Override
	public int getMaxPlayers() {
		return minPlayers;
	}

}
