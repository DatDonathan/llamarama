package at.jojokobi.llamarama.savegame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import at.jojokobi.donatengine.serialization.structured.ObjectLoader;

public class LocalUserDao implements UserDao {
	
	private File userFolder;
	private String fileExtension;
	private ObjectLoader userLoader;

	@Override
	public void add(GameUser user) {
		File userFile = new File(userFolder, getFilename(user));
		if (!userFile.exists()) {
			try (OutputStream out = new FileOutputStream(userFile)) {
				userLoader.save(out, user);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		else {
			throw new IllegalArgumentException("A user with that name already exists!");
		}
	}

	@Override
	public void update(GameUser user) {
		File userFile = new File(userFolder, getFilename(user));
		try (OutputStream out = new FileOutputStream(userFile)) {
			userLoader.save(out, user);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public GameUser get(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GameUser> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String getFilename (GameUser user) {
		return user.getUsername() + "." + fileExtension;
	}
	
}
