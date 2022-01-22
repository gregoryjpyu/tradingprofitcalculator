package model;

import exceptions.InfinityPLRatioException;
import exceptions.NoTradeException;
import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Float.POSITIVE_INFINITY;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class StrategyTest {
    private Trade trade1;
    private Trade trade2;
    private Trade trade3;
    private Trade trade4;
    private Trade trade5;
    private Trade trade6;
    private Strategy goodStrat;
    private Strategy badStrat;

    @BeforeEach
    void runBefore() {
        trade1 = new Trade("FB", 10030, 20040, 10);
        trade2 = new Trade("GME", 50000, 500, 2000);
        trade3 = new Trade("MEOH", 4532, 4532, 50);
        trade4 = new Trade("BB", 0, 400, 3000);
        trade5 = new Trade("KEKW", 10000000, 0, 1);
        trade6 = new Trade("OCUL", 325, 85000, 10000);

        goodStrat = new Strategy("goodStrat");
        badStrat = new Strategy("badStrat");
    }

    @Test
    void TestAddAndGetTrade() {
        List<Trade> actual = new ArrayList<Trade>();
        actual.add(trade1);
        actual.add(trade4);

        goodStrat.addTrade(trade1);
        goodStrat.addTrade(trade4);

        assertEquals(actual, goodStrat.getTradesList());

        List<Trade> actual2 = new ArrayList<Trade>();
        actual2.add(trade1);
        actual2.add(trade2);
        actual2.add(trade3);
        actual2.add(trade4);

        badStrat.addTrade(trade1);
        badStrat.addTrade(trade2);
        badStrat.addTrade(trade3);
        badStrat.addTrade(trade4);

        assertEquals(actual2, badStrat.getTradesList());

    }

    @Test
    void TestGetPositiveWinRate() {
        try {
            goodStrat.addTrade(trade1);
            goodStrat.addTrade(trade2);
            goodStrat.addTrade(trade4);
            goodStrat.calculateWinRate();
            assertEquals(66.67, goodStrat.getWinRate());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void TestNegativeWinrate() {
        try {
            badStrat.addTrade(trade1);
            badStrat.addTrade(trade2);
            badStrat.addTrade(trade3);
            badStrat.addTrade(trade4);
            badStrat.calculateWinRate();
            assertEquals(50, badStrat.getWinRate());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void TestNoTradeWinRate() {
        try {
            goodStrat.calculateWinRate();
        } catch (NoTradeException ex) {
            //pass
        }
    }

    @Test
    void TestProfitLossRatioAbove1() {
        try {
            goodStrat.addTrade(trade1);
            goodStrat.addTrade(trade2);
            goodStrat.addTrade(trade3);
            goodStrat.addTrade(trade6);
            goodStrat.calculateProfitLoss();
            assertEquals(8.55, goodStrat.getProfitLossRatio());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void TestProfitLossRatioBelow1() {
        try {
            badStrat.addTrade(trade1);
            badStrat.addTrade(trade2);
            badStrat.addTrade(trade4);
            badStrat.calculateProfitLoss();
            assertEquals(0.01, badStrat.getProfitLossRatio());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void TestZeroProfitLoss() {
        try {
            badStrat.addTrade(trade2);
            badStrat.calculateProfitLoss();
            assertEquals(0, badStrat.getProfitLossRatio());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void TestNoTradeProfitLoss() {
        try {
            goodStrat.calculateProfitLoss();
        } catch (NoTradeException e1) {
            //pass
        } catch (InfinityPLRatioException e2) {
            fail();
        }
    }

    @Test
    void TestInfinityProfitLoss() {
        try {
            goodStrat.addTrade(trade1);
            goodStrat.calculateProfitLoss();
        } catch (NoTradeException e1) {
            fail();
        } catch (InfinityPLRatioException e2) {
            //pass
        }
    }

    @Test
    void TestGetStrategyName() {
        assertEquals("badStrat", badStrat.getStrategyName());
    }

    @Test
    void TestGetTradeList() {
        goodStrat.addTrade(trade1);
        goodStrat.addTrade(trade4);
        ArrayList<Trade> tradeList = new ArrayList<Trade>();
        tradeList.add(trade1);
        tradeList.add(trade4);
        assertEquals(tradeList, goodStrat.getTradesList());
    }

    @Test
    void TestToJson() {
        goodStrat.addTrade(trade1);
        JSONObject trade = new JSONObject();
        trade.put("ticker", "FB");
        trade.put("priceBought", 10030);
        trade.put("priceSold", 20040);
        trade.put("shares", 10);

        JSONObject strat = new JSONObject();
        JSONArray jlist = new JSONArray();
        jlist.put(trade);

        strat.put("Strategy name", "goodStrat");
        strat.put("tradesList", jlist);

        assertEquals(strat.getString("Strategy name"), goodStrat.toJson().getString("Strategy name"));

    }


}
