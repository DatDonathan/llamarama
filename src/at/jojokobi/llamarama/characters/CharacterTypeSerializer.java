package at.jojokobi.llamarama.characters;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import at.jojokobi.donatengine.serialization.BinarySerializer;

public class CharacterTypeSerializer implements BinarySerializer<CharacterType>{

	@Override
	public void serialize(CharacterType t, DataOutput buffer) throws IOException {
		buffer.writeUTF(t.getName());
	}

	@Override
	public CharacterType deserialize(DataInput buffer) throws IOException {
		String id = buffer.readUTF();
		return CharacterTypeProvider.getCharacterTypes().get(id);
	}

	@Override
	public Class<CharacterType> getSerializingClass() {
		return CharacterType.class;
	}
	
}
