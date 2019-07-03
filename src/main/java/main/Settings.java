package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Settings {

	public static int lobbySize;
	public static boolean debugMode;
	
	private static final String path = "res/settings.txt";
	public static void load() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			reader.lines().forEach((s)->{
				String[] split = s.split("=");
				if(split[0].equalsIgnoreCase("lobbySize")) {
					lobbySize = Integer.parseInt(split[1].trim());
				}else if(split[0].trim().equalsIgnoreCase("debugmode")) {
					debugMode = Boolean.parseBoolean(split[1].trim());
				}
				
			});
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
