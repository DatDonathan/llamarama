package at.jojokobi.llamarama.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.ObjectComponent;
import at.jojokobi.donatengine.objects.properties.BooleanProperty;
import at.jojokobi.donatengine.objects.properties.DoubleProperty;
import at.jojokobi.donatengine.objects.properties.EnumProperty;
import at.jojokobi.donatengine.objects.properties.IntProperty;
import at.jojokobi.donatengine.objects.properties.ObjectProperty;
import at.jojokobi.donatengine.objects.properties.ObservableObjectProperty;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.objects.properties.StringProperty;
import at.jojokobi.donatengine.objects.properties.list.ObservableList;
import at.jojokobi.donatengine.rendering.CanvasRenderData;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.rendering.RenderRect;
import at.jojokobi.donatengine.rendering.RenderShape;
import at.jojokobi.donatengine.rendering.RenderText;
import at.jojokobi.donatengine.style.Color;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.style.Font;
import at.jojokobi.donatengine.util.Pair;
import at.jojokobi.donatengine.util.Position;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.characters.Direction;
import at.jojokobi.llamarama.characters.WeaponType;

public class CharacterComponent implements ObjectComponent {
	
	private EnumProperty<Direction> direction = new EnumProperty<Direction>(Direction.LEFT, Direction.class);
	private ObjectProperty<CharacterType> character = new ObjectProperty<CharacterType>(null);
	private IntProperty hp = new IntProperty(40);
	private DoubleProperty cooldown = new DoubleProperty(0);
	private DoubleProperty abilityCooldown = new DoubleProperty(0);
	private IntProperty kills = new IntProperty(0);
	private StringProperty name = new StringProperty("");
	private BooleanProperty useAbility = new BooleanProperty(false);
	
	private IntProperty weapon = new IntProperty(0);
	private ObservableObjectProperty<ObservableList<Weapon>> weapons = new ObservableObjectProperty<ObservableList<Weapon>>(new ObservableList<>());

	public CharacterComponent(CharacterType character, String name) {
		super();
		this.character.set(character);
		setName(name);
		setHp(character.getMaxHp());
	}
	
	@Override
	public void onSpawn(GameObject object, Level level) {
		ObjectComponent.super.onSpawn(object, level);
		if (level.getBehavior().isHost()) {
			for (WeaponType type : character.get().getWeapons()){
				weapons.get().add(new Weapon(type.getMaxBullets()));
			}
		}
		object.setWidth(getCharacter().getWidth());
		object.setHeight(getCharacter().getHeight());
		object.setLength(getCharacter().getLength());
		object.setxOffset(getCharacter().getxOffset());
		object.setyOffset(getCharacter().getyOffset());
		object.setzOffset(getCharacter().getzOffset());
	}

	@Override
	public void hostUpdate(GameObject object, Level level, UpdateEvent event) {
		Direction dir = getDirection();
		if (object.getxMotion() < 0) {
			dir = Direction.LEFT;
		}
		else if (object.getxMotion() > 0) {
			dir = Direction.RIGHT;
		}
		else if (object.getzMotion() < 0) {
			dir = Direction.UP;
		}
		else if (object.getzMotion() > 0) {
			dir = Direction.DOWN;
		}
		if (dir != getDirection()) {
			setDirection(dir);
		}
		if (getCharacter().getAbility() != null) {
			getCharacter().getAbility().update(level, object, event.getDelta(), this);
		}
		//Use Ability
		if (usingAbility ()) {
			if (getCharacter().getAbility().use(level, object, event.getDelta(), this)) {
				setAbilityCooldown(getCharacter().getAbility().getCooldown());
			}
		}
		//Die
//		if (getHp() <= 0) {
//			object.delete(level);
//		}
	}
	
