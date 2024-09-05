package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.controller.StockQuoteController;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.OkHttpClient;

public class Application {
  public static void main(String[] args) {
    Map<String, String> properties = new HashMap<>();
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

    if (!properties.containsValue("db-class")) {
      throw new IllegalArgumentException();
    }

    DatabaseConnectionManager dcm =
        new DatabaseConnectionManager(
            properties.get("host"),
            properties.get("database"),
            properties.get("username"),
            properties.get("password"));
    try (Connection connection = dcm.getConnection()) {
      OkHttpClient client = new OkHttpClient();

      QuoteDao quoteDao = new QuoteDao(connection);
      QuoteService quoteService = new QuoteService(quoteDao, properties.get("apiKey"), client);

      PositionDao positionDao = new PositionDao(connection);
      PositionService positionService = new PositionService(positionDao);

      StockQuoteController stockQuoteController =
          new StockQuoteController(quoteService, positionService);
      stockQuoteController.initClient();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
