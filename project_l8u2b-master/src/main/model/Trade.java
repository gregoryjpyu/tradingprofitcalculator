package model;

import org.json.JSONObject;

// Represents a trade, record of a stock bought and sold
public class Trade {
    private String ticker;    //symbol
    private int priceBought;  //price in cents
    private int priceSold;    //price in cents
    private boolean win;      //win or loss
    private float profit;     // how much you made
    private int shares;       // # you bought

    /*
     * REQUIRES: symbol has a non-zero length;
     * priceBought and priceSold must be >= 0;
     * also have 2 decimal points max;
     * shares must be > 0
     * EFFECTS: set ticker's name to symbol;
     *              priceBought to priceBought;
     *              priceSold to priceSold;
     *              shares to shares
     */
    public Trade(String symbol, int priceBought, int priceSold, int shares) {
        this.ticker = symbol;
        this.priceBought = priceBought;
        this.priceSold = priceSold;
        this.shares = shares;
    }

    /*
     * MODIFIES: win
     * EFFECTS: determine if the trade is won;
     * break even is considered as a loss;
     */
    public boolean isWin() {
        win = (priceSold > priceBought);
        return win;
    }

    /*
     * REQUIRES: price in cents (type int)
     * MODIFIES: profit
     * EFFECTS: calculate profit in dollars
     */
    public void profitAmount() {
        this.profit = ((priceSold - priceBought) * shares) / 100;
    }

    public float getProfit() {
        return profit;
    }

    //  EFFECTS: turns trade into a Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ticker", ticker);
        json.put("priceBought", priceBought);
        json.put("priceSold", priceSold);
        json.put("shares", shares);
        return json;
    }

    //effects: returns ticker
    public String getTicker() {
        return ticker;
    }

    //effects: returns priceBought
    public int getPriceBought() {
        return priceBought;
    }

    //effects: returns priceSold
    public int getPriceSold() {
        return priceSold;
    }

    //effects: returns Shares
    public int getShares() {
        return shares;
    }
}
