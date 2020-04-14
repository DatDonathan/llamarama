package at.jojokobi.llamarama.gamemode;

import java.util.List;
import java.util.Random;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelBoundsComponent;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.entities.EnemyCharacter;
import at.jojokobi.llamarama.gamemode.GameLevel.GameComponent;

public class EnemySpawnerEffect implements GameEffect {
	
	private List<CharacterType> types;
	private double timer;
	private double minInterval;
	private double additionalIntervall;
	private double nextTime;
	private int count;

	public EnemySpawnerEffect(List<CharacterType> types, double minInterval, double additionalIntervall) {
		super();
		this.types = types;
		this.minInterval = minInterval;
		this.additionalIntervall = additionalIntervall;
	}

	@Override
	public void update(Level level, GameComponent comp, double delta) {
		if (comp.isRunning()) {
			if (timer > nextTime) {
				LevelBoundsComponent bounds = level.getComponent(LevelBoundsComponent.class);
				Random random = new Random();
				CharacterType type = types.get(random.nextInt(types.size()));
				EnemyCharacter character = null;
				count++;
				switch (random.nextInt(4)) {
				case 0:
					//Left
					character = new EnemyCharacter(bounds.getPos().getX() + 1, bounds.getPos().getY() + 1, bounds.getPos().getZ() + bounds.getSize().getZ()/2, comp.getStartArea(), type, "Paparazzi " + count);
					break;
				case 1:
					//Right
					character = new EnemyCharacter(bounds.getPos().getX() + bounds.getSize().getX() - type.getWidth() - 1, bounds.getPos().getY() + 1, bounds.getPos().getZ() + bounds.getSize().getZ()/2, comp.getStartArea(), type, "Paparazzi " + count);
					break;
				case 2:
					//Top
					character = new EnemyCharacter(bounds.getPos().getX() + bounds.getSize().getX()/2, bounds.getPos().getY() + 1, bounds.getPos().getZ() + 1, comp.getStartArea(), type, "Paparazzi " + count);
					break;
				case 3:
					//Bottom
					character = new EnemyCharacter(bounds.getPos().getX() + bounds.getSize().getX()/2, bounds.getPos().getY() + 1, bounds.getPos().getZ() + bounds.getSize().getZ() - type.getLength() - 1, comp.getStartArea(), type, "Paparazzi " + count);
					break;
				}
				level.spawn(character);
				nextTime = timer + minInterval + additionalIntervall * random.nextDouble() * intervallMultiplier();
			}
			timer += delta;
		}
		else {
			timer = 0;
			count = 0;
			nextTime = 0;
		}
	}
	
	private double intervallMultiplier () {
		return 1 - Math.min(1, count/100.0);
	}
	
}
