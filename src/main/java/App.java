import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import PacketProcessors.PacketProcessor;
import game.Board;
import io.javalin.Javalin;

public class App {

	private static Map<String,PacketProcessor> processors = new HashMap<String,PacketProcessor>();
	
	private static Map<Integer,Board> boards = new HashMap<Integer,Board>();
	
	public static void main(String[] args) {
		Javalin app = Javalin.create()
                .enableStaticFiles("/public")
                .start(8080);
		DatabaseManager.connect("jdbc:sqlite:sql/brtetris.db", "", "");
		app.post("daten", ctx -> {
            ctx.result(processEvent( URLDecoder.decode(ctx.queryString(), StandardCharsets.UTF_8.toString())));
        });
		
		processors.put("input",(JSONObject obj) ->{
			JSONObject response = new JSONObject();
			response.put("succes", boards.get(obj.getInt("id")).move(obj.getString("key")));
			return response;
		});
		processors.put("getCurrentBoard", (JSONObject obj) ->{
			if(!boards.containsKey(obj.getInt("id"))) {
				boards.put(obj.getInt("id"), new Board());
			}
			JSONObject container = new JSONObject();
			container.put("tag", "board");
			container.put("rows", boards.get(obj.getInt("id")).toJSON());
			return container;
		});
		processors.put("login", (JSONObject obj) ->{
			String email = obj.getString("email");
			String password = obj.getString("password");
			JSONObject container = new JSONObject();
			try {
				container.put("succes", DatabaseManager.login(email, password));
			} catch (Exception e) {
				e.printStackTrace();
				container.put("succes",false);
			} 
			container.put("sessionId", Math.random());
			return container;
		});
		processors.put("register",(JSONObject obj) ->{
			String email = obj.getString("email");
			String password = obj.getString("password");
			String user = obj.getString("user");
			JSONObject container = new JSONObject();
			try {
				container.put("succes", DatabaseManager.register(email, user, password));
			} catch (Exception e) {
				e.printStackTrace();
				container.put("succes",false);
			} 
			return container;
		});
	}

	private static String processEvent(String decode) {
		JSONObject obj = new JSONObject(decode);
		return processors.get(obj.get("tag")).process(obj).toString();
	}
	
}
