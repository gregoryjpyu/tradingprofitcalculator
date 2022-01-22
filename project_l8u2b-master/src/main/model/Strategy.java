package model;


import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import exceptions.NoTradeException;
import exceptions.InfinityPLRatioException;

// Represents a strategy of stock trading, contains a list of trades records
public class Strategy {
    private ArrayList<Trade> tradesList;  //list of Trades happened
    private String strategyName;          //name of strategy
    private double winRate;               //win percentage
    private double profitLossRatio;       //money made/money loss


    // EFFECTS: name on account is set to strategyName;
    // create a new list of tickers.
    public Strategy(String name) {
        this.strategyName = name;
        tradesList = new ArrayList<Trade>();
    }


    // MODIFIES: tradesList
    // EFFECTS: add a ticker into tickerList
    public void addTrade(Trade t) {
        tradesList.add(t);
    }


    // MODIFIES: winRate
    // EFFECTS: calculate winRate
    public void calculateWinRate() throws NoTradeException {
        if (tradesList.size() == 0) {
            throw new NoTradeException("there's no trade for winrate");
        } else {
            float w = 0;
            for (Trade t : tradesList) {
                if (t.isWin()) {
                    w = w + 1;
                }
            }
            double wr = (w / tradesList.size());
            winRate = Math.round(wr * 10000.0) / 100.0;
            //round to 2 decimal places
        }
    }


    // MODIFIES: ProfitLossRatio
    // EFFECTS: calculate ProfitLossRatio
    public void calculateProfitLoss() throws NoTradeException, InfinityPLRatioException {
        if (tradesList.size() == 0) {
            throw new NoTradeException("there's no trade for PL ratio");
        } else if ((tradesList.size() == 1) & (tradesList.get(0).isWin())) {
            throw new InfinityPLRatioException("infinity PL ratio");
        } else {
            float w = 0;
            float l = 0;
            for (Trade t : tradesList) {
                t.profitAmount();
                if (t.isWin()) {
                    w = w + t.getProfit();
                } else {
                    l = l + (0 - t.getProfit());
                }
            }
            double plr = w / l;
            profitLossRatio = Math.round(plr * 100.0) / 100.0;
            //round to 2 decimal places

        }
    }

    //effects: returns winRate
    public double getWinRate() {
        return winRate;
    }

    //effects: returns profitLossRatio
    public double getProfitLossRatio() {
        return profitLossRatio;
    }

    //effects: returns strategyName
    public String getStrategyName() {
        return strategyName;
    }

    //effects: returns tradesList
    public ArrayList<Trade> getTradesList() {
        return tradesList;
    }


    //EFFECTS: turns strategy into a Json object;
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Strategy name", strategyName);
        json.put("tradesList", tradesListToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray tradesListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Trade t : tradesList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;

    }
}


