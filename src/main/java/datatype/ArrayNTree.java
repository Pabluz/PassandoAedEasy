package main.java.datatype;

import java.util.*;
import java.lang.reflect.Array;

/**
 * Projeto AED do grupo  aed69
 *
 * @author numero 47871 Ricardo Cruz
 * @author numero 48597 Joao Costa
 * @author numero ----- ----------
 *
 */
public class ArrayNTree<T extends Comparable<T>> implements NTree<T> {

	private T prev, data;
	private int size;
	private int capacity;
	private ArrayNTree<T>[] children;     // exemplo do array a usar

	/**
	 * Creates an empty tree 
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 */
	@SuppressWarnings("unchecked")
	public ArrayNTree(int capacity) {
		// TODO
		// exemplo de como se constroi o array de arvores
		this.data = null;
		this.capacity = capacity;
		this.children  = (ArrayNTree<T>[])Array.newInstance(ArrayNTree.class, capacity);
	}

	/**
	 * Create a tree with one element
	 * @param elem     The element value
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 */
	public ArrayNTree(T elem, int capacity) {
		this.data = elem;
		this.capacity = capacity;
		this.children = new ArrayNTree[capacity];
		size++;
		// TODO
	}

	/**
	 * Creates a tree with the elements inside the given list
	 * @param list     The list with all the elements to insert
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 * @return The tree with elements inside
	 */
	public ArrayNTree(List<T> list, int capacity) {
		int count = 0;
		T aux;
		ArrayNTree<T> newArrayNTree;
		while(count < list.size()){
			if(count == 0)
				newArrayNTree = new ArrayNTree<T>(list.get(0),capacity);
			else {

				//se o elemento na lista for menor que o data, entao tem de se trocar de posicao
				if (list.get(count).compareTo(newArrayNTree.data) == 1) {
					aux = newArrayNTree.data;
					newArrayNTree.data = list.get(count);

					for (int i = 0; i < newArrayNTree.children.length; i++) {
						if (newArrayNTree.children[i] == null)
							newArrayNTree.children[i].data = aux;
						//incompleto
					}
				}
				size++;
			}
		}
		// TODO
	}

	/////////////////////////////////////

	public boolean isEmpty() {
		return data==null;
	}

	/////////////////////////////////////

	public boolean isLeaf() {
		return false;  // TODO
	}

	public int size() {
		return size;
	}

	/////////////////////////////////////

	public int countLeaves() {
		return -1;  // TODO

	}

	/////////////////////////////////////

	public int height() {
		return 0;
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
		//TODO
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
		// comparar toList() do arrayNtree com o other.toList com um for each
	/////////////////////////////////////
		return false;  // TODO
	}
	

	public List<T> toList() {
			// procura prefixa e colocar os elementos numa lista com o tamanho size
	/////////////////////////////////////
		return null;  // TODO
	}


	/**
	 * @returns a new tree with the same elements of this
	 */
	public ArrayNTree<T> clone() {
		//percorrer a lista e vais colocar os elementos respeitando a regra de colocacao prefixa
	/////////////////////////////////////
		return null;  // TODO
	}
	

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
