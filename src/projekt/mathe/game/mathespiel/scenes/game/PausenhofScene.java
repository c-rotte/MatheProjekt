package projekt.mathe.game.mathespiel.scenes.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.nio.channels.NonWritableChannelException;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.pause.MainPauseScreen;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.PausenhofWorld;

public class PausenhofScene extends Scene{

	public PausenhofScene(Game game) {
		super(game, "pausenhof", Values.SCENE_BG_COLOR);
		MapPlayer player = new MapPlayer(this, true);
		PausenhofWorld world = new PausenhofWorld(this, player);
		player.setWorld(world);
		registerWorld(world);
		registerPlayer(player);
		registerPauseScreen(new MainPauseScreen(this));
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		camera.setMaxBounds(new Rectangle(-500, -500, 2500, 1750));
		if(lastID.equals("aula") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("aulaAusgang")) {
			player.x = 1010;
			player.y = -65;
			player.direction = "down";
			camera.focusX(1035);
			camera.focusY(0);
		}else {
			player.x = 1900;
			player.y = 600;
			player.direction = "left";
			camera.focusX(1360);
			camera.focusY(600);
		}
	}

	@Override
	public void onMouseClicked(MouseEvent e) {
	
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
