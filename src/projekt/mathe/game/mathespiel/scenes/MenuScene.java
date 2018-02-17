package projekt.mathe.game.mathespiel.scenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.help.TextureHelper;
import projekt.mathe.game.engine.particle.ParticleHolder;
import projekt.mathe.game.mathespiel.scenes.menu.Slider;
import projekt.mathe.game.mathespiel.scenes.menu.SliderHolder;

public class MenuScene extends Scene{
	
	private ParticleHolder particleHolder;
	private SliderHolder sliderHolder;
	private TextureHelper titelHelper;
	
	public MenuScene(Game container) {
		super(container, "menu", Color.BLACK);
		sliderHolder = new SliderHolder(this);
		sliderHolder.addElement(new Slider(this, sliderHolder, 568, 436, 144, 38, new Image[] {
				ResLoader.getImageByName("menu/buttons/Start1.png"),
				ResLoader.getImageByName("menu/buttons/Start2.png"),
				ResLoader.getImageByName("menu/buttons/Start3.png")
			}).addOnClickListener(new Runnable() {
				@Override
				public void run() {
					callScene("pausenhof", getDataForNextScene(), 80f);
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
		particleHolder = new ParticleHolder(this, 1.2f, 1, 80, Color.ORANGE);
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		camera.setX(0);
		camera.setY(0);
		sliderHolder.reset();
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
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		particleHolder.onPaint(g2d);
		g2d.drawImage(titelHelper.getCurrentImage(), 331, 140, 617, 55, null);
		sliderHolder.onPaint(g2d);
	}

	@Override
	public SceneData getDataForNextScene() {
		return new MainSceneData();
	}

}
