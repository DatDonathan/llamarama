package at.jojokobi.llamarama.gamemode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.TileMapParser;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.serialization.SerializationWrapper;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.PlayerCharacter;
import at.jojokobi.llamarama.gamemode.GameLevel.GameComponent;
import at.jojokobi.llamarama.gamemode.GameLevel.PlayerInformation;
import at.jojokobi.llamarama.items.HealingGrass;
import at.jojokobi.llamarama.items.SpitBucket;
import at.jojokobi.llamarama.maps.CSVLoadedMap;
import at.jojokobi.llamarama.maps.GameMap;

public class BattleRoyaleGameMode implements GameMode {
	
	private int minPlayers = 16;
	private double maxLobbyTime = 60.0;
	
	public BattleRoyaleGameMode() {
		
	}

	public BattleRoyaleGameMode(int minPlayers, double maxLobbyTime) {
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
		int count = 0;
		for (GameObject obj : level.getObjectsWithComponent(CharacterComponent.class)) {
			if (obj.getComponent(CharacterComponent.class).isAlive()) {
				count++;
			}
		}
		return count <= 1|| level.getInstances(PlayerCharacter.class).stream().allMatch(o -> !o.getComponent(CharacterComponent.class).isAlive());
	}

	@Override
	public void startGame(Level level, GameComponent comp) {
		
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

	@Override
	public String getName() {
		return "Battle Royale";
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeInt(minPlayers);
		buffer.writeDouble(maxLobbyTime);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		minPlayers = buffer.readInt();
		maxLobbyTime = buffer.readDouble();
	}

}
