package projekt.mathe.game.mathespiel.scenes.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.save.Saver;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.pause.MainPauseScreen;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.SekWorld;

public class SekScene extends Scene{

	private SekWorld sekWorld;
	
	public SekScene(Game container) {
		super(container, "sek", Values.SCENE_BG_COLOR);
		MapPlayer player = new MapPlayer(this);
		sekWorld = new SekWorld(this, player);
		registerWorld(sekWorld);
		player.setWorld(world);
		registerPlayer(player);
		registerPauseScreen(new MainPauseScreen(this));
		enableCodeDisplay();
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		player.reloadGender();
		camera.setMaxBounds(new Rectangle(-500, -500, 2500, 1558));
		if(lastID.equals("choose") && ((MainSceneData) sceneData).additional.containsKey("continue")) {
			if(Saver.containsData("lastPosX") && Saver.containsData("lastPosY")) {
				player.setX(Saver.getFloat("lastPosX"));
				player.setY(Saver.getFloat("lastPosY"));
				player.direction = Saver.getString("lastDir");
				camera.focusX(Saver.getFloat("lastCamFocusX"));
				camera.focusY(Saver.getFloat("lastCamFocusY"));
			}
		}else if(lastID.equals("tische") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("sekEingang")) {
			camera.focusX(140);
			camera.focusY(422);
			player.setX(-454);
			player.setY(360);
			player.direction = "right";
		}else if(lastID.equals("safe")) {
			camera.focusX(1360);
			camera.focusY(617);
			player.setX(1752);
			player.setY(521);
			player.direction = "up";
		}else {
			camera.focusX(1000);
			camera.focusY(300);
			player.setX(1000);
			player.setY(300);
		}
		sekWorld.onCall();
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
