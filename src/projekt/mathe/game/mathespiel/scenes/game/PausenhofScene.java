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
import projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person.PETeacher;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.PausenhofWorld;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class PausenhofScene extends Scene{

	private PETeacher peTeacher;
	
	public PausenhofScene(Game game) {
		super(game, "pausenhof", Values.SCENE_BG_COLOR);
		MapPlayer player = new MapPlayer(this);
		PausenhofWorld world = new PausenhofWorld(this, player);
		player.setWorld(world);
		registerWorld(world);
		registerPlayer(player);
		registerPauseScreen(new MainPauseScreen(this));
		peTeacher = new PETeacher(this, world, -1, -1);
		world.addEntity(peTeacher);
		enableCodeDisplay();
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		
		player.reloadGender();
		player.playerController.setActivated(true);
		camera.setMaxBounds(new Rectangle(-500, -500, 2500, 1750));
		if((lastID.equals("intro") || lastID.equals("choose")) && ((MainSceneData) sceneData).additional.containsKey("continue")) {
			if(Saver.containsData("lastPosX") && Saver.containsData("lastPosY")) {
				player.setX(Saver.getFloat("lastPosX"));
				player.setY(Saver.getFloat("lastPosY"));
				player.direction = Saver.getString("lastDir");
				camera.focusX(Saver.getFloat("lastCamFocusX"));
				camera.focusY(Saver.getFloat("lastCamFocusY"));
			}
		}else if(lastID.equals("aula") && ((MainSceneData) sceneData).getLastLoadingZoneID().equals("aulaAusgang")) {
			player.setX(1010);
			player.setY(-65);
			player.direction = "down";
			camera.focusX(1035);
			camera.focusY(0);
		}else if(lastID.equals("pyramid")) {
			if(((MainSceneData) sceneData).getMapPlayer() != null) {
				player = ((MainSceneData) sceneData).getMapPlayer();
			}
			System.out.println("Game completed: " + ((MainSceneData) sceneData).minigameCompleted());
		}else if(lastID.equals("race")) {
			if(((MainSceneData) sceneData).getMapPlayer() != null) {
				player = ((MainSceneData) sceneData).getMapPlayer();
			}
			System.out.println("Game completed: " + ((MainSceneData) sceneData).minigameCompleted());
		}else {
			player.setX(1900);
			player.setY(600);
			player.direction = "left";
			camera.focusX(1360);
			camera.focusY(600);
		}
		
		if(lastID.equals("race")) {
			if(((MainSceneData) sceneData).hadMinigame() && ((MainSceneData) sceneData).minigameCompleted()) {
				
				peTeacher.setState("success");
				player.setX(820);
				player.setY(10);
				player.direction = "left";
				camera.focusX(1035);
				camera.focusY(100);
				
				Dialog dialog = new Dialog(world) {
					@Override
					public void onSelected(Card lastcard, boolean finished) {
						
					}
					@Override
					public void onFinished(Card lastcard) {
						Saver.saveCurrentState(player, PausenhofScene.this);
						StringBuilder builder = new StringBuilder(Saver.getString("currCode"));
						builder.setCharAt(0, Saver.getString("safeCode").charAt(0));
						Saver.setData("currCode", builder.toString());
					}
				};
				dialog.addCard(new Card("Na gut, du kannst rein. Aber mach bloﬂ keinen Unsinn!"));
				dialog.addCard(new Card("Ach ja, ich habe hier einen Zettel, mit einem Zeichen gefunden: \"" + Saver.getString("safeCode").charAt(0) + "\". Vielleicht kannst du damit etwas anfangen..."));
				world.openDialog(dialog);
			}else {
				peTeacher.setState("activated");
				player.setX(820);
				player.setY(10);
				player.direction = "left";
				camera.focusX(1035);
				camera.focusY(100);
				
				Dialog dialog = new Dialog(world) {
					@Override
					public void onSelected(Card lastcard, boolean finished) {
						
					}
					@Override
					public void onFinished(Card lastcard) {
						Saver.saveCurrentState(player, PausenhofScene.this);
					}
				};
				dialog.addCard(new Card("Dich lasse ich nicht ins Geb‰ude, bei so einer Kondition!"));
				world.openDialog(dialog);
			}
		}else {
			if(Saver.containsData("currCode") && Saver.containsData("safeCode") && Saver.getString("currCode").charAt(0) == Saver.getString("safeCode").charAt(0)) {
				peTeacher.setState("success");
			}else {
				peTeacher.setState("normal");
			}
		}
		
	}

	@Override
	public void onMouseClicked(MouseEvent e) {
	
	}
	
	@Override
	public void onTick(float delta) {
		peTeacher.onTick(delta);
	}

	@Override
	public SceneData getDataForNextScene() {
		MainSceneData mainSceneData = new MainSceneData();
		mainSceneData.setMapPlayer(player);
		mainSceneData.setCamera(camera);
		return mainSceneData;
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		
	}

}
