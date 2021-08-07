import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Card Simulator Graphical User Interface
 * 
 * @author Daniel Xue 07/27/2021
 * 
 * Sources: UVA CS2110 Homework 7, 
 * https://docs.oracle.com/javase/tutorial/uiswing/components/componentlist.html for swing documentation
 * https://web.mit.edu/6.005/www/sp14/psets/ps4/java-6-tutorial/components.html for learning swing components
 * https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html for learning swing layout
 * and https://stackoverflow.com/questions/2715118/how-to-change-the-size-of-the-font-of-a-jlabel-to-take-the-maximum-size for the font resizing helper method
 * JDK 11 Documentation
 */

public class SortingTest extends JFrame {

	/**
	 * Default serialization String required by extending JFrame
	 */
	private static final long serialVersionUID = 1L;
	
	// back end elements to make things work
	private Table table;
	private Player computerPlayer;
	
	// window elements formed as instance variables
	private JPanel panelPane;
	
	// Card View Panels
	private JPanel computerCardViewPanel;
	
	// Card Labels
	private JLabel computerCardLabel;
	
	// Arrays to store card images --> remember to instantiate!
	private JLabel[] computerCardLabelArray = new JLabel[13];
	
	// JTextFields
	private JTextField compareDelayField;
	private JTextField computerSwapDelayField;
	private JTextField cardNumberField;
	
	// JComboBoxes
	private JComboBox<String> computerSortComboBox;
	private JComboBox<String> cardFaceUpComboBox;
	private JComboBox<String> deckTypeComboBox;
	
	// CardSorters
	private CardSorter computerCardSorter = new CardSorter();
	
	// helps determine sizing of card images
	private int cardLabelMaxDimension = 95;
	
	// helps determine sizing of frame and its various elements
	private int frameWidth = 900;
	private int frameHeight = 600;
	
	public SortingTest() {
		// Instantiate back-end elements
		this.table = new Table();
		this.computerPlayer = new Player();
		this.computerPlayer.joinTable(this.table);
	}
	
	public SortingTest(Table table, Player computerPlayer) {
		// Instantiate back-elements
		this.table = table;
		this.computerPlayer = computerPlayer;
		this.computerPlayer.joinTable(this.table);
	}
	
	/**
	 * Main method. This method initializes a PhotoViewer, loads images into a
	 * PhotographContainer, then initializes the Graphical User Interface.
	 * 
	 * @param args Optional command-line arguments
	 */
	public static void main(String[] args) {
		// Create a Table object for this instance of Sorting Magic
		Table table = new Table();
		
		// Create two players to join the table
		Player computerPlayer = new Player("Computer");

		// Instantiate the PhotoViewer Class
		SortingTest myViewer = new SortingTest(table, computerPlayer);

		// Invoke and start the Graphical User Interface
		javax.swing.SwingUtilities.invokeLater(() -> myViewer.initialize());
	}
	
