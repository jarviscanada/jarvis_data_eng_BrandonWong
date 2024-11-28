package ca.jrvs.apps.stockquote.util;

import java.sql.Connection;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CrudDAO<T, ID> {

  public static Logger logger = LoggerFactory.getLogger(CrudDAO.class);
  protected final Connection connection;

  public CrudDAO(Connection connection) {
    super();
    this.connection = connection;
  }

  /**
   * Saves a given entity. Used for create and update
   *
   * @param entity - must not be null
   * @return The saved entity. Will never be null
   * @throws IllegalArgumentException - if id is null
   */
  public abstract T save(T entity) throws IllegalArgumentException;

  /**
   * Retrieves an entity by its id
   *
   * @param id - must not be null
   * @return Entity with the given id or empty optional if none found
   * @throws IllegalArgumentException - if id is null
   */
  public abstract Optional<T> findById(ID id) throws IllegalArgumentException;

  /**
   * Retrieves all entities
   *
   * @return All entities
   */
  public abstract Iterable<T> findAll();

  /**
   * Deletes the entity with the given id. If the entity is not found, it is silently ignored
   *
   * @param id - must not be null
   * @throws IllegalArgumentException - if id is null
   */
  public abstract void deleteById(ID id) throws IllegalArgumentException;

  /** Deletes all entities managed by the repository */
  public abstract void deleteAll();
}
