package at.jojokobi.llamarama.gamemode;

import at.jojokobi.donatengine.style.Color;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class SingleWinner implements Winner {
	
	private CharacterComponent character;

	public SingleWinner(CharacterComponent character) {
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

	@Override
	public int getDeaths() {
		return 0;
	}

	@Override
	public int getScore() {
		return getKills();
	}
	
}
