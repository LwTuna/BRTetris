package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Shape {

	public static List<Shape> shapes = Arrays.asList(new Shape[] {new Shape("res/IShape.txt","I")});
	
	private String name;
	
	private int [][][]tables;
	
	public Shape(String url,String name) {
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
	}

	public String getName() {
		return name;
	}

	public int[][][] getTables() {
		return tables;
	}
	
	
}
