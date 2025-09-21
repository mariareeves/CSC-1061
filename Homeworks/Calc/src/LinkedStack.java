public class LinkedStack<T> implements Stack<T> {
    private static class Node<T> {
        T value;
        Node<T> next;
        Node(T v, Node<T> n) {
            value = v;
            next = n;
        }
    }

    private Node<T> head; // top of stack
    private int size;

    public LinkedStack() {
        head = null;
        size = 0;
    }

    @Override
    public void push(T item) {
        head = new Node<>(item, head);
        size++;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack underflow");
        }
        T val = head.value;
        head = head.next;
        size--;
        return val;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return head.value;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> curr = head;
        while (curr != null) {
            sb.append(curr.value);
            if (curr.next != null) sb.append(", ");
            curr = curr.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
