package ca.jrvs.apps.stockquote.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {
  private String ticker; // ID
  private int numOfShares;
  private double valuePaid;

  @JsonProperty("symbol")
  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  @JsonProperty("num_of_shares")
  public int getNumOfShares() {
    return numOfShares;
  }

  public void setNumOfShares(int numOfShares) {
    this.numOfShares = numOfShares;
  }

  @JsonProperty("value_paid")
  public double getValuePaid() {
    return valuePaid;
  }

  public void setValuePaid(double valuePaid) {
    this.valuePaid = valuePaid;
  }
}
