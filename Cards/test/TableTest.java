import static org.junit.Assert.*;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Card Simulator Table Test
 * 
 * @author Daniel Xue 07/27/2021
 * 
 * Sources: UVA CS2110 Lectures,
 * UVA CS2110 HW 5 and 6
 * JDK 11 Documentation
 */

public class TableTest {

	@Test
	public void defaultConstructorTest() {
		// initialize test Table
		Table table = new Table();
		// check default deck is, well, the default deck
		assertEquals("Table's default deck is not given by Deck's default constructor", new Deck(), table.getDeck());
		// check default cards is an empty ArrayList<Deck.Card>
		assertEquals("Table's default cards is not an empty ArrayList<Deck.Card>", new ArrayList<Deck.Card>(), table.getCards());
		// check default players is an empty ArrayList<Player>
		assertEquals("Table's default players is not an empty ArrayList<Player>", new ArrayList<Player>(), table.getPlayers());
	}
	
	@Test
	public void overloadedConstructorTest() {
		// initialize test Table with sample deck defined by arrays
		String[] rankArray = { "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
				"Draw Two", "Skip", "Reverse", "Wild", "Wild Draw Four" };
		String[] wildArray = { "Wild", "Wild Draw Four" };
		String[] suitArray = { "Red", "Yellow", "Green", "Blue" };
		Deck unoDeck = new Deck(Deck.asArrayList(rankArray), Deck.asArrayList(wildArray), Deck.asArrayList(suitArray));
		Table table = new Table(unoDeck);
		// check instantiated deck matches that passed
		assertEquals("Table's instantiated deck is not given by Deck's default constructor", unoDeck, table.getDeck());
		// check instantiated cards is an empty ArrayList<Deck.Card>
		assertEquals("Table's instantiated cards is not an empty ArrayList<Deck.Card>", new ArrayList<Deck.Card>(), table.getCards());
		// check instantiated players is an empty ArrayList<Player>
		assertEquals("Table's instantiated players is not an empty ArrayList<Player>", new ArrayList<Player>(), table.getPlayers());
	}
	
	@Test
	public void getAndSetDeckTest() {
		// initialize test table and players with default deck
		Deck deck = new Deck(Deck.asArrayList(UnoDeck.RANKS), Deck.asArrayList(UnoDeck.WILDS), Deck.asArrayList(UnoDeck.SUITS), Deck.asArrayList(UnoDeck.BACKS));
		Table table = new Table(deck);
		table.getDeck().fillDeck();
		table.drawCard(false, 14);
		Player playerOne = new Player("Jim");
		Player playerTwo = new Player("Bob");
		playerOne.joinTable(table);
		playerOne.drawCard(false, 14);
		playerTwo.joinTable(table);
		playerTwo.drawCard(false, 14);
		// initialize unique deck with Arrays and asArrayList
		String[] rankArray = { "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
				"Draw Two", "Skip", "Reverse", "Wild", "Wild Draw Four" };
		String[] wildArray = { "Wild", "Wild Draw Four" };
		String[] suitArray = { "Red", "Yellow", "Green", "Blue" };
		Deck unoDeck = new Deck(Deck.asArrayList(rankArray), Deck.asArrayList(wildArray), Deck.asArrayList(suitArray));
		// set new deck for the table
		table.setDeck(unoDeck);
		// check instantiated deck matches that passed in the setter
		assertEquals("Table's instantiated deck is not given by Table's setDeck()", unoDeck, table.getDeck());
		// check expected changes to table and players' cards
		assertTrue("Table's setDeck() does not clear its cards", table.getCards().isEmpty());
		for (Player player : table.getPlayers()) {
			assertTrue("Table's setDeck() does not clear its players' cards", player.getCards().isEmpty());
		}
		// check expected changes to table and players' cards
		Collections.sort(deck.getCards());
		Deck sameDeck = new Deck(Deck.asArrayList(UnoDeck.RANKS), Deck.asArrayList(UnoDeck.WILDS), Deck.asArrayList(UnoDeck.SUITS), Deck.asArrayList(UnoDeck.BACKS));
		sameDeck.fillDeck();
		assertEquals("Table's setDeck() does not return all cards from table and players to its old deck before setting the new one", sameDeck.getCards(), deck.getCards());
	}
	
