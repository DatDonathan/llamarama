package at.jojokobi.llamarama.entities;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.objects.properties.IntProperty;
import at.jojokobi.donatengine.objects.properties.ObservableObject;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.serialization.binary.BinarySerializable;
import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;

public class Weapon implements BinarySerializable, ObservableObject {
	
	private IntProperty bullets = new IntProperty(0);
	
	public Weapon (int bullets) {
		setBullets(bullets);
	}
	
	public Weapon() {
		this (0);
	}
	
	public int getBullets() {
		return bullets.get();
	}

	public void setBullets(int bullets) {
		this.bullets.set(bullets);
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeInt(getBullets());
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		setBullets(buffer.readInt());
	}

	@Override
	public List<ObservableProperty<?>> observerableProperties() {
		return Arrays.asList(bullets);
	}

	@Override
	public boolean stateChanged() {
		return false;
	}

	@Override
	public void writeChanges(DataOutput out, SerializationWrapper serialization) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readChanges(DataInput in, SerializationWrapper serialization) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
}
