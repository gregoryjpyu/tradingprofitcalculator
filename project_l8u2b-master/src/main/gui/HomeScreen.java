package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents a HomeScreen Panel, first panel that the users see, allows users to navigate
public class HomeScreen extends JPanel implements ActionListener {
    private TradeStrategyApp parent;
    private JLabel load;
    private JLabel welcome;
    private JLabel action;
    private JButton addNewButton;
    private JButton loadButton;
    private JButton deleteButton;

    //constructs a HomeScreen, sets up labels and buttons, tells user file location
    public HomeScreen(TradeStrategyApp parent) {
        this.parent = parent;
        addNewButton = new JButton("add a new strategy");
        addNewButton.addActionListener(this);
        loadButton = new JButton("load a previous strategy");
        loadButton.addActionListener(this);
        deleteButton = new JButton("delete a previous strategy");
        deleteButton.addActionListener(this);
        loadStatus();
        welcome = new JLabel("Welcome to Trade Strategy App!");
        action = new JLabel("Would you like to:");
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.setLayout(new GridLayout(0, 1));
        this.add(load);
        this.add(welcome);
        this.add(action);
        this.add(addNewButton);
        this.add(loadButton);
        this.add(deleteButton);
    }

    // effects: process JButton, addNewButton takes user to AddNewStratScreen
    // loadButton takes user to LoadScreen
    // deleteButton takes user to load SCreen
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addNewButton) {
            parent.moveToPanel(new AddNewStratScreen(parent));
        } else if (e.getSource() == loadButton) {
            parent.moveToPanel(new LoadScreen(parent));
        } else if (e.getSource() == deleteButton) {
            parent.moveToPanel(new DeleteScreen(parent));
        }
    }

    //modifies: this
    //effects: create JPanel that shows the user the file location at which the strategies are saved from
    // show "no past strategy" if there isn't any Strategy in StrategiesList
    public void loadStatus() {
        if (parent.isFileEmpty()) {
            load = new JLabel("*loaded from: there is not any past strategy*");
        } else {
            String str = "*loaded from: " + parent.getJsonStore() + "*";
            load = new JLabel(str);
        }

    }
}

