package projekt.mathe.game.mathespiel.scenes.game.player;

import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.help.TextureHelper;

public class MapPlayerTextureHelper extends TextureHelper{
	
	private String gender;
	private static final int running_Step = 7;
	
	public MapPlayerTextureHelper() {
		gender = "girl";
		addState("girl_up_standing", 1000, ResLoader.getImageByName("game/player/girl/oben_stehen.png"));
		addState("girl_up_running", running_Step, ResLoader.getImageByName("game/player/girl/oben_gehen_1.png"), ResLoader.getImageByName("game/player/girl/oben_gehen_2.png"));
		addState("girl_down_standing", 1000, ResLoader.getImageByName("game/player/girl/unten_stehen.png"));
		addState("girl_down_running", running_Step, ResLoader.getImageByName("game/player/girl/unten_gehen_1.png"), ResLoader.getImageByName("game/player/girl/unten_gehen_2.png"));
		addState("girl_right_standing", 1000, ResLoader.getImageByName("game/player/girl/rechts_stehen.png"));
		addState("girl_right_running", running_Step, ResLoader.getImageByName("game/player/girl/rechts_gehen_1.png"), ResLoader.getImageByName("game/player/girl/rechts_gehen_2.png"));
		addState("girl_left_standing", 1000, ResLoader.getImageByName("game/player/girl/links_stehen.png"));
		addState("girl_left_running", running_Step, ResLoader.getImageByName("game/player/girl/links_gehen_1.png"), ResLoader.getImageByName("game/player/girl/links_gehen_2.png"));
	}
	
	public void setGender(boolean girl) {
		if(girl) {
			gender = "girl";
		}else {
			gender = "boy";
		}
	}
	
	public void switchPlayerState(String mode) {
		switchState(gender + "_" + mode);
	}
	
}