	@Override
	public void update(GameObject object, Level level, UpdateEvent event) {
		double cooldown = getCooldown();
		cooldown -= event.getDelta();
		if (cooldown <= 0) {
			cooldown = 0;
		}
		this.cooldown.setUnchanged(cooldown);
		
		double abilityCooldown = getAbilityCooldown();
		abilityCooldown -= event.getDelta();
		if (abilityCooldown <= 0) {
			abilityCooldown = 0;
		}
		this.abilityCooldown.setUnchanged(abilityCooldown);
	}

	@Override
	public void clientUpdate(GameObject object, Level level, UpdateEvent event) {
		object.setRenderTag(character.get().getModelForDirection(getDirection()));
	}

	@Override
	public void renderBefore(GameObject object, List<RenderData> data, Camera cam, Level level) {
		
	}

	@Override
	public void renderAfter(GameObject object, List<RenderData> data, Camera cam, Level level) {
		//Ability
//		if (isUsingAbility() && getTemplate().getAbility() != null) {
//			getTemplate().getAbility().render(this, ctx, cam);
//		}
//		Vector2D topLeft = cam.toScreenPosition(new Vector3D(object.getX() - object.getxOffset(), object.getY() - object.getxOffset() + object.getRenderModel().getHeight(), object.getZ() - object.getzOffset()));
//		Vector2D topRight = cam.toScreenPosition(new Vector3D(object.getX() + object.getRenderModel().getWidth() - object.getxOffset(), object.getY() - object.getxOffset() + object.getRenderModel().getHeight(), object.getZ() - object.getzOffset()));
//		double width = topRight.getX() - topLeft.getX();
		
		List<RenderShape> shapes = new ArrayList<>();
		double width = object.getWidth();
		double height = 0.2;
		//HP
		double healthPercent = (double)getHp()/getCharacter().getMaxHp();
		shapes.add(new RenderRect(new Vector2D(-width/2 + width * healthPercent, -height * 7), width * (1 - healthPercent), height, new FixedStyle().reset().setFill(new Color(0.5, 0, 0, 1))));
		shapes.add(new RenderRect(new Vector2D(-width/2, -height * 7), width * healthPercent, height, new FixedStyle().reset().setFill(Color.RED)));
		//Cooldown
		double cooldownPercent = 1 - ((double)getCooldown()/getCurrentWeapon().getKey().getFireDelay());
		shapes.add(new RenderRect(new Vector2D(-width/2 + width * cooldownPercent, -height * 6), width * (1 - cooldownPercent), height, new FixedStyle().reset().setFill(new Color(0, 0.5, 0, 1))));
		shapes.add(new RenderRect(new Vector2D(-width/2, -height * 6), width * cooldownPercent, height, new FixedStyle().reset().setFill(Color.GREEN)));
		//Ability Cooldown
		double abilityCooldownPercent = 1 - ((double)getAbilityCooldown()/getMaxAbilityCooldown());
		shapes.add(new RenderRect(new Vector2D(-width/2 + width * abilityCooldownPercent, -height * 5), width * (1 - abilityCooldownPercent), height, new FixedStyle().reset().setFill(new Color(1, 0.5, 0, 1))));
		shapes.add(new RenderRect(new Vector2D(-width/2, -height * 5), width * abilityCooldownPercent, height, new FixedStyle().reset().setFill(Color.YELLOW)));
		//Bullets
		double bulletsPercent = (double) getCurrentWeapon().getValue().getBullets()/getCurrentWeapon().getKey().getMaxBullets();
		shapes.add(new RenderRect(new Vector2D(-width/2 + width * bulletsPercent, -height * 4), width * (1 - bulletsPercent), height, new FixedStyle().reset().setFill(new Color(0.2, 0.2, 0.2, 1))));
		shapes.add(new RenderRect(new Vector2D(-width/2, -height * 4), width * bulletsPercent, height, new FixedStyle().reset().setFill(new Color(0.2, 0.2, 1, 1))));
		//Name
		//Weapon
		shapes.add(new RenderText(new Vector2D(-width/2 - 0.5, -height * 5), weapon.get() + 1 + "", new FixedStyle().reset().setFont(new Font("Consolas", 32))));
		
		data.add(new CanvasRenderData(new Position(object.getPosition().add(object.getSize().multiply(0.5)).add(0, object.getHeight()/2, 0), object.getArea()), shapes));
		
		if (usingAbility()) {
			getCharacter().getAbility().render(level, object, this, data, cam);
		}
		//Team
//		if (team != null) {
//			ctx.strokeText(getTeam(), getX() - cam.getX(), getY() - 60 - cam.getY(), getWidth());
//		}
	}
	
