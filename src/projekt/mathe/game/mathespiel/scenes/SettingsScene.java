package projekt.mathe.game.mathespiel.scenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.particle.ParticleHolder;
import projekt.mathe.game.mathespiel.Settings;
import projekt.mathe.game.mathespiel.scenes.menu.Button;
import projekt.mathe.game.mathespiel.scenes.menu.ButtonHolder;
import projekt.mathe.game.mathespiel.scenes.menu.CheckBox;
import projekt.mathe.game.mathespiel.scenes.menu.CheckBoxHolder;

public class SettingsScene extends Scene{

	private CheckBoxHolder checkBoxHolder;
	private ButtonHolder buttonHolder;
	private ParticleHolder particleHolder;
	
	public SettingsScene(Game container) {
		super(container, "settings", Color.BLACK);
		checkBoxHolder = new CheckBoxHolder(this);
		checkBoxHolder.addElement(new CheckBox(this, "fps", 50, 110, 30, Color.LIGHT_GRAY, Color.DARK_GRAY).addText("FPS anzeigen", Color.WHITE).setClicked(Settings.FPS_ANZEIGEN));
		checkBoxHolder.addElement(new CheckBox(this, "hitbox", 50, 170, 30, Color.LIGHT_GRAY, Color.DARK_GRAY).addText("Hitboxen anzeigen", Color.WHITE).setClicked(Settings.HITBOXEN_ANZEIGEN));
		checkBoxHolder.addElement(new CheckBox(this, "darkmode", 50, 230, 30, Color.LIGHT_GRAY, Color.DARK_GRAY).addText("Dunkler Modus", Color.WHITE).setClicked(Settings.DARKMODE));
		buttonHolder = new ButtonHolder(this);
		buttonHolder.addElement(new Button(this, buttonHolder, 40, 40, 140, 40)
				.setText("ZURÜCK", 40, 110, 57, new Color[] {new Color(0, 180, 255), new Color(0, 100, 141), new Color(0, 55, 78)}, 3.4f)
				.addOnClickListener(new Runnable() {
					@Override
					public void run() {
						saveSettings();
						callScene("menu", getDataForNextScene(), Values.MENU_FADING);	
					}
				}));
		particleHolder = new ParticleHolder(this, 1.2f, 1, 80, Color.WHITE);
	}

	private void saveSettings() {
		Settings.FPS_ANZEIGEN = checkBoxHolder.wasClicked("fps");
		Settings.HITBOXEN_ANZEIGEN = checkBoxHolder.wasClicked("hitbox");
		Settings.DARKMODE = checkBoxHolder.wasClicked("darkmode");
		Settings.SMOOTH = checkBoxHolder.wasClicked("smooth");
	}
	
	@Override
	public void onCall(String lastID, SceneData sceneData) {
		camera.setX(0);
		camera.setY(0);
		buttonHolder.reset();
	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		checkBoxHolder.onMouseDragged(e);
		buttonHolder.onMouseDragged(e);
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		checkBoxHolder.onMouseMoved(e);
		buttonHolder.onMouseMoved(e);
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		checkBoxHolder.onMouseClicked(e);
		buttonHolder.onMouseClicked(e);
	}
	
	@Override
	public void onTick(float delta) {
		particleHolder.onTick(delta);
		buttonHolder.onTick(delta);
		checkBoxHolder.setClickable(!buttonHolder.wasClicked());
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		particleHolder.onPaint(g2d);
		checkBoxHolder.onPaint(g2d);
		buttonHolder.onPaint(g2d);
	}

	@Override
	public SceneData getDataForNextScene() {
		return new MainSceneData();
	}

}
