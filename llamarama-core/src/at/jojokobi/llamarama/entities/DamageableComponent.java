package at.jojokobi.llamarama.entities;

import java.util.ArrayList;
import java.util.List;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.ObjectComponent;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.llamarama.characters.DamageCause;

public class DamageableComponent implements ObjectComponent, Damagable {
	
	private Damagable delegate;
	
	public DamageableComponent(Damagable delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public boolean isAlive() {
		return delegate.isAlive();
	}
	
	@Override
	public int getHp() {
		return delegate.getHp();
	}
	
	@Override
	public void heal(int amount) {
		delegate.heal(amount);
	}
	
	@Override
	public void damage(Level level, CharacterComponent damager, int amount, DamageCause cause) {
		delegate.damage(level, damager, amount, cause);
	}

	@Override
	public void update(GameObject object, Level level, UpdateEvent event) {
		
	}

	@Override
	public void clientUpdate(GameObject object, Level level, UpdateEvent event) {
		
	}

	@Override
	public void hostUpdate(GameObject object, Level level, UpdateEvent event) {
		
	}

	@Override
	public void renderBefore(GameObject object, List<RenderData> data, Camera cam, Level level) {
		
	}

	@Override
	public void renderAfter(GameObject object, List<RenderData> data, Camera cam, Level level) {
		
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return new ArrayList<ObservableProperty<?>>();
	}

}
