package at.jojokobi.llamarama.gamemode;

import at.jojokobi.llamarama.entities.CharacterComponent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
	public Paint getColor() {
		return Color.BLACK;
	}
	
}
