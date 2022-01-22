package gui;


import exceptions.InfinityPLRatioException;
import exceptions.NoTradeException;
import model.Strategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import sun.audio.*;

// represents the ResultScreen panel, tells user important statistics and last saved.
public class ResultScreen extends JPanel implements ActionListener {
    private TradeStrategyApp parent;
    private JLabel winlabel;
    private JLabel winRate;
    private JLabel profitLabel;
    private JLabel profitLoss;
    private JLabel saveLabel;
    private JButton returnToMenu;
    private String winResult;
    private String profitResult;
    private String name;

    //Construct the ResultScreen Panel, sets up the labels, buttons, and sound effect.
    public ResultScreen(TradeStrategyApp parent, Strategy strategy) {
        this.parent = parent;
        calculateResults(strategy);
        name = strategy.getStrategyName();
        saveLabel = new JLabel("autosaved to " + parent.getJsonStore() + " at " + parent.getLastSaved());
        winlabel = new JLabel("The win rate of the strategy " + name + " is:");
        winRate = new JLabel(winResult + "%");
        profitLabel = new JLabel("The profit loss ratio is:");
        profitLoss = new JLabel(profitResult);
        try {
            setUpSound();
        } catch (Exception e) {
            //do nothing
        }
        returnToMenu = new JButton("return to menu");
        returnToMenu.addActionListener(this);
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setLayout(new GridLayout(0, 1));
        this.add(saveLabel);
        this.add(winlabel);
        this.add(winRate);
        this.add(profitLabel);
        this.add(profitLoss);
        this.add(returnToMenu);

    }

    //effects: Process JButton, ReturnToMenu takes user back to HomeScreen
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnToMenu) {
            parent.moveToPanel(new HomeScreen(parent));
        }


    }

    //modifies: this
    // gets the result of winRate and ProfitLoss from Strategy; returns them on JPanel;
    // special cases (no trade and infinite ratio) returns "not enough info..."
    public void calculateResults(Strategy s) {
        try {
            s.calculateWinRate();
            winResult = String.valueOf(s.getWinRate());
        } catch (NoTradeException e0) {
            winResult = "There's no trade recorded to determine the winrate";
        }
        try {
            s.calculateProfitLoss();
            profitResult = String.valueOf(s.getProfitLossRatio());
        } catch (NoTradeException e1) {
            profitResult = "There's no trade recorded to determine the profit loss ratio";
        } catch (InfinityPLRatioException e2) {
            profitResult = "There's not enough information to determine the profit loss ratio";
        }
    }

    //modifies: this
    //effects: plays sound effect in this panel
    public void setUpSound() throws IOException {
        //citation: sound url : https://www.youtube.com/watch?v=BK8fdyzgE1E
        String money = "data/money.wav";
        InputStream sound = new FileInputStream(money);
        AudioStream audioStream = new AudioStream(sound);
        AudioPlayer.player.start(audioStream);
    }
}
