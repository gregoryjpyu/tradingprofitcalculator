package gui;

import exceptions.InvalidResponseException;
import model.Strategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents a AddNewStratScreen panel, allows users to enter the name of a new strategy
public class AddNewStratScreen extends JPanel implements ActionListener {
    private TradeStrategyApp parent;
    private JLabel action;
    private JButton submit;
    private JButton returnToMenu;
    private JTextField input;

    //constructs AddNewStratScreen JPanel, sets up buttons, labels, textFields
    public AddNewStratScreen(TradeStrategyApp parent) {
        this.parent = parent;
        action = new JLabel("Please enter the name of your new strategy:");
        input = new JTextField();
        input.addActionListener(this);
        submit = new JButton("submit");
        submit.addActionListener(this);
        returnToMenu = new JButton("return to menu");
        returnToMenu.addActionListener(this);
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setLayout(new GridLayout(0, 1));
        this.add(action);
        this.add(input);
        this.add(submit);
        this.add(returnToMenu);

    }


    //effects: process JButton, "submit" makes a new Strategy
    // takes user to AddMoreYesOrNo with the new Strategy
    // ReturnToMenu takes user back to HomeScreen
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            Strategy newStrat = createNewStrat();
            parent.moveToPanel(new AddMoreYesOrNo(parent, createNewStrat()));
        } else if (e.getSource() == returnToMenu) {
            parent.moveToPanel(new HomeScreen(parent));
        }
    }

    //create a new strategy and save to json
    public Strategy createNewStrat() {
        return new Strategy(input.getText());
    }
}

