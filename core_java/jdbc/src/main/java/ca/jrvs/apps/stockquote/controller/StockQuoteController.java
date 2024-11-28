package ca.jrvs.apps.stockquote.controller;

import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.service.*;
import java.util.Optional;

public class StockQuoteController {
  private final QuoteService quoteService;
  private final PositionService positionService;

  public StockQuoteController(QuoteService quoteService, PositionService positionService) {
    this.quoteService = quoteService;
    this.positionService = positionService;
  }

  public Optional<Quote> initClient(String symbol) {
    return quoteService.fetchQuoteDataFromAPI(symbol);
  }

  public void buy(String symbol, int numOfShares, double price) {
    positionService.buy(symbol, numOfShares, price);
  }

  public void sell(String symbol) {
    positionService.sell(symbol);
  }
}
