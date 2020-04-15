package at.jojokobi.llamarama.savegame;


public class GameUser {

	private String username;
	private GameStatistics statistics;
	
	public GameUser() {
		
	}
	
	public GameUser(String username, GameStatistics statistics) {
		super();
		this.username = username;
		this.statistics = statistics;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public GameStatistics getStatistics() {
		return statistics;
	}
	public void setStatistics(GameStatistics statistics) {
		this.statistics = statistics;
	}
	
}
