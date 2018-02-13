package projekt.mathe.game.mathespiel.scenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.save.Saver;
import projekt.mathe.game.mathespiel.Settings;
import projekt.mathe.game.mathespiel.scenes.menu.CheckBox;
import projekt.mathe.game.mathespiel.scenes.menu.CheckBoxHolder;
import projekt.mathe.game.mathespiel.scenes.menu.Slider;
import projekt.mathe.game.mathespiel.scenes.menu.SliderHolder;

public class SettingsScene extends Scene{

	private CheckBoxHolder checkBoxHolder;
	private SliderHolder sliderHolder;
	private Image bg;

	public SettingsScene(Game container) {
		super(container, "settings", Color.CYAN);
		Settings.FPS_ANZEIGEN = Saver.containsData("fps") ? Saver.getBoolean("fps") : false;
		Settings.HITBOXEN_ANZEIGEN = Saver.containsData("hitbox") ? Saver.getBoolean("hitbox") : false;
		checkBoxHolder = new CheckBoxHolder(this);
		checkBoxHolder.addElement(new CheckBox(this, "fps", 50, 100, 30, Color.LIGHT_GRAY, Color.DARK_GRAY).addText("FPS anzeigen", Color.LIGHT_GRAY).setClicked(Settings.FPS_ANZEIGEN));
		checkBoxHolder.addElement(new CheckBox(this, "hitbox", 50, 160, 30, Color.LIGHT_GRAY, Color.DARK_GRAY).addText("Hitboxen anzeigen", Color.LIGHT_GRAY).setClicked(Settings.HITBOXEN_ANZEIGEN));
		sliderHolder = new SliderHolder(this);
		sliderHolder.addElement(new Slider(this, sliderHolder, -50, 20, 20, 120, 40, "BACK", 30, Color.WHITE, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY, new Runnable() {
			@Override
			public void run() {
				Settings.FPS_ANZEIGEN = checkBoxHolder.wasClicked("fps");
				Settings.HITBOXEN_ANZEIGEN = checkBoxHolder.wasClicked("hitbox");
				callScene("menu", getDataForNextScene(), 50f);
			}
		}));
		bg = ResLoader.getImageByName("general/menuBG.png");
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		camera.setX(0);
		camera.setY(0);
		sliderHolder.reset();
	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		checkBoxHolder.onMouseDragged(e);
		sliderHolder.onMouseDragged(e);
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		checkBoxHolder.onMouseMoved(e);
		sliderHolder.onMouseMoved(e);
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		checkBoxHolder.onMouseClicked(e);
		sliderHolder.onMouseClicked(e);
	}
	
	@Override
	public void onTick(float delta) {
		sliderHolder.onTick(delta);
		checkBoxHolder.setClickable(!sliderHolder.wasClicked());
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(bg, 0, 0, null);
		checkBoxHolder.onPaint(g2d);
		sliderHolder.onPaint(g2d);
	}

	@Override
	public SceneData getDataForNextScene() {
		return new MainSceneData();
	}

}
