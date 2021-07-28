import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Card Simulator Uno Deck
 * 
 * @author Daniel Xue 07/27/2021
 * 
 * Sources: UVA CS2110 Lectures,
 * UVA CS2110 HW 5, 6, and 7 (ImageIO and rescaling code taken from HW 7)
 * JDK 11 Documentation
 */

public class UnoDeck extends Deck {
	
	public static final String[] RANKS = { "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Skip", "Reverse", "Draw Two", "Wild", "Wild Draw Four" };
	public static final String[] WILDS = { "Wild", "Wild Draw Four" };
	public static final String[] SUITS = { "Red", "Yellow", "Green", "Blue" };
	public static final String[] BACKS = { "2019", "2013", "2001", "1971" };
	
	public static final String CARD_IMAGE_FOLDER_PATH = "Uno Deck/";
		
	/**
	 * Default constructor for Uno Deck with fun arrays and stuff
	 */
	public UnoDeck() {
		// Lists to store all rank, suit, and color options
		this.rankList = Deck.asArrayList(RANKS);
		this.wildList = Deck.asArrayList(WILDS);
		this.suitList = Deck.asArrayList(SUITS);
		this.backList = Deck.asArrayList(BACKS);
		// note that cards is instantiated as an empty ArrayList<Deck.Card> in Deck already
	}

	/**
	 * UnoDeck has no overloaded constructor because it is assumed
	 * that you just want a poker deck
	 */
	
	public class UnoCard extends Card {
		
		/**
		 * Default constructor
		 */
		public UnoCard() {
			this.rank = "Zero";
			this.suit = "Red";
			this.back = "2019";
			// face up is defaulted to true in Card already
		}
		
		public UnoCard(String rank, String suit) {
			super(rank, suit);
		}

		public UnoCard(String rank, String suit, String back) {
			super(rank, suit, back);
		}
		
		/**
		 * @return "<suit> <rank>" e.g. "Green Zero" if both not empty,
		 *         otherwise just "non-empty field" or "Blank Card"
		 */
		@Override
		public String toString() {
			if (!this.faceUp) {
				if (back.isBlank()) return "Blank Back";
				else return back + " Back";
			}
			if (!rank.isBlank() && !suit.isBlank()) return suit + " " + rank;
			if (!rank.isBlank() || !suit.isBlank()) return suit + rank;
			return "Blank Card";
		}
		
		/**
		 * Returns the absolute path of the card's image file
		 * @return String absolute path
		 */
		@Override 
		public String getImageFilePath() {
			// it's just the absolute path plus the card's toString and add .png! it's important
			String cardImageFilePath = CARD_IMAGE_FOLDER_PATH + toString() + ".png";	
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
				
				// Scale the image using code from UVA CS2110 HW 7 in Spring 2021
				
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
				this.cards.add(new UnoCard(rank, suit, back));
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
				this.cards.add(new UnoCard(rank, suit));
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
					cards.add(new UnoCard(rank, suit, back));
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
					cards.add(new UnoCard(rank, suit));
				}
				return true;
			}
		}
		// if passed rank or suit is not appropriate, return false
		return false;
	}
	
	// Override these fillDeck()s so we can have only one zero in each suit
	
	/**
	 * Fill deck with 1 of each regular card and two wild cards with passed back
	 * 
	 * @return boolean confirmation
	 */
	@Override
	public boolean fillDeck(String back) {
		// check that back is not null or in backList
		if (back == null || !this.backList.contains(back)) return false;
		// instantiate boolean checker
		boolean needCardChange = false;
		// loop through ranks
		for (String rank : this.rankList) {
			switch (rank) {
			// if rank is wild, check that there are four of each wild
			case "Wild":
			case "Wild Draw Four":
				if (countCard(rank, "", back) != 4) needCardChange = true;
				break;
			// if rank is zero, check that there is one for each suit
			case "Zero":
				for (String suit : this.suitList) {
					if (countCard(rank, suit, back) != 1) needCardChange = true;
				}
				break;
			// if rank is not wild or zero, check there is two for each suit
			default:
				for (String suit : this.suitList) {
					if (countCard(rank, suit, back) != 2) needCardChange = true;
				}
			}			
		}
		// if cardChange is needed
		if (needCardChange) {
			// fill deck with two of each regular and 4 of each card
			fillDeck(2, 4, back);
			// and delete one zero from each suit
			for (String suit: this.suitList) {
				deleteCard("Zero", suit, 1);
			}
			// and return true
			return true;
		}
		// otherwise return false
		return false;
	}

	/**
	 * Fill deck with 1 of each regular card and two wild cards
	 * 
	 * @return boolean confirmation
	 */
	@Override
	public boolean fillDeck() {
		// call prior fill deck with first back in deck
		return fillDeck(this.backList.get(0));
	}
	
	/**
	 * @return "<cards.size()>-card uno deck"
	 */
	@Override
	public String toString() {
		return cards.size() + "-card uno deck";
	}
}
