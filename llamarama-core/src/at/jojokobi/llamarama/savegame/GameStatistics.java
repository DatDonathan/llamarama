package at.jojokobi.llamarama.savegame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.serialization.structured.SerializedData;
import at.jojokobi.donatengine.serialization.structured.StructuredSerializable;
import at.jojokobi.donatengine.util.Pair;

public class GameStatistics implements StructuredSerializable{
	
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

	@Override
	public void serialize(SerializedData data) {
		List<StatEntry> entries = new ArrayList<>();
		for (var e : stats.entrySet()) {
			entries.add(new StatEntry(e.getKey().getKey(), e.getKey().getValue(), e.getValue()));
		}
		data.put("stats", entries);
	}

	@Override
	public void deserialize(SerializedData data) {
		List<StatEntry> entires = data.getList("stats", StatEntry.class);
		for (StatEntry entry : entires) {
			stats.put(new Pair<>(entry.category, entry.online),  entry.statistic);
		}
	}

	public static class StatEntry implements StructuredSerializable{
		
		public StatCategory category;
		public boolean online;
		public GameStatistic statistic;
		
		
		public StatEntry() {
			this(null, false, null);
		}
		
		public StatEntry(StatCategory category, boolean online, GameStatistic statistic) {
			super();
			this.category = category;
			this.online = online;
			this.statistic = statistic;
		}
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stats == null) ? 0 : stats.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameStatistics other = (GameStatistics) obj;
		if (stats == null) {
			if (other.stats != null)
				return false;
		} else if (!stats.equals(other.stats))
			return false;
		return true;
	}
	
}
