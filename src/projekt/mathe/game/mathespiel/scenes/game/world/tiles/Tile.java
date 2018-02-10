package projekt.mathe.game.mathespiel.scenes.game.world.tiles;

import java.awt.Graphics2D;
import java.awt.Image;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.TextureHelper;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public abstract class Tile extends ScreenElement{
	
	public World world;
	private TextureHelper textureHelper;
	public boolean belowPlayer;
	
	public Tile(Scene container, World world, int x, int y, int w, int h, boolean belowPlayer) {
		super(container, x, y, w, h);
		this.world = world;
		textureHelper = new TextureHelper();
		this.belowPlayer = belowPlayer;
	}
	
	@Override
	public void onTick(float delta) {
		textureHelper.onTick(delta);
	}
	
	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(textureHelper.getCurrentImage(), (int) x, (int) y, (int) w, (int) h, null);
	}
	
	public void addTextures(String mode, Image[] images, int steps) {
		textureHelper.addState(mode, steps, images);
	}

}
