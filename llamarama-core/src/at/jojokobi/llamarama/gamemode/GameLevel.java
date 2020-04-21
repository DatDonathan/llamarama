package at.jojokobi.llamarama.gamemode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.SimpleGameLogic;
import at.jojokobi.donatengine.event.StartEvent;
import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.gui.DynamicGUIFactory;
import at.jojokobi.donatengine.gui.GUI;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.gui.PercentualDimension;
import at.jojokobi.donatengine.gui.SimpleGUI;
import at.jojokobi.donatengine.gui.SimpleGUISystem;
import at.jojokobi.donatengine.gui.SimpleGUIType;
import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.gui.nodes.Button;
import at.jojokobi.donatengine.gui.nodes.HFlowBox;
import at.jojokobi.donatengine.gui.nodes.ListView;
import at.jojokobi.donatengine.gui.nodes.Text;
import at.jojokobi.donatengine.gui.nodes.TextField;
import at.jojokobi.donatengine.gui.nodes.VBox;
import at.jojokobi.donatengine.level.ChatComponent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelArea;
import at.jojokobi.donatengine.level.LevelBoundsComponent;
import at.jojokobi.donatengine.level.LevelComponent;
import at.jojokobi.donatengine.level.SingleplayerBehavior;
import at.jojokobi.donatengine.level.LevelBehavior;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.properties.ObjectProperty;
import at.jojokobi.donatengine.objects.properties.ObservableObjectProperty;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.objects.properties.map.ObservableMap;
import at.jojokobi.donatengine.platform.GamePlatform;
import at.jojokobi.donatengine.presence.GamePresence;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.rendering.RenderRect;
import at.jojokobi.donatengine.rendering.RenderShape;
import at.jojokobi.donatengine.rendering.RenderText;
import at.jojokobi.donatengine.rendering.ScreenCanvasRenderData;
import at.jojokobi.donatengine.serialization.binary.BinarySerializable;
import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;
import at.jojokobi.donatengine.style.Color;
import at.jojokobi.donatengine.style.FixedDimension;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.style.Font;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.ControlConstants;
import at.jojokobi.llamarama.MainMenuLevel;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.characters.CharacterTypeProvider;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.NonPlayerCharacter;
import at.jojokobi.llamarama.entities.PlayerCharacter;
import at.jojokobi.llamarama.maps.GameMap;
import at.jojokobi.llamarama.savegame.GameState;
import at.jojokobi.llamarama.savegame.GameStatistic;
import at.jojokobi.llamarama.savegame.GameUser;
import at.jojokobi.llamarama.savegame.StatCategory;

public class GameLevel extends Level{
	
	private static final String CRAB_EMOJI = Character.toString(0x1F980);
	private static final String[] AI_NAMES = {
		"GamingChungus",
		"RobloxGamerGirl2007",
		"YoshiGaming2007",
		"OfficerMain",
		"Wholesome69",
		"Keanu_man",
		"BigBoiBobby",
		"PufferfishEatingCarrot",
		CRAB_EMOJI + "Crab Emoji" + CRAB_EMOJI,
		"FortniteBadKeanuChungus",
		"xX_Skiller-Pro_Xx",
		"Sudschuk_Skiller",
		"MannVonUnvergleichlicherEhre",
		"DabbinNinja",
		"HyperExcel"
	};
	
	public static class StartMatchAction implements GUIAction {

		@Override
		public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
			
		}

