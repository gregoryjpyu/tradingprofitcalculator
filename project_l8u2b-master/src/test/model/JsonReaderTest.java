package model;
import model.StrategiesList;
import model.Strategy;
import model.Trade;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            StrategiesList s = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyStrategiesList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyStrategiesList.json");
        try {
            StrategiesList s = reader.read();
            assertEquals(0, s.numStrat());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralStrategiesList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStrategiesList.json");
        try {
            StrategiesList sl = reader.read();
            List<Strategy> strategies = sl.getStrategies();
            Strategy good = strategies.get(0);
            Strategy bad = strategies.get(1);

            assertEquals(2, strategies.size());

            checkStrategy("goodStrat", good);
            checkStrategy("badStrat", bad);


            ArrayList<Trade> goodList = good.getTradesList();
            checkTrade("FB", 10030, 20040, 10, goodList.get(0));
            checkTrade("BB", 0, 400, 3000, goodList.get(1));

            ArrayList<Trade> badList = bad.getTradesList();
            checkTrade("GME", 50000, 500, 2000, badList.get(0));
            checkTrade("MEOH", 4532, 4532, 50, badList.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

