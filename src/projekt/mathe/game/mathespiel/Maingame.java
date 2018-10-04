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
import projekt.mathe.game.mathespiel.scenes.game.LehrerzimmerScene;
import projekt.mathe.game.mathespiel.scenes.game.PausenhofScene;
import projekt.mathe.game.mathespiel.scenes.game.SekScene;
import projekt.mathe.game.mathespiel.scenes.game.TischeScene;
import projekt.mathe.game.mathespiel.scenes.game.minigames.angle.AngleScene;
import projekt.mathe.game.mathespiel.scenes.game.minigames.blackboard.BoardScene;
import projekt.mathe.game.mathespiel.scenes.game.minigames.boss.BossScene;
import projekt.mathe.game.mathespiel.scenes.game.minigames.boss.win.WinScene;
import projekt.mathe.game.mathespiel.scenes.game.minigames.pyramid.PyramidScene;
import projekt.mathe.game.mathespiel.scenes.game.minigames.race.RaceGameScene;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.scenes.SafeScene;

public class Maingame extends Game{

	private static final long serialVersionUID = 8243059591883622826L;
	private boolean finishedLoading;
	
	public Maingame(Frame frame) {
		super(60, frame, ResLoader.getImageByName("general/frameicon.png"));
		//this.registerScene(new WinScene(this));
		//setCurrentScene("boss_win", new MainSceneData(), 40f);
		///*
		registerMenuScenes();
		setCurrentScene("loading", new MainSceneData(), 60f);
		new Thread(() -> {
			registerGameScenes();
			finishedLoading = true;
		}).start();
	}
	
	private void registerGameScenes() {
		this.registerScene(new ChemieScene(this));
		this.registerScene(new PausenhofScene(this));
		this.registerScene(new AulaScene(this));
		this.registerScene(new TischeScene(this));
		this.registerScene(new SekScene(this));
		this.registerScene(new LehrerzimmerScene(this));
		this.registerScene(new PyramidScene(this));
		this.registerScene(new RaceGameScene(this));
		this.registerScene(new BoardScene(this));
		this.registerScene(new AngleScene(this));
		this.registerScene(new BossScene(this));
		this.registerScene(new WinScene(this));
		this.registerScene(new SafeScene(this));
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
		saveSettings();
		Saver.saveData();
		Logger.log("Caught Closing!");
		container.shutdown();
		System.exit(0);
	}
	
	private void saveSettings() {
		Saver.setData("fps", Settings.FPS_ANZEIGEN);
		Saver.setData("hitbox", Settings.HITBOXEN_ANZEIGEN);
		Saver.setData("darkmode", Settings.DARKMODE);
		Saver.setData("smooth", Settings.SMOOTH);
		Saver.setData("girl", Settings.GIRL);
	}
	
}
