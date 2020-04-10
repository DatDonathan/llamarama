package at.jojokobi.llamarama.entities;

import java.util.List;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.properties.EnumProperty;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.llamarama.characters.DamageCause;
import at.jojokobi.llamarama.characters.Direction;

public class StationaryShield extends GameObject implements Damagable {
	
	private int maxHp = 80;
	private int hp;
	private EnumProperty<Direction> direction = new EnumProperty<>(Direction.LEFT, Direction.class);

	public StationaryShield(double x, double y, double z, String area, Direction direction) {
		super(x, y, z, area, "ability.stationary_shield.left");
		this.hp = maxHp;
		this.direction.addListener((p, o , n) -> {
			switch(n) {
			case DOWN:
				setWidth(2);
				setHeight(2);
				setLength(0.5);
				setxOffset(0);
				setyOffset(0);
				setzOffset(0);
				setRenderTag("ability.stationary_shield.down");
				break;
			case LEFT:
				setWidth(0.5);
				setHeight(2);
				setLength(2);
				setxOffset(0);
				setyOffset(0);
				setzOffset(0);
				setRenderTag("ability.stationary_shield.left");
				break;
			case RIGHT:
				setWidth(0.5);
				setHeight(2);
				setLength(2);
				setxOffset(1.5);
				setyOffset(0);
				setzOffset(0);
				setRenderTag("ability.stationary_shield.right");
				break;
			case UP:
				setWidth(2);
				setHeight(2);
				setLength(0.5);
				setxOffset(0);
				setyOffset(0);
				setzOffset(0);
				setRenderTag("ability.stationary_shield.up");
				break;
			};
		});
		this.direction.set(direction);
		setSolid(true);
	}
	
	@Override
	public void hostUpdate(Level level, UpdateEvent event) {
		super.hostUpdate(level, event);
		if (isAlive()) {
			delete(level);
		}
	}

	@Override
	public boolean isAlive() {
		return hp <= 0;
	}

	@Override
	public int getHp() {
		return hp;
	}

	@Override
	public void heal(int amount) {
		hp += amount;
	}

	@Override
	public void damage(Level level, CharacterComponent damager, int amount, DamageCause cause) {
		hp -= amount;
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		List<ObservableProperty<?>> list = super.observableProperties();
		list.add(direction);
		return list;
	}
	
}
