package projekt.mathe.game.mathespiel.scenes.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.AulaWorld;

public class AulaScene extends Scene{

	public AulaScene(Game container) {
		super(container, "aula", Values.SCENE_BG_COLOR);
		MapPlayer player = new MapPlayer(this, true);
		registerWorld(new AulaWorld(this, player));
		player.setWorld(world);
		registerPlayer(player);
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		camera.setMaxBounds(new Rectangle(-500, -500, 2400, 2043));
		if(lastID.equals("pausenhof") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("pausenhofEingang")) {
			camera.focusX(620);
			camera.focusY(1100);
			player.x = 620;
			player.y = 1100;
			player.direction = "up";
		}else if(lastID.equals("chemie") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("chemieOben")) {
			camera.focusX(140);
			camera.focusY(1093);
			player.x = -480;
			player.y = 995;
			player.direction = "right";
		}else if(lastID.equals("chemie") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("chemieUnten")) {
			camera.focusX(140);
			camera.focusY(1183);
			player.x = -220;
			player.y = 1384;
			player.direction = "up";
		}else {
			camera.focusX(140);
			camera.focusY(207);
			player.x = 67;
			player.y = 127;
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
		return new MainSceneData();
	}

}
