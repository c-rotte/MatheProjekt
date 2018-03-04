package projekt.mathe.game.mathespiel;

import projekt.mathe.game.engine.Frame;
import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.help.Logger;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.save.Saver;
import projekt.mathe.game.mathespiel.scenes.ChooseScene;
import projekt.mathe.game.mathespiel.scenes.InfoScene;
import projekt.mathe.game.mathespiel.scenes.LoadingScene;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.MenuScene;
import projekt.mathe.game.mathespiel.scenes.SettingsScene;
import projekt.mathe.game.mathespiel.scenes.game.AulaScene;
import projekt.mathe.game.mathespiel.scenes.game.ChemieScene;
import projekt.mathe.game.mathespiel.scenes.game.DrawingScene;
import projekt.mathe.game.mathespiel.scenes.game.PausenhofScene;
import projekt.mathe.game.mathespiel.scenes.game.SekScene;
import projekt.mathe.game.mathespiel.scenes.game.TischeScene;

public class Maingame extends Game{

	private static final long serialVersionUID = 8243059591883622826L;
	private boolean finishedLoading;
	
	public Maingame(Frame frame) {
		super(60, frame, ResLoader.getImageByName("general/frameicon.png"));
		registerMenuScenes();
		setCurrentScene("loading", new MainSceneData(), 120f);
		new Thread(() -> {
			registerGameScenes();
			finishedLoading = true;
		}).start();
	}

	private void registerGameScenes(){
		this.registerScene(new ChemieScene(this));
		this.registerScene(new PausenhofScene(this));
		this.registerScene(new AulaScene(this));
		this.registerScene(new TischeScene(this));
		this.registerScene(new SekScene(this));
		this.registerScene(new DrawingScene(this));
	}

	private void registerMenuScenes() {
		this.registerScene(new LoadingScene(this));
		this.registerScene(new MenuScene(this));
		this.registerScene(new SettingsScene(this));
		this.registerScene(new InfoScene(this));
		this.registerScene(new ChooseScene(this));
	}
	
	public boolean finishedLoading() {
		return finishedLoading;
	}
	
	@Override
	public void onExit() {
		Saver.setData("fps", Settings.FPS_ANZEIGEN);
		Saver.setData("hitbox", Settings.HITBOXEN_ANZEIGEN);
		Saver.saveData();
		Logger.log("Caught Closing!");
		container.shutdown();
		System.exit(0);
	}
	
}
