package projekt.mathe.game.mathespiel.scenes.game.world.entities.moving;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.MovingTestDialog;

public class TestMoving extends MovingEntity{

	public TestMoving(Scene container, World world, int x, int y) {
		super(container, world, x, y, Values.TILE_SIZE, Values.TILE_SIZE, 3, true, true);
		addAim(x + 300, y);
		addAim(x + 300, y + 300);
		addAim(x, y + 300);
		addAim(x, y);
	}

	@Override
	public void onInteract(MapPlayer player) {
		world.openDialog(new MovingTestDialog(world));
	}

	@Override
	public void onTick(float delta) {
		if(!world.isDialogOpen()) {
			moveToAim(delta);
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fillRect((int) x, (int) y, (int) w, (int) h);
	}

}
