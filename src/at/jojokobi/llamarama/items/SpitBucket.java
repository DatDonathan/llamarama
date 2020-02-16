package at.jojokobi.llamarama.items;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import at.jojokobi.donatengine.javafx.rendering.Image2DModel;
import at.jojokobi.donatengine.javafx.rendering.RenderModel;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.serialization.SerializationWrapper;
import at.jojokobi.donatengine.util.Pair;
import at.jojokobi.llamarama.LlamaramaApplication;
import at.jojokobi.llamarama.characters.WeaponType;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.Weapon;
import at.jojokobi.llamarama.tiles.BushTile;
import javafx.scene.image.Image;

public class SpitBucket implements Item {
	
	public static final Image SPIT_BUCKET_IMAGE = new Image(BushTile.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "water.png"));
	public static final RenderModel SPIT_BUCKET_MODEL = new Image2DModel(SPIT_BUCKET_IMAGE);

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public void use(CharacterComponent comp, GameObject obj) {
		comp.reloadWeapon(comp.getCurrentWeapon().getKey().getMaxBullets()/3);
	}

	@Override
	public RenderModel getModel() {
		return SPIT_BUCKET_MODEL;
	}
	
	@Override
	public double getUsePriority(CharacterComponent comp, GameObject obj) {
		Pair<WeaponType, Weapon> weapon = comp.getCurrentWeapon();
		return 1 - ((double) weapon.getValue().getBullets()/weapon.getKey().getMaxBullets());
	}

}
