import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

/**
 * Card Simulator Deck
 * 
 * @author Daniel Xue 07/27/2021
 * 
 * Sources: UVA CS2110 Lectures,
 * UVA CS2110 HW 5, 6, and 7
 * JDK 11 Documentation
 */

public class Deck implements Comparable<Deck> {
	
	// I realized that I could just instantiate these as empty arrayLists here and still be fine
	protected ArrayList<String> rankList;
	protected ArrayList<String> wildList;
	protected ArrayList<String> suitList;
	protected ArrayList<String> backList;
	// cards instantiated as an empty ArrayList here because every deck starts with empty cards
	protected ArrayList<Card> cards = new ArrayList<>();
	
	public static final String CARD_IMAGE_FOLDER_PATH = "C:\\Users\\danie\\Pictures\\Cards\\Deck\\";

	/**
	 * Helper method that converts arrays of Strings to arrayLists with elements in
	 * the order that they are given
	 * @param <T> - Generic Type
	 * @param T[] items - array of said generic type
	 * @return ArrayList<T> - arrayList of said generic type
	 */
	public static <T> ArrayList<T> asArrayList(T[] items) {
		return new ArrayList<T>(Arrays.asList(items));
	}
	
	/**
	 * Default constructor
	 */
	public Deck() {
		// Empty lists to store all rank, suit, and color options
		this.rankList = new ArrayList<String>();
		this.wildList = new ArrayList<String>();
		this.suitList = new ArrayList<String>();
		this.backList = new ArrayList<String>();
	}

	public Deck(ArrayList<String> rankList, ArrayList<String> suitList) {
		// if rankList is null, initialize it as an empty ArrayList
		if (rankList == null) this.rankList = new ArrayList<>();
		else this.rankList = rankList;
		this.wildList = new ArrayList<>();
		// if suitList is null, initialize it as an empty ArrayList
		if (suitList == null) this.suitList = new ArrayList<>();
		else this.suitList = suitList;
		this.backList = new ArrayList<String>();
	}

	public Deck(ArrayList<String> rankList, ArrayList<String> wildList, ArrayList<String> suitList) {
		// if rankList is null, initialize it as an empty ArrayList
		if (rankList == null) this.rankList = new ArrayList<>();
		else this.rankList = rankList;
		// if the listed wild is a rank in the deck, put it in the wild list
		this.wildList = new ArrayList<String>();
		if (wildList != null) {
			for (String wild : wildList) {
				if (this.rankList.contains(wild))
					this.wildList.add(wild);
			}
		}
		// if suitList is null, initialize it as an empty ArrayList
		if (suitList == null) this.suitList = new ArrayList<>();
		else this.suitList = suitList;
		this.backList = new ArrayList<String>();
	}
	
	public Deck(ArrayList<String> rankList, ArrayList<String> wildList, ArrayList<String> suitList, ArrayList<String> backList) {
		// if rankList is null, initialize it as an empty ArrayList
		if (rankList == null) this.rankList = new ArrayList<>();
		else this.rankList = rankList;
		// if the listed wild is a rank in the deck, put it in the wild list
		this.wildList = new ArrayList<String>();
		if (wildList != null) {
			for (String wild : wildList) {
				if (this.rankList.contains(wild))
					this.wildList.add(wild);
			}
		}
		// if suitList is null, initialize it as an empty ArrayList
		if (suitList == null) this.suitList = new ArrayList<>();
		else this.suitList = suitList;
		// if backList is null, initialize it as an empty ArrayList
		if (backList == null) this.backList = new ArrayList<>();
		else this.backList = backList;
	}

	/**
	 * @return the rankList
	 */
	public ArrayList<String> getRankList() {
		return rankList;
	}

	/**
	 * @param rankList the rankList to set
	 */
	public boolean setRankList(ArrayList<String> rankList) {
		// return false if rankList is null
		if (rankList == null) return false;
		// otherwise set rankList and return true
		this.rankList = rankList;
		// TODO Code to empty deck and/or change composition of deck to reflect new rankList
		
		// Note: the following is done with a while loop to prevent concurrent modification
		// remove wilds not in new rankList from wildList		
		int index = 0;
		// loop until index is equal to wildList's size
		while (index < this.wildList.size()) {
			// check the new rankList contains at the index
			if (!rankList.contains(wildList.get(index))) {
				// if wild is not in new rankList, remove it from wild list
				wildList.remove(index);
			} else {
				// if wild is in new rankList, move index up one
				index++;
			}
		}
		// return true when done
		return true;
	}

