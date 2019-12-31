package at.jojokobi.llamarama.tiles;

import at.jojokobi.donatengine.objects.Tile;
import at.jojokobi.donatengine.rendering.BoxModel;
import at.jojokobi.donatengine.rendering.RenderModel;
import at.jojokobi.llamarama.LlamaramaApplication;
import javafx.scene.image.Image;

public class WaterTile extends Tile {
	
	public static final Image WATER_IMAGE = new Image(WaterTile.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "tiles/water.png"));
	public static final RenderModel WATER_MODEL = new BoxModel(WATER_IMAGE, WATER_IMAGE);
	
	public WaterTile(double x, double y, double z, String area) {
		super(x, y, z, area, 32, 32, 32, WATER_MODEL);
		setSolid(true);
		setNeedsUpdate(false);
	}
	
	public WaterTile() {
		this(0, 0, 0, "");
	}

}
