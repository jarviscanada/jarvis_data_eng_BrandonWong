package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.model.Position;
import ca.jrvs.apps.stockquote.util.CrudDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PositionDao extends CrudDAO<Position, String> {

  private static final String INSERT =
      "INSERT INTO position (symbol, number_of_shares, value_paid) VALUES (?, ?, ?)";
  private static final String GET_ONE = "SELECT * FROM position WHERE symbol=?";
  private static final String GET_ALL = "SELECT * FROM position";
  private static final String DELETE_ONE = "DELETE FROM position WHERE symbol=?";
  private static final String DELETE_ALL = "TRUNCATE position";

  public PositionDao(Connection connection) {
    super(connection);
  }

  @Override
  public Position save(Position entity) throws IllegalArgumentException {
    try (PreparedStatement statement = this.connection.prepareStatement(INSERT); ) {
      statement.setString(1, entity.getTicker());
      statement.setString(2, String.valueOf(entity.getNumOfShares()));
      statement.setString(3, String.valueOf(entity.getValuePaid()));
      statement.execute();
      return this.findById(entity.getTicker()).orElseThrow(NullPointerException::new);
    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public Optional<Position> findById(String id) throws IllegalArgumentException {
    if (id == null) throw new IllegalArgumentException("ERROR: Missing ID");
    Position position = new Position();
    try (PreparedStatement statement = this.connection.prepareStatement(GET_ONE); ) {
      statement.setString(1, id);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        position.setTicker(rs.getString("symbol"));
        position.setNumOfShares(rs.getInt("num_of_shares"));
        position.setValuePaid(rs.getDouble("value_paid"));
      }
    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw new IllegalArgumentException(e);
    }
    return Optional.of(position);
  }

  @Override
  public Iterable<Position> findAll() {
    List<Position> positionList = new ArrayList<>();
    try (PreparedStatement statement = this.connection.prepareStatement(GET_ALL); ) {
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        Position position = new Position();
        position.setTicker(rs.getString("symbol"));
        position.setNumOfShares(rs.getInt("num_of_shares"));
        position.setValuePaid(rs.getDouble("value_paid"));
        positionList.add(position);
      }
    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw new IllegalArgumentException(e);
    }
    return positionList;
  }

  @Override
  public void deleteById(String id) throws IllegalArgumentException {
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
}
