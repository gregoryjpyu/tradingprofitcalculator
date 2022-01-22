package gui;

import model.Strategy;
import model.Trade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents a AddTrade panel, allows user to add a new Trade to a Strategy
public class AddTrade extends JPanel implements ActionListener {
    private TradeStrategyApp parent;
    private Strategy strategy;
    private JLabel symbol;
    private JTextField userSymbol;
    private JLabel priceBought;
    private JTextField userPriceBought;
    private JLabel priceSold;
    private JTextField userPriceSold;
    private JLabel shares;
    private JTextField userShares;
    private JButton submit;

    //Constructs a AddTrade JPanel, sets up labels, buttons, fields,
    //create and add Trade to Strategy
    public AddTrade(TradeStrategyApp parent, Strategy strategy) {
        this.parent = parent;
        this.strategy = strategy;
        symbol = new JLabel("Please enter the ticker symbol:");
        userSymbol = new JTextField();
        priceBought = new JLabel("Price bought (in cents):");
        userPriceBought = new JTextField();
        priceSold = new JLabel("Price Sold (in cents):");
        userPriceSold = new JTextField();
        shares = new JLabel("Number of shares:");
        userShares = new JTextField();
        submit = new JButton("submit");
        submit.addActionListener(this);
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setLayout(new GridLayout(0, 1));
        this.add(symbol);
        this.add(userSymbol);
        this.add(priceBought);
        this.add(userPriceBought);
        this.add(priceSold);
        this.add(userPriceSold);
        this.add(shares);
        this.add(userShares);
        this.add(submit);
    }

    //modifies: Strategy
    //effects: process JButton, "submit" makes Trade and adds Trade in to Strategy
    // takes user to AddMoreYesOrNo screen after
    // if user inputs any invalid response, re-launch this panel(addTrade)
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            try {
                strategy.addTrade(makeTrade());
                parent.moveToPanel(new AddMoreYesOrNo(parent, strategy));
            } catch (Exception ex) {
                parent.moveToPanel(new AddTrade(parent, strategy));
            }

        }
    }

    //modifies: Strategy
    //effects: takes users input from JTextField and makes a Trade
    //returns the Trade
    public Trade makeTrade() {
        String symbol = userSymbol.getText();
        int priceBought = Integer.parseInt(userPriceBought.getText());
        int priceSold = Integer.parseInt(userPriceSold.getText());
        int shares = Integer.parseInt(userShares.getText());

        return new Trade(symbol, priceBought, priceSold, shares);
    }

}