	/**
	 * @return the wildList
	 */
	public ArrayList<String> getWildList() {
		return wildList;
	}

	/**
	 * @param wildList the wildList to set
	 */
	public boolean setWildList(ArrayList<String> wildList) {
		// return false if wildList is null
		if (wildList == null) return false;
		// otherwise add wilds in current rankList to form new wildList
		this.wildList = new ArrayList<String>();
		for (String wild : wildList) {
			if (this.rankList.contains(wild)) this.wildList.add(wild);
		}
		// return true when done
		return true;
		
		// TODO Code to empty deck and/or change composition of deck to reflect new wildList
	}

	/**
	 * @return the suitList
	 */
	public ArrayList<String> getSuitList() {
		return suitList;
	}

	/**
	 * @param suitList the suitList to set
	 */
	public boolean setSuitList(ArrayList<String> suitList) {
		// return false if suitList is null
		if (suitList == null) return false;
		// otherwise set suitList and return true
		this.suitList = suitList;
		return true; 
		
		// TODO Code to empty deck and/or change composition of deck to reflect new suitList
	}
	
	/**
	 * @return the backList
	 */
	public ArrayList<String> getBackList() {
		return backList;
	}

	/**
	 * @param backList the backList to set
	 */
	public boolean setBackList(ArrayList<String> backList) {
		// return false if suitList is null
		if (backList == null) return false;
		// otherwise set suitList and return true
		this.backList = backList;
		return true; 
		
		// TODO Code to empty deck and/or change composition of deck to reflect new backList
	}

	/**
	 * @return the cards
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}

	/**
	 * @param cards the cards to set
	 */
	public boolean setCards(ArrayList<Card> cards) {
		// TODO Add check to each card to make sure they have an appropriate rank/suit/back before being added?
		
		// return false if cards is null
		if (cards == null) return false;
		// otherwise set cards and return true
		this.cards = cards;
		return true;
	}

	/**
	 * Create one card with the passed suit, rank, and back and put it in the deck
	 * 
	 * @param String rank
	 * @param String suit
	 * @param String back
	 * @return boolean confirmation
	 */
	public boolean createCard(String rank, String suit, String back) {
		// check if passed fields are null
		if (rank != null && suit != null && back != null) {
			boolean appropriateRank = rank.isBlank() || rankList.contains(rank);
			boolean appropriateSuit = suit.isBlank() || suitList.contains(suit);
			boolean appropriateBack = back.isBlank() || backList.contains(back);
			// check if passed fields are either blanks or in the deck's lists
			if (appropriateRank && appropriateSuit && appropriateBack) {
				cards.add(new Card(rank, suit, back));
				return true;
			}
		}
		// if passed fields are not appropriate, return false
		return false;
	}
	
	/**
	 * Creates card with passed suit and rank and adds it to Deck's cards with default back
	 * @param rank
	 * @param suit
	 * @return boolean confirmation
	 */
	public boolean createCard(String rank, String suit) {
		// check if either rank or suit is null
		if (rank != null && suit != null) {
			boolean appropriateRank = rank.isBlank() || rankList.contains(rank);
			boolean appropriateSuit = suit.isBlank() || suitList.contains(suit);
			// check if passed rank and suit are either blanks or in the deck's list of ranks and suits
			if (appropriateRank && appropriateSuit) {
				// note that this creates a card with a blank back by default
				cards.add(new Card(rank, suit));
				return true;
			}
		}
		// if passed rank or suit is not appropriate, return false
		return false;
	}

	/**
	 * Create some quantity of cards with the passed rank, suit, and back and put it in the deck
	 * 
	 * @param String rank
	 * @param String suit
	 * @param String back
	 * @param int    quantity
	 * @return boolean confirmation
	 */
	public boolean createCard(String rank, String suit, String back, int quantity) {
		// check if passed fields are null
		if (rank != null && suit != null && back != null) {
			boolean appropriateRank = rank.isBlank() || rankList.contains(rank);
			boolean appropriateSuit = suit.isBlank() || suitList.contains(suit);
			boolean appropriateBack = back.isBlank() || backList.contains(back);
			// check if passed fields are either blanks or in the deck's lists
			if (appropriateRank && appropriateSuit && appropriateBack) {
				for (int i = 0; i < quantity; i++) {
					cards.add(new Card(rank, suit, back));
				}
				return true;
			}
		}
		// if passed fields are not appropriate, return false
		return false;
	}
	
