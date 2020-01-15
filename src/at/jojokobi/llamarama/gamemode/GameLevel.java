package at.jojokobi.llamarama.gamemode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import at.jojokobi.donatengine.gui.DynamicGUIFactory;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.gui.PercentualDimension;
import at.jojokobi.donatengine.gui.SimpleGUI;
import at.jojokobi.donatengine.gui.SimpleGUISystem;
import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.gui.nodes.Button;
import at.jojokobi.donatengine.gui.nodes.HFlowBox;
import at.jojokobi.donatengine.gui.nodes.TextField;
import at.jojokobi.donatengine.gui.nodes.VBox;
import at.jojokobi.donatengine.gui.style.FixedDimension;
import at.jojokobi.donatengine.gui.style.FixedStyle;
import at.jojokobi.donatengine.level.ChatComponent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelArea;
import at.jojokobi.donatengine.level.LevelBoundsComponent;
import at.jojokobi.donatengine.level.LevelComponent;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.net.MultiplayerBehavior;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.presence.GamePresence;
import at.jojokobi.donatengine.rendering.TwoDimensionalPerspective;
import at.jojokobi.donatengine.serialization.SerializationWrapper;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.characters.CharacterTypeProvider;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.NonPlayerCharacter;
import at.jojokobi.llamarama.entities.PlayerCharacter;
import at.jojokobi.llamarama.maps.GameMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameLevel extends Level{
	
	public static class StartMatchAction implements GUIAction {

		@Override
		public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
			
		}

		@Override
		public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
			
		}

		@Override
		public void perform(Level level, LevelHandler handler, long id, GUISystem system, Camera camera) {
			system.removeGUI(id);
			if (!level.getComponent(GameComponent.class).isRunning()) {
				level.getComponent(GameComponent.class).startMatch(level, handler);
			}
		}

		@Override
		public boolean executeOnClient() {
			return false;
		}
		
	}
	
	public static class SelectCharacterAction implements GUIAction{
		
		private long client;
		private String characterType;
		private String name;
		
		
		
		public SelectCharacterAction(long client, String characterType, String name) {
			super();
			this.client = client;
			this.characterType = characterType;
			this.name = name;
		}
		
		public SelectCharacterAction() {
			this(0, "", "");
		}

		@Override
		public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
			buffer.writeLong(client);
			buffer.writeUTF(characterType);
			buffer.writeUTF(name);
		}

		@Override
		public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
			client = buffer.readLong();
			characterType = buffer.readUTF();
			name = buffer.readUTF();
		}

		@Override
		public void perform(Level level, LevelHandler handler, long id, GUISystem system, Camera camera) {
			level.getComponent(GameComponent.class).characterChoices.put(client, new PlayerInformation(CharacterTypeProvider.getCharacterTypes().get(characterType), name.isEmpty() ? characterType : name));
		}

		@Override
		public boolean executeOnClient() {
			return false;
		}
		
	}
	
	public static class PlayerInformation {
		
		private CharacterType character;
		private String name;
		
		public PlayerInformation(CharacterType character, String name) {
			super();
			this.character = character;
			this.name = name;
		}
		
		public PlayerInformation() {
			
		}

		public CharacterType getCharacter() {
			return character;
		}

		public void setCharacter(CharacterType character) {
			this.character = character;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public static class GameComponent implements LevelComponent {
		
		private Map<Long, PlayerInformation> characterChoices = new HashMap<>();
		private List<Long> connectedClients = new ArrayList<>();
		
		private List<GameEffect> gameEffects;
		
		private GameMode gameMode;
		private Vector3D startPos;
		private String startArea;
		private GameMap currentMap;
		private double time;
		private boolean running = false;
		private String connectionString;
		private UUID partyId = UUID.randomUUID();

		public GameComponent(GameMode gameMode, String connectionString, Vector3D startPos, String startArea) {
			super();
			this.gameMode = gameMode;
			this.connectionString = connectionString;
			this.startPos = startPos;
			this.startArea = startArea;
		}

		@Override
		public void hostUpdate(Level level, LevelHandler handler, Camera cam, double delta) {
			time += delta;
			if (level.getBehavior().isHost()) {
				if (running) {
					gameMode.update(level, this, delta);
					if (gameMode.canEndGame(level, this)) {
						endMatch(level, handler);
					}
				}
				else if (gameMode.canStartGame(level, characterChoices, this)) {
					startMatch(level, handler);
				}
				gameEffects.forEach(e -> e.update(level, this, delta));
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
			characterChoices.put(id, new PlayerInformation(CharacterTypeProvider.getCharacterTypes().get("Corporal"), "Corporal"));
		}
		
		private void startMatch (Level level, LevelHandler handler) {
			GamePresence presence = new GamePresence();
			presence.setState("In Match");
			presence.setDetails("Battle Royale");
			presence.setPartySize(connectedClients.size());
			presence.setPartyMax(gameMode.getMaxPlayers());
			presence.setStartTimestamp(System.currentTimeMillis());
			presence.setLargeImageKey("corporal");
			presence.setLargeImageText("corporal");
			presence.setPartyId(partyId.toString());
			handler.getGamePresenceHandler().updatePresence(presence, null, null);
			
			time = 0;
			running = true;
			level.clear();
			
			gameEffects = gameMode.createEffects();
			
			currentMap = gameMode.getPossibleMaps().get(new Random().nextInt(gameMode.getPossibleMaps().size()));
			LevelBoundsComponent bounds = level.getComponent(LevelBoundsComponent.class);
			bounds.setPos(startPos);
			Vector3D size = currentMap.getSize();
			size.setY(32 * 64);
			bounds.setSize(size);
			currentMap.generate(level, startPos, startArea);
			for (var e : characterChoices.entrySet()) {
				PlayerCharacter player = new PlayerCharacter(startPos.getX() + Math.random() * currentMap.getSize().getX(), startPos.getY() + 32, startPos.getZ() + Math.random() * currentMap.getSize().getZ(), startArea, e.getKey(), e.getValue().getCharacter(), e.getValue().getName());
				level.spawn(player);
			}
			List<String> types = new ArrayList<>(CharacterTypeProvider.getCharacterTypes().keySet());
			for (int i = characterChoices.size(); i < gameMode.getMaxPlayers(); i++) {
				NonPlayerCharacter player = new NonPlayerCharacter(startPos.getX() + Math.random() * currentMap.getSize().getX(), startPos.getY() + 32, startPos.getZ() + Math.random() * currentMap.getSize().getZ(), startArea, CharacterTypeProvider.getCharacterTypes().get(types.get(new Random().nextInt(types.size()))));
				level.spawn(player);
			}
			
//			level.spawn(new NonPlayerCharacter(512, 32, 512, startArea, CharacterTypeProvider.getCharacterTypes().get("Corporal")));
			gameMode.startGame(level, this);
			characterChoices.clear();
		}
		
		private void endMatch (Level level, LevelHandler handler) {			
			level.getComponent(ChatComponent.class).postMessage(gameMode.determineWinner(level, this).getName() + " won the game!");
			gameMode.endGame(level, this);
			init(level, handler);
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
		public void init(Level level, LevelHandler handler) {
			initGame(level, handler);
		}
		
		private void initGame (Level level, LevelHandler handler) {
			GamePresence presence = new GamePresence();
			presence.setState("In Lobby");
			presence.setDetails("Battle Royale");
			presence.setPartySize(connectedClients.size());
			presence.setPartyMax(gameMode.getMaxPlayers());
			presence.setStartTimestamp(System.currentTimeMillis());
			presence.setLargeImageKey("corporal");
			presence.setLargeImageText("corporal");
			presence.setPartyId(partyId.toString());
			presence.setJoinSecret(connectionString);
			handler.getGamePresenceHandler().updatePresence(presence, null, r -> {
				System.out.println("Someone else joins");
				level.getComponent(ChatComponent.class).postMessage(r.getUsername() + " joined via " + r.getPlatform() + "!");
				return true;
			});
			
			characterChoices.clear();
			for (Long id : connectedClients) {
				characterChoices.put(id, new PlayerInformation(CharacterTypeProvider.getCharacterTypes().get("Corporal"), "Corporal"));
			}
			time = 0;
			running = false;
			gameEffects = new ArrayList<>();
			
			for (GameObject obj : level.getObjectsWithComponent(CharacterComponent.class)) {
				obj.delete(level);
			}
			
			level.getGuiSystem().showGUI(SELECT_CHARACTER_GUI);
		}

		public Vector3D getStartPos() {
			return startPos;
		}

		public String getStartArea() {
			return startArea;
		}

		public GameMap getCurrentMap() {
			return currentMap;
		}

		@Override
		public void update(Level level, LevelHandler handler, Camera cam, double delta) {
			
		}

		@Override
		public void clientUpdate(Level level, LevelHandler handler, Camera cam, double delta) {
			
		}
		
	}
	
	public static final String SELECT_CHARACTER_GUI = "select_character";
	
	private String mainArea = "main";
	
	
	public GameLevel(MultiplayerBehavior behavior, String connectionString) {
		super(behavior, 0, 0, 0);
		
		addComponent(new ChatComponent());
		addComponent(new LevelBoundsComponent(new Vector3D(), new Vector3D(128 * 32, 64 * 32, 128 * 32), true));
		addComponent(new GameComponent(new EndlessGameMode(8, 60), connectionString, new Vector3D(0, 0, 0), mainArea));
		
		DynamicGUIFactory fact = new DynamicGUIFactory();
		//Select Player GUI
		fact.registerGUI(SELECT_CHARACTER_GUI, () -> {
			HFlowBox box = new HFlowBox();
			box.setWidthDimension(new PercentualDimension(1));
			box.setHeightDimension(new PercentualDimension(1));
			//Nickname Field
			TextField nickname = new TextField();
			nickname.setText("Nickname");
			nickname.setWidthDimension(new PercentualDimension(1));
			nickname.addStyle(s -> true, new FixedStyle().setBorderRadius(5.0).setFont(new Font("Consolas", 24)));
			//Character Buttons
			for (Map.Entry<String, CharacterType> e : CharacterTypeProvider.getCharacterTypes().entrySet()) {
				Button button = new Button(e.getKey());
				button.setWidthDimension(new FixedDimension(200));
				button.setHeightDimension(new FixedDimension(200));
				button.addStyle(s -> true, new FixedStyle().setMargin(10).setBorderRadius(5.0).setFont(new Font("Consolas", 24)));
				button.addStyle(s -> s.isSelected(), new FixedStyle().setFill(Color.AQUA));
				
				button.setOnAction(() -> new SelectCharacterAction(getClientId(), e.getKey(), nickname.getText()));
				box.addChild(button);
			}
			//Nickname box
			VBox nicknameBox = new VBox();
			nicknameBox.addStyle(s -> true, new FixedStyle().setMargin(10).setBorderRadius(5.0).setBorder(Color.GRAY).setBorderStrength(2.0).setPadding(5).setFont(new Font("Consolas", 24)));
			nicknameBox.addChild(nickname);
			nicknameBox.setWidthDimension(new FixedDimension(200));
			nicknameBox.setHeightDimension(new FixedDimension(200));
			box.addChild(nicknameBox);
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
	}
	
	@Override
	public void start(Camera camera, LevelHandler handler) {
		super.start(camera, handler);
		camera.setPerspective(new TwoDimensionalPerspective());
		camera.setRotationX(90);
		camera.setRenderDistance(32 * 40);
	}
	
	@Override
	public synchronized void update(double delta, LevelHandler handler, Camera camera) {
		super.update(delta, handler, camera);
	}
	
}
