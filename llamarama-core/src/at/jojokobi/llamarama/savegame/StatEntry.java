package at.jojokobi.llamarama.savegame;

import at.jojokobi.donatengine.serialization.structured.SerializedData;
import at.jojokobi.donatengine.serialization.structured.StructuredSerializable;

public class StatEntry implements StructuredSerializable{
	
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