package at.jojokobi.llamarama.tiles;

import at.jojokobi.donatengine.objects.Tile;

public class GrassTile extends Tile {
	
//	public static final Image GRASS_IMAGE = new Image(GrassTile.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "tiles/grass.png"));
//	public static final RenderModel GRASS_MODEL = new BoxModel(GRASS_IMAGE, GRASS_IMAGE);
	
	public GrassTile(double x, double y, double z, String area) {
		super(x, y, z, area, 32, 32, 32, "tile.grass");
		setSolid(true);
		setNeedsUpdate(false);
	}
	
	public GrassTile() {
		this(0, 0, 0, "");
	}

}
