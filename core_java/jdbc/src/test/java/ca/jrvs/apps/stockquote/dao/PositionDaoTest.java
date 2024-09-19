package ca.jrvs.apps.stockquote.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.stockquote.model.Position;
import ca.jrvs.apps.stockquote.util.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PositionDaoTest {
  @Mock Connection connection;

  @Mock PreparedStatement statement;

  @Mock ResultSet resultSet;

  PositionDao positionDao;

  ObjectMapper mapper;

  @BeforeEach
  public void setup() {
    mapper = new ObjectMapper();
    positionDao = new PositionDao(connection);
  }

  @Test
  public void findExistingIdTest() throws IOException, SQLException {
    when(connection.prepareStatement(any(String.class))).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    when(resultSet.getString("symbol")).thenReturn("MSFT");
    when(resultSet.getInt("num_of_shares")).thenReturn(1);
    when(resultSet.getDouble("value_paid")).thenReturn(200.0);
    Optional<Position> position = positionDao.findById("MSFT");
    assertTrue(position.isPresent());
    assertEquals(
        mapper.readTree(new File("src/test/resources/json/position.json")),
        mapper.readTree(JsonParser.toJson(position.get(), true, true)));
  }

  @Test
  public void findMissingIdTest() throws SQLException {
    when(connection.prepareStatement(any(String.class))).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    Optional<Position> position = positionDao.findById("APPL");
    assertFalse(position.isEmpty());
  }

  @Test
  public void findNullIdTest() {
    IllegalArgumentException error =
        assertThrows(
            IllegalArgumentException.class, () -> positionDao.findById(null), "ERROR: Missing ID");
    assertTrue(error.getMessage().contains("ERROR: Missing ID"));
  }

  @Test
  public void findAllPositionsTest() throws SQLException {
    when(connection.prepareStatement(any(String.class))).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    Iterable<Position> positions = positionDao.findAll();
    List<Position> positionList = new ArrayList<>();
    positions.forEach(positionList::add);
    assertEquals(2, positionList.size());
  }

  @Test
  public void findEmptyPositionsTest() throws SQLException {
    when(connection.prepareStatement(any(String.class))).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(false);
    Iterable<Position> positions = positionDao.findAll();
    List<Position> positionList = new ArrayList<>();
    positions.forEach(positionList::add);
    assertEquals(0, positionList.size());
  }

  @Test
  public void deleteByIdTest() throws SQLException {
    when(connection.prepareStatement(any(String.class))).thenReturn(statement);
    positionDao.deleteById("MSFT");
  }

  @Test
  public void deleteMissingIdTest() throws SQLException {
    when(connection.prepareStatement(any(String.class))).thenReturn(statement);
    when(statement.execute()).thenThrow(new SQLException());
    assertThrows(IllegalArgumentException.class, () -> positionDao.deleteById("APPL"), "");
  }

  @Test
  public void deleteAllTest() throws SQLException {
    when(connection.prepareStatement(any(String.class))).thenReturn(statement);
    positionDao.deleteAll();
  }

  @Test
  public void deleteAllErrorTest() throws SQLException {
    when(connection.prepareStatement(any(String.class))).thenReturn(statement);
    when(statement.execute()).thenThrow(new SQLException());
    assertThrows(IllegalArgumentException.class, () -> positionDao.deleteAll(), "");
  }

  @Test
  public void createTest() throws SQLException, IOException {
    Position position = new Position();
    position.setTicker("MSFT");
    position.setNumOfShares(1);
    position.setValuePaid(200.0);
    when(connection.prepareStatement(any(String.class))).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.getString("symbol")).thenReturn("MSFT");
    when(resultSet.getInt("num_of_shares")).thenReturn(1);
    when(resultSet.getDouble("value_paid")).thenReturn(200.0);
    Position result = positionDao.save(position);
    assertEquals(
        mapper.readTree(new File("src/test/resources/json/position.json")),
        mapper.readTree(JsonParser.toJson(result, true, true)));
  }
}
