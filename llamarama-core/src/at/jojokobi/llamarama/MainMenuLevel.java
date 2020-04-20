package at.jojokobi.llamarama;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import at.jojokobi.donatengine.ClientGameLogic;
import at.jojokobi.donatengine.GameLogic;
import at.jojokobi.donatengine.SimpleGameLogic;
import at.jojokobi.donatengine.SimpleServerGameLogic;
import at.jojokobi.donatengine.event.StartEvent;
import at.jojokobi.donatengine.gui.DynamicGUIFactory;
import at.jojokobi.donatengine.gui.PercentualDimension;
import at.jojokobi.donatengine.gui.SimpleGUI;
import at.jojokobi.donatengine.gui.SimpleGUISystem;
import at.jojokobi.donatengine.gui.SimpleGUIType;
import at.jojokobi.donatengine.gui.actions.ChangeGUIAction;
import at.jojokobi.donatengine.gui.actions.ChangeLogicAction;
import at.jojokobi.donatengine.gui.nodes.Button;
import at.jojokobi.donatengine.gui.nodes.HBox;
import at.jojokobi.donatengine.gui.nodes.Text;
import at.jojokobi.donatengine.gui.nodes.TextField;
import at.jojokobi.donatengine.gui.nodes.VBox;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelBoundsComponent;
import at.jojokobi.donatengine.level.SingleplayerBehavior;
import at.jojokobi.donatengine.level.LevelBehavior;
import at.jojokobi.donatengine.net.ClientBehavior;
import at.jojokobi.donatengine.net.HostBehavior;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.presence.GamePresence;
import at.jojokobi.donatengine.style.Color;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.style.Font;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.characters.CharacterTypeProvider;
import at.jojokobi.llamarama.entities.NonPlayerCharacter;
import at.jojokobi.llamarama.gamemode.BattleRoyaleGameMode;
import at.jojokobi.llamarama.gamemode.DebugMode;
import at.jojokobi.llamarama.gamemode.EndlessGameMode;
import at.jojokobi.llamarama.gamemode.GameLevel;
import at.jojokobi.llamarama.gamemode.GameMode;
import at.jojokobi.llamarama.gamemode.InvasionGameMode;
import at.jojokobi.llamarama.maps.LlamaramaTileMapParser;
import at.jojokobi.llamarama.savegame.GameState;
import at.jojokobi.llamarama.savegame.GameStatistic;
import at.jojokobi.llamarama.savegame.GameUser;
import at.jojokobi.llamarama.savegame.StatCategory;
import at.jojokobi.netutil.ServerClientFactory;
import at.jojokobi.netutil.TCPServerClientFactory;
import at.jojokobi.netutil.client.Client;
import at.jojokobi.netutil.server.Server;

public class MainMenuLevel extends Level{
	
	public static final String MAIN_MENU_GUI = "main_menu";
	public static final String SELECT_GAME_GUI = "select_game";
	public static final String STATISTICS_GUI = "statistics";
	
