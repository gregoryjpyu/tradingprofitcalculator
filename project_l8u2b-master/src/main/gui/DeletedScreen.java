package gui;

import model.Strategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//represents a DeletedScreen panel, tells user their strategy is deleted
public class DeletedScreen extends JPanel implements ActionListener {
    private TradeStrategyApp parent;
    private JLabel saveLabel;
    private JLabel deletedLabel;
    private JButton returnToMenu;
    private Strategy strategy;

    public DeletedScreen(TradeStrategyApp parent, Strategy strategy) {
        this.parent = parent;
        this.strategy = strategy;
        saveLabel = new JLabel("autosaved to " + parent.getJsonStore() + " at " + parent.getLastSaved());
        deletedLabel = new JLabel("The strategy " + strategy.getStrategyName() + " has been deleted");
        returnToMenu = new JButton("return to menu");
        returnToMenu.addActionListener(this);
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setLayout(new GridLayout(0, 1));
        this.add(saveLabel);
        this.add(deletedLabel);
        this.add(returnToMenu);
    }

    //effects: process JButton, ReturnToMenu takes user back to HomeScreen
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnToMenu) {
            parent.moveToPanel(new HomeScreen(parent));
        }


    }
}
