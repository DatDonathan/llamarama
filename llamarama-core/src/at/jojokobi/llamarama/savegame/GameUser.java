package at.jojokobi.llamarama.savegame;

import at.jojokobi.donatengine.serialization.structured.SerializedData;
import at.jojokobi.donatengine.serialization.structured.StructuredSerializable;

public class GameUser implements StructuredSerializable {

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

	@Override
	public void serialize(SerializedData data) {
		data.put("username", username);
		data.put("statistics", statistics);
	}

	@Override
	public void deserialize(SerializedData data) {
		username = data.getString(username);
		statistics = data.getObject(username, GameStatistics.class);
	}
	
}
