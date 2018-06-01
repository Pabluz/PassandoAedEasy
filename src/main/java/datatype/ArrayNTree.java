package main.java.datatype;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
/**
 * Projeto AED do grupo  aed69
 * @author numero 48595 Joao Costa
 * @author numero 47871 Ricardo Cruz
 *
 */
public class ArrayNTree<T extends Comparable<T>> implements NTree<T>,Cloneable,Iterable<T> {

	private T data;
	private int capacity;   
	private int size;	
	private ArrayNTree<T> node;
	private ArrayNTree<T> tree;
	private ArrayNTree<T>[] children; 

	/**
	 * Creates an empty tree 
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 */
	@SuppressWarnings("unchecked")
	public ArrayNTree(int capacity) {
		this.capacity=capacity;    
		tree=this; 		
		this.children  = (ArrayNTree<T>[]) Array.newInstance(ArrayNTree.class, capacity);

	}
	/**
	 * Create a tree with one element
	 * @param elem     The element value
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 */
	public ArrayNTree(T elem, int capacity) {
		tree=this;
		data=elem; 
		capacity=capacity;   
		tree.size++;

		children  = (ArrayNTree<T>[]) Array.newInstance(ArrayNTree.class, capacity);
	}   

	/**
	 * Creates a tree with the elements inside the given list
	 * @param elem     The list with all the elements to insert
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 */
	public ArrayNTree(List<T> list, int capacity) {
		tree=this;
		this.capacity=capacity;
		this.children  = (ArrayNTree<T>[]) Array.newInstance(ArrayNTree.class, capacity);

		Iterator<T> treeList=list.iterator();
		while(treeList.hasNext()){
			this.insert(treeList.next());

		}                     
	}
	/**
	 * Verifies if tree is empty
	 * @return true iff tree is empty
	 */
	public boolean isEmpty() {
		return size == 0;  
	}

	/**
	 * Verifies if tree is a leaf, ie, only has one element
	 * @return true iff tree is a leaf
	 */	
	public boolean isLeaf(){
		boolean resultado=true;
		for(int k=0;k<children.length&&resultado;k++){
			if (children[k] != null)
				resultado=false;                     
		}	        
		return resultado;    
	}

	
	/**
	 * The number of elements of a tree. An empty tree has zero elements
	 * @return the number of elements
	 * 		O(1)
	 */
	public int size() {
		return size;  
	}


	/**
	 * Count the number of leaves. An empty tree has zero leaves
	 * @return the number of leaves
	 */
	public int countLeaves() {
		return countLeaves(tree);

	}

