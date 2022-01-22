package model;

import model.StrategiesList;
import model.Strategy;
import model.Trade;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkStrategy(String name, Strategy s) {
        assertEquals(name, s.getStrategyName());
    }

    protected void checkTrade(String ticker, int priceBought, int priceSold, int shares, Trade t) {
        assertEquals(ticker, t.getTicker());
        assertEquals(priceBought, t.getPriceBought());
        assertEquals(priceSold, t.getPriceSold());
        assertEquals(shares, t.getShares());


    }
}
