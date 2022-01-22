package gui;

import exceptions.InvalidResponseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Strategy;

import java.util.ArrayList;

//represents the LoadScreen Panel, allows user to select from existing strategies
public class LoadScreen extends JPanel implements ActionListener {
    private TradeStrategyApp parent;
    private JLabel action;
    private JButton select;
    private JButton returnToMenu;
    private JTextField input;

    // constructs a LoadScreen, sets up field
    public LoadScreen(TradeStrategyApp parent) {
        this.parent = parent;
        action = new JLabel("Please enter the name of the strategy that you would like to load");
        input = new JTextField(200);
        select = new JButton("submit");
        select.addActionListener(this);
        returnToMenu = new JButton("return to menu");
        returnToMenu.addActionListener(this);
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setLayout(new GridLayout(0, 1));
        this.add(action);
        makeStrategiesLabels();
        this.add(input);
        this.add(select);
        this.add(returnToMenu);

    }

    //effects: process JButton, "select" matches selected strategy
    // takes user to AddMoreYesOrNo screen with the selected Strategy
    //returnToMenu takes user back to HomeScreen
    // if user enters invalid responses, re-launch this panel (LoadScreen) again.
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == select) {
            try {
                Strategy s = matchInput();
                parent.moveToPanel(new AddMoreYesOrNo(parent, s));
            } catch (InvalidResponseException ex) {
                parent.moveToPanel(new LoadScreen(parent));
            }

        } else if (e.getSource() == returnToMenu) {
            parent.moveToPanel(new HomeScreen(parent));
        }

    }


    //effects: match the text field with the trades in StrategiesList in parent
    // returns the matched Strategy
    public Strategy matchInput() throws InvalidResponseException {
        ArrayList<Strategy> list = parent.getStrategiesList().getStrategies();
        for (Strategy s : list) {
            if (input.getText().equals(s.getStrategyName())) {
                return s;
            }
        }
        throw new InvalidResponseException("invalid response");
    }

    //modifies: this
    //effects: create JLabel for each Strategy in StrategiesList from parent
    public void makeStrategiesLabels() {
        ArrayList<Strategy> list = parent.getStrategiesList().getStrategies();
        if (list.isEmpty()) {
            this.add(new JLabel("there's no past strategy to select from"));
        } else {
            int count = 1;
            for (Strategy s : list) {
                String str = String.valueOf(count) + ". " + s.getStrategyName();
                this.add(new JLabel(str));
                count = count + 1;
            }
        }

    }
}

