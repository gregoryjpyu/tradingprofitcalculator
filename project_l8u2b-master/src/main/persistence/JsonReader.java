package persistence;

import exceptions.InvalidResponseException;
import model.StrategiesList;
import model.Strategy;
import model.Trade;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// represents a JsonReader that reads saved StrategiesList from file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads strategiesList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public StrategiesList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStrategiesList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses strategiesList from JSON object and returns it
    // makes new strategiesList
    private StrategiesList parseStrategiesList(JSONObject jsonObject) {
        StrategiesList sl = new StrategiesList();
        addStrategies(sl, jsonObject);
        return sl;
    }

    // MODIFIES: sl
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    // adds strategies to strategieslist
    private void addStrategies(StrategiesList sl, JSONObject jsonObject) {
        JSONArray strategies = jsonObject.getJSONArray("strategies");
        for (Object json : strategies) {
            JSONObject strat = (JSONObject) json;
            addStrategy(sl, strat);
        }
    }

    // MODIFIES: sl
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    // add strategy to strategies
    private void addStrategy(StrategiesList sl, JSONObject jsonObject) {
        String name = jsonObject.getString("Strategy name");
        JSONArray tradesList = jsonObject.getJSONArray("tradesList");
        Strategy strategy = new Strategy(name);
        for (Object json : tradesList) {
            JSONObject trade = (JSONObject) json;
            addTrade(sl, trade, strategy);
        }
        sl.addStrat(strategy);
    }

    // MODIFIES: sl
    // EFFECTS: parses thingy from JSON object and adds it to workroom\
    // add trade to strategy
    private void addTrade(StrategiesList sl, JSONObject jsonObject, Strategy s) {
        String name = jsonObject.getString("ticker");
        int priceBought = jsonObject.getInt("priceBought");
        int priceSold = jsonObject.getInt("priceSold");
        int shares = jsonObject.getInt("shares");
        Trade trade = new Trade(name, priceBought, priceSold, shares);
        s.addTrade(trade);
    }

}
