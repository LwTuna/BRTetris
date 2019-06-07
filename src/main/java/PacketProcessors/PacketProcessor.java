package PacketProcessors;
import org.json.JSONObject;

public interface PacketProcessor {

	public JSONObject process(JSONObject obj);
	
}
