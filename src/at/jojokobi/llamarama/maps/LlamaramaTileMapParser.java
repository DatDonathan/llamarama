package at.jojokobi.llamarama.maps;

import java.util.ArrayList;
import java.util.List;

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
	public List<GameObject> parse(int id, double x, double y, double z) {
		List<GameObject> objs = new ArrayList<>();
		switch (id) {
		case 0:
			objs.add(new GrassTile(x, y, z, ""));
			break;
		case 1:
			objs.add(new SandTile(x, y, z, ""));
			break;
		case 2:
			objs.add(new WaterTile(x, y, z, ""));
			break;
		case 3:
			objs.add(new BushTile(x, y + 32, z, ""));
			objs.add(new GrassTile(x, y, z, ""));
			break;
		case 4:
			objs.add(new StompTile(x, y + 32, z, ""));
			objs.add(new GrassTile(x, y, z, ""));
			break;
		case 5:
			objs.add(new LongGrassTile(x, y + 32, z, ""));
			objs.add(new GrassTile(x, y, z, ""));
			break;
		}
		return objs;
	}

}
