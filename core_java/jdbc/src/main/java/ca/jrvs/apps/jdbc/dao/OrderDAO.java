package ca.jrvs.apps.jdbc.dao;

import ca.jrvs.apps.jdbc.dto.OrderDTO;
import ca.jrvs.apps.jdbc.util.DataAccessObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends DataAccessObject<OrderDTO> {
  private static final String GET_ONE =
      "SELECT "
          + "c.first_name, c.last_name, c.email, "
          + "o.order_id, o.create_date, o.total_due, o.status, "
          + "s.first_name, s.last_name, s.email, ol.quantity, "
          + "p.code, p.name, p.size, p.variety, p.price "
          + "FROM orders o "
          + "JOIN customer c ON o.customer_id = c.customer_id "
          + "JOIN salesperson s ON o.salesperson_id = s.salesperson_id "
          + "JOIN order_item ol ON o.order "
          + "JOIN product p ON ol.product_id = p.product_id "
          + "WHERE o.oder_id=?";

  public OrderDAO(Connection connection) {
    super(connection);
  }

  @Override
  public OrderDTO findById(long id) throws SQLException {
    OrderDTO order = new OrderDTO();
    try (PreparedStatement statement = this.connection.prepareStatement(GET_ONE); ) {
      statement.setLong(1, id);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        order.setId(rs.getLong("o.order_id"));
        order.setCreationDate(Date.valueOf(rs.getString("o.creation_date")));
        order.setTotalDue(rs.getInt("o.total_due"));
        order.setStatus(rs.getString("o.status"));
        order.setCustomerFirstName(rs.getString("c.first_name"));
        order.setCustomerLastName(rs.getString("c.last_name"));
        order.setCustomerEmail(rs.getString("c.email"));
        order.setSalespersonFirstName(rs.getString("s.first_name"));
        order.setSalespersonLastName(rs.getString("s.last_name"));
        order.setSalespersonEmail(rs.getString("s.email"));
        order.setProductCode(rs.getString("p.code"));
        order.setProductName(rs.getString("p.name"));
        order.setProductSize(rs.getInt("p.size"));
        order.setProductVariety(rs.getString("p.variety"));
        order.setProductPrice(rs.getInt("p.price"));
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
      throw sqlException;
    }
    return order;
  }

  @Override
  public List<OrderDTO> findAll() throws SQLException {
    return new ArrayList<>();
  }

  @Override
  public OrderDTO update(OrderDTO dto) throws SQLException {
    return null;
  }

  @Override
  public OrderDTO create(OrderDTO dto) throws SQLException {
    return null;
  }

  @Override
  public void delete(long id) throws SQLException {}
}
