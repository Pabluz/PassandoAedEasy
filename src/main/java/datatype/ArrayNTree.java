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
		T aux;
		this.data = elem;
		this.capacity = capacity;
		
		if(data == elem && isLeaf())
			data = null;
		else{
			aux = data;
			data = children[0].data;
		}
		
		this.children  = (ArrayNTree<T>[])Array.newInstance(ArrayNTree.class, capacity);
		size++;
	}

	/**
	 * Creates a tree with the elements inside the given list
	 * @param list     The list with all the elements to insert
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 * @return The tree with elements inside
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
		return data!=null && noChild(data); //TODO
	}
	
	private boolean noChild(T elem) {
		ArrayNTree<T> tree = findTree(elem);
		if (tree == null) {
			return false;
		}
		return tree.isEmpty();
	}

	private ArrayNTree<T> findTree(T element) {
		int count = 0;

		while (count < children.length) {
			if (children[count].data == element)
				return children[count];
			count++;
		}

		return null;
	}
	public int size() {
		return size;
	} // ta feto

	/////////////////////////////////////
	public int countLeaves() {
		int count = 0;
		if(isEmpty()) {
			return 0;
		}else{
			return auxCountLeaves(this, count);
		}

	}
	
	private int auxCountLeaves(ArrayNTree<T> arvore, int count ) {
		
		for(ArrayNTree tree: arvore.children){

			if(tree!=null&&tree.isLeaf()) {
				count++;
			}
			 if(!tree.isLeaf()) {
				 auxCountLeaves(tree, count);
			 }
				 

		}
		return count;
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
				altura++;
				int currAlt = tree.auxHeight(0, altura);
				if(currAlt > altura) {
					altura = currAlt;
				}
			}
		}
		return altura + 1;
	}

	private int auxHeight(int currAlt, int altura) {
		for(ArrayNTree<T> tree:children) {
			//altura = auxHeight(tree);
			if(tree != null) {
				altura++;
				currAlt = tree.auxHeight(currAlt, altura);
				if(currAlt > altura) {
					altura = currAlt;
				}
			}
		}
		return altura;
	}

	
		//+ .max(auxHeight(root.left), auxHeight(root.right));	}




		public T min() { return toList().get(0); } //ta feto

		/////////////////////////////////////

		public T max() {
			return toList().get(toList().size() -1);
		} //ta feto

		/////////////////////////////////////

		public boolean contains(T elem) {

			List <T> listElems = toList();
			for(int i = 0; i<size(); i++){
				if(listElems.get(i).equals(elem))
					return true;
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

		private List<Integer> freeSpaces(int index){

			List<Integer> arrayIndex = new ArrayList<Integer>();
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
			T aux;
			int index = 0;
			ArrayNTree <T> currParent = this;
			if(!contains(elem) || isEmpty())
				return;
			else{
				if(currParent.data == elem && isLeaf()){
					currParent.data = null;
				}
				else if (currParent.data == elem && !isLeaf()){
					aux = currParent.data;
					currParent.data = currParent.children[0].data;
					currParent.children[0].data = aux;
					delete(aux);
				}
				else{
					while(index < currParent.children.length){
						if(elem.compareTo(currParent.children[index].data) > 0){
							currParent = currParent.children[index-1];
							index = 0;
						}
						if(elem.compareTo(currParent.children[index].data) == 0 && !isLeaf()){
							aux = currParent.children[index].data;
							currParent.data = currParent.children[index].children[0].data;
							currParent.children[index].children[0].data = aux;
							delete(aux);
						}
						index++;
					}
				
				}
			}
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
	public Iterator<T> iterator() {
		return new NTreeIterator(this);
	}
	
	/**
	 * Iterator for the ArrayNTree class
	 * 
	 *
	 */
	private class NTreeIterator implements Iterator<T> {

		private Queue<ArrayNTree<T>> deque = new ConcurrentLinkedQueue<>();
		
		private NTreeIterator(ArrayNTree<T> root) {
			queue(root);
		}
		
		/**
		 * 
		 */
		private void queue(ArrayNTree<T> elem) {
			// adiciona
			deque.add(elem);
			for (int i = 0; i< children.length && !elem.children[i].isEmpty(); i++)
			
				queue(elem.children[i]);
		}
		
		public boolean hasNext() {
			// se o deque nao é nul, ent existe um proximo elem
			return deque!=null;
		}

		
		public T next() throws NoSuchElementException {
			
			if (hasNext()) 
				
				return deque.poll().data;
				
			// caso naão exista o next lança uma exception
			throw new NoSuchElementException();
		}
		
	}

}
