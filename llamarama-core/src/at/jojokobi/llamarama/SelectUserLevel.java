package at.jojokobi.llamarama;

import at.jojokobi.donatengine.SimpleGameLogic;
import at.jojokobi.donatengine.event.StartEvent;
import at.jojokobi.donatengine.gui.DynamicGUIFactory;
import at.jojokobi.donatengine.gui.PercentualDimension;
import at.jojokobi.donatengine.gui.SimpleGUI;
import at.jojokobi.donatengine.gui.SimpleGUISystem;
import at.jojokobi.donatengine.gui.SimpleGUIType;
import at.jojokobi.donatengine.gui.actions.ChangeGUIAction;
import at.jojokobi.donatengine.gui.actions.ChangeLogicAction;
import at.jojokobi.donatengine.gui.nodes.Button;
import at.jojokobi.donatengine.gui.nodes.Node;
import at.jojokobi.donatengine.gui.nodes.Text;
import at.jojokobi.donatengine.gui.nodes.TextField;
import at.jojokobi.donatengine.gui.nodes.VBox;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelBehavior;
import at.jojokobi.donatengine.level.SingleplayerBehavior;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.style.Color;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.style.Font;
import at.jojokobi.llamarama.savegame.GameState;
import at.jojokobi.llamarama.savegame.GameStatistics;
import at.jojokobi.llamarama.savegame.GameUser;

public class SelectUserLevel extends Level {
	
	private static final String SELECT_USER_GUI = "select_user";
	private static final String CREATE_USER_GUI = "create_user";
	
	private GameState state;

	public SelectUserLevel(LevelBehavior behavior, GameState state) {
		super(behavior);
		this.state = state;
		
		DynamicGUIFactory guiFactory = new DynamicGUIFactory();
		guiFactory.registerGUI(SELECT_USER_GUI, new SimpleGUIType<>(GameState.class, (st, l) -> {
			VBox box = new VBox();
			box.setWidthDimension(new PercentualDimension(1));
			
			//Title
			Text title = new Text("Select a user ...");
			title.addStyle(s -> true, new FixedStyle().setFontColor(Color.BLACK).setFont(new Font("Consolas", 72)).setMarginTop(40.0).setMarginBottom(100.0));
			box.addChild(title);
			//Buttons
			for (GameUser user : st.getUserDao().getAll()) {
				Button button = new Button(user.getUsername());
				styleButton(button);
				button.setOnAction(() -> {
					st.setCurrentUser(user.getUsername());
					return new ChangeLogicAction(() -> new SimpleGameLogic(new MainMenuLevel(new SingleplayerBehavior(), st)));
				});
				box.addChild(button);
			}
			
			Button create = new Button("Create User");
			styleButton(create);
			create.setOnAction(() -> new ChangeGUIAction(CREATE_USER_GUI, st));
			box.addChild(create);

			return new SimpleGUI(box, SELECT_USER_GUI, st, l);
		}));
		guiFactory.registerGUI(CREATE_USER_GUI, new SimpleGUIType<>(GameState.class, (st, l) -> {
			VBox box = new VBox();
			box.setWidthDimension(new PercentualDimension(1));
			
			//Title
			Text title = new Text("Create a user ...");
			title.addStyle(s -> true, new FixedStyle().setFontColor(Color.BLACK).setFont(new Font("Consolas", 72)).setMarginTop(40.0).setMarginBottom(100.0));
			box.addChild(title);
			//Input
			Text nameLabel = new Text("Username:");
			nameLabel.addStyle(s -> true, new FixedStyle().setFontColor(Color.BLACK).setFont(new Font("Consolas", 24)));
			box.addChild(nameLabel);
			
			TextField nameField = new TextField();
			nameField.setText("Cool User");
			styleInput(nameField);
			box.addChild(nameField);
			//Buttons
			Button create = new Button("Create");
			styleButton(create);
			create.setOnAction(() -> {
				if (st.getUserDao().get(nameField.getText()) == null) {
					st.getUserDao().add(new GameUser(nameField.getText(), new GameStatistics()));
				}
				return new ChangeGUIAction(SELECT_USER_GUI, st);
			});
			box.addChild(create);

			return new SimpleGUI(box, CREATE_USER_GUI, st, l);
		}));
		initGuiSystem(new SimpleGUISystem(guiFactory));
	}
	
	private void styleInput (Node button) {
		button.setWidthDimension(new PercentualDimension(0.3));
		button.addStyle(s -> true, new FixedStyle().setBorder(Color.BLUE).setPadding(10).setFontColor(Color.BLACK).setFont(new Font("Consolas", 24)).setMargin(5.0));
	}
	
	private void styleButton (Button button) {
		button.addStyle(s -> true, new FixedStyle().setFill(Color.CYAN).setBorder(Color.BLUE).setPadding(10).setFontColor(Color.BLACK).setFont(new Font("Consolas", 24)).setMargin(5.0));
		button.addStyle(s -> s.isHovered(), new FixedStyle().setFill(Color.BLUE));
		button.addStyle(s -> s.isHovered(), new FixedStyle().setFill(Color.BLUE));
	}

	@Override
	public void generate(Camera camera) {
		
	}

	@Override
	public void start(StartEvent event) {
		super.start(event);
		getGuiSystem().showGUI(SELECT_USER_GUI, state, getClientId());
	}
	
}
