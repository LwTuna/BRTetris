import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import game.Board;
import game.Lobby;
import game.ShapePrefab;
import main.App;
import main.DatabaseException;
import main.DatabaseManager;
import main.LogUI;
import main.Settings;

@TestInstance(Lifecycle.PER_CLASS)
class JUnitTest{
	
	App app;
	
	public JUnitTest() {
		app =new App();
	}
	
	@Test
	@BeforeAll
	void testJunitConstuctorCall() {
		assertNotNull(app);
	}
	
	@Test
	void testJavalinPort() {
		assertEquals(8080, app.getApp().port());
	}
	@Test
	void hasProcessors() {
		assertEquals(4, app.getProcessors().size());
	}
	@Test
	void lobbyCreated() {
		assertNotNull(app.getLobby());
	}
	
	@Test
	void loadedSettings() {
		assertNotNull(Settings.debugMode);
		assertNotNull(Settings.lobbySize);
	}
	@Test
	void connectToDatabase() {
		assertTrue(DatabaseManager.isConnected());
	}
	@Test 
	void executeStatementOnDatabase(){
		assertNotNull(DatabaseManager.executeStatement("Select * from user;"));
	}
	@Test
	void registerUser() {
		assertTrue(DatabaseManager.register("123", "123", "123"));
	}
	@Test
	void loginTestUser() {
		try {
			assertTrue(DatabaseManager.login("123", "123"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Test
	void testPacketProcessor() {
		assertNotNull(app.processEvent("{\"tag\":\"login\",\"email\":\"123\",\"password\":\"123\"}"));
	}
	
	@Test
	void testLobbySize() {
		assertEquals(Settings.lobbySize, app.getLobby().getSize());
	}
	
	
	@Test
	void testlobbyLeave() {
		app.getLobby().join(1);
		app.getLobby().remove(1);
		assertEquals(app.getLobby().getBoards().size(), 0);
	}
	@Test 
	void testLobbyAutoStart() {
		app.getLobby().join(1);
		app.getLobby().join(2);
		assertTrue(app.getLobby().isRunning());
		app.getLobby().remove(1);
		app.getLobby().remove(2);
	}
	@Test
	void testLobbyAutoStop() {
		app.getLobby().join(1);
		app.getLobby().join(2);
		app.getLobby().stop();
		assertFalse(app.getLobby().isRunning());
	}
	@Test
	@BeforeAll
	void testAssertions() {
		assertTrue(true);
	}
	@Test
	@BeforeAll
	void testAssumptions() {
		assumeTrue(true);
	}
	
	@Test 
	void testShapesLoaded() {
		assertEquals(ShapePrefab.shapes.size(), 7);
	}
	@Test
	void testShapesStartX() {
		ShapePrefab.shapes.stream().forEach((s)-> {
			assertEquals(3, s.getStartX());
		});
	}
	int currentColor = 1;
	@Test
	void testShapeDiffrentColors() {
		ShapePrefab.shapes.stream().forEach((s)-> {
			assertEquals(currentColor, s.getColorId());			
			currentColor++;
		});
	}
	@Test
	void testThirdShapeName() {
		assertEquals("L", ShapePrefab.shapes.get(2).getName());
	}
	
	@Test
	void testShapeStartY() {
		ShapePrefab.shapes.stream().forEach((s)-> {
			assertEquals(0, s.getStartY());
		});
	}
	
	@Test
	void testShapeTable() {
		assertEquals(0, ShapePrefab.shapes.get(0).getTables()[0][0][0]);
		assertEquals(1, ShapePrefab.shapes.get(0).getTables()[0][1][0]);
	}
	@Test
	void testLobbyJoinWhenFull() {
		for(int i=1;i<=Settings.lobbySize;i++) {
			app.getLobby().join(i);
		}
		app.getLobby().join(Settings.lobbySize+1);
		assertEquals(Settings.lobbySize, app.getLobby().getBoards().size());
	}
	
	@Test
	void testIsResultInJsonFormat() {
		String result = app.processEvent("{\"tag\":\"register\",\"email\":\"123\",\"user\":\"123\",\"password\":\"123\"}");
		JSONObject r = new JSONObject(result);
		assertNotNull(r);
	}
	
	@Test
	void testRegisterPacket() {
		String result = app.processEvent("{\"tag\":\"register\",\"email\":\"123\",\"user\":\"123\",\"password\":\"123\"}");
		JSONObject r = new JSONObject(result);
		assertTrue(r.getBoolean("succes"));
	}
	
	@Test
	void testLoginPacket() {
		String result = app.processEvent("{\"tag\":\"login\",\"email\":\"123\",\"password\":\"123\"}");
		JSONObject r = new JSONObject(result);
		assertTrue(r.getBoolean("succes"));
		app.getLobby().remove(r.getInt("sessionId"));
	}
	@Test
	void testLoginGrantsSessionId() {
		JSONObject result = new JSONObject(app.processEvent("{\"tag\":\"login\",\"email\":\"123\",\"password\":\"123\"}"));
		assertNotNull(result.getInt("sessionId"));
		app.getLobby().remove(result.getInt("sessionId"));
	}
	@Test
	void testGetBoardStarted() {
		JSONObject loginresult = new JSONObject(app.processEvent("{\"tag\":\"login\",\"email\":\"123\",\"password\":\"123\"}"));
		
		JSONObject boardResult = new JSONObject(app.processEvent("{\"tag\":\"getCurrentBoard\",\"id\":"+loginresult.getInt("sessionId")+"}"));
		assertFalse(boardResult.getBoolean("started"));
		app.getLobby().remove(loginresult.getInt("sessionId"));
	}
	
	
	@Test
	void testGetBoardStartedWithLobbyFull() {
		List<Integer> ids = new ArrayList<Integer>();
		for(int i=0;i<Settings.lobbySize;i++) {
			JSONObject loginresult = new JSONObject(app.processEvent("{\"tag\":\"login\",\"email\":\"123\",\"password\":\"123\"}"));
			ids.add(loginresult.getInt("sessionId"));
		}
		JSONObject loginResult = new JSONObject(app.processEvent("{\"tag\":\"login\",\"email\":\"123\",\"password\":\"123\"}"));
		ids.add(loginResult.getInt("sessionId"));
		JSONObject boardResult = new JSONObject(app.processEvent("{\"tag\":\"getCurrentBoard\",\"id\":"+loginResult.getInt("sessionId")+"}"));
		assertFalse(boardResult.getBoolean("started"));
		ids.forEach((id)->{app.getLobby().remove(id);});
	}
	@Test
	@AfterAll
	void testLogUI() {
		assertNotNull(LogUI.getMessages());
	}
	@Test
	void testLogUIMessageAppend() {
		
		int messages = LogUI.getMessages().size();
		LogUI.print("test");
		if(Settings.debugMode) {
			assertEquals(messages+1, LogUI.getMessages().size());
			assertEquals(LogUI.getMessages().get(LogUI.getMessages().size()-1), "test");
		}else {
			assertEquals(messages, LogUI.getMessages().size());
		}
	}
	
	@Test
	void testDatabaseExceptionOnLogUi() {
		int messages = LogUI.getMessages().size();
		new DatabaseException("Wanted Eception on Test").printStackTrace();
		if(Settings.debugMode) {
			assertEquals(messages+1, LogUI.getMessages().size());
		}else {
			assertEquals(messages, LogUI.getMessages().size());
		}
	}
	@Test
	void createTestLobby() {
		Lobby lobby = new Lobby(2);
		lobby.join(1);
		lobby.join(2);
		
		assertTrue(lobby.isRunning());
	}
	@Test
	void createTestBoards() {
		Lobby lobby = new Lobby(2);
		lobby.join(1);
		lobby.join(2);
		
		assertTrue(lobby.getBoards().get(1).isRunning());
	}
	@Test
	void testWin() {
		Lobby lobby = new Lobby(2);
		lobby.join(1);
		lobby.join(2);
		lobby.getBoards().get(1).gameOver();
		assertEquals(1, lobby.getPlayersAlive());
	}
	@Test
	void testMoveDown() {
		Lobby lobby = new Lobby(2);
		lobby.join(1);
		lobby.join(2);
		assertTrue(lobby.getBoards().get(1).move("down"));
	}
	
	
	@Test
	void testMoveLeft() {
		Lobby lobby = new Lobby(2);
		lobby.join(1);
		lobby.join(2);
		assertTrue(lobby.getBoards().get(1).move("left"));
	}
	@Test
	void testMoveRight() {
		Lobby lobby = new Lobby(2);
		lobby.join(1);
		lobby.join(2);
		assertTrue(lobby.getBoards().get(1).move("right"));
	}
	
	@Test
	void testRotate() {
		Lobby lobby = new Lobby(2);
		lobby.join(1);
		lobby.join(2);
		assertTrue(lobby.getBoards().get(1).move("rotate"));
	}
	@Test
	void testBoardHeight() {
		assertEquals(20, Board.height);
	}
	@Test
	void testMoveDrop() {
		Lobby lobby = new Lobby(2);
		lobby.join(1);
		lobby.join(2);
		assertTrue(lobby.getBoards().get(1).move("drop"));
	}
	
	
	@Test
	void testClearRow() {
		Lobby lobby = new Lobby(2);
		lobby.join(1);
		lobby.join(2);
		lobby.getBoards().get(1).clearRow(1);
		for(int i=0;i<Board.width;i++) {
			assertEquals(0, lobby.getBoards().get(1).getTable()[i][1]);
		}
	}
	
	@Test
	void testBoardWidth() {
		assertEquals(10, Board.width);
	}
	
	@Test
	void testNewShapeInCurrentShape() {
		Lobby lobby = new Lobby(2);
		lobby.join(1);
		lobby.join(2);
		lobby.getBoards().get(1).createNewShape();
		assertTrue(lobby.getBoards().get(1).isGameOver());
	}
	@Test
	void testWinResult() {
		Lobby lobby = app.getLobby();
		lobby.join(1);
		lobby.join(2);
		lobby.getBoards().get(1).win();
		JSONObject boardResult = new JSONObject(app.processEvent("{\"tag\":\"getCurrentBoard\",\"id\":1}"));
		assertTrue(boardResult.getBoolean("isWon"));
		lobby.stop();
	}
	
	@Test
	void testGameOverResult() {
		Lobby lobby = app.getLobby();
		lobby.join(1);
		lobby.join(2);
		lobby.getBoards().get(1).gameOver();
		JSONObject boardResult = new JSONObject(app.processEvent("{\"tag\":\"getCurrentBoard\",\"id\":1}"));
		assertTrue(boardResult.getBoolean("gameOver"));
		lobby.stop();
	}
	
	@Test
	void testDownAndNewShape() {
		Lobby lobby = app.getLobby();
		lobby.join(1);
		lobby.join(2);
		for(int i=0;i<5;i++) {
			lobby.getBoards().get(1).move("down");
		}
		lobby.getBoards().get(1).createNewShape();
		assertFalse(lobby.getBoards().get(1).isGameOver());
	}
	@Test
	void testLoginUserNotCreated() {
		try {
			assertFalse(DatabaseManager.login("asdfaisuhdfausfhd", "uihdfasiuasfahipusdf"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Test
	void testSettingsFileExists() {
		assertTrue(new File("res/settings.txt").exists());
	}
	
	@Test
	void testAllFilesInRes() {
		File folder = new File("res");
		assertEquals(8, folder.listFiles().length);
	}
	
	@Test
	void testAllFilesInWebsiteRes() {
		File folder = new File("src/main/resources/public/res");
		assertEquals(9, folder.listFiles().length);
	}
	
	@Test
	void testOrgJsonLib() {
		JSONObject obj = new JSONObject();
		obj.put("test", true);
		String asString = obj.toString();
		assertTrue(new JSONObject(asString).getBoolean("test"));
	}
	@Test
	void testSQLite() {
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:sql/brtetris.db", "", "");
			assertNotNull(connection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testGradleBuildMainClass() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("build.gradle")));
			List<String> lines = Arrays.asList((String[])br.lines().toArray());
			assertTrue(lines.get(33).contains("mainClassName = 'main/App'"));
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testGradleSettings() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("build.settings")));
			List<String> lines = Arrays.asList((String[])br.lines().toArray());
			assertTrue(lines.get(9).contains("rootProject.name = 'BRTetris'"));
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
