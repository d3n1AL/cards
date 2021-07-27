import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

/**
 * Card Simulator Poker Deck Test
 * 
 * @author Daniel Xue 07/27/2021
 * 
 * Sources: UVA CS2110 Lectures,
 * UVA CS2110 HW 5 and 6
 * JDK 11 Documentation
 */

public class PokerDeckTest {

	@Test
	public void defaultConstructorTest() {
		// initialize test deck
		PokerDeck pokerDeck = new PokerDeck();
		// checks default rankList is given by its RANKS array
		assertEquals("PokerDeck's default list of ranks is not given by its RANKS array",
				Deck.asArrayList(PokerDeck.RANKS), pokerDeck.getRankList());
		// checks default wildList is given by its WILDS array
		assertEquals("PokerDeck's default list of wilds is not given by its WILDS array",
				Deck.asArrayList(PokerDeck.WILDS), pokerDeck.getWildList());
		// checks default suitList is given by its SUITS array
		assertEquals("PokerDeck's default list of suits is not given by its SUITS array",
				Deck.asArrayList(PokerDeck.SUITS), pokerDeck.getSuitList());
		// checks default backList is given by its BACKS array
		assertEquals("PokerDeck's default list of backs is not given by its BACKS array",
				Deck.asArrayList(PokerDeck.BACKS), pokerDeck.getBackList());
		// checks default cards is an empty ArrayList
		assertEquals("PokerDeck's default cards is not an empty ArrayList<PokerCard>", new ArrayList<PokerDeck.PokerCard>(), pokerDeck.getCards());
	}

	@Test
	public void getAndSetRankListTest() {
		// Array to initialize list elements
		String[] rankArray = { "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
				"Draw Two", "Skip", "Reverse", "Wild", "Wild Draw Four" };
		// initialize test deck and test cards and wilds
		PokerDeck deck = new PokerDeck();
		deck.getWildList().add("Two");
		// set a new rankList for the Deck
		assertTrue("Deck's setRankList() does not return true when passed a non-null ArrayList<String>", deck.setRankList(Deck.asArrayList(rankArray)));
		// checks instantiated rankList matches that given
		assertEquals("Deck's instantiated rank list does not match that given in the setter", Deck.asArrayList(rankArray),
				deck.getRankList());
		// checks that wildList is cleared since "Joker" is not in the passed rankList
		ArrayList<String> expectedWilds = new ArrayList<>();
		expectedWilds.add("Two");
		assertEquals("Deck's setRankList() did not remove wilds not in the new rankList set from its wildList",
				expectedWilds, deck.getWildList());
		// null case
		assertFalse("Deck's setRankList() does not return false when passed null", deck.setRankList(null));
	}

	@Test
	public void getAndSetWildListTest() {
		// Arrays to initialize list elements
		String[] rankArray = { "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
				"Draw Two", "Skip", "Reverse", "Wild", "Wild Draw Four" };
		String[] inputWildArray = { "Wild", "Joker", "Wild Draw Four" };
		// initialize test deck
		PokerDeck deck = new PokerDeck();
		// set a new wildList for the Deck
		deck.setRankList(Deck.asArrayList(rankArray));
		assertTrue("Deck's setWildList() does not return true when passed a non-null ArrayList<String>", deck.setWildList(Deck.asArrayList(inputWildArray)));
		// checks instantiated wildList matches that given without "Joker" in it
		String[] outputWildArray = { "Wild", "Wild Draw Four" };
		assertEquals("Deck's instantiated wild list does not match that given in the constructor",
				Deck.asArrayList(outputWildArray), deck.getWildList());
		// null case
		assertFalse("Deck's setWildList() does not return false when passed null", deck.setWildList(null));
	}

	@Test
	public void getAndSetSuitListTest() {
		// Array to initialize list elements
		String[] suitArray = { "Red", "Yellow", "Green", "Blue" };
		// initialize test deck and test cards
		PokerDeck deck = new PokerDeck();
		// set a new suitList for the Deck
		assertTrue("Deck's setSuitList() does not return true when passed a non-null ArrayList<String>", deck.setSuitList(Deck.asArrayList(suitArray)));
		// checks that deck's suitList matches that set
		assertEquals("Deck's suit list does not match that given in the setter", Deck.asArrayList(suitArray),
				deck.getSuitList());
		// null case
		assertFalse("Deck's setSuitList() does not return false when passed null", deck.setSuitList(null));
	}
	
	@Test
	public void getAndSetBackListTest() {
		// Array to initialize list elements
		String[] backArray = { "Red", "Black" };
		// initialize test deck and test cards
		PokerDeck deck = new PokerDeck();
		// set a new backList for the Deck
		assertTrue("Deck's setBackList() does not return true when passed a non-null ArrayList<String>", deck.setBackList(Deck.asArrayList(backArray)));
		// checks that deck's backList matches that set
		assertEquals("Deck's back list does not match that given in the setter", Deck.asArrayList(backArray),
				deck.getBackList());
		// null case
		assertFalse("Deck's setBackList() does not return false when passed null", deck.setBackList(null));
	}

	@Test
	public void getAndSetCardsTest() {
		// initialize test deck and test ArrayList<Deck.Card>
		PokerDeck deck = new PokerDeck();
		ArrayList<Deck.Card> sampleCards = new ArrayList<>();
		sampleCards.add(deck.new PokerCard("Two", "Diamonds"));
		sampleCards.add(deck.new PokerCard("Ace", "Spades"));
		// set deck's cards with setter
		assertTrue("Deck's setCards() does not return true when passed a non-null ArrayList<Deck.Card>", deck.setCards(sampleCards));
		// checks that deck's cards matches that set
		assertEquals("Deck's cards do not match those given in the setter", sampleCards, deck.getCards());
		// null case
		assertFalse("Deck's setCards() does not return false when passed null", deck.setCards(null));
	}
	
	@Test
	public void getAndSwitchJokerColorTest() {
		// initialize test deck and test ArrayList<Deck.Card>
		PokerDeck deck = new PokerDeck();
		// check that default jokerColor is 0
		assertEquals("PokerDeck's default jokerColor is not 'Black'", "Black", deck.getJokerColor());
		// flip poker deck's joker color
		deck.switchJokerColor();
		// checks that deck's jokercolor is 1
		assertEquals("PokerDeck's jokerColor is not 'Red' after one flip", "Red", deck.getJokerColor());
		// flip and check again
		deck.switchJokerColor();
		assertEquals("PokerDeck's jokerColor is not 'Black' after two flips", "Black", deck.getJokerColor());
	}
	
