package ca.jrvs.apps.stockquote.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.jrvs.apps.stockquote.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.QuoteHttpHelper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class QuoteServiceIntTest {
  static Map<String, String> properties = new HashMap<>();

  static QuoteHttpHelper quoteHttpHelper;

  static QuoteDao quoteDao;

  static QuoteService quoteService;

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
      OkHttpClient httpClient = new OkHttpClient();
      quoteHttpHelper = new QuoteHttpHelper(properties.get("apiKey"), httpClient);
      quoteDao = new QuoteDao(connection);
      quoteService = new QuoteService(quoteDao, quoteHttpHelper);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void fetchQuoteIntegrationTest() {
    Optional<Quote> quote = quoteService.fetchQuoteDataFromAPI("MSFT");
    assertTrue(quote.isPresent());
  }

  @Test
  public void fetchInvalidQuoteIntegrationTest() {
    Optional<Quote> quote = quoteService.fetchQuoteDataFromAPI("HYNG");
    assertTrue(quote.isEmpty());
  }
}
