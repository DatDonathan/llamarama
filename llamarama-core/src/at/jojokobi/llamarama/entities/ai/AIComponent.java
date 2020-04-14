package at.jojokobi.llamarama.entities.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.ObjectComponent;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class AIComponent implements ObjectComponent{
	
	private PathFinder pathFinder = new AdvancedPathFinder();
	private CharacterTask currentTask;
	private List<CharacterTask> tasks = new ArrayList<>();
	private Vector3D goal;
	private Mover mover;

	public AIComponent(PathFinder pathFinder, List<CharacterTask> tasks, Mover mover) {
		super();
		this.pathFinder = pathFinder;
		this.tasks = tasks;
		this.mover = mover;
	}

	@Override
	public void update(GameObject object, Level level, UpdateEvent event) {
		
	}

	@Override
	public void clientUpdate(GameObject object, Level level, UpdateEvent event) {
		
	}

	@Override
	public void hostUpdate(GameObject object, Level level, UpdateEvent event) {
		CharacterComponent comp = object.getComponent(CharacterComponent.class);
		//Update AI
		CharacterTask newTask = null;
		for (CharacterTask task : tasks) {
			if (newTask == null && task.canApply(level, object, comp)) {
				newTask = task;
			}
		}
		//Switch task
		if (newTask != currentTask) {
			if (currentTask != null) {
				currentTask.deactivate(level, object, comp);
			}
			currentTask = newTask;
			if (currentTask != null) {
				currentTask.activate(level, object, comp);
			}
		}
		//Apply AI
		if (currentTask != null) {
			goal = currentTask.apply(level, object, comp, event.getDelta());
		}
		else {
			goal = null;
		}
		Vector3D motion = new Vector3D();
		if (goal != null) {
			motion = pathFinder.findMotion(goal, comp.getCharacter().getSpeed(), level, event.getDelta(), object, comp);
		}

		if (!comp.canMove()) {
			motion.multiply(0);
		}
		mover.move(object, motion);
	}

	@Override
	public void renderBefore(GameObject object, List<RenderData> data, Camera cam, Level level) {
		
	}

	@Override
	public void renderAfter(GameObject object, List<RenderData> data, Camera cam, Level level) {
		
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList();
	}

}