	private int countLeaves(ArrayNTree<T> child){

		if(child==null)
			return 0;
		if(child.isLeaf())
			return 1;

		return count(child);

	}
	private int count(ArrayNTree<T> child) {
		int countL=0;
		for(int j=0;j<capacity;j++){
			countL+=countLeaves(child.children[j]);			
		}

		return countL;
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
	/**
	 * /**
	 * The minimum value of the tree
	 * @requires !isEmtpy()
	 * @return the maximum value
	 *     O(1)
	 */
	 
	public T min() { return toList().get(0); }
	/////////////////////////////////////
	/**
	 * The maximum value of the tree
	 * @requires !isEmtpy()
	 * @return the maximum value
	 *								O(1)
	 */
	
	public T max() {
		return toList().get(toList().size() -1);
	} 
	
	/**
	 * dif
	 *  O(1)
	 */	 
	public boolean contains(T elem) {

		List <T> listElems = toList();
		for(int i = 0; i<size(); i++){
			if(listElems.get(i).equals(elem))
				return true;
		}
		return false;
	}
	/**
	 * Insert element into tree keeping the invariant
	 * If an element already exists, the tree does not change
	 * @param elem the element to be inserted
	 */
	public void insert(T elem) {
		insert(tree, elem);
		
	}
	private void insert(ArrayNTree<T> currentTree, T elem) {

		if(currentTree.data == null){
			currentTree.data=elem;
			tree.size++;
			return;
		}
		if(elem.compareTo(currentTree.data) == 0){
			return;
		}

		if(elem.compareTo(currentTree.data) < 0){
			addBeLow(currentTree,elem);
			put(currentTree);                
			this.tree.size++;
			return;
		}

		if(elem.compareTo(currentTree.data) > 0){
			addBigest(currentTree,elem);
		}
	}  
	private void addBeLow(ArrayNTree<T>currentTree, T elem){
		T  data=currentTree.data;
		currentTree.data=elem;
		ArrayNTree<T> fst= currentTree.children[0];
		currentTree.children[0]=new ArrayNTree<T>(data ,capacity);
		currentTree.children[0].node=currentTree;
		currentTree.children[0].children[0]=fst;
		
	}
	private void addBigest(ArrayNTree<T>currentTree, T elem){

		for(int i=0;i<capacity;i++){
			if(currentTree.children[i] == null){                            
				currentTree.children[i]=new ArrayNTree<T>(elem,capacity);
				currentTree.children[i].node=currentTree;

				this.tree.size++;

				return;                                                   
			}else if(elem.compareTo(currentTree.children[i].data) < 0 ) {
				if(freeSpace(currentTree.children)&&i<capacity-1 ){
					ArrayNTree<T> value= currentTree.children[i],
							v;
					for(int k=i;k<capacity-1;k++){
						v=currentTree.children[k+1];
						currentTree.children[k+1]=value;
						value=v;
					}
					currentTree.children[i]=new ArrayNTree<T>(elem,capacity);
					currentTree.children[i].node=currentTree;
					tree.size++;
					return;	
				}
				if(i==0){								

					insert(currentTree.children[i],elem);
					return;

				} 
				if (elem.compareTo(currentTree.children[i].data) < 0&&i!=0 
						&& elem.compareTo(currentTree.children[i-1].data) > 0 ){
					insert(currentTree.children[i-1],elem);
					return;
				}
			}	                             
			if (elem.compareTo(currentTree.children[i].data) == 0) {
				return;
			}
		}
		insert(currentTree.children[capacity-1],elem);	
		return;
	}


	private boolean freeSpace(ArrayNTree<T>[] arrayTree){

		for(int i =0;i<arrayTree.length;i++){
			if(arrayTree[i]==null)
				return true;
		}	
		return false;
	}       
	/**
	 * Delete element from tree keeping the invariant
	 * If an element does not exist, the tree does not change
	 * @param elem the element to be deleted
	 */

	public void delete(T elem) {	
		delete(elem,this.tree);
	}

	private void delete(T elem,ArrayNTree<T> currentTree) {	      
		if(elem.compareTo(currentTree.data) == 0){
			order(currentTree);                                		
			put(currentTree);	
			tree.size--;			
			return;
		}else {
			for(int i=0;i<capacity;i++) {
				if(currentTree.children[i] != null && 
					currentTree.children[i].isLeaf()&&elem.compareTo(currentTree.children[i].data) == 0)
				{	
					currentTree.children[i]=null;

					for(int j=i;j<capacity-1;j++){
						currentTree.children[j]=currentTree.children[j+1];
					}
					currentTree.children[capacity-1]=null;
					put(currentTree);
					tree.size--;
					return;
				}

				if( i!= 0 && currentTree.children[i]!=null && elem.compareTo(currentTree.children[i].data) < 0){
					delete(elem,currentTree.children[i-1]);
					return;
				}
				else if(i == 0 && currentTree.children[i]!= null && elem.compareTo(currentTree.children[i].data) < 0){
					delete(elem,currentTree.children[i]);
					return;
				}
				if(currentTree.children[i]!=null && elem.compareTo(currentTree.children[i].data) == 0) {
					delete(elem,currentTree.children[i]);
					return;
				}
			}

			int index=0;
			for(int i=0; i < capacity;i++){
				if(currentTree.children[i]!=null)
					index=index+1;
			}
			delete(elem,currentTree.children[index-1]);
			return;
		}      
	}	

	private void put(ArrayNTree<T> current){
		if(current!=null) {
			for(int c=0;c<capacity;c++){
				if(current.children[c]!=null && !current.children[c].isLeaf() && freeSpace(current.children)){
					T elem=current.children[c].data;

					order(current.children[c]);
					this.tree.size--;
					insert(elem);

					put(current.children[c+1]);
					return;
				}
			}
		}	
	}
	private void order(ArrayNTree<T> treeOrd){
		 
		if(treeOrd.children[0] == null)
			return;

		if(treeOrd.children[0].isLeaf()){
			treeOrd.data=treeOrd.children[0].data;
			treeOrd.children[0]=null;
			treeOrd.children=jump(treeOrd.children);
			return;
			
		}else	{
			treeOrd.data=treeOrd.children[0].data;	
			order(treeOrd.children[0]);
		}
	}

	private ArrayNTree<T>[] jump(ArrayNTree<T>[] children){

		if(!freeSpace(children))
			return children;
		for(int k=1;k<capacity;k++)
			children[k-1]=children[k];
			children[capacity-1]=null;
			return children;

	} 

	/**
	 * Is this tree equal to another object?
	 * Two NTrees are equal iff they have the same values
	 */
	@SuppressWarnings("unchecked")
	@Override
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

		return true;}

	/**
	 * Convert tree into list. The list has the elements accordingly to the tree's 
	 * prefix traversal, ie, the elements will be sequenced by increasing order
	 * @returns the list with the tree's elements
	 */	
	public List<T> toList() {
		List<T> lista=new LinkedList<T>();
		toList(lista,tree);

		return lista;
	}
	
	private void toList( List<T> lista,ArrayNTree<T> tree) {
		if(tree!=null) {
			lista.add(tree.data);
			for(int j=0;j<capacity;j++) {
				toList(lista,tree.children[j]);	}
		}
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
 
	public String info() {
		return this + ", size: " + size() + ", height: " + height() + ", nLeaves: " + countLeaves();
	}

	/**
	 * @returns an iterator traversing elements in a increasing order
	 */
	public Iterator<T> iterator() {
		return new NArrayTrreIterator();  // TODO
	}

	private class NArrayTrreIterator implements Iterator<T> {

	private Stack<ArrayNTree<T>> stackTree;

	private NArrayTrreIterator(){
			stackTree=new Stack();
			if(!tree.isEmpty())
				stackTree.push(tree);
		}
			//next tree on array
		public T next(){

			for(int k=capacity-1;k>-1;k--){

				if(node.children[k].isEmpty())
					//pilha, primeiroo a entrar ultimo a sair
					stackTree.push(node.children[k]);
			}
			return node.data;
		}
		public boolean hasNext(){
			return !stackTree.empty();
		}
	}
}
