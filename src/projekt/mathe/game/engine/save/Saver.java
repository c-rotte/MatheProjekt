package projekt.mathe.game.engine.save;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Logger;
import projekt.mathe.game.mathespiel.Settings;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;

public class Saver {

	private static File file;
	private static HashMap<String, Object> data;
	
	public static void initialize() {
		file = new File(getAPPDATAPath() + "/saves.data");
		data = new HashMap<>();
		if(!file.exists()) {
			try {
				file.createNewFile();
				Logger.log("Savefile created!");
			} catch (IOException e) {e.printStackTrace();}
		}else {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = null;
				while((line = reader.readLine()) != null) {
					String[] split = line.split(":");
					if(split.length == 2) {
						data.put(split[0], split[1]);
					}
				}
				reader.close();
				Logger.log("Savefile loaded!");
				loadSettings();
			} catch (Exception e) {e.printStackTrace();}
		}
	}
	
	private static String getAPPDATAPath() {
		String OS = (System.getProperty("os.name")).toUpperCase();
		if(OS.contains("WIN")) {
			return System.getenv("APPDATA");
		}else if(OS.contains("MAC")){
			return System.getProperty("user.home") + "/Library";
		}else {
			return System.getProperty("user.home");
		}
	}
	
	private static void loadSettings() {
		Settings.FPS_ANZEIGEN = Saver.containsData("fps") ? Saver.getBoolean("fps") : false;
		Settings.HITBOXEN_ANZEIGEN = Saver.containsData("hitbox") ? Saver.getBoolean("hitbox") : false;
		Settings.DARKMODE = Saver.containsData("darkmode") ? Saver.getBoolean("darkmode") : false;
		Settings.SMOOTH = Saver.containsData("smooth") ? Saver.getBoolean("smooth") : false;
		Settings.GIRL = Saver.containsData("girl") ? Saver.getBoolean("girl") : false;
	}
	
	public static void removeData(String key) {
		data.remove(key);
	}
	
	public static void setData(String key, Object data) {
		Saver.data.put(key, String.valueOf(data));
	}
	
	public static void saveData() {
		try {
			PrintWriter printWriter = new PrintWriter(new FileOutputStream(file));
			for(String key : data.keySet()) {
				printWriter.println(key + ":" + String.valueOf(data.get(key)));
			}
			printWriter.close();
			Logger.log("Saved " + data.keySet().size() + " lines!");
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static int getInt(String key) {
		if(!data.containsKey(key)) {
			throw new NullPointerException("Couldn't find Data!");
		}
		return (int) data.get(key);
	}
	
	public static double getDouble(String key) {
		if(!data.containsKey(key)) {
			throw new NullPointerException("Couldn't find Data!");
		}
		return (double) data.get(key);
	}
	
	public static String getString(String key) {
		if(!data.containsKey(key)) {
			throw new NullPointerException("Couldn't find Data!");
		}
		return String.valueOf(data.get(key));
	}
	
	public static float getFloat(String key) {
		if(!data.containsKey(key)) {
			throw new NullPointerException("Couldn't find Data!");
		}
		return Float.parseFloat((String) data.get(key));
	}
	
	public static short getShort(String key) {
		if(!data.containsKey(key)) {
			throw new NullPointerException("Couldn't find Data!");
		}
		return (short) data.get(key);
	}
	
	public static boolean getBoolean(String key) {
		if(!data.containsKey(key)) {
			throw new NullPointerException("Couldn't find Data!");
		}
		return String.valueOf(data.get(key)).equals("true");
	}
	
	public static boolean containsData(String key) {
		return data.containsKey(key);
	}

	public static void newGame() {
		setData("existingGame", true);
		setData("safeCode", Helper.generateRandomString(5));
		setData("currCode", "-----");
		
		removeData("readFemale");
		
		removeData("lastPosX");
		removeData("lastPosY");
		removeData("lastCamFocusX");
		removeData("lastCamFocusY");
		removeData("lastScene");
		removeData("lastDir");
		
		removeData("peState");
		
		removeData("bossdefeated");
	}
	
	public static void saveCurrentState(MapPlayer player, Scene scene) {
		setData("lastPosX", player.getX());
		setData("lastPosY", player.getY());
		setData("lastCamFocusX", scene.camera.getFocusX());
		setData("lastCamFocusY", scene.camera.getFocusY());
		setData("lastScene", scene.getId());
		setData("lastDir", player.direction);
	}
	
	public static void clearPlayerState() {
		removeData("lastPosX");
		removeData("lastPosY");
		removeData("lastCamFocusX");
		removeData("lastCamFocusY");
		removeData("lastScene");
		removeData("lastDir");
	}
	
}
