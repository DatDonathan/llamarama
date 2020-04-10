package at.jojokobi.llamarama.characters;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class CharacterTypeProvider {

	/*public static final Image CORPORAL_RIGHT = new Image(
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
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "officer_down.png"));*/

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
		corporal.setSpeed(8.5);
		corporal.setKnockOutLimit(4.5);
		corporal.addWeapon(new WeaponType(0.4, 18, 40, new SingleShotBehavior()));
		corporal.addWeapon(new WeaponType(0.4, 35, 1, new MeleeAttackBehavior()));
		corporal.setWidth(1.5);
		corporal.setHeight(1.5);
		corporal.setLength(1.5);
		corporal.setxOffset(0.25);
		corporal.setyOffset(0);
		corporal.setzOffset(0.5);
		corporal.getModels().put(Direction.UP, "character.corporal.up");
		corporal.getModels().put(Direction.DOWN, "character.corporal.down");
		corporal.getModels().put(Direction.RIGHT, "character.corporal.right");
		corporal.getModels().put(Direction.LEFT, "character.corporal.left");
		types.put("Corporal", corporal);

		// Speedy
		CharacterType speedy = new CharacterType();
		speedy.setName("Speedy");
		speedy.setMaxHp(50);
		speedy.setSpeed(13.125);
		speedy.setKnockOutLimit(4.0);
		speedy.addWeapon(new WeaponType(1 / 12.0, 4, 50, new SingleShotBehavior()));
		speedy.addWeapon(new WeaponType(3.5, 40, 5, new SingleShotBehavior(75)));
		speedy.setAbility(new StationaryShieldAbility());
		speedy.setWidth(1.5);
		speedy.setHeight(1.5);
		speedy.setLength(1.5);
		speedy.setxOffset(0.25);
		speedy.setyOffset(0);
		speedy.setzOffset(0.5);
		speedy.getModels().put(Direction.UP, "character.speedy.up");
		speedy.getModels().put(Direction.DOWN, "character.speedy.down");
		speedy.getModels().put(Direction.RIGHT, "character.speedy.right");
		speedy.getModels().put(Direction.LEFT, "character.speedy.left");
		types.put("Speedy", speedy);

		// Jeremy
		CharacterType jeremy = new CharacterType();
		jeremy.setName("Jeremy");
		jeremy.setMaxHp(50);
		jeremy.setSpeed(9.375);
		jeremy.setKnockOutLimit(4.0);
		jeremy.addWeapon(new WeaponType(0.5, 4, 150, new MultiShotBehavior(8)));
		jeremy.addWeapon(new WeaponType(3.5, 40, 5, new SingleShotBehavior(75)));
		jeremy.setAbility(new HookAbility());
		jeremy.setWidth(1.5);
		jeremy.setHeight(1.5);
		jeremy.setLength(1.5);
		jeremy.setxOffset(0.25);
		jeremy.setyOffset(0);
		jeremy.setzOffset(0.5);
		jeremy.getModels().put(Direction.UP, "character.jeremy.up");
		jeremy.getModels().put(Direction.DOWN, "character.jeremy.down");
		jeremy.getModels().put(Direction.RIGHT, "character.jeremy.right");
		jeremy.getModels().put(Direction.LEFT, "character.jeremy.left");
		types.put("Jeremy", jeremy);

		// Officer
		CharacterType officer = new CharacterType();
		officer.setName("Officer");
		officer.setMaxHp(120);
		officer.setSpeed(7.5);
		officer.setKnockOutLimit(4.5);
		officer.addWeapon(new WeaponType(1/3.0, 5, 120, new SingleShotBehavior()));
		officer.addWeapon(new WeaponType(0.2, 20, 1, new PunchBehavior()));
		officer.setAbility(new ShieldAbility());
		officer.setWidth(1.5);
		officer.setHeight(1.5);
		officer.setLength(1.5);
		officer.setxOffset(0.25);
		officer.setyOffset(0);
		officer.setzOffset(0.5);
		officer.getModels().put(Direction.UP, "character.officer.up");
		officer.getModels().put(Direction.DOWN, "character.officer.down");
		officer.getModels().put(Direction.RIGHT, "character.officer.right");
		officer.getModels().put(Direction.LEFT, "character.officer.left");
		types.put("Officer", officer);
		
		// Ivan
		CharacterType ivan = new CharacterType();
		ivan.setName("Ivan");
		ivan.setMaxHp(80);
		ivan.setSpeed(7.5);
		ivan.setKnockOutLimit(5.5);
		ivan.addWeapon(new WeaponType(0.7, 10, 40, new RecoilBehavior(new MultiShotBehavior(3), 0.7)));
		ivan.addWeapon(new WeaponType(2.5, 80, 1, new RecoilBehavior(new BombBehavior(), 2)));
		ivan.setAbility(new PuddleAbility());
		ivan.setWidth(1.5);
		ivan.setHeight(1.5);
		ivan.setLength(1.5);
		ivan.setxOffset(0.25);
		ivan.setyOffset(0);
		ivan.setzOffset(0.5);
		ivan.getModels().put(Direction.UP, "character.ivan.up");
		ivan.getModels().put(Direction.DOWN, "character.ivan.down");
		ivan.getModels().put(Direction.RIGHT, "character.ivan.right");
		ivan.getModels().put(Direction.LEFT, "character.ivan.left");
		types.put("Ivan", ivan);
		
		//Triplets
		CharacterType triplets = new CharacterType();
		triplets.setName("Triplets");
		triplets.setMaxHp(60);
		triplets.setSpeed(11.25);
		triplets.setKnockOutLimit(3.0);
		triplets.addWeapon(new WeaponType(7/12.0, 10, 50, new TripletBehavior(3)));
		triplets.addWeapon(new WeaponType(0.5, 4, 60, new ToyBehavior()));
		triplets.setAbility(new HealAbility());
		triplets.setWidth(1.5);
		triplets.setHeight(1.5);
		triplets.setLength(1.5);
		triplets.setxOffset(0.25);
		triplets.setyOffset(0);
		triplets.setzOffset(0.5);
		triplets.getModels().put(Direction.UP, "character.triplets.up");
		triplets.getModels().put(Direction.DOWN, "character.triplets.down");
		triplets.getModels().put(Direction.RIGHT, "character.triplets.right");
		triplets.getModels().put(Direction.LEFT, "character.triplets.left");
		types.put("Triplets", triplets);

		return types;
	}

}
