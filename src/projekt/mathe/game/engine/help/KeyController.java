package projekt.mathe.game.engine.help;

import java.util.ArrayList;

public class KeyController {

	private ArrayList<Integer> pressed;

	public KeyController() {
		pressed = new ArrayList<>();
	}
	
	public void press(int code) {
		if(pressed.contains(code)) {
			return;
		}
		pressed.add(code);
	}
	
	public void release(int code) {
		pressed.remove(new Integer(code));
	}
	
	public boolean isPressed(int code) {
		return pressed.contains(code);
	}
	
	public void reset() {
		pressed.clear();
	}
	
}
