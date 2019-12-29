package at.jojokobi.llamarama;

import java.io.IOException;

import at.jojokobi.donatengine.ClientGameLogic;
import at.jojokobi.donatengine.SimpleGameLogic;
import at.jojokobi.donatengine.SimpleServerGameLogic;
import at.jojokobi.donatengine.gui.DynamicGUIFactory;
import at.jojokobi.donatengine.gui.PercentualDimension;
import at.jojokobi.donatengine.gui.SimpleGUI;
import at.jojokobi.donatengine.gui.SimpleGUISystem;
import at.jojokobi.donatengine.gui.actions.ChangeLogicAction;
import at.jojokobi.donatengine.gui.nodes.Button;
import at.jojokobi.donatengine.gui.nodes.Text;
import at.jojokobi.donatengine.gui.nodes.TextField;
import at.jojokobi.donatengine.gui.nodes.VBox;
import at.jojokobi.donatengine.gui.style.FixedStyle;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.net.ClientBehavior;
import at.jojokobi.donatengine.net.HostBehavior;
import at.jojokobi.donatengine.net.MultiplayerBehavior;
import at.jojokobi.donatengine.net.SingleplayerBehavior;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.llamarama.gamemode.GameLevel;
import at.jojokobi.netutil.ServerClientFactory;
import at.jojokobi.netutil.TCPServerClientFactory;
import at.jojokobi.netutil.client.Client;
import at.jojokobi.netutil.server.Server;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MainMenuLevel extends Level{
	
	public static final String MAIN_MENU_GUI = "main_menu";

	public MainMenuLevel(MultiplayerBehavior behavior) {
		super(behavior, 0, 0, 0);
		
		DynamicGUIFactory fact = new DynamicGUIFactory();
		fact.registerGUI(MAIN_MENU_GUI, () -> {
			VBox box = new VBox();
			box.setWidthDimension(new PercentualDimension(1));
			
			ServerClientFactory factory = TCPServerClientFactory.getInstance();
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
			singleplayer.setOnAction(() -> new ChangeLogicAction(() -> new SimpleGameLogic(new GameLevel(new SingleplayerBehavior()))));
			singleplayer.addStyle(s -> true, new FixedStyle().setFill(Color.LIGHTBLUE).setBorder(Color.BLUE).setPadding(10).setFontColor(Color.BLACK).setFont(new Font("Consolas", 24)).setMargin(5.0));
			singleplayer.addStyle(s -> s.isHovered(), new FixedStyle().setFill(Color.AQUA));
			//Host
			Button hostGame = new Button("Host Game");
			hostGame.setWidthDimension(new PercentualDimension(0.3));
			hostGame.addStyle(s -> true, new FixedStyle().setFill(Color.LIGHTBLUE).setBorder(Color.BLUE).setPadding(10).setFontColor(Color.BLACK).setFont(new Font("Consolas", 24)).setMargin(5.0));
			hostGame.addStyle(s -> s.isHovered(), new FixedStyle().setFill(Color.AQUA));
			hostGame.setOnAction(() -> new ChangeLogicAction(() -> {
				Server server = null;
				try {
					server = factory.createServer(44444);
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				return new SimpleServerGameLogic(new GameLevel(new HostBehavior(true)), server);
			}));
			//IP input
			TextField ip = new TextField();
			ip.setWidthDimension(new PercentualDimension(0.3));
			ip.addStyle(s -> true, new FixedStyle().setFill(Color.WHITE).setBorder(Color.BLUE).setPadding(10).setFontColor(Color.BLACK).setFont(new Font("Consolas", 24)).setMargin(5.0));

			//Join
			Button joinGame = new Button("Join Game");
			joinGame.setWidthDimension(new PercentualDimension(0.3));
			joinGame.addStyle(s -> true, new FixedStyle().setFill(Color.LIGHTBLUE).setBorder(Color.BLUE).setPadding(10).setFontColor(Color.BLACK).setFont(new Font("Consolas", 24)).setMargin(5.0));
			joinGame.addStyle(s -> s.isHovered(), new FixedStyle().setFill(Color.AQUA));
			joinGame.setOnAction(() -> new ChangeLogicAction(() -> {
				Client client = null;
				try {
					client = factory.createClient(ip.getText(), 44444);
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				return new ClientGameLogic(new GameLevel(new ClientBehavior()), client);
			})); 
			
			box.addChild(singleplayer);
			box.addChild(hostGame);
			box.addChild(ip);
			box.addChild(joinGame);
			
			return new SimpleGUI(box, MAIN_MENU_GUI);
		});
		initGuiSystem(new SimpleGUISystem(fact));
	}

	@Override
	public void generate(Camera camera) {
		
	}

	@Override
	public void start(Camera camera) {
		super.start(camera);
		getGuiSystem().showGUI(MAIN_MENU_GUI);
	}
	
	@Override
	public void spawnPlayer(long client, Camera camera) {
		
	}

}