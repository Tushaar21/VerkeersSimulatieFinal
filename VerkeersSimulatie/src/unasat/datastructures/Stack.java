package unasat.datastructures;


public class Stack<T> {
    private Node<T> top;

    // Node class for linked list
    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public Stack() {
        top = null;
    }

    // Pushes an item onto the top of this stack
    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
    }

    // Removes and returns the top item from this stack
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        T item = top.data;
        top = top.next;
        return item;
    }

    // Returns the top item without removing it from the stack
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return top.data;
    }

    // Returns true if the stack is empty, false otherwise
    public boolean isEmpty() {
        return top == null;
    }

    // Removes all elements from the stack
    public void clear() {
        top = null;
    }

    // Prints all elements in the stack (for debugging purposes)
    public void printStack() {
        Node<T> current = top;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }
}