	@Test
	public void createCardWithBackTest() {
		// initialize test deck and test ArrayList<Deck.Card> for comparison
		PokerDeck deck = new PokerDeck();
		ArrayList<PokerDeck.PokerCard> expectedCards = new ArrayList<>();
		// check default createCard()'s logic flow (with back!!)
		assertFalse("Deck's createCard() with back allows cards with ranks not in the rankList to be created",
				deck.createCard("Go", "Spades", "Gray"));
		assertFalse("Deck's createCard() with back allows cards with suits not in the suitList to be created",
				deck.createCard("Ace", "Fish", "Gray"));
		assertFalse("Deck's createCard() with back allows cards with backs not in the backList to be created",
				deck.createCard("Ace", "Spades", "Yeeeet"));
		assertTrue("Deck's createCard() with back does not allow blank cards to be created", deck.createCard("", "", ""));
		assertTrue(
				"Deck's createCard()  with back does not allow cards with ranks, suits, and backs in Deck's respective lists to be created",
				deck.createCard("Ace", "Spades", "Gray"));
		// check expected changes in Deck's cards ArrayList
		expectedCards.add(deck.new PokerCard("", "", ""));
		expectedCards.add(deck.new PokerCard("Ace", "Spades", "Gray"));
		assertEquals("Deck's createCard() does not correctly add created cards to Deck's cards ArrayList",
				expectedCards, deck.getCards());
		// null cases
		assertFalse("Deck's createCard() with back does not return false when the passed rank is null", deck.createCard(null, "Spades", "Gray"));
		assertFalse("Deck's createCard() with back does not return false when the passed suit is null", deck.createCard("Joker", null, "Gray"));
		assertFalse("Deck's createCard() with back does not return false when the passed back is null", deck.createCard("Two", "Spades", null));
		assertFalse("Deck's createCard() does not return false when the passed fields are null", deck.createCard(null, null, null));
		// check overloaded createCard()'s logic flow
		assertFalse("Deck's overloaded createCard() with back allows cards with ranks not in the rankList to be created",
				deck.createCard("Go", "Spades", "Red", 7));
		assertFalse("Deck's overloaded createCard() with back allows cards with suits not in the suitList to be created",
				deck.createCard("Ace", "Fish", "Red", 6));
		assertFalse("Deck's overloaded createCard() with back allows cards with backs not in the backList to be created",
				deck.createCard("Ace", "Spades", "big yeet", 6));
		assertTrue("Deck's overloaded createCard() with back does not allow blank cards to be created",
				deck.createCard("", "", "", 3));
		assertTrue(
				"Deck's overloaded createCard() with back does not allow cards with ranks, suits, and backs in Deck's respective lists to be created",
				deck.createCard("Two", "Diamonds", "Red", 2));
		// check expected changes in Deck's cards ArrayList
		expectedCards.add(deck.new PokerCard("", "", ""));
		expectedCards.add(deck.new PokerCard("", "", ""));
		expectedCards.add(deck.new PokerCard("", "", ""));
		expectedCards.add(deck.new PokerCard("Two", "Diamonds", "Red"));
		expectedCards.add(deck.new PokerCard("Two", "Diamonds", "Red"));
		assertEquals("Deck's overloaded createCard() with back does not correctly add created cards to Deck's cards ArrayList",
				expectedCards, deck.getCards());
		// null cases
		assertFalse("Deck's overloaded createCard() with back does not return false when the passed rank is null", deck.createCard(null, "Spades", "Red", 1));
		assertFalse("Deck's overloaded createCard() with back does not return false when the passed suit is null", deck.createCard("Joker", null, "Red", 2));
		assertFalse("Deck's overloaded createCard() with back does not return false when the passed back is null", deck.createCard("Two", "Spades", null, 2));
		assertFalse("Deck's overloaded createCard() with back does not return false when the passed fields are null", deck.createCard(null, null, null, 3));
	}

	@Test
	public void createCardWithoutBackTest() {
		// initialize test deck and test ArrayList<Deck.Card> for comparison
		PokerDeck deck = new PokerDeck();
		ArrayList<Deck.Card> expectedCards = new ArrayList<>();
		// check default createCard()'s logic flow
		assertFalse("Deck's createCard() allows cards with ranks not in the rankList to be created",
				deck.createCard("Go", "Spades"));
		assertFalse("Deck's createCard() allows cards with suits not in the suitList to be created",
				deck.createCard("Ace", "Fish"));
		assertTrue("Deck's createCard() does not allow blank cards to be created", deck.createCard("", ""));
		assertTrue(
				"Deck's createCard() does not allow cards with ranks and suits in Deck's rankList and suitList to be created",
				deck.createCard("Ace", "Spades"));
		// check expected changes in Deck's cards ArrayList
		expectedCards.add(deck.new Card("", ""));
		expectedCards.add(deck.new Card("Ace", "Spades"));
		assertEquals("Deck's createCard() does not correctly add created cards to Deck's cards ArrayList",
				expectedCards, deck.getCards());
		// null cases
		assertFalse("Deck's createCard() does not return false when the passed rank is null", deck.createCard(null, "Spades"));
		assertFalse("Deck's createCard() does not return false when the passed suit is null", deck.createCard("Joker", null));
		assertFalse("Deck's createCard() does not return false when the passed rank and suit is null", deck.createCard(null, null));
		// check overloaded createCard()'s logic flow
		assertFalse("Deck's overloaded createCard() allows cards with ranks not in the rankList to be created",
				deck.createCard("Go", "Spades", 7));
		assertFalse("Deck's overloaded createCard() allows cards with suits not in the suitList to be created",
				deck.createCard("Ace", "Fish", 6));
		assertTrue("Deck's overloaded createCard() does not allow blank cards to be created",
				deck.createCard("", "", 3));
		assertTrue(
				"Deck's overloaded createCard() does not allow cards with ranks and suits in Deck's rankList and suitList to be created",
				deck.createCard("Joker", "", 2));
		// check expected changes in Deck's cards ArrayList
		expectedCards.add(deck.new Card("", ""));
		expectedCards.add(deck.new Card("", ""));
		expectedCards.add(deck.new Card("", ""));
		expectedCards.add(deck.new Card("Joker", ""));
		expectedCards.add(deck.new Card("Joker", ""));
		assertEquals("Deck's overloaded createCard() does not correctly add created cards to Deck's cards ArrayList",
				expectedCards, deck.getCards());
		// null cases
		assertFalse("Deck's overloaded createCard() does not return false when the passed rank is null", deck.createCard(null, "Spades", 1));
		assertFalse("Deck's overloaded createCard() does not return false when the passed suit is null", deck.createCard("Joker", null, 2));
		assertFalse("Deck's overloaded createCard() does not return false when the passed rank and suit is null", deck.createCard(null, null, 3));
	}
	
