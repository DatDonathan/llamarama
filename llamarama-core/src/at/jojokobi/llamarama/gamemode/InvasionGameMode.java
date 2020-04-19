package at.jojokobi.llamarama.gamemode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.PlayerComponent;
import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;
import at.jojokobi.llamarama.characters.CharacterTypeProvider;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.gamemode.GameLevel.GameComponent;
import at.jojokobi.llamarama.gamemode.GameLevel.PlayerInformation;
import at.jojokobi.llamarama.items.HealingGrass;
import at.jojokobi.llamarama.items.SpitBucket;
import at.jojokobi.llamarama.maps.CSVLoadedMap;
import at.jojokobi.llamarama.maps.GameMap;
import at.jojokobi.llamarama.savegame.StatCategory;

public class InvasionGameMode implements GameMode{
	
	public static final int[][][] ZOO_TILEMAP =
		{{{4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,4,4,4,4},
		  {4,0,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,3,1,1,1,1,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0,4},
		  {4,5,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,3,0,0,0,0,0,0,0,0,0,5,5,0,0,0,0,0,4},
		  {4,5,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,3,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		  {4,0,5,5,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0},
		  {0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0},
		  {0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,5,5,0,0},
		  {0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,5,0,0},
		  {0,0,0,0,0,0,1,1,1,1,4,4,4,4,4,4,4,0,0,0,0,0,0,4,4,4,4,4,4,4,1,1,1,1,0,0,0,0,0,0},
		  {0,0,0,0,0,0,1,1,1,1,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,1,1,1,1,0,0,0,0,0,0},
		  {0,3,0,0,0,3,1,1,1,1,4,0,5,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,4,1,1,1,1,3,0,0,3,0,0},
		  {1,1,1,1,1,1,1,1,1,1,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,1,1,1,1,1,1,1,1,1,1},
		  {1,1,1,1,1,1,1,1,1,1,4,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,1,1,1,1,1,1,1,1,1,1},
		  {1,1,1,1,1,1,1,1,1,1,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,1,1,1,1,1,1,1,1,1,1},
		  {1,1,1,1,1,1,1,1,1,1,4,0,0,0,0,5,0,0,0,0,0,0,0,0,0,0,2,2,2,4,1,1,1,1,1,1,1,1,1,1},
		  {3,0,0,3,0,0,1,1,1,1,4,5,0,0,0,0,0,0,0,0,0,0,5,0,0,0,2,2,2,4,1,1,1,1,0,3,0,0,3,0},
		  {0,0,0,0,0,0,1,1,1,1,4,0,0,0,0,5,0,0,0,0,0,0,0,0,0,0,2,2,2,4,1,1,1,1,0,0,0,0,0,0},
		  {0,0,0,0,0,0,1,1,1,1,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,1,1,1,1,0,0,0,0,0,0},
		  {0,0,5,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,5,5,0,0},
		  {0,0,5,5,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,5,0,0,0},
		  {0,0,5,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,4},
		  {4,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,4},
		  {4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		  {4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,4,4,4}}};

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public boolean canStartGame(Level level, Map<Long, PlayerInformation> players, GameComponent comp) {
		return players.size() >= getMaxPlayers();
	}

	@Override
	public boolean canEndGame(Level level, GameComponent comp) {
		return level.getObjectsWithComponent(PlayerComponent.class).stream().allMatch(o -> o.getComponent(CharacterComponent.class) == null || !o.getComponent(CharacterComponent.class).isAlive());
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
	public List<ScoreboardEntry> getScoreboardEntries(Level level, GameComponent comp, boolean all) {
		List<ScoreboardEntry> entries = new ArrayList<ScoreboardEntry>();
		for (GameObject obj : level.getObjectsWithComponent(CharacterComponent.class)) {
			CharacterComponent ch = obj.getComponent(CharacterComponent.class);
			PlayerComponent pl = obj.getComponent(PlayerComponent.class);
			entries.add(new SingleScoreboardEntry(ch, pl == null ? -1 : pl.getClient()));
		}
		entries.sort((l, r) -> Integer.compare(r.getKills(), l.getKills())); 
		return entries;
	}

	@Override
	public Winner determineWinner(Level level, GameComponent comp) {
		List<GameObject> objs = level.getObjectsWithComponent(CharacterComponent.class);
		for (Iterator<GameObject> iterator = objs.iterator(); iterator.hasNext();) {
			GameObject object = iterator.next();
			if (object.getComponent(PlayerComponent.class) == null) {
				iterator.remove();
			}
		}
		objs.sort((l, r) ->  {
			CharacterComponent cl = l.getComponent(CharacterComponent.class);
			CharacterComponent cr = r.getComponent(CharacterComponent.class);
			return Integer.compare(cr.getKills() , cl.getKills());
		});
		return new SingleWinner(objs.get(0).getComponent(CharacterComponent.class));
	}

	@Override
	public int getMaxPlayers() {
		return 1;
	}

	@Override
	public List<GameEffect> createEffects() {
		return Arrays.asList(new ItemSpawnerEffect(Arrays.asList(HealingGrass::new, SpitBucket::new), 5, 0.0025, 1), new EnemySpawnerEffect(Arrays.asList(CharacterTypeProvider.getCharacterTypes().get("Paparazzi")), 1, 10));
	}

	@Override
	public List<GameMap> getPossibleMaps() {
		return Arrays.asList(new CSVLoadedMap(ZOO_TILEMAP));
	}

	@Override
	public String getName() {
		return "Invasion Mode";
	}
	
	@Override
	public StatCategory getCategory() {
		return StatCategory.INVASION;
	}

}
