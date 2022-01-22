package model;

import model.StrategiesList;
import model.Strategy;
import model.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
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
        goodStrat.addTrade(trade1);
        goodStrat.addTrade(trade4);

        badStrat = new Strategy("badStrat");
        badStrat.addTrade(trade2);
        badStrat.addTrade(trade3);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            StrategiesList s = new StrategiesList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyStratList() {
        try {
            StrategiesList s = new StrategiesList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyStrategiesList.json");
            writer.open();
            writer.write(s);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyStrategiesList.json");
            s = reader.read();
            assertEquals(0, s.numStrat());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralStrategyListGood() {
        try {
            StrategiesList s = new StrategiesList();
            s.addStrat(goodStrat);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStrategiesList.json");
            writer.open();
            writer.write(s);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralStrategiesList.json");
            s = reader.read();
            List<Strategy> strategies = s.getStrategies();
            Strategy good = strategies.get(0);
            assertEquals(2, strategies.size());
            checkStrategy("goodStrat", good);

            List<Trade> goodList = good.getTradesList();
            checkTrade("FB", 10030, 20040, 10, goodList.get(0));
            checkTrade("BB", 0, 400, 3000, goodList.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralStrategyListBad() {
        try {
            StrategiesList s = new StrategiesList();
            s.addStrat(badStrat);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStrategiesList.json");
            writer.open();
            writer.write(s);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralStrategiesList.json");
            s = reader.read();
            List<Strategy> strategies = s.getStrategies();
            Strategy bad = strategies.get(1);
            assertEquals(2, strategies.size());
            checkStrategy("badStrat", bad);

            ArrayList<Trade> badList = bad.getTradesList();
            checkTrade("GME", 50000, 500, 2000, badList.get(0));
            checkTrade("MEOH", 4532, 4532, 50, badList.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
