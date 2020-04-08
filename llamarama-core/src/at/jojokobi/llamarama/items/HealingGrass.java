package at.jojokobi.llamarama.items;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class HealingGrass implements Item {
	
//	public static final Image HEALING_GRASS_IMAGE = new Image(BushTile.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "heal_grass.png"));
//	public static final RenderModel HEALING_GRASS_MODEL = new Image2DModel(HEALING_GRASS_IMAGE);

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public void use(CharacterComponent comp, GameObject obj) {
		comp.heal(comp.getCharacter().getMaxHp()/3);
	}
	
	@Override
	public double getUsePriority(CharacterComponent comp, GameObject obj) {
		return 1 - ((double) comp.getHp()/comp.getCharacter().getMaxHp());
	}

	@Override
	public String getModel() {
		return "item.healing_grass";
	}
	
}
