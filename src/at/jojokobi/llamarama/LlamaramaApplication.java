package at.jojokobi.llamarama;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.llamarama.characters.CharacterTypeSerializer;
import at.jojokobi.donatengine.GameLoop;
import at.jojokobi.donatengine.GameView;
import at.jojokobi.donatengine.SimpleGameLogic;
import at.jojokobi.donatengine.input.Axis;
import at.jojokobi.donatengine.input.ButtonAxis;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.net.SingleplayerBehavior;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.ressources.IRessourceHandler;
import at.jojokobi.donatengine.ressources.RessourceHandler;
import at.jojokobi.donatengine.serialization.BinarySerialization;
import at.jojokobi.llamarama.characters.CharacterType;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

public class LlamaramaApplication extends Application {
	
	public static final String IMAGE_PATH = "/assets/images/";
	
	private GameView view;
	
	public static void main(String[] args) {
		BinarySerialization.getInstance().registerSerializer(CharacterType.class, new CharacterTypeSerializer());
		launch (args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Llamarama - worlds first tactical llama shooter!");
		new Thread(() -> {
			Level level = new MainMenuLevel(new SingleplayerBehavior());
			Camera cam = new Camera(0, 0, 0, 1280, 768);
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
			keyBindings.put(ControlConstants.PAUSE, KeyCode.ESCAPE);
			Map<String, Axis> axisBindings = new HashMap<>();
			axisBindings.put(ControlConstants.MOVEMENT, new ButtonAxis(ControlConstants.UP, ControlConstants.DOWN, ControlConstants.LEFT, ControlConstants.RIGHT));
			view = new GameView(stage, new SimpleGameLogic(level), cam, ressourceHandler, keyBindings, new HashMap<>(), axisBindings);
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
