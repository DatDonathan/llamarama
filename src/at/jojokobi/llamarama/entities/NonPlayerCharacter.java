package at.jojokobi.llamarama.entities;

import java.util.ArrayList;
import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.characters.CharacterTypeProvider;
import javafx.scene.canvas.GraphicsContext;

public class NonPlayerCharacter extends CharacterInstance {
	
	private PathFinder pathFinder = new DirectPathFinder();
	private CharacterTask currentTask;
	private List<CharacterTask> tasks = new ArrayList<>();
	private Vector3D goal;

	public NonPlayerCharacter(double x, double y, double z, String area, CharacterType character) {
		super(x, y, z, area, character, character.getName());
		tasks.add(new FollowTask(o -> o.getComponent(CharacterComponent.class) != null && o.getComponent(CharacterComponent.class).isAlive(), 1024, false));
		tasks.add(new RandomTask());
	}
	
	public NonPlayerCharacter() {
		this(0, 0, 0, "", CharacterTypeProvider.getCharacterTypes().get("Corporal"));
	}
	
	@Override
	public void hostUpdate(Level level, LevelHandler handler, Camera camera, double delta) {
		super.hostUpdate(level, handler, camera, delta);
		CharacterComponent comp = getComponent(CharacterComponent.class);
		//Update AI
		CharacterTask newTask = null;
		for (CharacterTask task : tasks) {
			if (newTask == null && task.canApply(level, this, comp)) {
				newTask = task;
			}
		}
		//Switch task
		if (newTask != currentTask) {
			if (currentTask != null) {
				currentTask.deactivate(level, this, comp);
			}
			currentTask = newTask;
			if (currentTask != null) {
				currentTask.activate(level, this, comp);
			}
		}
		//Apply AI
		if (currentTask != null) {
			goal = currentTask.apply(level, this, comp, delta);
		}
		else {
			goal = null;
		}
		if (goal != null) {
			Vector3D motion = pathFinder.findMotion(goal, comp.getCharacter().getSpeed(), level, this, comp);
			setxMotion(motion.getX());
			setyMotion(motion.getY());
			setzMotion(motion.getZ());
		}
	}
	
	@Override
	public void render(GraphicsContext ctx, Camera cam, Level level) {
		if (getComponent(CharacterComponent.class).isAlive()) {
			super.render(ctx, cam, level);
		}
	}
	
}
