package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

  static Logger logger = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    DatabaseConnectionManager dcm =
        new DatabaseConnectionManager("localhost", "enterprise", "postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM CUSTOMER");
      while (resultSet.next()) {
        logger.info(String.valueOf(resultSet.getInt(1)));
      }
    } catch (SQLException e) {
      logger.error(e.getMessage());
    }
    logger.info("Hello, World!");
  }
}
