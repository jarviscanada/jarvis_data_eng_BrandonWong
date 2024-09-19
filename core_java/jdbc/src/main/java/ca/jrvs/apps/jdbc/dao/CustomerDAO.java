package ca.jrvs.apps.jdbc.dao;

import ca.jrvs.apps.jdbc.dto.CustomerDTO;
import ca.jrvs.apps.jdbc.util.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends DataAccessObject<CustomerDTO> {

  private static final String GET_ONE =
      "SELECT customer_id, first_name, last_name, "
          + "email, phone, address, city, state, zipcode FROM customer WHERE customer_id=?";

  private static final String GET_ALL =
      "SELECT customer_id, first_name, last_name, "
          + "email, phone, address, city, state, zipcode FROM customer";

  private static final String UPDATE =
      "UPDATE customer SET first_name = ?, last_name=?, "
          + "email = ?, phone = ?, address = ?, city = ?, state = ?, zipcode = ? WHERE customer_id = ?";

  private static final String INSERT =
      "INSERT INTO customer (first_name, last_name, "
          + "email, phone, address, city, state, zipcode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String DELETE = "DELETE FROM customer WHERE customer_id=?";

  public CustomerDAO(Connection connection) {
    super(connection);
  }

  @Override
  public CustomerDTO findById(long id) throws SQLException {
    CustomerDTO customer = new CustomerDTO();
    try (PreparedStatement statement = this.connection.prepareStatement(GET_ONE); ) {
      statement.setLong(1, id);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        createCustomerDTO(rs, customer);
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
      throw sqlException;
    }
    return customer;
  }

  @Override
  public List<CustomerDTO> findAll() throws SQLException {
    List<CustomerDTO> customers = new ArrayList<>();
    try (PreparedStatement statement = this.connection.prepareStatement(GET_ALL); ) {
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        CustomerDTO customer = new CustomerDTO();
        createCustomerDTO(rs, customer);
        customers.add(customer);
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
      throw sqlException;
    }
    return customers;
  }

  @Override
  public CustomerDTO update(CustomerDTO dto) throws SQLException {
    CustomerDTO customer = null;
    try (PreparedStatement statement = this.connection.prepareStatement(UPDATE); ) {
      createCustomerStatement(dto, statement);
      statement.setLong(9, dto.getId());
      statement.execute();
      customer = this.findById(dto.getId());
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
      throw sqlException;
    }
    return customer;
  }

  @Override
  public CustomerDTO create(CustomerDTO dto) throws SQLException {
    try (PreparedStatement statement = this.connection.prepareStatement(UPDATE); ) {
      createCustomerStatement(dto, statement);
      statement.execute();
      int id = this.getLastVal(CUSTOMER_SEQUENCE);
      return this.findById(id);
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
      throw sqlException;
    }
  }

  @Override
  public void delete(long id) throws SQLException {
    try (PreparedStatement statement = this.connection.prepareStatement(DELETE); ) {
      statement.setLong(1, id);
      statement.executeQuery();
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
      throw sqlException;
    }
  }

  private void createCustomerDTO(ResultSet rs, CustomerDTO customer) throws SQLException {
    customer.setId(rs.getLong("customer_id"));
    customer.setFirstName(rs.getString("first_name"));
    customer.setLastName(rs.getString("last_name"));
    customer.setEmail(rs.getString("email"));
    customer.setPhone(rs.getString("phone"));
    customer.setAddress(rs.getString("address"));
    customer.setCity(rs.getString("city"));
    customer.setState(rs.getString("state"));
    customer.setZipCode(rs.getString("zipcode"));
  }

  private void createCustomerStatement(CustomerDTO dto, PreparedStatement statement)
      throws SQLException {
    statement.setString(1, dto.getFirstName());
    statement.setString(2, dto.getLastName());
    statement.setString(3, dto.getEmail());
    statement.setString(4, dto.getPhone());
    statement.setString(5, dto.getAddress());
    statement.setString(6, dto.getCity());
    statement.setString(7, dto.getState());
    statement.setString(8, dto.getZipCode());
  }
}
