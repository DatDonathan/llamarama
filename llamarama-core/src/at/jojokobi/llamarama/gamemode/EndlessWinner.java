package at.jojokobi.llamarama.gamemode;

import at.jojokobi.llamarama.entities.CharacterComponent;

public class EndlessWinner extends SingleWinner {

	public EndlessWinner(CharacterComponent character) {
		super(character);
	}
	
	@Override
	public int getScore() {
		return getKills() - getDeaths();
	}
	
}
