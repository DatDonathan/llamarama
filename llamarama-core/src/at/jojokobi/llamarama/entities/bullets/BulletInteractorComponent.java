package at.jojokobi.llamarama.entities.bullets;

import java.util.ArrayList;
import java.util.List;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.ObjectComponent;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.rendering.RenderData;

public class BulletInteractorComponent implements ObjectComponent, BulletInteractor {
	
	private BulletInteractor delegate;
	

	public BulletInteractorComponent(BulletInteractor delegate) {
		super();
		this.delegate = delegate;
	}
	
	@Override
	public boolean block(CollisionDamager bullet) {
		return delegate.block(bullet);
	}

	@Override
	public boolean canBeDamaged(CollisionDamager bullet) {
		return delegate.canBeDamaged(bullet);
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
