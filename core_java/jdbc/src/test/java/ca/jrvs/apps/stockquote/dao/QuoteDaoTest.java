package ca.jrvs.apps.stockquote.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.practice.JsonParser;
import ca.jrvs.apps.stockquote.model.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
public class QuoteDaoTest {
  private static final Logger logger = LoggerFactory.getLogger(QuoteDaoTest.class);
  @Mock Connection connection;

  @Mock PreparedStatement statement;

  @Mock ResultSet resultSet;

  QuoteDao quoteDao;

  ObjectMapper objectMapper;

  @BeforeEach
  public void setup() throws SQLException {
    objectMapper = new ObjectMapper();
    quoteDao = new QuoteDao(connection);
    when(connection.prepareStatement(any(String.class))).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    when(resultSet.getString("symbol")).thenReturn("MSFT");
    when(resultSet.getDouble("open")).thenReturn(151.65);
    when(resultSet.getDouble("high")).thenReturn(153.42);
    when(resultSet.getDouble("low")).thenReturn(0.0);
    when(resultSet.getDouble("price")).thenReturn(123.0);
    when(resultSet.getInt("volume")).thenReturn(20);
    when(resultSet.getDate("latest_trading_day")).thenReturn(Date.valueOf("2024-09-06"));
    when(resultSet.getDouble("previous_close")).thenReturn(6.0);
    when(resultSet.getDouble("change")).thenReturn(2.0);
    when(resultSet.getString("change_percent")).thenReturn("0.2374%");
    when(resultSet.getTimestamp("timestamp")).thenReturn(new Timestamp(1725508800000L));
  }

  @Test
  public void test() throws IOException {
    Optional<Quote> quote = quoteDao.findById("MSFT");
    assertTrue(quote.isPresent());
    assertEquals(
        objectMapper.readTree(JsonParser.toJson(quote.get(), true, true)),
        objectMapper.readTree(new File("src/test/resources/json/quote.json")));
  }
}
