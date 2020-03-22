package at.jojokobi.llamarama.maps;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.TileMapParser;
import at.jojokobi.donatengine.tiles.TileSystem;
import at.jojokobi.llamarama.tiles.BushTile;
import at.jojokobi.llamarama.tiles.GrassTile;
import at.jojokobi.llamarama.tiles.LongGrassTile;
import at.jojokobi.llamarama.tiles.SandTile;
import at.jojokobi.llamarama.tiles.StompTile;
import at.jojokobi.llamarama.tiles.WaterTile;

public class LlamaramaTileMapParser extends TileMapParser {

	@Override
	public void parse(int id, int x, int y, int z, String area, Level level) {
		TileSystem system = level.getTileSystem();
		switch (id) {
		case 0:
			system.place(new GrassTile(), x, y, z, area);
			break;
		case 1:
			system.place(new SandTile(), x, y, z, area);
			break;
		case 2:
			system.place(new WaterTile(), x, y, z, area);
			break;
		case 3:
			system.place(new BushTile(), x, y + 1, z, area);
			system.place(new GrassTile(), x, y, z, area);
			break;
		case 4:
			system.place(new StompTile(), x, y + 1, z, area);
			system.place(new GrassTile(), x, y, z, area);
			break;
		case 5:
			system.place(new LongGrassTile(), x, y + 1, z, area);
			system.place(new GrassTile(), x, y, z, area);
			break;
		}
	}

}
