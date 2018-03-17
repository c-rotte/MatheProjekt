package projekt.mathe.game.mathespiel.scenes.game.minigames.pyramid;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;

public class Number {

	private boolean dezimal;
	private int n1, n2;
	private LooseBlock looseBlock;
	
	public Number(int n1, int n2, LooseBlock looseBlock) {
		dezimal = false;
		this.n1 = n1;
		this.n2 = n2;
		this.looseBlock = looseBlock;
	}

	public void onPaint(Graphics2D g2d) {
		if(looseBlock != null) {
			g2d.setColor(Color.DARK_GRAY);
			g2d.fillRect((int) (looseBlock.x + 20), (int) (looseBlock.y + looseBlock.h/2 - 2), 60, 4);
			Helper.drawStringAroundPosition((int) (looseBlock.x + looseBlock.w/2), (int) (looseBlock.y + looseBlock.h/4) - 3, "" + n1, Color.DARK_GRAY, 25, FONT.VCR, g2d, null, -1);
			Helper.drawStringAroundPosition((int) (looseBlock.x + looseBlock.w/2), (int) (looseBlock.y + (looseBlock.h/4) * 3), "" + n2, Color.DARK_GRAY, 25, FONT.VCR, g2d, null, -1);
		}
	}
	
	public int getN1() {
		return n1;
	}
	
	public int getN2() {
		return n2;
	}
	
}
