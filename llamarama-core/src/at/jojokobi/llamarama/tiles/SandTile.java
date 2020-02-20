package at.jojokobi.llamarama.tiles;

import at.jojokobi.donatengine.objects.Tile;

public class SandTile extends Tile {
	
//	public static final Image SAND_IMAGE = new Image(SandTile.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "tiles/sand.png"));
//	public static final RenderModel SAND_MODEL = new BoxModel(SAND_IMAGE, SAND_IMAGE);
	
	public SandTile(double x, double y, double z, String area) {
		super(x, y, z, area, 32, 32, 32, "tile.sand");
		setSolid(true);
		setNeedsUpdate(false);
	}
	
	public SandTile() {
		this(0, 0, 0, "");
	}

}
