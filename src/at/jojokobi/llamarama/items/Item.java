package at.jojokobi.llamarama.items;

import at.jojokobi.donatengine.javafx.rendering.RenderModel;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.serialization.BinarySerializable;
import at.jojokobi.llamarama.entities.CharacterComponent;

public interface Item extends BinarySerializable{
	
	public void use (CharacterComponent comp, GameObject obj);
	
	public double getUsePriority (CharacterComponent comp, GameObject obj);
	
	public RenderModel getModel ();

}
