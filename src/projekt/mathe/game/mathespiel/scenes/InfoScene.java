package projekt.mathe.game.mathespiel.scenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.engine.particle.ParticleHolder;
import projekt.mathe.game.mathespiel.scenes.menu.Button;
import projekt.mathe.game.mathespiel.scenes.menu.ButtonHolder;

public class InfoScene extends Scene{

	private ButtonHolder buttonHolder;
	private ParticleHolder particleHolder;
	
	public InfoScene(Game container) {
		super(container, "info", Color.BLACK);
		particleHolder = new ParticleHolder(this, 1.2f, 1, 80, Color.WHITE);
		buttonHolder = new ButtonHolder(this);
		buttonHolder.addElement(new Button(this, buttonHolder, 20, 20, 133, 33, new Image[] {
			ResLoader.getImageByName("menu/buttons/Zur�ck1.png"),
			ResLoader.getImageByName("menu/buttons/Zur�ck2.png"),
			ResLoader.getImageByName("menu/buttons/Zur�ck3.png")
		}).addOnClickListener(new Runnable() {
			@Override
			public void run() {
				callScene("menu", getDataForNextScene(), 60f);
			}
		}));
	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		buttonHolder.onMouseDragged(e);
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		buttonHolder.onMouseMoved(e);
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		buttonHolder.onMouseClicked(e);
	}
	
	@Override
	public void onCall(String lastID, SceneData sceneData) {
		buttonHolder.reset();
	}

	@Override
	public void onTick(float delta) {
		particleHolder.onTick(delta);
		buttonHolder.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		particleHolder.onPaint(g2d);
		buttonHolder.onPaint(g2d);
		Helper.drawStringAroundPosition(640, 200, "Nachts im Bunker", Color.WHITE, 40, FONT.VCR, g2d, null, -1);
		Helper.drawStringAroundPosition(640, 250, "2017 - 2018", Color.WHITE, 40, FONT.VCR, g2d, null, -1);
		Helper.drawStringAroundPosition(640, 300, "~", Color.WHITE, 40, FONT.VCR, g2d, null, -1);
	}

	@Override
	public SceneData getDataForNextScene() {
		return new MainSceneData();
	}

}
