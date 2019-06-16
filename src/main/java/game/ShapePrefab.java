package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ShapePrefab {

	public static List<ShapePrefab> shapes = Arrays.asList(new ShapePrefab[] {new ShapePrefab("res/IShape.txt","I",5,0,1)});
	
	private String name;
	private int startX,startY;
	private int colorId;
	
	private int [][][]tables;
	
	public ShapePrefab(String url,String name,int startX,int startY,int colorId) {
		tables = new int[4][4][4]; //AmountShapes|Height|Width
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(url)));
			this.name = name;
			String line;
			int currentLine = 0;
			while((line = reader.readLine())!=null) {
				String[] split = line.split("\\s+");
				int currentShape = currentLine/4;
				if(currentLine %4 == 0) {
					tables[currentShape] = new int[4][4];
				}
				for(String s: split) {
					tables[currentShape][currentLine-(currentShape*4)] = new int[]{Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2]),Integer.parseInt(split[3])};
				}
				currentLine++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		this.startX = startX;
		this.startY = startY;
		this.colorId = colorId;
		
	}

	public String getName() {
		return name;
	}

	public int[][][] getTables() {
		return tables;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getColorId() {
		return colorId;
	}
	
	
}
