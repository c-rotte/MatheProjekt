package projekt.mathe.game.mathespiel.scenes.game.minigames.race;

import java.awt.Image;

import projekt.mathe.game.engine.help.TextureHelper;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayerTextureHelper;

public abstract class RaceTextures{

	private TextureHelper textureHelper;
	
	public RaceTextures(TextureHelper textureHelper) {
		this.textureHelper = textureHelper;
	}
	
	public void onTick(float delta) {
		textureHelper.onTick(delta);
	}
	
	public Image getCurrImage() {
		return textureHelper.getCurrentImage();
	}
	
	public TextureHelper getTextureHelper() {
		return textureHelper;
	}
	
	public abstract void setSprinting();
	
	public abstract void setStill();
	
}
