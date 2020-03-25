package at.jojokobi.llamarama.gamemode;

import at.jojokobi.donatengine.style.Color;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class SingleScoreboardEntry implements ScoreboardEntry {
	
	private CharacterComponent character;

	public SingleScoreboardEntry(CharacterComponent character) {
		super();
		this.character = character;
	}

	@Override
	public String getName() {
		return character.getName();
	}

	@Override
	public Color getColor() {
		return Color.BLACK;
	}

	@Override
	public int getKills() {
		return character.getKills();
	}
	
}
