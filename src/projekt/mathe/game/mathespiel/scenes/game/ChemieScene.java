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
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.ChemieWorld;

public class ChemieScene extends Scene{

	public ChemieScene(Game container) {
		super(container, "chemie", Values.SCENE_BG_COLOR);
		MapPlayer player = new MapPlayer(this);
		ChemieWorld world = new ChemieWorld(this, player);
		player.setWorld(world);
		registerPlayer(player);
		registerWorld(world);
		registerPauseScreen(new MainPauseScreen(this));
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		player.reloadGender();
		camera.setMaxBounds(new Rectangle(-500, -500, 1360, 1443));
		if(lastID.equals("aula") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("chemieOben")) {
			camera.focusX(220);
			camera.focusY(-140);
			player.setX(720);
			player.setY(-165);
			player.direction = "left";
		}else if(lastID.equals("aula") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("chemieUnten")) {
			camera.focusX(220);
			camera.focusY(583);
			player.setX(720);
			player.setY(685);
			player.direction = "left";
		}else {
			camera.focusX(220);
			camera.focusY(-140);
			player.setX(200);
			player.setY(-171);
		}
	}

	@Override
	public void onTick(float delta) {
		
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SceneData getDataForNextScene() {
		MainSceneData mainSceneData = new MainSceneData();
		mainSceneData.setMapPlayer(player);
		mainSceneData.setCamera(camera);
		return mainSceneData;
	}

}
