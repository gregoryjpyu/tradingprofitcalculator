package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class TestStrategiesList {
    private Trade trade1;
    private Trade trade2;
    private Strategy goodStrat;
    private Strategy badStrat;

    @BeforeEach
    void runBefore() {
        trade1 = new Trade("FB", 10030, 20040, 10);
        trade2 = new Trade("GME", 50000, 500, 2000);

        goodStrat = new Strategy("goodStrat");
        goodStrat.addTrade(trade1);
        goodStrat.addTrade(trade2);

        badStrat = new Strategy("badStrat");
        badStrat.addTrade(trade1);
    }

    @Test
    void testAddStrategiesList() {
        StrategiesList expected = new StrategiesList();
        expected.addStrat(goodStrat);
        expected.addStrat(badStrat);

        StrategiesList actual = new StrategiesList();
        ArrayList<Strategy> list = new ArrayList<>();
        list.add(goodStrat);
        list.add(badStrat);
        actual.addStrategiesList(list);

        assertEquals(expected.getStrategies(), actual.getStrategies());
    }
}
