package projekt.mathe.game.mathespiel.scenes.game.minigames.race;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person.Person;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person.Person.TYPE;

public class RaceEnemy extends RaceEntity{

	private static RaceEnemyTextureHelper textures = new RaceEnemyTextureHelper();
	
	public RaceEnemy(Scene container, int x, int y) {
		super(container, x, y, 54, 75, textures, false);
		textures.onTick(5);
	}

	public static class RaceEnemyTextureHelper extends RaceTextures{

		public RaceEnemyTextureHelper() {
			super(Person.TextureGenerator.generateTextureHelper(TYPE.SPORTS));
		}

		@Override
		public void setSprinting() {
			getTextureHelper().switchState("up_running");
		}

		@Override
		public void setStill() {
			getTextureHelper().switchState("up");
		}
		
	}
	
}
