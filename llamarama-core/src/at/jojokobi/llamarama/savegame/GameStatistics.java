package at.jojokobi.llamarama.savegame;

import java.util.HashMap;
import java.util.Map;

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

}