package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.MaxHeap;

/**
 * 
 * @author Pawel Paszki
 * @version 06/04/2016
 * This unit test case iterates over all dictionaryEntries' elements
 * and makes sure, that each of the child in the tree comparing to its parent
 * does not have a greater value (ie is not greater lexicographically)
 *
 */
public class MaxHeapTest {
	private MaxHeap<?> maxHeap;
	@Before
	public void setUp() throws Exception {
		maxHeap = new MaxHeap<Object>();
		maxHeap.loadDictionary();
	}

	@After
	public void tearDown() throws Exception {
		maxHeap = null;
	}

	/**
	 * this method tests if the dictionary is in a form of MaxHeap, ie 
	 * The parent node has greater value of its child nodes
	 */
	@Test
	public void test() {
		for(int i = 0; i < maxHeap.getDictionaryEntries().size(); i = i * 2 + 1) {
			assertTrue(maxHeap.getDictionaryEntries().get(i / 2).compareTo(maxHeap.getDictionaryEntries().get(i)) >= 0);
		}
	}

}
