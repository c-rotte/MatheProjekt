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
import projekt.mathe.game.engine.help.TextureHelper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.engine.particle.ParticleHolder;
import projekt.mathe.game.mathespiel.Maingame;
import projekt.mathe.game.mathespiel.scenes.menu.Slider;
import projekt.mathe.game.mathespiel.scenes.menu.SliderHolder;

public class MenuScene extends Scene{
	
	private ParticleHolder particleHolder;
	private SliderHolder sliderHolder;
	private TextureHelper titelHelper;
	private boolean gameLoading;
	private TextureHelper loadingHelper;
	
	public MenuScene(Game container) {
		super(container, "menu", Color.BLACK);
		loadingHelper = new TextureHelper();
		Image[] loadingIMG = new Image[45];
		for(int i = 0; i < 45; i++) {
			loadingIMG[i] = Helper.colorImage(ResLoader.getImageByName("menu/loading/frames_" + (i + 1) + ".png"), Color.WHITE);
		}
		loadingHelper.addState("normal", 10, loadingIMG);
		sliderHolder = new SliderHolder(this);
		sliderHolder.addElement(new Slider(this, sliderHolder, 568, 436, 144, 38, new Image[] {
				ResLoader.getImageByName("menu/buttons/Start1.png"),
				ResLoader.getImageByName("menu/buttons/Start2.png"),
				ResLoader.getImageByName("menu/buttons/Start3.png")
			}).addOnClickListener(new Runnable() {
				@Override
				public void run() {
					startGame();
				}
			}));
		sliderHolder.addElement(new Slider(this, sliderHolder, 524, 496, 232, 38, new Image[] {
				ResLoader.getImageByName("menu/buttons/Optionen1.png"),
				ResLoader.getImageByName("menu/buttons/Optionen2.png"),
				ResLoader.getImageByName("menu/buttons/Optionen3.png")
			}).addOnClickListener(new Runnable() {
				@Override
				public void run() {
					callScene("settings", getDataForNextScene(), 80f);
				}
			}));
		sliderHolder.addElement(new Slider(this, sliderHolder, 582, 550, 116, 43, new Image[] {
				ResLoader.getImageByName("menu/buttons/Über1.png"),
				ResLoader.getImageByName("menu/buttons/Über2.png"),
				ResLoader.getImageByName("menu/buttons/Über3.png")
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
		gameLoading = true;
	}
	
	@Override
	public void onCall(String lastID, SceneData sceneData) {
		camera.setX(0);
		camera.setY(0);
		sliderHolder.reset();
		gameLoading = false;
	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		sliderHolder.onMouseDragged(e);
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		sliderHolder.onMouseMoved(e);
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		sliderHolder.onMouseClicked(e);
	}
	
	@Override
	public void onTick(float delta) {
		sliderHolder.onTick(delta);
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
		sliderHolder.onPaint(g2d);
		if(gameLoading && !((Maingame) container).finishedLoading()) {
			g2d.drawImage(loadingHelper.getCurrentImage(), 1200, 640, 70, 70, null);
			Helper.drawStringFromLeft(1000, 690, "Loading...", Color.WHITE, 40, FONT.Ailerons, g2d);
		}
	}

	@Override
	public SceneData getDataForNextScene() {
		return new MainSceneData();
	}

}
