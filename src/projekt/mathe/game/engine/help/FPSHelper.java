package projekt.mathe.game.engine.help;

public class FPSHelper {

	private long startTime;
	
	private int fps;
	
	private int returnFPS;
	
	public FPSHelper() {
		startTime = System.currentTimeMillis();
		fps = 0;
		returnFPS = 0;
	}
	
	public void frameFinished() {
		fps++;
		if(System.currentTimeMillis() - startTime >= 1000) {
			returnFPS = fps;
			fps = 0;
			startTime = System.currentTimeMillis();
		}
	}
	
	public int getFPS() {
		return returnFPS;
	}
	
}
