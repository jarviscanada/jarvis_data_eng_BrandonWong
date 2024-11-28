package ca.jrvs.apps.stockquote.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.model.Position;
import ca.jrvs.apps.stockquote.util.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PositionServiceUnitTest {

  @Mock PositionDao positionDao;

  PositionService positionService;
  ObjectMapper mapper;

  @BeforeEach
  public void setup() {
    mapper = new ObjectMapper();
    positionService = new PositionService(positionDao);
  }

  @Test
  public void buyExistingTickerTest() throws JsonProcessingException {
    Position input = new Position();
    input.setTicker("MSFT");
    input.setNumOfShares(1);
    input.setValuePaid(200.0);

    when(positionDao.save(any(Position.class))).thenReturn(input);
    Position output = positionService.buy("MSFT", 1, 200.0);
    assertEquals(
        mapper.readTree(JsonParser.toJson(input, true, true)),
        mapper.readTree(JsonParser.toJson(output, true, true)));
  }

  @Test
  public void buyNonExistingTickerTest() {
    when(positionDao.save(any(Position.class))).thenReturn(null);
    Position output = positionService.buy("APPL", 1, 200.0);
    assertNull(output);
  }

  @Test
  public void sellExistingTickerTest() {
    doNothing().when(positionDao).deleteById("MSFT");
    positionService.sell("MSFT");
  }

  @Test
  public void sellNonExistingTickerTest() {
    doThrow(new IllegalArgumentException()).when(positionDao).deleteById("APPL");
    assertThrows(IllegalArgumentException.class, () -> positionService.sell("APPL"), "");
  }
}
