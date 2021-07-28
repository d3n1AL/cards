import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Card Simulator Poker Deck
 * 
 * @author Daniel Xue 07/27/2021
 * 
 * Sources: UVA CS2110 Lectures,
 * UVA CS2110 HW 5, 6, and 7 (ImageIO and rescaling code taken from HW 7)
 * JDK 11 Documentation
 */

public class PokerDeck extends Deck {
	
	// TODO make some different configurations for the order of ranks
	// for example if we're going by caps order we should have joker at the end
	// if we're assigning numbers to each rank, ace should be considered "one" and moved after Joker in the beginning
	// this hybrid thing I'm doing is kind of confusing and weirdly inconsistent
	public static final String[] RANKS = { "Joker", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace" };
	public static final String[] WILDS = { "Joker" };
	public static final String[] SUITS = { "Spades", "Clubs", "Diamonds", "Hearts" };
	public static final String[] BACKS = { "Gray", "Red", "Yellow", "Green", "Blue", "Purple" };
	
	public static final String CARD_IMAGE_FOLDER_PATH = "C:\\Users\\danie\\Pictures\\Cards\\Poker Deck\\";
	
	private String jokerColor = "Black";
	
	/**
	 * Default constructor for Poker Deck with fun arrays and stuff
	 */
	public PokerDeck() {
		// Lists to store all rank, suit, and color options
		this.rankList = Deck.asArrayList(RANKS);
		this.wildList = Deck.asArrayList(WILDS);
		this.suitList = Deck.asArrayList(SUITS);
		this.backList = Deck.asArrayList(BACKS);
		// note that cards is instantiated as an empty ArrayList<Deck.Card> in Deck already
	}
	
	/**
	 * @return the jokerColor
	 */
	public String getJokerColor() {
		return jokerColor;
	}

	/**
	 * Flips jokerColor from 0 to 1 and 1 to 0
	 */
	public void switchJokerColor() {
		if (this.jokerColor == "Black") this.jokerColor = "Red";
		else this.jokerColor = "Black";
	}

	/**
	 * PokerDeck has no overloaded constructor because it is assumed
	 * that you just want a poker deck
	 */
	
	public class PokerCard extends Card {
		
		/**
		 * Default constructor
		 */
		public PokerCard() {
			this.rank = "Ace";
			this.suit = "Spades";
			this.back = "Gray";
			// face up is defaulted to true in Card already
		}
		
		public PokerCard(String rank, String suit) {
			super(rank, suit);
		}

		public PokerCard(String rank, String suit, String back) {
			super(rank, suit, back);
		}
		
		/**
		 * @return "<rank> of <suit>" e.g. "Ace of Spades" if both not empty,
		 *         otherwise just "non-empty field" or "Blank Card"
		 */
		@Override
		public String toString() {
			if (!this.faceUp) {
				if (back.isBlank()) return "Blank Back";
				else return back + " Back";
			}
			if (!rank.isBlank() && !suit.isBlank()) return rank + " of " + suit;
			if (!rank.isBlank() || !suit.isBlank()) return rank + suit;
			return "Blank Card";
		}
		
		/**
		 * Returns the absolute path of the card's image file
		 * @return String absolute path
		 */
		@Override 
		public String getImageFilePath() {
			// it's just the absolute path plus the card's toString
			String cardImageFilePath = CARD_IMAGE_FOLDER_PATH + toString();	
			// if the card is a joker, add another a part to figure out what color joker it is
			if (this.faceUp && this.rank.equals("Joker")) {
				cardImageFilePath += " (" + jokerColor + ")";
				switchJokerColor();
			}
			// add .png! it's important
			cardImageFilePath += ".png";
			// and finally return it 
			return cardImageFilePath;
		}
		
		/**
		 * Returns a buffered image representation of the card scaled to the passed maxDimension
		 * 
		 * Note: the following code was taken from UVA CS2110 HW 7 in Spring of 2021
		 * 
		 * @param int maxDimension of BufferedImage
		 * @return scaled BufferedImage of the card
		 */
		@Override
		public BufferedImage getImage(int maxDimension) {	
			// get a BufferedImage using ImageIO
			try {
				BufferedImage cardImage = ImageIO.read(new File(getImageFilePath()));
				
				// Scale the image using code from UVA CS2110 HW 7
				// Determine the height and width of the original image
				int origWidth = cardImage.getWidth();
				int origHeight = cardImage.getHeight();

				// Scale the image to the maximum height and width
				int width = 0;
				int height = 0;
				if (origWidth > origHeight) {
					width = maxDimension;
					height = (int) (origHeight * ((double) width / origWidth));
				} else {
					height = maxDimension;
					width = (int) (origWidth * ((double) height / origHeight));
				}
				
				// scale cardImage and draw it onto a bufferedImage
				Image tmp = cardImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				Graphics2D gd = scaled.createGraphics();
				gd.drawImage(tmp, 0, 0, null);
				gd.dispose();
					
				// return scaled BufferedImage
				return scaled;
				
			} catch (IOException e) {
				// if fileName not found, indicate so and return null
				System.out.println("Image for card inputted not found!");
				e.printStackTrace();
				return null;
			}	
		}
	}
	
	// all previous createCard() methods must be overridden with the new PokerCard() constructor
	
	/**
	 * Create one card with the passed suit, rank, and back and put it in the deck
	 * 
	 * @param String rank
	 * @param String suit
	 * @param String back
	 * @return boolean confirmation
	 */
	@Override
	public boolean createCard(String rank, String suit, String back) {
		// check if either rank or suit is null
		if (rank != null && suit != null && back != null) {
			boolean appropriateRank = rank.isBlank() || rankList.contains(rank);
			boolean appropriateSuit = suit.isBlank() || suitList.contains(suit);
			boolean appropriateBack = back.isBlank() || backList.contains(back);
			// check if passed rank and suit are either blanks or in the deck's list of
			// ranks and suits
			if (appropriateRank && appropriateSuit && appropriateBack) {
				this.cards.add(new PokerCard(rank, suit, back));
				return true;
			}
		}
		// if passed rank or suit is not appropriate, return false
		return false;
	}
	
	/**
	 * Creates card with passed suit and rank and adds it to Deck's cards with default back
	 * @param rank
	 * @param suit
	 * @return
	 */
	@Override
	public boolean createCard(String rank, String suit) {
		// check if either rank or suit is null
		if (rank != null && suit != null) {
			boolean appropriateRank = rank.isBlank() || rankList.contains(rank);
			boolean appropriateSuit = suit.isBlank() || suitList.contains(suit);
			// check if passed rank and suit are either blanks or in the deck's list of
			// ranks and suits
			if (appropriateRank && appropriateSuit) {
				this.cards.add(new PokerCard(rank, suit));
				return true;
			}
		}
		// if passed rank or suit is not appropriate, return false
		return false;
	}

	/**
	 * Create some quantity of cards with the passed rank, suit, and back and put it in the
	 * deck
	 * 
	 * @param String rank
	 * @param String suit
	 * @param String back
	 * @param int    quantity
	 * @return boolean confirmation
	 */
	@Override
	public boolean createCard(String rank, String suit, String back, int quantity) {
		// check if either rank or suit is null
		if (rank != null && suit != null && back != null) {
			boolean appropriateRank = rank.isBlank() || rankList.contains(rank);
			boolean appropriateSuit = suit.isBlank() || suitList.contains(suit);
			boolean appropriateBack = back.isBlank() || backList.contains(back);
			// check if passed rank and suit are either blanks or in the deck's list of
			// ranks and suits
			if (appropriateRank && appropriateSuit && appropriateBack) {
				for (int i = 0; i < quantity; i++) {
					cards.add(new PokerCard(rank, suit, back));
				}
				return true;
			}
		}
		// if passed rank or suit is not appropriate, return false
		return false;
	}
	
	/**
	 * Create some quantity of cards with the passed rank and suit and put it in the
	 * deck
	 * 
	 * @param String rank
	 * @param String suit
	 * @param int    quantity
	 * @return boolean confirmation
	 */	
	@Override
	public boolean createCard(String rank, String suit, int quantity) {
		// check if either rank or suit is null
		if (rank != null && suit != null) {
			boolean appropriateRank = rank.isBlank() || rankList.contains(rank);
			boolean appropriateSuit = suit.isBlank() || suitList.contains(suit);
			// check if passed rank and suit are either blanks or in the deck's list of
			// ranks and suits
			if (appropriateRank && appropriateSuit) {
				for (int i = 0; i < quantity; i++) {
					cards.add(new PokerCard(rank, suit));
				}
				return true;
			}
		}
		// if passed rank or suit is not appropriate, return false
		return false;
	}
	
	/**
	 * @return "<cards.size()>-card poker deck"
	 */
	@Override
	public String toString() {
		return cards.size() + "-card poker deck";
	}
}