	@Test
	public void countCardWithRankOrSuitOrBackTest() {
		// initialize test deck and create Cards for counting
		PokerDeck deck = new PokerDeck();
		deck.createCard("Queen", "Hearts", "Gray", 2);
		deck.createCard("Queen", "Diamonds", "Red", 3);
		deck.createCard("Seven", "Hearts", "Red", 11);
		deck.createCard("Seven", "Diamonds", "Gray", 7);
		// check that count card, well, counts cards correctly
		assertEquals("Deck's single-field countCard() does not correctly count all the cards with the passed rank", 5,
				deck.countCard("Queen"));
		assertEquals("Deck's single-field countCard() does not correctly count all the cards with the passed suit", 13,
				deck.countCard("Hearts"));
		assertEquals("Deck's single-field countCard() does not correctly count all the cards with the passed back", 14,
				deck.countCard("Red"));
		assertEquals("Deck's single-field countCard() does return zero when given a rank or suit not present in the cards", 0,
				deck.countCard("Ace"));
		// null case
		assertEquals("Deck's single-field countCard() does not return zero when null is passed", 0, deck.countCard(null));
	}

	@Test
	public void countCardWithRankAndSuitTest() {
		// initialize test deck and create Cards for counting
		PokerDeck deck = new PokerDeck();
		deck.createCard("Queen", "Hearts", 2);
		deck.createCard("Queen", "Diamonds", 3);
		deck.createCard("Seven", "Hearts", 7);
		// check that count card, well, counts cards correctly
		assertEquals("Deck's double-field countCard() does not correctly count all the cards with the passed rank and suit", 2,
				deck.countCard("Queen", "Hearts"));
		assertEquals("Deck's double-field countCard() does not correctly count all the cards with the passed rank and suit", 3,
				deck.countCard("Queen", "Diamonds"));
		assertEquals("Deck's double-field countCard() does not correctly count all the cards with the passed rank and suit", 7,
				deck.countCard("Seven", "Hearts"));
		assertEquals("Deck's double-field countCard() does return zero when given a rank and suit not present in the cards", 0,
				deck.countCard("Ace", "Spades"));
		// null case
		assertEquals("Deck's double-field countCard() does not return zero when null is passed for the rank", 0, deck.countCard(null, "Hearts"));
		assertEquals("Deck's double-field countCard() does not return zero when null is passed for the suit", 0, deck.countCard("Ace", null));
		assertEquals("Deck's double-field countCard() does not return zero when null is passed for the rank and suit", 0, deck.countCard(null, null));
	}
	
	@Test
	public void countCardWithAllFieldsTest() {
		// initialize test deck and create Cards for counting
		PokerDeck deck = new PokerDeck();
		deck.createCard("Queen", "Hearts", "Gray", 2);
		deck.createCard("Queen", "Diamonds", "Gray", 3);
		deck.createCard("Seven", "Hearts", "Gray", 11);
		deck.createCard("Queen", "Hearts", "Red", 7);
		// check that count card, well, counts cards correctly
		assertEquals("Deck's triple-field countCard() does not correctly count all the cards with the passed rank and suit", 2,
				deck.countCard("Queen", "Hearts", "Gray"));
		assertEquals("Deck's triple-field countCard() does not correctly count all the cards with the passed rank and suit", 3,
				deck.countCard("Queen", "Diamonds", "Gray"));
		assertEquals("Deck's triple-field countCard() does not correctly count all the cards with the passed rank and suit", 11,
				deck.countCard("Seven", "Hearts", "Gray"));
		assertEquals("Deck's triple-field countCard() does not correctly count all the cards with the passed rank and suit", 7,
				deck.countCard("Queen", "Hearts", "Red"));
		assertEquals("Deck's triple-field countCard() does return zero when given a field not present in the cards", 0,
				deck.countCard("Ace", "Spades", "Gray"));
		// null case
		assertEquals("Deck's triple-field countCard() does not return zero when null is passed for the rank", 0, deck.countCard(null, "Hearts", "Gray"));
		assertEquals("Deck's triple-field countCard() does not return zero when null is passed for the suit", 0, deck.countCard("Queen", null, "Gray"));
		assertEquals("Deck's triple-field countCard() does not return zero when null is passed for the back", 0, deck.countCard("Queen", "Hearts", null));
		assertEquals("Deck's triple-field countCard() does not return zero when null is passed for all fields", 0, deck.countCard(null, null, null));
	}
	
	@Test
	public void deleteCardTest() {
		// initialize test deck and create Cards for deleting
		PokerDeck deck = new PokerDeck();
		deck.createCard("Three", "Clubs", "Gray", 2);
		deck.createCard("Five", "Clubs", "Red", 2);
		deck.createCard("Three", "Spades", "Gray", 1);
		deck.createCard("Five", "Spades", "Red", 2);
		deck.createCard("Five", "Spades", "Gray", 3);
		deck.createCard("Three", "Spades", "Gray", 3);
		deck.createCard("Five", "Clubs", "Gray", 1);
		// initialize ArrayList for comparison
		ArrayList<Deck.Card> expectedCards = new ArrayList<>();
		expectedCards.add(deck.new Card("Five", "Spades", "Red"));
		expectedCards.add(deck.new Card("Five", "Spades", "Red"));
		// check default deleteCard()'s logic flow
		assertTrue("Deck's deleteCard() does not return true when passed a rank present in cards",
				deck.deleteCard("Three"));
		assertFalse("Deck's deleteCard() does not return false when passed a rank not present in cards",
				deck.deleteCard("Three"));
		assertTrue("Deck's deleteCard() does not return true when passed a suit present in cards",
				deck.deleteCard("Clubs"));
		assertFalse("Deck's deleteCard() does not return false when passed a suit not present in cards",
				deck.deleteCard("Clubs"));
		assertTrue("Deck's deleteCard() does not return true when passed a back present in cards",
				deck.deleteCard("Gray"));
		assertFalse("Deck's deleteCard() does not return false when passed a back not present in cards",
				deck.deleteCard("Gray"));
		// check expected changes in Deck's cards ArrayList
		assertEquals(
				"Deck's deleteCard() does not correctly remove cards with the passed field in the cards ArrayList",
				expectedCards, deck.getCards());
		// null cases
		assertFalse("Deck's deleteCard() does not return false when passed null", deck.deleteCard(null));
	}
	
