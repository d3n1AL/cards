import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

/**
 * Card Simulator Uno Deck Test
 * 
 * @author Daniel Xue 07/27/2021
 * 
 * Sources: UVA CS2110 Lectures,
 * UVA CS2110 HW 5 and 6
 * JDK 11 Documentation
 */

public class UnoDeckTest {

	@Test
	public void defaultConstructorTest() {
		// initialize test deck
		UnoDeck unoDeck = new UnoDeck();
		// checks default rankList is given by its RANKS array
		assertEquals("UnoDeck's default list of ranks is not given by its RANKS array",
				Deck.asArrayList(UnoDeck.RANKS), unoDeck.getRankList());
		// checks default wildList is given by its WILDS array
		assertEquals("UnoDeck's default list of wilds is not given by its WILDS array",
				Deck.asArrayList(UnoDeck.WILDS), unoDeck.getWildList());
		// checks default suitList is given by its SUITS array
		assertEquals("UnoDeck's default list of suits is not given by its SUITS array",
				Deck.asArrayList(UnoDeck.SUITS), unoDeck.getSuitList());
		// checks default backList is given by its BACKS array
		assertEquals("UnoDeck's default list of backs is not given by its BACKS array",
				Deck.asArrayList(UnoDeck.BACKS), unoDeck.getBackList());
		// checks default cards is an empty ArrayList
		assertEquals("UnoDeck's default cards is not an empty ArrayList<UnoCard>", new ArrayList<UnoDeck.UnoCard>(), unoDeck.getCards());
	}

	@Test
	public void getAndSetRankListTest() {
		// initialize test deck and test cards and wilds
		UnoDeck deck = new UnoDeck();
		deck.getWildList().add("Two");
		// set a new rankList for the Deck
		assertTrue("Deck's setRankList() does not return true when passed a non-null ArrayList<String>", deck.setRankList(Deck.asArrayList(PokerDeck.RANKS)));
		// checks instantiated rankList matches that given
		assertEquals("Deck's instantiated rank list does not match that given in the setter", Deck.asArrayList(PokerDeck.RANKS),
				deck.getRankList());
		// checks that wildList is cleared since "Wild" and "Wild Draw Four" are not in the passed rankList
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
		UnoDeck deck = new UnoDeck();
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
		UnoDeck deck = new UnoDeck();
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
		UnoDeck deck = new UnoDeck();
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
		UnoDeck deck = new UnoDeck();
		ArrayList<Deck.Card> sampleCards = new ArrayList<>();
		sampleCards.add(deck.new UnoCard("Two", "Diamonds"));
		sampleCards.add(deck.new UnoCard("Ace", "Spades"));
		// set deck's cards with setter
		assertTrue("Deck's setCards() does not return true when passed a non-null ArrayList<Deck.Card>", deck.setCards(sampleCards));
		// checks that deck's cards matches that set
		assertEquals("Deck's cards do not match those given in the setter", sampleCards, deck.getCards());
		// null case
		assertFalse("Deck's setCards() does not return false when passed null", deck.setCards(null));
	}
	
