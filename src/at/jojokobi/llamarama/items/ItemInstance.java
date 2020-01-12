package at.jojokobi.llamarama.items;

import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.properties.ObjectProperty;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class ItemInstance extends GameObject{
	
	private ObjectProperty<Item> item = new ObjectProperty<Item>(null);
	private double lifetime = 20.0;

	public ItemInstance(double x, double y, double z, String area, Item item) {
		super(x, y, z, area, null);
		this.item.addListener((p, o, i) -> {
			if (i != null) {
				setRenderModel(i.getModel());
			}
		});
		this.item.set(item);
	}
	
	public ItemInstance() {
		this(0, 0, 0, "", null);
	}
	
	@Override
	public void hostUpdate(Level level, LevelHandler handler, Camera camera, double delta) {
		super.hostUpdate(level, handler, camera, delta);
		lifetime -= delta;
		
		boolean used = false;
		for (GameObject obj : getCollided(level)) {
			CharacterComponent comp = obj.getComponent(CharacterComponent.class);
			if (comp != null) {
				item.get().use(comp, obj);
				delete(level);
				used = true;
				break;
			}
		}
		if (lifetime <= 0 && !used) {
			delete(level);
		}
	}
	
	@Override
	public List<ObservableProperty<?>> observableProperties() {
		List<ObservableProperty<?>> props = super.observableProperties();
		props.add(item);
		return props;
	}

}