	@Test
	public void deleteCardWithRankAndSuitTest() {
		// initialize test deck and create Cards for deleting
		PokerDeck deck = new PokerDeck();
		deck.createCard("Three", "Clubs", 2);
		deck.createCard("Five", "Clubs", 2);
		deck.createCard("Three", "Spades", 1);
		deck.createCard("Five", "Spades", 2);
		deck.createCard("Five", "Clubs", 1);
		deck.createCard("Three", "Spades", 3);
		deck.createCard("Queen", "Hearts");
		// initialize ArrayList for comparison
		ArrayList<Deck.Card> expectedCards = new ArrayList<>();
		expectedCards.add(deck.new Card("Three", "Clubs"));
		expectedCards.add(deck.new Card("Three", "Clubs"));
		expectedCards.add(deck.new Card("Five", "Clubs"));
		expectedCards.add(deck.new Card("Queen", "Hearts"));
		// check default deleteCard()'s logic flow
		assertTrue("Deck's deleteCard() does not return true when passed a suit and rank present in cards",
				deck.deleteCard("Three", "Spades"));
		assertFalse("Deck's deleteCard() does not return false when passed a suit and rank not present in cards",
				deck.deleteCard("Three", "Spades"));
		// check overloaded deleteCard()'s logic flow
		assertTrue("Deck's overloaded deleteCard() does not return true when passed a suit, rank, and quantity less than current count present in cards",
				deck.deleteCard("Five", "Clubs", 2));
		assertFalse(
				"Deck's overloaded deleteCard() does not return false when passed a quantity and of suit and rank not present in cards",
				deck.deleteCard("Five", "Spades", 3));
		assertTrue("Deck's overloaded deleteCard() does not return true when passed a suit, rank, and quantity less than current count present in cards",
				deck.deleteCard("Five", "Spades", 2));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed a quantity of 0 or less",
				deck.deleteCard("Three", "Clubs", 0));
		// check expected changes in Deck's cards ArrayList
		assertEquals(
				"Deck's deleteCard() does not correctly remove cards with the passed rank and suit in the cards ArrayList",
				expectedCards, deck.getCards());
		// null cases
		assertFalse("Deck's deleteCard() does not return false when passed a null rank", deck.deleteCard(null, "Clubs"));
		assertFalse("Deck's deleteCard() does not return false when passed a null suit", deck.deleteCard("Five", null));
		assertFalse("Deck's deleteCard() does not return false when passed a null rank and suit", deck.deleteCard(null, null));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed a null rank", deck.deleteCard(null, "Clubs", 3));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed a null suit", deck.deleteCard("Five", null, 1));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed a null rank and suit", deck.deleteCard(null, null, 2));
	}
	
	@Test
	public void deleteCardWithAllFieldsTest() {
		// initialize test deck and create Cards for deleting
		PokerDeck deck = new PokerDeck();
		deck.createCard("Three", "Clubs", "Gray", 2);
		deck.createCard("Five", "Clubs", "Red", 2);
		deck.createCard("Three", "Spades", "Gray", 1);
		deck.createCard("Five", "Spades", "Red", 2);
		deck.createCard("Three", "Spades", "Gray", 3);
		deck.createCard("Five", "Clubs", "Gray", 1);
		// initialize ArrayList for comparison
		ArrayList<Deck.Card> expectedCards = new ArrayList<>();
		expectedCards.add(deck.new Card("Three", "Clubs", "Gray"));
		expectedCards.add(deck.new Card("Three", "Clubs", "Gray"));
		expectedCards.add(deck.new Card("Five", "Clubs", "Red"));
		expectedCards.add(deck.new Card("Five", "Clubs", "Gray"));
		// check default deleteCard()'s logic flow
		assertTrue("Deck's deleteCard() does not return true when passed fields present in cards",
				deck.deleteCard("Three", "Spades", "Gray"));
		assertFalse("Deck's deleteCard() does not return false when passed fields not present in cards",
				deck.deleteCard("Three", "Spades", "Gray"));
		// check overloaded deleteCard()'s logic flow
		assertTrue("Deck's overloaded deleteCard() does not return true when passed a suit, rank, and quantity less than current count present in cards",
				deck.deleteCard("Five", "Clubs", "Red", 1));
		assertFalse(
				"Deck's overloaded deleteCard() does not return false when passed a quantity and of suit and rank not present in cards",
				deck.deleteCard("Five", "Spades", "Red", 3));
		assertTrue("Deck's overloaded deleteCard() does not return true when passed a suit, rank, and quantity less than current count present in cards",
				deck.deleteCard("Five", "Spades", "Red", 2));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed a quantity of 0 or less",
				deck.deleteCard("Three", "Clubs", "Gray", 0));
		// check expected changes in Deck's cards ArrayList
		assertEquals(
				"Deck's deleteCard() does not correctly remove cards with the passed fields in the cards ArrayList",
				expectedCards, deck.getCards());
		// null cases
		assertFalse("Deck's deleteCard() does not return false when passed a null rank", deck.deleteCard(null, "Clubs", "Gray"));
		assertFalse("Deck's deleteCard() does not return false when passed a null suit", deck.deleteCard("Five", null, "Gray"));
		assertFalse("Deck's deleteCard() does not return false when passed a null back", deck.deleteCard("Five", "Clubs", null));
		assertFalse("Deck's deleteCard() does not return false when passed null fields", deck.deleteCard(null, null, null));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed a null rank", deck.deleteCard(null, "Clubs", "Gray", 3));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed a null suit", deck.deleteCard("Five", null, "Gray", 1));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed a null suit", deck.deleteCard("Five", "Clubs", null, 1));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed null fields", deck.deleteCard(null, null, null, 2));
	}

	@Test
	public void fillDeckTest() {
		// initialize test deck and create Cards to check additions and deletions
		PokerDeck deck = new PokerDeck();
		deck.createCard("Eight", "Hearts", "Gray");
		deck.createCard("Three", "Spades", "Red", 3);
		deck.createCard("Eight", "Hearts", "Red");
		// initialize ArrayList for comparison
		ArrayList<Deck.Card> expectedCards = new ArrayList<>();
		// add 2 of each regular card and 4 of each wild card to the expected Cards
		for (String rank : deck.getRankList()) {
			if (deck.getWildList().contains(rank)) {
				for (int i = 0; i < 4; i++) {
					expectedCards.add(deck.new Card(rank, "", "Red"));
				}
			} else {
				for (String suit : deck.getSuitList()) {
					expectedCards.add(deck.new Card(rank, suit, "Red"));
					expectedCards.add(deck.new Card(rank, suit, "Red"));
				}
			}
		}
		expectedCards.add(deck.new Card("Eight", "Hearts", "Gray"));
		Collections.sort(expectedCards);;
		// check two-field fillDeck()'s logic flow
		assertTrue("Deck's fillDeck() does not return true when cards is empty and two nonzero ints are passed",
				deck.fillDeck(2, 4, "Red"));
		assertFalse(
				"Deck's fillDeck() does not return false when cards already has the passed number of regular and wild cards",
				deck.fillDeck(2, 4, "Red"));
		// check expected changes in Deck's cards ArrayList
		assertEquals("Deck's fillDeck() does not correctly modify the cards ArrayList", expectedCards, deck.getCards());
		// check expected changes in Deck's cards ArrayList with one-field fillDeck()
		assertTrue(
				"Deck's fillDeck() does not return true when cards has a differing amount of regular and/or wild cards from that passed",
				deck.fillDeck(0, "Red"));
		expectedCards.clear();
		expectedCards.add(deck.new Card("Eight", "Hearts", "Gray"));
		assertEquals("Deck's fillDeck() does not empty the cards ArrayList of cards with the passed back when 0s are both passed", expectedCards,
				deck.getCards());
		// check expected changes in Deck's cards ArrayList with the default fillDeck()
		assertTrue(
				"Deck's fillDeck() does not return true when cards has a differing amount of regular and/or wild cards from that passed",
				deck.fillDeck());
		// add 1 of each regular card and 2 of each wild card to expectedCards
		expectedCards.clear();
		for (String rank : deck.getRankList()) {
			if (deck.getWildList().contains(rank)) {
				expectedCards.add(deck.new Card(rank, "", "Gray"));
				expectedCards.add(deck.new Card(rank, "", "Gray"));
			} else {
				for (String suit : deck.getSuitList()) {
					expectedCards.add(deck.new Card(rank, suit, "Gray"));
				}
			}
		}
		assertEquals("Deck's default fillDeck() does not correctly modify the cards ArrayList", expectedCards,
				deck.getCards());
		// null case
		assertFalse(
				"Deck's fillDeck() does not return false when already filled with the default back and null is passed for the back",
				deck.fillDeck(null));
		// non-appropriate back case
		assertFalse(
				"Deck's fillDeck() does not return false when already filled with the default back and null is passed for the back",
				deck.fillDeck("Blurple"));
		// single-field numRegular case
		assertTrue(
				"Deck's fillDeck() does not return true when passed a single int when wilds are present in the deck",
				deck.fillDeck(2));
		expectedCards.clear();
		for (String rank : deck.getRankList()) {
			if (!deck.getWildList().contains(rank)) {
				for (String suit : deck.getSuitList()) {
					expectedCards.add(deck.new Card(rank, suit));
					expectedCards.add(deck.new Card(rank, suit));
				}
			}
		}
	}
	
	@Test
	public void isEmptyTest() {
		// initialize test deck
		PokerDeck deck = new PokerDeck();
		// check it returns true when cards is empty
		assertTrue("Deck's isEmpty() does not return true when cards is empty", deck.isEmpty());
		// check it return false when cards is not empty
		deck.createCard("Joker", "");
		assertFalse("Deck's isEmpty() does not return false when cards not empty", deck.isEmpty());
	}

	@Test
	public void emptyDeckTest() {
		// initialize test deck and fill deck with cards for emptying
		PokerDeck deck = new PokerDeck();
		deck.fillDeck();
		// initialize ArrayList for comparison
		ArrayList<Deck.Card> expectedCards = new ArrayList<>();
		// check emptyDeck()'s logic flow
		assertTrue("Deck's emptyDeck() does not return true when cards is not empty", deck.emptyDeck());
		assertFalse("Deck's emptyDeck() does not return false when cards is empty", deck.emptyDeck());
		// check expected changes in Deck's cards ArrayList
		assertEquals("Deck's emptyDeck() does not correctly empty its cards ArrayList", expectedCards, deck.getCards());
	}

	@Test
	public void toStringTest() {
		// initialize test decks and fill them with cards
		PokerDeck deck = new PokerDeck();
		// empty deck case
		assertEquals("PokerDeck's toString() does not return the correct String when empty",
				"0-card poker deck", deck.toString());
		// full deck case
		deck.fillDeck();
		assertEquals("PokerDeck's toString() does not return the correct String when empty",
				"54-card poker deck", deck.toString());
	}

	@Test
	public void equalsTest() {
		// Instantiate test cases for each logic flow
		PokerDeck deck = new PokerDeck();
		Table table = new Table();
		Deck deckWithDifferentRanks = new PokerDeck();
		deckWithDifferentRanks.getRankList().remove("Joker");
		Deck deckWithDifferentWilds = new PokerDeck();
		deckWithDifferentWilds.getWildList().add("Two");
		Deck deckWithDifferentSuits = new PokerDeck();
		deckWithDifferentSuits.getSuitList().add("Pringles");
		Deck deckWithDifferentBacks = new PokerDeck();
		deckWithDifferentBacks.getBackList().add("Cooool");
		Deck fullDeck = new Deck(Deck.asArrayList(PokerDeck.RANKS), Deck.asArrayList(PokerDeck.WILDS), Deck.asArrayList(PokerDeck.SUITS), Deck.asArrayList(PokerDeck.BACKS));
		fullDeck.fillDeck();
		Collections.sort(fullDeck.getRankList());
		Collections.sort(fullDeck.getWildList());
		Collections.sort(fullDeck.getSuitList());
		Collections.sort(fullDeck.getBackList());
		// Test each for expected outcome
		assertFalse("Deck equals() does not return false when given null pointer", deck.equals(null));
		assertFalse("Deck equals() does not return false when not given a Table", deck.equals(table));
		assertFalse("Deck equals() does not return false when given Deck with different ranks",
				deck.equals(deckWithDifferentRanks));
		assertFalse("Deck equals() does not return false when given Deck with different wilds",
				deck.equals(deckWithDifferentWilds));
		assertFalse("Deck equals() does not return false when given Deck with different suits",
				deck.equals(deckWithDifferentSuits));
		assertFalse("Deck equals() does not return false when given Deck with different backs",
				deck.equals(deckWithDifferentBacks));
		assertTrue(
				"Deck equals() does not return true when given Deck with same ranks, wilds, and suits (regardless of order or # of cards)",
				deck.equals(fullDeck));
	}

	@Test
	public void compareToTest() {
		// Instantiate test cases for each logic flow
		Deck deck = new PokerDeck();
		Deck deckWithFewerRanks = new PokerDeck();
		deckWithFewerRanks.getRankList().remove("Two");
		Deck deckWithFewerSuits = new PokerDeck();
		deckWithFewerSuits.getSuitList().remove("Clubs");
		Deck deckWithMoreWilds = new PokerDeck();
		deckWithMoreWilds.getWildList().add("Two");
		Deck deckWithMoreBacks = new PokerDeck();
		deckWithMoreBacks.getBackList().add("Purple");
		Deck deckWithMoreCards = new PokerDeck();
		deckWithMoreCards.fillDeck();
		Deck sameDeck = new Deck(Deck.asArrayList(PokerDeck.RANKS), Deck.asArrayList(PokerDeck.WILDS), Deck.asArrayList(PokerDeck.SUITS), Deck.asArrayList(PokerDeck.BACKS));
		// Order tests by comparison order
		assertTrue("Two Decks with different ranks are not sorted in descending order by number of ranks",
				deck.compareTo(deckWithFewerRanks) < 0);
		assertTrue("Two Decks with different suits are not sorted in descending order by number of suits",
				deck.compareTo(deckWithFewerSuits) < 0);
		assertTrue("Two Decks with different wilds are not sorted in descending order by number of wilds",
				deck.compareTo(deckWithMoreWilds) > 0);
		assertTrue("Two Decks with different backs are not sorted in descending order by number of backs",
				deck.compareTo(deckWithMoreBacks) > 0);
		assertTrue("Two Decks with different cards are not sorted in descending order by number of cards",
				deck.compareTo(deckWithMoreCards) > 0);
		assertEquals("Two Cards with the same ranks, suits, wilds, and cards are not sorted together", 0,
				deck.compareTo(sameDeck));
	}
	
	@Test
	public void isSortedTest() {
		// instantiate test decks
		Deck deck = new PokerDeck();
		// empty deck case
		assertTrue("Deck's isSorted() does not return true when Deck's cards is empty", deck.isSorted());
		// non-empty, in order deck case
		deck.createCard("Joker", "");
		deck.createCard("Eight", "Clubs");
		deck.createCard("Queen", "Hearts");
		assertTrue("Deck's isSorted() does not return true when Deck's cards are in natural ascending order", deck.isSorted());
		// non-empty, out of order deck case
		deck.createCard("Joker", "");
		deck.createCard("Ace", "Spades");
		assertFalse("Deck's isSorted does not return false when Deck's cards are not in natural ascending order", deck.isSorted());
	}
	
	@Test
	public void sortTest() {
		// instantiate test deck
		Deck deck = new PokerDeck();
		// empty deck case for logic flow
		assertFalse("Deck's sort() does not return false when Deck's cards is empty", deck.sort());
		// non-empty, out of order deck case
		deck.createCard("Joker", "");
		deck.createCard("Eight", "Clubs");
		deck.createCard("Queen", "Hearts");
		deck.createCard("Joker", "");
		deck.createCard("Ace", "Spades");
		assertTrue("Deck's sort() does not return true when Deck's cards are not in natural ascending order", deck.sort());
		// non-empty, in-order deck case
		assertFalse("Deck's sort() does not return false when Deck's cards are in natural ascending order", deck.sort());
		// check for expected order of the cards
		String[] rankArray = { "Joker", "Joker", "Eight", "Queen", "Ace" };
		String[] suitArray = { "", "", "Clubs", "Hearts", "Spades" };
		ArrayList<Deck.Card> expectedCards = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			expectedCards.add(deck.new Card(rankArray[i], suitArray[i]));
		}
		assertEquals("Deck's sort() does not correctly modify Deck's cards when they are not in order", expectedCards, deck.getCards());
	}
	
	@Test
	public void shuffleTest() {
		// instantiate test deck and test cards
		Deck deck = new PokerDeck();
		deck.fillDeck();
		deck.shuffle();
		// check if sorted after shuffling (note: has improbable chance of failing)
		assertFalse("Deck's shuffle() did not place cards in some non-natural ascending order", deck.isSorted());
	}

	@Test
	public void defaultCardConstructorTest() {
		// initialize test deck and test card
		PokerDeck deck = new PokerDeck();
		PokerDeck.PokerCard card = deck.new PokerCard();
		assertEquals("PokerCard's default rank is not 'Ace'", "Ace", card.getRank());
		assertEquals("PokerCard's default suit is not 'Spades'", "Spades", card.getSuit());
		assertEquals("PokerCard's default back is not 'Gray'", "Gray", card.getBack());
		// checks default faceUp is true
		assertTrue("PokerCard's default faceUp is not true", card.isFaceUp());
	}

	@Test
	public void cardConstructorWithRankAndSuitTest() {
		// initialize test deck and test cards
		PokerDeck deck = new PokerDeck();
		// empty case
		Deck.Card blankCard = deck.new PokerCard("Go", "Fish");
		// checks instantiated rank is an empty String
		assertEquals("Card's instantiated rank is not an empty String when passed rank is not in Deck's rankList", "",
				blankCard.getRank());
		// checks instantiated suit is an empty String
		assertEquals("Card's instantiated suit is not an empty String when passed suit is not in Deck's suitList", "",
				blankCard.getSuit());
		// checks instantiated back is an empty String
		assertEquals("Card's instantiated back is not an empty String when not specified", "",
				blankCard.getBack());
		// checks instantiated faceUp is true
		assertTrue("PokerCard's default faceUp is not true", blankCard.isFaceUp());
		// full case
		Deck.Card sampleCard = deck.new PokerCard("Ace", "Spades");
		// checks instantiated rank matches that given in constructor
		assertEquals("Card's instantiated rank does not match that given in constructor", "Ace", sampleCard.getRank());
		// checks instantiated suit matches that given in constructor
		assertEquals("Card's instantiated suit does not match that given in constructor", "Spades",
				sampleCard.getSuit());
		// wild case
		Deck.Card wildCard = deck.new PokerCard("Joker", "Spades");
		// checks instantiated suit is blank since Joker is a wild card
		assertEquals("Card's instantiated suit is not an empty String when passed a rank in Deck's wildList", "",
				wildCard.getSuit());
		// checks instantiated faceUp is true
		assertTrue("Card's default faceUp is not true", sampleCard.isFaceUp());
		// null case
		Deck.Card emptyCard = deck.new PokerCard(null, null);
		assertEquals("Card's instantiated rank is not an empty String when passed null", "",
				emptyCard.getRank());
		assertEquals("Card's instantiated suit is not an empty String when passed null", "",
				emptyCard.getSuit());
		// checks instantiated back is an Empty String
		assertEquals("Card's instantiated back is not an empty String when not specified", "",
				emptyCard.getBack());

	}
	
	@Test
	public void cardConstructorWithAllFieldsTest() {
		// initialize test deck and test cards
		PokerDeck deck = new PokerDeck();
		// empty case
		Deck.Card blankCard = deck.new PokerCard("Go", "Fish", "Please");
		// checks instantiated rank is an empty String
		assertEquals("Card's instantiated rank is not an empty String when passed rank is not in Deck's rankList", "",
				blankCard.getRank());
		// checks instantiated suit is an empty String
		assertEquals("Card's instantiated suit is not an empty String when passed suit is not in Deck's suitList", "",
				blankCard.getSuit());
		// checks instantiated back is the first back
		assertEquals("Card's instantiated back is not an empty String when passed back is not in Deck's backList", "",
				blankCard.getBack());
		// checks instantiated faceUp is true
		assertTrue("PokerCard's default faceUp is not true", blankCard.isFaceUp());
		// full case
		Deck.Card sampleCard = deck.new PokerCard("Ace", "Spades", "Red");
		// checks instantiated rank matches that given in constructor
		assertEquals("Card's instantiated rank does not match that given in constructor", "Ace", sampleCard.getRank());
		// checks instantiated suit matches that given in constructor
		assertEquals("Card's instantiated suit does not match that given in constructor", "Spades",
				sampleCard.getSuit());
		// checks instantiated back matches that given in constructor
		assertEquals("Card's instantiated back does not match that given in constructor", "Red",
				sampleCard.getBack());
		// wild case
		Deck.Card wildCard = deck.new PokerCard("Joker", "Spades", "Gray");
		// checks instantiated suit is blank since Joker is a wild card
		assertEquals("Card's instantiated suit is not an empty String when passed a rank in Deck's wildList", "",
				wildCard.getSuit());
		// checks instantiated faceUp is true
		assertTrue("Card's default faceUp is not true", sampleCard.isFaceUp());
		// null case
		Deck.Card emptyCard = deck.new PokerCard(null, null, null);
		assertEquals("Card's instantiated rank is not an empty String when passed null", "",
				emptyCard.getRank());
		assertEquals("Card's instantiated suit is not an empty String when passed null", "",
				emptyCard.getSuit());
		// checks instantiated back is an empty String
		assertEquals("Card's instantiated back is not an empty String when passed null", "",
				emptyCard.getBack());
	}

	@Test
	public void getAndSetCardRankTest() {
		// initialize test deck and test card
		PokerDeck deck = new PokerDeck();
		Deck.Card card = deck.new PokerCard();
		// checks that card cannot be set with a rank not in Deck's rankList
		assertFalse("Card's setRank() does not return false when passed a rank not in the Deck's rankList",
				card.setRank("Go"));
		// checks that card can be set with a rank in Deck's rankList
		assertTrue("Card's setRank() does not return true when passed a rank in the Deck's rankList",
				card.setRank("Two"));
		// checks instantiated rank matches that given in setter
		assertEquals("Card's rank does not match that given in setter", "Two", card.getRank());
		// null case
		assertFalse("Card's setRank() does not return false when null is passed", card.setRank(null));
	}

	@Test
	public void getAndSetCardSuitTest() {
		// initialize test deck and test card
		PokerDeck deck = new PokerDeck();
		Deck.Card card = deck.new PokerCard();
		// checks that card cannot be set with a suit not in Deck's suitList
		assertFalse("Card's setSuit() does not return false when passed a suit not in the Deck's suitList",
				card.setSuit("Fish"));
		// checks that card can be set with a suit in Deck's suitList
		assertTrue("Card's setSuit() does not return true when passed a suit in the Deck's suitList",
				card.setSuit("Hearts"));
		// checks instantiated suit matches that given in setter
		assertEquals("Card's suit does not match that given in setter", "Hearts", card.getSuit());
		// wild case
		Deck.Card wildCard = deck.new Card("Joker", "");
		// checks that card cannot be set with a suit when it is a wild card
		assertFalse("Card's setSuit() does not return false when the card's rank is in Deck's wildList",
				wildCard.setSuit("Hearts"));
		// null case
		assertFalse("Card's setSuit() does not return false when null is passed", card.setSuit(null));
	}
	
	@Test
	public void getAndSetCardBackTest() {
		// initialize test deck and test card
		PokerDeck deck = new PokerDeck();
		Deck.Card card = deck.new PokerCard();
		// checks that card cannot be set with a suit not in Deck's suitList
		assertFalse("Card's setBack() does not return false when passed a back not in the Deck's backList",
				card.setBack("Please"));
		// checks that card can be set with a suit in Deck's suitList
		assertTrue("Card's setBack() does not return true when passed a back in the Deck's backList",
				card.setBack("Green"));
		// checks instantiated suit matches that given in setter
		assertEquals("Card's back does not match that given in setter", "Green", card.getBack());
		// null case
		assertFalse("Card's setBack() does not return false when null is passed", card.setBack(null));
	}
	
	@Test
	public void getAndSetCardFaceUpTest() {
		// initialize test deck and test card
		PokerDeck deck = new PokerDeck();
		Deck.Card card = deck.new PokerCard();
		card.setFaceUp(false);
		// checks instantiated suit matches that given in setter
		assertFalse("Card's faceUp does not match that given in setter", card.isFaceUp());
	}
	
	@Test
	public void cardFlipTest() {
		// initialize test deck and test card
		PokerDeck deck = new PokerDeck();
		Deck.Card card = deck.new PokerCard();
		// card's default faceUp is true, so calling flip() should make it false
		card.flip();
		assertFalse("Card's flip() does not set its faceUp to false when it is true", card.isFaceUp());
		// and vice versa
		card.flip();
		assertTrue("Card's flip() does not set its faceUp to true when it is false", card.isFaceUp());
	}

	@Test
	public void cardToStringTest() {
		// initialize test deck
		PokerDeck deck = new PokerDeck();
		// Default case
		Deck.Card blankCard = deck.new PokerCard("", "", "");
		assertEquals("Card's toString() does not return 'Blank Card' when given default Card", "Blank Card",
				blankCard.toString());
		// Empty rank or suit case
		Deck.Card rankCard = deck.new PokerCard("Joker", "");
		assertEquals("Card's toString() does not return its rank when its suit is empty", "Joker", rankCard.toString());
		Deck.Card suitCard = deck.new PokerCard("", "Diamonds");
		assertEquals("Card's toString() does not return its suit when its rank is empty", "Diamonds",
				suitCard.toString());
		// Rank and suit case
		Deck.Card rankAndSuitCard = deck.new PokerCard("King", "Hearts");
		assertEquals("Card's toString() does not return '<rank> of <suit>' and its rank and suit are non-empty", "King of Hearts",
				rankAndSuitCard.toString());
		// Face-Down Blank Back case
		rankAndSuitCard.setFaceUp(false);
		assertEquals("Card's toString() does not return 'Blank Back' when face down with no back", "Blank Back",
				rankAndSuitCard.toString());
		// Face-Down not blank back case
		rankAndSuitCard.setBack("Purple");
		assertEquals("Card's toString() does not return '<back> Back' when face down with a non-empty back", "Purple Back",
				rankAndSuitCard.toString());
	}

	@Test
	public void cardEqualsTest() {
		// Instantiate test cases for each logic flow
		PokerDeck deck = new PokerDeck();
		Deck.Card card = deck.new PokerCard("", "", "");
		Table table = new Table();
		Deck.Card cardWithRank = deck.new PokerCard("Ace", "");
		Deck.Card cardWithSuit = deck.new PokerCard("", "Spades");
		Deck.Card cardWithBack = deck.new PokerCard("", "", "Blue");
		Deck.Card sameCard = deck.new Card();
		sameCard.setFaceUp(false);
		// Test each for expected outcome
		assertFalse("Card equals() does not return false when given null pointer", card.equals(null));
		assertFalse("Card equals() does not return false when not given a Table", card.equals(table));
		assertFalse("Card equals() does not return false when given Card with different rank",
				card.equals(cardWithRank));
		assertFalse("Card equals() does not return false when given Card with different suit",
				card.equals(cardWithSuit));
		assertFalse("Card equals() does not return false when given Card with different back",
				card.equals(cardWithBack));
		assertTrue("Card equals() does not return true when given Card with same fields", card.equals(sameCard));
		// note that sameCard is not even a PokerCard! PokerCard is a Card, so they're the same
	}

	@Test
	public void cardCompareToTest() {
		// Instantiate test cases for each logic flow
		PokerDeck deck = new PokerDeck();
		Deck.Card card = deck.new PokerCard("Two", "Hearts", "Red");
		Deck.Card cardWithRank = deck.new PokerCard("Joker", "", "Yellow");
		Deck.Card cardWithSuit = deck.new PokerCard("Two", "Spades", "Yellow");
		Deck.Card cardWithBack = deck.new PokerCard("Two", "Hearts", "Gray");
		Deck.Card sameCard = deck.new Card("Two", "Hearts", "Red");
		sameCard.setFaceUp(false);
		// Order tests by comparison order
		assertTrue("Two Cards with different ranks are not sorted in ascending order by index in Deck's rankList",
				card.compareTo(cardWithRank) > 0);
		assertTrue("Two Cards with different suits are not sorted in ascending order by index in Deck's suitList",
				card.compareTo(cardWithSuit) > 0);
		assertTrue("Two Cards with different backs are not sorted in ascending order by index in Deck's backList",
				card.compareTo(cardWithBack) > 0);
		assertEquals("Two Cards with the same rank, suit, and back are not sorted together regardless of faceUp", 0, card.compareTo(sameCard));
		// note that sameCard is not even a PokerCard! PokerCard is a Card, so they're the same
	}
	
	@Test
	public void cardGetImageFilePathTest() {
		// initialize test deck
		PokerDeck deck = new PokerDeck();
		// Default case
		Deck.Card sampleCard = deck.new PokerCard("Queen", "Hearts", "Gray");
		assertEquals("Card's getImageFilePath() does not return the image file folder path, the Card's toString(), and the image's file type", 
				"C:\\Users\\danie\\Pictures\\Cards\\Poker Deck\\Queen of Hearts.png",
				sampleCard.getImageFilePath());
		// Joker Case
		Deck.Card wildCard = deck.new PokerCard("Joker", "", "Gray");
		assertEquals("Card's getImageFilePath() does not add the appropriate joker color when getting the image file path for the card the first time", 
				"C:\\Users\\danie\\Pictures\\Cards\\Poker Deck\\Joker (Black).png",
				wildCard.getImageFilePath());
		assertEquals("Card's getImageFilePath() does not add the appropriate joker color when getting the image file path for the card the second time", 
				"C:\\Users\\danie\\Pictures\\Cards\\Poker Deck\\Joker (Red).png",
				wildCard.getImageFilePath());
		assertEquals("Card's getImageFilePath() does not add the appropriate joker color when getting the image file path for the card the third time", 
				"C:\\Users\\danie\\Pictures\\Cards\\Poker Deck\\Joker (Black).png",
				wildCard.getImageFilePath());
		wildCard.setFaceUp(false);
		assertEquals("Card's getImageFilePath() does not return the image file folder path, the Card's toString(), and the image's file type when the card is face down", 
				"C:\\Users\\danie\\Pictures\\Cards\\Poker Deck\\Gray Back.png",
				wildCard.getImageFilePath());
	}
}
