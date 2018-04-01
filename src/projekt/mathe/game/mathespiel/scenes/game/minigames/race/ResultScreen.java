package projekt.mathe.game.mathespiel.scenes.game.minigames.race;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.sql.ResultSet;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;

public class ResultScreen extends ScreenElement{

	private int absolutX, absolutY;
	private String answeredState;
	private Animator alpha;
	private boolean finished;
	
	public ResultScreen(Scene container) {
		super(container, -1, -1, 100, -1);
		absolutX = 640;
		absolutY = 500;
		alpha = new Animator(100, 1);
	}

	public boolean isFinished() {
		return finished;
	}

	public String getAnsweredState() {
		return answeredState;
	}
	
	public void reset(String answeredState) {
		this.answeredState = answeredState;
		alpha.reset();
		finished = false;
	}
	
	@Override
	public void onTick(float delta) {
		setX(getContainer().camera.translateAbsolutX(absolutX));
		setY(getContainer().camera.translateAbsolutY(absolutY));
		alpha.calculate(delta);
		if(alpha.finished() && !finished) {
			finished = true;
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		Composite composite = g2d.getComposite();
		if(alpha.getCurrValueRelative() <= .25) {
			float value = alpha.getCurrValueRelative() * 4;
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, value));
		}else if(alpha.getCurrValueRelative() >= .75) {
			float value = (1 - alpha.getCurrValueRelative()) * 4;
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, value));
		}
		if(answeredState.equals("t")) {
			Helper.drawStringAroundPosition((int) getX(), (int) getY(), "RICHTIG", Color.WHITE, (int) getW(), FONT.VCR, g2d, Color.GREEN, 10f);
		}else if(answeredState.equals("f")){
			Helper.drawStringAroundPosition((int) getX(), (int) getY(), "FALSCH", Color.WHITE, (int) getW(), FONT.VCR, g2d, Color.RED, 10f);
		}else if(answeredState.equals("n")){
			Helper.drawStringAroundPosition((int) getX(), (int) getY(), "ZU SPÄT", Color.WHITE, (int) getW(), FONT.VCR, g2d, Color.DARK_GRAY, 10f);
		}
		g2d.setComposite(composite);
	}

}
