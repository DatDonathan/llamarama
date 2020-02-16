package at.jojokobi.llamarama;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.llamarama.characters.CharacterTypeSerializer;
import at.jojokobi.llamarama.characters.Direction;
import at.jojokobi.llamarama.entities.Bullet;
import at.jojokobi.llamarama.entities.NonPlayerCharacter;
import at.jojokobi.llamarama.entities.PlayerCharacter;
import at.jojokobi.llamarama.entities.Weapon;
import at.jojokobi.llamarama.gamemode.BattleRoyaleGameMode;
import at.jojokobi.llamarama.gamemode.EndlessGameMode;
import at.jojokobi.llamarama.gamemode.GameLevel.PlayerInformation;
import at.jojokobi.llamarama.gamemode.GameLevel.SelectCharacterAction;
import at.jojokobi.llamarama.gamemode.GameLevel.StartMatchAction;
import at.jojokobi.llamarama.items.HealingGrass;
import at.jojokobi.llamarama.items.ItemInstance;
import at.jojokobi.llamarama.items.SpitBucket;
import at.jojokobi.llamarama.tiles.BushTile;
import at.jojokobi.llamarama.tiles.GrassTile;
import at.jojokobi.llamarama.tiles.LongGrassTile;
import at.jojokobi.llamarama.tiles.SandTile;
import at.jojokobi.llamarama.tiles.StompTile;
import at.jojokobi.llamarama.tiles.WaterTile;
import at.jojokobi.donatengine.GameLoop;
import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.SimpleGameLogic;
import at.jojokobi.donatengine.javafx.input.Axis;
import at.jojokobi.donatengine.javafx.input.ButtonAxis;
import at.jojokobi.donatengine.javafx.rendering.StretchYZPerspective;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.net.SingleplayerBehavior;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.presence.DiscordGamePresence;
import at.jojokobi.donatengine.ressources.IRessourceHandler;
import at.jojokobi.donatengine.ressources.RessourceHandler;
import at.jojokobi.donatengine.serialization.BinarySerialization;
import at.jojokobi.llamarama.characters.CharacterType;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

public class LlamaramaApplication extends Application {
	
	public static final String IMAGE_PATH = "/assets/images/";
	
	private Game view;
	
	public static void main(String[] args) {
		BinarySerialization.getInstance().registerSerializer(CharacterType.class, new CharacterTypeSerializer());
		
		BinarySerialization.getInstance().getIdClassFactory().addClass(CharacterType.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(Direction.class);
		
		BinarySerialization.getInstance().getIdClassFactory().addClass(Bullet.class);
		
		BinarySerialization.getInstance().getIdClassFactory().addClass(NonPlayerCharacter.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(PlayerCharacter.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(Weapon.class);
		
		BinarySerialization.getInstance().getIdClassFactory().addClass(StartMatchAction.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(SelectCharacterAction.class);
		
		BinarySerialization.getInstance().getIdClassFactory().addClass(HealingGrass.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(ItemInstance.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(SpitBucket.class);
		
		BinarySerialization.getInstance().getIdClassFactory().addClass(BushTile.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(GrassTile.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(LongGrassTile.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(SandTile.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(StompTile.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(WaterTile.class);

		BinarySerialization.getInstance().getIdClassFactory().addClass(BattleRoyaleGameMode.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(EndlessGameMode.class);
		
		BinarySerialization.getInstance().getIdClassFactory().addClass(PlayerInformation.class);

		launch (args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Llamarama - worlds first tactical llama shooter!");
		new Thread(() -> {
			Level level = new MainMenuLevel(new SingleplayerBehavior());
			Camera cam = new Camera(0, 0, 0, 1280, 768);
			cam.setRenderDistance(40*32);
			cam.setPerspective(new StretchYZPerspective());
			IRessourceHandler ressourceHandler = new RessourceHandler();
			Map<String, KeyCode> keyBindings = new HashMap<>();
			keyBindings.put(ControlConstants.LEFT, KeyCode.A);
			keyBindings.put(ControlConstants.RIGHT, KeyCode.D);
			keyBindings.put(ControlConstants.UP, KeyCode.W);
			keyBindings.put(ControlConstants.DOWN, KeyCode.S);
			keyBindings.put(ControlConstants.ATTACK, KeyCode.SPACE);
			keyBindings.put(ControlConstants.CAM_UP, KeyCode.PLUS);
			keyBindings.put(ControlConstants.CAM_DOWN, KeyCode.MINUS);
			keyBindings.put(ControlConstants.SWAP_WEAPON, KeyCode.SHIFT);
			keyBindings.put(ControlConstants.USE_ABILITY, KeyCode.CONTROL);
			keyBindings.put(ControlConstants.PAUSE, KeyCode.ESCAPE);
			Map<String, Axis> axisBindings = new HashMap<>();
			axisBindings.put(ControlConstants.MOVEMENT, new ButtonAxis(ControlConstants.UP, ControlConstants.DOWN, ControlConstants.LEFT, ControlConstants.RIGHT));
			view = new Game(stage, new SimpleGameLogic(level), cam, ressourceHandler, keyBindings, new HashMap<>(), axisBindings);
			view.getGamePresenceHandler().addPlatform(new DiscordGamePresence("663770162188779521", ""));
			GameLoop loop = new GameLoop(60, view);
			loop.start();
		}, "Game-Thread").start();
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		if (view != null) {
			view.stopGame();
		}
	}

}
