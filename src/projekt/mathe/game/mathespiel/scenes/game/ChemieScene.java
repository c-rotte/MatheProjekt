package projekt.mathe.game.mathespiel.scenes.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.ChemieWorld;

public class ChemieScene extends Scene{

	public ChemieScene(Game container) {
		super(container, "chemie", new Color(120, 120, 120));
		MapPlayer player = new MapPlayer(this, true);
		ChemieWorld world = new ChemieWorld(this, player);
		player.setWorld(world);
		registerPlayer(player);
		registerWorld(world);
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		camera.setMaxBounds(new Rectangle(-500, -500, 1500, 1500));
		if(lastID.equals("aula") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("chemieOben")) {
			camera.focusX(360);
			camera.focusY(-120);
			player.x = 720;
			player.y = -165;
			player.direction = "left";
		}else if(lastID.equals("aula") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("chemieUnten")) {
			camera.focusX(360);
			camera.focusY(640);
			player.x = 720;
			player.y = 685;
			player.direction = "left";
		}else {
			camera.focusX(200);
			camera.focusY(-120);
			player.x = 200;
			player.y = -171;
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
		return new MainSceneData();
	}

}
