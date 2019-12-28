package at.jojokobi.llamarama.gamemode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.gui.DynamicGUIFactory;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.gui.PercentualDimension;
import at.jojokobi.donatengine.gui.SimpleGUI;
import at.jojokobi.donatengine.gui.SimpleGUISystem;
import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.gui.nodes.Button;
import at.jojokobi.donatengine.gui.nodes.HFlowBox;
import at.jojokobi.donatengine.gui.style.FixedDimension;
import at.jojokobi.donatengine.gui.style.FixedStyle;
import at.jojokobi.donatengine.level.ChatComponent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelArea;
import at.jojokobi.donatengine.level.LevelComponent;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.level.TileMapParser;
import at.jojokobi.donatengine.net.MultiplayerBehavior;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.rendering.TwoDimensionalPerspective;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.characters.CharacterTypeProvider;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.NonPlayerCharacter;
import at.jojokobi.llamarama.entities.PlayerCharacter;
import at.jojokobi.llamarama.maps.CSVLoadedMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameLevel extends Level{
	
	public static class StartMatchAction implements GUIAction {

		@Override
		public void serialize(DataOutput buffer) throws IOException {
			
		}

		@Override
		public void deserialize(DataInput buffer) throws IOException {
			
		}

		@Override
		public void perform(Level level, LevelHandler handler, long id, GUISystem system, Camera camera) {
			system.removeGUI(id);
			level.getComponent(GameComponent.class).startMatch(level);
		}

		@Override
		public boolean executeOnClient() {
			return false;
		}
		
	}
	
	public static class SelectCharacterAction implements GUIAction{
		
		private long client;
		private String characterType;
		
		
		
		public SelectCharacterAction(long client, String characterType) {
			super();
			this.client = client;
			this.characterType = characterType;
		}
		
		public SelectCharacterAction() {
			this(0, "");
		}

		@Override
		public void serialize(DataOutput buffer) throws IOException {
			buffer.writeLong(client);
			buffer.writeUTF(characterType);
		}

		@Override
		public void deserialize(DataInput buffer) throws IOException {
			client = buffer.readLong();
			characterType = buffer.readUTF();
		}

		@Override
		public void perform(Level level, LevelHandler handler, long id, GUISystem system, Camera camera) {
			level.getComponent(GameComponent.class).characterChoices.put(client, CharacterTypeProvider.getCharacterTypes().get(characterType));
		}

		@Override
		public boolean executeOnClient() {
			return false;
		}
		
	}
	
	public static class GameComponent implements LevelComponent {
		
		private Map<Long, CharacterType> characterChoices = new HashMap<>();
		private List<Long> connectedClients = new ArrayList<>();
		
		private GameMode gameMode;
		private Vector3D startPos;
		private String startArea;
		private double time;
		private boolean running = false;

		public GameComponent(GameMode gameMode, Vector3D startPos, String startArea) {
			super();
			this.gameMode = gameMode;
			this.startPos = startPos;
			this.startArea = startArea;
		}

		@Override
		public void update(Level level, double delta) {
			time += delta;
			if (running) {
				gameMode.update(level, this, delta);
				if (gameMode.canEndGame(level, this)) {
					endMatch(level);
				}
			}
			else if (gameMode.canStartGame(level, characterChoices, this)) {
				startMatch(level);
			}
		}

		@Override
		public void renderBefore(GraphicsContext ctx, Camera cam, Level level) {
			
		}

		@Override
		public void renderAfter(GraphicsContext ctx, Camera cam, Level level) {
			
		}
		
		@Override
		public void onConnectPlayer(Camera cam, Level level, long id) {
			LevelComponent.super.onConnectPlayer(cam, level, id);
			connectedClients.add(id);
			characterChoices.put(id, CharacterTypeProvider.getCharacterTypes().get("Corporal"));
		}
		
		private void startMatch (Level level) {
			time = 0;
			running = true;
			gameMode.startGame(level, this);
			for (var e : characterChoices.entrySet()) {
				PlayerCharacter player = new PlayerCharacter(startPos.getX(), startPos.getY(), startPos.getZ(), startArea, e.getKey(), e.getValue());
				level.spawn(player);
			}
			characterChoices.clear();
		}
		
		private void endMatch (Level level) {
			level.getComponent(ChatComponent.class).postMessage(gameMode.determineWinner(level, this).getName() + " won the game!");
			gameMode.endGame(level, this);
			init(level);
		}

		@Override
		public List<ObservableProperty<?>> observableProperties() {
			return Arrays.asList();
		}

		public double getTime() {
			return time;
		}

		public boolean isRunning() {
			return running;
		}

		@Override
		public void init(Level level) {
			characterChoices.clear();
			for (Long id : connectedClients) {
				characterChoices.put(id, CharacterTypeProvider.getCharacterTypes().get("Corporal"));
			}
			time = 0;
			running = false;
			
			for (GameObject obj : level.getObjectsWithComponent(CharacterComponent.class)) {
				obj.delete(level);
			}
			
			level.spawn(new NonPlayerCharacter(128, 32, 128, startArea, CharacterTypeProvider.getCharacterTypes().get("Corporal")));
			level.spawn(new NonPlayerCharacter(512, 32, 256, startArea, CharacterTypeProvider.getCharacterTypes().get("Speedy")));
			
			level.getGuiSystem().showGUI(SELECT_CHARACTER_GUI);
		}
		
	}
	
	public static final String SELECT_CHARACTER_GUI = "select_character";
	
	private String mainArea = "main";
	
	
	public GameLevel(MultiplayerBehavior behavior) {
		super(behavior, 0, 0, 0);
		
		addComponent(new ChatComponent());
		addComponent(new GameComponent(new BattleRoyaleGameMode(8, 60), new Vector3D(0, 32, 0), mainArea));
		
		DynamicGUIFactory fact = new DynamicGUIFactory();
		fact.registerGUI(SELECT_CHARACTER_GUI, () -> {
			HFlowBox box = new HFlowBox();
			box.setWidthDimension(new PercentualDimension(1));
			box.setHeightDimension(new PercentualDimension(1));
			//Character Buttons
			for (Map.Entry<String, CharacterType> e : CharacterTypeProvider.getCharacterTypes().entrySet()) {
				Button button = new Button(e.getKey());
				button.setWidthDimension(new FixedDimension(200));
				button.setHeightDimension(new FixedDimension(200));
				button.addStyle(s -> true, new FixedStyle().setMargin(10).setBorderRadius(5.0).setFont(new Font("Consolas", 24)));
				button.addStyle(s -> s.isSelected(), new FixedStyle().setFill(Color.AQUA));
				
				button.setOnAction(() -> new SelectCharacterAction(getClientId(), e.getKey()));
				box.addChild(button);
			}
			//Start Game Button
			Button button = new Button("Start Game");
			button.setWidthDimension(new FixedDimension(200));
			button.setHeightDimension(new FixedDimension(200));
			button.addStyle(s -> true, new FixedStyle().setMargin(10).setBorderRadius(5.0).setFont(new Font("Consolas", 24)));
			button.addStyle(s -> !behavior.isHost(), new FixedStyle().setBorder(Color.GRAY).setFontColor(Color.GRAY));
			
			if (behavior.isHost()) {
				button.setOnAction(() -> new StartMatchAction());
			}
			box.addChild(button);
			
			return new SimpleGUI(box, SELECT_CHARACTER_GUI);
		});
		initGuiSystem(new SimpleGUISystem(fact));
	}

	@Override
	public void generate(Camera camera) {
		addArea(mainArea, new LevelArea());
		new CSVLoadedMap(TileMapParser.loadTilemap(getClass().getResourceAsStream("/assets/maps/online.csv"), 128)).generate(this, new Vector3D(), mainArea);
	}
	
	@Override
	public void start(Camera camera) {
		super.start(camera);
		camera.setPerspective(new TwoDimensionalPerspective());
		camera.setRotationX(80);
		camera.setRenderDistance(32 * 40);
	}
	
}
