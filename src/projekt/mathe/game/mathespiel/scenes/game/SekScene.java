package projekt.mathe.game.mathespiel.scenes.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.SekWorld;

public class SekScene extends Scene{

	public SekScene(Game container) {
		super(container, "sek", Values.SCENE_BG_COLOR);
		MapPlayer player = new MapPlayer(this, true);
		registerWorld(new SekWorld(this, player));
		player.setWorld(world);
		registerPlayer(player);
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		camera.setMaxBounds(new Rectangle(-500, -500, 2500, 1558));
		if(lastID.equals("tische") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("sekEingang")) {
			camera.focusX(140);
			camera.focusY(422);
			player.x = -454;
			player.y = 360;
		}else {
			camera.focusX(1000);
			camera.focusY(300);
			player.x = 1000;
			player.y = 300;
		}
	}

	@Override
	public void onTick(float delta) {
		//System.out.println("Kamera: x=" + camera.getFocusX() + "; y=" + camera.getFocusY() + "; Player: x=" + player.x + "; y=" + player.y);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		
	}

	@Override
	public SceneData getDataForNextScene() {
		return new MainSceneData();
	}

}
