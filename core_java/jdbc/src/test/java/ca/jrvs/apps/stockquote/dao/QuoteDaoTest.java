package ca.jrvs.apps.stockquote.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.practice.JsonParser;
import ca.jrvs.apps.stockquote.model.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class QuoteDaoTest {
  @Mock Connection connection;

  @Mock PreparedStatement statement;

  @Mock ResultSet resultSet;

  QuoteDao quoteDao;

  ObjectMapper objectMapper;

  @BeforeEach
  public void setup() {
    objectMapper = new ObjectMapper();
    quoteDao = new QuoteDao(connection);
  }

  @Test
  public void findExistingIdTest() throws IOException, SQLException {
    mockConnection();
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    mockQuote();
    Optional<Quote> quote = quoteDao.findById("MSFT");
    assertTrue(quote.isPresent());
    assertEquals(
        objectMapper.readTree(new File("src/test/resources/json/quote.json")),
        objectMapper.readTree(JsonParser.toJson(quote.get(), true, true)));
  }

  @Test
  public void findMissingIdTest() throws SQLException {
    mockConnection();
    Optional<Quote> quote = quoteDao.findById("APPL");
    assertFalse(quote.isEmpty());
  }

  @Test
  public void findNullIdTest() {
    IllegalArgumentException error =
        assertThrows(
            IllegalArgumentException.class, () -> quoteDao.findById(null), "ERROR: Missing ID");
    assertTrue(error.getMessage().contains("ERROR: Missing ID"));
  }

  @Test
  public void findAllQuotesTest() throws SQLException {
    mockConnection();
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    Iterable<Quote> quotes = quoteDao.findAll();
    List<Quote> quoteList = new ArrayList<>();
    quotes.forEach(quoteList::add);
    assertEquals(2, quoteList.size());
  }

  @Test
  public void findEmptyQuotesTest() throws SQLException {
    mockConnection();
    when(resultSet.next()).thenReturn(false);
    Iterable<Quote> quotes = quoteDao.findAll();
    List<Quote> quoteList = new ArrayList<>();
    quotes.forEach(quoteList::add);
    assertEquals(0, quoteList.size());
  }

  @Test
  public void deleteByIdTest() throws SQLException {
    when(connection.prepareStatement(any(String.class))).thenReturn(statement);
    quoteDao.deleteById("MSFT");
  }

  @Test
  public void deleteMissingIdTest() throws SQLException {
    when(connection.prepareStatement(any(String.class))).thenReturn(statement);
    when(statement.execute()).thenThrow(new SQLException());
    assertThrows(IllegalArgumentException.class, () -> quoteDao.deleteById("APPL"), "");
  }

  @Test
  public void deleteAllTest() throws SQLException {
    when(connection.prepareStatement(any(String.class))).thenReturn(statement);
    quoteDao.deleteAll();
  }

  @Test
  public void deleteAllErrorTest() throws SQLException {
    when(connection.prepareStatement(any(String.class))).thenReturn(statement);
    when(statement.execute()).thenThrow(new SQLException());
    assertThrows(IllegalArgumentException.class, () -> quoteDao.deleteAll(), "");
  }

  @Test
  public void createTest() throws SQLException, IOException {
    Quote quote = new Quote();
    quote.setTicker("MSFT");
    quote.setOpen(151.65);
    quote.setHigh(153.42);
    quote.setLow(0.0);
    quote.setPrice(123.0);
    quote.setVolume(20);
    quote.setLatestTradingDay(new Date(1725595200000L));
    quote.setPreviousClose(6.0);
    quote.setChange(2.0);
    quote.setChangePercent("0.2374%");
    quote.setTimestamp(new Timestamp(1725508800000L));
    when(connection.prepareStatement(any(String.class))).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    mockQuote();
    Quote result = quoteDao.save(quote);
    assertEquals(
        objectMapper.readTree(new File("src/test/resources/json/quote.json")),
        objectMapper.readTree(JsonParser.toJson(result, true, true)));
  }

  private void mockConnection() throws SQLException {
    when(connection.prepareStatement(any(String.class))).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
  }

  private void mockQuote() throws SQLException {
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
}
