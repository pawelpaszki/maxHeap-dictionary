package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Entry;
import utils.FileHandler;

/**
 * 
 * @author Pawel Paszki version 06/04/2016
 * 
 *         This class contains an ArrayList of Entries. it controls adding,
 *         removing and searching for Entries. Those Entries are stored in the
 *         ArrayList as a maxHeap
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class MaxHeap<T> implements MaxHeapInterface {
	private int numberOfStepsInSearch = 0;
	private ArrayList<Entry> dictionaryEntries = new ArrayList<Entry>();
	private int firstFreeIndex = 0;

	/**
	 * adds new Entry to the ArrayList and calls siftUp method in order to find
	 * out, if that newly added Entry is in the correct position
	 */
	@Override
	public void add(Comparable newEntry) {
		dictionaryEntries.add((Entry) newEntry);
		firstFreeIndex++;
		siftUp();
	}

	/**
	 * if an item specified with index k is greater than its parent in the three
	 * - they need to swap spaces.
	 */
	private void siftUp() {
		int k = firstFreeIndex - 1;
		while (k > 0) {
			int p = (k - 1) / 2;
			@SuppressWarnings("unchecked")
			Comparable<Comparable<?>> item = dictionaryEntries.get(k);
			Comparable parent = dictionaryEntries.get(p);
			if (item.compareTo(parent) > 0) {
				Comparable newParent = dictionaryEntries.get(k);
				dictionaryEntries.set(k, (Entry) parent);
				dictionaryEntries.set(p, (Entry) newParent);
				k = p;
			} else {
				break;
			}
		}
	}

	/**
	 * this method sifts down the item, which is higher in the three than it
	 * suppose to be - ie it has lower value than its child
	 */
	private void siftDown() {
		int k = 0;
		int l = 2 * k + 1;
		while (l < firstFreeIndex) {
			int max = l;
			int r = l + 1;
			if (r < firstFreeIndex) {
				if (dictionaryEntries.get(r).compareTo(dictionaryEntries.get(l)) > 0) {
					max++;
				}
			}
			if (dictionaryEntries.get(k).compareTo(dictionaryEntries.get(max)) < 0) {
				Comparable tempParent = dictionaryEntries.get(k);
				Comparable currentParent = dictionaryEntries.get(max);
				dictionaryEntries.set(k, (Entry) currentParent);
				dictionaryEntries.set(max, (Entry) tempParent);
				k = max;
				l = 2 * k + 1;
			} else {
				break;
			}
		}
	}

	/**
	 * this method prints all the Spanish words from the dictionary
	 */
	public void printEntries() {
		for (int i = 0; i < firstFreeIndex; i++) {
			System.out.println(dictionaryEntries.get(i).getSpanishWord());
		}
	}

	/**
	 * this method loads dictionary from the hardcoded directory
	 */
	public void loadDictionary() {
		FileHandler loader = new FileHandler();
		try {
			List wordList = loader.readFile("dictionary\\esp_to_eng.dat");
			for (int i = 0; i < wordList.size();) {
				add(new Entry(String.valueOf(wordList.get(i++)), String.valueOf(wordList.get(i++))));
			}
		} catch (Exception e) {

		}
	}

	/**
	 * this method loads dictionary from the file specified by the parameter
	 * 
	 * @param filename
	 */
	public void loadDictionary(String filename) {
		FileHandler loader = new FileHandler();
		try {
			List wordList = loader.readFile(filename);
			for (int i = 0; i < wordList.size();) {
				add(new Entry(String.valueOf(wordList.get(i++)), String.valueOf(wordList.get(i++))));
			}
		} catch (Exception e) {

		}
	}

	/**
	 * this method saves the dictiorany to hardcoded path
	 */
	public void saveDictionary() {
		FileHandler writer = new FileHandler();
		ArrayList<String> wordPairs = new ArrayList<String>();
		for (int i = 0; i < dictionaryEntries.size(); i++) {
			wordPairs.add(dictionaryEntries.get(i).getSpanishWord());
			wordPairs.add(dictionaryEntries.get(i).getEnglishWord());
		}
		if (wordPairs.size() > 0) {
			writer.writeToFile("dictionary/ESPtoeENG.txt", wordPairs);
		}
	}

	/**
	 * this method saves the current state of dictiorany for file with its path
	 * specified as a parameter
	 * 
	 * @param filename
	 */
	public void saveDictionary(String filename) {
		FileHandler writer = new FileHandler();
		ArrayList<String> wordPairs = new ArrayList<String>();
		for (int i = 0; i < dictionaryEntries.size(); i++) {
			wordPairs.add(dictionaryEntries.get(i).getSpanishWord());
			wordPairs.add(dictionaryEntries.get(i).getEnglishWord());
		}
		if (wordPairs.size() > 0 && !filename.equalsIgnoreCase("esp_to_eng")) {
			writer.writeToFile("dictionary/" + filename + ".dat", wordPairs);
		}
	}

	/**
	 * this method removes the root of the tree, if its present
	 */
	@Override
	public Comparable removeMax() {
		if (firstFreeIndex > 0) {
			Entry removedItem = dictionaryEntries.get(0);
			dictionaryEntries.set(0, dictionaryEntries.get(firstFreeIndex - 1));
			dictionaryEntries.remove(firstFreeIndex - 1);
			firstFreeIndex--;
			siftDown();
			return removedItem;
		} else {
			return null;
		}
	}

	/**
	 * this method returns the root, if present, otherwise it returns null
	 */
	@Override
	public Comparable getMax() {
		if (firstFreeIndex == 0) {
			return null;
		} else {
			return dictionaryEntries.get(0);
		}
	}

	/**
	 * returns true, if there are no Entries in the dictionary
	 */
	@Override
	public boolean isEmpty() {
		return firstFreeIndex == 0;
	}

	/**
	 * returns the number of the elements in the dictionaryEntries
	 */
	@Override
	public int getSize() {
		return dictionaryEntries.size();
	}

	/**
	 * this method clears the dictionary
	 */
	@Override
	public void clear() {
		dictionaryEntries = new ArrayList<Entry>();
	}

	/**
	 * @return the dictionaryEntries
	 */
	public ArrayList<Entry> getDictionaryEntries() {
		return dictionaryEntries;
	}

	/**
	 * @param dictionaryEntries
	 *            the dictionaryEntries to set
	 */
	public void setDictionaryEntries(ArrayList<Entry> dictionaryEntries) {
		this.dictionaryEntries = dictionaryEntries;
	}

	/**
	 * 
	 * @param spanishWordis
	 *            passed
	 * @return the english word if a match is found or null otherwise
	 */
	public String getEntry(String spanishWord) {
		Entry searchedEntry = new Entry(spanishWord, null);
		String englishWord = null;
		int entryIndex = findEntry(0, searchedEntry);
		if (entryIndex != -1) {
			englishWord = dictionaryEntries.get(entryIndex).getEnglishWord();
		}
		return englishWord;
	}

	/**
	 * 
	 * @param index
	 *            specifies the index of the Entry to be checked
	 * @param searchedEntry
	 *            specifies the the word to be found
	 * @return
	 */
	private int findEntry(int index, Entry searchedEntry) {
		int entryIndex = -1;
		if (dictionaryEntries.size() > index) {
			if (dictionaryEntries.get(index).compareTo(searchedEntry) == 0) {
				numberOfStepsInSearch++;
				return index;
			} else if (dictionaryEntries.get(index).compareTo(searchedEntry) < 0) {
				entryIndex = -1;
			} else if (dictionaryEntries.get(index).compareTo(searchedEntry) > 0) {
				numberOfStepsInSearch++;
				int leftIndex = findEntry(index * 2 + 1, searchedEntry);
				
				if (leftIndex != -1) {
					entryIndex = leftIndex;
				}
				if (entryIndex == -1) {
					int rightIndex = findEntry(index * 2 + 2, searchedEntry);
					if (rightIndex != -1) {
						entryIndex = rightIndex;
					}
				}
			}
		}
		return entryIndex;
	}

	/**
	 * @return the numberOfStepsInSearch
	 */
	public int getNumberOfStepsInSearch() {
		return numberOfStepsInSearch;
	}

	/**
	 * @param numberOfStepsInSearch
	 *            the numberOfStepsInSearch to set
	 */
	public void setNumberOfStepsInSearch(int numberOfStepsInSearch) {
		this.numberOfStepsInSearch = numberOfStepsInSearch;
	}
}
