import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Card Simulator Player Test
 * 
 * @author Daniel Xue 07/27/2021
 * 
 * Sources: UVA CS2110 Lectures,
 * UVA CS2110 HW 5 and 6
 * JDK 11 Documentation
 */

public class PlayerTest {

	@Test
	public void defaultConstructorTest() {
		// initialize new Player and check fields match defaults
		Player player = new Player();
		assertEquals("Player's default playerName is not an empty String", "", player.getPlayerName());
		assertEquals("Player's playerTable should not be instantiated in the constructor", null,
				player.getPlayerTable());
		assertEquals("Player's gamesPlayed should default to 0", 0, player.getGamesPlayed());
		assertEquals("Player's gamesPlayed should default to 0", 0, player.getGamesWon());
		assertEquals("Player's cards should default to an empty ArrayList", new ArrayList<Deck.Card>(),
				player.getCards());
	}

	@Test
	public void overloadedConstructorTest() {
		// initialize new Player and check fields match that passed or defaults
		Player player = new Player("Dave");
		assertEquals("Player's instantiated playerName is not that passed in the constructor", "Dave",
				player.getPlayerName());
		assertEquals("Player's playerTable should not be instantiated in the constructor", null,
				player.getPlayerTable());
		assertEquals("Player's gamesPlayed should default to 0", 0, player.getGamesPlayed());
		assertEquals("Player's gamesPlayed should default to 0", 0, player.getGamesWon());
		assertEquals("Player's cards should default to an empty ArrayList", new ArrayList<Deck.Card>(),
				player.getCards());
		// playerName null case
		Player nullPlayer = new Player(null);
		assertEquals("Player's instantiated playerName is not an empty String when null is passed in the constructor",
				"", nullPlayer.getPlayerName());
	}

	@Test
	public void getAndSetPlayerNameTest() {
		// initialize new Player
		Player player = new Player();
		// set the Player's name to a non-default
		player.setPlayerName("David");
		// check that playerName was correctly changed
		assertEquals("Player's playerName does not match the one given in the setter", "David", player.getPlayerName());
		// playerName null case
		assertFalse("Player's playerName does not return false when null is passed to the setter",
				player.setPlayerName(null));
		assertEquals("Player's playerName does not remain the same when null is passed to the setter", "David",
				player.getPlayerName());
	}

	@Test
	public void getAndSetPlayerTableTest() {
		// initialize new Player
		Player player = new Player();
		// set the Player's table to a non-default
		player.setPlayerTable(new Table());
		// check that playerName was correctly changed
		assertEquals("Player's playerTable does not match the one given in the setter", new Table(),
				player.getPlayerTable());
	}

	@Test
	public void getAndSetGamesPlayedTest() {
		// initialize new Player
		Player player = new Player();
		// set the Player's gamesPlayed to less than zero
		assertFalse("Player's setGamesPlayed() does not return false when an int less than 0 is passed", player.setGamesPlayed(-99));
		// set the Player's gamesPlayed to a non-default
		assertTrue("Player's setGamesPlayed() does not return true when an int greater than or equal to 0 is passed", player.setGamesPlayed(3));
		// check that gamesPlayed was correctly changed
		assertEquals("Player's gamesPlayed does not match the one given in the setter", 3, player.getGamesPlayed());
		// set the Player's gamesPlayed to less than gamesWon
		player.setGamesWon(2);
		assertFalse("Player's setGamesPlayed() does not return false when an int less than gamesWon is passed", player.setGamesPlayed(1));
	}

	@Test
	public void getAndSetGamesWonTest() {
		// initialize new Player
		Player player = new Player();
		player.setGamesPlayed(3);
		// set the Player's gamesWon to less than 0
		assertFalse("Player's setGamesWon() does not return false when an int less than 0 is passed", player.setGamesWon(-87));
		// set the Player's gamesWon to greater than gamesPlayed
		assertFalse("Player's setGamesWon() does not return false when an int greater than gamesPlayed is passed", player.setGamesWon(26));
		// set the Player's gamesWon in bounds
		assertTrue("Player's setGamesWon() does not return true when a non-negative int less than or equal to gamesPlayed is passed", player.setGamesWon(2));
		// check that gamesPlayed was correctly changed
		assertEquals("Player's gamesWon does not match the one given in the setter", 2, player.getGamesWon());
	}

