package at.jojokobi.llamarama.items;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.ObjectComponent;
import at.jojokobi.donatengine.objects.properties.ObjectProperty;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class ItemComponent implements ObjectComponent {
	
	private ObjectProperty<Item> item = new ObjectProperty<Item>(null);
	
	public ItemComponent (Item item) {
		this.item.set(item);
	}
	
	@Override
	public void onSpawn(GameObject object, Level level) {
		ObjectComponent.super.onSpawn(object, level);
		object.setRenderTag(item.get().getModel());
	}

	@Override
	public void update(GameObject object, Level level, UpdateEvent event) {

	}

	public Item getItem() {
		return item.get();
	}

	public void setItem(Item item) {
		this.item.set(item);
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList(item);
	}

	@Override
	public void clientUpdate(GameObject object, Level level, UpdateEvent event) {
		
	}

	@Override
	public void hostUpdate(GameObject object, Level level, UpdateEvent event) {
		for (GameObject obj : object.getCollided(level)) {
			CharacterComponent comp = obj.getComponent(CharacterComponent.class);
			if (comp != null && comp.isAlive()) {
				item.get().use(comp, obj);
				object.delete(level);
				break;
			}
		}
	}

	@Override
	public void renderBefore(GameObject object, List<RenderData> data, Camera cam, Level level) {
		
	}

	@Override
	public void renderAfter(GameObject object, List<RenderData> data, Camera cam, Level level) {
		
	}
	
}
