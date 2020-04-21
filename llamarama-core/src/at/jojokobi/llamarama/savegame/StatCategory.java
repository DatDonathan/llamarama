package at.jojokobi.llamarama.savegame;

public enum StatCategory {
	INVASION, DEATH_MATCH, ON_TIME;
	
	public String getName() {
		switch (this) {
		case DEATH_MATCH:
			return "Battle Royale";
		case INVASION:
			return "Invasion";
		case ON_TIME:
			return "Endless";
		}
		return "";
	}
}
