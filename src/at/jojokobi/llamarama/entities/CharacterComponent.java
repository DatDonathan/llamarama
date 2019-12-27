package at.jojokobi.llamarama.entities;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.ObjectComponent;
import at.jojokobi.donatengine.objects.properties.DoubleProperty;
import at.jojokobi.donatengine.objects.properties.EnumProperty;
import at.jojokobi.donatengine.objects.properties.IntProperty;
import at.jojokobi.donatengine.objects.properties.ObjectProperty;
import at.jojokobi.donatengine.objects.properties.ObservableList;
import at.jojokobi.donatengine.objects.properties.ObservableObjectProperty;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.util.Pair;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.characters.Direction;
import at.jojokobi.llamarama.characters.WeaponType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CharacterComponent implements ObjectComponent {
	
	private EnumProperty<Direction> direction = new EnumProperty<Direction>(Direction.LEFT, Direction.class);
	private ObjectProperty<CharacterType> character = new ObjectProperty<CharacterType>(null);
	private IntProperty hp = new IntProperty(40);
	private DoubleProperty cooldown = new DoubleProperty(0);
	private double abilityCooldown = 0;
	private IntProperty kills = new IntProperty(0);
	
	private IntProperty weapon = new IntProperty(0);
	private ObservableObjectProperty<ObservableList<Weapon>> weapons = new ObservableObjectProperty<ObservableList<Weapon>>(new ObservableList<>());

	public CharacterComponent(CharacterType character) {
		super();
		this.character.set(character);
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
	}

	@Override
	public void hostUpdate(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
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
		//Die
//		if (getHp() <= 0) {
//			object.delete(level);
//		}
	}
	
	@Override
	public void update(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
		double cooldown = getCooldown();
		cooldown -= delta;
		if (cooldown <= 0) {
			cooldown = 0;
		}
		this.cooldown.setUnchanged(cooldown);
	}

	@Override
	public void clientUpdate(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
		object.setRenderModel(character.get().getModelForDirection(getDirection()));
	}

	@Override
	public void renderBefore(GameObject object, GraphicsContext ctx, Camera cam, Level level) {
		
	}

	@Override
	public void renderAfter(GameObject object, GraphicsContext ctx, Camera cam, Level level) {
		//Ability
//		if (isUsingAbility() && getTemplate().getAbility() != null) {
//			getTemplate().getAbility().render(this, ctx, cam);
//		}
		Vector2D topLeft = cam.toScreenPosition(new Vector3D(object.getX() - object.getxOffset(), object.getY() - object.getxOffset() + object.getRenderModel().getHeight(), object.getZ() - object.getzOffset() + object.getRenderModel().getLength()));
		Vector2D topRight = cam.toScreenPosition(new Vector3D(object.getX() + object.getRenderModel().getWidth() - object.getxOffset(), object.getY() - object.getxOffset() + object.getRenderModel().getHeight(), object.getZ() - object.getzOffset() + object.getRenderModel().getLength()));
		double width = topRight.getX() - topLeft.getX();
		
		//HP
		ctx.setFill(new Color(0.5, 0, 0, 1));
		ctx.fillRect(topLeft.getX(), topLeft.getY() - 35, width, 5);
		ctx.setFill(Color.RED);
		ctx.fillRect(topLeft.getX(), topLeft.getY() - 35, width * ((double)getHp()/getCharacter().getMaxHp()), 5);
		//Cooldown
		ctx.setFill(new Color(0, 0.5, 0, 1));
		ctx.fillRect(topLeft.getX(), topLeft.getY() - 25, width, 5);
		ctx.setFill(new Color(0, 1, 0, 1));
		ctx.fillRect(topLeft.getX(), topLeft.getY() - 25, width * (1-((double)getCooldown()/getCurrentWeapon().getKey().getFireDelay())), 5);
		//Ability Cooldown
		ctx.setFill(Color.ORANGE);
		ctx.fillRect(topLeft.getX(), topLeft.getY() - 30, width, 5);
		ctx.setFill(Color.YELLOW);
		ctx.fillRect(topLeft.getX(), topLeft.getY() - 25, width * (1-((double)getAbilityCooldown()/getMaxAbilityCooldown())), 5);
		//Bullets
		ctx.setFill(new Color(0.2, 0.2, 0.2, 1));
		ctx.fillRect(topLeft.getX(), topLeft.getY() - 20, width, 5);
		ctx.setFill(new Color(0.2, 0.2, 1, 1));
		ctx.fillRect(topLeft.getX(), topLeft.getY() - 20, width * ((double)getCurrentWeapon().getValue().getBullets()/getCurrentWeapon().getKey().getMaxBullets()), 5);
		//Name
//		ctx.setFill(Color.BLACK);
//		ctx.setFont(new Font("Consolas", 12));
//		ctx.strokeText(getNickname(), getX() - cam.getX(), getY() - 50 - cam.getY(), getWidth());
//		ctx.strokeText("Kills " + getKills(), getX() - cam.getX(), getY() - 40 - cam.getY(), getWidth());
		//Team
//		if (team != null) {
//			ctx.strokeText(getTeam(), getX() - cam.getX(), getY() - 60 - cam.getY(), getWidth());
//		}
	}
	
	public Pair<WeaponType, Weapon> getCurrentWeapon () {
		return new Pair<>(getCharacter().getWeapons().get(getSelectedWeapon()), getWeapons().get(getSelectedWeapon()));
	}
	
	private double getMaxAbilityCooldown () {
		return getCharacter().getAbility() == null ? 1 : getCharacter().getAbility().getCooldown();
	}
	
	public void attack (GameObject object, Level level) {
		Pair<WeaponType, Weapon> weapon = getCurrentWeapon();
		if (getCooldown() <= 0 && isAlive()) {
			weapon.getValue().setBullets(weapon.getValue().getBullets() - weapon.getKey().getFireBehavior().shoot(object, this, weapon.getKey(), weapon.getValue(), level));
			setCooldown(weapon.getKey().getFireDelay());
		}
	}
	
	public boolean isAlive () {
		return getHp() > 0;
	}
	
	public String getName () {
		return getCharacter().getName();
	}
	
	public void swapWeapon () {
		weapon.increment(1);
		if (getSelectedWeapon() >= getWeapons().size()) {
			weapon.set(0);
		}
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
		return abilityCooldown;
	}

	public void setCooldown(double cooldown) {
		this.cooldown.set(Math.max(0, cooldown));
	}

	public void setAbilityCooldown(double abilityCooldown) {
		this.abilityCooldown = abilityCooldown;
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

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList(direction, character, hp, kills, weapon, weapons);
	}
	
}
