package ca.jrvs.apps.stockquote.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.jrvs.apps.stockquote.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.model.Position;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PositionServiceIntTest {
  static Map<String, String> properties = new HashMap<>();

  static PositionDao positionDao;

  static PositionService positionService;

  @BeforeAll
  public static void setup() {
    try (BufferedReader br =
        new BufferedReader(new FileReader("src/main/resources/properties.txt"))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] tokens = line.split(":");
        properties.put(tokens[0], tokens[1]);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    DatabaseConnectionManager dcm =
        new DatabaseConnectionManager(
            properties.get("host"),
            properties.get("database"),
            properties.get("username"),
            properties.get("password"));
    try (Connection connection = dcm.getConnection()) {
      positionDao = new PositionDao(connection);
      positionService = new PositionService(positionDao);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void buyIntegrationTest() {
    Position position = positionService.buy("MSFT", 1, 200.0);
    assertEquals("MSFT", position.getTicker());
    assertEquals(1, position.getNumOfShares());
    assertEquals(200.0, position.getValuePaid());
  }

  @Test
  public void sellIntegrationTest() {
    positionService.sell("MSFT");
  }
}
