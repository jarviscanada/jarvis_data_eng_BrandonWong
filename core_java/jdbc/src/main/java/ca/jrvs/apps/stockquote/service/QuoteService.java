package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.QuoteHttpHelper;
import java.util.Optional;
import okhttp3.OkHttpClient;

public class QuoteService {
  private QuoteDao dao;
  private QuoteHttpHelper httpHelper;

  public QuoteService(QuoteDao dao, String apiKey, OkHttpClient httpClient) {
    this.dao = dao;
    httpHelper = new QuoteHttpHelper(apiKey, httpClient);
  }

  public Optional<Quote> fetchQuoteDataFromAPI(String ticker) {
    Quote quote = httpHelper.fetchQuoteInfo(ticker);
    return Optional.of(dao.save(quote));
  }
}
