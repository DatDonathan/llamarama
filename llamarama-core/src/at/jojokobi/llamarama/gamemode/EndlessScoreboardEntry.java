package at.jojokobi.llamarama.gamemode;

import at.jojokobi.llamarama.entities.CharacterComponent;

public class EndlessScoreboardEntry extends SingleScoreboardEntry {

	public EndlessScoreboardEntry(CharacterComponent character, long clientId) {
		super(character, clientId);
	}
	
	@Override
	public int getScore() {
		return getKills() - getDeaths();
	}

}
