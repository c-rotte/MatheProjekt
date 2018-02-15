package projekt.mathe.game.mathespiel;

import projekt.mathe.game.engine.Frame;
import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.help.Logger;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.save.Saver;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.MenuScene;
import projekt.mathe.game.mathespiel.scenes.SettingsScene;
import projekt.mathe.game.mathespiel.scenes.TischeScene;
import projekt.mathe.game.mathespiel.scenes.game.AulaScene;
import projekt.mathe.game.mathespiel.scenes.game.ChemieScene;
import projekt.mathe.game.mathespiel.scenes.game.DrawingScene;
import projekt.mathe.game.mathespiel.scenes.game.PausenhofScene;
import projekt.mathe.game.mathespiel.scenes.loading.LoadingScene;

public class Maingame extends Game{

	private static final long serialVersionUID = 8243059591883622826L;

	public Maingame(Frame frame) {
		super(60, frame, ResLoader.getImageByName("general/frameicon.png"));
		registerScenes();
	}

	private void registerScenes(){
		this.registerScene(new SettingsScene(this));
		this.registerScene(new MenuScene(this));
		this.registerScene(new ChemieScene(this));
		this.registerScene(new LoadingScene(this));
		this.registerScene(new PausenhofScene(this));
		this.registerScene(new AulaScene(this));
		this.registerScene(new TischeScene(this));
		this.registerScene(new DrawingScene(this));
		setCurrentScene("loading", new MainSceneData(), 120f);
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
