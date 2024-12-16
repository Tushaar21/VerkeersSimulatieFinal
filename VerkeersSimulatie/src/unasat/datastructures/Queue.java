package unasat.datastructures;


public class Queue<T> {
    // Node class to hold the data and the reference to the next node
    private static class Node<T> {
        private final T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head; // remove from the head
    private Node<T> tail; // add to the tail
    private int size;

    // Method to add an element to the queue
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = tail;
        }
        size++;
    }

    // Method to remove an element from the queue
    public T dequeue() {
        if (head == null) throw new IllegalStateException("Queue is empty");
        T data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return data;
    }

    // Method to get the front element of the queue without removing it
    public T peek() {
        if (head == null) throw new IllegalStateException("Queue is empty");
        return head.data;
    }

    // Method to check if the queue is empty
    public boolean isEmpty() {
        return head == null;
    }

    // Method to get the size of the queue
    public int size() {
        return size;
    }

    // Method to clear the queue
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
}
