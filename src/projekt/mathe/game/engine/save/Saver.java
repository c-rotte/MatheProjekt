package projekt.mathe.game.engine.save;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import projekt.mathe.game.engine.help.Logger;
import projekt.mathe.game.mathespiel.Settings;

public class Saver {

	private final static String path = System.getenv("APPDATA") + "/saves.data";
	private static File file;
	private static HashMap<String, Object> data;
	
	public static void initialize() {
		file = new File(path);
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
	
	private static void loadSettings() {
		Settings.FPS_ANZEIGEN = Saver.containsData("fps") ? Saver.getBoolean("fps") : false;
		Settings.HITBOXEN_ANZEIGEN = Saver.containsData("hitbox") ? Saver.getBoolean("hitbox") : false;
		Settings.DARKMODE = Saver.containsData("darkmode") ? Saver.getBoolean("darkmode") : false;
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
		return (float) data.get(key);
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
	
	public static HashMap<String, Object> getMinigameStats(String id) {
		HashMap<String, Object> stats = new HashMap<>();
		for(String string : data.keySet()) {
			if(string.contains(id + "_")) {
				stats.put(string.replaceAll(id + "_", ""), data.get(string));
			}
		}
		return stats;
	}
	
	public static boolean containsData(String key) {
		return data.containsKey(key);
	}
	
}
