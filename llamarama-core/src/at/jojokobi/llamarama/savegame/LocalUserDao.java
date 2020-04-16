package at.jojokobi.llamarama.savegame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import at.jojokobi.donatengine.serialization.structured.ObjectLoader;

public class LocalUserDao implements UserDao {
	
	private File userFolder;
	private String fileExtension;
	private ObjectLoader userLoader;

	public LocalUserDao(File userFolder, String fileExtension, ObjectLoader userLoader) {
		super();
		this.userFolder = userFolder;
		this.fileExtension = fileExtension;
		this.userLoader = userLoader;
	}

	@Override
	public void add(GameUser user) {
		File userFile = new File(userFolder, getFilename(user.getUsername()));
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
		File userFile = new File(userFolder, getFilename(user.getUsername()));
		try (OutputStream out = new FileOutputStream(userFile)) {
			userLoader.save(out, user);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public GameUser get(String username) {
		File userFile = new File(userFolder, getFilename(username));
		if (userFile.exists()) {
			try (InputStream in = new FileInputStream(userFile)) {
				return userLoader.load(in, GameUser.class).create();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		else {
			return null;
		}
	}

	@Override
	public List<GameUser> getAll() {
		List<GameUser> users = new ArrayList<GameUser>();
		for (File file : userFolder.listFiles()) {
			if (file.isFile()) {
				try (InputStream in = new FileInputStream(file)) {
					users.add(userLoader.load(in, GameUser.class).create());
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
		return users;
	}
	
	private String getFilename (String username) {
		return username + "." + fileExtension;
	}
	
}
