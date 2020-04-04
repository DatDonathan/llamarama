package at.jojokobi.llamarama.entities;

import at.jojokobi.donatengine.objects.BrakeMotionComponent;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.characters.Direction;

public abstract class CharacterInstance extends GameObject {

	private CharacterComponent comp;

	public CharacterInstance(double x, double y, double z, String area, CharacterType character, String name) {
		super(x, y, z, area, character.getModelForDirection(Direction.LEFT));
		comp = new CharacterComponent(character, name);
		comp.characterProperty().addListener((p, o, n) -> {
			getComponent(BrakeMotionComponent.class).setBrakeXLimit(n.getSpeed());
			getComponent(BrakeMotionComponent.class).setBrakeZLimit(n.getSpeed());
		});
		addComponent(comp);
		addComponent(new BrakeMotionComponent(18, 0, 18, character.getSpeed(), 0, character.getSpeed()));
	}

	public Direction getDirection() {
		return comp.getDirection();
	}

	public CharacterType getCharacter() {
		return comp.getCharacter();
	}

	public void setDirection(Direction direction) {
		comp.setDirection(direction);
	}

	public double getCooldown() {
		return comp.getCooldown();
	}

	public double getAbilityCooldown() {
		return comp.getAbilityCooldown();
	}

	public void setCooldown(double cooldown) {
		comp.setCooldown(cooldown);
	}

	public void setAbilityCooldown(double abilityCooldown) {
		comp.setAbilityCooldown(abilityCooldown);
	}

	public void setHp(Integer t) {
		comp.setHp(t);
	}

	public Integer getHp() {
		return comp.getHp();
	}

	public void setKills(Integer t) {
		comp.setKills(t);
	}

	public Integer getKills() {
		return comp.getKills();
	}
		
}
