package projekt.mathe.game.mathespiel.scenes.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.pause.MainPauseScreen;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.AulaWorld;

public class AulaScene extends Scene{

	public AulaScene(Game container) {
		super(container, "aula", Values.SCENE_BG_COLOR);
		MapPlayer player = new MapPlayer(this);
		registerWorld(new AulaWorld(this, player));
		player.setWorld(world);
		registerPlayer(player);
		registerPauseScreen(new MainPauseScreen(this));
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		player.reloadGender();
		camera.setMaxBounds(new Rectangle(-500, -500, 2400, 2043));
		if(lastID.equals("pausenhof") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("pausenhofEingang")) {
			camera.focusX(620);
			camera.focusY(1100);
			player.setX(620);
			player.setY(1100);
			player.direction = "up";
		}else if(lastID.equals("chemie") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("chemieOben")) {
			camera.focusX(140);
			camera.focusY(1093);
			player.setX(-480);
			player.setY(995);
			player.direction = "right";
		}else if(lastID.equals("chemie") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("chemieUnten")) {
			camera.focusX(140);
			camera.focusY(1183);
			player.setX(-220);
			player.setY(1384);
			player.direction = "up";
		}else if(lastID.equals("tische") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("aulaEingang")) {
			camera.focusX(140);
			camera.focusY(-140);
			player.setX(-370);
			player.setY(-150);
			player.direction = "right";
		}else {
			camera.focusX(140);
			camera.focusY(207);
			player.setX(67);
			player.setY(127);
			player.direction = "down";
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
