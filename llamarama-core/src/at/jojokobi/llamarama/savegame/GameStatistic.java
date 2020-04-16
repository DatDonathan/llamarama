package at.jojokobi.llamarama.savegame;

import at.jojokobi.donatengine.serialization.structured.SerializedData;
import at.jojokobi.donatengine.serialization.structured.StructuredSerializable;

public class GameStatistic implements Cloneable, StructuredSerializable {
	
	private int kills;
	private int deaths;
	private int highscore;
	
	
	public GameStatistic() {
		this(0, 0, 0);
	}
		
	public GameStatistic(int kills, int deaths, int highscore) {
		super();
		this.kills = kills;
		this.deaths = deaths;
		this.highscore = highscore;
	}

	public int getKills() {
		return kills;
	}
	public void setKills(int kills) {
		this.kills = kills;
	}
	
	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getHighscore() {
		return highscore;
	}

	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}

	@Override
	public GameStatistic clone() {
		return new GameStatistic(kills, deaths, highscore);
	}

	@Override
	public void serialize(SerializedData data) {
		data.put("kills", kills);
		data.put("deaths", deaths);
		data.put("highscore", highscore);
	}

	@Override
	public void deserialize(SerializedData data) {
		kills = data.getInt("kills");
		deaths = data.getInt("deaths");
		highscore = data.getInt("highscore");
	}

}
