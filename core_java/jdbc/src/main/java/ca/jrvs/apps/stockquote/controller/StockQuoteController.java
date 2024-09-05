package ca.jrvs.apps.stockquote.controller;

import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.service.*;
import java.util.Optional;

public class StockQuoteController {
  private QuoteService quoteService;
  private PositionService positionService;

  public StockQuoteController(QuoteService quoteService, PositionService positionService) {
    this.quoteService = quoteService;
    this.positionService = positionService;
  }

  public void initClient() {
    Optional<Quote> optionalQuote = quoteService.fetchQuoteDataFromAPI("MSFT");
    if (optionalQuote.isPresent()) {
      Quote quote = optionalQuote.get();
      positionService.buy("MSFT", 1, quote.getPrice());
    } else {
      System.out.println("Oops");
    }
  }
}
