import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

/**
 * Card Simulator Graphical User Interface
 * 
 * @author Daniel Xue 07/27/2021
 * 
 * Sources: UVA CS2110 Homework 7, 
 * https://docs.oracle.com/javase/tutorial/uiswing/components/componentlist.html
 * https://web.mit.edu/6.005/www/sp14/psets/ps4/java-6-tutorial/components.html
 * https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
 * JDK 11 Documentation
 */

public class SortingMagic extends JFrame {

	/**
	 * Default serialization String required by extending JFrame
	 */
	private static final long serialVersionUID = 1L;
	
	// back end elements to make things work
	private Table table;
	private Player computerPlayer;
	private Player humanPlayer;
	
	// window elements formed as instance variables
	private JPanel panelPane;
	
	// Card View Panels
	private JPanel computerCardViewPanel;
	private JPanel playerCardViewPanel;
	
	// Card Labels
	private JLabel computerCardLabel;
	private JLabel playerCardLabel;
	
	// Arrays to store card images --> remember to instantiate!
	private JLabel[] computerCardLabelArray = new JLabel[13];
	private JLabel[] playerCardLabelArray = new JLabel[13];
	
	// JTextFields
	private JTextField compareDelayField;
	private JTextField computerSwapDelayField;
	private JTextField cardNumberField;
	private JTextField maxFaceUpField;
	private JTextField playerSwapDelayField;
	
	// JComboBoxes
	private JComboBox<String> computerSortComboBox;
	private JComboBox<String> cardFaceUpComboBox;
	private JComboBox<String> deckTypeComboBox;
	
	// CardSorters
	private CardSorter computerCardSorter = new CardSorter();
	private CardSorter playerCardSorter = new CardSorter();
	
	// helps determine sizing of card images
	private int cardLabelMaxDimension = 125;
	
	public SortingMagic() {
		// Instantiate back-end elements
		this.table = new Table();
		this.computerPlayer = new Player();
		this.computerPlayer.joinTable(this.table);
		this.humanPlayer = new Player();
		this.humanPlayer.joinTable(this.table);
	}
	
