package com.CSC161.ArrayAndLinkedList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * @author downey
 * @param <E>
 *
 */
public class MyDoubleLinkedList<E> implements List<E> {

	/**
	 * Node is identical to ListNode from the example, but parameterized with T
	 *
	 * @author downey
	 *
	 */
	private class Node {
		public E data;
		public Node next;
		public Node prev;      // Doubly

		public Node(E data) {
			this.data = data;
			this.next = null;
			this.prev = null;     // Doubly
		}
		@SuppressWarnings("unused")
		public Node(E data, Node next) {
			this.data = data;
			this.next = next;
			next.prev = this;    // Doubly
		}
		public String toString() {
			return "Node(" + data.toString() + ")";
		}
	}

	private int size;            // keeps track of the number of elements
	private Node head;           // reference to the first node
	private Node tail;            // Doubly reference to the last node
	/**
	 *
	 */
	public MyDoubleLinkedList() {
		head = null;
		tail = null;        // Doubly
		size = 0;
	}

	// IMPLEMENT - done
	@Override
	public boolean add(E element) {
		Node newNode = new Node(element);
		// Case 1: empty list
		if (head == null) {
			head = newNode;
			tail = newNode; // first and last node are the same
		}
		// Case 2: at least one node in the list
		else {
			tail.next = newNode;   // link the current tail to the new node
			newNode.prev = tail;   // set new node’s prev pointer
			tail = newNode;        // move tail to the new node
		}

		size++; // increase the number of nodes
		return true;
	}

	// IMPLEMENT - done
	@Override
	public void add(int index, E element) {
		Node newNode = new Node(element);

		// Case 0: empty list — becomes first node
		if (size == 0) {
			head = newNode;
			tail = newNode;
			size++;
			return;
		}

		if (index <= 0) {
			// insert at head
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
			size++;
			return;
		}

		if (index >= size) {
			// insert at tail
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
			size++;
			return;
		}

		// Middle insert: 0 < index < size
		Node prev = getNode(index - 1);
		if (prev == null) {
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
			size++;
			return;
		}

		Node next = prev.next; // node currently at 'index'
		newNode.prev = prev;
		newNode.next = next;
		prev.next = newNode;
		if (next != null) next.prev = newNode;

		size++;
	}



	@Override
	public boolean addAll(Collection<? extends E> collection) {
		boolean flag = true;
		for (E element: collection) {
			flag &= add(element);
		}
		return flag;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}

	@Override
	public boolean contains(Object obj) {
		return indexOf(obj) != -1;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		for (Object obj: collection) {
			if (!contains(obj)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public E get(int index) {
		Node node = getNode(index);
		return node.data;
	}

	/** Returns the node at the given index.
	 * @param index
	 * @return
	 */
	private Node getNode(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Node node = head;
		for (int i=0; i<index; i++) {
			node = node.next;
		}
		return node;
	}

	@Override
	public int indexOf(Object target) {
		// TODO: FILL THIS IN!
		Node node = head;
		int index = 0;

		// Traverse the list
		while (node != null) {
			// equals check
			if ((target == null && node.data == null) ||
					(target != null && target.equals(node.data))) {
				return index;  // found match
			}

			node = node.next;
			index++;
		}

		// Not found
		return -1;
	}

	/** Checks whether an element of the array is the target.
	 *
	 * Handles the special case that the target is null.
	 *
	 * @param target
	 * @param object
	 */
	private boolean equals(Object target, Object element) {
		if (target == null) {
			return element == null;
		} else {
			return target.equals(element);
		}
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		E[] array = (E[]) toArray();
		return Arrays.asList(array).iterator();
	}

	@Override
	public int lastIndexOf(Object target) {
		Node node = head;
		int index = -1;
		for (int i=0; i<size; i++) {
			if (equals(target, node.data)) {
				index = i;
			}
			node = node.next;
		}
		return index;
	}

	@Override
	public ListIterator<E> listIterator() {
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return null;
	}

	// IMPLEMENT - done
	@Override
	public boolean remove(Object obj) {
		// find first matching node
		Node cur = head;
		while (cur != null) {
			if ((obj == null && cur.data == null) ||
					(obj != null && obj.equals(cur.data))) {
				break;
			}
			cur = cur.next;
		}
		if (cur == null) return false; // not found

		Node prev = cur.prev;
		Node next = cur.next;

		// relink prev -> next
		if (prev == null) {           // removing head
			head = next;
		} else {
			prev.next = next;
		}

		// relink next -> prev
		if (next == null) {           // removing tail
			tail = prev;
		} else {
			next.prev = prev;
		}

		size--;
		return true;
	}

	//  reverse
	public void reverse() {
		if (head == null || head.next == null) return; // nothing to reverse

		Node current = head;
		Node temp = null;

		// Swap next and prev for every node
		while (current != null) {
			temp = current.prev;
			current.prev = current.next;
			current.next = temp;
			current = current.prev;
		}

		// After swapping, temp is now pointing to the node before the new head
		if (temp != null) {
			head = temp.prev;
		}

		// Swap head and tail references
		Node swap = head;
		head = tail;
		tail = swap;
	}

	//IMPLEMENT - done
	@Override
	public E remove(int index) {
		// Return null if index is invalid or list is empty
		if (index < 0 || index >= size || head == null) {
			return null;
		}

		Node cur = getNode(index);   // safely returns the node at that index
		if (cur == null) return null; // extra safety

		E element = cur.data;

		Node prev = cur.prev;
		Node next = cur.next;

		// unlink previous
		if (prev == null) {          // removing the first node
			head = next;
		} else {
			prev.next = next;
		}

		// unlink next
		if (next == null) {          // removing the last node
			tail = prev;
		} else {
			next.prev = prev;
		}

		size--;

		// if list becomes empty, reset both head and tail to null
		if (size == 0) {
			head = null;
			tail = null;
		}

		return element; // return removed item
	}


	@Override
	public boolean removeAll(Collection<?> collection) {
		boolean flag = true;
		for (Object obj: collection) {
			flag &= remove(obj);
		}
		return flag;
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E set(int index, E element) {
		Node node = getNode(index);
		E old = node.data;
		node.data = element;
		return old;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
			throw new IndexOutOfBoundsException();
		}
		// TODO: classify this and improve it.
		int i = fromIndex;
		MyDoubleLinkedList<E> list = new MyDoubleLinkedList<E>();

// start directly at fromIndex to avoid scanning from head every time
		Node node = getNode(fromIndex);

// copy elements in [fromIndex, toIndex) (exclusive end), standard subList behavior
		while (node != null && i < toIndex) {
			list.add(node.data);
			node = node.next;
			i++;
		}

		return list;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		int i = 0;
		for (Node node=head; node != null; node = node.next) {
			// System.out.println(node);
			array[i] = node.data;
			i++;
		}
		return array;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

}
