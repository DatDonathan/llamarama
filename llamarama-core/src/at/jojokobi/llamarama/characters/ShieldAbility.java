package at.jojokobi.llamarama.characters;

import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.rendering.ModelRenderData;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.util.Position;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.Bullet;
import at.jojokobi.llamarama.entities.CharacterComponent;

public class ShieldAbility implements Ability {
	
	/*public static final Image DONUT_RIGHT = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "donut_right.png"));
	public static final Image DONUT_LEFT = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "donut_left.png"));
	public static final Image DONUT_UP = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "donut_up.png"));
	public static final Image DONUT_DOWN = new Image(
			CharacterTypeProvider.class.getResourceAsStream(LlamaramaApplication.IMAGE_PATH + "donut_down.png"));*/

	@Override
	public void update(Level level, GameObject object, double delta, CharacterComponent character) {
		
	}

	@Override
	public boolean use(Level level, GameObject object, double delta, CharacterComponent character) {
		boolean used = false;
		List<Bullet> bullets = findBullets(level, object, character);
		for (Bullet bullet : bullets) {
			if (bullet.getShooter() != character) {
//				bullet.delete(level);
//			}
//			else {
				bullet.setxMotion(-bullet.getxMotion());
				bullet.setyMotion(-bullet.getyMotion());
				bullet.setzMotion(-bullet.getzMotion());
				bullet.setShooter(character);
			}
			used = true;
		}
		return used && Math.random() <= 0.3;
	}

	@Override
	public boolean shouldUse(Level level, GameObject object, CharacterComponent character) {
		List<Bullet> bullets = object.getObjectsInDirection(level, character.getDirection().getMotion(), 128, Bullet.class);
		return !bullets.isEmpty() && bullets.stream().allMatch(o -> o.getShooter() != character);
	}

	@Override
	public void render(Level level, GameObject object, CharacterComponent character, List<RenderData> data, Camera cam) {
		Vector3D pos = object.getPositionVector();
		String img = "";
		switch (character.getDirection()) {
		case DOWN:
			pos.add(0, 0, object.getLength());
			img = "ability.donut_shield.down";
			break;
		case LEFT:
			pos.add(-1, 0, 0);
			img = "ability.donut_shield.left";
			break;
		case RIGHT:
			pos.add(object.getWidth(), 0, 0);
			img = "ability.donut_shield.right";
			break;
		case UP:
			pos.add(0, 0, -1);
			img = "ability.donut_shield.up";
			break;
		default:
			break;
		}
		data.add(new ModelRenderData(new Position(pos, object.getArea()), img));
	}
	
	private List<Bullet> findBullets (Level level, GameObject object, CharacterComponent character) {
		return object.getObjectsInDirection(level, character.getDirection().getMotion(), 1, Bullet.class);
	}

	@Override
	public double getCooldown() {
		return 4.0;
	}

}
