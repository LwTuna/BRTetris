import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import PacketProcessors.PacketProcessor;
import io.javalin.Javalin;

public class App {

	private static Map<String,PacketProcessor> processors = new HashMap<String,PacketProcessor>();
	
	public static void main(String[] args) {
		Javalin app = Javalin.create()
                .enableStaticFiles("/public")
                .start(8080);
		
		app.post("daten", ctx -> {
            ctx.result(processEvent( URLDecoder.decode(ctx.queryString(), StandardCharsets.UTF_8.toString())));
        });
		processors.put("test", (JSONObject obj) ->{
			JSONObject response = new JSONObject();
			response.put("tag", "test");
			return response;
		});
		
	}

	private static String processEvent(String decode) {
		JSONObject obj = new JSONObject(decode);
		return processors.get(obj.get("tag")).process(obj).toString();
	}
	
}
