package at.jojokobi.llamarama.tiles;

import at.jojokobi.donatengine.tiles.Tile;

public class LongGrassTile extends Tile {
	
	public LongGrassTile() {
		super("tile.long_grass");
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}
	
}
