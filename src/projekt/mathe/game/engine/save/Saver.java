package projekt.mathe.game.engine.save;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import projekt.mathe.game.engine.help.Logger;

public class Saver {

	private final static String path = System.getProperty("user.dir") + "/saves.data";
	private static File file;
	private static HashMap<String, String> data;
	
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
			} catch (Exception e) {e.printStackTrace();}
		}
	}
	
	public static void setData(String key, Object data) {
		Saver.data.put(key, String.valueOf(data));
	}
	
	public static void saveData() {
		try {
			PrintWriter printWriter = new PrintWriter(new FileOutputStream(file));
			for(String key : data.keySet()) {
				printWriter.println(key + ":" + data.get(key));
			}
			printWriter.close();
			Logger.log("Saved " + data.keySet().size() + " lines!");
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static int getInt(String key) throws Exception {
		if(!data.containsKey(key)) {
			throw new Exception("Couldn't find Data!");
		}
		return Integer.valueOf(data.get(key));
	}
	
	public static double getDouble(String key) throws Exception {
		if(!data.containsKey(key)) {
			throw new Exception("Couldn't find Data!");
		}
		return Double.valueOf(data.get(key));
	}
	
	public static String getString(String key) throws Exception {
		if(!data.containsKey(key)) {
			throw new Exception("Couldn't find Data!");
		}
		return String.valueOf(data.get(key));
	}
	
	public static float getFloat(String key) throws Exception {
		if(!data.containsKey(key)) {
			throw new Exception("Couldn't find Data!");
		}
		return Float.valueOf(data.get(key));
	}
	
	public static short getShort(String key) throws Exception {
		if(!data.containsKey(key)) {
			throw new Exception("Couldn't find Data!");
		}
		return Short.valueOf(data.get(key));
	}
	
}
