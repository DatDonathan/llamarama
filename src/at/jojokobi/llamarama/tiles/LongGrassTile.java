package at.jojokobi.llamarama.tiles;

import at.jojokobi.donatengine.objects.Tile;
import at.jojokobi.donatengine.rendering.Image2DModel;
import at.jojokobi.donatengine.rendering.RenderModel;
import at.jojokobi.llamarama.LlamaramaApplication;
import javafx.scene.image.Image;

public class LongGrassTile extends Tile {
	
	public static final Image LONG_GRASS_IMAGE = new Image(LongGrassTile.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "tiles/tall_grass.png"));
	public static final RenderModel LONG_GRASS_MODEL = new Image2DModel(LONG_GRASS_IMAGE);
	
	public LongGrassTile(double x, double y, double z, String area) {
		super(x, y, z, area, 32, 32, 32, LONG_GRASS_MODEL);
		setSolid(false);
	}
	
	public LongGrassTile() {
		this(0, 0, 0, "");
	}

}