	/**
	 * Create some quantity of cards with the passed rank and suit and put it in the deck
	 * 
	 * @param String rank
	 * @param String suit
	 * @param int    quantity
	 * @return boolean confirmation
	 */
	public boolean createCard(String rank, String suit, int quantity) {
		// check if either rank or suit is null
		if (rank != null && suit != null) {
			boolean appropriateRank = rank.isBlank() || rankList.contains(rank);
			boolean appropriateSuit = suit.isBlank() || suitList.contains(suit);
			// check if passed rank and suit are either blanks or in the deck's list of ranks and suits
			if (appropriateRank && appropriateSuit) {
				for (int i = 0; i < quantity; i++) {
					cards.add(new Card(rank, suit));
					// note: creates a card with a blank back by default
				}
				return true;
			}
		}
		// if passed rank or suit is not appropriate, return false
		return false;
	}
	
	/**
	 * Counts number of Cards in Deck's cards with the passed rank or suit
	 * 
	 * @param String field
	 * @return int number of cards
	 */
	public int countCard(String field) {		
		int cardCounter = 0;
		// check if rankOrSuit passed is null, if so return 0
		if (field != null) {
			for (Deck.Card card : cards) {
				if (card.rank.equals(field) || card.suit.equals(field) || card.back.equals(field)) {
					cardCounter++;
				}
			}
		}
		return cardCounter;
	}

	/**
	 * Counts number of Cards in Deck's cards with the passed rank and suit
	 * 
	 * @param String rank
	 * @param String suit
	 * @return int number of cards
	 */
	public int countCard(String rank, String suit) {
		int numCards = 0;
		// check if rank or suit passed is null, if so return 0
		if (rank != null && suit != null) {
			for (Deck.Card card : cards) {
				if (card.rank.equals(rank) && card.suit.equals(suit)) {
					numCards++;
				}
			}
		}
		return numCards;
	}
	
	/**
	 * Counts number of cards in Deck's cards with the passed rank, suit, and back
	 * 
	 * @param String rank
	 * @param String suit
	 * @param String back
	 * @return int number of cards
	 */
	public int countCard(String rank, String suit, String back) {
		int numCards = 0;
		// check if rank or suit passed is null, if so return 0
		if (rank != null && suit != null && back != null) {
			for (Deck.Card card : cards) {
				if (card.rank.equals(rank) && card.suit.equals(suit) && card.back.equals(back)) {
					// this is used instead of Card's equals() because I think it uses less memory??
					// y'know, because you don't have to cast object as a card since you know they're cards
					numCards++;
				}
			}
		}
		return numCards;
	}
		
	/**
	 * Delete every instance of card with the passed field from the deck
	 * 
	 * @param String field
	 * @return boolean confirmation
	 */
	public boolean deleteCard(String field) {	
		// used to be null checks and a boolean checker here but I realized I did that in countCard() already and just decided to call that
		if (countCard(field) <= 0) return false;

		// Note: cards MUST be removed using while loop to prevent concurrent modification
		// it is possible to do the same with a for loop by making a copy of the ArrayList
		// BUT that takes up more memory to do the same thing in about the same amount of lines
		
		// instantiate int to loop over cards
		int i = 0;
		// loop through cards
		while (i < this.cards.size()) {
			// if a card's rank and suit matches that passed, remove it
			if (this.cards.get(i).rank.equals(field) || this.cards.get(i).suit.equals(field) || this.cards.get(i).back.equals(field)) {
				// note: again this is used over Card's equals() to save memory (if it does)
				this.cards.remove(i);
			// otherwise increment counter up
			} else {
				i++;
			}
		}
		// return true if cards were deleted
		return true;
	}

