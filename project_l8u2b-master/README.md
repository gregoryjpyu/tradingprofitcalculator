# Personal Project Trading Strategy App

## Strategy Tester


Having a statistically proven strategy is crucial in the business of stock trading. 
This program hopes to achieve a user-friendly platform for traders to track their
existing or potential strategies. The program currenly requires manual input of stock 
performance and provides the 2 of the most important statistic (win rate and profit loss ratio).
Users' strategies are now saved and deleted from the system. Also, users can revisit these
previous strategies to view its statistic or to add more trades.
I myself use these statistics to track my own stock trading performance and hope 
to eventually achieve automation of strategy testing.


User Stories:
    As a user, I want to be able to:
-  add past trades (unlimited) into a strategy
-  name my strategy
-  get the crucial statistics for my strategy
-  keep testing without having to start the app over
-  save different strategies (have a record of the trades)
-  select any previous strategy and get back its crucial statistics
-  select any previous strategy and continue adding trades record
-  delete any previous strategy 

Phase 4: Task 2
(1 option/ robust + exceptions)
The Strategy class is robust as its calculateWinRate and
calculatProfitLossRatio methods deal with the special cases of having to calculate
statistics without trade or having to calculate infinite profit loss ratio. These
methods are called by the results screen in gui. Methods in this gui catches
the exception thrown by these methods and adjust to the JLabel accordingly.

Phase 4: Task 3
If more time were given, the classes which represent the different JPanels could be
further refractored. All these classes shared a common actionPerformed method
which could be refractored and overrided. The load and delete screen can be
refractored, as they both use a similar select method, this would allow further implementation
 to be easier as the select featured is refractored out. Also, the panels that pass down a 
Strategy could be refractored and have the parent save the Strategy being passed down. 