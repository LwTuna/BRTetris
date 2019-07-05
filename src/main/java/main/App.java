package main;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

import PacketProcessors.PacketProcessor;
import game.Lobby;
import io.javalin.Javalin;

public class App {

	private static Map<String,PacketProcessor> processors = new HashMap<String,PacketProcessor>();
	private static Lobby lobby;
	
	public static void main(String[] args) {
		Javalin app = Javalin.create()
                .enableStaticFiles("/public")
                .start(8080);
		DatabaseManager.connect("jdbc:sqlite:sql/brtetris.db", "", "");
		Settings.load();
		lobby = new Lobby(Settings.lobbySize);
		app.post("daten", ctx -> {
            ctx.result(processEvent( URLDecoder.decode(ctx.queryString(), StandardCharsets.UTF_8.toString())));
        });
		
		processors.put("input",(JSONObject obj) ->{
			JSONObject response = new JSONObject();
			response.put("tag", "input");
			try {
				response.put("succes", lobby.getBoards().get(obj.getInt("id")).move(obj.getString("key")));
			}catch(Exception e) {
				e.printStackTrace();
				response.put("succes", false);
			}
			return response;
		});
		processors.put("getCurrentBoard", (JSONObject obj) ->{
			if(!lobby.getBoards().containsKey(obj.getInt("id"))) {
				lobby.join(obj.getInt("id"));
			}
			JSONObject container = new JSONObject();
			container.put("tag", "board");
			container.put("started", lobby.getBoards().get(obj.getInt("id")).isRunning());
			container.put("rows", lobby.getBoards().get(obj.getInt("id")).toJSON());
			container.put("gameOver", lobby.getBoards().get(obj.getInt("id")).isGameOver());
			container.put("isWon", lobby.getBoards().get(obj.getInt("id")).isWon());
			container.put("playersAlive", lobby.getPlayersAlive());
			return container;
		});
		processors.put("login", (JSONObject obj) ->{
			String email = obj.getString("email");
			String password = obj.getString("password");
			
			JSONObject container = new JSONObject();
			container.put("tag", "login");
			try {
				container.put("succes", DatabaseManager.login(email, password));
			} catch (Exception e) {
				e.printStackTrace();
				container.put("succes",false);
			} 
			container.put("sessionId", (int)Math.round(Math.random() * 100000));
			return container;
		});
		processors.put("register",(JSONObject obj) ->{
			String email = obj.getString("email");
			String password = obj.getString("password");
			String user = obj.getString("user");
			JSONObject container = new JSONObject();
			container.put("tag", "register");
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
