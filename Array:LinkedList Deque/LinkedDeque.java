import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedDeque.
 *
 * @author Kenny Zheng
 * @version 1.0
 * @userid kzheng73 (i.e. gburdell3)
 * @GTID 903529640 (i.e. 900000000)
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class LinkedDeque<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the front of the deque.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        } else if (head == null) {
            LinkedNode<T> node = new LinkedNode<T>(data);
            head = node;
            tail = node;
            size = 1;
        } else {
            LinkedNode<T> node = new LinkedNode<T>(data, null, head);
            head.setPrevious(node);
            head = node;
            size++;
        }
    }

    /**
     * Adds the element to the back of the deque.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        } else if (head == null) {
            LinkedNode<T> node = new LinkedNode<T>(data);
            head = node;
            tail = node;
            size = 1;
        } else {
            LinkedNode<T> node = new LinkedNode<T>(data, tail, null);
            tail.setNext(node);
            tail = node;
            size++;
        }

    }

    /**
     * Removes and returns the first element of the deque.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty.");
        } else {
            LinkedNode<T> remove = head;
            if (head == tail) {
                head = null;
                tail = null;
                size--;
                return remove.getData();
            }
            remove.getNext().setPrevious(null);
            head = remove.getNext();
            size--;
            return remove.getData();
        }
    }

    /**
     * Removes and returns the last element of the deque.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty.");
        } else {
            LinkedNode<T> remove = tail;
            if (head == tail) {
                head = null;
                tail = null;
                size--;
                return remove.getData();
            }
            remove.getPrevious().setNext(null);
            tail = remove.getPrevious();
            size--;
            return remove.getData();

        }


    }

    /**
     * Returns the first data of the deque without removing it.
     * <p>
     * Must be O(1).
     *
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty.");
        } else {
            return head.getData();
        }

    }

    /**
     * Returns the last data of the deque without removing it.
     * <p>
     * Must be O(1).
     *
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty.");
        } else {
            return tail.getData();
        }

    }

    /**
     * Returns the head node of the deque.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the deque.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the deque.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
