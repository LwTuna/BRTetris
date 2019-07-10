import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import game.ShapePrefab;
import main.App;
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
	void testLobbyJoin() {
		app.getLobby().join(1);
		assertEquals(app.getLobby().getBoards().size(), 1);
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
	}
	@Test
	void testLobbyAutoStop() {
		app.getLobby().join(1);
		app.getLobby().join(2);
		app.getLobby().stop();
		assertFalse(app.getLobby().isRunning());
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
}
