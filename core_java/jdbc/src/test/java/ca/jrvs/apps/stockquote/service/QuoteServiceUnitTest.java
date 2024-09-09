package ca.jrvs.apps.stockquote.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.practice.JsonParser;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.QuoteHttpHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class QuoteServiceUnitTest {

  @Mock QuoteHttpHelper quoteHttpHelper;

  @Mock QuoteDao quoteDao;

  QuoteService quoteService;
  ObjectMapper mapper;

  @BeforeEach
  public void setup() {
    mapper = new ObjectMapper();
    quoteService = new QuoteService(quoteDao, quoteHttpHelper);
  }

  @Test
  public void fetchQuoteDataFromAPITest() throws IOException {
    Quote quote = new Quote();
    quote.setTicker("MSFT");
    quote.setOpen(151.65);
    quote.setHigh(153.42);
    quote.setLow(0.0);
    quote.setPrice(123.0);
    quote.setVolume(20);
    quote.setLatestTradingDay(new Date(1725595200000L));
    quote.setPreviousClose(6.0);
    quote.setChange(2.0);
    quote.setChangePercent("0.2374%");
    quote.setTimestamp(new Timestamp(1725508800000L));

    when(quoteHttpHelper.fetchQuoteInfo("MSFT")).thenReturn(quote);
    when(quoteDao.save(any(Quote.class))).thenReturn(quote);
    Optional<Quote> result = quoteService.fetchQuoteDataFromAPI("MSFT");
    assertTrue(result.isPresent());
    assertEquals(
        mapper.readTree(new File("src/test/resources/json/quote.json")),
        mapper.readTree(JsonParser.toJson(result.get(), true, true)));
  }

  @Test
  public void fetchEmptyQuote() {
    Quote quote = new Quote();
    quote.setTicker("MSFT");
    quote.setOpen(151.65);
    quote.setHigh(153.42);
    quote.setLow(0.0);
    quote.setPrice(123.0);
    quote.setVolume(20);
    quote.setLatestTradingDay(new Date(1725595200000L));
    quote.setPreviousClose(6.0);
    quote.setChange(2.0);
    quote.setChangePercent("0.2374%");
    quote.setTimestamp(new Timestamp(1725508800000L));

    when(quoteHttpHelper.fetchQuoteInfo("MSFT")).thenReturn(quote);
    when(quoteDao.save(any(Quote.class))).thenReturn(null);
    assertThrows(NullPointerException.class, () -> quoteService.fetchQuoteDataFromAPI("MSFT"), "");
  }
}
