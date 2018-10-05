package projekt.mathe.game.mathespiel.scenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.engine.help.Logger;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.help.TextureHelper;
import projekt.mathe.game.engine.particle.ParticleHolder;
import projekt.mathe.game.engine.save.Saver;
import projekt.mathe.game.mathespiel.Maingame;
import projekt.mathe.game.mathespiel.Settings;
import projekt.mathe.game.mathespiel.scenes.menu.Button;
import projekt.mathe.game.mathespiel.scenes.menu.ButtonHolder;
import projekt.mathe.game.mathespiel.scenes.menu.GenderWarning;
import projekt.mathe.game.mathespiel.scenes.menu.NewGameWarning;

public class ChooseScene extends Scene{

	private ButtonHolder buttonHolder;
	private ParticleHolder particleHolder;

	private TextureHelper loadingHelper;

	private NewGameWarning newGameWarning;
	private GenderWarning genderWarning;
	
	private String state; //normal, gender, warning, loading
	
	private boolean continued;
	
	public ChooseScene(Game container) {
		super(container, "choose", Color.BLACK);
		particleHolder = new ParticleHolder(this, 1.2f, 1, 80, Color.WHITE);
		loadingHelper = new TextureHelper();
		Image[] loadingIMG = new Image[45];
		for(int i = 0; i < 45; i++) {
			loadingIMG[i] = Helper.colorImage(ResLoader.getImageByName("menu/loading/frames_" + (i + 1) + ".png"), Color.WHITE);
		}
		loadingHelper.addState("normal", 10, loadingIMG);
		buttonHolder = new ButtonHolder(this);
		newGameWarning = new NewGameWarning(this);
		genderWarning = new GenderWarning(this);
		state = "normal";
	}
	
	private void reloadButtons() {
		buttonHolder.clear();
		buttonHolder.addElement(new Button(this, buttonHolder, 40, 40, 140, 40)
				.setText("ZURÜCK", 40, 110, 57, new Color[] {new Color(0, 180, 255), new Color(0, 100, 141), new Color(0, 55, 78)}, 3.4f)
				.addOnClickListener(new Runnable() {
					@Override
					public void run() {
						callScene("menu", getDataForNextScene(), Values.MENU_FADING);	
					}
				}));
		if(Saver.containsData("existingGame") && Saver.getBoolean("existingGame")) {
			buttonHolder.addElement(new Button(this, buttonHolder, 450, 315, 380, 40)
					.setText("WEITERSPIELEN", 50, 640, 330, new Color[] {new Color(0, 180, 255), new Color(0, 100, 141), new Color(0, 55, 78)}, 3.4f)
					.addOnClickListener(new Runnable() {
						@Override
						public void run() {
							continued = true;
							state = "loading";
						}
					}));
			buttonHolder.addElement(new Button(this, buttonHolder, 480, 375, 320, 40)
					.setText("NEUES SPIEL", 50, 640, 390, new Color[] {new Color(0, 180, 255), new Color(0, 100, 141), new Color(0, 55, 78)}, 3.4f)
					.addOnClickListener(new Runnable() {
						@Override
						public void run() {
							newGameWarning.open();
							state = "warning";
						}
					}));
		}else {
			buttonHolder.addElement(new Button(this, buttonHolder, 480, 340, 320, 40)
					.setText("NEUES SPIEL", 50, 640, 355, new Color[] {new Color(0, 180, 255), new Color(0, 100, 141), new Color(0, 55, 78)}, 3.4f)
					.addOnClickListener(new Runnable() {
						@Override
						public void run() {
							genderWarning.open();
							state = "gender";
						}
					}));
		}
	}

	private void reset() {
		newGameWarning.reset();
		genderWarning.reset();
		state = "normal";
		buttonHolder.reset();
		continued = false;
	}
	
	@Override
	public void onCall(String lastID, SceneData sceneData) {
		reloadButtons();
		state = "normal";
		continued = false;
	}

	@Override
	public void onTick(float delta) {
		particleHolder.onTick(delta);
		buttonHolder.onTick(delta);
		switch (state) {
			case "normal":
			
				break;
			case "gender":
				if(genderWarning.getGender() != null) {
					Settings.GIRL = genderWarning.getGender().equals("girl");
					genderWarning.reset();
					genderWarning.close();
					Saver.newGame();
					state = "loading";
				}
				break;
			case "warning":
				if(newGameWarning.getState().equals("continued")) {
					newGameWarning.reset();
					newGameWarning.close();
					state = "gender";
					genderWarning.open();
				}else if(newGameWarning.getState().equals("cancelled")){
					newGameWarning.reset();
					newGameWarning.close();
					state = "normal";
					reset();
				}
				break;
			case "loading":
				loadingHelper.onTick(delta);
				if(((Maingame) container).finishedLoading()) {
					state = "normal";
					if(continued) {
						if(Saver.containsData("lastScene")) {
							MainSceneData mainSceneData = (MainSceneData) getDataForNextScene();
							mainSceneData.additional.put("continue", true);
							callScene(Saver.getString("lastScene"), mainSceneData, 70f);
						}else {
							callScene("pausenhof", getDataForNextScene(), 70f);
						}
					}else {
						callScene("intro", getDataForNextScene(), 70f);
					}
					reset();
				}
				break;
		}
		
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		particleHolder.onPaint(g2d);
		buttonHolder.onPaint(g2d);
		if(state.equals("loading") && !((Maingame) container).finishedLoading()) {
			g2d.drawImage(loadingHelper.getCurrentImage(), 1200, 640, 70, 70, null);
			int prozent = Math.round(Logger.getRelativeLoadedObjects() * 100);
			Helper.drawStringFromLeft(1110, 690, prozent + "%", Color.WHITE, 40, FONT.VCR, g2d, null, -1);
		}
		if(newGameWarning.isOpen()) {
			fillScene(g2d, Color.BLACK, .3f);
			newGameWarning.onPaint(g2d);
		}else if(genderWarning.isOpen()) {
			fillScene(g2d, Color.BLACK, .3f);
			genderWarning.onPaint(g2d);
		}
	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		if(newGameWarning.isOpen()) {
			newGameWarning.onMouseDragged(e);
		}else if(genderWarning.isOpen()) {
			genderWarning.onMouseDragged(e);
		}else {
			buttonHolder.onMouseDragged(e);
		}
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		if(newGameWarning.isOpen()) {
			newGameWarning.onMouseMoved(e);
		}else if(genderWarning.isOpen()) {
			genderWarning.onMouseMoved(e);
		}else {
			buttonHolder.onMouseMoved(e);
		}
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		if(newGameWarning.isOpen()) {
			newGameWarning.onMouseClicked(e);
		}else if(genderWarning.isOpen()) {
			genderWarning.onMouseClicked(e);
		}else {
			buttonHolder.onMouseClicked(e);
		}
	}
	
	@Override
	public SceneData getDataForNextScene() {
		return new MainSceneData();
	}

}
