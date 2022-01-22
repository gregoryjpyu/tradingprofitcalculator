package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Strategy;

//represents a AreYouSureScreen JPanel, asks user to confirm deleting a strategy, also updates Json
public class AreYouSureScreen extends JPanel implements ActionListener {
    private TradeStrategyApp parent;
    private Strategy strategy;
    private JLabel action;
    private JButton yes;
    private JButton no;

    //constructs a AreYouSureScreen JPanel, sets up fields, buttons, labels
    public AreYouSureScreen(TradeStrategyApp parent, Strategy strategy) {
        this.parent = parent;
        this.strategy = strategy;
        action = new JLabel("Are you sure?");
        yes = new JButton("Yes");
        yes.addActionListener(this);
        no = new JButton("No, return to delete menu");
        no.addActionListener(this);
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setLayout(new GridLayout(0, 1));
        this.add(action);
        this.add(yes);
        this.add(no);

    }

    //modifies: parent
    //effects: process JButton, "yes" deletes Strategy from StrategiesList
    // takes user to DeletedScreen with the strategy being deleted
    // "no" takes user back to DeleteScreen
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == yes) {
            delete();
            parent.moveToPanel(new DeletedScreen(parent, strategy));
        } else if (e.getSource() == no) {
            parent.moveToPanel(new DeleteScreen(parent));
        }

    }

    //modifies: parent
    //effects: deletes Strategy from StrategiesList, saves to JSON
    public void delete() {
        parent.getStrategiesList().getStrategies().remove(strategy);
        parent.saveStrategy();
    }
}
