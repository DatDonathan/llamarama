package at.jojokobi.llamarama.entities;

import java.util.ArrayList;
import java.util.List;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.characters.CharacterTypeProvider;
import at.jojokobi.llamarama.entities.ai.AdvancedPathFinder;
import at.jojokobi.llamarama.entities.ai.AttackTask;
import at.jojokobi.llamarama.entities.ai.CharacterTask;
import at.jojokobi.llamarama.entities.ai.FollowTask;
import at.jojokobi.llamarama.entities.ai.PathFinder;
import at.jojokobi.llamarama.entities.ai.RandomTask;
import at.jojokobi.llamarama.entities.ai.UseAbilityTask;
import at.jojokobi.llamarama.items.ItemComponent;

public class NonPlayerCharacter extends CharacterInstance {
	
	private PathFinder pathFinder = new AdvancedPathFinder();
	private CharacterTask currentTask;
	private List<CharacterTask> tasks = new ArrayList<>();
	private Vector3D goal;

	public NonPlayerCharacter(double x, double y, double z, String area, CharacterType character) {
		super(x, y, z, area, character, character.getName());
		tasks.add(new FollowTask(o -> o.getComponent(ItemComponent.class) != null && o.getComponent(ItemComponent.class).getItem().getUsePriority(getComponent(CharacterComponent.class), this) >= 0.9, 32, true));
		tasks.add(new UseAbilityTask());
		tasks.add(new AttackTask(o -> o != this && o.getComponent(CharacterComponent.class) != null && o.getComponent(CharacterComponent.class).isAlive(), 32));
		tasks.add(new FollowTask(o -> o.getComponent(ItemComponent.class) != null && o.getComponent(ItemComponent.class).getItem().getUsePriority(getComponent(CharacterComponent.class), this) >= 0.7, 32, true));
		tasks.add(new FollowTask(o -> o != this && o.getComponent(CharacterComponent.class) != null && o.getComponent(CharacterComponent.class).isAlive(), 32, true));
		tasks.add(new FollowTask(o -> o.getComponent(ItemComponent.class) != null && o.getComponent(ItemComponent.class).getItem().getUsePriority(getComponent(CharacterComponent.class), this) > 0, 32, true));
		tasks.add(new RandomTask());
	}
	
	public NonPlayerCharacter() {
		this(0, 0, 0, "", CharacterTypeProvider.getCharacterTypes().get("Corporal"));
	}
	
	@Override
	public void hostUpdate(Level level, UpdateEvent event) {
		super.hostUpdate(level, event);
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
			goal = currentTask.apply(level, this, comp, event.getDelta());
		}
		else {
			goal = null;
		}
		Vector3D motion = new Vector3D();
		double speed = comp.getCharacter().getSpeed();
		if (goal != null) {
			motion = pathFinder.findMotion(goal, comp.getCharacter().getSpeed(), level, event.getDelta(), this, comp);
		}
		if (getxMotion() >= -speed && getxMotion() <= speed) {
			setxMotion(motion.getX());
		}
		if (getzMotion() >= -speed && getzMotion() <= speed) {
			setzMotion(motion.getZ());
		}
	}
	
	@Override
	public void render(List<RenderData> data, Camera cam, Level level) {
		if (getComponent(CharacterComponent.class).isAlive()) {
			super.render(data, cam, level);
		}
	}
	
	@Override
	public String toString() {
		CharacterComponent comp = getComponent(CharacterComponent.class);
		return comp.getName() + " [" + comp.isAlive() + "]";
	}
	
}