	@Test
	public void getAndSetCardsTest() {
		// initialize test table with default deck
		Table table = new Table();
		// initialize test cards with the table's deck and add them to a list
		ArrayList<Deck.Card> expectedCards = new ArrayList<>();
		expectedCards.add(table.getDeck().new Card("Joker", ""));
		expectedCards.add(table.getDeck().new Card("Two", "Diamonds"));
		expectedCards.add(table.getDeck().new Card("Seven", "Clubs"));
		// set new cards for the table
		assertTrue("Table's setCards() does not return true when passed a non-null ArrayList<Deck.Card>()", table.setCards(expectedCards));
		// check instantiated cards matches the ArrayList<Deck.Card> passed in the setter
		assertEquals("Table's instantiated cards is not given by Table's setCards()", expectedCards, table.getCards());
		// null case
		assertFalse("Table's setCards() does not return false when passed null", table.setCards(null));
	}
	
	@Test
	public void getAndSetPlayersTest() {
		// initialize test table with default deck
		Table table = new Table();
		// initialize test players and add them to a list
		ArrayList<Player> expectedPlayers = new ArrayList<>();
		expectedPlayers.add(new Player("Dave"));
		expectedPlayers.add(new Player("Danny"));
		expectedPlayers.add(new Player("Darby"));
		// set new players for the table
		assertTrue("Table's setPlayers() does not return true when passed a non-null ArrayList<Player>()", table.setPlayers(expectedPlayers));
		// check instantiated players matches the ArrayList<Player> passed in the setter
		assertEquals("Table's instantiated players is not given by Table's setCards()", expectedPlayers, table.getPlayers());
		// null case
		assertFalse("Table's setPlayers() does not return true when passed null", table.setPlayers(null));
	}
	
	@Test
	public void toStringTest() {
		// empty table case
		Table emptyTable = new Table(new Deck(new ArrayList<String>(), new ArrayList<String>()));
		assertEquals("Table's toString() does not return the correct String for an empty Table", "0-player table with a " + emptyTable.getDeck().toString(), emptyTable.toString());
		// full table case
		Table sampleTable = new Table();
		sampleTable.getPlayers().add(new Player("Steve"));
		sampleTable.getPlayers().add(new Player("Bob"));
		assertEquals("Table's toString() does not return the correct String for a non-empty Table", "2-player table with a " + sampleTable.getDeck().toString(), sampleTable.toString());
		// no deck case
		Table tableWithNoDeck = new Table(null);
		assertEquals("Table's toString() does not return the correct String for a table with no deck", "0-player table with no deck", tableWithNoDeck.toString());
	}
	
	@Test
	public void equalsTest() {
		// instantiate test cases for each logic flow
		Table table = new Table(new UnoDeck());
		Player player = new Player();
		Table tableWithDifferentDeck = new Table(new Deck(new ArrayList<String>(), new ArrayList<String>()));
		Table sameTable = new Table(new UnoDeck());
		sameTable.getCards().add(sameTable.getDeck().new Card("Two", "Hearts"));
		sameTable.getPlayers().add(new Player("Bob"));
		sameTable.getPlayers().add(new Player("Benjy"));
		// test for each expected outcome
		assertFalse("Table's equals() does not return false when passed a null pointer", table.equals(null));
		assertFalse("Table's equals() does not return false when passed a Player", table.equals(player));
		assertFalse("Table's equals() does not return false when passed a table with a different deck", table.equals(tableWithDifferentDeck));
		assertTrue("Table's equals() does not return true when passed a table with the same deck", table.equals(sameTable));
		// null cases
		Table tableWithNoDeck = new Table(null);
		Table otherTableWithNoDeck = new Table(null);
		assertTrue("Table's equals() does not return true when passed two tables with no decks", tableWithNoDeck.equals(otherTableWithNoDeck));
		assertFalse("Table's equals() does not return false when passed one table with a deck and another without", table.equals(otherTableWithNoDeck));
		assertFalse("Table's equals() does not return false when passed one table without a deck and the other with one", tableWithNoDeck.equals(table));
	}
	
