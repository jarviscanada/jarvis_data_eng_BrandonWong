package ca.jrvs.apps.stockquote.util;

import ca.jrvs.apps.stockquote.model.Quote;
import java.io.IOException;
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
      return JsonParser.toObjectFromJson(response.body().string(), Quote.class);
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
