package at.jojokobi.llamarama.savegame;

import java.util.List;

public interface UserDao {
	
	public void add (GameUser user);
	
	public void update (GameUser user);
	
	public GameUser get (String username);
	
	public List<GameUser> getAll ();

}
