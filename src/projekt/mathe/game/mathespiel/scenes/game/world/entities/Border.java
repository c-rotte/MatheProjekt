package projekt.mathe.game.mathespiel.scenes.game.world.entities;

import java.awt.Graphics2D;
import java.awt.Image;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public class Border extends Entity{

	private static final Image border = ResLoader.getImageByName("game/entities/border.png");
	
	public Border(Scene container, World world, int x, int y) {
		super(container, world, x, y, 180, 52, true, true);
	}

	@Override
	public void onInteract(MapPlayer player) {
		
	}

	@Override
	public void onTick(float delta) {
		
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(border, (int) getX(), (int) getY(), null);
	}

}
