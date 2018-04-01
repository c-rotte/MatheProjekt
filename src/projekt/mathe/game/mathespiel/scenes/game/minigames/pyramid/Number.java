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
			g2d.setColor(Color.BLACK);
			g2d.fillRect((int) (looseBlock.getX() + 20), (int) (looseBlock.getY() + looseBlock.getH()/2 - 2), 60, 4);
			Helper.drawStringAroundPosition((int) (looseBlock.getX() + looseBlock.getW()/2), (int) (looseBlock.getY() + looseBlock.getH()/4) - 3, "" + n1, Color.BLACK, 25, FONT.VCR, g2d, null, -1);
			Helper.drawStringAroundPosition((int) (looseBlock.getX() + looseBlock.getW()/2), (int) (looseBlock.getY() + (looseBlock.getH()/4) * 3), "" + n2, Color.BLACK, 25, FONT.VCR, g2d, null, -1);
		}
	}
	
	public int getN1() {
		return n1;
	}
	
	public int getN2() {
		return n2;
	}
	
}
