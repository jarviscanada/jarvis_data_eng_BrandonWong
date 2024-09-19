package ca.jrvs.apps.stockquote.util;

import ca.jrvs.apps.stockquote.model.Quote;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import okhttp3.*;

public class QuoteHttpHelper {
  private final String apiKey;
  private final OkHttpClient client;

  public QuoteHttpHelper(String apiKey, OkHttpClient client) {
    this.apiKey = apiKey;
    this.client = client;
  }

  public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {
    Request request =
        new Request.Builder()
            .url(
                "https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol="
                    + symbol
                    + "&datatype=json")
            .header("X-RapidAPI-KEY", apiKey)
            .header("X-RapidAPI-Host", "real-time-stock-finance-quote.p.rapidapi.com")
            .method("GET", RequestBody.create(new byte[0], null))
            .build();
    try (Response response = client.newCall(request).execute()) {
      Quote quote = JsonParser.toObjectFromJson(response.body().string(), Quote.class);
      if (quote != null) {
        quote.setTimestamp(Timestamp.from(Instant.now()));
        return quote;
      }
      return null;
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