	@Test
	public void getAndSetCardsTest() {
		// initialize new Player, new Deck, and new ArrayList of cards
		Player player = new Player();
		Deck deck = new Deck();
		ArrayList<Deck.Card> expectedCards = new ArrayList<>();
		expectedCards.add(deck.new Card("Two", "Hearts"));
		expectedCards.add(deck.new Card("Three", "Diamonds"));
		expectedCards.add(deck.new Card("Four", "Spades"));
		// set the Player's cards to the list of cards
		assertFalse("Player's setCards() does not return false when null is passed", player.setCards(null));
		assertTrue("Player's setCards() does not return true when a non-null ArrayList is passed", player.setCards(expectedCards));
		// check that cards was correctly changed
		assertEquals("Player's setCards() does not match the one given in the setter", expectedCards,
				player.getCards());
	}

	@Test
	public void atTableTest() {
		// initialize test player and test Table
		Player player = new Player();
		Table table = new Table();
		// null playerTable case
		assertFalse("Player's atTable() does not return false when its playerTable is null", player.atTable());
		// non-null playerTable case
		player.setPlayerTable(table);
		assertTrue("Player's atTable() does not return true when its playerTable is not null", player.atTable());
	}

	@Test
	public void atTableWithTest() {
		// initialize test player and test Table with no deck
		Player player = new Player();
		Table table = new Table();
		table.setDeck(null);
		// null playerTable case
		assertFalse("Player's atTableWithDeck() does not return false when its playerTable is null",
				player.atTableWithDeck());
		// non-null playerTable case, null deck case
		player.setPlayerTable(table);
		assertFalse("Player's atTableWithDeck() does not return false when its playerTable has no deck",
				player.atTableWithDeck());
		// non-null playerTable case, non-null deck case
		table.setDeck(new Deck());
		assertTrue("Player's atTableWithDeck() does not return true when its playerTable exists and has a deck",
				player.atTableWithDeck());
	}

	@Test
	public void joinTableTest() {
		// initialize test Player and Table
		Player player = new Player();
		Table table = new Table();
		// check logic flows
		assertTrue("Player's joinTable() does not return true when playerTable is null", player.joinTable(table));
		assertFalse("Player's joinTable() does not return false when playerTable is not null", player.joinTable(table));
		// check Player's playerTable is Table
		assertEquals("Player's joinTable() does not set its playerTable to the table passed", table,
				player.getPlayerTable());
		// check Table has Player in its players ArrayList
		ArrayList<Player> expectedPlayers = new ArrayList<>();
		expectedPlayers.add(player);
		assertEquals("Player's joinTable() does not add Player to the Table's players ArrayList", expectedPlayers,
				table.getPlayers());
	}

	@Test
	public void leaveTableTest() {
		// initialize test Player and Table, fill table's deck, let player join the
		// table and draw cards
		Player player = new Player();
		Table table = new Table(new PokerDeck());
		table.getDeck().createCard("Joker", "", 2);
		table.getDeck().createCard("Two", "Spades", 3);
		player.joinTable(table);
		player.drawCard(false, 2);
		// check logic flows
		assertTrue("Player's leaveTable() does not return true when playerTable is not null", player.leaveTable());
		assertFalse("Player's leaveTable() does not return false when playerTable is null", player.leaveTable());
		// check Player's playerTable is null
		assertEquals("Player's leaveTable() does not set its playerTable to null", null, player.getPlayerTable());
		// check Table no longer has Player in its players ArrayList
		ArrayList<Player> expectedPlayers = new ArrayList<>();
		assertEquals("Player's leaveTable() does not remove itself from its playerTable's players ArrayList",
				expectedPlayers, table.getPlayers());
		// check Player's cards are clear
		ArrayList<Player> expectedPlayerCards = new ArrayList<>();
		assertEquals("Player's leaveTable() does not clear its cards ArrayList", expectedPlayerCards,
				player.getCards());
		// check Table's Deck's cards have been returned
		ArrayList<Deck.Card> expectedDeckCards = new ArrayList<>();
		expectedDeckCards.add(table.getDeck().new Card("Two", "Spades"));
		expectedDeckCards.add(table.getDeck().new Card("Two", "Spades"));
		expectedDeckCards.add(table.getDeck().new Card("Two", "Spades"));
		expectedDeckCards.add(table.getDeck().new Card("Joker", ""));
		expectedDeckCards.add(table.getDeck().new Card("Joker", ""));
		assertEquals("Player's leaveTable() does not add its cards to its table's deck's cards ArrayList",
				expectedDeckCards, table.getDeck().getCards());
	}

