package at.jojokobi.llamarama.characters;

import java.util.ArrayList;
import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.rendering.ModelRenderData;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.util.Position;
import at.jojokobi.donatengine.util.Vector3D;
import at.jojokobi.llamarama.entities.CharacterComponent;
import at.jojokobi.llamarama.entities.bullets.AbstractBullet;
import at.jojokobi.llamarama.entities.bullets.Bullet;
import at.jojokobi.llamarama.entities.bullets.Zap;

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
		List<AbstractBullet> bullets = findBullets(level, object, character);
		for (AbstractBullet bullet : bullets) {
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
	public double getUsePriority(Level level, GameObject object, CharacterComponent character) {
		List<AbstractBullet> bullets = new ArrayList<>();
		bullets.addAll(object.getObjectsInDirection(level, character.getDirection().toVector(), 1, Bullet.class));
		bullets.addAll(object.getObjectsInDirection(level, character.getDirection().toVector(), 1, Zap.class));
		return !bullets.isEmpty() && bullets.stream().allMatch(o -> o.getShooter() != character) ? 1 : 0;
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
		data.add(new ModelRenderData(new Position(pos, object.getArea()), img, object.getAnimationTimer()));
	}
	
	private List<AbstractBullet> findBullets (Level level, GameObject object, CharacterComponent character) {
		List<AbstractBullet> bullets = new ArrayList<>();
		bullets.addAll(object.getObjectsInDirection(level, character.getDirection().toVector(), 1, Bullet.class));
		bullets.addAll(object.getObjectsInDirection(level, character.getDirection().toVector(), 1, Zap.class));
		return bullets;
	}

	@Override
	public double getCooldown() {
		return 3.0;
	}

}
