package projekt.mathe.game.mathespiel.scenes;

import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.help.Camera;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;

public class MainSceneData extends SceneData{

	private String lastLoadingZone;
	private MapPlayer mapPlayer;
	private Camera camera;
	
	public MainSceneData() {
		lastLoadingZone = "";
	}
	
	public void setlastLoadingZoneID(String lastLoadingZone) {
		this.lastLoadingZone = lastLoadingZone;
	}

	public String getLastLoadingZoneID() {
		return lastLoadingZone;
	}

	public String getLastLoadingZone() {
		return lastLoadingZone;
	}

	public void setLastLoadingZone(String lastLoadingZone) {
		this.lastLoadingZone = lastLoadingZone;
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
