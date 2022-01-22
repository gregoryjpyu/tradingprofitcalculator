package gui;

import javax.swing.*;
import java.awt.*;

import model.StrategiesList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;


// represents the TradeStrategyApp class
// inspired from: https://introcs.cs.princeton.edu/java/15inout/GUI.java.html
public class TradeStrategyApp extends JFrame {
    private static final String JSON_STORE = "./data/strategieslist.json";
    private StrategiesList strategiesList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JFrame frame;
    private JPanel panel;
    private Boolean fileEmpty;
    private long millis;
    private java.util.Date date;
    private String lastSaved;

    //EFFECTS: run the app
    public static void main(String[] args) {
        //start a new home screen
        new TradeStrategyApp();
    }

    // EFFECTS: constructor, sets up JSON, JPanel, JFrame;
    public TradeStrategyApp() {
        strategiesList = new StrategiesList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        fileEmpty = false;
        lastSaved = "";
        try {
            strategiesList = jsonReader.read();
        } catch (Exception e) {
            fileEmpty = true;
        }

        //sets up the frame and display it
        frame = new JFrame();
        panel = new HomeScreen(this);
        frame.getContentPane().add(panel);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("TradeStrategyApp");
        frame.pack();
        frame.setVisible(true);
        frame.setState(Frame.NORMAL);
    }

    //modifies: this
    //change the panel to the input, allows for the app to "switch page"
    public void moveToPanel(JPanel screen) {
        frame.getContentPane().removeAll();
        frame.add(screen);
        frame.invalidate();
        frame.validate();

    }

    //effects: return stored location
    public String getJsonStore() {
        return JSON_STORE;
    }

    //effects: return the strategiesList so operations can be performed at other panels and etc.;
    public StrategiesList getStrategiesList() {
        return strategiesList;
    }

    //effects: returns if strategiesList is empty;
    public Boolean isFileEmpty() {
        return fileEmpty;
    }

    // return the last time the app was saved
    public String getLastSaved() {
        return lastSaved;
    }

    //modifies: this
    //effects: save file to json, update lastSaved
    public void saveStrategy() {
        try {
            jsonWriter.open();
            jsonWriter.write(strategiesList);
            jsonWriter.close();
            millis = System.currentTimeMillis();
            date = new java.util.Date();
            lastSaved = date.toString();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}
