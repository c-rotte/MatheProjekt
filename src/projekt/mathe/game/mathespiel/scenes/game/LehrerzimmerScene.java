package projekt.mathe.game.mathespiel.scenes.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.save.Saver;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.pause.MainPauseScreen;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.LehrerzimmerWorld;

public class LehrerzimmerScene extends Scene {

	public LehrerzimmerScene(Game container) {
		super(container, "lehrerzimmer", Values.SCENE_BG_COLOR);
		MapPlayer player = new MapPlayer(this);
		LehrerzimmerWorld world = new LehrerzimmerWorld(this, player);
		player.setWorld(world);
		registerPlayer(player);
		registerWorld(world);
		registerPauseScreen(new MainPauseScreen(this));
		enableCodeDisplay();
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		player.reloadGender();
		camera.setMaxBounds(new Rectangle(-500, -500, 1350, 844));
		if(lastID.equals("choose") && ((MainSceneData) sceneData).additional.containsKey("continue")) {
			if(Saver.containsData("lastPosX") && Saver.containsData("lastPosY")) {
				player.setX(Saver.getFloat("lastPosX"));
				player.setY(Saver.getFloat("lastPosY"));
				player.direction = Saver.getString("lastDir");
				camera.focusX(Saver.getFloat("lastCamFocusX"));
				camera.focusY(Saver.getFloat("lastCamFocusY"));
			}
		}else {
			player.setX(474);
			player.setY(175);
			player.direction = "up";
			camera.focusX(210);
			camera.focusY(-16);
		}
	}

	@Override
	public void onTick(float delta) {
	
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		
	}

	@Override
	public SceneData getDataForNextScene() {
		MainSceneData mainSceneData = new MainSceneData();
		mainSceneData.setMapPlayer(player);
		mainSceneData.setCamera(camera);
		return mainSceneData;
	}

}