	@Test
	public void createCardWithBackTest() {
		// initialize test deck and test ArrayList<Deck.Card> for comparison
		UnoDeck deck = new UnoDeck();
		ArrayList<UnoDeck.UnoCard> expectedCards = new ArrayList<>();
		// check default createCard()'s logic flow (with back!!)
		assertFalse("Deck's createCard() with back allows cards with ranks not in the rankList to be created",
				deck.createCard("Go", "Green", "2019"));
		assertFalse("Deck's createCard() with back allows cards with suits not in the suitList to be created",
				deck.createCard("Zero", "Fish", "2019"));
		assertFalse("Deck's createCard() with back allows cards with backs not in the backList to be created",
				deck.createCard("Zero", "Green", "Yeeeet"));
		assertTrue("Deck's createCard() with back does not allow blank cards to be created", deck.createCard("", "", ""));
		assertTrue(
				"Deck's createCard()  with back does not allow cards with ranks, suits, and backs in Deck's respective lists to be created",
				deck.createCard("Zero", "Green", "2019"));
		// check expected changes in Deck's cards ArrayList
		expectedCards.add(deck.new UnoCard("", "", ""));
		expectedCards.add(deck.new UnoCard("Zero", "Green", "2019"));
		assertEquals("Deck's createCard() does not correctly add created cards to Deck's cards ArrayList",
				expectedCards, deck.getCards());
		// null cases
		assertFalse("Deck's createCard() with back does not return false when the passed rank is null", deck.createCard(null, "Green", "2019"));
		assertFalse("Deck's createCard() with back does not return false when the passed suit is null", deck.createCard("Zero", null, "2019"));
		assertFalse("Deck's createCard() with back does not return false when the passed back is null", deck.createCard("Zero", "Green", null));
		assertFalse("Deck's createCard() does not return false when the passed fields are null", deck.createCard(null, null, null));
		// check overloaded createCard()'s logic flow
		assertFalse("Deck's overloaded createCard() with back allows cards with ranks not in the rankList to be created",
				deck.createCard("Go", "Red", "2013", 7));
		assertFalse("Deck's overloaded createCard() with back allows cards with suits not in the suitList to be created",
				deck.createCard("Draw Two", "Fish", "2013", 6));
		assertFalse("Deck's overloaded createCard() with back allows cards with backs not in the backList to be created",
				deck.createCard("Draw Two", "Red", "big yeet", 6));
		assertTrue("Deck's overloaded createCard() with back does not allow blank cards to be created",
				deck.createCard("", "", "", 3));
		assertTrue(
				"Deck's overloaded createCard() with back does not allow cards with ranks, suits, and backs in Deck's respective lists to be created",
				deck.createCard("Draw Two", "Red", "2013", 2));
		// check expected changes in Deck's cards ArrayList
		expectedCards.add(deck.new UnoCard("", "", ""));
		expectedCards.add(deck.new UnoCard("", "", ""));
		expectedCards.add(deck.new UnoCard("", "", ""));
		expectedCards.add(deck.new UnoCard("Draw Two", "Red", "2013"));
		expectedCards.add(deck.new UnoCard("Draw Two", "Red", "2013"));
		assertEquals("Deck's overloaded createCard() with back does not correctly add created cards to Deck's cards ArrayList",
				expectedCards, deck.getCards());
		// null cases
		assertFalse("Deck's overloaded createCard() with back does not return false when the passed rank is null", deck.createCard(null, "Red", "2013", 1));
		assertFalse("Deck's overloaded createCard() with back does not return false when the passed suit is null", deck.createCard("Draw Two", null, "2013", 2));
		assertFalse("Deck's overloaded createCard() with back does not return false when the passed back is null", deck.createCard("Draw Two", "Red", null, 2));
		assertFalse("Deck's overloaded createCard() with back does not return false when the passed fields are null", deck.createCard(null, null, null, 3));
	}

	@Test
	public void createCardWithoutBackTest() {
		// initialize test deck and test ArrayList<Deck.Card> for comparison
		UnoDeck deck = new UnoDeck();
		ArrayList<Deck.Card> expectedCards = new ArrayList<>();
		// check default createCard()'s logic flow
		assertFalse("Deck's createCard() allows cards with ranks not in the rankList to be created",
				deck.createCard("Go", "Red"));
		assertFalse("Deck's createCard() allows cards with suits not in the suitList to be created",
				deck.createCard("Draw Two", "Fish"));
		assertTrue("Deck's createCard() does not allow blank cards to be created", deck.createCard("", ""));
		assertTrue(
				"Deck's createCard() does not allow cards with ranks and suits in Deck's rankList and suitList to be created",
				deck.createCard("Draw Two", "Red"));
		// check expected changes in Deck's cards ArrayList
		expectedCards.add(deck.new Card("", ""));
		expectedCards.add(deck.new Card("Draw Two", "Red"));
		assertEquals("Deck's createCard() does not correctly add created cards to Deck's cards ArrayList",
				expectedCards, deck.getCards());
		// null cases
		assertFalse("Deck's createCard() does not return false when the passed rank is null", deck.createCard(null, "Red"));
		assertFalse("Deck's createCard() does not return false when the passed suit is null", deck.createCard("Draw Two", null));
		assertFalse("Deck's createCard() does not return false when the passed rank and suit is null", deck.createCard(null, null));
		// check overloaded createCard()'s logic flow
		assertFalse("Deck's overloaded createCard() allows cards with ranks not in the rankList to be created",
				deck.createCard("Go", "Green", 7));
		assertFalse("Deck's overloaded createCard() allows cards with suits not in the suitList to be created",
				deck.createCard("Zero", "Fish", 6));
		assertTrue("Deck's overloaded createCard() does not allow blank cards to be created",
				deck.createCard("", "", 3));
		assertTrue(
				"Deck's overloaded createCard() does not allow cards with ranks and suits in Deck's rankList and suitList to be created",
				deck.createCard("Zero", "Green", 2));
		// check expected changes in Deck's cards ArrayList
		expectedCards.add(deck.new Card("", ""));
		expectedCards.add(deck.new Card("", ""));
		expectedCards.add(deck.new Card("", ""));
		expectedCards.add(deck.new Card("Zero", "Green"));
		expectedCards.add(deck.new Card("Zero", "Green"));
		assertEquals("Deck's overloaded createCard() does not correctly add created cards to Deck's cards ArrayList",
				expectedCards, deck.getCards());
		// null cases
		assertFalse("Deck's overloaded createCard() does not return false when the passed rank is null", deck.createCard(null, "Green", 1));
		assertFalse("Deck's overloaded createCard() does not return false when the passed suit is null", deck.createCard("Zero", null, 2));
		assertFalse("Deck's overloaded createCard() does not return false when the passed rank and suit is null", deck.createCard(null, null, 3));
	}
	
