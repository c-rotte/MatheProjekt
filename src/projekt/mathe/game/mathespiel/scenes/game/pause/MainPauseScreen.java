package projekt.mathe.game.mathespiel.scenes.game.pause;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.engine.pause.PauseScreen;

public class MainPauseScreen extends PauseScreen{

	private Color backgroundColor;
	private Animator bgAnimator;
	
	public MainPauseScreen(Scene container) {
		super(container, 200, 150, 880, 420);
		setSpeed(50);
		backgroundColor = new Color(0, 0, 0, 150);
		getHolder().addElement(new MainPauseScreenClickable(container, this, 390, 350, 100, 40, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY, new Runnable() {
			@Override
			public void run() {
				container.callScene("menu", container.getDataForNextScene(), 60);
			}
		}).addText("Menu", 30, Color.WHITE).setMaxClickTimes(1));
		getHolder().addElement(new MainPauseScreenClickable(container, this, 350, 270, 180, 40, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY, new Runnable() {
			@Override
			public void run() {
				//Speichervorgang
			}
		}).addText("Speichern", 30, Color.WHITE).setMaxClickTimes(-1));
		bgAnimator = new Animator(xUnterschied, getSPEED());
	}

	@Override
	public void onToggle() {
		bgAnimator.reset();
	}
	
	@Override
	public void onTick(float delta) {
		getHolder().onTick(delta);
		bgAnimator.calculate(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		if(getState().equals("fadingIn") || getState().equals("shown")) {
			getContainer().fillScene(g2d, Color.BLACK, bgAnimator.getCurrValueRelative() / 2f);
		}else if(getState().equals("fadingOut")) {
			getContainer().fillScene(g2d, Color.BLACK, (1f - bgAnimator.getCurrValueRelative()) / 2f);
		}
		g2d.setColor(backgroundColor);
		g2d.fill(getBounds());
		Helper.drawStringAroundPosition(getMiddle().x, (int) (y + 40), "PAUSE", Color.WHITE, 40, FONT.Ailerons, g2d, null, -1);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(getMiddle().x - 60, (int) (y + 63), 120, 2);
		getHolder().onPaint(g2d);
	}

}
