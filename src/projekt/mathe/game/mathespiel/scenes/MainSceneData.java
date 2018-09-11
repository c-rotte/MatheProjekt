package projekt.mathe.game.mathespiel.scenes;

import java.util.HashMap;

import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.help.Camera;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;

public class MainSceneData extends SceneData{

	private String lastLoadingZone;
	private MapPlayer mapPlayer;
	private Camera camera;
	private String minigameCompleted;
	public HashMap<String, Object> additional;
	
	public MainSceneData() {
		lastLoadingZone = "";
		additional = new HashMap<>();
	}
	
	public boolean hadMinigame() {
		return minigameCompleted != null;
	}
	
	public boolean minigameCompleted() {
		return minigameCompleted.equals("ja");
	}
	
	public void setMinigameCompleted(boolean b) {
		minigameCompleted = b ? "ja" : "nein";
	}
	
	public void setlastLoadingZoneID(String lastLoadingZone) {
		this.lastLoadingZone = lastLoadingZone;
	}

	public String getLastLoadingZoneID() {
		return lastLoadingZone;
	}

	public MapPlayer getMapPlayer() {
		return mapPlayer;
	}

	public void setMapPlayer(MapPlayer mapPlayer) {
		this.mapPlayer = mapPlayer;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
}
