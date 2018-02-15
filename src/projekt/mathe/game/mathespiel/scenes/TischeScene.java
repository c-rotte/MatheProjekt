package projekt.mathe.game.mathespiel.scenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.mathespiel.Main;
import projekt.mathe.game.mathespiel.Settings;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.TischeWorld;

public class TischeScene extends Scene{

	public TischeScene(Game container) {
		super(container, "tische", Values.SCENE_BG_COLOR);
		MapPlayer player = new MapPlayer(this, true);
		registerWorld(new TischeWorld(this, player));
		player.setWorld(world);
		registerPlayer(player);
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		camera.setMaxBounds(new Rectangle(-500, -500, 1100, 1443));
		if(lastID.equals("aula") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("tischeEingang")) {
			camera.focusX(50);
			camera.focusY(-68);
			player.x = -60;
			player.y = -136;
			player.direction = "right";
		}else {
			camera.focusX(50);
			camera.focusY(222);
			player.x = 300;
			player.y = 222;
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
		return new MainSceneData();
	}

}
