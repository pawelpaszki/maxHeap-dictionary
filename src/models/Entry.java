package models;

/**
 * 
 * @author Pawel Paszki This class represents an individual entry in the
 *         dictionary, which stores two Strings - one representing a spanish
 *         word and another one - its english translation
 * @param <T>
 */
public class Entry<T> implements Comparable<T> {

	private String spanishWord;
	private String englishWord;

	/**
	 * constructor used to create new Entry
	 * @param spanishWord - Spanish word
	 * @param englishWord - English word
	 */
	public Entry(String spanishWord, String englishWord) {
		this.spanishWord = spanishWord;
		this.englishWord = englishWord;
	}
	/**
	 * @return the spanishWord
	 */
	public String getSpanishWord() {
		return spanishWord;
	}

	/**
	 * @param spanishWord
	 *            the spanishWord to set
	 */
	public void setSpanishWord(String spanishWord) {
		this.spanishWord = spanishWord;
	}

	/**
	 * @return the englishWord
	 */
	public String getEnglishWord() {
		return englishWord;
	}

	/**
	 * @param englishWord
	 *            the englishWord to set
	 */
	public void setEnglishWord(String englishWord) {
		this.englishWord = englishWord;
	}

	/**
	 * returns the value obtained after comparing "this" object to another
	 * object (-1 || 0 || 1)
	 */
	@Override
	public int compareTo(Object other) {
		try {
			return this.getSpanishWord().compareTo(((Entry<?>) other).getSpanishWord());
		} catch (ClassCastException e) {
			return -2;
		}
	}	
}
