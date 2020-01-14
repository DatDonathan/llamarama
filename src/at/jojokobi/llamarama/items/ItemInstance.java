package at.jojokobi.llamarama.items;


import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;

public class ItemInstance extends GameObject{
	
	private double lifetime = 20.0;

	public ItemInstance(double x, double y, double z, String area, Item item) {
		super(x, y, z, area, null);
		addComponent(new ItemComponent(item));
	}
	
	public ItemInstance() {
		this(0, 0, 0, "", null);
	}
	
	@Override
	public void hostUpdate(Level level, LevelHandler handler, Camera camera, double delta) {
		super.hostUpdate(level, handler, camera, delta);
		lifetime -= delta;
		if (lifetime <= 0) {
			delete(level);
		}
	}
	
}