	public static final int[][][] PARK_TILEMAP = 
		{{{4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
		 {4,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,5,5,0,0,0,0,0,0,0,0,0,1,1,1,4},
		 {4,1,1,1,0,0,3,0,0,0,0,0,0,5,5,0,0,0,0,0,1,1,1,0,0,0,5,0,0,3,0,0,0,0,0,0,1,1,1,4},
		 {4,1,1,1,0,0,0,0,0,0,0,3,0,5,0,5,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,4},
		 {4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		 {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,0,0,0,0,0,4,4,4,0,0,0,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
		 {4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,5,5,0,0,4},
		 {4,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,5,5,0,4},
		 {4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,4,0,0,0,0,0,0,0,0,3,0,0,0,4},
		 {4,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,5,5,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,4},
		 {4,1,1,1,0,0,0,0,0,0,0,0,0,0,4,5,5,5,0,0,1,1,1,0,0,0,4,0,0,0,0,0,0,0,0,0,1,1,1,4},
		 {4,1,1,1,0,0,0,0,0,0,0,0,0,0,4,5,5,0,0,0,1,1,1,0,0,0,4,0,0,0,0,0,0,0,0,0,1,1,1,4},
		 {4,1,1,1,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,1,1,1,0,0,0,4,0,0,0,0,0,0,0,0,0,1,1,1,4},
		 {4,0,0,0,0,0,0,0,5,5,0,0,0,0,4,0,0,3,0,0,0,0,0,0,0,0,4,0,3,0,0,0,0,0,0,0,0,0,0,4},
		 {4,0,0,0,0,0,0,0,5,5,5,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,4},
		 {4,0,0,0,0,0,0,0,0,0,5,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		 {4,0,0,0,0,0,0,0,0,0,3,0,0,0,4,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		 {4,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,3,0,0,0,4},
		 {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,0,0,0,0,0,4,4,4,0,0,0,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
		 {4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
		 {4,1,1,1,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,3,0,0,0,0,0,1,1,1,4},
		 {4,1,1,1,0,0,0,5,5,5,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,5,5,0,0,0,1,1,1,4},
		 {4,1,1,1,0,0,0,5,5,0,3,0,0,0,0,0,0,0,0,0,1,1,1,0,0,3,0,0,0,0,0,5,5,0,0,0,1,1,1,4},
		 {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,5,4,4,4,4,4,4,4}}};
	
	private ServerClientFactory factory = TCPServerClientFactory.getInstance();
	private String mainArea = "main";
	
	private GameState state;

	public MainMenuLevel(LevelBehavior behavior, GameState state) {
		super(behavior);
		this.state = state;
		addComponent(new LevelBoundsComponent(new Vector3D(), new Vector3D(40, 24, 24), true));
		
		DynamicGUIFactory fact = new DynamicGUIFactory();
		fact.registerGUI(MAIN_MENU_GUI, new SimpleGUIType<>(Object.class, (data, clientId) -> {
			VBox box = new VBox();
			box.setWidthDimension(new PercentualDimension(1));
			
			//Title
			Text title = new Text("Llamarama");
			title.addStyle(s -> true, new FixedStyle().setFontColor(Color.BLACK).setFont(new Font("Consolas", 72)).setMarginTop(40.0));
			Text subtitle = new Text("worlds first tactical llama shooter");
			subtitle.addStyle(s -> true, new FixedStyle().setFontColor(Color.BLACK).setFont(new Font("Consolas", 24)).setMarginBottom(80.0));
			box.addChild(title);
			box.addChild(subtitle);
			//Buttons
			Button singleplayer = new Button("Singleplayer");
			singleplayer.setWidthDimension(new PercentualDimension(0.3));
			singleplayer.setOnAction(() -> new ChangeGUIAction(SELECT_GAME_GUI, new SelectGameData(m -> new SimpleGameLogic(new GameLevel(new SingleplayerBehavior(), "", false, m, state)))));
			styleButton(singleplayer);
			//Host
			Button hostGame = new Button("Host Game");
			styleButton(hostGame);
			hostGame.setOnAction(() -> new ChangeGUIAction(SELECT_GAME_GUI, new SelectGameData(d -> {
				Server server = null;
				try {
					server = factory.createServer(44444);
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				//Find server IP
				String ip = server.getHostAddress().toString().substring(1);
				System.out.println(ip);
//				try {
//					ip = Inet4Address.getLocalHost().getHostAddress();
//					System.out.println(ip);
//				} catch (UnknownHostException e) {
//					e.printStackTrace();
//				}
//				URL url;
//				try {
//					url = new URL("https://jojokobi.lima-city.de/ip.php");
//					try (InputStream in = url.openStream();
//							Scanner scanner = new Scanner(in)) {
//						ip = scanner.nextLine();
//					} catch (IOException e) {
//						e.printStackTrace();
//						throw new RuntimeException(e);
//					}
//				} catch (MalformedURLException e1) {
//					e1.printStackTrace();
//				}
				return new SimpleServerGameLogic(new GameLevel(new HostBehavior(true), ip, true, d, state), server);
			})));
			//IP input
			TextField ip = new TextField();
			ip.setWidthDimension(new PercentualDimension(0.3));
			ip.addStyle(s -> true, new FixedStyle().setFill(Color.WHITE).setBorder(Color.BLUE).setPadding(10).setFontColor(Color.BLACK).setFont(new Font("Consolas", 24)).setMargin(5.0));

			//Join
			Button joinGame = new Button("Join Game");
			styleButton(joinGame);
			joinGame.setOnAction(() -> new ChangeLogicAction(() -> {
				Client client = null;
				try {
					client = factory.createClient(ip.getText(), 44444);
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				return new ClientGameLogic(new GameLevel(new ClientBehavior(), client.getServerInetAddress() + "", true, null, state), client);
			})); 
			//Statistics
			Button statistics = new Button("Statistics");
			styleButton(statistics);
			statistics.setOnAction(() -> new ChangeGUIAction(STATISTICS_GUI, state.loadCurrentUser()));
			
			box.addChild(singleplayer);
			box.addChild(hostGame);
			box.addChild(ip);
			box.addChild(joinGame);
			box.addChild(statistics);
			
			return new SimpleGUI(box, MAIN_MENU_GUI, null, clientId);
		}));
		fact.registerGUI(SELECT_GAME_GUI, new SimpleGUIType<>(SelectGameData.class, (d, id) -> {
			VBox box = new VBox();
			box.setWidthDimension(new PercentualDimension(1));
			
			List<GameMode> modes = Arrays.asList(new InvasionGameMode(), new BattleRoyaleGameMode(16, 60), new EndlessGameMode(8, 60), new DebugMode());
			for (GameMode mode : modes) {
				Button button = new Button(mode.getName());
				styleButton(button);
				button.setOnAction(() -> new ChangeLogicAction(() -> d.getLogicSupplier().apply(mode)));
				box.addChild(button);
			}
			return new SimpleGUI(box, SELECT_GAME_GUI, d, id);
		}));
		fact.registerGUI(STATISTICS_GUI, new SimpleGUIType<>(GameUser.class, (user, id) -> {
			HBox box = new HBox();
			box.setWidthDimension(new PercentualDimension(1.0));
			box.addStyle(s -> true, new FixedStyle().setPadding(20));
			//Texts
			VBox textBox = new VBox();
			textBox.addStyle(s -> true, new FixedStyle().setFill(Color.CYAN).setBorder(Color.BLUE).setPadding(10).setBorderStrength(2.0));
			textBox.setWidthDimension(new PercentualDimension(0.5));
			textBox.setHeightDimension(new PercentualDimension(1));
			
			Text username = new Text(user.getUsername());
			username.addStyle(s -> true, new FixedStyle().setFont(new Font("Consolas", 32)).setMarginBottom(50.0));
			
			Text kills = new Text("Kills: ");
			kills.addStyle(s -> true, new FixedStyle().setFont(new Font("Consolas", 32)));
			Text deaths = new Text("Deaths: ");
			deaths.addStyle(s -> true, new FixedStyle().setFont(new Font("Consolas", 32)));
			Text highscore = new Text("Highscore: ");
			highscore.addStyle(s -> true, new FixedStyle().setFont(new Font("Consolas", 32)));
			Text kdr = new Text("Kill Death Ratio: ");
			kdr.addStyle(s -> true, new FixedStyle().setFont(new Font("Consolas", 32)));
			textBox.addChild(username);
			textBox.addChild(kills);
			textBox.addChild(deaths);
			textBox.addChild(kdr);
			textBox.addChild(highscore);
			//Categories
			VBox buttonBox = new VBox();
			buttonBox.addStyle(s -> true, new FixedStyle().setFill(Color.CYAN).setBorder(Color.BLUE).setPadding(10).setBorderStrength(2.0));
			buttonBox.setWidthDimension(new PercentualDimension(0.5));
			buttonBox.setHeightDimension(new PercentualDimension(1));
			
			Button allButton = new Button("All");
			styleButton(allButton);
			allButton.setWidthDimension(new PercentualDimension(1));
			allButton.setOnAction(() -> {
				GameStatistic stat = user.getStatistics().getStat(StatCategory.values(), true, false);
				kills.setText("Kills: " + stat.getKills());
				deaths.setText("Deaths: " + stat.getDeaths());
				kdr.setText(String.format("Kill Death Ratio: %.3f", stat.getKillDeathRatio()));
				highscore.setText("Highscore: " + stat.getHighscore());
				return null;
			});
			buttonBox.addChild(allButton);
			
			for (StatCategory category : StatCategory.values()) {
				Button button = new Button(category.toString());
				styleButton(button);
				button.setWidthDimension(new PercentualDimension(1));
				button.setOnAction(() -> {
					GameStatistic stat = user.getStatistics().getStat(new StatCategory[] {category}, true, false);
					kills.setText("Kills: " + stat.getKills());
					deaths.setText("Deaths: " + stat.getDeaths());
					kdr.setText(String.format("Kill Death Ratio: %.3f", stat.getKillDeathRatio()));
					highscore.setText("Highscore: " + stat.getHighscore());
					return null;
				});
				buttonBox.addChild(button);
			}
			box.addChild(textBox);
			box.addChild(buttonBox);

			return new SimpleGUI(box, STATISTICS_GUI, user, id);
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
		parseTilemap(PARK_TILEMAP, new LlamaramaTileMapParser(), mainArea);
		LevelBoundsComponent comp = getComponent(LevelBoundsComponent.class);
		List<String> types = new ArrayList<>(CharacterTypeProvider.getCharacterTypes().keySet());
		//Spawn AIs
		for (int i = 0; i < 8; i++) {		
			NonPlayerCharacter ch = new NonPlayerCharacter(Math.random() * comp.getSize().getX(), 1, Math.random() * comp.getSize().getZ(), mainArea, CharacterTypeProvider.getCharacterTypes().get(types.get(new Random().nextInt(types.size()))));
			spawn(ch);
		}
		camera.setArea(mainArea);
	}
	
	@Override
	public void init() {
		super.init();
		Camera camera = getCamera();
		camera.setRotationX(-90);
		getGuiSystem().showGUI(MAIN_MENU_GUI, null, getClientId());
		camera.setX(20);
		camera.setY(24);
		camera.setZ(12);
	}

	@Override
	public void start(StartEvent event) {
		super.start(event);
		
		GamePresence presence = new GamePresence();
		presence.setDetails("In menu");
		presence.setLargeImageKey("corporal");
		presence.setLargeImageText("corporal");
		event.getGame().getGamePresenceHandler().updatePresence(presence, secret -> {
			System.out.println("Join attempt");
			Client client = null;
			try {
				client = factory.createClient(secret, 44444);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			event.getGame().changeLogic(new ClientGameLogic(new GameLevel(new ClientBehavior(), client.getServerInetAddress() + "", true, null, state), client));
		}, null);
	}

}

class SelectGameData {
	
	private Function<GameMode, GameLogic> logicSupplier;
	

	public SelectGameData(Function<GameMode, GameLogic> logicSupplier) {
		super();
		this.logicSupplier = logicSupplier;
	}

	public Function<GameMode, GameLogic> getLogicSupplier() {
		return logicSupplier;
	}
	
}
