package at.jojokobi.llamarama.javafx;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.GameLogic;
import at.jojokobi.donatengine.SimpleGameLogic;
import at.jojokobi.donatengine.audio.AudioSystem;
import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.javafx.GameApplication;
import at.jojokobi.donatengine.javafx.input.Axis;
import at.jojokobi.donatengine.javafx.input.ButtonAxis;
import at.jojokobi.donatengine.javafx.input.SceneInput;
import at.jojokobi.donatengine.level.SingleplayerBehavior;
import at.jojokobi.donatengine.presence.DiscordGamePresence;
import at.jojokobi.donatengine.rendering.GameView;
import at.jojokobi.donatengine.serialization.binary.BinarySerialization;
import at.jojokobi.donatengine.serialization.binary.BinarySerializationWrapper;
import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;
import at.jojokobi.donatengine.serialization.structured.XMLObjectLoader;
import at.jojokobi.llamarama.ControlConstants;
import at.jojokobi.llamarama.SelectUserLevel;
import at.jojokobi.llamarama.Serializables;
import at.jojokobi.llamarama.savegame.GameState;
import at.jojokobi.llamarama.savegame.LocalUserDao;
import javafx.scene.input.KeyCode;

public class LlamaramaApplication extends GameApplication {
	
	public static final String IMAGE_PATH = "/assets/images/";
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	protected InputStream soundsInput() {
		return getClass().getResourceAsStream("/sounds.json");
	}

	@Override
	protected InputStream imagesInput() {
		return getClass().getResourceAsStream("/images.json");
	}

	@Override
	protected InputStream modelsInput() {
		return getClass().getResourceAsStream("/models.json");
	}

	@Override
	protected void putControls(SceneInput input) {
		Map<String, KeyCode> keyBindings = input.getKeyBindings();
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
		Map<String, Axis> axisBindings = input.getAxisBindings();
		axisBindings.put(ControlConstants.MOVEMENT, new ButtonAxis(ControlConstants.UP, ControlConstants.DOWN, ControlConstants.LEFT, ControlConstants.RIGHT));
	}

	@Override
	protected Game createGame(AudioSystem system, Input input, GameView view) {
		File userFolder = new File("./users");
		userFolder.mkdirs();
		GameLogic logic = new SimpleGameLogic(new SelectUserLevel(new SingleplayerBehavior(), new GameState(new LocalUserDao(userFolder, "xml", new XMLObjectLoader()))));
		SerializationWrapper serialization = new BinarySerializationWrapper(BinarySerialization.getInstance().getIdClassFactory());
		view.setTitle("Llamarama - worlds first tactical llama shooter");
		Game game = new Game(logic, input, system, serialization, view);
		game.getGamePresenceHandler().addPlatform(new DiscordGamePresence("663770162188779521", ""));
		return game;
	}

	@Override
	protected int getUpdatesPerSecond() {
		return 60;
	}

	@Override
	protected void initApplication() {
		Serializables.register();
	}

	@Override
	protected Class<?> getRessourceRoot() {
		return ControlConstants.class;
	}

	@Override
	protected String imagesRoot() {
		return "assets/images";
	}

	@Override
	protected String soundsRoot() {
		return "assets/sounds";
	}
	
//	@Override
//	public void start(Stage stage) throws Exception {
//		stage.setTitle("Llamarama - worlds first tactical llama shooter!");
//		new Thread(() -> {
//			Level level = new MainMenuLevel(new SingleplayerBehavior());
//			Camera cam = new Camera(0, 0, 0, 1280, 768);
//			cam.setRenderDistance(40*32);
//			cam.setPerspective(new StretchYZPerspective());
//			IRessourceHandler ressourceHandler = new RessourceHandler();
//			Map<String, KeyCode> keyBindings = new HashMap<>();
//			keyBindings.put(ControlConstants.LEFT, KeyCode.A);
//			keyBindings.put(ControlConstants.RIGHT, KeyCode.D);
//			keyBindings.put(ControlConstants.UP, KeyCode.W);
//			keyBindings.put(ControlConstants.DOWN, KeyCode.S);
//			keyBindings.put(ControlConstants.ATTACK, KeyCode.SPACE);
//			keyBindings.put(ControlConstants.CAM_UP, KeyCode.PLUS);
//			keyBindings.put(ControlConstants.CAM_DOWN, KeyCode.MINUS);
//			keyBindings.put(ControlConstants.SWAP_WEAPON, KeyCode.SHIFT);
//			keyBindings.put(ControlConstants.USE_ABILITY, KeyCode.CONTROL);
//			keyBindings.put(ControlConstants.PAUSE, KeyCode.ESCAPE);
//			Map<String, Axis> axisBindings = new HashMap<>();
//			axisBindings.put(ControlConstants.MOVEMENT, new ButtonAxis(ControlConstants.UP, ControlConstants.DOWN, ControlConstants.LEFT, ControlConstants.RIGHT));
//			view = new Game(stage, new SimpleGameLogic(level), cam, ressourceHandler, keyBindings, new HashMap<>(), axisBindings);
//			view.getGamePresenceHandler().addPlatform(new DiscordGamePresence("663770162188779521", ""));
//			GameLoop loop = new GameLoop(60, view);
//			loop.start();
//		}, "Game-Thread").start();
//	}
	
//	@Override
//	public void stop() throws Exception {
//		super.stop();
//		if (view != null) {
//			view.stopGame();
//		}
//	}

}