	@Test
	public void countCardWithRankOrSuitOrBackTest() {
		// initialize test deck and create Cards for counting
		UnoDeck deck = new UnoDeck();
		deck.createCard("Reverse", "Blue", "1971", 2);
		deck.createCard("Reverse", "Green", "2001", 3);
		deck.createCard("Seven", "Blue", "2001", 11);
		deck.createCard("Seven", "Green", "1971", 7);
		// check that count card, well, counts cards correctly
		assertEquals("Deck's single-field countCard() does not correctly count all the cards with the passed rank", 5,
				deck.countCard("Reverse"));
		assertEquals("Deck's single-field countCard() does not correctly count all the cards with the passed suit", 13,
				deck.countCard("Blue"));
		assertEquals("Deck's single-field countCard() does not correctly count all the cards with the passed back", 14,
				deck.countCard("2001"));
		assertEquals("Deck's single-field countCard() does return zero when given a rank or suit not present in the cards", 0,
				deck.countCard("Two"));
		// null case
		assertEquals("Deck's single-field countCard() does not return zero when null is passed", 0, deck.countCard(null));
	}

	@Test
	public void countCardWithRankAndSuitTest() {
		// initialize test deck and create Cards for counting
		UnoDeck deck = new UnoDeck();
		deck.createCard("Reverse", "Blue", 2);
		deck.createCard("Reverse", "Green", 3);
		deck.createCard("Seven", "Blue", 7);
		// check that count card, well, counts cards correctly
		assertEquals("Deck's double-field countCard() does not correctly count all the cards with the passed rank and suit", 2,
				deck.countCard("Reverse", "Blue"));
		assertEquals("Deck's double-field countCard() does not correctly count all the cards with the passed rank and suit", 3,
				deck.countCard("Reverse", "Green"));
		assertEquals("Deck's double-field countCard() does not correctly count all the cards with the passed rank and suit", 7,
				deck.countCard("Seven", "Blue"));
		assertEquals("Deck's double-field countCard() does return zero when given a rank and suit not present in the cards", 0,
				deck.countCard("Draw Two", "Red"));
		// null case
		assertEquals("Deck's double-field countCard() does not return zero when null is passed for the rank", 0, deck.countCard(null, "Blue"));
		assertEquals("Deck's double-field countCard() does not return zero when null is passed for the suit", 0, deck.countCard("Reverse", null));
		assertEquals("Deck's double-field countCard() does not return zero when null is passed for the rank and suit", 0, deck.countCard(null, null));
	}
	
	@Test
	public void countCardWithAllFieldsTest() {
		// initialize test deck and create Cards for counting
		UnoDeck deck = new UnoDeck();
		deck.createCard("Reverse", "Blue", "2019", 2);
		deck.createCard("Reverse", "Green", "2019", 3);
		deck.createCard("Seven", "Blue", "2019", 11);
		deck.createCard("Reverse", "Blue", "2001", 7);
		// check that count card, well, counts cards correctly
		assertEquals("Deck's triple-field countCard() does not correctly count all the cards with the passed rank and suit", 2,
				deck.countCard("Reverse", "Blue", "2019"));
		assertEquals("Deck's triple-field countCard() does not correctly count all the cards with the passed rank and suit", 3,
				deck.countCard("Reverse", "Green", "2019"));
		assertEquals("Deck's triple-field countCard() does not correctly count all the cards with the passed rank and suit", 11,
				deck.countCard("Seven", "Blue", "2019"));
		assertEquals("Deck's triple-field countCard() does not correctly count all the cards with the passed rank and suit", 7,
				deck.countCard("Reverse", "Blue", "2001"));
		assertEquals("Deck's triple-field countCard() does return zero when given a field not present in the cards", 0,
				deck.countCard("Ace", "Spades", "2019"));
		// null case
		assertEquals("Deck's triple-field countCard() does not return zero when null is passed for the rank", 0, deck.countCard(null, "Blue", "2019"));
		assertEquals("Deck's triple-field countCard() does not return zero when null is passed for the suit", 0, deck.countCard("Reverse", null, "2019"));
		assertEquals("Deck's triple-field countCard() does not return zero when null is passed for the back", 0, deck.countCard("Reverse", "Blue", null));
		assertEquals("Deck's triple-field countCard() does not return zero when null is passed for all fields", 0, deck.countCard(null, null, null));
	}
	
