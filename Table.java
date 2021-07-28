import java.util.ArrayList;

/**
 * Card Simulator Table
 * 
 * @author Daniel Xue 07/27/2021
 * 
 * Sources: UVA CS2110 Lectures,
 * UVA CS2110 HW 5 and 6
 * JDK 11 Documentation
 */

public class Table implements Comparable<Table> {
	
	private Deck deck;
	// default both of these ArrayLists as empty lists
	private ArrayList<Deck.Card> cards = new ArrayList<>();
	private ArrayList<Player> players = new ArrayList<>();

	// TODO figure out a way to store presets in each Game/JFrame so you can load everything automatically with 
	// the Table(Game game) overloaded constructor private Game game;
	
	public Table() {
		// initialize each field with its defaults
		this.deck = new Deck();
	}
	
	public Table(Deck deck) {
		// initialize deck with inputed parameters
		this.deck = deck;
	}
	
	/**
	 * @return the deck
	 */
	public Deck getDeck() {
		return deck;
	}

	/**
	 * @param deck the deck to set
	 */
	public void setDeck(Deck deck) {
		// return all of table's cards to deck
		returnAllCards();
		// return all of the player's cards to the deck
		for (Player player : players) {
			player.returnAllCards();
		}
		// set deck 
		this.deck = deck;
	}

	/**
	 * @return the cardsOnTable
	 */
	public ArrayList<Deck.Card> getCards() {
		return cards;
	}

	/**
	 * @param cardsOnTable the cardsOnTable to set
	 */
	public boolean setCards(ArrayList<Deck.Card> cards) {
		// return false if cards is null
		if (cards == null) return false;
		// otherwise set cards and return true
		this.cards = cards;
		return true;
	}

	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public boolean setPlayers(ArrayList<Player> players) {
		// return false if players is null
		if (players == null) return false;
		// otherwise set players and return true
		this.players = players;
		return true;
	}
	
	/**
	 * @return "<# of players>-player table with a <deck>"
	 */
	@Override
	public String toString() {
		if (hasDeck()) return players.size() + "-player table with a " + deck.toString();
		return players.size() + "-player table with no deck";
	}
	
	/**
	 * @param object 
	 * @return true if object is instance of table and has same deck
	 */
	@Override
	public boolean equals(Object object) {
		// checks that object is an instance of Table
		if (object instanceof Table) {
			// casts object as a Table
			Table other = (Table) object;
			// note that Table's cards and players will not affect its equality
			boolean sameDeck = false;
			if (this.hasDeck() && other.hasDeck()) {
				sameDeck = this.deck.equals(other.deck);
			} else if (!this.hasDeck() && !other.hasDeck()) {
				sameDeck = true;
			}
			// returns true if all fields return the same thing
			if (sameDeck) return true;
		}
		// otherwise returns false
		return false;	
	}
	
	/**
	 * Natural ordering: deck (natural ordering), # of players (descending)
	 * @param otherTable
	 * @return int ordering
	 */
	@Override
	public int compareTo(Table otherTable) {
		// instantiate ints to store comparisons in order
		// because you can't call compareTo on null, you have to do this 
		// icky if tree to get the right int to return
		int firstCompare = 0;
		// if both have decks, call compareTo on them
		if (this.hasDeck() && otherTable.hasDeck()) {
			firstCompare = this.deck.compareTo(otherTable.deck);
		// sort this one with a deck before the other without one
		} else if (this.hasDeck()) {
			return -1;
		// sort this one without a deck after the other with one
		} else if (otherTable.hasDeck()) {
			return 1;
		}
		// otherwise keep it 0 if they both don't have one
		int secondCompare = otherTable.players.size() - this.players.size();
		// decision flow to decide what to return
		if (firstCompare != 0) return firstCompare;
		else return secondCompare;
	}
	
	/**
	 * Returns true if Table has a deck
	 * @return boolean confirmation
	 */
	public boolean hasDeck() {
		return this.deck != null;
	}
	
	/**
	 * Draws first quantity of cards (starting @ index 0) from the table's deck and places it in cards face up or face down
	 * @param boolean faceUp
	 * @param int quantity
	 * @return boolean confirmation
	 */
	public boolean drawCard(boolean faceUp, int quantity) {
		// return false if table has no deck or deck has fewer cards than the quantity passed
		if (!hasDeck() || deck.getCards().size() < quantity) return false;
		// loop for each card drawn
		for (int i = 0; i < quantity; i++) {
			// remove first card from deck
			Deck.Card cardDrawn = this.deck.getCards().remove(0);
			// set the card's faceUp with that passed
			cardDrawn.setFaceUp(faceUp);
			// add it to the Table's cards
			this.cards.add(cardDrawn);
		}
		// return true when done
		return true;
	}
	
	/**
	 * Draws first card (@ index 0) from table's deck and places it face up or face down
	 * @param boolean faceUp
	 * @return boolean confirmation
	 */
	public boolean drawCard(boolean faceUp) {
		return drawCard(faceUp, 1);
	}
	
	/**
	 * Draws a card from the table's deck and places it face down in cards
	 * @return boolean confirmation
	 */
	public boolean drawCard() {
		return drawCard(false, 1);
	}
	
	/**
	 * Removes card from the table's cards at the given index and places it at the end of its deck
	 * @param int index
	 * @return boolean confirmation
	 */
	public boolean returnCard(int index) {
		// return false if table has no deck or index is not in bounds
		if (!hasDeck() || index < 0 || index >= this.cards.size()) return false;
		// remove card from table at the index 
		Deck.Card cardReturned = this.cards.remove(index);
		// set the card faceDown on the table
		cardReturned.setFaceUp(false);
		// add it to the end of deck's cards and return true
		this.deck.getCards().add(cardReturned);		
		return true;
	}
	
	/**
	 * Clears the table's cards and places them at the end of the deck
	 * @return boolean confirmation
	 */
	public boolean returnAllCards() {
		// return false if table has no deck or cards is already empty
		if (!hasDeck() || this.cards.isEmpty()) return false;
		// add each of the table's cards face-down to the deck
		for (Deck.Card card : this.cards) {
			card.setFaceUp(false);
			this.deck.getCards().add(card);
		}
		// clear the table's cards and return true
		this.cards.clear();
		return true;
	}
}