	/**
	 * Delete every instance of card with the passed suit and rank from the deck
	 * 
	 * @param String rank
	 * @param String suit
	 * @return boolean confirmation
	 */
	public boolean deleteCard(String rank, String suit) {	
		// used to be null checks and a boolean checker here but I realized I did that in countCard() already and just decided to call that
		if (countCard(rank, suit) <= 0) return false;

		// Note: cards MUST be removed using while loop to prevent concurrent modification
		// it is possible to do the same with a for loop by making a copy of the ArrayList
		// BUT that takes up more memory to do the same thing in about the same amount of lines
		
		// instantiate int to loop over cards
		int i = 0;
		// loop through cards
		while (i < this.cards.size()) {
			// if a card's rank and suit matches that passed, remove it
			if (this.cards.get(i).rank.equals(rank) && this.cards.get(i).suit.equals(suit)) {
				this.cards.remove(i);
			// otherwise increment counter up
			} else {
				i++;
			}
		}
		// return true if cards were deleted
		return true;
				
		// For loop version:
//		// make a card copy to loop over
//		ArrayList<Card> cardsCopy = new ArrayList<Card>(this.cards);
//		// loop over cards
//		for (Card card : cardsCopy) {
//			// if a card's rank and suit matches that passed, remove it
//			if (card.rank.equals(rank) && card.suit.equals(suit)) {
//				cards.remove(card);
//			}
//		}
	}
	
	/**
	 * Delete every instance of card with the passed suit, rank, and back from the deck
	 * 
	 * @param String rank
	 * @param String suit
	 * @param String back
	 * @return boolean confirmation
	 */
	public boolean deleteCard(String rank, String suit, String back) {
		// there used to be a boolean checker and null checks here
		// but I realized they're already built into countCard so I don't need them B)
		
		// check if there are cards to be deleted, if there aren't return false
		if (countCard(rank, suit, back) <= 0) return false;

		// see above note for why I'm using a while loop here			
		// instantiate counter to loop through cards
		int i = 0;
		// loop over cards
		while (i < this.cards.size()) {
			// if a card's fields matches that passed, remove it
			if (this.cards.get(i).rank.equals(rank) && this.cards.get(i).suit.equals(suit) && this.cards.get(i).back.equals(back)) {
				cards.remove(i);
				// otherwise move the counter up
			} else {
				i++;
			}
		}
		// return true since there were some cards to be deleted
		return true;
	}

	/**
	 * Delete some quantity of instances of cards with the passed suit and rank from the deck
	 * 
	 * @param String rank
	 * @param String suit
	 * @param int    quantity
	 * @return boolean confirmation
	 */
	public boolean deleteCard(String rank, String suit, int quantity) {	
		// note: null checks and boolean confirmation already built into first if-statement
		int cardCount = countCard(rank, suit);
		// check that 1) a nonzero amount of cards in the deck, 2) a nonzero quantity to be deleted
		// and 3) that quantity deleted is less than that in the deck
		if (cardCount > 0 && quantity > 0 && quantity <= cardCount) {
			// instantiate counter for deletions
			int deletionCounter = 0;
			// see above, above note as to why I'm using a while loop here (rip concurrent modification)
			int i = 0;
			// loop over cards if deletionCounter is less than quantity 
			while (deletionCounter < quantity) {
				// if a card has the same rank and suit, remove it and add one to deletions
				if (this.cards.get(i).rank.equals(rank) && this.cards.get(i).suit.equals(suit)) {
					this.cards.remove(i);
					deletionCounter++;
				// otherwise move the index up
				} else {
					i++;
				}
			}
			return true;
		}
		// return false if rank is null, suit is null, no cards with rank or suit are present
		// quantity is 0 or less and/or quantity is greater than cards present in deck
		return false;
	}
	
	/**
	 * Delete some quantity of instances of cards with the passed suit and rank and back from the deck
	 * 
	 * @param String rank
	 * @param String suit
	 * @param int    quantity
	 * @return boolean confirmation
	 */
	public boolean deleteCard(String rank, String suit, String back, int quantity) {
		// note: null checks and boolean confirmation already built into first if-statement
		int cardCount = countCard(rank, suit, back);
		// check that 1) a nonzero amount of cards in the deck, 2) a nonzero quantity to be deleted
		// and 3) that quantity deleted is less than that in the deck
		if (cardCount > 0 && quantity > 0 && quantity <= cardCount) {
			// instantiate counter for deletions
			int deletionCounter = 0;
			// see above, above note as to why I'm using a while loop here (rip concurrent modification)
			int i = 0;
			// loop over cards if deletionCounter is less than quantity 
			while (deletionCounter < quantity) {
				// if a card has the same rank and suit, remove it and add one to deletions
				if (this.cards.get(i).rank.equals(rank) && this.cards.get(i).suit.equals(suit) && this.cards.get(i).back.equals(back)) {
					this.cards.remove(i);
					deletionCounter++;
				// otherwise move the index up
				} else {
					i++;
				}
			}
			return true;
		}
		// return false if rank is null, suit is null, no cards with rank or suit are present
		// quantity is 0 or less and/or quantity is greater than cards present in deck
		return false;
	}

