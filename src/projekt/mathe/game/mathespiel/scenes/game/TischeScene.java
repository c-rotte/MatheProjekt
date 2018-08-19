package projekt.mathe.game.mathespiel.scenes.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.pause.MainPauseScreen;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.TischeWorld;

public class TischeScene extends Scene{

	public TischeScene(Game container) {
		super(container, "tische", Values.SCENE_BG_COLOR);
		MapPlayer player = new MapPlayer(this);
		registerWorld(new TischeWorld(this, player));
		player.setWorld(world);
		registerPlayer(player);
		registerPauseScreen(new MainPauseScreen(this));
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		player.reloadGender();
		camera.setMaxBounds(new Rectangle(-500, -500, 1100, 1443));
		if(lastID.equals("aula") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("tischeEingang")) {
			camera.focusX(50);
			camera.focusY(-68);
			player.setX(-50);
			player.setY(-136);
			player.direction = "right";
		}else if(lastID.equals("sek") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("tischeEingang")) {
			camera.focusX(50);
			camera.focusY(-140);
			player.setX(450);
			player.setY(-256);
			player.direction = "left";
		}else if(lastID.equals("lehrerzimmer") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("lehrerzimmer")) {
			camera.focusX(50);
			camera.focusY(-140);
			player.setX(148);
			player.setY(-390);
			player.direction = "down";
		}else {
			camera.focusX(50);
			camera.focusY(222);
			player.setX(300);
			player.setY(222);
			player.direction = "down";
		}
	}

	@Override
	public void onMouseExited(MouseEvent e) {
		
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
