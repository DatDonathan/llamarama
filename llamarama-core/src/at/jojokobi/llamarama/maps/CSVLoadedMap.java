package at.jojokobi.llamarama.maps;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.util.Vector3D;

public class CSVLoadedMap implements GameMap{
	
	private int[][][] map;

	public CSVLoadedMap(int[][][] map) {
		super();
		this.map = map;
	}

	@Override
	public void generate(Level level, Vector3D pos, String area) {
		level.parseTilemap(map, new LlamaramaTileMapParser(), area);
	}

	@Override
	public Vector3D getSize() {
		return new Vector3D(map[0][0].length, map.length, map[0].length);
	}

}