	@Test
	public void drawCardWithQuantityTest() {
		// instantiate test Player and test Table with default deck, fill deck with cards
		Player player = new Player();
		Table table = new Table(null);
		// check drawCard(int quantity) logic flow
		assertFalse("Player's overloaded drawCard() does not return false when playerTable is null",
				player.drawCard(true, 4));
		player.joinTable(table);
		assertFalse("Player's overloaded drawCard() does not return false when playerTable has no deck",
				player.drawCard(true, 4));
		table.setDeck(new Deck(Deck.asArrayList(PokerDeck.RANKS), Deck.asArrayList(PokerDeck.WILDS), Deck.asArrayList(PokerDeck.SUITS), Deck.asArrayList(PokerDeck.BACKS)));
		table.getDeck().createCard("Four", "Clubs", 4);
		table.getDeck().createCard("Five", "Spades", 5);
		assertFalse(
				"Player's overloaded drawCard() does not return false when a quantity greater than its deck's size is passed",
				player.drawCard(true, 10));
		assertTrue(
				"Players overloaded drawCard() does not return true when a quantity less than its deck's size is passed",
				player.drawCard(true, 8));
		// check expected changes to Player's cards
		ArrayList<Deck.Card> expectedPlayerCards = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			expectedPlayerCards.add(table.getDeck().new Card("Four", "Clubs"));
		}
		for (int j = 0; j < 4; j++) {
			expectedPlayerCards.add(table.getDeck().new Card("Five", "Spades"));
		}
		for (Deck.Card card : expectedPlayerCards) {
			card.setFaceUp(true);
		}
		assertEquals("Player's overloaded drawCard() does not correctly modify Players cards ArrayList",
				expectedPlayerCards, player.getCards());
		// check expected changes to Deck's cards
		ArrayList<Deck.Card> expectedDeckCards = new ArrayList<>();
		expectedDeckCards.add(table.getDeck().new Card("Five", "Spades"));
		assertEquals("Player's overloaded drawCard does not correctly modify its playerTable's Deck's cards ArrayList",
				expectedDeckCards, table.getDeck().getCards());
	}

	@Test
	public void drawCardTest() {
		// instantiate test Player and test Table with default deck, fill deck with
		// cards
		Player player = new Player();
		Table table = new Table(null);
		// check drawCard(int quantity) logic flow
		assertFalse("Player's default drawCard() does not return false when playerTable is null", player.drawCard());
		player.joinTable(table);
		assertFalse("Player's default drawCard() does not return false when playerTable has no deck",
				player.drawCard());
		table.setDeck(new Deck(Deck.asArrayList(PokerDeck.RANKS), Deck.asArrayList(PokerDeck.WILDS), Deck.asArrayList(PokerDeck.SUITS), Deck.asArrayList(PokerDeck.BACKS)));
		table.getDeck().createCard("Four", "Clubs");
		assertTrue("Players default drawCard() does not return true when playerTable's deck is non-empty",
				player.drawCard());
		assertFalse("Player's default drawCard() does not return false when playerTable's deck is empty",
				player.drawCard());
		table.getDeck().createCard("Five", "Diamonds");
		assertTrue("Players single-field drawCard() does not return true when playerTable's deck is non-empty",
				player.drawCard(true));
		assertFalse("Player's single-field drawCard() does not return false when playerTable's deck is empty",
				player.drawCard(true));
		// check expected changes to Player's cards
		ArrayList<Deck.Card> expectedPlayerCards = new ArrayList<>();
		expectedPlayerCards.add(table.getDeck().new Card("Four", "Clubs"));
		expectedPlayerCards.add(table.getDeck().new Card("Five", "Diamonds"));
		expectedPlayerCards.get(1).setFaceUp(true);
		assertEquals("Player's default drawCard() does not correctly modify Players cards ArrayList",
				expectedPlayerCards, player.getCards());
		// check expected changes to Deck's cards
		ArrayList<Deck.Card> expectedDeckCards = new ArrayList<>();
		assertEquals("Player's default drawCard does not correctly modify its playerTable's Deck's cards ArrayList",
				expectedDeckCards, table.getDeck().getCards());
	}

	@Test
	public void returnCardTest() {
		// instantiate test Player and Table with default deck, have player join table
		// and get cards
		Player player = new Player();
		// no table case
		assertFalse("Player's returnCard() does not return false when playerTable is null", player.returnCard(0));
		// table with no deck case
		Table table = new Table(null);
		player.joinTable(table);
		assertFalse("Player's returnCard() does not return false when playerTable has no deck", player.returnCard(0));
		// check indices
		table.setDeck(new Deck());
		player.getCards().add(table.getDeck().new Card("Ace", "Spades"));
		player.getCards().add(table.getDeck().new Card("Two", "Clubs"));
		player.getCards().add(table.getDeck().new Card("Three", "Spades"));
		assertFalse("Player's returnCard() does not return false when passed an index less than 0",
				player.returnCard(-1));
		assertFalse(
				"Player's returnCard() does not return false when passed an index greater than or equal to its size",
				player.returnCard(player.getCards().size()));
		assertTrue("Player's returnCard() does not return true when passed an index in its bounds",
				player.returnCard(1));
		// check expected changes to Players cards
		ArrayList<Deck.Card> expectedPlayerCards = new ArrayList<>();
		expectedPlayerCards.add(table.getDeck().new Card("Ace", "Spades"));
		expectedPlayerCards.add(table.getDeck().new Card("Three", "Spades"));
		assertEquals("Player's returnCard() does not correctly modify its cards ArrayList", expectedPlayerCards,
				player.getCards());
		// check expected changes to Deck's cards
		ArrayList<Deck.Card> expectedDeckCards = new ArrayList<>();
		expectedDeckCards.add(table.getDeck().new Card("Two", "Clubs"));
		assertEquals("Player's returnCard() does not correctly modify its deck's cards ArrayList", expectedDeckCards,
				table.getDeck().getCards());
	}

	@Test
	public void returnAllCardsTest() {
		// instantiate test Player and Table with default deck, have player join table
		// and get cards
		Player player = new Player();
		// no table case
		assertFalse("Player's returnAllCards() does not return false when playerTable is null",
				player.returnAllCards());
		// table with no deck case
		Table table = new Table(null);
		player.joinTable(table);
		assertFalse("Player's returnAllCards() does not return false when playerTable has no deck",
				player.returnAllCards());
		// check indices
		table.setDeck(new Deck());
		player.getCards().add(table.getDeck().new Card("Ace", "Spades"));
		player.getCards().add(table.getDeck().new Card("Two", "Clubs"));
		player.getCards().add(table.getDeck().new Card("Three", "Spades"));
		assertTrue("Player's returnAllCards() does not return true when cards is not empty", player.returnAllCards());
		assertFalse("Player's returnAllCards() does not return false when cards is empty", player.returnAllCards());
		// check expected changes to Players cards
		ArrayList<Deck.Card> expectedPlayerCards = new ArrayList<>();
		assertEquals("Player's returnAllCards() does not correctly modify its cards ArrayList", expectedPlayerCards,
				player.getCards());
		// check expected changes to Deck's cards
		ArrayList<Deck.Card> expectedDeckCards = new ArrayList<>();
		expectedDeckCards.add(table.getDeck().new Card("Ace", "Spades"));
		expectedDeckCards.add(table.getDeck().new Card("Two", "Clubs"));
		expectedDeckCards.add(table.getDeck().new Card("Three", "Spades"));
		assertEquals("Player's returnAllCards() does not correctly modify its deck's cards ArrayList",
				expectedDeckCards, table.getDeck().getCards());
	}

	@Test
	public void toStringTest() {
		// initialize test players and test table
		Player namedPlayer = new Player("Tam");
		namedPlayer.setGamesPlayed(6);
		namedPlayer.setGamesWon(3);
		Player unnamedPlayer = new Player();
		Table sampleTable = new Table();
		Table blankTable = new Table(new Deck(new ArrayList<String>(), new ArrayList<String>()));
		// case: no table, blank name
		assertEquals("Player's toString() does not match that specified when Player has a blank name and no table",
				"[no name] (" + unnamedPlayer.getGamesPlayed() + ", " + unnamedPlayer.getGamesWon() + ")",
				unnamedPlayer.toString());
		// case: no table, non-blank name
		assertEquals("Player's toString() does not match that specified when Player has a non-blank name and no table",
				namedPlayer.getPlayerName() + " (" + namedPlayer.getGamesPlayed() + ", " + namedPlayer.getGamesWon() + ")",
				namedPlayer.toString());
		// case: at table, blank name
		unnamedPlayer.joinTable(sampleTable);
		assertEquals("Player's toString() does not match that specified when Player has a blank name and a table",
				"[no name]" + " at a " + unnamedPlayer.getPlayerTable().toString() + " (" + unnamedPlayer.getGamesPlayed() + ", " + unnamedPlayer.getGamesWon() + ")",
				unnamedPlayer.toString());
		// case: at table, non-blank name
		namedPlayer.joinTable(blankTable);
		assertEquals("Player's toString() does not match that specified when Player has a non-blank name and a table",
				namedPlayer.getPlayerName() + " at a " + namedPlayer.getPlayerTable().toString() + " (" + namedPlayer.getGamesPlayed() + ", " + namedPlayer.getGamesWon() + ")",
				namedPlayer.toString());
	}

	@Test
	public void equalsTest() {
		// instantiate test cases for each logic flow
		Player player = new Player();
		Deck deck = new Deck();
		Player playerWithName = new Player("Deborah");
		Player samePlayer = new Player();
		samePlayer.setGamesPlayed(16);
		samePlayer.setGamesWon(8);
		samePlayer.joinTable(new Table(deck));
		ArrayList<Deck.Card> sampleCards = new ArrayList<>();
		sampleCards.add(deck.new Card("Ace", "Spades"));
		sampleCards.add(deck.new Card("Joker", ""));
		samePlayer.setCards(sampleCards);
		// test for each expected outcome
		assertFalse("Player equals() does not return false when passed a null pointer", player.equals(null));
		assertFalse("Player equals() does not return false when passed a Deck", player.equals(deck));
		assertFalse("Player equals() does not return false when passed a Player with a different name", player.equals(playerWithName));
		assertTrue("Player equals() does not return true when passed a Player with the same name", player.equals(samePlayer));
	}

	@Test
	public void compareToTest() {
		// instantiate test objects
		Table sampleTable = new Table();
		Table blankTable = new Table(new Deck(new ArrayList<String>(), new ArrayList<String>()));
		ArrayList<Deck.Card> sampleCards = new ArrayList<>();
		sampleCards.add(sampleTable.getDeck().new Card("Ace", "Hearts"));
		sampleCards.add(sampleTable.getDeck().new Card("Two", "Diamonds"));
		Player player = new Player("Sam");
		player.setPlayerTable(blankTable);
		// different name case
		Player playerWithName = new Player("Deborah");
		playerWithName.joinTable(sampleTable);
		assertTrue("Player's compareTo() does not sort players with different names in ascending order by name", player.compareTo(playerWithName) > 0);
		// different table case
		Player playerWithTable = new Player("Sam");
		playerWithTable.joinTable(sampleTable);
		player.setGamesPlayed(10);
		assertTrue("Player's compareTo() does not sort players with the same name with different tables in natural order by table", player.compareTo(playerWithTable) > 0);
		// otherPlayer has no table case
		Player playerWithNoTable = new Player("Sam");
		assertEquals("Player's compareTo() does not sort the player at a table before the other not at one when they have the same name", -1, player.compareTo(playerWithNoTable));
		// player has no table case
		player.leaveTable();
		assertEquals("Player's compareTo() does not sort the other player at a table before the player not at one when they have the same name", 1, player.compareTo(playerWithTable));
		// different gamesPlayed case
		Player playerWithGamesPlayed = new Player("Sam");
		playerWithGamesPlayed.setGamesPlayed(50);
		player.setGamesWon(5);
		assertEquals("Player's compareTo() does not sort players with the same name and table in descending order of gamesPlayed", 40, player.compareTo(playerWithGamesPlayed));
		// different gamesWon case
		Player playerWithGamesWon = new Player("Sam");
		playerWithGamesWon.setGamesPlayed(10);
		playerWithGamesWon.setGamesWon(10);
		player.setCards(sampleCards);
		assertEquals("Player's compareTo() does not sort players with the same name, table, and gamesPlayed in descending order by gamesWon", 5, player.compareTo(playerWithGamesWon));
		// different cards case
		Player playerWithCards = new Player("Sam");
		ArrayList<Deck.Card> moreCards = new ArrayList<>();
		moreCards.add(sampleTable.getDeck().new Card("King", "Hearts"));
		moreCards.add(sampleTable.getDeck().new Card("Two", "Spades"));
		playerWithCards.setCards(moreCards);
		player.setGamesWon(0);
		player.setGamesPlayed(0);
		player.getCards().remove(0);
		assertEquals("Player's compareTo() does not sort players with the same name, table, gamesPlayed, and gamesWon in descending order by number of cards", 1, player.compareTo(playerWithCards));
		// same fields case
		Player samePlayer = new Player("Sam");
		player.getCards().clear();
		assertEquals("Player's compareTo() does not sort players with the same name, table, gamesPlayed, gamesWon and number of cards together", 0, player.compareTo(samePlayer));
	}

}
