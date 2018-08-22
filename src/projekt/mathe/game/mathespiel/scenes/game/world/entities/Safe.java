package projekt.mathe.game.mathespiel.scenes.game.world.entities;

import java.awt.Graphics2D;
import java.awt.Image;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public class Safe extends Entity{

	private static final Image safe = ResLoader.getImageByName("game/entities/safe.jpg");
	
	public Safe(Scene container, World world, int x, int y) {
		super(container, world, x, y, 150, 186, true, true);
	}

	@Override
	public void onInteract(MapPlayer player) {
		if(player.direction.equals("up")) {
			getContainer().callScene("safe", getContainer().getDataForNextScene(), 10f);
		}
	}

	@Override
	public void onTick(float delta) {
		
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(safe, (int) getX(), (int) getY(), null);
	}

}
