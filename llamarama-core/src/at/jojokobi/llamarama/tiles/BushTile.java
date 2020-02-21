package at.jojokobi.llamarama.tiles;

import at.jojokobi.donatengine.objects.Tile;

public class BushTile extends Tile {
	
//	public static final Image BUSH_IMAGE = new Image(BushTile.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "tiles/bush.png"));
//	public static final RenderModel BUSH_MODEL = new Image2DModel(BUSH_IMAGE);
	
	public BushTile(double x, double y, double z, String area) {
		super(x, y, z, area, 1, 1, 1, "tile.bush");
		setSolid(true);
		setNeedsUpdate(false);
	}
	
	public BushTile() {
		this(0, 0, 0, "");
	}

}
