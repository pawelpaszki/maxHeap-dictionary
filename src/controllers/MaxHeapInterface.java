package controllers;

/**
 * 
 * @author Pawel Paszki
 * @version 06/04/2016
 * 
 * This interface specifies methods to be implemented by MaxHeap class
 * @param <T>
 */
public interface MaxHeapInterface<T extends Comparable<? super T>> {
	/**
	 * Adds a new entry to the heap.
	 * 
	 * @param newEntry
	 *            an object to be added
	 */
	public void add(T newEntry);

	/**
	 * Removes and returns the largest item in the heap.
	 * 
	 * @return either the largest object in the heap or, if the heap is empty
	 *         before the operation, null
	 */
	public T removeMax();

	/**
	 * Retrieves the largest item in the heap.
	 * 
	 * @return either the largest object in the heap or, if the heap is empty,
	 *         null
	 */
	public T getMax();

	/**
	 * Detects whether the heap is empty.
	 * 
	 * @return true if the heap is empty, else returns false
	 */
	public boolean isEmpty();

	/**
	 * Gets the size of the heap.
	 * 
	 * @return the number of entries currently in the heap
	 */
	public int getSize();

	/**
	 * Removes all entries from the heap.
	 */
	public void clear();
}
