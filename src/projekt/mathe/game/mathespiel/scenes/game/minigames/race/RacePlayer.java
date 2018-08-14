package projekt.mathe.game.mathespiel.scenes.game.minigames.race;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayerTextureHelper;

public class RacePlayer extends RaceEntity{

	private static RacePlayerTextureHelper textures = new RacePlayerTextureHelper();
	
	public RacePlayer(Scene container, int x, int y) {
		super(container, x, y, 54, 75, textures, true);
	}

	public static class RacePlayerTextureHelper extends RaceTextures{

		public RacePlayerTextureHelper() {
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