		@Override
		public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
			
		}

		@Override
		public void perform(Level level, Game game, long id, GUISystem system) {
			if (!level.getComponent(GameComponent.class).isRunning() && level.getClientId() == system.getGUI(id).getClient()) {
				level.getComponent(GameComponent.class).startMatch(level, game);
			}
			system.removeGUI(id);
		}

		@Override
		public boolean executeOnClient() {
			return false;
		}
		
	}
	
	public static class LeaveGameAction implements GUIAction {

		@Override
		public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
			
		}

		@Override
		public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
			
		}

		@Override
		public void perform(Level level, Game game, long id, GUISystem system) {
			GameComponent comp = level.getComponent(GameComponent.class);
			if (comp.isRunning()) {
				comp.endMatch(level, game);
			}
			game.changeLogic(new SimpleGameLogic(new MainMenuLevel(new SingleplayerBehavior(), comp.state)));
		}

		@Override
		public boolean executeOnClient() {
			return true;
		}
		
	}
	
	public static class SelectCharacterAction implements GUIAction{
		
		private String characterType;
		private String name;
		
		public SelectCharacterAction(String characterType, String name) {
			super();
			this.characterType = characterType;
			this.name = name;
		}
		
		public SelectCharacterAction() {
			this("", "");
		}

		@Override
		public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
			buffer.writeUTF(characterType);
			buffer.writeUTF(name);
		}

		@Override
		public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
			characterType = buffer.readUTF();
			name = buffer.readUTF();
		}

		@Override
		public void perform(Level level, Game game, long id, GUISystem system) {
			GUI gui = system.getGUI(id);
			long client = gui.getClient();
			level.getComponent(GameComponent.class).characterChoices.get().put(client, new PlayerInformation(CharacterTypeProvider.getCharacterTypes().get(characterType), name.isEmpty() ? characterType : name));
			system.removeGUI(id);
			system.showGUI(LIST_CHARACTERS_GUI, null, client);
			System.out.println("Showing list to " + client);
		}

		@Override
		public boolean executeOnClient() {
			return false;
		}
		
	}
	
	public static class PlayerInformation implements BinarySerializable {
		
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
		
		@Override
		public String toString() {
			return name + " (" + character.getName() + ")";
		}

		@Override
		public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
			serialization.serialize(character, buffer);
			buffer.writeUTF(name);
		}

		@Override
		public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
			character = serialization.deserialize(CharacterType.class, buffer);
			name = buffer.readUTF();
		}
		
	}
	
	public static class GameComponent implements LevelComponent {
		
		private ObservableObjectProperty<ObservableMap<Long, PlayerInformation>> characterChoices = new ObservableObjectProperty<ObservableMap<Long,PlayerInformation>>(new ObservableMap<Long,PlayerInformation>());
		private List<Long> connectedClients = new ArrayList<>();
		
		private List<GameEffect> gameEffects;
		
		private ObjectProperty<GameMode> gameMode = new ObjectProperty<>(null);
		private GameState state;
		private Vector3D startPos;
		private String startArea;
		private GameMap currentMap;
		private double time;
		private boolean running = false;
		private String connectionString;
		private boolean online;
		private UUID partyId = UUID.randomUUID();

		public GameComponent(GameMode gameMode, String connectionString, boolean online, GameState state, Vector3D startPos, String startArea) {
			super();
			this.gameMode.set(gameMode);
			this.connectionString = connectionString;
			this.online = online;
			this.state = state;
			this.startPos = startPos;
			this.startArea = startArea;
		}

		@Override
		public void hostUpdate(Level level, UpdateEvent event) {
			time += event.getDelta();
			if (level.getBehavior().isHost()) {
				if (running) {
					gameMode.get().update(level, this, event.getDelta());
					if (gameMode.get().canEndGame(level, this)) {
						endMatch(level, event.getGame());
					}
				}
				else if (gameMode.get().canStartGame(level, characterChoices.get(), this)) {
					startMatch(level, event.getGame());
				}
				gameEffects.forEach(e -> e.update(level, this, event.getDelta()));
			}
		}
		
		@Override
		public void onConnectPlayer(Level level, long id) {
			LevelComponent.super.onConnectPlayer(level, id);
			connectedClients.add(id);
			if (!isRunning()) {
				level.getGuiSystem().showGUI(SELECT_CHARACTER_GUI, null, id);
			}
		}
		
		void startMatch (Level level, Game game) {
			GamePresence presence = new GamePresence();
			presence.setState("In Match as " + state.getCurrentUser());
			presence.setDetails(gameMode.get().getName());
			presence.setPartySize(connectedClients.size());
			presence.setPartyMax(gameMode.get().getMaxPlayers());
			presence.setStartTimestamp(System.currentTimeMillis());
			presence.setLargeImageKey("corporal");
			presence.setLargeImageText("corporal");
			presence.setPartyId(partyId.toString());
			game.getGamePresenceHandler().updatePresence(presence, null, null);
			
			time = 0;
			running = true;
			level.getGuiSystem().clear();
			level.clear();
			
			gameEffects = gameMode.get().createEffects();
			
			currentMap = gameMode.get().getPossibleMaps().get(new Random().nextInt(gameMode.get().getPossibleMaps().size()));
			LevelBoundsComponent bounds = level.getComponent(LevelBoundsComponent.class);
			bounds.setPos(startPos);
			Vector3D size = currentMap.getSize();
			size.setY(64);
			bounds.setSize(size);
			currentMap.generate(level, startPos, startArea);
			for (var e : characterChoices.get().entrySet()) {
				PlayerCharacter player = new PlayerCharacter(startPos.getX() + Math.random() * currentMap.getSize().getX(), startPos.getY() + 1, startPos.getZ() + Math.random() * currentMap.getSize().getZ(), startArea, e.getKey(), e.getValue().getCharacter(), e.getValue().getName());
				level.spawn(player);
			}
			List<String> types = new ArrayList<>(CharacterTypeProvider.getCharacterTypes().keySet());
			for (int i = characterChoices.get().size(); i < gameMode.get().getMaxPlayers(); i++) {
				NonPlayerCharacter player = new NonPlayerCharacter(startPos.getX() + Math.random() * currentMap.getSize().getX(), startPos.getY() + 1, startPos.getZ() + Math.random() * currentMap.getSize().getZ(), startArea, CharacterTypeProvider.getCharacterTypes().get(types.get(new Random().nextInt(types.size()))), AI_NAMES[new Random().nextInt(AI_NAMES.length)]);
				level.spawn(player);
			}
			
//			level.spawn(new NonPlayerCharacter(512, 1, 512, startArea, CharacterTypeProvider.getCharacterTypes().get("Corporal")));
			gameMode.get().startGame(level, this);
			characterChoices.get().clear();
		}
		
		void endMatch (Level level, Game game) {	
			Winner winner = gameMode.get().determineWinner(level, this);
			level.getComponent(ChatComponent.class).postMessage(winner.getName() + " won the game with " + winner.getKills() + " kills!", 5000);
			//Set score
			if (gameMode.get().getCategory() != null) {
				GameUser user = state.loadCurrentUser();
				for (ScoreboardEntry entry : gameMode.get().getScoreboardEntries(level, this, true)) {
					if (entry.isUser(user, level)) {
						GameStatistic stat = user.getStatistics().getStat(new StatCategory[] {gameMode.get().getCategory()}, online);
						stat.setDeaths(stat.getDeaths() + entry.getDeaths());
						stat.setKills(stat.getKills() + entry.getKills());
						stat.setHighscore(Math.max(stat.getHighscore(), entry.getScore()));
						user.getStatistics().putStat(stat, gameMode.get().getCategory(), online);
						state.getUserDao().update(user);
						break;
					}
				}
			}
			gameMode.get().endGame(level, this);
			initGame(level, game);
		}

		@Override
		public List<ObservableProperty<?>> observableProperties() {
			return Arrays.asList(gameMode, characterChoices);
		}

		public double getTime() {
			return time;
		}

		public boolean isRunning() {
			return running;
		}

		@Override
		public void init(Level level, StartEvent event) {
			initGame(level, event.getGame());
		}
		
		private void initGame (Level level, Game game) {
			GamePresence presence = new GamePresence();
			presence.setState("In Lobby");
			presence.setDetails(gameMode.get().getName());
			presence.setPartySize(connectedClients.size());
			presence.setPartyMax(gameMode.get().getMaxPlayers());
			presence.setStartTimestamp(System.currentTimeMillis());
			presence.setLargeImageKey("corporal");
			presence.setLargeImageText("corporal");
			presence.setPartyId(partyId.toString());
			presence.setJoinSecret(connectionString);
			game.getGamePresenceHandler().updatePresence(presence, null, r -> {
				System.out.println("Someone else joins");
				level.getComponent(ChatComponent.class).postMessage(r.getUsername() + " joined via " + r.getPlatform() + "!", 5000);
				return true;
			});
			
			characterChoices.get().clear();
//			for (Long id : connectedClients) {
//				characterChoices.put(id, new PlayerInformation(CharacterTypeProvider.getCharacterTypes().get("Corporal"), "Corporal"));
//			}
			time = 0;
			running = false;
			gameEffects = new ArrayList<>();
			
			for (GameObject obj : level.getObjectsWithComponent(CharacterComponent.class)) {
				obj.delete(level);
			}
			
			for (long client : connectedClients) {
				System.out.println("Showing to " + client);
				level.getGuiSystem().showGUI(SELECT_CHARACTER_GUI, null, client);
			}
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
		public void update(Level level, UpdateEvent event) {
			
		}

		@Override
		public void clientUpdate(Level level, UpdateEvent event) {
			
		}
		
		@Override
		public void renderAfter(List<RenderData> data, Camera cam, Level level) {
			LevelComponent.super.renderAfter(data, cam, level);
			
			Font font = new Font("Consolas", 16);
			if (gameMode.get() != null) {
				List<ScoreboardEntry> entries = gameMode.get().getScoreboardEntries(level, this, false);
				
				double width = 0;
				double height = 4;
				for (ScoreboardEntry entry : entries) {
					Vector2D dim = GamePlatform.getFontSystem().calculateTextDimensions(entry.getName(), font);
					width = Math.max(width, dim.getX());
					height += dim.getY();
				}
				width += 30 + 30 + 2;
				
				//Scoreboard
				List<RenderShape> shapes = new ArrayList<> ();
				shapes.add(new RenderRect(new Vector2D(cam.getViewWidth() - width, 0), width, height, new FixedStyle().reset().setBorder(Color.TRANSPARENT).setFill(new Color(0.4, 0.4, 0.4, 0.5))));
				double y = 2;
				//Names
				for (ScoreboardEntry entry : entries) {
					Vector2D dim = GamePlatform.getFontSystem().calculateTextDimensions(entry.getName(), font);
					shapes.add(new RenderText(new Vector2D(cam.getViewWidth() - width + 2, y), entry.getName(), new FixedStyle().reset().setFont(font)));
					shapes.add(new RenderText(new Vector2D(cam.getViewWidth() - 30, y), entry.getScore() + "", new FixedStyle().reset().setFont(font)));
					y += dim.getY();
				}
				data.add(new ScreenCanvasRenderData(new Vector2D(0, 0), shapes));
			}
		}
		
	}
	
	public static final String SELECT_CHARACTER_GUI = "select_character";
	public static final String LIST_CHARACTERS_GUI = "list_characters";
	public static final String PAUSE_MENU_GUI = "pause_menu";
	
	private String mainArea = "main";
	
	public GameLevel(LevelBehavior behavior, String connectionString, boolean online, GameMode mode, GameState state) {
		super(behavior);
		addComponent(new ChatComponent());
		addComponent(new LevelBoundsComponent(new Vector3D(), new Vector3D(128, 64, 128), true));
		GameComponent comp = new GameComponent(mode, connectionString, online, state, new Vector3D(0, 0, 0), mainArea);
		addComponent(comp);
		
		DynamicGUIFactory fact = new DynamicGUIFactory();
		//Select Player GUI
		fact.registerGUI(SELECT_CHARACTER_GUI, new SimpleGUIType<>(Object.class, (data, client) -> {
			PlayerInformation info = new PlayerInformation(CharacterTypeProvider.getCharacterTypes().get("Corporal"), "Nickname");
			
			HFlowBox box = new HFlowBox();
			box.setWidthDimension(new PercentualDimension(1));
			box.setHeightDimension(new PercentualDimension(1));
			//Nickname Field
			TextField nickname = new TextField();
			nickname.setText(state.getCurrentUser());
			nickname.setWidthDimension(new PercentualDimension(1));
			nickname.addStyle(s -> true, new FixedStyle().setBorderRadius(5.0).setFont(new Font("Consolas", 24)));
			//Character Buttons
			for (Map.Entry<String, CharacterType> e : CharacterTypeProvider.getCharacterTypes().entrySet()) {
				Button button = new Button(e.getKey());
				button.setWidthDimension(new FixedDimension(200));
				button.setHeightDimension(new FixedDimension(200));
				button.addStyle(s -> true, new FixedStyle().setMargin(10).setBorderRadius(5.0).setFont(new Font("Consolas", 24)));
				button.addStyle(s -> s.isSelected(), new FixedStyle().setFill(Color.CYAN));
				
				button.setOnAction(() -> {
					info.setCharacter(e.getValue());
					return null;
				});
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
			Button button = new Button("Select");
			button.setWidthDimension(new FixedDimension(200));
			button.setHeightDimension(new FixedDimension(200));
			button.addStyle(s -> true, new FixedStyle().setMargin(10).setBorderRadius(5.0).setFont(new Font("Consolas", 24)));
			
			button.setOnAction(() -> new SelectCharacterAction(info.getCharacter().getName(), nickname.getText()));
			box.addChild(button);
			
			return new SimpleGUI(box, SELECT_CHARACTER_GUI, data, client);
		}));
		//List characters gui
		fact.registerGUI(LIST_CHARACTERS_GUI, new SimpleGUIType<>(Object.class, (data, client) -> {
			VBox box = new VBox();
			box.setWidthDimension(new PercentualDimension(1));
			
			ListView<PlayerInformation> playerView = new ListView<PlayerInformation>(() -> Arrays.asList(comp.characterChoices.get().entrySet().stream().map(e -> e.getValue()).toArray(PlayerInformation[]::new)));
			playerView.addStyle(s -> true, new FixedStyle().setMargin(10).setBorder(Color.BLACK).setBorderStrength(2.0).setFont(new Font("Consolas", 24)));
			playerView.setHeightDimension(new FixedDimension(500));
			playerView.setWidthDimension(new PercentualDimension(0.5));
			box.addChild(playerView);
			
			Button startButton = new Button("Start Game");
			startButton.addStyle(s -> true, new FixedStyle().setMargin(10).setBorderRadius(5.0).setFont(new Font("Consolas", 24)));
			startButton.addStyle(s -> !behavior.isHost(), new FixedStyle().setBorder(Color.GRAY).setFontColor(Color.GRAY));
			startButton.setWidthDimension(new PercentualDimension(0.5));
			startButton.setOnAction(() -> new StartMatchAction());
			
			if (behavior.isHost()) {
				box.addChild(startButton);
			}
			
			return new SimpleGUI(box, LIST_CHARACTERS_GUI, data, client);
		}));
		fact.registerGUI(PAUSE_MENU_GUI, new SimpleGUIType<>(Object.class, (data, client) -> {
			VBox box = new VBox ();
			box.setWidthDimension(new PercentualDimension(1));
			
			//Title
			Text title = new Text("Menu");
			title.addStyle(s -> true, new FixedStyle().setFontColor(Color.BLACK).setFont(new Font("Consolas", 72)).setMarginTop(40.0).setMarginBottom(200.0));
			box.addChild(title);
			//Buttons
			Button returnButton = new Button("Return to Main Menu");
			returnButton.setOnAction(() -> new LeaveGameAction());
			styleButton(returnButton);
			box.addChild(returnButton);
			
			return new SimpleGUI(box, PAUSE_MENU_GUI, data, client);
		}));
		initGuiSystem(new SimpleGUISystem(fact));
	}
	
	private void styleButton (Button button) {
		button.setWidthDimension(new PercentualDimension(0.3));
		button.addStyle(s -> true, new FixedStyle().setFill(Color.CYAN).setBorder(Color.BLUE).setPadding(10).setFontColor(Color.BLACK).setFont(new Font("Consolas", 24)).setMargin(5.0));
		button.addStyle(s -> s.isHovered(), new FixedStyle().setFill(Color.BLUE));
	}

	@Override
	public void generate(Camera camera) {
		addArea(mainArea, new LevelArea());
	}
	
	@Override
	public void update(UpdateEvent event) {
		super.update(event);
		/*if (getBehavior().isClient()) {
			if (event.getInput().getInput().getButton(ControlConstants.CAM_UP)) {
				getCamera().setRotationX(getCamera().getRotationX() + 60 * event.getDelta());
			}
			else if (event.getInput().getInput().getButton(ControlConstants.CAM_DOWN)) {
				getCamera().setRotationX(getCamera().getRotationX() - 60 * event.getDelta());
			}
		}*/
		//TODO Open GUI on ESC
		for (var p : event.getInput().getInputs()) {
			if (p.getValue().isPressed(ControlConstants.PAUSE)) {
				if (getGuiSystem().getGUIs().entrySet().stream().allMatch(e -> !p.getKey().equals(e.getValue().getClient()) || !PAUSE_MENU_GUI.equals(e.getValue().getType()))) {
					getGuiSystem().showGUI(PAUSE_MENU_GUI, null, p.getKey());
				}
				else {
					for (var e : getGuiSystem().getGUIs().entrySet()) {
						if (p.getKey().equals(e.getValue().getClient()) && PAUSE_MENU_GUI.equals(e.getValue().getType())) {
							getGuiSystem().removeGUI(e.getKey());
						}
					}
				}
			}
		}
	}
	
	@Override
	public void init() {
		super.init();
		Camera camera = getCamera();
		camera.setRotationX(-90);
	}
	
}
