package at.jojokobi.llamarama.savegame;

public class GameStatistic implements Cloneable {
	
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

}
