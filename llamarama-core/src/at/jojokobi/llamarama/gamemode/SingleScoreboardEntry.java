package at.jojokobi.llamarama.gamemode;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.style.Color;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.savegame.GameUser;

public class SingleScoreboardEntry implements ScoreboardEntry {
	
	private CharacterComponent character;
	private long clientId;

	public SingleScoreboardEntry(CharacterComponent character, long clientId) {
		super();
		this.character = character;
		this.clientId = clientId;
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
	public int getScore() {
		return character.getKills();
	}

	@Override
	public int getDeaths() {
		return character.getDeaths();
	}
	
	@Override
	public boolean isUser(GameUser user, Level level) {
		//TODO more accurate check
		return level.getClientId() == clientId;
	}

	
}
