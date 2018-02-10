package projekt.mathe.game.mathespiel.scenes.game.world.entities;

import java.awt.Graphics2D;
import java.awt.Image;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.help.TextureHelper;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;

public class SignEntity extends Entity{
	
	private TextureHelper textureHelper;
	private Dialog dialog;
	
	private static Image[] textures = {
		ResLoader.getImageByName("game/entities/sign.png")
	};
	
	public SignEntity(Scene container, World world, int x, int y, Dialog dialog) {
		super(container, world, x, y, Values.TILE_SIZE, Values.TILE_SIZE, true, true);
		textureHelper = new TextureHelper();
		textureHelper.addState("normal", 10, textures);
		this.dialog = dialog;
	}

	@Override
	public void onTick(float delta) {
		
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(textureHelper.getCurrentImage(), (int) x, (int) y, (int) w, (int) h, null);
	}

	@Override
	public void onInteract(MapPlayer player) {
		world.openDialog(dialog.reset());
	}

}
