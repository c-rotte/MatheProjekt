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
	private boolean gameLoading;
	private TextureHelper loadingHelper;
	private int loadedObjectsAtStart;
	
	public MenuScene(Game container) {
		super(container, "menu", Color.BLACK);
		loadingHelper = new TextureHelper();
		Image[] loadingIMG = new Image[45];
		for(int i = 0; i < 45; i++) {
			loadingIMG[i] = Helper.colorImage(ResLoader.getImageByName("menu/loading/frames_" + (i + 1) + ".png"), Color.WHITE);
		}
		loadingHelper.addState("normal", 10, loadingIMG);
		buttonHolder = new ButtonHolder(this);
		buttonHolder.addElement(new Button(this, buttonHolder, 568, 436, 144, 38, new Image[] {
				ResLoader.getImageByName("menu/buttons/Start1.png"),
				ResLoader.getImageByName("menu/buttons/Start2.png"),
				ResLoader.getImageByName("menu/buttons/Start3.png")
			}).addOnClickListener(new Runnable() {
				@Override
				public void run() {
					startGame();
				}
			}));
		buttonHolder.addElement(new Button(this, buttonHolder, 524, 496, 232, 38, new Image[] {
				ResLoader.getImageByName("menu/buttons/Optionen1.png"),
				ResLoader.getImageByName("menu/buttons/Optionen2.png"),
				ResLoader.getImageByName("menu/buttons/Optionen3.png")
			}).addOnClickListener(new Runnable() {
				@Override
				public void run() {
					callScene("settings", getDataForNextScene(), 60f);
				}
			}));
		buttonHolder.addElement(new Button(this, buttonHolder, 582, 550, 116, 43, new Image[] {
				ResLoader.getImageByName("menu/buttons/Über1.png"),
				ResLoader.getImageByName("menu/buttons/Über2.png"),
				ResLoader.getImageByName("menu/buttons/Über3.png")
			}).addOnClickListener(new Runnable() {
				@Override
				public void run() {
					callScene("info", getDataForNextScene(), 60f);
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

	private void startGame() {
		loadedObjectsAtStart = Logger.getLoadedObjects();
		gameLoading = true;
	}
	
	@Override
	public void onCall(String lastID, SceneData sceneData) {
		camera.setX(0);
		camera.setY(0);
		buttonHolder.reset();
		gameLoading = false;
		loadedObjectsAtStart = 0;
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
		if(gameLoading) {
			if(((Maingame) container).finishedLoading()) {
				gameLoading = false;
				callScene("pausenhof", getDataForNextScene(), 80f);
			}else {
				loadingHelper.onTick(delta);
			}
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		particleHolder.onPaint(g2d);
		g2d.drawImage(titelHelper.getCurrentImage(), 331, 140, 617, 55, null);
		buttonHolder.onPaint(g2d);
		if(gameLoading && !((Maingame) container).finishedLoading()) {
			g2d.drawImage(loadingHelper.getCurrentImage(), 1200, 640, 70, 70, null);
			Helper.drawStringFromLeft(1050, 690, "Lädt...", Color.WHITE, 40, FONT.VCR, g2d, null, -1);
		}
	}

	@Override
	public SceneData getDataForNextScene() {
		return new MainSceneData();
	}

}
