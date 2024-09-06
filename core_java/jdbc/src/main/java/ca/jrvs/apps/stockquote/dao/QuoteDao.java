package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.CrudDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuoteDao extends CrudDAO<Quote, String> {

  private static final String INSERT =
      "INSERT INTO quote "
          + "(symbol, open, high, low, price, volume, latest_trading_day, "
          + "previous_close, change, change_percent, timestamp) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String GET_ONE =
      "SELECT * FROM quote WHERE symbol=? LIMIT 1 ORDER BY timestamp DESC";

  private static final String GET_ALL = "SELECT * FROM quote";

  private static final String DELETE_ONE = "DELETE FROM quote WHERE symbol=?";

  private static final String DELETE_ALL = "TRUNCATE quote";

  public QuoteDao(Connection connection) {
    super(connection);
  }

  @Override
  public Quote save(Quote entity) throws IllegalArgumentException {
    try (PreparedStatement statement = this.connection.prepareStatement(INSERT); ) {
      statement.setString(1, entity.getTicker());
      statement.setString(2, String.valueOf(entity.getOpen()));
      statement.setString(3, String.valueOf(entity.getHigh()));
      statement.setString(4, String.valueOf(entity.getLow()));
      statement.setString(5, String.valueOf(entity.getPrice()));
      statement.setString(6, String.valueOf(entity.getVolume()));
      statement.setString(7, String.valueOf(entity.getLatestTradingDay()));
      statement.setString(8, String.valueOf(entity.getPreviousClose()));
      statement.setString(9, String.valueOf(entity.getChange()));
      statement.setString(10, entity.getChangePercent());
      statement.setString(11, String.valueOf(entity.getTimestamp()));
      statement.execute();
      return this.findById(entity.getTicker()).orElseThrow(NullPointerException::new);
    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public Optional<Quote> findById(String id) throws IllegalArgumentException {
    Quote quote = new Quote();
    try (PreparedStatement statement = this.connection.prepareStatement(GET_ONE); ) {
      statement.setString(1, id);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        createQuote(rs, quote);
      }
    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw new IllegalArgumentException(e);
    }
    return Optional.of(quote);
  }

  @Override
  public Iterable<Quote> findAll() {
    List<Quote> quoteList = new ArrayList<>();
    try (PreparedStatement statement = this.connection.prepareStatement(GET_ALL); ) {
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        Quote quote = new Quote();
        createQuote(rs, quote);
        quoteList.add(quote);
      }
    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw new IllegalArgumentException(e);
    }
    return quoteList;
  }

  @Override
  public void deletebyId(String id) throws IllegalArgumentException {
    try (PreparedStatement statement = this.connection.prepareStatement(DELETE_ONE); ) {
      statement.setString(1, id);
      statement.execute();
    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public void deleteAll() {
    try (PreparedStatement statement = this.connection.prepareStatement(DELETE_ALL); ) {
      statement.execute();
    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw new IllegalArgumentException(e);
    }
  }

  private void createQuote(ResultSet rs, Quote quote) throws SQLException {
    quote.setTicker(rs.getString("symbol"));
    quote.setOpen(rs.getDouble("open"));
    quote.setHigh(rs.getDouble("high"));
    quote.setLow(rs.getDouble("low"));
    quote.setPrice(rs.getDouble("price"));
    quote.setVolume(rs.getInt("volume"));
    quote.setLatestTradingDay(rs.getDate("latest_trading_day"));
    quote.setPreviousClose(rs.getDouble("previous_close"));
    quote.setChange(rs.getDouble("change"));
    quote.setChangePercent(rs.getString("change_percent"));
    quote.setTimestamp(rs.getTimestamp("timestamp"));
  }
}
