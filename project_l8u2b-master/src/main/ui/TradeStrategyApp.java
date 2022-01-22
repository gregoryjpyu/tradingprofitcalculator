package ui;

import exceptions.InvalidResponseException;
import model.StrategiesList;
import model.Strategy;
import model.Trade;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

// Represents the TradeStrategy application;
/*public class TradeStrategyApp {
    private static final String JSON_STORE = "./data/strategieslist.json";
    private Scanner input;
    private StrategiesList strategiesList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the TradeStrategy application and checks for exception
    public TradeStrategyApp() throws FileNotFoundException, InvalidResponseException {
        strategiesList = new StrategiesList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        try {
            strategiesList = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (Exception e) {
            System.out.println("there is not any past strategy");
        }

        runTradeStrategy();
    }

    // MODIFIES: this
    // EFFECTS: process user input, quit or not
    private void runTradeStrategy() throws InvalidResponseException {
        boolean keepGoing = true;
        input = new Scanner(System.in);

        while (keepGoing) {
            appMenu();
            String command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else if (command.equals("a")) {
                addNewStrategy();
            } else if (command.equals("l")) {
                loadOption();
            } else if (command.equals("d")) {
                deleteOption();

            } else {
                System.out.println("Selection is not valid. Please try again.");
            }
        }
        System.out.println("\nThank you for using TradeStrategy");

    }

    // EFFECTS: checks if there's strategiesList is empty so a strategy could be loaded
    private void loadOption() {
        if (strategiesList.getStrategies().size() != 0) {
            modifyStrategy(loadMenu());
        } else {
            System.out.println("There is not any strategy to load. Please try again.");
        }
    }

    // EFFECTS: checks if there's strategiesList is empty so a strategy could be deleted
    private void deleteOption() {
        if (strategiesList.getStrategies().size() != 0) {
            deleteStrategy(deleteMenu());
        } else {
            System.out.println("There is not any strategy to delete. Please try again.");
        }

    }

    // EFFECTS: displays menu of options (add,load,delete a strategy or quit)
    private void appMenu() {
        System.out.println("\nWould you like to:");
        System.out.println("\ta -> Add a new strategy");
        System.out.println("\tl -> Load a previous strategy");
        System.out.println("\td -> Delete a previous strategy");
        System.out.println("\tq -> Quit application");

    }

    // MODIFIES: this
    // EFFECTS: process user input, name of strategy
    private void addNewStrategy() throws InvalidResponseException {
        System.out.println("Please enter the strategy name:");

        String name = input.next();
        Strategy strategy = new Strategy(name);
        addTradesToNewStrategy(strategy);
    }

    // REQUIRES: there exists a strategy in strategiesList
    // MODIFIES: this
    // EFFECTS: process user input, selection of the saved strategy;
    //          return the selected strategy wish to be deleted
    private Strategy loadMenu() {
        System.out.println("please enter the number of the strategy you would like to revisit:");
        int menuLabel = 1;
        List<Strategy> strategies = strategiesList.getStrategies();
        for (Strategy s : strategies) {
            System.out.println(menuLabel + ": " + s.getStrategyName());
            menuLabel = menuLabel + 1;

        }
        int select = input.nextInt();
        select = select - 1;
        return strategiesList.getStrategies().get(select);

    }

    // REQUIRES: there exists a strategy in strategiesList
    // MODIFIES: this
    // EFFECTS: process user input, selection of the saved strategy;
    //          return the selected strategy
    private Strategy deleteMenu() {
        System.out.println("please enter the number of the strategy you would like to delete:");
        int menuLabel = 1;
        List<Strategy> strategies = strategiesList.getStrategies();
        for (Strategy s : strategies) {
            System.out.println(menuLabel + ": " + s.getStrategyName());
            menuLabel = menuLabel + 1;

        }
        int select = input.nextInt();
        select = select - 1;
        return strategiesList.getStrategies().get(select);

    }

    // MODIFIES: strategiesList
    // EFFECTS: given a Strategy, remove it from StrategiesList and saves to a file
    private void deleteStrategy(Strategy s) {
        ArrayList<Strategy> strats = strategiesList.getStrategies();
        strats.remove(s);
        strategiesList.addStrategiesList(strats);

        try {
            jsonWriter.open();
            jsonWriter.write(strategiesList);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this, statregiesList
    // EFFECTS: Process user inputs, only accepts y/n
    //          Keep adding new trades to the new strategy until users say no.
    //          Adds the strategy to strategiesList
    //          Saves file and launches results after
    private void addTradesToNewStrategy(Strategy s) {
        boolean adding = true;

        while (adding) {
            System.out.println("\nwould you like to add a new trade?");
            System.out.println("\ty -> yes");
            System.out.println("\tn -> no");

            String command = input.next();
            command = command.toLowerCase();

            if (command.equals("y")) {
                addTrade(s);
            } else if (command.equals("n")) {
                adding = false;
            } else {
                System.out.println("Selection is not valid, Please try again.");
            }

        }
        strategiesList.addStrat(s);
        saveStrategy();
        calculateResults(s);
    }

    // MODIFIES: this, statregiesList
    // EFFECTS: Process user inputs, only accepts y/n
    //          Keep adding new trades to the strategy modifying until users say no
    //          saves file and calculales results after
    private void modifyStrategy(Strategy s) {
        boolean adding = true;

        while (adding) {
            System.out.println("\nwould you like to add a new trade?");
            System.out.println("\ty -> yes");
            System.out.println("\tn -> no");

            String command = input.next();
            command = command.toLowerCase();

            if (command.equals("y")) {
                addTrade(s);
            } else if (command.equals("n")) {
                adding = false;
            } else {
                System.out.println("Selection is not valid, Please try again.");
            }

        }
        saveStrategy();
        calculateResults(s);
    }

    // MODIFIES: this
    // EFFECTS: process user input; turns input into Trades;
    //          Then adds trade into strategy;
    private void addTrade(Strategy s) {
        System.out.println("Please enter the ticker symbol:");
        String symbol = input.next();

        System.out.println("Please enter the price (in cents) the stock was bought at:");
        int priceBought = input.nextInt();

        System.out.println("Please enter the price (in cents) the stock was sold at:");
        int priceSold = input.nextInt();

        System.out.println("Please enter the number of shares bought:");
        int shares = input.nextInt();

        Trade trade = new Trade(symbol, priceBought, priceSold, shares);

        s.addTrade(trade);
    }

    // MODIFIES: this
    // EFFECTS: calculate winRate and profitLossRatio
    private void calculateResults(Strategy s) {
        if (s.getTradesList().size() == 0) {
            System.out.println("There were no trades in this strategy");
        } else if ((s.getTradesList().size() == 1)
                & (s.getTradesList().get(0).isWin())) {  // profitLoss = infinity
            s.calculateWinRate();
            resultMenuInfinity(s);
        } else {
            s.calculateWinRate();
            s.calculateProfitLoss();
            resultMenu(s);
        }
    }

    // EFFECTS: saves the StrategiesList to file;
    private void saveStrategy() {
        try {
            jsonWriter.open();
            jsonWriter.write(strategiesList);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    //Effects: Display the important statistics
    private void resultMenu(Strategy s) {

        String winRate = String.valueOf(s.getWinRate());
        String profitLossRatio = String.valueOf(s.getProfitLossRatio());

        System.out.println("The winrate of the strategy " + s.getStrategyName() + " is");
        System.out.println(winRate + "%");

        System.out.println("The profit loss ratio of the strategy " + s.getStrategyName() + " is");
        System.out.println(profitLossRatio);
    }

    //Effects: Display the important statistics for the case when
    private void resultMenuInfinity(Strategy s) {
        String winRate = String.valueOf(s.getWinRate());

        System.out.println("The winrate of the strategy " + s.getStrategyName() + " is");
        System.out.println(winRate + "%");

        System.out.println("There is not enough data to determine the profit loss ratio");
        System.out.println("There was not a loss trade");
    }


}

 */

