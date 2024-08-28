package ca.jrvs.apps.jdbc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationTest {
  static Logger logger = LoggerFactory.getLogger(ApplicationTest.class);

  @BeforeAll
  public static void setupAll() {
    logger.info("Setup Test Layer");
  }

  @BeforeEach
  public void setupUnit() {
    logger.info("Setup Test Case");
  }

  @Test
  public void test1() {
    logger.info("Test 1");
  }

  @Test
  public void test2() {
    logger.info("Test 2");
  }
}
