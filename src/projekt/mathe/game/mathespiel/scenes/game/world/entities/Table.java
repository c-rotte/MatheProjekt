package projekt.mathe.game.mathespiel.scenes.game.world.entities;

import java.awt.Graphics2D;
import java.awt.Image;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public class Table extends Entity {

	private static final Image table = ResLoader.getImageByName("game/entities/table.png");
	
	public Table(Scene container, World world, int x, int y) {
		super(container, world, x, y, 100, 50, true, true);
	}

	@Override
	public void onInteract(MapPlayer player) {
		
	}

	@Override
	public void onTick(float delta) {
		
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(table, (int) getX(), (int) getY(), null);
	}

}