	@Test
	public void compareToTest() {
		// instantiate test cases for each logic flow
		Table table = new Table(new PokerDeck());
		Table tableWithEmptyDeck = new Table(new Deck(new ArrayList<String>(), new ArrayList<String>()));
		tableWithEmptyDeck.getPlayers().add(new Player("Simon"));
		Table tableWithPlayers = new Table(new PokerDeck());
		tableWithPlayers.getPlayers().add(new Player("Sarah"));
		tableWithPlayers.getPlayers().add(new Player("Erica"));
		Table sameTable = new Table(new PokerDeck());
		sameTable.getCards().add(sameTable.getDeck().new Card("Two", "Hearts"));
		// order tests by comparison order
		assertTrue("Two Tables with different decks are not sorted in natural order by their decks", table.compareTo(tableWithEmptyDeck) < 0);
		assertEquals("Two Tables with different players are not sorted in descending order by # of players", tableWithPlayers.getPlayers().size() - table.getPlayers().size(), table.compareTo(tableWithPlayers));
		assertEquals("Two Tables with the same deck and # of players are not sorted together", 0, table.compareTo(sameTable));
		// null cases
		Table tableWithNoDeck = new Table(null);
		Table otherTableWithNoDeck = new Table(null);
		assertEquals("Table's compareTo() does not sort two tables with no decks and no players together", 0, tableWithNoDeck.compareTo(otherTableWithNoDeck));
		assertEquals("Table's compareTo() does not sort a table with a deck before another without", -1, table.compareTo(otherTableWithNoDeck));
		assertEquals("Table's compareTo() does not sort a table without a deck after the other with one", 1, tableWithNoDeck.compareTo(table));
	}
	
