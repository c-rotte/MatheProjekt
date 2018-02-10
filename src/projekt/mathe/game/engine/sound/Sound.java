package projekt.mathe.game.engine.sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import projekt.mathe.game.engine.help.ResLoader;

public class Sound implements LineListener{

	private AudioInputStream stream;
	private String path;
	private boolean done;
	private boolean playing;
	private float volume;
	
	public Sound(String path, float volume) {
		this.volume = volume;
		this.path = path;
	}

	public void reload() {
		try {
			this.stream = AudioSystem.getAudioInputStream(ResLoader.getFile(path));
		} catch (UnsupportedAudioFileException | IOException e) {e.printStackTrace();}
	}
	
	public final void play() {
		playing = true;
		done = false;
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e1) {e1.printStackTrace();}
		try {
			clip.open(stream);
			FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float max = control.getMaximum();
			float min = control.getMinimum();
			float range = max - min;
			control.setValue(min + (range * volume));
			clip.start();
			waitUntilDone();
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		} finally {
			clip.close();
			playing = false;
		}
	}

	public Sound reset() {
		done = false;
		playing = false;
		reload();
		return this;
	}
	
	public void stop() {
		done = true;
		notifyAll();
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	@Override
	public void update(LineEvent event) {
		Type eType = event.getType();
		if(eType.equals(Type.STOP) || eType.equals(Type.CLOSE)) {
			done = true;
			notifyAll();
		}
	}

	private void waitUntilDone() {
		while(!done) {
			try {
				synchronized (this) {
					wait();
				}
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
}
