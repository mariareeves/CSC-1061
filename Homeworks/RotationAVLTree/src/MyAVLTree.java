import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyAVLTree<K,V> implements Map<K,V>, Iterable<MyAVLTree<K,V>.Node> {
	private Node root = null;
	private int size = 0;
	private List<Node> path = new ArrayList<>();
	
	protected class Node {
		private K key;
		private V value;
		private Node left = null;
		private Node right = null;
		private int height;
		
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public void setKey(K key) {
			this.key = key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public Node getLeft() {
			return left;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public Node getRight() {
			return right;
		}

		public void setRight(Node right) {
			this.right = right;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}
	}

	@Override
	public Iterator<Node> iterator() {
		return new RecursiveIterator();
	}
	
	private class RecursiveIterator implements Iterator<Node> {

		private List<Node> list = new LinkedList<>();
		
		public RecursiveIterator() {
			inorder(root);
		}
		
		private void inorder(Node node) {
			if (node == null) {
				return;
			}
			inorder(node.left);
			list.add(node);
			inorder(node.right);
		}
			
//		private void preorder(Node node) {
//			if (node == null) {
//				return;
//			}
//			list.add(node.value);
//			preorder(node.left);
//			preorder(node.right);
//		}
//	
//		private void postorder(Node node) {
//			if (node == null) {
//				return;
//			}
//			postorder(node.left);
//			postorder(node.right);
//			list.add(node.value);
//		}
		
		@Override
		public boolean hasNext() {
			return !list.isEmpty();
		}

		@Override
		public Node next() {
			return list.remove(0);
		}
		
	}

	private class NonRecursiveIterator implements Iterator<V> {
		private Deque<Node> stack = new ArrayDeque<>();
		
		public NonRecursiveIterator() {
			pushOnStack(root);
		}
		
		public void pushOnStack(Node node) {
			Node current = node;
			while (current != null) {
				stack.push(current);
				current = current.left;
			}
		}
		
		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public V next() {
			Node node = stack.pop();
			V value = node.value;
			pushOnStack(node.right);
			return value;
		}
		
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V get(Object key) {
		Node current = root;
		Comparable<K> k = (Comparable<K>) key;
		
		while(current != null) {
			if (k.compareTo(current.key) < 0 ) {
				current = current.left;
			}
			else if (k.compareTo(current.key) > 0) {
				current = current.right;
			}
			else {
				return current.value;
			}
		}
		
		return null;
	}

	@Override
	public V put(K key, V value) {
		if (root == null) {
			Node newNode = new Node(key, value);
			root = newNode;
			size++;
			updateHeight(root);
			return value;
		}
		
		path.clear();
		Node parent = null;
		Node current = root;
		Comparable<K> k = (Comparable<K>) key;
		while(current != null) {
			path.add(current);
			if(k.compareTo(current.key) < 0) {
				parent = current;
				current = current.left;
			}
			else if (k.compareTo(current.key) > 0) {
				parent = current;
				current = current.right;
			}
			else {
				V oldVal = current.value;
				current.value = value;
				return oldVal;
			}
		}
		
		Node newNode = new Node(key, value);
		if (k.compareTo(parent.key) < 0) {
			parent.left = newNode;
		}
		else {
			parent.right = newNode;
		}
		
		updateHeight(root);
		balancePath();
		
		size++;
		return null;
	}
	
	private void updateHeight(Node node) {
		if(node.left == null && node.right == null) {
			node.height = 0;
		}
		else if (node.left == null) {
			node.height = node.right.height + 1;
		}
		else if (node.right == null) {
			node.height = node.left.height + 1;
		}
		else {
			node.height = Math.max(node.left.height,  node.right.height) + 1;
		}
	}
	
	private int balanceFactor(Node current) {
		int balanceFactor = 0;
		if (current.right == null) {
			balanceFactor = -current.height;
		}
		else if (current.left == null) {
			balanceFactor = current.height;
		}
		else {
			balanceFactor = current.right.height - current.left.height;
		}
		return balanceFactor;
 	}
	
	private void balancePath() {
		for (int i = path.size() - 1; i >= 0; i--) {
			Node gp = path.get(i);
			updateHeight(gp);
			Node parent_of_gp = null;
			if (i > 0) {
				parent_of_gp = path.get(i - 1);
			}

			int bf = balanceFactor(gp);

			switch (bf) {
				case -2:
					// tree is too heavy on the left side
					if (balanceFactor(gp.left) <= 0) {
						// LL case: left child also heavy on left
						balanceLL(gp, parent_of_gp);
					} else {
						// LR case: left child heavy on right
						balanceLR(gp, parent_of_gp);
					}
					break;

				case 2:
					// tree is too heavy on the right side
					if (balanceFactor(gp.right) >= 0) {
						// RR case: right child also heavy on right
						balanceRR(gp, parent_of_gp);
					} else {
						// RL case: right child heavy on left
						balanceRL(gp, parent_of_gp);
					}
					break;
			}
		}
	}
	
	private void balanceLL(Node gp, Node parentOfGp) {
		Node parent = gp.left;
		Node child = parent.left;
		
		// Deal with gp connection to it's parent
		if (gp == root) {
			root = parent;
		}
		else {
			if (parentOfGp.left == gp) {
				parentOfGp.left = parent;
			}
			else {
				parentOfGp.right = parent;
			}
		}
	
		// Make other node hanging off parent to gp
		gp.left = parent.right;
		// Move gp to right of parent
		parent.right = gp;

		updateHeight(gp); 
		updateHeight(child);
		updateHeight(parent);
	}
	
	private void balanceLR(Node gp, Node parentOfGp) {
		Node parent = gp.left;
		Node child = parent.right;

		// Deal with gp connection to it's parent
		if (gp == root) {
			root = child;
		}
		else {
			if (parentOfGp.left == gp) {
				parentOfGp.left = child;
			}
			else {
				parentOfGp.right = child;
			}
		}
		
		// Since child is going to lose it's nodes 
		// assign them to the right place first
		parent.right = child.left;
		gp.left = child.right;
		
		// Now change child's children
		child.left = parent;
		child.right = gp;
		
		updateHeight(gp);
		updateHeight(parent);
		updateHeight(child);
	}

	// Homework - Complete this section
	//  Right-Right case
	private void balanceRR(Node gp, Node parentOfGp) {

		Node parent = gp.right;       // parent is gp's right child
		Node child = parent.right;    // parent's right child

		// Connect parent to gp's parent
		if (gp == root) {
			root = parent;            // parent becomes new root
		} else {
			// attach parent on the correct side of gp's parent
			if (parentOfGp.left == gp) {
				parentOfGp.left = parent;
			} else {
				parentOfGp.right = parent;
			}
		}

		// Move parent's left subtree to gp's right
		gp.right = parent.left;

		// Put gp under parent so the left side
		parent.left = gp;

		// Update heights
		updateHeight(gp);
		if (child != null) updateHeight(child);
		updateHeight(parent);
	}

	// Homework - Complete this section
	// Fix Right-Left case
	private void balanceRL(Node gp, Node parentOfGp) {

		Node parent = gp.right;       // parent is gp's right child
		Node child = parent.left;     // child is the left child of parent

		// Connect child to gp's parent
		if (gp == root) {
			root = child;             // child becomes new root
		} else {
			// attach child to the correct side of gp's parent
			if (parentOfGp.left == gp) {
				parentOfGp.left = child;
			} else {
				parentOfGp.right = child;
			}
		}

		// Move subtrees before rotating
		parent.left = child.right;    // child's right subtree goes to parent
		gp.right = child.left;        // child's left subtree goes to gp

		// Now rotate child to the top
		child.left = gp;              // gp becomes left child
		child.right = parent;         // parent becomes right child

		// Update heights
		updateHeight(gp);
		updateHeight(parent);
		updateHeight(child);
	}


	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}
}