	@Test
	public void drawCardWithFaceUpAndQuantityTest() {
		// instantiate test Table with default deck, fill deck with cards
		Table table = new Table(new PokerDeck());
		table.getDeck().createCard("Four", "Clubs", 4);
		table.getDeck().createCard("Five", "Spades", 5);
		// check drawCard(boolean faceUp, int quantity) logic flow
		assertFalse("Table's overloaded drawCard() does not return false when a quantity greater than its deck's size is passed", table.drawCard(false, 10));
		assertTrue("Table's overloaded drawCard() does not return true when a quantity less than its deck's size is passed", table.drawCard(true, 8));
		// check expected changes to Table's cards
		ArrayList<Deck.Card> expectedTableCards = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			expectedTableCards.add(table.getDeck().new Card("Four", "Clubs"));
		}
		for (int j = 0; j < 4; j++) {
			expectedTableCards.add(table.getDeck().new Card("Five", "Spades"));
		}
		for (Deck.Card card : expectedTableCards) {
			card.setFaceUp(true);
		}
		assertEquals("Table's overloaded drawCard() does not correctly modify Table's cards ArrayList", expectedTableCards, table.getCards());
		// check expected changes to Deck's cards
		ArrayList<Deck.Card> expectedDeckCards = new ArrayList<>();
		expectedDeckCards.add(table.getDeck().new Card("Five", "Spades"));
		assertEquals("Table's overloaded drawCard() does not correctly modify Desk's cards ArrayList", expectedDeckCards, table.getDeck().getCards());	
		// null deck case
		table.setDeck(null);
		assertFalse("Table's drawCard() does not return false when it has no deck", table.drawCard(true, 2));
	}
	
	@Test
	public void drawCardWithFaceUpTest() {
		// instantiate test Table with default deck, fill deck with cards
		Table table = new Table(new PokerDeck());
		table.getDeck().createCard("Five", "Spades");
		table.getDeck().createCard("Six", "Hearts");
		// check drawCard(boolean faceUp) logic flow
		assertTrue("Table's overloaded drawCard() does not return true when its deck is non-empty and quantity passed is 1", table.drawCard(true));
		assertTrue("Table's overloaded drawCard() does not return true when its deck is non-empty and quantity passed is 1", table.drawCard(false));
		assertFalse("Table's overloaded drawCard() does not return false when its deck is empty", table.drawCard(true));
		// check expected changes to Table's cards
		ArrayList<Deck.Card> expectedTableCards = new ArrayList<>(); 
		expectedTableCards.add(table.getDeck().new Card("Five", "Spades"));
		expectedTableCards.get(expectedTableCards.size()-1).setFaceUp(true);
		expectedTableCards.add(table.getDeck().new Card("Six", "Hearts"));
		assertEquals("Table's overloaded drawCard() does not correctly modify Table's cards ArrayList", expectedTableCards, table.getCards());
		// check expected changes to Deck's cards
		ArrayList<Deck.Card> expectedDeckCards = new ArrayList<>(); 
		expectedDeckCards.clear();
		assertEquals("Table's overloaded drawCard() does not correctly modify Desk's cards ArrayList", expectedDeckCards, table.getDeck().getCards());
	}
	
	@Test
	public void drawCardTest() {
		// instantiate test Table with default deck, fill deck with cards
		Table table = new Table(new PokerDeck());
		// check drawCard() logic flow
		table.getDeck().createCard("Seven", "Diamonds");
		assertTrue("Table's default drawCard() does not return true when its deck is non-empty", table.drawCard());
		assertFalse("Table's default drawCard() does not return false when its deck is empty", table.drawCard());
		// check expected changes to Table's cards
		ArrayList<Deck.Card> expectedTableCards = new ArrayList<>(); 
		expectedTableCards.add(table.getDeck().new Card("Seven", "Diamonds"));
		assertEquals("Table's overloaded drawCard() does not correctly modify Table's cards ArrayList", expectedTableCards, table.getCards());
		// check expected changes to Deck's cards
		ArrayList<Deck.Card> expectedDeckCards = new ArrayList<>(); 
		expectedDeckCards.clear();
		assertEquals("Table's overloaded drawCard() does not correctly modify Desk's cards ArrayList", expectedDeckCards, table.getDeck().getCards());		
	}

	@Test
	public void returnCardTest() {
		// instantiate test Table with default deck, fill deck with cards and draw cards
		Table table = new Table();
		table.getCards().add(table.getDeck().new Card("Ace", "Spades"));
		table.getCards().add(table.getDeck().new Card("Two", "Clubs"));
		table.getCards().add(table.getDeck().new Card("Three", "Spades"));
		// check returnCard() logic flow
		assertFalse("Table's returnCard() does not return false when passed an index less than 0", table.returnCard(-1));
		assertFalse("Table's returnCard() does not return false when passed an index greater than or equal to its size", table.returnCard(table.getCards().size()));
		assertTrue("Table's returnCard() does not return true when passed an index in its bounds", table.returnCard(1));
		// check expected changes to Table's cards
		ArrayList<Deck.Card> expectedTableCards = new ArrayList<>();
		expectedTableCards.add(table.getDeck().new Card("Ace", "Spades"));
		expectedTableCards.add(table.getDeck().new Card("Three", "Spades"));
		assertEquals("Table's returnCard() does not correctly modify its cards ArrayList", expectedTableCards, table.getCards());
		// check expected changes to Deck's cards
		ArrayList<Deck.Card> expectedDeckCards = new ArrayList<>();
		expectedDeckCards.add(table.getDeck().new Card("Two", "Clubs"));
		assertEquals("Table's returnCard() does not correctly modify its deck's cards ArrayList", expectedDeckCards, table.getDeck().getCards());
		// null deck case
		table.setDeck(null);
		assertFalse("Table's returnCard() does not return false when table has no deck", table.returnCard(0));
	}
	
	@Test
	public void returnAllCardsTest() {
		// instantiate test Table with default deck, fill deck with cards and draw cards
		Table table = new Table();
		// empty cards case
		assertFalse("Table's returnAllCards() does not return false when its cards ArrayList is empty", table.returnAllCards());
		// non-empty cards case
		table.getCards().add(table.getDeck().new Card("Ace", "Spades"));
		table.getCards().add(table.getDeck().new Card("Two", "Clubs"));
		table.getCards().add(table.getDeck().new Card("Three", "Spades"));
		assertTrue("Table's returnCard() does not return true when its cards ArrayList is non-empty", table.returnAllCards());
		// check expected changes to Table's cards
		ArrayList<Deck.Card> expectedTableCards = new ArrayList<>();
		assertEquals("Table's returnAllCards() does not correctly modify its cards ArrayList", expectedTableCards, table.getCards());
		// check expected changes to Deck's cards
		ArrayList<Deck.Card> expectedDeckCards = new ArrayList<>();
		expectedDeckCards.add(table.getDeck().new Card("Ace", "Spades"));
		expectedDeckCards.add(table.getDeck().new Card("Two", "Clubs"));
		expectedDeckCards.add(table.getDeck().new Card("Three", "Spades"));
		assertEquals("Table's returnCard() does not correctly modify its deck's cards ArrayList", expectedDeckCards, table.getDeck().getCards());
		// null deck case
		table.setDeck(null);
		assertFalse("Table's returnAllCards() does not return false when it has no deck", table.returnAllCards());
	}
}
