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

	public ItemInstance(double x, double y, double z, String area, Item item) {
		super(x, y, z, area, item.getModel());
		this.item.set(item);
	}
	
	@Override
	public void hostUpdate(Level level, LevelHandler handler, Camera camera, double delta) {
		super.hostUpdate(level, handler, camera, delta);
		
		for (GameObject obj : getCollided(level)) {
			CharacterComponent comp = obj.getComponent(CharacterComponent.class);
			if (comp != null) {
				item.get().use(comp, obj);
				delete(level);
				break;
			}
		}
	}
	
	@Override
	public List<ObservableProperty<?>> observableProperties() {
		List<ObservableProperty<?>> props = super.observableProperties();
		props.add(item);
		return props;
	}

}