	/**
	 * Fill deck with passed number of regular cards and wild cards with the given back
	 * (note this does not affect cards with a different back)
	 * 
	 * @param int numRegular - number of regular cards
	 * @param int numWild - number of wild cards
	 * @param String back
	 * @return boolean confirmation
	 */
	public boolean fillDeck(int numRegular, int numWild, String back) {
		// if back is null or not in backList, set cardBack to default, otherwise set cardBack to back
		String cardBack;
		if (back == null || !this.backList.contains(back)) cardBack = this.backList.get(0);
		else cardBack = back;
		// create boolean to check cards added
		boolean cardsChanged = false;
		// loop over possible ranks
		for (String rank : rankList) {
			// check if rank is regular or wild card
			if (!wildList.contains(rank)) {
				// if rank is regular, loop over suits
				for (String suit : suitList) {
					// check number of cards with given rank and suit
					int currentRegular = countCard(rank, suit, cardBack);
					// add a card of that rank and suit until
					// the count matches the number passed
					if (currentRegular < numRegular) {
						createCard(rank, suit, cardBack, numRegular - currentRegular);
						cardsChanged = true;
					}
					// likewise remove cards of that rank and suit
					// until the count matches the number passed
					if (currentRegular > numRegular) {
						deleteCard(rank, suit, cardBack, currentRegular - numRegular);
						cardsChanged = true;
					}
				}
			} else {
				int currentWild = countCard(rank, "", cardBack);
				// if rank is wild card, add wild cards of that rank
				// until the count matches the number passed
				if (currentWild < numWild) {
					createCard(rank, "", cardBack, numWild - currentWild);
					cardsChanged = true;
				}
				// likewise remove wild cards until the count
				// matches the number passed
				if (currentWild > numWild) {
					deleteCard(rank, "", cardBack, currentWild - numWild);
					cardsChanged = true;
				}
			}
		}
		// sort cards by natural order
		sort();
		// trim ArrayList size to save space (since ideally you won't need to more than this)
		cards.trimToSize();
		// return confirmation of cards changed
		return cardsChanged;
	}
	
	/**
	 * Fill deck with passed number of regular cards and wild cards with the default back
	 * 
	 * @param int numRegular - number of regular cards
	 * @param int numWild - number of wild cards
	 * @return boolean confirmation
	 */
	public boolean fillDeck(int numRegular, int numWild) {
		return fillDeck(numRegular, numWild, backList.get(0));
	}
	
	/**
	 * Fill deck with passed number of regular cards and no wild cards with passed back
	 * 
	 * @return boolean confirmation
	 */
	public boolean fillDeck(int numRegular, String back) {
		return fillDeck(numRegular, 0, back);
	}

	/**
	 * Fill deck with passed number of regular cards and no wild cards with default back
	 * 
	 * @return boolean confirmation
	 */
	public boolean fillDeck(int numRegular) {
		return fillDeck(numRegular, 0);
	}
	
	/**
	 * Fill deck with 1 of each regular card and two wild cards with passed back
	 * 
	 * @return boolean confirmation
	 */
	public boolean fillDeck(String back) {
		return fillDeck(1, 2, back);
	}

	/**
	 * Fill deck with 1 of each regular card and two wild cards
	 * 
	 * @return boolean confirmation
	 */
	public boolean fillDeck() {
		return fillDeck(1, 2);
	}
	
	/**
	 * Check if deck has cards 
	 * 
	 * @return boolean confirmation
	 */
	public boolean isEmpty() {
		return this.cards.isEmpty();
	}

	/**
	 * Empty deck of all cards
	 * 
	 * @return boolean confirmation
	 */
	public boolean emptyDeck() {
		// return false if cards is already empty
		if (isEmpty()) return false;
		// otherwise, clear the ArrayList and return true
		this.cards.clear();
		return true;
	}

	/**
	 * @return "<cards.size()>-card deck with ranks <rankList> and suits <suitList>"
	 */
	@Override
	public String toString() {
		return cards.size() + "-card deck with " + rankList.size() + " ranks and " + suitList.size() + " suits";
	}

