package ca.jrvs.apps.stockquote.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.sql.Timestamp;

public class Quote {
  private String ticker; // ID
  private double open;
  private double high;
  private double low;
  private double price;
  private int volume;
  private Date latestTradingDay;
  private double previousClose;
  private double change;
  private String changePercent;
  private Timestamp timestamp;

  @JsonProperty("timestamp")
  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  @JsonProperty("change_percent")
  public String getChangePercent() {
    return changePercent;
  }

  public void setChangePercent(String changePercent) {
    this.changePercent = changePercent;
  }

  @JsonProperty("change")
  public double getChange() {
    return change;
  }

  public void setChange(double change) {
    this.change = change;
  }

  @JsonProperty("previous_close")
  public double getPreviousClose() {
    return previousClose;
  }

  public void setPreviousClose(double previousClose) {
    this.previousClose = previousClose;
  }

  @JsonProperty("latest_trading_day")
  public Date getLatestTradingDay() {
    return latestTradingDay;
  }

  public void setLatestTradingDay(Date latestTradingDay) {
    this.latestTradingDay = latestTradingDay;
  }

  @JsonProperty("volume")
  public int getVolume() {
    return volume;
  }

  public void setVolume(int volume) {
    this.volume = volume;
  }

  @JsonProperty("price")
  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @JsonProperty("low")
  public double getLow() {
    return low;
  }

  public void setLow(double low) {
    this.low = low;
  }

  @JsonProperty("high")
  public double getHigh() {
    return high;
  }

  public void setHigh(double high) {
    this.high = high;
  }

  @JsonProperty("open")
  public double getOpen() {
    return open;
  }

  public void setOpen(double open) {
    this.open = open;
  }

  @JsonProperty("symbol")
  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }
}
