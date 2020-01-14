package at.jojokobi.llamarama.items;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.ObjectComponent;
import at.jojokobi.donatengine.objects.properties.ObjectProperty;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.llamarama.entities.CharacterComponent;
import javafx.scene.canvas.GraphicsContext;

public class ItemComponent implements ObjectComponent {
	
	private ObjectProperty<Item> item = new ObjectProperty<Item>(null);
	
	public ItemComponent (Item item) {
		this.item.set(item);
	}
	
	@Override
	public void onSpawn(GameObject object, Level level) {
		ObjectComponent.super.onSpawn(object, level);
		object.setRenderModel(item.get().getModel());
	}

	@Override
	public void update(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
		for (GameObject obj : object.getCollided(level)) {
			CharacterComponent comp = obj.getComponent(CharacterComponent.class);
			if (comp != null && comp.isAlive()) {
				item.get().use(comp, obj);
				object.delete(level);
				break;
			}
		}
	}

	public Item getItem() {
		return item.get();
	}

	public void setItem(Item item) {
		this.item.set(item);
	}

	@Override
	public void clientUpdate(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hostUpdate(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderBefore(GameObject object, GraphicsContext ctx, Camera cam, Level level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderAfter(GameObject object, GraphicsContext ctx, Camera cam, Level level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList(item);
	}
	
}
