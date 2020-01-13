package at.jojokobi.llamarama.characters;

import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.LlamaramaApplication;
import at.jojokobi.llamarama.entities.Bullet;
import at.jojokobi.llamarama.entities.CharacterComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ShieldAbility implements Ability {
	
	public static final Image DONUT_RIGHT = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "donut_right.png"));
	public static final Image DONUT_LEFT = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "donut_left.png"));
	public static final Image DONUT_UP = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "donut_up.png"));
	public static final Image DONUT_DOWN = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "donut_down.png"));

	@Override
	public void update(Level level, GameObject object, double delta, CharacterComponent character) {
		
	}

	@Override
	public boolean use(Level level, GameObject object, double delta, CharacterComponent character) {
		boolean used = false;
		List<Bullet> bullets = findBullets(level, object, character);
		for (Bullet bullet : bullets) {
			if (bullet.getShooter() == character) {
				bullet.delete(level);
			}
			else {
				bullet.setxMotion(-bullet.getxMotion());
				bullet.setyMotion(-bullet.getyMotion());
				bullet.setzMotion(-bullet.getzMotion());
				bullet.setShooter(character);
			}
			used = used && Math.random() <= 0.3;
		}
		return used;
	}

	@Override
	public boolean shouldUse(Level level, GameObject object, CharacterComponent character) {
		return !findBullets(level, object, character).isEmpty();
	}

	@Override
	public void render(Level level, GameObject object, CharacterComponent character, GraphicsContext ctx, Camera cam) {
		Vector2D pos = cam.toScreenPosition(new Vector3D(object.getX() - object.getxOffset(), object.getY() - object.getyOffset(), object.getZ() - object.getzOffset()));
		Image img = null;
		switch (character.getDirection()) {
		case DOWN:
			pos.add(0, 32);
			img = DONUT_DOWN;
			break;
		case LEFT:
			pos.add(-32, 0);
			img = DONUT_LEFT;
			break;
		case RIGHT:
			pos.add(32, 0);
			img = DONUT_RIGHT;
			break;
		case UP:
			pos.add(0, -32);
			img = DONUT_UP;
			break;
		default:
			break;
		}
		ctx.drawImage(img, pos.getX(), pos.getY());
	}
	
	private List<Bullet> findBullets (Level level, GameObject object, CharacterComponent character) {
		return object.getObjectsInDirection(level, character.getDirection().getMotion(), 32, Bullet.class);
	}

	@Override
	public double getCooldown() {
		return 2.0;
	}

}
