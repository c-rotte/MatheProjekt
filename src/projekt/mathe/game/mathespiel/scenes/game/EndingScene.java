package projekt.mathe.game.mathespiel.scenes.game;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;

public class EndingScene extends Scene{

	private Animator animator;
	
	public EndingScene(Game container) {
		super(container, "ending", Color.BLACK);
		animator = new Animator(500, 1);
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		animator.reset();
	}

	@Override
	public void onTick(float delta) {
		animator.calculate(delta);
		if(animator.finished() && !fading) {
			callScene("menu", getDataForNextScene(), 200f);
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		Helper.drawStringAroundPosition(640, 360, "~", Color.WHITE, 50, FONT.VCR, g2d, null, -1);
	}

	@Override
	public SceneData getDataForNextScene() {
		return new MainSceneData();
	}

}
