package projekt.mathe.game.mathespiel.scenes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.help.TextureHelper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.engine.help.Logger;
import projekt.mathe.game.engine.particle.ParticleHolder;
import projekt.mathe.game.mathespiel.Maingame;
import projekt.mathe.game.mathespiel.scenes.menu.Button;
import projekt.mathe.game.mathespiel.scenes.menu.ButtonHolder;

public class MenuScene extends Scene{
	
	private ParticleHolder particleHolder;
	private ButtonHolder buttonHolder;
	private TextureHelper titelHelper;
	
	public MenuScene(Game container) {
		super(container, "menu", Color.BLACK);
		buttonHolder = new ButtonHolder(this);
		buttonHolder.addElement(new Button(this, buttonHolder, 565, 420, 150, 40)
				.setText("START", 50, 640, 435, new Color[] {new Color(0, 180, 255), new Color(0, 100, 141), new Color(0, 55, 78)}, 3.4f)
				.addOnClickListener(new Runnable() {
					@Override
					public void run() {
						callScene("choose", getDataForNextScene(), Values.MENU_FADING);
					}
				}));
		buttonHolder.addElement(new Button(this, buttonHolder, 525, 480, 230, 40)
				.setText("OPTIONEN", 50, 640, 495, new Color[] {new Color(0, 180, 255), new Color(0, 100, 141), new Color(0, 55, 78)}, 3.4f)
				.addOnClickListener(new Runnable() {
					@Override
					public void run() {
						callScene("settings", getDataForNextScene(), Values.MENU_FADING);
					}
				}));
		buttonHolder.addElement(new Button(this, buttonHolder, 580, 537, 120, 43)
				.setText("ÜBER", 50, 640, 555, new Color[] {new Color(0, 180, 255), new Color(0, 100, 141), new Color(0, 55, 78)}, 3.4f)
				.addOnClickListener(new Runnable() {
					@Override
					public void run() {
						callScene("info", getDataForNextScene(), Values.MENU_FADING);
					}
				}));
		Image[] titelImages = new Image[21];
		for(int i = 0; i < 21; i++) {
			titelImages[i] = ResLoader.getImageByName("menu/titel/titel_000" + (i <= 9 ? "0" : "") + i + ".png");
		}
		titelHelper = new TextureHelper();
		titelHelper.addState("normal", 9, titelImages);
		particleHolder = new ParticleHolder(this, 1.2f, 1, 80, Color.WHITE);
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		camera.setX(0);
		camera.setY(0);
		buttonHolder.reset();
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
	public void onTick(float delta) {
		buttonHolder.onTick(delta);
		titelHelper.onTick(delta);
		particleHolder.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		particleHolder.onPaint(g2d);
		g2d.drawImage(titelHelper.getCurrentImage(), 331, 140, 617, 55, null);
		buttonHolder.onPaint(g2d);
	}

	@Override
	public SceneData getDataForNextScene() {
		return new MainSceneData();
	}

}
