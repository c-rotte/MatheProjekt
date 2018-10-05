package projekt.mathe.game.mathespiel.scenes.game;

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
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.SekWorld;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

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
			if(Saver.containsData("currCode") && Saver.containsData("safeCode") && Saver.getString("currCode").charAt(4) == Saver.getString("safeCode").charAt(4) && !Saver.containsData("bossdefeated")) {
				((SekWorld) world).getBoss().reset();
				((SekWorld) world).getBoss().trigger();
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
		}else if(lastID.equals("boss_win")){
			((SekWorld) world).getBoss().reset();
			camera.focusX(522);
			camera.focusY(94);
			player.setX(154);
			player.setY(-5);
			player.direction = "left";
			((SekWorld) world).getFemale().setDirection("right");
			Dialog dialog = new Dialog(world) {
				@Override
				public void onSelected(Card lastcard, boolean finished) {
					
				}
				@Override
				public void onFinished(Card lastcard) {
					Saver.saveCurrentState(player, SekScene.this);
					player.playerController.setActivated(true);
				}
			};
			dialog.addCard(new Card("Wie gut, dass du diesen Störenfried vertrieben hast!"));
			dialog.addCard(new Card("Der traut sich so schnell nicht mehr hierher. Aber nun muss ich mich auf meinen Unterricht vorbereiten."));
			world.openDialog(dialog);
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
