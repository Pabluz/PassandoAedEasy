package main.java.datatype;

import java.util.*;
import java.lang.reflect.Array;

/**
 * Projeto AED do grupo nº ??  [preencher]
 *  
 * @author numero aluno + nome 
 * @author numero aluno + nome 
 * @author numero aluno + nome
 *  
 */
public class ArrayNTree<T extends Comparable<T>> implements NTree<T> {

	private T data;
	private ArrayNTree<T>[] children;     // exemplo do array a usar
	
	// podem incluir outros atributos
	
	/////////////////////////////////////

	/**
	 * Creates an empty tree 
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 */
	@SuppressWarnings("unchecked")
	public ArrayNTree(int capacity) {
		// TODO
		
        // exemplo de como se constroi o array de arvores
		this.children  = (ArrayNTree<T>[])Array.newInstance(ArrayNTree.class, capacity);
	}

	/**
	 * Create a tree with one element
	 * @param elem     The element value
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 */
	public ArrayNTree(T elem, int capacity) {
		// TODO
	}

	/**
	 * Creates a tree with the elements inside the given list
	 * @param elem     The list with all the elements to insert
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 */
	public ArrayNTree(List<T> list, int capacity) {
		// TODO
	}

	/////////////////////////////////////

	public boolean isEmpty() {
		return false;  // TODO
	}

	/////////////////////////////////////

	public boolean isLeaf() {
		return false;  // TODO
	}

	/////////////////////////////////////

	public int size() {
		return -1;  // TODO
	}

	/////////////////////////////////////

	public int countLeaves() {
		return -1;  // TODO

	}

	/////////////////////////////////////

	public int height() {
		return -1;  // TODO
	}

	/////////////////////////////////////

	public T min() {
		return null;  // TODO
	}

	/////////////////////////////////////

	public T max() {
		return null;  // TODO
	}

	/////////////////////////////////////

	public boolean contains(T elem) {
		return false;  // TODO
	}

	/////////////////////////////////////

	public void insert(T elem) {
		// TODO
	}

	/////////////////////////////////////

	public void delete(T elem) {
		// TODO
	}

	/////////////////////////////////////

	/**
	 * Is this tree equal to another object?
	 * Two NTrees are equal iff they have the same values
	 */
	@SuppressWarnings("unchecked")
	public boolean equals(Object other) {
		return false;  // TODO
	}

	/////////////////////////////////////

	public List<T> toList() {
		return null;  // TODO
	}

	/////////////////////////////////////

	/**
	 * @returns a new tree with the same elements of this
	 */
	public ArrayNTree<T> clone() {
		return null;  // TODO
	}

	/////////////////////////////////////

	public String toString() {
		if (isEmpty())
			return "[]";

		if (isLeaf())
			return "["+data+"]";

		StringBuilder sb = new StringBuilder();
		sb.append("["+data+":");

		for(NTree<T> brt : children)
			if (brt!=null)
				sb.append(brt.toString());

		return sb.append("]").toString();				
	}

	// more detailed information about tree structure 
	public String info() {
		return this + ", size: " + size() + ", height: " + height() + ", nLeaves: " + countLeaves();
	}

	/////////////////////////////////////

	@Override
	public Iterator<T> iterator() {
		return null;  // TODO
	}

}
