package at.jojokobi.llamarama.maps;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.TileMapParser;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.llamarama.tiles.BushTile;
import at.jojokobi.llamarama.tiles.GrassTile;
import at.jojokobi.llamarama.tiles.LongGrassTile;
import at.jojokobi.llamarama.tiles.SandTile;
import at.jojokobi.llamarama.tiles.StompTile;
import at.jojokobi.llamarama.tiles.WaterTile;

public class LlamaramaTileMapParser extends TileMapParser {

	@Override
	public GameObject parse(int id, int x, int y, int z, Level level) {
		switch (id) {
		case 0:
			return new GrassTile(x*32, y*32, z*32, "");
		case 1:
			return new SandTile(x*32, y*32, z*32, "");
		case 2:
			return new WaterTile(x*32, y*32, z*32, "");
		case 3:
			level.spawn(new BushTile(x*32, y*32 + 32, z*32, "main"));
			return new GrassTile(x*32, y*32, z*32, "");
		case 4:
			level.spawn(new StompTile(x*32, y*32 + 32, z*32, "main"));
			return new GrassTile(x*32, y*32, z*32, "");
		case 5:
			level.spawn(new LongGrassTile(x*32, y*32 + 32, z*32, "main"));
			return new GrassTile(x*32, y*32, z*32, "");
		}
		return null;
	}

}