	/**
	 * Initialize all the GUI components. This method will be called by
	 * SwingUtilities when the application is started.
	 */
	private void initialize() {

		// Tell Java to exit the program when the window is closed
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Tell Java to title the window to Sorting Magic
		this.setTitle("Sorting Test");
		
		// We will use border layout on the main panel, since it is much easier for
		// organizing panels.
		panelPane = new JPanel(new BorderLayout());
		
		// TODO go back and redo all the index numbers for the elements
		
		// --------------------CARD VIEW PANEL--------------------
		
		// Create a panel to display and interact with cards
		JPanel cardPanel = new JPanel(new BorderLayout());
		
		// Specify dimension of each card panel relative to the frameWidth and Height
		Dimension cardPanelSize = new Dimension(5*this.frameWidth/6, 5*this.frameHeight/8);
		
		// ----------Computer Sub-Panel----------
		
		// Create a panel that displays the computer's cards
		JPanel computerCardPanel = new JPanel(new BorderLayout());
		computerCardPanel.setPreferredSize(cardPanelSize);
		// titles the panel with the computer player's name
		computerCardPanel.setBorder(BorderFactory.createTitledBorder(this.computerPlayer.getPlayerName()));
		
		// Add computer sub-panel to card view panel's west side
		cardPanel.add(computerCardPanel, BorderLayout.PAGE_START);
		
		// Create a label that displays computer's sorting information
		this.computerCardLabel = new JLabel("Compares: "+computerCardSorter.getCompareCounter()+"     Swaps: "+computerCardSorter.getSwapCounter()+"     Time Elapsed: "+computerCardSorter.getTimeElapsed()+" seconds");
		computerCardPanel.add(this.computerCardLabel, BorderLayout.PAGE_START);
		
		// Create a label that displays cards on a grid
		this.computerCardViewPanel = new JPanel(new GridLayout(0, 13));
		computerCardPanel.add(this.computerCardViewPanel, BorderLayout.CENTER);
		
		// --------------------CONTROL PANEL--------------------
		
		// Create a panel to display controls for sorting/racing
		JPanel controlPanel = new JPanel(new FlowLayout());
		
		// Instantiate new dimensions for labels and textfields
		Dimension labelDimension = new Dimension(120, 20);
		Dimension textFieldDimension = new Dimension(75, 20);
		Dimension comboBoxDimension = new Dimension(100, 20);
		Dimension buttonDimension = new Dimension(75, 25);
		
		// ----------Computer Setting Sub-Panel----------
		
		// Create a panel that displays computer sorting options
		JPanel computerSettingPanel = new JPanel(new BorderLayout());
//		computerOptionsPanel.setPreferredSize(new Dimension(450, 150));
		computerSettingPanel.setBorder(BorderFactory.createTitledBorder("Computer Settings"));
		
		// Add computer options sub-panel to control panel
		controlPanel.add(computerSettingPanel);
		
		// Create a panel that provides input for the compare delay in ms
		JPanel compareDelayPanel = new JPanel();
		
		// Label for compare delay input
		JLabel compareDelayLabel = new JLabel("Compare Delay (ms): ");
		compareDelayLabel.setPreferredSize(labelDimension);
		compareDelayPanel.add(compareDelayLabel);
		
		// TextField for compare delay input
		this.compareDelayField = new JTextField("200");
		this.compareDelayField.setPreferredSize(textFieldDimension);
		compareDelayPanel.add(this.compareDelayField);
		
		// Add the panel about the compare delay info to the information panel
		computerSettingPanel.add(compareDelayPanel, BorderLayout.PAGE_START);
		
		// Create a panel that provides input for the compare delay in ms
		JPanel computerSwapDelayPanel = new JPanel();
		
		// Label for compare delay input
		JLabel computerSwapDelayLabel = new JLabel("Swap Delay (ms): ");
		computerSwapDelayLabel.setPreferredSize(labelDimension);
		computerSwapDelayPanel.add(computerSwapDelayLabel);
		
		// TextField for compare delay input
		this.computerSwapDelayField = new JTextField("300");
		this.computerSwapDelayField.setPreferredSize(textFieldDimension);
		computerSwapDelayPanel.add(this.computerSwapDelayField);
		
		// Add the panel about the compare delay info to the information panel
		computerSettingPanel.add(computerSwapDelayPanel, BorderLayout.CENTER);
		
		// Create a panel that provides input for the computer's sort algorithm
		JPanel computerSortPanel = new JPanel();
		
		// Label for sort algorithm input
		JLabel computerSortLabel = new JLabel("Sorting Algorithm: ");
		computerSortLabel.setPreferredSize(labelDimension);
		computerSortPanel.add(computerSortLabel);
		
		// ComboBox for sort algorithm input
		String[] sorts = { "Bubble Sort", "Selection Sort", "Insertion Sort", "Shell Sort", "Mid Sort", "Quick Sort" };
		this.computerSortComboBox = new JComboBox<>(sorts);
		// default is "Bubble Sort"
		this.computerSortComboBox.setSelectedIndex(0);
		this.computerSortComboBox.setPreferredSize(comboBoxDimension);
		computerSortPanel.add(this.computerSortComboBox);
		
		// Add the computer sort panel to the computer options panel
		computerSettingPanel.add(computerSortPanel, BorderLayout.PAGE_END);
		
		// ----------Deck Options Sub-Panel----------
		
		// Create a panel that displays deck sorting options
		JPanel deckSettingPanel = new JPanel(new BorderLayout());
		deckSettingPanel.setBorder(BorderFactory.createTitledBorder("Deck Settings"));
		
		// Add computer options sub-panel to control panel
		controlPanel.add(deckSettingPanel);
		
		// Create a panel that provides input for the number of cards to sort
		JPanel cardNumberPanel = new JPanel();
		
		// Label for compare delay input
		JLabel cardNumberLabel = new JLabel("# of cards to sort: ");
		cardNumberLabel.setPreferredSize(labelDimension);
		cardNumberPanel.add(cardNumberLabel);
		
		// TextField for compare delay input
		this.cardNumberField = new JTextField("13");
		this.cardNumberField.setPreferredSize(textFieldDimension);
		cardNumberPanel.add(this.cardNumberField);
		
		// Add the panel about the compare delay info to the information panel
		deckSettingPanel.add(cardNumberPanel, BorderLayout.PAGE_START);
		
		// Create a panel that provides input for whether cards start faceUp
		JPanel cardFaceUpPanel = new JPanel();
		
		// Label for compare delay input
		JLabel cardFaceUpLabel = new JLabel("Cards start: ");
		cardFaceUpLabel.setPreferredSize(labelDimension);
		cardFaceUpPanel.add(cardFaceUpLabel);
		
		// TextField for compare delay input
		String[] cardFaceUpArray = { "Face Up", "Face Down" };
		this.cardFaceUpComboBox = new JComboBox<>(cardFaceUpArray);
		this.cardFaceUpComboBox.setPreferredSize(comboBoxDimension);
		this.cardFaceUpComboBox.setSelectedIndex(0); // default is face up
		cardFaceUpPanel.add(this.cardFaceUpComboBox);
		
		// Add the panel about the compare delay info to the information panel
		deckSettingPanel.add(cardFaceUpPanel, BorderLayout.CENTER);
		
		// Create a panel that provides input for which card to use
		JPanel deckTypePanel = new JPanel();
		
		// Label for deck type input
		JLabel deckTypeLabel = new JLabel("Deck type: ");
		deckTypeLabel.setPreferredSize(labelDimension);
		deckTypePanel.add(deckTypeLabel);
		
		// TextField for compare delay input
		String[] deckTypeArray = { "Uno", "Poker" };
		this.deckTypeComboBox = new JComboBox<>(deckTypeArray);
		this.deckTypeComboBox.setPreferredSize(comboBoxDimension);
		this.deckTypeComboBox.setSelectedIndex(0); // default is Uno
		deckTypePanel.add(this.deckTypeComboBox);
		
		// Add the panel about the compare delay info to the information panel
		deckSettingPanel.add(deckTypePanel, BorderLayout.PAGE_END);
		
		// ----------Action Sub-Panel----------
		
		// Create a panel that displays various sorting/racing actions
		JPanel actionPanel = new JPanel(new GridLayout(0, 1));
		actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
		
		// Add computer options sub-panel to control panel
		controlPanel.add(actionPanel);
		
		// Create tutorial button and add it to the action panel
		JButton dealButton = new JButton("Deal");
		dealButton.setPreferredSize(buttonDimension);
		actionPanel.add(dealButton);
		// Add Deal Button Listener to demonstrate the computer's sorting method
		dealButton.addActionListener(new DealButtonListener());
		
		// Create demo button and add it to the action panel
		JButton testButton = new JButton("Test");
		testButton.setPreferredSize(buttonDimension);
		actionPanel.add(testButton);
		// Add Demo Button Listener to demonstrate the computer's sorting method
		testButton.addActionListener(new TestButtonListener());
		
		// Create demo button and add it to the action panel
		JButton demoButton = new JButton("Demo");
		demoButton.setPreferredSize(buttonDimension);
		actionPanel.add(demoButton);
		// Add Demo Button Listener to demonstrate the computer's sorting method
		demoButton.addActionListener(new DemoButtonListener());
		
		// Create demo button and add it to the action panel
		JButton timerButton = new JButton("Timer Demo");
		timerButton.setPreferredSize(buttonDimension);
		actionPanel.add(timerButton);
		// Add Demo Button Listener to demonstrate the computer's sorting method
		timerButton.addActionListener(new TimerButtonListener());
		
		// --------------------MAIN DISPLAY--------------------
		
		// Add all the panels to the main display based on BorderLayout
		panelPane.add(cardPanel, BorderLayout.CENTER);
		panelPane.add(controlPanel, BorderLayout.PAGE_START);
		
		// Add the panelPane to the contentPane of the Frame (Window)
		this.getContentPane().add(panelPane);

		// Set the preferred size and show the main application window
		this.setPreferredSize(new Dimension(this.frameWidth, this.frameHeight));
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * ActionListener for the deal button
	 */
	private class DealButtonListener implements ActionListener {
		/**
		 * Action performed operation. 
		 * 
		 * @param evt The event that was performed
		 */
		@Override
		public void actionPerformed(ActionEvent evt) {
			// TODO should I reset the values in the card sorters for each new sort or should I just leave it as is with these?
			// reset the card labels too
			computerCardLabel.setText("Compares: 0     Swaps: 0     Time Elapsed: 0.0 seconds");
			// clear the borders around both the card view panels
			computerCardViewPanel.setBorder(BorderFactory.createEmptyBorder());
			
			// set the right deck type depending on what's selected
			// note: you can't use deckTypeComboBox.getSelectedItem() because it only returns an object
			switch(deckTypeComboBox.getItemAt(deckTypeComboBox.getSelectedIndex())) {
			case "Poker":
				// note that setDeck automatically returns all table and player cards to deck before switch decks
				table.setDeck(new PokerDeck());
				break;
			default:
				table.setDeck(new UnoDeck());
			}
			
			// refill and reshuffle the deck
			table.getDeck().fillDeck();
			table.getDeck().shuffle();
			
			// loop over table's players
			for (Player player : table.getPlayers()) {
				// if face up is selected, draw whatever of cards is inputted face up
				if (cardFaceUpComboBox.getSelectedItem().equals("Face Up")) {
					player.drawCard(true, Integer.parseInt(cardNumberField.getText()));
					// otherwise draw whatever number of cards is inputted face down
				} else if (cardFaceUpComboBox.getSelectedItem().equals("Face Down")) {
					player.drawCard(false, Integer.parseInt(cardNumberField.getText()));
				}
			}
			
			// display both players' cards
			createAndShowCardLabels(computerPlayer.getPlayerName(), cardLabelMaxDimension);
		}
	}
	
	private boolean createAndShowCardLabels(String playerName, int maxDimension) {		
		// initialize pointers so we don't have to access memory everytime
		JPanel panel;
		Player player;
		// get card depending on which playerName is inputted, return false if not "Computer" or "Player"
		if (playerName.equals(computerPlayer.getPlayerName())) {
			panel = this.computerCardViewPanel;
			player = this.computerPlayer;
		} else {
			return false;
		}
		// extract cards from player
		ArrayList<Deck.Card> cards = player.getCards(); 
		// have to remove all labels so we can re-display new ones
		panel.removeAll();
		// have to remove all cards so we can have new ones to mess around with, make the array the same size as the cards list passed
		JLabel[] cardLabelArray = new JLabel[cards.size()];
		
		// print cards for good luck
		System.out.println(cards);
		// note: you must manually loop through indices or this WILL choke on repeated cards
		// loop through player/computer's cards
		for (int i = 0; i < cards.size(); i++) {	
			// extract cardImage from card
			BufferedImage cardImage = cards.get(i).getImage(maxDimension);
			// check if the card's image is null, if so return false
			if (cardImage == null) return false;
			// create new image display label for each card and add it to the panel
			JLabel cardLabel = new JLabel();
			cardLabel.setIcon(new ImageIcon(cardImage));
			panel.add(cardLabel);	
			cardLabelArray[i] = cardLabel;	
		}		
		
		// assign the array to one of the cardLabelArrays
		// note: you have to do it like this or else the cardLabelArray will be cleared since it's only local to this method
		if (playerName.equals(computerPlayer.getPlayerName())) {
			computerCardLabelArray = cardLabelArray;
		}
		// re-update the panel
		panel.repaint();
		SwingUtilities.updateComponentTreeUI(this);
		return true;
	}
	
	// I got lazy making the race button so I just made more methods so I can use them elsewhere
	public void beginComputerSorting() {
		// note: you can't use getSelectedItem() since it returns an object and you want a String/int/etc.
		switch(computerSortComboBox.getItemAt(computerSortComboBox.getSelectedIndex())) {
		// bubble sort runs bubble sort on the left with the inputted compare and swap delay
		case "Bubble Sort":
			computerCardSorter.bubbleSort(computerPlayer.getCards(), computerCardLabelArray, Integer.parseInt(compareDelayField.getText()), Integer.parseInt(computerSwapDelayField.getText()), cardLabelMaxDimension);
			break;
		// and so on with selection sort and the others
		case "Selection Sort":
			computerCardSorter.selectionSort(computerPlayer.getCards(), computerCardLabelArray, Integer.parseInt(compareDelayField.getText()), Integer.parseInt(computerSwapDelayField.getText()), cardLabelMaxDimension);
			break;
		case "Insertion Sort":
			computerCardSorter.insertionSort(computerPlayer.getCards(), computerCardLabelArray, Integer.parseInt(compareDelayField.getText()), Integer.parseInt(computerSwapDelayField.getText()), cardLabelMaxDimension);
			break;
		case "Shell Sort":
			computerCardSorter.shellSort(computerPlayer.getCards(), computerCardLabelArray, Integer.parseInt(compareDelayField.getText()), Integer.parseInt(computerSwapDelayField.getText()), cardLabelMaxDimension);
			break;
		case "Merge Sort":
			computerCardSorter.mergeSort(computerPlayer.getCards(), Integer.parseInt(compareDelayField.getText()), Integer.parseInt(computerSwapDelayField.getText()));
			break;
		}
	}
	
	/**
	 * ActionListener for the test button
	 */
	private class TestButtonListener implements ActionListener {
		// fun with timers
		private Timer timer;
		private boolean hasBorder = false;
		private int i = 0;
		private int j = 1;
		
		// instantiate time in constructor
		public TestButtonListener() {
			// Make a timer with fun anonymous class
			timer = new Timer(0, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// check that there's more than four cards in the array and in the player's hand
					if (computerPlayer.getCards().size() >= 1 && computerCardLabelArray.length >= 1) {
						// check if there's a border 
						if (hasBorder) {
							// if it has a border, clear border
							computerCardLabelArray[i].setBorder(BorderFactory.createEmptyBorder());
							computerCardLabelArray[j].setBorder(BorderFactory.createEmptyBorder()); 
							
							// swap the cards
							Deck.Card[] computerCardArray = computerPlayer.getCards().toArray(new Deck.Card[computerPlayer.getCards().size()]);
							computerCardSorter.swap(computerCardArray, computerCardLabelArray, i, j, cardLabelMaxDimension);
							CardSorter.toCardArrayListFromArray(computerPlayer.getCards(), computerCardArray);
							
							// print for good luck
							System.out.println(computerPlayer.getCards());
							
							// set hasBorder to false
							hasBorder = false;
							
							// increment i up by one unless it's greater than or equal to the size of cards
							// in which case, set it to zero
							if (i >= computerPlayer.getCards().size()-1) i = 0;
							else i++;
							// same for j
							if (j >= computerPlayer.getCards().size()-1) j = 0;
							else j++;
							
							// stop if i is at the end?
							if (i >= computerPlayer.getCards().size()-1) timer.stop();
							
						} else {
							// if it doesn't, mark the first two cards in the array with a blue border
							computerCardLabelArray[i].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 5));
							computerCardLabelArray[j].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 5)); 
							// set hasBorder to true
							hasBorder = true;
						}
						
						// wait some amount of time
						try {
						    TimeUnit.MILLISECONDS.sleep(Integer.parseInt(computerSwapDelayField.getText()));
						} catch (InterruptedException ie) {
						    Thread.currentThread().interrupt();
						}
						
						computerCardViewPanel.repaint();
					}
				}	
			});
		}
		
		/**
		 * Runs the selected sort with the inputed compare and swap delay
		 * on the computer side of the window
		 * 
		 * @param evt The event that was performed
		 */
		@Override
		public void actionPerformed(ActionEvent evt) {
			// check that there's more than one card in the array and in the player's hand
			if (computerPlayer.getCards().size() >= 1 && computerCardLabelArray.length >= 1) {
				// if timer is running, stop the timer
				if (timer.isRunning()) {
					timer.stop();
					System.out.println("Timer off");
				} else {
					// reset values on i and j
					i = 0;
					j = 1;
					// clear borders
					for (JLabel cardLabel : computerCardLabelArray) {
						cardLabel.setBorder(BorderFactory.createEmptyBorder());
					}
					// if timer is not running, start the timer
					timer.start();
					System.out.println("Timer on");
				}
			}
		}
	}
	
	/**
	 * ActionListener for the demo button
	 */
	private class DemoButtonListener implements ActionListener {
		/**
		 * Runs the selected sort with the inputed compare and swap delay
		 * on the computer side of the window
		 * 
		 * @param evt The event that was performed
		 */
		@Override
		public void actionPerformed(ActionEvent evt) {
			// begin the computer sorting
			beginComputerSorting();
			// Get timeElapsed in seconds
			double timeElapsedInSeconds = (double) computerCardSorter.getTimeElapsed() / 1000.0;
			// re-displays the label with the new compares and swaps
			computerCardLabel.setText("Compares: "+computerCardSorter.getCompareCounter()+"     Swaps: "+computerCardSorter.getSwapCounter()+"     Time Elapsed: "+timeElapsedInSeconds+" seconds");
			// let's add a green border to signify this
			computerCardViewPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 0), 5));
			
			// re-display the image icons?
			for (int i = 0; i < computerCardLabelArray.length; i++) {
				CardSorter.resetCardLabelImage(computerPlayer.getCards(), computerCardLabelArray, i, cardLabelMaxDimension);
			}
			
			// repaint to show the new elements
			computerCardLabel.repaint();
		}
	}
	
	private Timer sortTimer;
	
	private class CompareAndSwapListener implements ActionListener {
		
		private boolean comparing = false;
		private boolean swapping = false;
		
		private int i = 0;
		private int j = 1;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// check if not comparing
			if (!comparing) {
				// mark cards with green border
				computerCardLabelArray[i].setBorder(BorderFactory.createLineBorder(new Color(0, 255, 0), 5));
				computerCardLabelArray[j].setBorder(BorderFactory.createLineBorder(new Color(0, 255, 0), 5));
				// set comparing to true
				comparing = true;
			} else {
				// check if swapping
				if (!swapping) {
					// if comparing and not swapping, wait the compare delay
					try {
					    TimeUnit.MILLISECONDS.sleep(Integer.parseInt(compareDelayField.getText()));
					} catch (InterruptedException ie) {
					    Thread.currentThread().interrupt();
					}
					
					// check if passed cards are in order
					if (computerPlayer.getCards().get(i).compareTo(computerPlayer.getCards().get(j)) > 0) {
						// if not mark cards with yellow border
						computerCardLabelArray[i].setBorder(BorderFactory.createLineBorder(new Color(255, 255, 0), 5));
						computerCardLabelArray[j].setBorder(BorderFactory.createLineBorder(new Color(255, 255, 0), 5));
						// and set swapping to true
						swapping = true;
					} else {
						// otherwise clear border, set comparing to false, and stop
						computerCardLabelArray[i].setBorder(BorderFactory.createEmptyBorder());
						computerCardLabelArray[j].setBorder(BorderFactory.createEmptyBorder()); 
						comparing = false;
						sortTimer.stop();
					}
				} else {
					// if swapping, wait swap delay
					try {
					    TimeUnit.MILLISECONDS.sleep(Integer.parseInt(computerSwapDelayField.getText()));
					} catch (InterruptedException ie) {
					    Thread.currentThread().interrupt();
					}
					// swap the cards
					Deck.Card[] computerCardArray = computerPlayer.getCards().toArray(new Deck.Card[computerPlayer.getCards().size()]);
					computerCardSorter.swap(computerCardArray, computerCardLabelArray, i, j, cardLabelMaxDimension);
					CardSorter.toCardArrayListFromArray(computerPlayer.getCards(), computerCardArray);
					// print for good luck
					System.out.println(computerPlayer.getCards());
					// clear border
					computerCardLabelArray[i].setBorder(BorderFactory.createEmptyBorder());
					computerCardLabelArray[j].setBorder(BorderFactory.createEmptyBorder()); 
					// set comparing and swapping to false
					comparing = false;
					swapping = false;
					// stop timer
					sortTimer.stop();
				}	
			}
		}
	}
	
	// helper methods to clean up sortTimer methods
	
	private void resetComputerCardLabel() {
		// Get timeElapsed in seconds
		double timeElapsedInSeconds = (double) computerCardSorter.getTimeElapsed() / 1000.0;
		// re-displays the label with the new compares and swaps
		computerCardLabel.setText("Compares: "+computerCardSorter.getCompareCounter()+"     Swaps: "+computerCardSorter.getSwapCounter()+"     Time Elapsed: "+timeElapsedInSeconds+" seconds");
		// repaint the cardLabel
		computerCardLabel.repaint();
	}
	
	private int compareComputerCards(int a, int b) {
		// wait compare delay
		try {
		    TimeUnit.MILLISECONDS.sleep(Integer.parseInt(compareDelayField.getText()));
		} catch (InterruptedException ie) {
		    Thread.currentThread().interrupt();
		}
		// increment compareCounter
		computerCardSorter.setCompareCounter(computerCardSorter.getCompareCounter()+1);
		// reset ComputerCardLabel
		resetComputerCardLabel();
		// return the results of the comparison
		return computerPlayer.getCards().get(a).compareTo(computerPlayer.getCards().get(b));
	}
	
	private void swapComputerCards(int a, int b) {
		// wait computer swap delay
		try {
		    TimeUnit.MILLISECONDS.sleep(Integer.parseInt(computerSwapDelayField.getText()));
		} catch (InterruptedException ie) {
		    Thread.currentThread().interrupt();
		}
		// increment swapCounter
		computerCardSorter.setSwapCounter(computerCardSorter.getSwapCounter()+1);
		// reset ComputerCardLabel
		resetComputerCardLabel();
		// swap the cards
		Deck.Card[] computerCardArray = computerPlayer.getCards().toArray(new Deck.Card[computerPlayer.getCards().size()]);
		computerCardSorter.swap(computerCardArray, computerCardLabelArray, a, b, cardLabelMaxDimension);
		CardSorter.toCardArrayListFromArray(computerPlayer.getCards(), computerCardArray);
		// print for good luck
		System.out.println(computerPlayer.getCards());
	}
	
	private void markComputerCardLabel(int index, String color) {
		switch(color) {
		case "Yellow":
			computerCardLabelArray[index].setBorder(BorderFactory.createLineBorder(new Color(255, 255, 0), 5));
			break;
		case "Green":
			computerCardLabelArray[index].setBorder(BorderFactory.createLineBorder(new Color(0, 255, 0), 5));
			break;
		case "Blue":
			computerCardLabelArray[index].setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 5));
			break;
		default:
			computerCardLabelArray[index].setBorder(BorderFactory.createEmptyBorder());
		}
	}
	
	private class BubbleSortListener implements ActionListener {
		
		private boolean comparing = false;
		private boolean swapping = false;
		private boolean isSorted = true;
		
		private int i = 0;
		private int j = 0;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// set i to end of cards if at beginning 
			if (i <= 0) i = computerPlayer.getCards().size() - 1;

			// check if not comparing
			if (!comparing) {
				// mark cards with green border
				markComputerCardLabel(j, "Green");
				markComputerCardLabel(j+1, "Green");
				// set comparing to true
				comparing = true;
			} else {
				// check if swapping
				if (!swapping) {
					// check if passed cards are in order
					if (compareComputerCards(j, j+1) > 0) {
						// if not mark cards with yellow border
						markComputerCardLabel(j, "Yellow");
						markComputerCardLabel(j+1, "Yellow");
						// and set swapping to true
						swapping = true;
						// set isSorted to false
						isSorted = false;
					} else {
						// otherwise clear border, set comparing to false, and stop
						markComputerCardLabel(j, "Empty");
						markComputerCardLabel(j+1, "Empty");
						comparing = false;		
						incrementPointers();
					}
				} else {
					swapComputerCards(j, j+1);
					// clear border
					markComputerCardLabel(j, "Empty");
					markComputerCardLabel(j+1, "Empty");
					// set comparing and swapping to false
					comparing = false;
					swapping = false;
					incrementPointers();
				}	
			}
		}
		
		private void incrementPointers() {
			// check if j is greater than or equal to i-1
			if (j >= i-1) {
				// if so, set j to 0 and decrement i by 1
				j = 0;
				i--;
				// and check if x is at 0 or the rest of the array is sorted, if so, stop the timer
				if (i <= 0 || isSorted) {
					// record end time
					computerCardSorter.setTimeElapsed(System.currentTimeMillis() - computerCardSorter.getTimeElapsed());
					resetComputerCardLabel();
					sortTimer.stop();
				}
				// and set isSorted to true
				isSorted = true;
			// otherwise increment j by 1
			} else {
				j++;
			}
		}
	}
	
	private class SelectionSortListener implements ActionListener {
		
		private boolean comparing = false;
		private boolean swapping = false;
		
		private int i = 0;
		private int j = 1;
		private int smallestCardIndex = 0;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// check if not comparing
			if (!comparing) {
				// mark cards with green border
				markComputerCardLabel(smallestCardIndex, "Yellow");
				markComputerCardLabel(j, "Green");
				// set comparing to true
				comparing = true;
			} else {
				// check if swapping
				if (!swapping) {
					// check if passed cards are in order
					if (compareComputerCards(smallestCardIndex, j) > 0) {
						// clear border on smallestCardIndex, leave mark j with yellow border
						markComputerCardLabel(j, "Yellow");
						markComputerCardLabel(smallestCardIndex, "Empty");
						// set smallestCardIndex to j
						smallestCardIndex = j;
					} else {
						// otherwise clear border on j
						markComputerCardLabel(j, "Empty");
					}					
					// if j is at the end, set swapping to true, otherwise set comparing to false
					if (j >= computerPlayer.getCards().size() - 1) {
						swapping = true;
					} else {
						comparing = false;
						// increment j by 1
						j++;
					}
				// if swapping wait swap delay and then swap
				} else {
					swapComputerCards(i, smallestCardIndex);
					// clear the border on smallestCardIndex
					markComputerCardLabel(smallestCardIndex, "Empty");
					// set both comparing and swapping to false
					comparing = false;
					swapping = false;
					// increment pointers
					incrementPointers();
				}
			}
		}
		
		private void incrementPointers() {			
			// increment i by 1
			i++;				
			// and check if i is past the last index or the rest of the array is sorted, if so, stop the timer
			if (i >= computerPlayer.getCards().size() - 1) {
				// and set i, j, and smallestCardIndex back to where it was in the beginning
				i = 0;
				j = i+1;
				smallestCardIndex = i;
				// record end time
				computerCardSorter.setTimeElapsed(System.currentTimeMillis() - computerCardSorter.getTimeElapsed());
				resetComputerCardLabel();
				// stop the timer
				sortTimer.stop();
			} else {
				// set j to i+1 and smallestCardIndex to i if i is not at the end
				j = i+1;
				smallestCardIndex = i;
			}
		}
	}
	
	private class InsertionSortListener implements ActionListener {
		
		private boolean comparing = false;
		private boolean swapping = false;
		
		private int i = 1;
		private int j = 1;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// check if not comparing
			if (!comparing) {
				// mark cards with green border
				markComputerCardLabel(j, "Green");
				markComputerCardLabel(j-1, "Green");
				// set comparing to true
				comparing = true;
			} else {
				// check if swapping
				if (!swapping) {
					// check if passed cards are in order
					if (compareComputerCards(j-1, j) > 0) {						
						// if not mark cards with yellow border
						markComputerCardLabel(j-1, "Yellow");
						markComputerCardLabel(j, "Yellow");
						// and set swapping to true
						swapping = true;
					} else {
						// otherwise clear border, set comparing to false, and stop
						markComputerCardLabel(j-1, "Empty");
						markComputerCardLabel(j, "Empty");
						comparing = false;		
						incrementPointers();
					}
				} else {
					swapComputerCards(j-1, j);
					// clear border
					markComputerCardLabel(j-1, "Empty");
					markComputerCardLabel(j, "Empty"); 
					// set comparing and swapping to false
					comparing = false;
					swapping = false;
					// check if j is one before the beginning
					if (j <= 1) {
						// if so, increment pointers
						incrementPointers();
					// otherwise increment j by 1
					} else {
						j--;
					}
				}	
			}
		}
		
		private void incrementPointers() {
			// if so, increment i by 1 and set j to i
			i++;
			j = i;
			// and check if i is past the end of the array, if so, stop the timer
			if (i >= computerPlayer.getCards().size()) {
				// record end time
				computerCardSorter.setTimeElapsed(System.currentTimeMillis() - computerCardSorter.getTimeElapsed());
				resetComputerCardLabel();
				// stop the timer
				sortTimer.stop();
			}
		}
	}
	
	private class ShellSortListener implements ActionListener {
		
		private boolean comparing = false;
		private boolean swapping = false;
		
		private int i;
		private int j;
		private int gap;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// check if gap is 1
			if (gap <= 0) {
				// set initial gap size as half the size of computerPlayer's cards
				gap = computerPlayer.getCards().size() / 2;
				// set i and j at the gap
				i = gap;
				j = gap;
			}
			
			// check if not comparing
			if (!comparing) {
				// mark cards with green border
				markComputerCardLabel(j, "Green");
				markComputerCardLabel(j-gap, "Green");
				// set comparing to true
				comparing = true;
			} else {
				// check if swapping
				if (!swapping) {
					// check if passed cards are in order
					if (compareComputerCards(j-gap, j) > 0) {
						// if not mark cards with yellow border
						markComputerCardLabel(j, "Yellow");
						markComputerCardLabel(j-gap, "Yellow");
						// and set swapping to true
						swapping = true;
					} else {
						// otherwise clear border, set comparing to false, and stop
						markComputerCardLabel(j, "Empty");
						markComputerCardLabel(j-gap, "Empty");
						comparing = false;		
						incrementPointers();
					}
				} else {					
					swapComputerCards(j-gap, j);
					// clear border
					markComputerCardLabel(j, "Empty");
					markComputerCardLabel(j-gap, "Empty");
					// set comparing and swapping to false
					comparing = false;
					swapping = false;
					// check if j is one before the beginning
					if (j < 2*gap) {
						// if so, increment pointers
						incrementPointers();
					// otherwise decrement j by the gap
					} else {
						j -= gap;
					}
				}	
			}
		}
		
		private void incrementPointers() {
			// if so, increment i by one and set j by the gap
			i++;
			j = i;
			// and check if i is past the end of the array
			if (i >= computerPlayer.getCards().size()) {
				// if so, check if gap size is one or less
				if (gap <= 1) {
					// record end time
					computerCardSorter.setTimeElapsed(System.currentTimeMillis() - computerCardSorter.getTimeElapsed());
					resetComputerCardLabel();
					// if gap is one or less, stop the timer
					sortTimer.stop();
				// otherwise, half the gap and repeat
				} else {
					gap /= 2;
					// set i and j at the gap
					i = gap;
					j = gap;
				}
			}
		}
	}
	
	private class MidSortListener implements ActionListener {
		
		private boolean hasPivot = false;
		private boolean hasHigh = false;
		private boolean comparing = false;
		private boolean swapping = false;
		
		private int i;
		private int j;
		private int pivot;
		private int low;
		private int high;
		private int firstCardComparison;
		private int secondCardComparison;
		private int thirdCardComparison;
		
		private ArrayList<Integer> lowQueue = new ArrayList<>();
		private ArrayList<Integer> highQueue = new ArrayList<>();
		
		@Override
		public void actionPerformed(ActionEvent e) {						
			// check if there's a high
			if (!hasHigh) {
				// if high and low queues are empty, queue low as first index and high as last index
				if (lowQueue.isEmpty() && highQueue.isEmpty()) {
					lowQueue.add(0);
					highQueue.add(computerPlayer.getCards().size());
				}
				// choose the last card as the high (dequeue from queue of highs)
				high = highQueue.remove(0);
				// set a low at the beginning (dequeue from queue of lows)
				low = lowQueue.remove(0);
				// set hasHigh to true
				hasHigh = true;
			// if hasHigh, check if hasPivot
			} else {
				if (!hasPivot) {
					// check if low >= high (slice only has one card or less)
					if (low >= high) {
						// set hasHigh to false
						hasHigh = false;
						// and clear border at pivot
						markComputerCardLabel(pivot, "Empty");
						// check if queues are empty so we know when to stop
						if (lowQueue.isEmpty() && highQueue.isEmpty()) {
							
//							boolean isSorted = true;
//							for (int i = 0; i < computerPlayer.getCards().size()-1; i++) {
//								if (computerPlayer.getCards().get(i).compareTo(computerPlayer.getCards().get(i+1)) > 0) {
//									isSorted = false;
//								}
//							}
//							if (isSorted) System.out.println("cards is sorted!!!!");
//							else System.out.println("cards is NOT sorted!!!!");
							
							// record end time
							computerCardSorter.setTimeElapsed(System.currentTimeMillis() - computerCardSorter.getTimeElapsed());
							resetComputerCardLabel();
							
							sortTimer.stop();	
						}
						// otherwise, go back to the beginning with remaining highs and lows in queue
					// remember the else so none of this unnecessary stuff runs
					} else {
						// define pivot as pivot = low + (high - low) / 2
						pivot = low + (high - low) / 2;
						// set i as low
						i = low;
						// set j as pivot+1
						j = pivot + 1;
						// and mark pivot with yellow border
						markComputerCardLabel(pivot, "Yellow");
						
//						System.out.println("current pivot: " + pivot);
//						System.out.println("current high: " + high);
//						System.out.println("current low: " + low);
//						System.out.println("current i: " + i);
//						System.out.println("current j: " + j);
						
						// finally, set hasPivot to true
						hasPivot = true;
					}
				// otherwise, call partition() method
				} else {
					partition();
				}
			}
		}
		
		private void partition() {	
			// check if i is past the pivot or if j is past the end
			if (i < pivot || j < high) {
				// check if i at or past the pivot
				if (i >= pivot) {
					// compare pivot and j
					if (firstCardComparison == 0) {
						// if j is smaller than pivot, swap pivot with card immediately to right
						firstCardComparison = compareThenSwap(pivot, j, pivot, pivot+1);
					} else if (firstCardComparison > 0) {
						// if card immediately to the right was not j, swap that card with j
						if (pivot+1 != j) {
							if (secondCardComparison == 0) {
								secondCardComparison = compareAndSwap(pivot, j);
							} else {
								// then increment pivot and j up
								pivot++;
								j++;
								// and set card comparisons to 0
								firstCardComparison = 0;
								secondCardComparison = 0;
								// mark pivot with yellow border
								markComputerCardLabel(pivot, "Yellow");
								
//								System.out.println("pivot: " + pivot);
//								System.out.println("i: " + i);
//								System.out.println("j: " + j);
							}
						} else {
							// then increment pivot and j up
							pivot++;
							j++;
							// and set card comparisons to 0
							firstCardComparison = 0;
							// mark pivot with yellow border
							markComputerCardLabel(pivot, "Yellow");
							
//							System.out.println("pivot: " + pivot);
//							System.out.println("i: " + i);
//							System.out.println("j: " + j);
						}
					} else if (firstCardComparison < 0) {
						// then increment j up
						j++;
						// and set card comparisons to 0
						firstCardComparison = 0;
						// mark pivot with yellow border
						markComputerCardLabel(pivot, "Yellow");
						
//						System.out.println("pivot: " + pivot);
//						System.out.println("i: " + i);
//						System.out.println("j: " + j);
					}
				// check if j is at or past the end of the array
				} else if (j >= high) {
					// compare i and pivot
					if (firstCardComparison == 0) {
						// if i is greater than pivot, swap pivot with card immediate to the left
						firstCardComparison = compareThenSwap(i, pivot, pivot-1, pivot);
					} else if (firstCardComparison > 0) {
						// if card immediately to the left was not i, swap that card with i
						if (pivot-1 != i) {
							if (secondCardComparison == 0) {
								secondCardComparison = compareAndSwap(i, pivot);
							} else {
								// then decrement pivot and increment i
								pivot--;
								i++;
								// and set card comparisons to 0
								firstCardComparison = 0;
								secondCardComparison = 0;
								// mark pivot with yellow border
								markComputerCardLabel(pivot, "Yellow");
								
//								System.out.println("pivot: " + pivot);
//								System.out.println("i: " + i);
//								System.out.println("j: " + j);
							}
						} else {
							// then decrement pivot and increment i
							pivot--;
							i++;
							// and set card comparisons to 0
							firstCardComparison = 0;
							// mark pivot with yellow border
							markComputerCardLabel(pivot, "Yellow");
							
//							System.out.println("pivot: " + pivot);
//							System.out.println("i: " + i);
//							System.out.println("j: " + j);
						}
					} else if (firstCardComparison < 0) {
						// then increment i
						i++;
						// and set card comparisons to 0
						firstCardComparison = 0;
						// mark pivot with yellow border
						markComputerCardLabel(pivot, "Yellow");
						
//						System.out.println("pivot: " + pivot);
//						System.out.println("i: " + i);
//						System.out.println("j: " + j);
					}
				// if i is not past the pivot and j is not past the end
				} else {
					// compare and swap i and j 
					if (firstCardComparison == 0) {
						firstCardComparison = compareAndSwap(i, j);
					} else {
						if (secondCardComparison == 0) {
							secondCardComparison = compareAndSwap(pivot, j);
						} else {
							if (thirdCardComparison == 0) {
								thirdCardComparison = compareAndSwap(i, pivot);
							} else {
								// if the pivot changes, reset i and j
								if (secondCardComparison > 0 || thirdCardComparison > 0) {
									i = low;
									j = pivot + 1;
								// otherwise increment i and j by 1
								} else {
									i++;
									j++;
								}
//								System.out.println("pivot: " + pivot);
//								System.out.println("i: " + i);
//								System.out.println("j: " + j);
								// reset cardComparisons
								firstCardComparison = 0;
								secondCardComparison = 0;
								thirdCardComparison = 0;
								// mark pivot with yellow border
								markComputerCardLabel(pivot, "Yellow");
							}
						}
					}
				}				
			// if i is greater than mid, stop current partition method
			} else {
			// call quicksort on the left 
				// queue low to the queue of lows
				lowQueue.add(low);
				// queue mid to the queue of highs
				highQueue.add(pivot);
			
			// call quicksort on the right
				// queue mid+1 to the queue of lows
				lowQueue.add(pivot+1);
				// queue high to the queue of highs	
				highQueue.add(high);
				// set hasPivot and hasHigh to false
				hasHigh = false;
				hasPivot = false;
				// and clear pivot border
				markComputerCardLabel(pivot, "Empty");
				
//				System.out.println(lowQueue);
//				System.out.println(highQueue);
			}
		}
		
		private int compareAndSwap(int a, int b) {			
			// check if not comparing
			if (!comparing) {
				
//				System.out.println("Comparing and swapping " + a + " and " + b + "...");
				
				// mark cards with green border
				markComputerCardLabel(a, "Green");
				markComputerCardLabel(b, "Green");
				
				// set comparing to true
				comparing = true;
			} else {
				// check if swapping
				if (!swapping) {					
					// check if passed cards are in order
					if (compareComputerCards(a, b) > 0) {
						// if not mark cards with yellow border
						markComputerCardLabel(a, "Yellow");
						markComputerCardLabel(b, "Yellow");
						// and set swapping to true
						swapping = true;
					} else {
						// otherwise clear border, set comparing to false, and stop
						markComputerCardLabel(a, "Empty");
						markComputerCardLabel(b, "Empty");
						comparing = false;		
						// return -1 to indicate a comes before b
						return -1;
					}
				} else {
					swapComputerCards(a, b);
					// clear border
					markComputerCardLabel(a, "Empty");
					markComputerCardLabel(b, "Empty");
					// set comparing and swapping to false
					comparing = false;
					swapping = false;
					// return 1 to indicate a comes after b
					return 1;
				}	
			}
			// return 0 in all other cases
			return 0;
		}
		
		private int compareThenSwap(int a, int b, int c, int d) {			
			// check if not comparing
			if (!comparing) {
				
//				System.out.println("Comparing and swapping " + a + " and " + b + "...");
				
				// mark cards with green border				
				markComputerCardLabel(a, "Green");
				markComputerCardLabel(b, "Green");
				// set comparing to true
				comparing = true;
			} else {
				// check if swapping
				if (!swapping) {		
					// check if passed cards are in order
					if (compareComputerCards(a, b) > 0) {
						// clear compared cards' border
						markComputerCardLabel(a, "Empty");
						markComputerCardLabel(b, "Empty");
						// if not mark cards with yellow border
						markComputerCardLabel(c, "Yellow");
						markComputerCardLabel(d, "Yellow");
						// and set swapping to true
						swapping = true;
					} else {
						// otherwise clear border, set comparing to false, and stop
						markComputerCardLabel(a, "Empty");
						markComputerCardLabel(b, "Empty");
						comparing = false;		
						// return -1 to indicate a comes before b
						return -1;
					}
				} else {					
					swapComputerCards(c, d);
					// clear border
					markComputerCardLabel(c, "Empty");
					markComputerCardLabel(d, "Empty");
					// set comparing and swapping to false
					comparing = false;
					swapping = false;
					// return 1 to indicate a comes after b
					return 1;
				}	
			}
			// return 0 in all other cases
			return 0;
		}
	}
	
	private class QuickSortListener implements ActionListener {
		
		private boolean hasPivot = false;
		private boolean hasHigh = false;
		private boolean comparing = false;
		private boolean swapping = false;
		
		private int i;
		private int pivot;
		private int low;
		private int high;
		private int firstCardComparison;
		private int secondCardComparison;
		
		private ArrayList<Integer> lowQueue = new ArrayList<>();
		private ArrayList<Integer> highQueue = new ArrayList<>();
		
		@Override
		public void actionPerformed(ActionEvent e) {						
			// check if there's a high
			if (!hasHigh) {
				// if high and low queues are empty, queue low as first index and high as last index
				if (lowQueue.isEmpty() && highQueue.isEmpty()) {
					lowQueue.add(0);
					highQueue.add(computerPlayer.getCards().size());
				}
				// choose the last card as the high (dequeue from queue of highs)
				high = highQueue.remove(0);
				// set a low at the beginning (dequeue from queue of lows)
				low = lowQueue.remove(0);
				// set hasHigh to true
				hasHigh = true;
			// if hasHigh, check if hasPivot
			} else {
				if (!hasPivot) {
					// check if low >= high (slice only has one card or less)
					if (low >= high) {
						// set hasHigh to false
						hasHigh = false;
						// and clear border at pivot
						markComputerCardLabel(pivot, "Empty");
						// check if queues are empty so we know when to stop
						if (lowQueue.isEmpty() && highQueue.isEmpty()) {
							
//							boolean isSorted = true;
//							for (int i = 0; i < computerPlayer.getCards().size()-1; i++) {
//								if (computerPlayer.getCards().get(i).compareTo(computerPlayer.getCards().get(i+1)) > 0) {
//									isSorted = false;
//								}
//							}
//							if (isSorted) System.out.println("cards is sorted!!!!");
//							else System.out.println("cards is NOT sorted!!!");
							
							// record end time
							computerCardSorter.setTimeElapsed(System.currentTimeMillis() - computerCardSorter.getTimeElapsed());
							resetComputerCardLabel();
							
							sortTimer.stop();	
						}
						// otherwise, go back to the beginning with remaining highs and lows in queue
					// remember the else so none of this unnecessary stuff runs
					} else {
						// define pivot as last index of array
						pivot = high-1;
						// set i as low
						i = low;
						// and mark pivot with yellow border
						markComputerCardLabel(pivot, "Yellow");
						
//						System.out.println("current pivot: " + pivot);
//						System.out.println("current high: " + high);
//						System.out.println("current low: " + low);
//						System.out.println("current i: " + i);
//						
						// finally, set hasPivot to true
						hasPivot = true;
					}
				// otherwise, call partition() method
				} else {
					partition();
				}
			}
		}
		
		private void partition() {	
			// check if i is past the pivot
			if (i < pivot) {
				// compare i and pivot
				if (firstCardComparison == 0) {
					// if i is greater than pivot, swap pivot with card immediate to the left
					firstCardComparison = compareThenSwap(i, pivot, pivot-1, pivot);
				} else if (firstCardComparison > 0) {
					// if card immediately to the left was not i, swap that card with i
					if (pivot-1 != i) {
						if (secondCardComparison == 0) {
							secondCardComparison = compareAndSwap(i, pivot);
						} else {
							// then decrement pivot and increment i
							pivot--;
//							i++;
							// and set card comparisons to 0
							firstCardComparison = 0;
							secondCardComparison = 0;
							// mark pivot with yellow border
							markComputerCardLabel(pivot, "Yellow");
						
//							System.out.println("pivot: " + pivot);
//							System.out.println("i: " + i);
						}
					} else {
						// then decrement pivot and increment i
						pivot--;
//						i++;
						// and set card comparisons to 0
						firstCardComparison = 0;
						// mark pivot with yellow border
						markComputerCardLabel(pivot, "Yellow");
					
//						System.out.println("pivot: " + pivot);
//						System.out.println("i: " + i);
					}
				} else if (firstCardComparison < 0) {
					// then increment i
					i++;
					// and set card comparisons to 0
					firstCardComparison = 0;
					// mark pivot with yellow border
					markComputerCardLabel(pivot, "Yellow");
				
//					System.out.println("pivot: " + pivot);
//					System.out.println("i: " + i);
				}				
			// if i is greater than mid, stop current partition method
			} else {
			// call quicksort on the left 
				// queue low to the queue of lows
				lowQueue.add(low);
				// queue mid to the queue of highs
				highQueue.add(pivot);
			
			// call quicksort on the right
				// queue mid+1 to the queue of lows
				lowQueue.add(pivot+1);
				// queue high to the queue of highs	
				highQueue.add(high);
				// set hasPivot and hasHigh to false
				hasHigh = false;
				hasPivot = false;
				// and clear pivot border
				markComputerCardLabel(pivot, "Empty");
				
//				System.out.println(lowQueue);
//				System.out.println(highQueue);
			}
		}
		
		private int compareAndSwap(int a, int b) {			
			// check if not comparing
			if (!comparing) {
				
//				System.out.println("Comparing and swapping " + a + " and " + b + "...");
				
				// mark cards with green border
				markComputerCardLabel(a, "Green");
				markComputerCardLabel(b, "Green");
				
				// set comparing to true
				comparing = true;
			} else {
				// check if swapping
				if (!swapping) {					
					// check if passed cards are in order
					if (compareComputerCards(a, b) > 0) {
						// if not mark cards with yellow border
						markComputerCardLabel(a, "Yellow");
						markComputerCardLabel(b, "Yellow");
						// and set swapping to true
						swapping = true;
					} else {
						// otherwise clear border, set comparing to false, and stop
						markComputerCardLabel(a, "Empty");
						markComputerCardLabel(b, "Empty");
						comparing = false;		
						// return -1 to indicate a comes before b
						return -1;
					}
				} else {
					swapComputerCards(a, b);
					// clear border
					markComputerCardLabel(a, "Empty");
					markComputerCardLabel(b, "Empty");
					// set comparing and swapping to false
					comparing = false;
					swapping = false;
					// return 1 to indicate a comes after b
					return 1;
				}	
			}
			// return 0 in all other cases
			return 0;
		}
		
		private int compareThenSwap(int a, int b, int c, int d) {			
			// check if not comparing
			if (!comparing) {
				
//				System.out.println("Comparing and swapping " + a + " and " + b + "...");
				
				// mark cards with green border				
				markComputerCardLabel(a, "Green");
				markComputerCardLabel(b, "Green");
				// set comparing to true
				comparing = true;
			} else {
				// check if swapping
				if (!swapping) {		
					// check if passed cards are in order
					if (compareComputerCards(a, b) > 0) {
						// clear compared cards' border
						markComputerCardLabel(a, "Empty");
						markComputerCardLabel(b, "Empty");
						// if not mark cards with yellow border
						markComputerCardLabel(c, "Yellow");
						markComputerCardLabel(d, "Yellow");
						// and set swapping to true
						swapping = true;
					} else {
						// otherwise clear border, set comparing to false, and stop
						markComputerCardLabel(a, "Empty");
						markComputerCardLabel(b, "Empty");
						comparing = false;		
						// return -1 to indicate a comes before b
						return -1;
					}
				} else {					
					swapComputerCards(c, d);
					// clear border
					markComputerCardLabel(c, "Empty");
					markComputerCardLabel(d, "Empty");
					// set comparing and swapping to false
					comparing = false;
					swapping = false;
					// return 1 to indicate a comes after b
					return 1;
				}	
			}
			// return 0 in all other cases
			return 0;
		}
	}
	
	private class TimerButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// note: you can't use getSelectedItem() since it returns an object and you want a String/int/etc.
			switch(computerSortComboBox.getItemAt(computerSortComboBox.getSelectedIndex())) {
			case "Bubble Sort":
				sortTimer = new Timer(0, new BubbleSortListener());
				break;
			case "Selection Sort":
				sortTimer = new Timer(0, new SelectionSortListener());
				break;
			case "Insertion Sort":
				sortTimer = new Timer(0, new InsertionSortListener());
				break;
			case "Shell Sort":
				sortTimer = new Timer(0, new ShellSortListener());
				break;
			case "Mid Sort":
				sortTimer = new Timer(0, new MidSortListener());
				break;
			case "Quick Sort":
				sortTimer = new Timer(0, new QuickSortListener());
				break;
			}	
			// record start time
			computerCardSorter.resetCounters();
			// start sort if there is at least one card in the computer
			if (computerPlayer.getCards().size() >= 1 && computerCardLabelArray.length >= 1) sortTimer.start();
		}
	}
}
