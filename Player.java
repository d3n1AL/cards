import java.util.ArrayList;

/**
 * Card Simulator Player
 * 
 * @author Daniel Xue 07/27/2021
 * 
 * Sources: UVA CS2110 Lectures,
 * UVA CS2110 HW 5 and 6
 * JDK 11 Documentation
 */

public class Player implements Comparable<Player> {
	
	private String playerName;
	private Table playerTable;
	// gamesPlayed and gamesWon default to 0
	private int gamesPlayed = 0;
	private int gamesWon = 0;
	// cards always starts as an empty ArrayList
	private ArrayList<Deck.Card> cards = new ArrayList<>();
	
	public Player() {
		this.playerName = "";
		// playerTable is left null
	}
	
	public Player(String playerName) {
		// if inputted player name is null, make it an empty String
		if (playerName == null) this.playerName = "";
		// otherwise set it as normal
		else this.playerName = playerName;
		// playerTable is left null
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName the playerName to set
	 */
	public boolean setPlayerName(String playerName) {
		// return false if playerName is null
		if (playerName == null) return false;
		// otherwise set playerName and return true
		this.playerName = playerName;
		return true;
	}

	/**
	 * @return the playerTable
	 */
	public Table getPlayerTable() {
		return playerTable;
	}

	/**
	 * @param playerTable the playerTable to set
	 */
	public void setPlayerTable(Table playerTable) {
		this.playerTable = playerTable;
	}

	/**
	 * @return the gamesPlayed
	 */
	public int getGamesPlayed() {
		return gamesPlayed;
	}

	/**
	 * @param gamesPlayed the gamesPlayed to set
	 */
	public boolean setGamesPlayed(int gamesPlayed) {
		// return false if gamesPlayed is less than 0 or its gamesWon
		if (gamesPlayed < 0 || gamesPlayed < this.gamesWon) return false;
		// otherwise set gamesPlayed to that passed and return true
		this.gamesPlayed = gamesPlayed;
		return true;
	}

	/**
	 * @return the gamesWon
	 */
	public int getGamesWon() {
		return gamesWon;
	}

	/**
	 * @param gamesWon the gamesWon to set
	 */
	public boolean setGamesWon(int gamesWon) {
		// return false if gamesWon is less than 0 or greater than gamesPlayed
		if (gamesWon < 0 || gamesWon > this.gamesPlayed) return false;
		// otherwise set gamesWon to that passed and return true
		this.gamesWon = gamesWon;
		return true;
	}

	/**
	 * @return the playerHand
	 */
	public ArrayList<Deck.Card> getCards() {
		return cards;
	}

	/**
	 * @param playerHand the playerHand to set
	 */
	public boolean setCards(ArrayList<Deck.Card> cards) {
		// return false if null is passed
		if (cards == null) return false;
		// otherwise set cards and return true
		this.cards = cards;
		return true;
	}
	
	/**
	 * Returns true if the player is at a table
	 * @return boolean confirmation
	 */
	public boolean atTable() {
		return this.playerTable != null;
	}
	
	/**
	 * Returns true if the player is at a table with a deck
	 * @return boolean confirmation
	 */
	public boolean atTableWithDeck() {
		return atTable() && this.playerTable.hasDeck();
	}
	
	/** 
	 * Adds the player to a table with some deck
	 * @param table
	 * @return boolean confirmation
	 */
	public boolean joinTable(Table table) {
		// check if player is already at table
		if (atTable()) return false;
		// set playerTable to table passed
		setPlayerTable(table);
		// add player to table's players ArrayList and return true
		table.getPlayers().add(this);
		return true;
	}
	
	/**
	 * Removes player from current table
	 * @param table
	 * @return boolean confirmation
	 */
	public boolean leaveTable() {
		// check if player is not at table
		if (!atTable()) return false;
		// TODO add line here about returning all cards + test it
		returnAllCards();
		// remove player from table's players ArrayList
		this.playerTable.getPlayers().remove(this);
		// set playerTable to null and return true
		setPlayerTable(null);
		return true;
	}
	
	/**
	 * Draws first quantity of cards face down (starting at index 0) from table's deck and places it in cards
	 * @param boolean faceUp
	 * @param int quantity
	 * @return boolean confirmation
	 */
	public boolean drawCard(boolean faceUp, int quantity) {
		// check if player is at table with deck and quantity is less than or equal to deck's size
		if (!atTableWithDeck() || this.playerTable.getDeck().getCards().size() < quantity) return false;
		// loop for each card drawn
		for (int i = 0; i < quantity; i++) {
			// remove first card from deck
			Deck.Card cardDrawn = this.playerTable.getDeck().getCards().remove(0);
			// set the card to be not faceUp
			cardDrawn.setFaceUp(faceUp);
			// add card to Player's cards
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
	 * Draws first card face down from table's deck (@ index 0) and places it in cards
	 * @return boolean confirmation
	 */
	public boolean drawCard() {
		return drawCard(true, 1);
	}
	
	/**
	 * Removes the card at the given index from Player's cards and put it at the end of the deck
	 * @param int index
	 * @return boolean confirmation
	 */
	public boolean returnCard(int index) {
		// check if player is at table with deck, and index is in bounds
		if (!atTableWithDeck() || index < 0 || index >= this.cards.size()) return false;
		// remove card from table at index
		Deck.Card cardReturned = this.cards.remove(index);
		// set the card to be face down
		cardReturned.setFaceUp(false);
		// add the card to the end of deck's cards and return true
		this.playerTable.getDeck().getCards().add(cardReturned);
		return true;
	}
	
	/**
	 * Removes all cards from Player's cards and puts them at the end of the deck
	 * @return boolean confirmation
	 */
	public boolean returnAllCards() {
		// check if player is at table with deck, and that their card is not empty
		if (!atTableWithDeck() || this.cards.isEmpty()) return false;
		// add each of the player's cards face-down from the player to the deck 
		for (Deck.Card card : this.cards) {
			card.setFaceUp(false);
			this.playerTable.getDeck().getCards().add(card);
		}
		// clear player's cards and return true
		this.cards.clear();
		return true;
	}
	
	/**
	 * @return "<playerName> at <playerTable> (<gamesPlayed>, <gamesWon>)"
	 */
	@Override
	public String toString() {
		if (atTable() && !playerName.isBlank()) return playerName + " at a " + playerTable.toString() + " (" + gamesPlayed + ", " + gamesWon + ")";
		else if (atTable()) return "[no name] at a " + playerTable.toString() + " (" + gamesPlayed + ", " + gamesWon + ")";
		else if (!playerName.isBlank()) return playerName + " (" + gamesPlayed + ", " + gamesWon + ")";
		else return "[no name] (" + gamesPlayed + ", " + gamesWon + ")";
	}
	
	/**
	 * @param object 
	 * @return true if object is instance of player and has same name
	 */
	@Override
	public boolean equals(Object object) {
		// checks that object is an instance of Player
		if (object instanceof Player) {
			// casts object as a Table
			Player other = (Player) object;
			// note that Player's playerTable, cards, gamesPlayed, and gamesWon will not affect its equality
			boolean sameName = this.playerName.equals(other.playerName);
			// returns true if all fields return the same thing
			if (sameName) return true;
		}
		// otherwise returns false
		return false;		
	}
	
	/**
	 * Natural ordering: name (ascending), playerTable (natural ordering), gamesPlayed (descending), gamesWon (descending), # of cards (descending)
	 * @param otherPlayer
	 * @return int ordering
	 */
	@Override
	public int compareTo(Player otherPlayer) {
		// instantiate ints to store comparisons in order
		int firstCompare = this.playerName.compareTo(otherPlayer.playerName);
		// since playerTable can be null, we need to do something weird:
		// first initialize secondCompare as 0
		int secondCompare = 0;
		// case: neither is null --> use regular compareTo
		if (this.atTable() && otherPlayer.atTable()) {
			secondCompare = this.playerTable.compareTo(otherPlayer.playerTable);
		// if this has a table, sort it ahead of the other
		} else if (this.atTable()) {
			return -1;
		// if the other has a table, sort it after the other
		} else if (otherPlayer.atTable()) {
			return 1;
		}
		// if both do not, sort them together
		int thirdCompare = otherPlayer.gamesPlayed - this.gamesPlayed;
		int fourthCompare = otherPlayer.gamesWon - this.gamesWon;
		int fifthCompare = otherPlayer.cards.size() - this.cards.size();
		// decision flow to decide what to return
		if (firstCompare != 0) return firstCompare;
		else if (secondCompare != 0) return secondCompare;	
		else if (thirdCompare != 0) return thirdCompare;
		else if (fourthCompare != 0) return fourthCompare;
		else return fifthCompare;
	}
}
