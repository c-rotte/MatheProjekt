package projekt.mathe.game.engine.sound;

import java.util.HashMap;

public class SoundManager {

	private HashMap<String, Sound> sounds;
	
	public SoundManager() {
		sounds = new HashMap<>();
	}
	
	public void addSound(String id, Sound sound) {
		sounds.put(id, sound);
	}
	
	public void play(String id) {
		new Thread(() -> {
			sounds.get(id).reset().play();
		}).start();
	}
	
	public void stopPlaying(String id) {
		Sound sound = sounds.get(id);
		if(sound.isPlaying()) {
			sound.stop();
		}
	}
	
	public void stopEverySound() {
		for(String string : sounds.keySet()) {
			stopPlaying(string);
		}
	}
	
}
