package projekt.mathe.game.mathespiel.scenes;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.engine.help.ResLoader;

public class LoadingScene extends Scene{

	private Animator animator;
	private Image logo;
	private boolean called;
	
	public LoadingScene(Game game) {
		super(game, "loading", Color.WHITE);
		animator = new Animator(200, 1);
		logo = ResLoader.getImageByName("general/frameicon.png");
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		camera.focusX(0);
		camera.focusY(0);
	}

	@Override
	public void onTick(float delta) {
		if(animator.finished()) {
			if(!called) {
				callScene("menu", getDataForNextScene(), 60f);
				called = true;
			}
			return;
		}
		animator.calculate(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		Composite composite = g2d.getComposite();
		if(animator.getCurrValueRelative() <= 0.5f) {
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, animator.getCurrValueRelative() * 2);
			g2d.setComposite(ac);
		}
		g2d.drawImage(logo, -155, -155, 310, 310, null);
		g2d.setComposite(composite);
	}

	@Override
	public SceneData getDataForNextScene() {
		return new MainSceneData();
	}

}
