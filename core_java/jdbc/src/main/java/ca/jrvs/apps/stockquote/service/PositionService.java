package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.model.Position;

public class PositionService {

  private final PositionDao dao;

  public PositionService(PositionDao dao) {
    this.dao = dao;
  }

  public Position buy(String ticker, int numberOfShares, double price) {
    Position position = new Position();
    position.setTicker(ticker);
    position.setNumOfShares(numberOfShares);
    position.setValuePaid(price);
    return dao.save(position);
  }

  public void sell(String ticker) {
    dao.deleteById(ticker);
  }
}
