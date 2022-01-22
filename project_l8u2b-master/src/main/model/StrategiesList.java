package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a collection of trading strategies.
public class StrategiesList implements Writable {

    private ArrayList<Strategy> strategiesList;

    // EFFECTS: constructs strategiesList with an empty list of strategies;
    public StrategiesList() {
        strategiesList = new ArrayList<Strategy>();
    }


    // MODIFIES: this
    // EFFECTS: adds a strategy tp strategiesList.
    public void addStrat(Strategy s) {

        strategiesList.add(s);
    }

    // MODIFIES: this
    // EFFECTS: sets the strategiesList field to the provided list.
    public void addStrategiesList(ArrayList<Strategy> s) {
        strategiesList = s;
    }

    // EFFECTS: returns an unmodifiable list of strategies in strategiesList
    public ArrayList<Strategy> getStrategies() {
        return (strategiesList);
    }

    // EFFECTS: returns number of thingies in the strategiesList
    public int numStrat() {
        return strategiesList.size();
    }

    @Override
    // EFFECTS: create Json object strategies and put strategies in it;
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("strategies", strategiesListToJson());
        return json;
    }

    // EFFECTS: returns things in this StrategiesList as a JSON array
    private JSONArray strategiesListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Strategy s : strategiesList) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;

    }
}
