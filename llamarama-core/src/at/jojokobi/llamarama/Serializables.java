package at.jojokobi.llamarama;

import at.jojokobi.donatengine.serialization.binary.BinarySerialization;
import at.jojokobi.llamarama.characters.CharacterType;
import at.jojokobi.llamarama.characters.CharacterTypeSerializer;
import at.jojokobi.llamarama.characters.Direction;
import at.jojokobi.llamarama.entities.Bomb;
import at.jojokobi.llamarama.entities.Bullet;
import at.jojokobi.llamarama.entities.NonPlayerCharacter;
import at.jojokobi.llamarama.entities.PlayerCharacter;
import at.jojokobi.llamarama.entities.Puddle;
import at.jojokobi.llamarama.entities.Weapon;
import at.jojokobi.llamarama.gamemode.BattleRoyaleGameMode;
import at.jojokobi.llamarama.gamemode.DebugMode;
import at.jojokobi.llamarama.gamemode.EndlessGameMode;
import at.jojokobi.llamarama.gamemode.GameLevel.PlayerInformation;
import at.jojokobi.llamarama.gamemode.GameLevel.SelectCharacterAction;
import at.jojokobi.llamarama.gamemode.GameLevel.StartMatchAction;
import at.jojokobi.llamarama.items.HealingGrass;
import at.jojokobi.llamarama.items.ItemInstance;
import at.jojokobi.llamarama.items.SpitBucket;
import at.jojokobi.llamarama.tiles.BushTile;
import at.jojokobi.llamarama.tiles.GrassTile;
import at.jojokobi.llamarama.tiles.LongGrassTile;
import at.jojokobi.llamarama.tiles.SandTile;
import at.jojokobi.llamarama.tiles.StompTile;
import at.jojokobi.llamarama.tiles.WaterTile;

public class Serializables {

	public static void register () {
		BinarySerialization.getInstance().registerSerializer(CharacterType.class, new CharacterTypeSerializer());
		
		BinarySerialization.getInstance().getIdClassFactory().addClass(CharacterType.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(Direction.class);
		
		BinarySerialization.getInstance().getIdClassFactory().addClass(Bullet.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(Puddle.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(Bomb.class);
		
		BinarySerialization.getInstance().getIdClassFactory().addClass(NonPlayerCharacter.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(PlayerCharacter.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(Weapon.class);
		
		BinarySerialization.getInstance().getIdClassFactory().addClass(StartMatchAction.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(SelectCharacterAction.class);
		
		BinarySerialization.getInstance().getIdClassFactory().addClass(HealingGrass.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(ItemInstance.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(SpitBucket.class);
		
		BinarySerialization.getInstance().getIdClassFactory().addClass(BushTile.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(GrassTile.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(LongGrassTile.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(SandTile.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(StompTile.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(WaterTile.class);

		BinarySerialization.getInstance().getIdClassFactory().addClass(BattleRoyaleGameMode.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(EndlessGameMode.class);
		BinarySerialization.getInstance().getIdClassFactory().addClass(DebugMode.class);
		
		BinarySerialization.getInstance().getIdClassFactory().addClass(PlayerInformation.class);
	}
	
}
