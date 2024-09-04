package ca.jrvs.apps.stockquote.util;

import ca.jrvs.apps.stockquote.model.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;

public class QuoteHttpHelper {
  private String apiKey;
  private OkHttpClient client;

  public QuoteHttpHelper(String apiKey) {
    this.apiKey = apiKey;
    client = new OkHttpClient();
  }

  public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {
    Request request = new Request.Builder()
            .url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&datatype=json")
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
