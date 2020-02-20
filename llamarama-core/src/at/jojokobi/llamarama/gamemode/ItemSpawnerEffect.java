package at.jojokobi.llamarama.gamemode;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.llamarama.gamemode.GameLevel.GameComponent;
import at.jojokobi.llamarama.items.Item;
import at.jojokobi.llamarama.items.ItemInstance;

public class ItemSpawnerEffect implements GameEffect {
	
	private List<Supplier<Item>> items;
	private double interval;
	private double chancePerTile;
	private double tileSize;
	
	private double timer;
	
	public ItemSpawnerEffect(List<Supplier<Item>> items, double interval, double chancePerTile, double tileSize) {
		super();
		this.items = items;
		this.interval = interval;
		this.chancePerTile = chancePerTile;
		this.tileSize = tileSize;
	}

	@Override
	public void update(Level level, GameComponent comp, double delta) {
		if (comp.isRunning()) {
			if (timer > interval) {
				Random random = new Random();
				for (double x = 0; x < comp.getCurrentMap().getSize().getX(); x += tileSize) {
					for (double z = 0; z < comp.getCurrentMap().getSize().getZ(); z += tileSize) {
						if (random.nextDouble() < chancePerTile) {
							ItemInstance item = new ItemInstance(comp.getStartPos().getX() + x, comp.getCurrentMap().getSize().getY(), comp.getStartPos().getZ() + z, comp.getStartArea(), items.get(random.nextInt(items.size())).get());
							if (!item.collidesSolid(level)) {
								level.spawn(item);
							}
						}
					}
				}
				timer = 0;
			}
			
			timer += delta;
		}
		else {
			timer = 0;
		}
	}
	
}
