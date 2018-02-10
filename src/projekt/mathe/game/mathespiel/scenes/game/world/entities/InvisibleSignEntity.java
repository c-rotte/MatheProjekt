package projekt.mathe.game.mathespiel.scenes.game.world.entities;

import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;

public class InvisibleSignEntity extends SignEntity{

	public InvisibleSignEntity(Scene container, World world, int x, int y, Dialog dialog) {
		super(container, world, x, y, dialog);
	}

	@Override
	public void onPaint(Graphics2D g2d) {}
	
}
