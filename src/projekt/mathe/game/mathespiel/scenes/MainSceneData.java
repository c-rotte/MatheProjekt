package projekt.mathe.game.mathespiel.scenes;

import projekt.mathe.game.engine.SceneData;

public class MainSceneData extends SceneData{

	private String lastLoadingZone;
	
	public MainSceneData() {
		lastLoadingZone = "";
	}
	
	public void setlastLoadingZoneID(String lastLoadingZone) {
		this.lastLoadingZone = lastLoadingZone;
	}

	public String getLastLoadingZoneID() {
		return lastLoadingZone;
	}
	
}