	public void revive () {
		setHp(getCharacter().getMaxHp());
		int i = 0;
		for (Weapon weapon : weapons.get()) {
			weapon.setBullets(getCharacter().getWeapons().get(i).getMaxBullets());
			i++;
		}
	}
	
	public boolean isUseAbility() {
		return useAbility.get();
	}

	public void setUseAbility(boolean usingAbility) {
		this.useAbility.set(usingAbility);
	}
	
	public boolean usingAbility () {
		return getAbilityCooldown() <= 0 && useAbility.get() && getCharacter().getAbility() != null && isAlive();
	}

	public Pair<WeaponType, Weapon> getCurrentWeapon () {
		return new Pair<>(getCharacter().getWeapons().get(getSelectedWeapon()), getWeapons().get(getSelectedWeapon()));
	}
	
	public void reloadWeapon (int amount) {
		Pair<WeaponType, Weapon> weapon = getCurrentWeapon();
		weapon.getValue().setBullets(Math.min(weapon.getValue().getBullets() + amount, weapon.getKey().getMaxBullets()));
	}
	
	private double getMaxAbilityCooldown () {
		return getCharacter().getAbility() == null ? 1 : getCharacter().getAbility().getCooldown();
	}
	
	public void attack (GameObject object, Level level) {
		Pair<WeaponType, Weapon> weapon = getCurrentWeapon();
		if (getCooldown() <= 0 && isAlive() && !usingAbility()) {
			weapon.getValue().setBullets(weapon.getValue().getBullets() - weapon.getKey().getFireBehavior().shoot(object, this, weapon.getKey(), weapon.getValue(), level));
			setCooldown(weapon.getKey().getFireDelay());
		}
	}
	
	public void heal (int amount) {
		setHp(Math.min(getHp() + amount, getCharacter().getMaxHp()));
	}
	
	public void damage (int amount) {
		setHp(Math.max(getHp() - amount, 0));
	}
	
	public boolean isAlive () {
		return getHp() > 0;
	}
	
	public String getName () {
		return name.get();
	}
	
	public void swapWeapon () {
		weapon.increment(1);
		if (getSelectedWeapon() >= getWeapons().size()) {
			weapon.set(0);
		}
		cooldown.set(getCurrentWeapon().getKey().getFireDelay());
	}
	
	public Direction getDirection() {
		return direction.get();
	}

	public CharacterType getCharacter() {
		return character.get();
	}

	public void setDirection(Direction direction) {
		this.direction.set(direction);
	}

	public double getCooldown() {
		return cooldown.get();
	}

	public double getAbilityCooldown() {
		return abilityCooldown.get();
	}

	public void setCooldown(double cooldown) {
		this.cooldown.set(Math.max(0, cooldown));
	}

	public void setAbilityCooldown(double abilityCooldown) {
		this.abilityCooldown.set(abilityCooldown);
	}

	public void setHp(Integer t) {
		hp.set(t);
	}

	public Integer getHp() {
		return hp.get();
	}

	public void setKills(Integer t) {
		kills.set(t);
	}

	public Integer getKills() {
		return kills.get();
	}

	public Integer getSelectedWeapon() {
		return weapon.get();
	}
	
	public ObservableList<Weapon> getWeapons() {
		return weapons.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}
	
	public ObjectProperty<CharacterType> characterProperty () {
		return character;
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList(direction, character, hp, kills, cooldown, abilityCooldown, useAbility, weapon, weapons, name);
	}
	
}
