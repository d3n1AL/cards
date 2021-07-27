import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Card Simulator Card Sorter
 * 
 * @author Daniel Xue 07/27/2021
 * 
 * Sources: UVA CS2110 Lectures,
 * (specifically Prof. Daniel Graham's explanation and pseudocode of merge sort),
 * in addition to the Wikipedia article on implementing ShellSort,
 * JDK 11 Documentation
 */

public class CardSorter {
	
	private int flipCounter;
	private int compareCounter;
	private int swapCounter;
	private long timeElapsed;

	/**
	 * @return the flipCounter
	 */
	public int getFlipCounter() {
		return flipCounter;
	}

	/**
	 * @param flipCounter the flipCounter to set
	 */
	public void setFlipCounter(int flipCounter) {
		this.flipCounter = flipCounter;
	}

	/**
	 * @return the compareCounter
	 */
	public int getCompareCounter() {
		return compareCounter;
	}

	/**
	 * @param compareCounter the compareCounter to set
	 */
	public void setCompareCounter(int compareCounter) {
		this.compareCounter = compareCounter;
	}

	/**
	 * @return the swapCounter
	 */
	public int getSwapCounter() {
		return swapCounter;
	}

	/**
	 * @param swapCounter the swapCounter to set
	 */
	public void setSwapCounter(int swapCounter) {
		this.swapCounter = swapCounter;
	}

	/**
	 * @return the timeElapsed
	 */
	public long getTimeElapsed() {
		return timeElapsed;
	}

	/**
	 * @param timeElapsed the timeElapsed to set
	 */
	public void setTimeElapsed(long timeElapsed) {
		this.timeElapsed = timeElapsed;
	}
	
	/**
	 * Resets the card label's image to reflect some change in the card at the index in cards with the given maxDimension
	 */
	public static void resetCardLabelImage(ArrayList<Deck.Card> cards, JLabel[] cardLabelArray, int index, int maxDimension) {
		cardLabelArray[index].setIcon(new ImageIcon(cards.get(index).getImage(maxDimension)));
		// don't forget to repaint!
		cardLabelArray[index].repaint();
	}
	
	/**
	 * Resets the card label's image to reflect some change in the card at the index in the cardArray with the given maxDimension
	 */
	public static void resetCardLabelImage(Deck.Card[] cardArray, JLabel[] cardLabelArray, int index, int maxDimension) {
		cardLabelArray[index].setIcon(new ImageIcon(cardArray[index].getImage(maxDimension)));
		// don't forget to repaint!
		cardLabelArray[index].repaint();
	}

	public static Deck.Card[] asCardArray(ArrayList<Deck.Card> cards) {
		Deck.Card[] cardArray = new Deck.Card[cards.size()];
		// yes, this command exists and will do everything for you
		cards.toArray(cardArray);
		return cardArray;
	}
	
	public static void toCardArrayListFromArray(ArrayList<Deck.Card> cards, Deck.Card[] cardArray) {
		// you can't use 
		// cards = new ArrayList<Deck.Card>(Arrays.asList(cardArray));
		// because it doesn't save
		cards.clear();
		for (Deck.Card card : cardArray) {
			cards.add(card);
		}
	}
	
	/**
	 * Swap the two cards at the indices given in the passed ArrayList
	 * @param cardArray - Deck.Card array
	 * @param i - index of the first card
	 * @param j - index of the second card
	 * @param maxDimension 
	 * @return boolean confirmation
	 */
	protected boolean swap(Deck.Card[] cardArray, JLabel[] cardLabelArray, int i, int j, int maxDimension) {	
		// return false if cards is null or empty or passed indices are out of bounds and/or the same
		if (i < 0 || i >= cardArray.length || j < 0 || j >= cardArray.length || i == j) return false;
		
		// get current cards at indices i and j and store them
		Deck.Card card1 = cardArray[i];
		Deck.Card card2 = cardArray[j];
		// assign both to the other index
		cardArray[i] = card2;
		cardArray[j] = card1;
		
		// reset their card images
		CardSorter.resetCardLabelImage(cardArray, cardLabelArray, i, maxDimension);
		CardSorter.resetCardLabelImage(cardArray, cardLabelArray, j, maxDimension);		
		
		// return true when done
		return true;
	}
	
	public boolean selectionSort(ArrayList<Deck.Card> cards, JLabel[] cardLabelArray, int compareDelay, int swapDelay, int maxDimension) {
		// return false if cards is null or empty
		if (cards == null || cards.isEmpty()) return false;
		// show what sort you're running
		System.out.println("Running selection sort...");
		// assign cards as an array
		Deck.Card[] cardArray = asCardArray(cards);
		// log the start time in milliseconds
		long start = System.currentTimeMillis();
		// instantiate swap and comparison counters
		this.compareCounter = 0;
		this.swapCounter = 0;
		// otherwise loop through cards
		int i = 0;
		while (i < cardArray.length) {
			// store current index as having the smallest card
			int smallestCardIndex = i;
			// loop through every card after i
			for (int j = i+1; j < cardArray.length; j++) {
				// if any card is smaller than the smallest, then it's the new smallest
				if (cardArray[j].compareTo(cardArray[smallestCardIndex]) < 0) {
					smallestCardIndex = j;
				}
				// each compare has a delay of 20 milliseconds
				try {
				    TimeUnit.MILLISECONDS.sleep(compareDelay);
				} catch (InterruptedException ie) {
				    Thread.currentThread().interrupt();
				}
				// add one to compareCounter
				this.compareCounter++;
			}
			// swap smallest card with current card (if same, will just return false)
			if (swap(cardArray, cardLabelArray, i, smallestCardIndex, maxDimension))	{ 
				this.swapCounter++;
				// each swap has a delay of 15 milliseconds
				try {
				    TimeUnit.MILLISECONDS.sleep(swapDelay);
				} catch (InterruptedException ie) {
				    Thread.currentThread().interrupt();
				}
			}
			// increment swap counter up and i up too
			i++;
//			// print the cards
//			System.out.println(Arrays.toString(cardArray));
		}
		// log the end time in milliseconds
		long end = System.currentTimeMillis();
		// store the time elapsed in the time elapsed field
		this.timeElapsed = end-start;
		// assign the sorted array as the new list
		toCardArrayListFromArray(cards, cardArray);
		// print final number of compares and swap
		System.out.println("Number of compares: " + this.compareCounter);
		System.out.println("Number of swaps: " + this.swapCounter);
		System.out.println("Elapsed time in milliseconds: " + this.timeElapsed);
		// return true when done
		return true;
	}
	
	public boolean selectionSort(ArrayList<Deck.Card> cards, JLabel[] cardLabelArray, int maxDimension) {
		return selectionSort(cards, cardLabelArray, 0, 0, maxDimension);
	}
	
	public boolean insertionSort(ArrayList<Deck.Card> cards, JLabel[] cardLabelArray, int compareDelay, long swapDelay, int maxDimension) {
		// return false if cards is null or empty
		if (cards == null || cards.isEmpty()) return false;
		// show what sort you're running
		System.out.println("Running insertion sort...");
		// convert cards to array to work with 
		Deck.Card[] cardArray = asCardArray(cards);
		// log the start time in milliseconds
		long start = System.currentTimeMillis();
		// instantiate swap and comparison counters
		this.compareCounter = 0;
		this.swapCounter = 0;
		// otherwise loop through cards
		int i = 0;
		while (i < cardArray.length) {
			// initialize another int j at the current index
			int j = i;
			// loop until the card before the card at j is smaller or j is 0
			while (j > 0) {
				// each compare has a delay of 20 milliseconds
				try {
				    TimeUnit.MILLISECONDS.sleep(compareDelay);
				} catch (InterruptedException ie) {
				    Thread.currentThread().interrupt();
				}
				if (cardArray[j-1].compareTo(cardArray[j]) > 0) {
					this.compareCounter++;
					// swap card at j with the card at the index before
					if (swap(cardArray, cardLabelArray, j-1, j, maxDimension)) { 
						this.swapCounter++;
						// each swap has a delay
						try {
						    TimeUnit.MILLISECONDS.sleep(swapDelay);
						} catch (InterruptedException ie) {
						    Thread.currentThread().interrupt();
						}
					}
				} else {
					this.compareCounter++;
					break;
				}
				// increment j down one
				j--;
			}
			// increment i up one
			i++;
		}
		// log the end time in milliseconds
		long end = System.currentTimeMillis();
		// store the time elapsed in the time elapsed field
		this.timeElapsed = end-start;
		// assign the sorted array as the new list
		toCardArrayListFromArray(cards, cardArray);
		// print final number of compares and swap
		System.out.println("Number of compares: " + this.compareCounter);
		System.out.println("Number of swaps: " + this.swapCounter);
		System.out.println("Elapsed time in milliseconds: " + this.timeElapsed);
		// return true when done
		return true;
	}
	
	public boolean insertionSort(ArrayList<Deck.Card> cards, JLabel[] cardLabelArray, int maxDimension) {
		return insertionSort(cards, cardLabelArray, 0, 0, maxDimension);
	}
	
	public boolean bogoSort(ArrayList<Deck.Card> cards) {
		// return false if cards is null or empty
		if (cards == null || cards.isEmpty()) return false;
		// show what sort you're running
		System.out.println("Running bogo sort...");
		// log the start time in milliseconds
		long start = System.currentTimeMillis();
		// instantiate swap and comparison counters
		this.compareCounter = 0;
		int shuffleCounter = 0;
		// otherwise initialize boolean to check if sorted
		boolean inOrder = false;
		// repeat until cards is sorted
		while (!inOrder) {
			// set inOrder to true
			inOrder = true;
			// loop through every card but the last
			for (int i = 0; i < cards.size() - 1; i++) {
				// set inOrder to false if any cards comes before the card it's after
				if (cards.get(i+1).compareTo(cards.get(i)) < 0) inOrder = false;
				this.compareCounter++;
			}
			// if inOrder is false, shuffle the deck
			if (!inOrder) Collections.shuffle(cards);
			shuffleCounter++;
		}
		// log the end time in milliseconds
		long end = System.currentTimeMillis();
		// store the time elapsed in the time elapsed field
		this.timeElapsed = end-start;
		// print final number of compares and swap
		System.out.println("Number of compares: " + this.compareCounter);
		System.out.println("Number of shuffles: " + shuffleCounter);
		System.out.println("Elapsed time in milliseconds: " + this.timeElapsed);
		// return true when done
		return true;
	}
	
	/**
	 * The following methods for implementing merge sort were taken from
	 * Prof. Daniel Graham's Spring 2021 CS2110 Lecture on recursive sorts
	 */
	public void merge(Deck.Card[] cardArray, Deck.Card[] auxArray, int low, int mid, int high, int compareDelay, int swapDelay) {
		// copy portion of the cardArray passed from low to high into the auxArray
		for (int k = low; k <= high; k++) {
			auxArray[k] = cardArray[k];
		}
		// instantiate one index at low and the other at mid + 1
		int i = low;
		int j = mid + 1;
		// loop over indices from low to high
		for (int k = low; k <= high; k++) {
			// when left has no elements, add from right sub-array and increment j by 1
			if (i > mid) {
				cardArray[k] = auxArray[j++];
			// when right has no elements, add from left sub-array and increment i by 1
			} else if (j > high) {
				cardArray[k] = auxArray[i++];
			} else {
				// compare cards from both sides, add delay and counter
				int cardComparison = auxArray[i].compareTo(auxArray[j]);
				try {
				    TimeUnit.MILLISECONDS.sleep(compareDelay);
				} catch (InterruptedException ie) {
				    Thread.currentThread().interrupt();
				}
				this.compareCounter++;
				// when right comes before left, add from right sub-array and increment j by 1
				if (cardComparison > 0) { 
					cardArray[k] = auxArray[j++];
					// count this as a swap even though it really isn't, whatever man
					if (this.swapCounter % 2 == 0) {
						// only do it every other time so the delay lines up with every two array accesses like a swap
						try {
						    TimeUnit.MILLISECONDS.sleep(swapDelay);
						} catch (InterruptedException ie) {
						    Thread.currentThread().interrupt();
						}
					}
					this.swapCounter++;
				// when left comes before right, add from left sub-array and increment i by 1
				} else if (cardComparison < 0) {
					cardArray[k] = auxArray[i++];
					// count this as a swap even though it really isn't, whatever man
					if (this.swapCounter % 2 == 0) {
						// only do it every other time so the delay lines up with every two array accesses like a swap
						try {
						    TimeUnit.MILLISECONDS.sleep(swapDelay);
						} catch (InterruptedException ie) {
						    Thread.currentThread().interrupt();
						}
					}
					this.swapCounter++;
				// when both are equal, add from the left sub-array and increment i by 1
				} else { 
					cardArray[k] = auxArray[i++];
				}
			}
		}
	}
	
	public boolean mergeSort(Deck.Card[] cardArray, Deck.Card[] auxArray, int low, int high, int compareDelay, int swapDelay) {
		// if selected portion of array has 1 or less elements, do nothing
		if (low >= high) return false;
		// instantiate mid as low + (high - low) / 2 to prevent overflow
		int mid = low + (high - low) / 2;
		// mergeSort first half
		mergeSort(cardArray, auxArray, low, mid, compareDelay, swapDelay);
		// mergeSort second half
		mergeSort(cardArray, auxArray, mid+1, high, compareDelay, swapDelay);
		// merge sorted results together
		merge(cardArray, auxArray, low, mid, high, compareDelay, swapDelay);
		// return true when done
		return true;
	}
	
	public boolean mergeSort(ArrayList<Deck.Card> cards, int compareDelay, int swapDelay) {		
		// return false if cards is null or empty
		if (cards == null || cards.isEmpty()) return false;
		// convert cards to array to work with 
		Deck.Card[] cardArray = asCardArray(cards);
		// show what sort you're running
		System.out.println("Running merge sort...");
		// log the start time in milliseconds
		long start = System.currentTimeMillis();
		// instantiate compareCounter at 0
		this.compareCounter = 0;
		this.swapCounter = 0;
		// create auxArray with the same length as cardsArray
		Deck.Card[] auxArray = new Deck.Card[cardArray.length];
		// note: merge automatically puts stuff in aux so don't worry about it here
		// sort using cardsArray and auxArray starting from 0 to the end
		if (mergeSort(cardArray, auxArray, 0, cardArray.length-1, compareDelay, swapDelay)) {
			// if merge successful, copy results in cardsArray back into cards
			toCardArrayListFromArray(cards, cardArray);
			// log the end time in milliseconds
			long end = System.currentTimeMillis();
			// half the swapCounter so it represents swaps as two array accesses
			this.swapCounter /= 2;
			// store the time elapsed in the time elapsed field
			this.timeElapsed = end-start;
			// print final number of compares and swap
			System.out.println("Number of compares: " + this.compareCounter);
			System.out.println("Number of \"swaps\": " + this.swapCounter);
			System.out.println("Elapsed time in milliseconds: " + this.timeElapsed);
			// return true when done
			return true;
		} 
		// return false otherwise
		return false;
	}
	
	public boolean mergeSort(ArrayList<Deck.Card> cards) {
		return mergeSort(cards, 0, 0);
	}
	
	public boolean bubbleSort(ArrayList<Deck.Card> cards, JLabel[] cardLabelArray, int compareDelay, int swapDelay, int maxDimension) {		
		// return false if cards is null or empty
		if (cards == null || cards.isEmpty()) return false;
		// show what sort you're running
		System.out.println("Running bubble sort...");
		// convert cards to array to work with 
		Deck.Card[] cardArray = asCardArray(cards);
		// log the start time in milliseconds
		long start = System.currentTimeMillis();
		// instantiate swap and comparison counters
		this.compareCounter = 0;
		this.swapCounter = 0;
		// loop through cards starting from end to one before beginning
		for (int i = cardArray.length-1; i > 0; i--) {
			// instantiate boolean to check if sorted as true
			boolean isSorted = true;
			// loop through cards from beginning to the cursor
			for (int j = 0; j < i; j++) {
				// each compare has a delay
				try {
				    TimeUnit.MILLISECONDS.sleep(compareDelay);
				} catch (InterruptedException ie) {
				    Thread.currentThread().interrupt();
				}
				// if current element comes after the element to the right, swap the two
				if (cardArray[j].compareTo(cardArray[j+1]) > 0) {
					
					cardLabelArray[j].getMouseListeners();
					
					if (swap(cardArray, cardLabelArray, j, j+1, maxDimension)) {
						// each swap has a delay
						try {
						    TimeUnit.MILLISECONDS.sleep(swapDelay);
						} catch (InterruptedException ie) {
						    Thread.currentThread().interrupt();
						}
						this.swapCounter++;
						// and set isSorted to false
						isSorted = false;
					}
				// otherwise the element to the right is the new biggest
				}
				// increment compareCounter by 1
				this.compareCounter++;
			}
//			// print the cards
//			System.out.println(Arrays.toString(cardArray));
			// if isSorted is true, break the loop
			if (isSorted) break;
		}
		// log the end time in milliseconds
		long end = System.currentTimeMillis();
		// store the time elapsed in the time elapsed field
		this.timeElapsed = end-start;
		// assign the sorted array as the new list
		toCardArrayListFromArray(cards, cardArray);
		// print final number of compares and swap
		System.out.println("Number of compares: " + this.compareCounter);
		System.out.println("Number of swaps: " + this.swapCounter);
		System.out.println("Elapsed time in milliseconds: " + this.timeElapsed);
		// return true when done
		return true;
	}
	
	public boolean bubbleSort(ArrayList<Deck.Card> cards, JLabel[] cardLabelArray, int maxDimension) {
		return bubbleSort(cards, cardLabelArray, 0, 0, maxDimension);
	}
	
	/**
	 * Sorts cards using the best-known sequence of gaps found by Marcin Ciura
	 * which is [1, 4, 10, 23, 57, 132, 301, 701, 1750]
	 * found here: https://oeis.org/A102549
	 * 
	 * @param cards - ArrayList<Deck.Card>
	 * @param compareDelay
	 * @param swapDelay
	 * @param maxDimension 
	 * @return boolean confirmation
	 */
	public boolean shellSort(ArrayList<Deck.Card> cards, JLabel[] cardLabelArray, int compareDelay, long swapDelay, int maxDimension) {		
		// return false if cards is null or empty
		if (cards == null || cards.isEmpty()) return false;
		// convert cards to array to work with 
		Deck.Card[] cardArray = asCardArray(cards);
		// show what sort you're running
		System.out.println("Running shell sort...");
		// log the start time in milliseconds
		long start = System.currentTimeMillis();
		// instantiate compareCounter at 0
		this.compareCounter = 0;
		this.swapCounter = 0;
		
		// ignore this chunk of code using the original gap size 
//		// define the initial gap size as half of the cards' size
//		int gap = cardArray.length / 2;
//		// loop until the gap is less than or equal to one
//		while (gap >= 1) {
//			// do insertion sort but instead of comparing to the one immediately to the right
//			// compare to the one to the right of the gap
			
		// let's use optimal values
		int[] gapArray = { 1750, 701, 301, 132, 57, 23, 10, 4, 1 };
		for (int gap : gapArray) {
			// loop over all elements more than one gap from the beginning (elements from [0...gap-1] are already in order)
			for (int i = gap; i < cardArray.length; i++) {
				// loop from the current index until they are less than the gap 
				for (int j = i; j >= gap; j -= gap) {
					// each compare has a delay
					try {
					    TimeUnit.MILLISECONDS.sleep(compareDelay);
					} catch (InterruptedException ie) {
					    Thread.currentThread().interrupt();
					}
					// compare the element at j with the one a gap away to the right
					if (cardArray[j].compareTo(cardArray[j-gap]) < 0) {
						// increment compareCounter by one
						this.compareCounter++;
						// if the current card comes before the one a gap away to the right
						// swap them
						if (swap(cardArray, cardLabelArray, j, j-gap, maxDimension)) {
							// each swap has a delay
							try {
							    TimeUnit.MILLISECONDS.sleep(swapDelay);
							} catch (InterruptedException ie) {
							    Thread.currentThread().interrupt();
							}
							// increment swap counter by one
							this.swapCounter++;
						}
					} else {
						// increment compareCounter by one
						this.compareCounter++;
						break;
					}

				}
			}
			
//			// ignore this chunk of code that cuts each gap by two and repeats
//			// divide gap by 2 and repeat
//			gap /= 2;
		}
		// if sort successful, copy results in cardsArray back into cards
		toCardArrayListFromArray(cards, cardArray);
		// log the end time in milliseconds
		long end = System.currentTimeMillis();
		// store the time elapsed in the time elapsed field
		this.timeElapsed = end-start;
		// print final number of compares and swap
		System.out.println("Number of compares: " + this.compareCounter);
		System.out.println("Number of swaps: " + this.swapCounter);
		System.out.println("Elapsed time in milliseconds: " + this.timeElapsed);
		// return true when done
		return true;
	} 
	
	public boolean shellSort(ArrayList<Deck.Card> cards, JLabel[] cardLabelArray, int maxDimension) {
		return shellSort(cards, cardLabelArray, 0, 0, maxDimension);
	}
	
	public boolean quickSort(ArrayList<Deck.Card> cards) {		
		// return false if cards is null or empty
		if (cards == null || cards.isEmpty()) return false;
		// convert cards to array to work with 
		Deck.Card[] cardArray = asCardArray(cards);
		// show what sort you're running
		System.out.println("Running quick sort...");
		// log the start time in milliseconds
		long start = System.nanoTime();
		// instantiate counters at 0
		this.compareCounter = 0;
		this.swapCounter = 0;
		// sort using cardsArray
		if (quickSort(cardArray)) {
			// if merge successful, copy results in cardsArray back into cards
			toCardArrayListFromArray(cards, cardArray);
			// log the end time in milliseconds
			long end = System.nanoTime();
			// store the time elapsed in the time elapsed field
			this.timeElapsed = end-start;
			// print final number of compares and swap
			System.out.println("Number of compares: " + this.compareCounter);
			System.out.println("Number of swaps: " + this.swapCounter);
			System.out.println("Elapsed time in nanoseconds: " + this.timeElapsed);
			// return true when done
			return true;
		} 
		// return false otherwise
		return false;
	}
	
	public boolean quickSort(Deck.Card[] cardArray) {		
		
		// return false otherwise
		return false;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// initialize new Sorter
//		CardSorter sorter = new CardSorter();
		
		// initialize test table with default deck 
		Table table = new Table(new UnoDeck());
		
		// fill deck with cards and shuffle deck
//		String[] rankArray = { "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten" };
//		table.getDeck().setRankList(Deck.asArrayList(rankArray));
//		for (String rank : table.getDeck().getRankList()) {
//			table.getDeck().createCard(rank, "Hearts");
//		}
		table.getDeck().fillDeck();
		table.getDeck().shuffle();
		
		// draw cards from deck onto table faceUp
		table.drawCard(true, 10);
		
		// show drawn cards
		System.out.println(table.getCards());
		
		// sort cards using methods defined above 
//		Collections.sort(table.getCards());
//		sorter.selectionSort(table.getCards());
//		sorter.insertionSort(table.getCards());
//		sorter.bogoSort(table.getCards());
//		sorter.mergeSort(table.getCards());
//		sorter.shellSort(table.getCards());
//		sorter.bubbleSort(table.getCards(), 100, 200, 125);
		
		// use a thread + wait/delay method to slow down each step?
		// check API
		
		// use a thread + wait/delay method to check the final sort result
		System.out.println(table.getCards());
		
	}

}
