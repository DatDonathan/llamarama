package at.jojokobi.llamarama.savegame;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.donatengine.serialization.structured.SerializedData;
import at.jojokobi.donatengine.serialization.structured.StructuredSerializable;
import at.jojokobi.donatengine.util.Pair;

public class GameStatistics {
	
	private Map<Pair<StatCategory, Boolean>, GameStatistic> stats = new HashMap<>();
	
	public GameStatistic getStat (StatCategory[] categories, boolean... onlineStates) {
		GameStatistic stat = new GameStatistic();
		for (StatCategory category : categories) {
			for (boolean online : onlineStates) {
				GameStatistic s = stats.get(new Pair<>(category, online));
				if (s != null) {
					stat.setDeaths(stat.getDeaths() + s.getDeaths());
					stat.setKills(stat.getKills() + s.getKills());
					stat.setHighscore(Math.max(stat.getHighscore(), s.getHighscore()));
				}
			}
		}
		return stat;
	}
	
	public void putStat (GameStatistic stat, StatCategory category, boolean... onlineStates) {
		for (boolean online : onlineStates) {
			stats.put(new Pair<>(category, online), stat.clone());
		}
	}

	public static class StatEntry implements StructuredSerializable{
		
		public StatCategory category;
		public boolean online;
		public GameStatistic statistic;
		@Override
		public void serialize(SerializedData data) {
			data.put("category", category);
			data.put("online", online);
			data.put("statistic", statistic);
		}
		@Override
		public void deserialize(SerializedData data) {
			category = data.getEnum("category", StatCategory.class);
			online = data.getBoolean("online");
			statistic = data.getObject("statistic", GameStatistic.class);
		}
		
	}
	
}
