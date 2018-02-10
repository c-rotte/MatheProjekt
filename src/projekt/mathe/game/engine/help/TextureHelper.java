package projekt.mathe.game.engine.help;

import java.awt.Image;
import java.util.ArrayList;

public class TextureHelper {

	private ArrayList<String> modes;
	private ArrayList<Image[]> images;
	private ArrayList<Integer> steps;
	
	private String currMode;
	private Image[] currImages;
	private int currSteps;
	
	private Image i;
	
	private float tickCounter;
	private int positionCounter;
	
	public TextureHelper() {
		modes = new ArrayList<>();
		images = new ArrayList<>();
		steps = new ArrayList<>();
	}
	
	public void addState(String mode, int steps, Image... images) {
		this.modes.add(mode);
		this.images.add(images);
		this.steps.add(steps);
		if(modes.size() == 1) {
			switchState(mode);
		}
	}
	
	public int getSize() {
		return modes.size();
	}
	
	public void switchState(String mode) {
		int pos = modes.indexOf(mode);
		currMode = mode;
		currImages = images.get(pos);
		currSteps = steps.get(pos);
		tickCounter = 0;
		positionCounter = 0;
		i = currImages[0];
	}
	
	public Image getCurrentImage() {
		return i;
	}
	
	public String getCurrentMode() {
		return currMode;
	}
	
	public void onTick(float delta) {
		tickCounter += delta;
		if(tickCounter >= currSteps) {
			tickCounter = 0;
			positionCounter++;
			i = currImages[positionCounter % currImages.length];
		}
	}
	
}
