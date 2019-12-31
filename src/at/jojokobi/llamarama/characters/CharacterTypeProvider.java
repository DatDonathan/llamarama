package at.jojokobi.llamarama.characters;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import at.jojokobi.donatengine.rendering.Image2DModel;
import at.jojokobi.llamarama.LlamaramaApplication;
import javafx.scene.image.Image;

public class CharacterTypeProvider {

	public static final Image CORPORAL_RIGHT = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "corporal_right.png"));
	public static final Image CORPORAL_LEFT = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "corporal_left.png"));
	public static final Image CORPORAL_UP = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "corporal_up.png"));
	public static final Image CORPORAL_DOWN = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "corporal_down.png"));

	public static final Image SPEEDY_RIGHT = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "speedy_right.png"));
	public static final Image SPEEDY_LEFT = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "speedy_left.png"));
	public static final Image SPEEDY_UP = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "speedy_up.png"));
	public static final Image SPEEDY_DOWN = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "speedy_down.png"));

	public static final Image JEREMY_RIGHT = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "jeremy_right.png"));
	public static final Image JEREMY_LEFT = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "jeremy_left.png"));
	public static final Image JEREMY_UP = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "jeremy_up.png"));
	public static final Image JEREMY_DOWN = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "jeremy_down.png"));

	public static final Image OFFICER_RIGHT = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "officer_right.png"));
	public static final Image OFFICER_LEFT = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "officer_left.png"));
	public static final Image OFFICER_UP = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "officer_up.png"));
	public static final Image OFFICER_DOWN = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "officer_down.png"));

	private static Map<String, CharacterType> characterTypes;

	public static Map<String, CharacterType> getCharacterTypes() {
		if (characterTypes == null) {
			characterTypes = generateCharacterTypes();
		}
		return Collections.unmodifiableMap(characterTypes);
	}

	private static Map<String, CharacterType> generateCharacterTypes() {
		Map<String, CharacterType> types = new HashMap<>();
		// Corporal
		CharacterType corporal = new CharacterType();
		corporal.setName("Corporal");
		corporal.setMaxHp(70);
		corporal.setSpeed(240);
		corporal.addWeapon(new WeaponType(0.4, 18, 40, new SingleShotBehavior()));
		corporal.addWeapon(new WeaponType(0.4, 35, 1, new MeleeAttackBehavior()));
		corporal.setWidth(48);
		corporal.setHeight(48);
		corporal.setLength(48);
		corporal.setxOffset(8);
		corporal.setyOffset(0);
		corporal.setzOffset(16);
		corporal.getModels().put(Direction.UP, new Image2DModel(CORPORAL_UP));
		corporal.getModels().put(Direction.DOWN, new Image2DModel(CORPORAL_DOWN));
		corporal.getModels().put(Direction.RIGHT, new Image2DModel(CORPORAL_RIGHT));
		corporal.getModels().put(Direction.LEFT, new Image2DModel(CORPORAL_LEFT));
		types.put("Corporal", corporal);

		// Speedy
		CharacterType speedy = new CharacterType();
		speedy.setName("Speedy");
		speedy.setMaxHp(50);
		speedy.setSpeed(420);
		speedy.addWeapon(new WeaponType(1 / 12.0, 4, 50, new SingleShotBehavior()));
		speedy.addWeapon(new WeaponType(3.5, 40, 5, new SingleShotBehavior(2400)));
		speedy.setWidth(48);
		speedy.setHeight(48);
		speedy.setLength(48);
		speedy.setxOffset(8);
		speedy.setyOffset(0);
		speedy.setzOffset(16);
		speedy.getModels().put(Direction.UP, new Image2DModel(SPEEDY_UP));
		speedy.getModels().put(Direction.DOWN, new Image2DModel(SPEEDY_DOWN));
		speedy.getModels().put(Direction.RIGHT, new Image2DModel(SPEEDY_RIGHT));
		speedy.getModels().put(Direction.LEFT, new Image2DModel(SPEEDY_LEFT));
		types.put("Speedy", speedy);

		// Jeremy
		CharacterType jeremy = new CharacterType();
		jeremy.setName("Jeremy");
		jeremy.setMaxHp(50);
		jeremy.setSpeed(300);
		jeremy.addWeapon(new WeaponType(0.5, 4, 150, new MultiShotBehavior(8)));
		jeremy.addWeapon(new WeaponType(3.5, 40, 5, new SingleShotBehavior(2400)));
		jeremy.setWidth(48);
		jeremy.setHeight(48);
		jeremy.setLength(48);
		jeremy.setxOffset(8);
		jeremy.setyOffset(0);
		jeremy.setzOffset(16);
		jeremy.getModels().put(Direction.UP, new Image2DModel(JEREMY_UP));
		jeremy.getModels().put(Direction.DOWN, new Image2DModel(JEREMY_DOWN));
		jeremy.getModels().put(Direction.RIGHT, new Image2DModel(JEREMY_RIGHT));
		jeremy.getModels().put(Direction.LEFT, new Image2DModel(JEREMY_LEFT));
		types.put("Jeremy", jeremy);

		// Officer
		CharacterType officer = new CharacterType();
		officer.setName("Officer");
		officer.setMaxHp(120);
		officer.setSpeed(240);
		officer.addWeapon(new WeaponType(1/3.0, 9, 120, new SingleShotBehavior()));
		// officer.addWeapon(new WeaponType(0.4, 35, 1, new MeleeAttackBehavior()));
		officer.setWidth(48);
		officer.setHeight(48);
		officer.setLength(48);
		officer.setxOffset(8);
		officer.setyOffset(0);
		officer.setzOffset(16);
		officer.getModels().put(Direction.UP, new Image2DModel(OFFICER_UP));
		officer.getModels().put(Direction.DOWN, new Image2DModel(OFFICER_DOWN));
		officer.getModels().put(Direction.RIGHT, new Image2DModel(OFFICER_RIGHT));
		officer.getModels().put(Direction.LEFT, new Image2DModel(OFFICER_LEFT));
		types.put("Officer", officer);

		return types;
	}

}
