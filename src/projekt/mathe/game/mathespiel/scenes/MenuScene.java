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
import projekt.mathe.game.mathespiel.scenes.menu.Slider;
import projekt.mathe.game.mathespiel.scenes.menu.SliderHolder;

public class MenuScene extends Scene{
	
	private SliderHolder sliderHolder;
	private TextureHelper bgHelper;

	public MenuScene(Game container) {
		super(container, "menu", Color.WHITE);
		sliderHolder = new SliderHolder(this);
		sliderHolder.addElement(new Slider(this, sliderHolder, -200, 85, 85, 200, 50, "START", 30, Color.WHITE, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY, new Runnable() {
			@Override
			public void run() {
				callScene("pausenhof", new MainSceneData(), 150f);
			}
		}));
		sliderHolder.addElement(new Slider(this, sliderHolder, -300, 85, 185, 200, 50, "TEST", 30, Color.WHITE, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY, new Runnable() {
			@Override
			public void run() {
				callScene("pausenhof", new MainSceneData(), 150f);
			}
		}));
		sliderHolder.addElement(new Slider(this, sliderHolder, -400, 85, 285, 200, 50, "TEST2", 30, Color.WHITE, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY, new Runnable() {
			@Override
			public void run() {
				callScene("pausenhof", new MainSceneData(), 150f);
			}
		}));
		sliderHolder.addElement(new Slider(this, sliderHolder, -500, 85, 385, 200, 50, "TEST3", 30, Color.WHITE, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY, new Runnable() {
			@Override
			public void run() {
				callScene("pausenhof", new MainSceneData(), 150f);
			}
		}));
		sliderHolder.addElement(new Slider(this, sliderHolder, -600, 85, 485, 200, 50, "TEST3", 30, Color.WHITE, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY, new Runnable() {
			@Override
			public void run() {
				callScene("pausenhof", new MainSceneData(), 150f);
			}
		}));
		sliderHolder.addElement(new Slider(this, sliderHolder, -700, 85, 585, 200, 50, "TEST3", 30, Color.WHITE, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY, new Runnable() {
			@Override
			public void run() {
				callScene("pausenhof", new MainSceneData(), 150f);
			}
		}));
		bgHelper = new TextureHelper();
		bgHelper.addState("normal", 100000, new Image[] {ResLoader.getImageByName("general/menuBG.png")});
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		camera.setX(0);
		camera.setY(0);
	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		sliderHolder.onMouseDragged(e);
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		sliderHolder.onMouseDragged(e);
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		sliderHolder.onMouseClicked(e);
	}
	
	@Override
	public void onTick(float delta) {
		sliderHolder.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(bgHelper.getCurrentImage(), 0, 0, null);
		sliderHolder.onPaint(g2d);
	}

	@Override
	public SceneData getDataForNextScene() {
		return new MainSceneData();
	}

}