	/**
	 * @param object the object to compare
	 * @return boolean indicating two decks are equal
	 */
	@Override
	public boolean equals(Object object) {
		// checks that object is an instance of Deck
		if (object instanceof Deck) {
			// casts object as instance of deck
			Deck other = (Deck) object;
			// booleans to make sure all ranks, wilds, and suits are the same
			// note: order does not matter, so lists are cast as sets
			// TODO find a more efficient way to compare elements without taking order into account (if there is, anyways)
			boolean sameRanks = new TreeSet<String>(this.rankList).equals(new TreeSet<String>(other.rankList));
			boolean sameWilds = new TreeSet<String>(this.wildList).equals(new TreeSet<String>(other.wildList));
			boolean sameSuits = new TreeSet<String>(this.suitList).equals(new TreeSet<String>(other.suitList));
			boolean sameBacks = new TreeSet<String>(this.backList).equals(new TreeSet<String>(other.backList));
			// returns true if all fields return the same thing
			if (sameRanks && sameWilds && sameSuits && sameBacks) {
				return true;
			}
			// note that decks' cards are not compared since two decks can be the same with a different number of current cards
		}
		// otherwise returns false
		return false;
	}

	/**
	 * Natural ordering: number of ranks (descending), number of suits (descending),
	 * number of wilds (descending), number of backs (descending), number of cards (descending)
	 * 
	 * @param otherDeck the other Deck to compare
	 * @return int indicating the order of the decks
	 */
	@Override
	public int compareTo(Deck otherDeck) {
		// instantiate ints to store comparisons in order
		int firstCompare = otherDeck.rankList.size() - this.rankList.size();
		int secondCompare = otherDeck.suitList.size() - this.suitList.size();
		int thirdCompare = otherDeck.wildList.size() - this.wildList.size();
		int fourthCompare = otherDeck.backList.size() - this.backList.size();
		int fifthCompare = otherDeck.cards.size() - this.cards.size();
		// decision flow to decide what to return
		if (firstCompare != 0) return firstCompare;
		else if (secondCompare != 0) return secondCompare;
		else if (thirdCompare != 0) return thirdCompare;
		else if (fourthCompare != 0) return fourthCompare;
		else return fifthCompare;
	}
	
	/**
	 * Checks if deck is sorted; returns true if empty
	 * 
	 * @return boolean confirmation
	 */
	public boolean isSorted() {
		// initialize boolean for confirmation
		boolean inOrder = true;
		// loop over all cards except the last one
		for (int i = 0; i < cards.size() - 1; i++) {
			// if the card after it actually comes before, the deck is not in order
			if (cards.get(i+1).compareTo(cards.get(i)) < 0) inOrder = false;
		}
		// return final boolean result
		return inOrder;
	}
	
	/**
	 * Sorts Deck's cards by natural order
	 * 
	 * @return boolean confirmation
	 */
	public boolean sort() {
		// if already sorted, return false
		if (isSorted()) return false;
		// otherwise sort and return true
		Collections.sort(cards);
		return true;
	}
	
	/**
	 * Shuffles Deck's cards 
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}

	public class Card implements Comparable<Card> {

		protected String rank;
		protected String suit;
		protected String back;
		// default cards are drawn face up
		protected boolean faceUp = true;
		
		// TODO Decide if default card should be blank card or should be first entry in each respective list

		public Card() {
			// default to blank card, since the other constructors default to blank too
			this.rank = "";
			this.suit = "";
			this.back = "";
		}
		
		public Card(String rank, String suit) {
			// set rank to that passed if it's not null and in Deck's rankList
			if (rank != null && rankList.contains(rank)) this.rank = rank;
			else this.rank = "";
			// set suit to that passed if it's not null and in Deck's suitList
			if (suit != null && suitList.contains(suit) && !wildList.contains(rank)) this.suit = suit;
			else this.suit = "";
			// default the back to blank too, since the other constructors default to blank
			this.back = "";
		}

		public Card(String rank, String suit, String back) {
			// set rank to that passed if it's not null and in Deck's rankList
			if (rank != null && rankList.contains(rank)) this.rank = rank;
			else this.rank = "";
			// set suit to that passed if it's not null and in Deck's suitList
			if (suit != null && suitList.contains(suit) && !wildList.contains(rank)) this.suit = suit;
			else this.suit = "";
			// set back to that passed if it's not null and in Deck's backList
			if (back != null && backList.contains(back)) this.back = back;
			else this.back = "";
		}

		/**
		 * @return the rank
		 */
		public String getRank() {
			return rank;
		}

