package ca.jrvs.apps.jdbc.util;

import java.sql.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DataAccessObject<T extends DataTransferObject> {

  public static Logger logger = LoggerFactory.getLogger(DataAccessObject.class);

  protected final Connection connection;
  protected static final String LAST_VAL = "SELECT last_value FROM ";
  protected static final String CUSTOMER_SEQUENCE = "hp_customer_seq";

  public DataAccessObject(Connection connection) {
    super();
    this.connection = connection;
  }

  public abstract T findById(long id) throws SQLException;

  public abstract List<T> findAll() throws SQLException;

  public abstract T update(T dto) throws SQLException;

  public abstract T create(T dto) throws SQLException;

  public abstract void delete(long id) throws SQLException;

  protected int getLastVal(String sequence) throws SQLException {
    int key = 0;
    String sql = LAST_VAL + sequence;
    try (Statement statement = connection.createStatement()) {
      ResultSet rs = statement.executeQuery(sql);
      while (rs.next()) {
        key = rs.getInt(1);
      }
      return key;
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
      throw sqlException;
    }
  }
}
