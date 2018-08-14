package projekt.mathe.game.mathespiel.scenes.game.minigames.race;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayerTextureHelper;

public class RaceEnemy extends RaceEntity{

	private static RaceEnemyTextureHelper textures = new RaceEnemyTextureHelper();
	
	public RaceEnemy(Scene container, int x, int y) {
		super(container, x, y, 54, 75, textures, false);
		textures.onTick(5);
	}

	public static class RaceEnemyTextureHelper extends RaceTextures{

		public RaceEnemyTextureHelper() {
			super(new MapPlayerTextureHelper());
		}

		@Override
		public void setSprinting() {
			((MapPlayerTextureHelper) getTextureHelper()).switchPlayerState("up_running");
		}

		@Override
		public void setStill() {
			((MapPlayerTextureHelper) getTextureHelper()).switchPlayerState("up_standing");
		}
		
	}
	
}
