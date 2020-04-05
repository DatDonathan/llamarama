package at.jojokobi.llamarama.characters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterType {

	private int maxHp;
	private double speed;
	private double knockOutLimit;
	
	private List<WeaponType> weapons = new ArrayList<>();
	private Ability ability;
	private String name;
	
	private double width;
	private double height;
	private double length;
	private double xOffset;
	private double yOffset;
	private double zOffset;
	
	private Map<Direction, String> models = new HashMap<Direction, String> ();
	
	
	public String getModelForDirection (Direction direction) {
		return models.get(direction);
	}

	public int getMaxHp() {
		return maxHp;
	}

	public double getSpeed() {
		return speed;
	}

	public Ability getAbility() {
		return ability;
	}

	public String getName() {
		return name;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getxOffset() {
		return xOffset;
	}

	public double getyOffset() {
		return yOffset;
	}

	public double getLength() {
		return length;
	}

	public double getzOffset() {
		return zOffset;
	}

	public double getKnockOutLimit() {
		return knockOutLimit;
	}

	public void setKnockOutLimit(double knockOutLimit) {
		this.knockOutLimit = knockOutLimit;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public void setzOffset(double zOffset) {
		this.zOffset = zOffset;
	}

	public Map<Direction, String> getModels() {
		return models;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setAbility(Ability ability) {
		this.ability = ability;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setxOffset(double xOffset) {
		this.xOffset = xOffset;
	}

	public void setyOffset(double yOffset) {
		this.yOffset = yOffset;
	}

	public void setModels(Map<Direction, String> models) {
		this.models = models;
	}

	public void addWeapon (WeaponType weapon) {
		weapons.add(weapon);
	}
	
	public List<WeaponType> getWeapons() {
		return weapons;
	}
	
}
