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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((statistics == null) ? 0 : statistics.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		GameUser other = (GameUser) obj;
		if (statistics == null) {
			if (other.statistics != null)
				return false;
		} else if (!statistics.equals(other.statistics))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}
