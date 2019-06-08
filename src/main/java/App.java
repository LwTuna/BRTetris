import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		app.post("daten", ctx -> {
            ctx.result(processEvent( URLDecoder.decode(ctx.queryString(), StandardCharsets.UTF_8.toString())));
        });
		
		processors.put("input",(JSONObject obj) ->{
			
			return obj;
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
	}

	private static String processEvent(String decode) {
		System.out.println(decode);
		JSONObject obj = new JSONObject(decode);
		return processors.get(obj.get("tag")).process(obj).toString();
	}
	
}