		/**
		 * @param rank the rank to set
		 */
		public boolean setRank(String rank) {
			// check rank isn't null and that's it's in the rankList
			if (rank != null && rankList.contains(rank)) {
				this.rank = rank;
				return true;
			}
			return false;
		}

		/**
		 * @return the suit
		 */
		public String getSuit() {
			return suit;
		}

		/**
		 * @param suit the suit to set
		 */
		public boolean setSuit(String suit) {
			// check suit isn't null and that it's in the suitList
			if (suit != null && suitList.contains(suit) && !wildList.contains(rank)) {
				this.suit = suit;
				return true;
			}
			return false;
		}
		
		/**
		 * @return the rank
		 */
		public String getBack() {
			return back;
		}

		/**
		 * @param rank the rank to set
		 */
		public boolean setBack(String back) {
			// check back isn't null and that it's in the backList
			if (back != null && backList.contains(back)) {
				this.back = back;
				return true;
			}
			return false;
		}

		/**
		 * @return the faceUp
		 */
		public boolean isFaceUp() {
			return faceUp;
		}

		/**
		 * @param faceUp the faceUp to set
		 */
		public void setFaceUp(boolean faceUp) {
			this.faceUp = faceUp;
		}
		
		/**
		 * Flips the card's faceUp to false if true and vice verse
		 */
		public void flip() {
			if (this.faceUp) this.faceUp = false;
			else this.faceUp = true;
		}

		/**
		 * @return "(<rank>, <suit>)" e.g. "(Ace, Spades)" if both not empty,
		 *         otherwise just "(non-empty field)" or "(Blank)"
		 */
		@Override
		public String toString() {
			if (!this.faceUp) {
				if (back.isBlank()) return "Blank Back";
				else return back + " Back";
			}
			if (!rank.isBlank() && !suit.isBlank()) return "(" + rank + ", " + suit + ")";
			if (!rank.isBlank() || !suit.isBlank()) return "(" + rank + suit + ")";
			return "(Blank)";
		}

		/**
		 * @param object the object to compare
		 * @return boolean indicating two cards are equal
		 */
		@Override
		public boolean equals(Object object) {
			// checks that object is an instance of Deck.Card
			if (object instanceof Deck.Card) {
				// casts object as card
				Deck.Card other = (Deck.Card) object;
				// booleans to compare all fields
				boolean sameRank = (this.rank.equals(other.rank));
				boolean sameSuit = (this.suit.equals(other.suit));
				boolean sameBack = (this.back.equals(other.back));
				// returns true if all fields return the same thing
				if (sameRank && sameSuit && sameBack) return true;
			}
			// otherwise returns false
			return false;
		}

		/**
		 * Natural ordering: rank index (ascending), suit index (ascending)
		 * 
		 * @param otherCard the other Card to compare
		 * @return int indicating the order of the cards
		 */
		@Override
		public int compareTo(Card otherCard) {
			// instantiate ints to store comparisons in order
			int firstCompare = rankList.indexOf(this.rank) - rankList.indexOf(otherCard.rank);
			int secondCompare = suitList.indexOf(this.suit) - suitList.indexOf(otherCard.suit);
			int thirdCompare = backList.indexOf(this.back) - backList.indexOf(otherCard.back); 
			// decision flow to decide what to return
			if (firstCompare != 0) return firstCompare;
			else if (secondCompare != 0) return secondCompare;
			else return thirdCompare;
		}
		
		/**
		 * Returns the absolute path of the card's imageFileName 
		 * @return an empty String
		 */
		public String getImageFilePath() {
			// TODO implement default case with actual file that exists (see MemeMagic code for adding text to BufferedImage)
			return CARD_IMAGE_FOLDER_PATH + toString();
		}
		
		/**
		 * Returns a buffered image representation of the card scaled to the passed maxDimension
		 * @param maxDimension of BufferedImage
		 * @return a blank BufferedImage
		 */
		public BufferedImage getImage(int maxDimension) {
			// TODO implement default case
			return new BufferedImage(maxDimension, maxDimension, BufferedImage.TYPE_INT_ARGB);
		}
	}
}
