package main.java.datatype;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Projeto AED do grupo  aed69
 *
 * @author numero 47871 Ricardo Cruz
 * @author numero 48597 Joao Costa
 *
 */
public class ArrayNTree<T extends Comparable<T>> implements NTree<T> {

	private T  data;
	private int size;
	private int capacity;
	private ArrayNTree<T>[] children;     // exemplo do array a usar;

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
	@SuppressWarnings("unchecked")
	public ArrayNTree(T elem, int capacity) {
		this.data = elem;
		this.capacity = capacity;
		this.children  = (ArrayNTree<T>[])Array.newInstance(ArrayNTree.class, capacity);
		size++;
	}

	/**
	 * Creates a tree with the elements inside the given list
	 * @param list     The list with all the elements to insert
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 * @return The tree with elements inside
	 */
	/*public ArrayNTree(List<T> list, int capacity) {
		int count = 0;
		T aux;
		ArrayNTree<T> newArrayNTree;
		while(count < list.size()) {
		}
		// TODO
	}
	*/
	
	public ArrayNTree(List<T> list, int capacity) {
		this.capacity = capacity;
		this.size = list.size();
		for (T e : list)
			insert(e);
	}

	/////////////////////////////////////

	public boolean isEmpty() {
		return data == null;
	} //ta feto

	/////////////////////////////////////

	public boolean isLeaf() {
		return data!=null && auxLeaf(children); //TODO
	}
	private boolean auxLeaf(ArrayNTree<T>[] child){
		boolean verifica=false;
		for(int i=0; i<child.length&&!verifica; i++) 
			if(child[i]==null)
				return true;


		return false;
	}
	public int size() {
		return size;
	} // ta feto

	/////////////////////////////////////
	public int countLeaves() {
		return capacity;


		//	return countLeavesAux(children);	\\\\\\\\\\\\
	}



	public int countLeavesAux(ArrayNTree<T>[] child) {
		return capacity;

		/////////////////////////////////////

	}
		/**
		 * The tree's height. An empty tree has height zero, a leaf has height one
		 * @return the tree's height
		 */
	public int height() {

	    int altura = 0;
	    for(ArrayNTree<T> tree:children) {
	        //altura = auxHeight(tree);
	    	if(tree != null) {
	    		int currAlt = tree.height();
	    		if(currAlt > altura) {
	    			altura = currAlt;
	    		}
	    	}
	    }
	    return altura + 1;
	}

	
		//+ .max(auxHeight(root.left), auxHeight(root.right));	}




		public T min() { return toList().get(0); } //ta feto

		/////////////////////////////////////

		public T max() {

			List<T> listElems = toList();
			T max = listElems.get(0);
			int count = 1;

			while(count < listElems.size()){

				if(listElems.get(count).compareTo(max) > 0)
					max = listElems.get(count);

				count++;
			}
			return max;
		} //ta feto

		/////////////////////////////////////

		public boolean contains(T elem) {

			List <T> listElems = toList();
			for(int i = 0; i<size(); i++){
				if(listElems.get(i).equals(elem))
					return true;
				else{
					if(listElems.get(i).compareTo(elem) > 0)
						return false;
				}
			}
			return false;
		}// ta feto

		/////////////////////////////////////


		/**
		 * Insert element into tree keeping the invariant
		 * If an element already exists, the tree does not change
		 * @param elem the element to be inserted
		 */
		public void insert(T elem) {

			T aux;
			int index = 0;
			boolean found = false;

			//se a arvore ja contiver o elemento ele nao insere
			if (contains(elem))
				return;

			//se a arvore estiver vazia ele coloca o elemento no data
			if (isEmpty()) {
				data = elem;
			} else {
				//caso a raiz da arvore seja maior do que o elemento a ser inserido
				if (data.compareTo(elem) > 0) {
					aux = data;
					data = elem;
					insert(aux);
				} else {
					while (index < children.length) {

						List<Integer> espaco = freeSpaces(index);
						//se nao ta cheio ele coloca no inicio
						if (children[index].size() - 1 < capacity) {
							if (espaco.size() == capacity) {
								children[index].children[0].data = elem;
								return;
							}
							//se nao tiver espaco livre passa para a proxima arvore
							else if (espaco.size() == 0)
								index++;
							//se tiver espaco livre mas nao der para colocar no inicio
							else {
								for (int i = 0; i < capacity; i++) {
									if (children[index].children[i].data.compareTo(elem) > 0) {
										if (children[index].children[i - 1].data == null) {
											children[index].children[i - 1].data = elem;
											break;
										}
									} else {
										aux = children[index].children[i - 1].data;
										children[index].children[i - 1].data = elem;
										insert(aux);
									}
								}
							}
						}
					}
				}
			}
		}
		private T findBiggerElem(int index,int childIndex, T elem){

			while(index < capacity){
				if(elem.compareTo(children[index].children[childIndex].data) < 0){
					return children[index].data;
				}
				index++;
			}

			return null;

		}

		private List<Integer> freeSpaces(int index){

			List<Integer> arrayIndex = new ArrayList<>();
			int local = 0;
			for(int i = 0; i< capacity; i++){
				if(children[index].children[i].data == null) {
					arrayIndex.add(index);
					local++;
				}
			}

			return arrayIndex;
		}

		/////////////////////////////////////

		public void delete(T elem) {
			// TODO
		}

		/////////////////////////////////////

		/**
		 * Is this tree equal to another object?
		 *
		 * Two NTrees are equal iff they have the same values
		 */
		@SuppressWarnings("unchecked")
		public boolean equals(Object other) {

			// se o objeto for o mesmo
			if(this==other)
				return true;

			// Se nao forem do mesmo tipo
			if(!(other instanceof ArrayNTree ))
				return false;

			if(this.size()!= ((NTree<T>) other).size())
				return false;

			List <T> original = this.toList();
			List <T> otherList = ((ArrayNTree) other).toList();
			int count = 0;

			while(count< size()) {
				if (original.get(count).equals(otherList.get(count)))
					return false;
				count++;
			}

			return true;
		}


		//ta feto
		public List<T> toList() {
			List<T> list = new ArrayList<T>();
			Iterator<T> iterator = iterator();

			while(iterator.hasNext())
				list.add(iterator.next());

			return list;
		}

		/**
		 * @returns a new tree with the same elements of this
		 */
		public ArrayNTree<T>clone(){
			//FIXME
			List<T> list = toList();
			ArrayNTree<T> clone = new ArrayNTree<T>(capacity);
			for(int i = 0; i <list.size(); i++)
				clone.insert(list.get(i));
			return clone;
		}

		/**
		 * @returns
		 */
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
		public Iterator<T> iterator(){
			return new IteratorNTree();
		}

		//FIXME
		private class IteratorNTree implements Iterator{
			private int position = 0;

			public boolean hasNext() {
				return position < size() && data != null;

			}

			public T next() {
				if (this.hasNext())
					return children[position++].data;

				return null;
			}
		}
	}


