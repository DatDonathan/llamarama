package at.jojokobi.llamarama.entities;

import java.util.ArrayList;
import java.util.List;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.characters.CharacterTypeProvider;
import at.jojokobi.llamarama.entities.ai.AIComponent;
import at.jojokobi.llamarama.entities.ai.AdvancedPathFinder;
import at.jojokobi.llamarama.entities.ai.AttackTask;
import at.jojokobi.llamarama.entities.ai.CharacterTask;
import at.jojokobi.llamarama.entities.ai.FollowTask;
import at.jojokobi.llamarama.entities.ai.RandomTask;
import at.jojokobi.llamarama.entities.ai.SwapWeaponTask;
import at.jojokobi.llamarama.entities.ai.UseAbilityTask;
import at.jojokobi.llamarama.items.ItemComponent;

public class EnemyCharacter extends CharacterInstance{

	public EnemyCharacter(double x, double y, double z, String area, CharacterType character, String name) {
		super(x, y, z, area, character, name);
		List<CharacterTask> tasks = new ArrayList<>();
		tasks.add(new UseAbilityTask(1.0));
		tasks.add(new FollowTask(o -> o.getComponent(ItemComponent.class) != null && o.getComponent(ItemComponent.class).getItem().getUsePriority(getComponent(CharacterComponent.class), this) >= 0.9, 32, true));
		tasks.add(new UseAbilityTask(0.9));
		tasks.add(new AttackTask(o -> o != this && o.getComponent(DamageableComponent.class) != null && o.getComponent(DamageableComponent.class).isAlive() && !(o instanceof EnemyCharacter), 32)); //TODO remove instanceof
		tasks.add(new FollowTask(o -> o.getComponent(ItemComponent.class) != null && o.getComponent(ItemComponent.class).getItem().getUsePriority(getComponent(CharacterComponent.class), this) >= 0.7, 32, true));
		tasks.add(new SwapWeaponTask());
		tasks.add(new UseAbilityTask(0.7));
		tasks.add(new FollowTask(o -> o.getComponent(ItemComponent.class) != null && o.getComponent(ItemComponent.class).getItem().getUsePriority(getComponent(CharacterComponent.class), this) > 0, 32, true));
		tasks.add(new UseAbilityTask(0.1));
		tasks.add(new RandomTask());
		addComponent(new AIComponent(new AdvancedPathFinder(), tasks, (object, motion) -> {
			if (canControl()) {
				object.setxMotion(motion.getX());
				object.setzMotion(motion.getZ());
			}
		}));
	}
	
	public EnemyCharacter(double x, double y, double z, String area, CharacterType character) {
		this(x, y, z, area, character, character.getName());
	}
	
	public EnemyCharacter() {
		this(0, 0, 0, "", CharacterTypeProvider.getCharacterTypes().get("Corporal"), "Corporal");
	}
	
	@Override
	public void update(Level level, UpdateEvent event) {
		super.update(level, event);
		if (!getComponent(CharacterComponent.class).isAlive()) {
			delete(level);
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
