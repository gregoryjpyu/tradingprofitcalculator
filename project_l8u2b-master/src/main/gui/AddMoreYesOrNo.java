package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Strategy;

//represents a AddMoreYesOrNo panel, allows user to choose whether or not to add a new trade
// saves the Strategy if "no"
public class AddMoreYesOrNo extends JPanel implements ActionListener {
    private TradeStrategyApp parent;
    private Strategy strategy;
    private JLabel action;
    private JButton yes;
    private JButton no;
    private JButton returnToMenu;

    //constructs a AddMoreYesOrNo JPanel, sets up buttons, labels
    public AddMoreYesOrNo(TradeStrategyApp parent, Strategy strategy) {
        this.parent = parent;
        this.strategy = strategy;
        action = new JLabel("Would you like to add a new trade?");
        yes = new JButton("Yes");
        yes.addActionListener(this);
        no = new JButton("No, proceed to results");
        no.addActionListener(this);
        returnToMenu = new JButton("return to menu (Progress will be lost)");
        returnToMenu.addActionListener(this);
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setLayout(new GridLayout(0, 1));
        this.add(action);
        this.add(yes);
        this.add(no);
        this.add(returnToMenu);

    }

    // modifies: parent
    //effects: process JButton, "yes" takes user to AddMoreYesOrNo screen with the current Strategy
    //"no" saves the Strategy to StrategiesList and takes user to ResultScreen with the current Strategy
    // returnToMenu takes user back to home screen without saving
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == yes) {
            parent.moveToPanel(new AddTrade(parent, strategy));
        } else if (e.getSource() == no) {
            save(strategy);
            try {
                parent.moveToPanel(new ResultScreen(parent, strategy));
            } catch (Exception ex) {
                //
            }
        } else if (e.getSource() == returnToMenu) {
            parent.moveToPanel(new HomeScreen(parent));
        }

    }

    //modifies: parent
    //effects: adds the Strategy to StrategiesList if it is new (isn't in the current StrategiesList)
    // otherwise, saves the changes (if any) of the Strategy to StrategiesList
    public void save(Strategy strategy) {
        if (!parent.getStrategiesList().getStrategies().contains(strategy)) {
            parent.getStrategiesList().addStrat(strategy);
        }
        parent.saveStrategy();

    }
}


