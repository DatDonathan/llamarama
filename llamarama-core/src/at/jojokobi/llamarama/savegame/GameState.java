package at.jojokobi.llamarama.savegame;

public class GameState {
	
	private UserDao userDao;
	private String currentUser;
	
	public GameState(UserDao userDao) {
		super();
		this.userDao = userDao;
	}
	
	public GameUser loadCurrentUser () {
		return userDao.get(currentUser);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

}
