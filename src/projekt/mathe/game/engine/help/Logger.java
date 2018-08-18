package projekt.mathe.game.engine.help;

public class Logger {

	private static int LOADED = 0;
	
	public static void log(String s) {
		System.out.println("[LOG] > " + s);
	}
	
	public static void notifyLoading(Object object) {
		LOADED++;
		log("Loading Object [" + LOADED + "] (" + object.toString() + ")");
	}
	
	public static int getLoadedObjects() {
		return LOADED;
	}

	public static float getRelativeLoadedObjects() {
		return ((float) LOADED) / ((float) getAmountToLoad());
	}
	
	public static int getAmountToLoad() {
		return 7014;
	}
	
}