	public SortingMagic(Table table, Player computerPlayer, Player humanPlayer) {
		// Instantiate back-elements
		this.table = table;
		this.computerPlayer = computerPlayer;
		this.computerPlayer.joinTable(this.table);
		this.humanPlayer = humanPlayer;
		this.humanPlayer.joinTable(this.table);
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
		Player humanPlayer = new Player("Player");

		// Instantiate the PhotoViewer Class
		SortingMagic myViewer = new SortingMagic(table, computerPlayer, humanPlayer);

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

		// Tell Java to title the window to Meme Magic
		this.setTitle("Sorting Magic");
		
		// We will use border layout on the main panel, since it is much easier for
		// organizing panels.
		panelPane = new JPanel(new BorderLayout());
		
		// TODO go back and redo all the index numbers for the elements
		
		// --------------------CARD VIEW PANEL--------------------
		
		// Create a panel to display and interact with cards
		JPanel cardPanel = new JPanel(new BorderLayout());
		
		// ----------Computer Sub-Panel----------
		
		// Create a panel that displays the computer's cards
		JPanel computerCardPanel = new JPanel(new BorderLayout());
		computerCardPanel.setPreferredSize(new Dimension(1000, 300));
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
		
		// ----------Player Sub-Panel----------
		
		// Create a panel that displays the player's cards
		JPanel playerCardPanel = new JPanel(new BorderLayout());
		playerCardPanel.setPreferredSize(new Dimension(1000, 300));
		// Name panel using humanPlayer's inputted name
		playerCardPanel.setBorder(BorderFactory.createTitledBorder(this.humanPlayer.getPlayerName()));
		
		// Add player card sub-panel to the card view panel's east side
		cardPanel.add(playerCardPanel, BorderLayout.PAGE_END);
		
		// Create a label that displays computer's sorting information
		this.playerCardLabel = new JLabel("Flips: "+"     Swaps: "+"     Time Elapsed: "+" seconds");
		playerCardPanel.add(this.playerCardLabel, BorderLayout.PAGE_START);
		
		// Create a label that displays cards on a grid
		this.playerCardViewPanel = new JPanel(new GridLayout(0, 13));
		playerCardPanel.add(this.playerCardViewPanel, BorderLayout.CENTER);
		
		// --------------------CONTROL PANEL--------------------
		
		// Create a panel to display controls for sorting/racing
		JPanel controlPanel = new JPanel(new FlowLayout());
		
		// ----------Computer Options Sub-Panel----------
		
		// Create a panel that displays computer sorting options
		JPanel computerOptionPanel = new JPanel(new BorderLayout());
//		computerOptionsPanel.setPreferredSize(new Dimension(450, 150));
		computerOptionPanel.setBorder(BorderFactory.createTitledBorder("Computer Options"));
		
		// Add computer options sub-panel to control panel
		controlPanel.add(computerOptionPanel);
		
		// Create a panel that provides input for the compare delay in ms
		JPanel compareDelayPanel = new JPanel();
		
		// Label for compare delay input
		JLabel compareDelayLabel = new JLabel("Compare Delay (ms): ");
		compareDelayLabel.setPreferredSize(new Dimension(120, 20));
		compareDelayPanel.add(compareDelayLabel);
		
		// TextField for compare delay input
		this.compareDelayField = new JTextField("0");
		this.compareDelayField.setPreferredSize(new Dimension(50, 20));
		compareDelayPanel.add(this.compareDelayField);
		
		// Add the panel about the compare delay info to the information panel
		computerOptionPanel.add(compareDelayPanel, BorderLayout.PAGE_START);
		
		// Create a panel that provides input for the compare delay in ms
		JPanel computerSwapDelayPanel = new JPanel();
		
		// Label for compare delay input
		JLabel computerSwapDelayLabel = new JLabel("Swap Delay (ms): ");
		computerSwapDelayLabel.setPreferredSize(new Dimension(120, 20));
		computerSwapDelayPanel.add(computerSwapDelayLabel);
		
		// TextField for compare delay input
		this.computerSwapDelayField = new JTextField("0");
		this.computerSwapDelayField.setPreferredSize(new Dimension(50, 20));
		computerSwapDelayPanel.add(this.computerSwapDelayField);
		
		// Add the panel about the compare delay info to the information panel
		computerOptionPanel.add(computerSwapDelayPanel, BorderLayout.CENTER);
		
		// Create a panel that provides input for the computer's sort algorithm
		JPanel computerSortPanel = new JPanel();
		
		// Label for sort algorithm input
		JLabel computerSortLabel = new JLabel("Sorting Algorithm: ");
		computerSortLabel.setPreferredSize(new Dimension(120, 20));
		computerSortPanel.add(computerSortLabel);
		
		// ComboBox for sort algorithm input
		String[] sorts = { "Bubble Sort", "Selection Sort", "Insertion Sort", "Shell Sort", "Merge Sort" };
		this.computerSortComboBox = new JComboBox<>(sorts);
		// default is "Bubble Sort"
		this.computerSortComboBox.setSelectedIndex(0);
		this.computerSortComboBox.setPreferredSize(new Dimension(100, 20));
		computerSortPanel.add(this.computerSortComboBox);
		
		// Add the computer sort panel to the computer options panel
		computerOptionPanel.add(computerSortPanel, BorderLayout.PAGE_END);
		
		// ----------Deck Options Sub-Panel----------
		
		// Create a panel that displays deck sorting options
		JPanel deckOptionPanel = new JPanel(new BorderLayout());
		deckOptionPanel.setBorder(BorderFactory.createTitledBorder("Deck Options"));
		
		// Add computer options sub-panel to control panel
		controlPanel.add(deckOptionPanel);
		
		// Create a panel that provides input for the number of cards to sort
		JPanel cardNumberPanel = new JPanel();
		
		// Label for compare delay input
		JLabel cardNumberLabel = new JLabel("# of cards to sort: ");
		cardNumberLabel.setPreferredSize(new Dimension(120, 20));
		cardNumberPanel.add(cardNumberLabel);
		
		// TextField for compare delay input
		this.cardNumberField = new JTextField("13");
		this.cardNumberField.setPreferredSize(new Dimension(50, 20));
		cardNumberPanel.add(this.cardNumberField);
		
		// Add the panel about the compare delay info to the information panel
		deckOptionPanel.add(cardNumberPanel, BorderLayout.PAGE_START);
		
		// Create a panel that provides input for whether cards start faceUp
		JPanel cardFaceUpPanel = new JPanel();
		
		// Label for compare delay input
		JLabel cardFaceUpLabel = new JLabel("Cards start: ");
		cardFaceUpLabel.setPreferredSize(new Dimension(80, 20));
		cardFaceUpPanel.add(cardFaceUpLabel);
		
		// TextField for compare delay input
		String[] cardFaceUpArray = { "Face Up", "Face Down" };
		this.cardFaceUpComboBox = new JComboBox<>(cardFaceUpArray);
		this.cardFaceUpComboBox.setPreferredSize(new Dimension(90, 20));
		this.cardFaceUpComboBox.setSelectedIndex(0); // default is face up
		cardFaceUpPanel.add(this.cardFaceUpComboBox);
		
		// Add the panel about the compare delay info to the information panel
		deckOptionPanel.add(cardFaceUpPanel, BorderLayout.CENTER);
		
		// Create a panel that provides input for which card to use
		JPanel deckTypePanel = new JPanel();
		
		// Label for deck type input
		JLabel deckTypeLabel = new JLabel("Deck type: ");
		deckTypeLabel.setPreferredSize(new Dimension(80, 20));
		deckTypePanel.add(deckTypeLabel);
		
		// TextField for compare delay input
		String[] deckTypeArray = { "Uno", "Poker" };
		this.deckTypeComboBox = new JComboBox<>(deckTypeArray);
		this.deckTypeComboBox.setPreferredSize(new Dimension(90, 20));
		this.deckTypeComboBox.setSelectedIndex(0); // default is Uno
		deckTypePanel.add(this.deckTypeComboBox);
		
		// Add the panel about the compare delay info to the information panel
		deckOptionPanel.add(deckTypePanel, BorderLayout.PAGE_END);

		// ----------Player Options Sub-Panel----------
		
		// Create a panel that displays player sorting options
		JPanel playerOptionsPanel = new JPanel(new BorderLayout());
		playerOptionsPanel.setBorder(BorderFactory.createTitledBorder("Player Options"));
		
		// Add computer options sub-panel to control panel
		controlPanel.add(playerOptionsPanel);
		
		// Create a panel that provides input for the max faceUp on the player side
		JPanel maxFaceUpPanel = new JPanel();
		
		// Label for compare delay input
		JLabel maxFaceUpLabel = new JLabel("Max # of cards face-up: ");
		maxFaceUpLabel.setPreferredSize(new Dimension(140, 20));
		maxFaceUpPanel.add(maxFaceUpLabel);
		
		// TextField for compare delay input
		this.maxFaceUpField = new JTextField("2");
		this.maxFaceUpField.setPreferredSize(new Dimension(70, 20));
		maxFaceUpPanel.add(this.maxFaceUpField);
		
		// Add the panel about the compare delay info to the information panel
		playerOptionsPanel.add(maxFaceUpPanel, BorderLayout.PAGE_START);
		
		// Create a panel that provides input for the compare delay in ms
		JPanel playerSwapDelayPanel = new JPanel();
		
		// Label for compare delay input
		JLabel playerSwapDelayLabel = new JLabel("Swap Delay (ms): ");
		playerSwapDelayLabel.setPreferredSize(new Dimension(120, 20));
		playerSwapDelayPanel.add(playerSwapDelayLabel);
		
		// TextField for compare delay input
		this.playerSwapDelayField = new JTextField("0");
		this.playerSwapDelayField.setPreferredSize(new Dimension(50, 20));
		playerSwapDelayPanel.add(this.playerSwapDelayField);
		
		// Add the panel about the compare delay info to the information panel
		playerOptionsPanel.add(playerSwapDelayPanel, BorderLayout.CENTER);
		
		// ----------Action Sub-Panel----------
		
		// Create a panel that displays various sorting/racing actions
		JPanel actionPanel = new JPanel(new GridLayout(0, 1));
		actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
		
		// Add computer options sub-panel to control panel
		controlPanel.add(actionPanel);
		
		// Create tutorial button and add it to the action panel
		JButton dealButton = new JButton("Deal");
		dealButton.setPreferredSize(new Dimension(150, 20));
		actionPanel.add(dealButton);
		// Add Deal Button Listener to demonstrate the computer's sorting method
		dealButton.addActionListener(new DealButtonListener());
		
		// Create demo button and add it to the action panel
		JButton demoButton = new JButton("Demo");
		demoButton.setPreferredSize(new Dimension(150, 20));
		actionPanel.add(demoButton);
		// Add Demo Button Listener to demonstrate the computer's sorting method
		demoButton.addActionListener(new DemoButtonListener());
		
		// Create practice button and add it to the action panel
		JButton practiceButton = new JButton("Practice");
		practiceButton.setPreferredSize(new Dimension(150, 20));
		actionPanel.add(practiceButton);
		// Add Practice Button Listener to demonstrate player sorting!
		practiceButton.addActionListener(new PracticeButtonListener());
		
		// Create race button and add it to the action panel
		JButton raceButton = new JButton("Race!");
		practiceButton.setPreferredSize(new Dimension(150, 20));
		actionPanel.add(raceButton);
		// Add Race Button Listener for, well, racing
		raceButton.addActionListener(new RaceButtonListener());
		
		// TODO Make a high score display for player?
		// TODO Add option to race two computers?
		
		// --------------------MAIN DISPLAY--------------------
		
		// Add all the panels to the main display based on BorderLayout
		panelPane.add(cardPanel, BorderLayout.CENTER);
		panelPane.add(controlPanel, BorderLayout.PAGE_START);
		
		// Add the panelPane to the contentPane of the Frame (Window)
		this.getContentPane().add(panelPane);

		// Set the preferred size and show the main application window
		this.setPreferredSize(new Dimension(1200, 800));
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
			playerCardLabel.setText("Flips: 0     Swaps: 0     Time Elapsed: 0.0 seconds");
			// clear the borders around both the card view panels
			computerCardViewPanel.setBorder(BorderFactory.createEmptyBorder());
			playerCardViewPanel.setBorder(BorderFactory.createEmptyBorder());
			
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
			createAndShowCardLabels(humanPlayer.getPlayerName(), cardLabelMaxDimension);
									
			// re-add player's card's listeners
			for (int i = 0; i < playerCardViewPanel.getComponentCount(); i++) {
				// loop through player's display card panel, check if it's a JLabel
				if (playerCardViewPanel.getComponents()[i] instanceof JLabel) {
					// if it's a JLabel, then add a MouseListener and tell it what display panel it's on, give it access to the other labels, and tell it where it is
					playerCardViewPanel.getComponents()[i].addMouseListener(new CardListener(humanPlayer.getPlayerName(), i));
				}
			}
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
		} else if (playerName.equals(humanPlayer.getPlayerName())) {
			panel = this.playerCardViewPanel;
			player = this.humanPlayer;
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
		} else if (playerName.equals(humanPlayer.getPlayerName())) {
			playerCardLabelArray = cardLabelArray;
		}
		// re-update the panel
		panel.repaint();
		SwingUtilities.updateComponentTreeUI(this);
		return true;
	}
	
	// I got lazy making the race button so I just made more methods so I can use them elsewhere
	public void beginComputerSorting() {
		// comboBox 0 is the sorter select, depending on what is selected
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
	
	// ArrayList to store what cards are selected
	private ArrayList<Integer> computerSelectedIndices = new ArrayList<>();
	private ArrayList<Integer> playerSelectedIndices = new ArrayList<>();
	
	// more random fields I made to add in features I wanted but it's spaghetti
	private int currentFaceUp = 0;
	
	/**
	 * ActionListener for swapping cards
	 */
	private class CardListener implements MouseListener {
		
		// needs states to access each label and keep track of borders
		// playerName kept for easier use of displayCard()
		private JPanel cardViewPanel;
		private ArrayList<Deck.Card> cards;
		private ArrayList<Integer> selectedIndices;
		private JLabel[] cardLabelArray;
		private int cardIndex;
		
		public CardListener() {
			// please no null cases
			this.cardViewPanel = new JPanel();
			this.cards = new ArrayList<>();
			this.selectedIndices = new ArrayList<>();
			this.cardLabelArray = new JLabel[0];
			this.cardIndex = 0;
		}
		
		public CardListener(String playerName, int cardLabelIndex) {
			this();
			// no null cases thnx
			if (playerName.equals(computerPlayer.getPlayerName())) {
				this.cardViewPanel = computerCardViewPanel;
				this.cards = computerPlayer.getCards();
				this.selectedIndices = computerSelectedIndices;
				this.cardLabelArray = computerCardLabelArray;
			} else if (playerName.equals(humanPlayer.getPlayerName())) {
				this.cardViewPanel = playerCardViewPanel;
				this.cards = humanPlayer.getCards();
				this.selectedIndices = playerSelectedIndices;
				this.cardLabelArray = playerCardLabelArray;
			}
			// same here lol
			if (cardLabelIndex > 0) this.cardIndex = cardLabelIndex;
		}
		
		// TODO Configure this CardListener for PvP if I ever want to do that
		
		/**
		 * Action performed operation when clicked
		 * 
		 * @param evt The event that was performed
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			// check if player card interaction is allowed; if not, do nothing
			if (playerCardInteractionOn) {
				// will put on a border when clicked if no border
				// and will turn off the border when clicked if it has a border
				if (selectedIndices.contains(cardIndex)) {
					this.cardLabelArray[this.cardIndex].setBorder(BorderFactory.createEmptyBorder());
					// remove from selected cards
					selectedIndices.clear();
					
					System.out.println(currentFaceUp);
					System.out.println(Integer.parseInt(maxFaceUpField.getText()));
					
					// check that cards are dealt face down
					if (this.cards.get(cardIndex).isFaceUp() || cardFaceUpComboBox.getSelectedItem().equals("Face Up") || currentFaceUp < Integer.parseInt(maxFaceUpField.getText())) {
						// flips actual card
						this.cards.get(cardIndex).flip();
						// if now face down, decrease one from faceUp, otherwise add one
						if (this.cards.get(cardIndex).isFaceUp()) {
							currentFaceUp++;
						} else {
							currentFaceUp--;
						}
						// increment flipCounter
						playerCardSorter.setFlipCounter(playerCardSorter.getFlipCounter()+1);
						// reset the card image to show card front/back
						CardSorter.resetCardLabelImage(this.cards, this.cardLabelArray, this.cardIndex, cardLabelMaxDimension);
					}
				} else {
					// if there is a card already selected, deselect the card, swap both, and reset their card images
					if (selectedIndices.size() > 0) {
						
						// get the cardIndex of the other selected card by finding it in the player's hand
						int otherCardIndex = selectedIndices.get(0);
						
//						// check if the otherCard index is the same as this cardListener's
//						if (otherCardIndex == this.cardIndex) {
//							// if this is the case, the other Card is a duplicate that comes after this card
//							// so you have to get its index from the subList after this card
//							// make sense?
//							otherCardIndex = this.cards.subList(this.cardIndex, this.cards.size()).indexOf(selectedCards.get(0));
//						}
						
						// get the other cardLabel's CardListener
						// turn off the border of the other card
						this.cardLabelArray[otherCardIndex].setBorder(BorderFactory.createEmptyBorder());
						// swap the cards in the arrayList
						Deck.Card[] cardArray = CardSorter.asCardArray(this.cards);
						playerCardSorter.swap(cardArray, this.cardLabelArray, this.cardIndex, otherCardIndex, cardLabelMaxDimension);					
						// Add one to swapCounter, this may be deleted later if I clean up code
						playerCardSorter.setSwapCounter(playerCardSorter.getSwapCounter()+1);
						// add in the time delay to swapping, this may also be deleted later
						// TODO see comments above this
						try {
						    TimeUnit.MILLISECONDS.sleep(Integer.parseInt(playerSwapDelayField.getText()));
						} catch (InterruptedException ie) {
						    Thread.currentThread().interrupt();
						}
						
						CardSorter.toCardArrayListFromArray(this.cards, cardArray);		
						
						// check if cardArray is sorted by comparing it to the sortedPlayerCardArray stored when PracticeButton is hit
						// note: cardArray.equals(sortedPlayerCardArray) is equivalent to cardArray == sortedPlayerCardArray
						// and will always return false, you must use Arrays.equals!!
						if (Arrays.equals(cardArray, sortedPlayerCardArray)) {
							// end player card sorting
							endPlayerSorting();
						}
						
						// clear selected Cards
						selectedIndices.clear();
						
						// note: I would try to find some way to access each label's CardListener but I don't know how to
						// because I don't know how Class<T> works and it's not worth the effor to learn at the moment so
						// we're just going try to avoid it for now
						
					} else {
						this.cardLabelArray[this.cardIndex].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 5));
						// add to selected cards
						selectedIndices.add(this.cardIndex);
					}
					
					// print selectedIndices for good luck
					System.out.println(selectedIndices);
				}
				
				// repaints the panel so the changes to the card properly display
				this.cardLabelArray[this.cardIndex].repaint();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	// Let's add a field here to store the sorted card array that practice button listener creates
	private Deck.Card[] sortedPlayerCardArray = new Deck.Card[0];
	// TODO find a better variable name?
	private boolean playerCardInteractionOn = false;
	
	// let's make this a method so I can use it elsewhere and be lazy
	public void beginPlayerSorting() {
		// clear the border around playerCardViewPanel
		playerCardViewPanel.setBorder(BorderFactory.createEmptyBorder());
		// check if human player's cards is non-empty
		if (!humanPlayer.getCards().isEmpty()) {
			// reset the CardSorter
			playerCardSorter = new CardSorter();
			
			// if not empty, create an array with its contents
			Deck.Card[] sortedCardArray = humanPlayer.getCards().toArray(new Deck.Card[humanPlayer.getCards().size()]);
			// sort the clone
			Arrays.sort(sortedCardArray);
			// store store it as the sortedPlayerCardArray
			sortedPlayerCardArray = sortedCardArray;
			
			// turn playerCardInteraction on
			playerCardInteractionOn = true;
			// record start time in milliseconds
			playerCardSorter.setTimeElapsed(System.currentTimeMillis());
		}
//		// print out the sorted array
//		System.out.println(Arrays.toString(sortedPlayerCardArray));
	}
	
	public void endPlayerSorting() {
		// record the end time by setting the CardSorter's time 
		playerCardSorter.setTimeElapsed(System.currentTimeMillis()-playerCardSorter.getTimeElapsed());
		// turn off player-card interaction
		playerCardInteractionOn = false;
		// get the player's sort time in milliseconds with ~math~
		double timeElapsedInSeconds = ((double) playerCardSorter.getTimeElapsed())/1000.0;
		// print for good measure
		System.out.println(timeElapsedInSeconds);
		// show the time elapsed in seconds 
		playerCardLabel.setText("Flips: "+playerCardSorter.getFlipCounter()+"     Swaps: "+playerCardSorter.getSwapCounter()+"     Time Elapsed: "+timeElapsedInSeconds+" seconds");
		// let's add a green border to signify this
		playerCardViewPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 0), 5));
	}
	
	/**
	 * ActionListener for the practice button
	 */
	private class PracticeButtonListener implements ActionListener {
		/**
		 * Action performed operation. 
		 * 
		 * @param evt The event that was performed
		 */
		@Override
		public void actionPerformed(ActionEvent evt) {
			beginPlayerSorting();
		}
	}
	
	// update the frame?
	public void updateFrame() {
		SwingUtilities.updateComponentTreeUI(this);
	}

	/**
	 * ActionListener for the demo button
	 */
	private class RaceButtonListener implements ActionListener {
		/**
		 * Action performed operation. 
		 * 
		 * @param evt The event that was performed
		 */
		@Override
		public void actionPerformed(ActionEvent evt) {
			// deal button code
			// reset the card labels too
			computerCardLabel.setText("Compares: 0     Swaps: 0     Time Elapsed: 0 milliseconds");
			playerCardLabel.setText("Flips: 0     Swaps: 0     Time Elapsed: 0.0 seconds");
			// clear the borders around both the card view panels
			computerCardViewPanel.setBorder(BorderFactory.createEmptyBorder());
			playerCardViewPanel.setBorder(BorderFactory.createEmptyBorder());
			
			// return all cards from table and players
			table.returnAllCards();
			for (Player player : table.getPlayers()) {
				player.getCards().clear();;
			}
			
			// refill and reshuffle the deck
			table.getDeck().fillDeck();
			table.getDeck().shuffle();
			
			// have table draw the cards
			if (cardFaceUpComboBox.getSelectedItem().equals("Face Up")) {
				table.drawCard(true, Integer.parseInt(cardNumberField.getText()));
				// otherwise draw whatever number of cards is inputted face down
			} else if (cardFaceUpComboBox.getSelectedItem().equals("Face Down")) {
				table.drawCard(false, Integer.parseInt(cardNumberField.getText()));
			}
			
			// have each player clone them
			for (Player player : table.getPlayers()) {
				player.setCards(new ArrayList<Deck.Card>(table.getCards()));
			}
			
			// display both players' cards
			createAndShowCardLabels(computerPlayer.getPlayerName(), cardLabelMaxDimension);
			createAndShowCardLabels(humanPlayer.getPlayerName(), cardLabelMaxDimension);
									
			// re-add player's card's listeners
			for (int i = 0; i < playerCardViewPanel.getComponentCount(); i++) {
				// loop through player's display card panel, check if it's a JLabel
				if (playerCardViewPanel.getComponents()[i] instanceof JLabel) {
					// if it's a JLabel, then add a MouseListener and tell it what display panel it's on, give it access to the other labels, and tell it where it is
					playerCardViewPanel.getComponents()[i].addMouseListener(new CardListener(humanPlayer.getPlayerName(), i));
				}
			}
			
			// begin computer sorting
			beginComputerSorting();			
			// re-displays the label with the new compares and swaps
			computerCardLabel.setText("Compares: "+computerCardSorter.getCompareCounter()+"     Swaps: "+computerCardSorter.getSwapCounter()+"     Time Elapsed: "+computerCardSorter.getTimeElapsed()+" milliseconds");
			// let's add a green border to signify this
			computerCardViewPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 0), 5));
			// repaint to show the new elements
			computerCardLabel.repaint();
			// begin player sorting
			beginPlayerSorting();
		}
	}
}