	@Test
	public void deleteCardTest() {
		// initialize test deck and create Cards for deleting
		UnoDeck deck = new UnoDeck();
		deck.createCard("Three", "Yellow", "2019", 2);
		deck.createCard("Five", "Yellow", "2013", 2);
		deck.createCard("Three", "Red", "2019", 1);
		deck.createCard("Five", "Red", "2013", 2);
		deck.createCard("Five", "Red", "2019", 3);
		deck.createCard("Three", "Red", "2019", 3);
		deck.createCard("Five", "Yellow", "2019", 1);
		// initialize ArrayList for comparison
		ArrayList<Deck.Card> expectedCards = new ArrayList<>();
		expectedCards.add(deck.new Card("Five", "Red", "2013"));
		expectedCards.add(deck.new Card("Five", "Red", "2013"));
		// check default deleteCard()'s logic flow
		assertTrue("Deck's deleteCard() does not return true when passed a rank present in cards",
				deck.deleteCard("Three"));
		assertFalse("Deck's deleteCard() does not return false when passed a rank not present in cards",
				deck.deleteCard("Three"));
		assertTrue("Deck's deleteCard() does not return true when passed a suit present in cards",
				deck.deleteCard("Yellow"));
		assertFalse("Deck's deleteCard() does not return false when passed a suit not present in cards",
				deck.deleteCard("Yellow"));
		assertTrue("Deck's deleteCard() does not return true when passed a back present in cards",
				deck.deleteCard("2019"));
		assertFalse("Deck's deleteCard() does not return false when passed a back not present in cards",
				deck.deleteCard("2019"));
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
		UnoDeck deck = new UnoDeck();
		deck.createCard("Three", "Yellow", 2);
		deck.createCard("Five", "Yellow", 2);
		deck.createCard("Three", "Red", 1);
		deck.createCard("Five", "Red", 2);
		deck.createCard("Five", "Yellow", 1);
		deck.createCard("Three", "Red", 3);
		deck.createCard("Reverse", "Blue");
		// initialize ArrayList for comparison
		ArrayList<Deck.Card> expectedCards = new ArrayList<>();
		expectedCards.add(deck.new Card("Three", "Yellow"));
		expectedCards.add(deck.new Card("Three", "Yellow"));
		expectedCards.add(deck.new Card("Five", "Yellow"));
		expectedCards.add(deck.new Card("Reverse", "Blue"));
		// check default deleteCard()'s logic flow
		assertTrue("Deck's deleteCard() does not return true when passed a suit and rank present in cards",
				deck.deleteCard("Three", "Red"));
		assertFalse("Deck's deleteCard() does not return false when passed a suit and rank not present in cards",
				deck.deleteCard("Three", "Red"));
		// check overloaded deleteCard()'s logic flow
		assertTrue("Deck's overloaded deleteCard() does not return true when passed a suit, rank, and quantity less than current count present in cards",
				deck.deleteCard("Five", "Yellow", 2));
		assertFalse(
				"Deck's overloaded deleteCard() does not return false when passed a quantity and of suit and rank not present in cards",
				deck.deleteCard("Five", "Red", 3));
		assertTrue("Deck's overloaded deleteCard() does not return true when passed a suit, rank, and quantity less than current count present in cards",
				deck.deleteCard("Five", "Red", 2));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed a quantity of 0 or less",
				deck.deleteCard("Three", "Yellow", 0));
		// check expected changes in Deck's cards ArrayList
		assertEquals(
				"Deck's deleteCard() does not correctly remove cards with the passed rank and suit in the cards ArrayList",
				expectedCards, deck.getCards());
		// null cases
		assertFalse("Deck's deleteCard() does not return false when passed a null rank", deck.deleteCard(null, "Yellow"));
		assertFalse("Deck's deleteCard() does not return false when passed a null suit", deck.deleteCard("Five", null));
		assertFalse("Deck's deleteCard() does not return false when passed a null rank and suit", deck.deleteCard(null, null));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed a null rank", deck.deleteCard(null, "Yellow", 3));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed a null suit", deck.deleteCard("Five", null, 1));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed a null rank and suit", deck.deleteCard(null, null, 2));
	}
	
	@Test
	public void deleteCardWithAllFieldsTest() {
		// initialize test deck and create Cards for deleting
		UnoDeck deck = new UnoDeck();
		deck.createCard("Three", "Yellow", "2019", 2);
		deck.createCard("Five", "Yellow", "2013", 2);
		deck.createCard("Three", "Red", "2019", 1);
		deck.createCard("Five", "Red", "2013", 2);
		deck.createCard("Three", "Red", "2019", 3);
		deck.createCard("Five", "Yellow", "2019", 1);
		// initialize ArrayList for comparison
		ArrayList<Deck.Card> expectedCards = new ArrayList<>();
		expectedCards.add(deck.new Card("Three", "Yellow", "2019"));
		expectedCards.add(deck.new Card("Three", "Yellow", "2019"));
		expectedCards.add(deck.new Card("Five", "Yellow", "2013"));
		expectedCards.add(deck.new Card("Five", "Yellow", "2019"));
		// check default deleteCard()'s logic flow
		assertTrue("Deck's deleteCard() does not return true when passed fields present in cards",
				deck.deleteCard("Three", "Red", "2019"));
		assertFalse("Deck's deleteCard() does not return false when passed fields not present in cards",
				deck.deleteCard("Three", "Red", "2019"));
		// check overloaded deleteCard()'s logic flow
		assertTrue("Deck's overloaded deleteCard() does not return true when passed a suit, rank, and quantity less than current count present in cards",
				deck.deleteCard("Five", "Yellow", "2013", 1));
		assertFalse(
				"Deck's overloaded deleteCard() does not return false when passed a quantity and of suit and rank not present in cards",
				deck.deleteCard("Five", "Red", "2013", 3));
		assertTrue("Deck's overloaded deleteCard() does not return true when passed a suit, rank, and quantity less than current count present in cards",
				deck.deleteCard("Five", "Red", "2013", 2));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed a quantity of 0 or less",
				deck.deleteCard("Three", "Yellow", "2019", 0));
		// check expected changes in Deck's cards ArrayList
		assertEquals(
				"Deck's deleteCard() does not correctly remove cards with the passed fields in the cards ArrayList",
				expectedCards, deck.getCards());
		// null cases
		assertFalse("Deck's deleteCard() does not return false when passed a null rank", deck.deleteCard(null, "Yellow", "2019"));
		assertFalse("Deck's deleteCard() does not return false when passed a null suit", deck.deleteCard("Five", null, "2019"));
		assertFalse("Deck's deleteCard() does not return false when passed a null back", deck.deleteCard("Five", "Yellow", null));
		assertFalse("Deck's deleteCard() does not return false when passed null fields", deck.deleteCard(null, null, null));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed a null rank", deck.deleteCard(null, "Yellow", "2019", 3));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed a null suit", deck.deleteCard("Five", null, "2019", 1));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed a null suit", deck.deleteCard("Five", "Yellow", null, 1));
		assertFalse("Deck's overloaded deleteCard() does not return false when passed null fields", deck.deleteCard(null, null, null, 2));
	}

	@Test
	public void fillDeckTest() {
		// initialize test deck and create Cards to check additions and deletions
		UnoDeck deck = new UnoDeck();
		deck.createCard("Eight", "Blue", "2019");
		deck.createCard("Three", "Red", "2013", 3);
		deck.createCard("Eight", "Blue", "2013");
		// initialize ArrayList for comparison
		ArrayList<Deck.Card> expectedCards = new ArrayList<>();
		// add 2 of each regular card and 4 of each wild card to the expected Cards
		for (String rank : deck.getRankList()) {
			if (deck.getWildList().contains(rank)) {
				for (int i = 0; i < 4; i++) {
					expectedCards.add(deck.new Card(rank, "", "2013"));
				}
			} else {
				for (String suit : deck.getSuitList()) {
					expectedCards.add(deck.new Card(rank, suit, "2013"));
					expectedCards.add(deck.new Card(rank, suit, "2013"));
				}
			}
		}
		expectedCards.add(deck.new Card("Eight", "Blue", "2019"));
		Collections.sort(expectedCards);;
		// check two-field fillDeck()'s logic flow
		assertTrue("Deck's fillDeck() does not return true when cards is empty and two nonzero ints are passed",
				deck.fillDeck(2, 4, "2013"));
		assertFalse(
				"Deck's fillDeck() does not return false when cards already has the passed number of regular and wild cards",
				deck.fillDeck(2, 4, "2013"));
		// check expected changes in Deck's cards ArrayList
		assertEquals("Deck's fillDeck() does not correctly modify the cards ArrayList", expectedCards, deck.getCards());
		// check expected changes in Deck's cards ArrayList with one-field fillDeck()
		assertTrue(
				"Deck's fillDeck() does not return true when cards has a differing amount of regular and/or wild cards from that passed",
				deck.fillDeck(0, "2013"));
		expectedCards.clear();
		expectedCards.add(deck.new Card("Eight", "Blue", "2019"));
		assertEquals("Deck's fillDeck() does not empty the cards ArrayList of cards with the passed back when 0s are both passed", expectedCards,
				deck.getCards());
		// check expected changes in Deck's cards ArrayList with the default fillDeck()
		assertTrue(
				"Deck's fillDeck() does not return true when cards has a differing amount of regular and/or wild cards from that passed",
				deck.fillDeck());
		assertFalse(
				"Deck's fillDeck() does not return false when cards does not have a differing amount of regular and/or wild cards from that passed",
				deck.fillDeck());
		// add 1 of each regular card and 2 of each wild card to expectedCards
		expectedCards.clear();
		for (String rank : deck.getRankList()) {
			if (deck.getWildList().contains(rank)) {
				expectedCards.add(deck.new Card(rank, "", "2019"));
				expectedCards.add(deck.new Card(rank, "", "2019"));
				expectedCards.add(deck.new Card(rank, "", "2019"));
				expectedCards.add(deck.new Card(rank, "", "2019"));				
			} else {
				for (String suit : deck.getSuitList()) {
					expectedCards.add(deck.new Card(rank, suit, "2019"));
					if (!rank.equals("Zero")) expectedCards.add(deck.new Card(rank, suit, "2019"));
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
		UnoDeck deck = new UnoDeck();
		// check it returns true when cards is empty
		assertTrue("Deck's isEmpty() does not return true when cards is empty", deck.isEmpty());
		// check it return false when cards is not empty
		deck.createCard("Wild", "");
		assertFalse("Deck's isEmpty() does not return false when cards not empty", deck.isEmpty());
	}

	@Test
	public void emptyDeckTest() {
		// initialize test deck and fill deck with cards for emptying
		UnoDeck deck = new UnoDeck();
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
		UnoDeck deck = new UnoDeck();
		// empty deck case
		assertEquals("UnoDeck's toString() does not return the correct String when empty",
				"0-card uno deck", deck.toString());
		// full deck case
		deck.fillDeck();
		assertEquals("UnoDeck's toString() does not return the correct String when empty",
				"108-card uno deck", deck.toString());
	}

	@Test
	public void equalsTest() {
		// Instantiate test cases for each logic flow
		UnoDeck deck = new UnoDeck();
		Table table = new Table();
		Deck deckWithDifferentRanks = new UnoDeck();
		deckWithDifferentRanks.getRankList().remove("Wild");
		Deck deckWithDifferentWilds = new UnoDeck();
		deckWithDifferentWilds.getWildList().add("Two");
		Deck deckWithDifferentSuits = new UnoDeck();
		deckWithDifferentSuits.getSuitList().add("Pringles");
		Deck deckWithDifferentBacks = new UnoDeck();
		deckWithDifferentBacks.getBackList().add("Cooool");
		Deck fullDeck = new Deck(Deck.asArrayList(UnoDeck.RANKS), Deck.asArrayList(UnoDeck.WILDS), Deck.asArrayList(UnoDeck.SUITS), Deck.asArrayList(UnoDeck.BACKS));
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
		Deck deck = new UnoDeck();
		Deck deckWithFewerRanks = new UnoDeck();
		deckWithFewerRanks.getRankList().remove("Two");
		Deck deckWithFewerSuits = new UnoDeck();
		deckWithFewerSuits.getSuitList().remove("Yellow");
		Deck deckWithMoreWilds = new UnoDeck();
		deckWithMoreWilds.getWildList().add("Two");
		Deck deckWithMoreBacks = new UnoDeck();
		deckWithMoreBacks.getBackList().add("Purple");
		Deck deckWithMoreCards = new UnoDeck();
		deckWithMoreCards.fillDeck();
		Deck sameDeck = new Deck(Deck.asArrayList(UnoDeck.RANKS), Deck.asArrayList(UnoDeck.WILDS), Deck.asArrayList(UnoDeck.SUITS), Deck.asArrayList(UnoDeck.BACKS));
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
		Deck deck = new UnoDeck();
		// empty deck case
		assertTrue("Deck's isSorted() does not return true when Deck's cards is empty", deck.isSorted());
		// non-empty, in order deck case
		deck.createCard("Zero", "Red");
		deck.createCard("Eight", "Yellow");
		deck.createCard("Reverse", "Blue");
		assertTrue("Deck's isSorted() does not return true when Deck's cards are in natural ascending order", deck.isSorted());
		// non-empty, out of order deck case
		deck.createCard("Zero", "Red");
		deck.createCard("Draw Two", "Red");
		assertFalse("Deck's isSorted does not return false when Deck's cards are not in natural ascending order", deck.isSorted());
	}
	
	@Test
	public void sortTest() {
		// instantiate test deck
		Deck deck = new UnoDeck();
		// empty deck case for logic flow
		assertFalse("Deck's sort() does not return false when Deck's cards is empty", deck.sort());
		// non-empty, out of order deck case
		deck.createCard("Zero", "Red");
		deck.createCard("Eight", "Yellow");
		deck.createCard("Reverse", "Blue");
		deck.createCard("Zero", "Red");
		deck.createCard("Draw Two", "Red");
		assertTrue("Deck's sort() does not return true when Deck's cards are not in natural ascending order", deck.sort());
		// non-empty, in-order deck case
		assertFalse("Deck's sort() does not return false when Deck's cards are in natural ascending order", deck.sort());
		// check for expected order of the cards
		String[] rankArray = { "Zero", "Zero", "Eight", "Reverse", "Draw Two" };
		String[] suitArray = { "Red", "Red", "Yellow", "Blue", "Red" };
		ArrayList<Deck.Card> expectedCards = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			expectedCards.add(deck.new Card(rankArray[i], suitArray[i]));
		}
		assertEquals("Deck's sort() does not correctly modify Deck's cards when they are not in order", expectedCards, deck.getCards());
	}
	
	@Test
	public void shuffleTest() {
		// instantiate test deck and test cards
		Deck deck = new UnoDeck();
		deck.fillDeck();
		deck.shuffle();
		// check if sorted after shuffling (note: has improbable chance of failing)
		assertFalse("Deck's shuffle() did not place cards in some non-natural ascending order", deck.isSorted());
	}

	@Test
	public void defaultCardConstructorTest() {
		// initialize test deck and test card
		UnoDeck deck = new UnoDeck();
		UnoDeck.UnoCard card = deck.new UnoCard();
		assertEquals("UnoCard's default rank is not 'Zero'", "Zero", card.getRank());
		assertEquals("UnoCard's default suit is not 'Red'", "Red", card.getSuit());
		assertEquals("UnoCard's default back is not '2019'", "2019", card.getBack());
		// checks default faceUp is true
		assertTrue("UnoCard's default faceUp is not true", card.isFaceUp());
	}

	@Test
	public void cardConstructorWithRankAndSuitTest() {
		// initialize test deck and test cards
		UnoDeck deck = new UnoDeck();
		// empty case
		Deck.Card blankCard = deck.new UnoCard("Go", "Fish");
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
		assertTrue("UnoCard's default faceUp is not true", blankCard.isFaceUp());
		// full case
		Deck.Card sampleCard = deck.new UnoCard("Draw Two", "Red");
		// checks instantiated rank matches that given in constructor
		assertEquals("Card's instantiated rank does not match that given in constructor", "Draw Two", sampleCard.getRank());
		// checks instantiated suit matches that given in constructor
		assertEquals("Card's instantiated suit does not match that given in constructor", "Red",
				sampleCard.getSuit());
		// wild case
		Deck.Card wildCard = deck.new UnoCard("Wild", "Red");
		// checks instantiated suit is blank since Joker is a wild card
		assertEquals("Card's instantiated suit is not an empty String when passed a rank in Deck's wildList", "",
				wildCard.getSuit());
		// checks instantiated faceUp is true
		assertTrue("Card's default faceUp is not true", sampleCard.isFaceUp());
		// null case
		Deck.Card emptyCard = deck.new UnoCard(null, null);
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
		UnoDeck deck = new UnoDeck();
		// empty case
		Deck.Card blankCard = deck.new UnoCard("Go", "Fish", "Please");
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
		assertTrue("UnoCard's default faceUp is not true", blankCard.isFaceUp());
		// full case
		Deck.Card sampleCard = deck.new UnoCard("Draw Two", "Red", "2013");
		// checks instantiated rank matches that given in constructor
		assertEquals("Card's instantiated rank does not match that given in constructor", "Draw Two", sampleCard.getRank());
		// checks instantiated suit matches that given in constructor
		assertEquals("Card's instantiated suit does not match that given in constructor", "Red",
				sampleCard.getSuit());
		// checks instantiated back matches that given in constructor
		assertEquals("Card's instantiated back does not match that given in constructor", "2013",
				sampleCard.getBack());
		// wild case
		Deck.Card wildCard = deck.new UnoCard("Wild", "Red", "2019");
		// checks instantiated suit is blank since Joker is a wild card
		assertEquals("Card's instantiated suit is not an empty String when passed a rank in Deck's wildList", "",
				wildCard.getSuit());
		// checks instantiated faceUp is true
		assertTrue("Card's default faceUp is not true", sampleCard.isFaceUp());
		// null case
		Deck.Card emptyCard = deck.new UnoCard(null, null, null);
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
		UnoDeck deck = new UnoDeck();
		Deck.Card card = deck.new UnoCard();
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
		UnoDeck deck = new UnoDeck();
		Deck.Card card = deck.new UnoCard();
		// checks that card cannot be set with a suit not in Deck's suitList
		assertFalse("Card's setSuit() does not return false when passed a suit not in the Deck's suitList",
				card.setSuit("Fish"));
		// checks that card can be set with a suit in Deck's suitList
		assertTrue("Card's setSuit() does not return true when passed a suit in the Deck's suitList",
				card.setSuit("Blue"));
		// checks instantiated suit matches that given in setter
		assertEquals("Card's suit does not match that given in setter", "Blue", card.getSuit());
		// wild case
		Deck.Card wildCard = deck.new Card("Wild", "");
		// checks that card cannot be set with a suit when it is a wild card
		assertFalse("Card's setSuit() does not return false when the card's rank is in Deck's wildList",
				wildCard.setSuit("Blue"));
		// null case
		assertFalse("Card's setSuit() does not return false when null is passed", card.setSuit(null));
	}
	
	@Test
	public void getAndSetCardBackTest() {
		// initialize test deck and test card
		UnoDeck deck = new UnoDeck();
		Deck.Card card = deck.new UnoCard();
		// checks that card cannot be set with a suit not in Deck's suitList
		assertFalse("Card's setBack() does not return false when passed a back not in the Deck's backList",
				card.setBack("Please"));
		// checks that card can be set with a suit in Deck's suitList
		assertTrue("Card's setBack() does not return true when passed a back in the Deck's backList",
				card.setBack("2001"));
		// checks instantiated suit matches that given in setter
		assertEquals("Card's back does not match that given in setter", "2001", card.getBack());
		// null case
		assertFalse("Card's setBack() does not return false when null is passed", card.setBack(null));
	}
	
	@Test
	public void getAndSetCardFaceUpTest() {
		// initialize test deck and test card
		UnoDeck deck = new UnoDeck();
		Deck.Card card = deck.new UnoCard();
		card.setFaceUp(false);
		// checks instantiated suit matches that given in setter
		assertFalse("Card's faceUp does not match that given in setter", card.isFaceUp());
	}
	
	@Test
	public void cardFlipTest() {
		// initialize test deck and test card
		UnoDeck deck = new UnoDeck();
		Deck.Card card = deck.new UnoCard();
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
		UnoDeck deck = new UnoDeck();
		// Default case
		Deck.Card blankCard = deck.new UnoCard("", "", "");
		assertEquals("Card's toString() does not return 'Blank Card' when given default Card", "Blank Card",
				blankCard.toString());
		// Empty rank or suit case
		Deck.Card rankCard = deck.new UnoCard("Wild", "");
		assertEquals("Card's toString() does not return its rank when its suit is empty", "Wild", rankCard.toString());
		Deck.Card suitCard = deck.new UnoCard("", "Green");
		assertEquals("Card's toString() does not return its suit when its rank is empty", "Green",
				suitCard.toString());
		// Rank and suit case
		Deck.Card rankAndSuitCard = deck.new UnoCard("Skip", "Blue");
		assertEquals("Card's toString() does not return '<suit> <rank>' and its rank and suit are non-empty", "Blue Skip",
				rankAndSuitCard.toString());
		// Face-Down Blank Back case
		rankAndSuitCard.setFaceUp(false);
		assertEquals("Card's toString() does not return 'Blank Back' when face down with no back", "Blank Back",
				rankAndSuitCard.toString());
		// Face-Down not blank back case
		rankAndSuitCard.setBack("2001");
		assertEquals("Card's toString() does not return '<back> Back' when face down with a non-empty back", "2001 Back",
				rankAndSuitCard.toString());
	}

	@Test
	public void cardEqualsTest() {
		// Instantiate test cases for each logic flow
		UnoDeck deck = new UnoDeck();
		Deck.Card card = deck.new UnoCard("", "", "");
		Table table = new Table();
		Deck.Card cardWithRank = deck.new UnoCard("Zero", "");
		Deck.Card cardWithSuit = deck.new UnoCard("", "Red");
		Deck.Card cardWithBack = deck.new UnoCard("", "", "1971");
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
		// note that sameCard is not even a UnoCard! UnoCard is a Card, so they're the same
	}

	@Test
	public void cardCompareToTest() {
		// Instantiate test cases for each logic flow
		UnoDeck deck = new UnoDeck();
		Deck.Card card = deck.new UnoCard("Two", "Blue", "2013");
		Deck.Card cardWithRank = deck.new UnoCard("One", "", "2001");
		Deck.Card cardWithSuit = deck.new UnoCard("Two", "Red", "2001");
		Deck.Card cardWithBack = deck.new UnoCard("Two", "Blue", "2019");
		Deck.Card sameCard = deck.new Card("Two", "Blue", "2013");
		sameCard.setFaceUp(false);
		// Order tests by comparison order
		assertTrue("Two Cards with different ranks are not sorted in ascending order by index in Deck's rankList",
				card.compareTo(cardWithRank) > 0);
		assertTrue("Two Cards with different suits are not sorted in ascending order by index in Deck's suitList",
				card.compareTo(cardWithSuit) > 0);
		assertTrue("Two Cards with different backs are not sorted in ascending order by index in Deck's backList",
				card.compareTo(cardWithBack) > 0);
		assertEquals("Two Cards with the same rank, suit, and back are not sorted together regardless of faceUp", 0, card.compareTo(sameCard));
		// note that sameCard is not even a UnoCard! UnoCard is a Card, so they're the same
	}
	
	@Test
	public void cardGetImageFilePathTest() {
		// initialize test deck
		UnoDeck deck = new UnoDeck();
		// Default case
		Deck.Card sampleCard = deck.new UnoCard("Reverse", "Blue", "2019");
		assertEquals("Card's getImageFilePath() does not return the image file folder path, the Card's toString(), and the image's file type", 
				"C:\\Users\\danie\\Pictures\\Cards\\Uno Deck\\Blue Reverse.png",
				sampleCard.getImageFilePath());
		// Joker Case
		Deck.Card wildCard = deck.new UnoCard("Wild", "", "2019");
		assertEquals("Card's getImageFilePath() does not add the appropriate joker color when getting the image file path for the card the first time", 
				"C:\\Users\\danie\\Pictures\\Cards\\Uno Deck\\Wild.png",
				wildCard.getImageFilePath());
		wildCard.setFaceUp(false);
		assertEquals("Card's getImageFilePath() does not return the image file folder path, the Card's toString(), and the image's file type when the card is face down", 
				"C:\\Users\\danie\\Pictures\\Cards\\Uno Deck\\2019 Back.png",
				wildCard.getImageFilePath());
	}
}
