import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     * <p>
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Out of bounds.");
        } else if (data == null) {
            throw new IllegalArgumentException("data is null.");
        } else if (size == 0 || head == null || tail == null) {
            head = new DoublyLinkedListNode<T>(data);
            tail = head;
            size++;
        } else {
            DoublyLinkedListNode<T> curr = head;
            if (index == 0) {
                DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data, null, head);
                head.setPrevious(newNode);
                head = newNode;
            } else if (index == size) {
                DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data, tail, null);
                tail.setNext(newNode);
                tail = newNode;
            } else {
                if (size - index < index) {
                    for (int i = 0; i < index - 1; i++) {
                        curr = curr.getNext();
                    }
                } else {
                    curr = tail;
                    for (int i = size; i > index; i--) {
                        curr = curr.getPrevious();
                    }
                }
                DoublyLinkedListNode<T> node = new DoublyLinkedListNode<T>(data, curr, curr.getNext());
                if (size > 1) {
                    curr.getNext().setPrevious(node);
                }
                curr.setNext(node);
            }
            size++;
        }
    }


    /**
     * Adds the element to the front of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        addAtIndex(0, data);
    }


    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        addAtIndex(size, data);

    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     * <p>
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Out of bounds.");
        } else if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            DoublyLinkedListNode<T> curr = head;
            int i = 0;
            while (i != index) {
                curr = curr.getNext();
                i++;
            }
            curr.getPrevious().setNext(curr.getNext());
            curr.getNext().setPrevious(curr.getPrevious());
            size--;
            return curr.getData();
        }

    }

    /**
     * Removes and returns the first element of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (head == null) {
            throw new NoSuchElementException("List is empty.");
        } else {
            DoublyLinkedListNode<T> dataRemoved = head;
            if (head == tail) {
                head = null;
                tail = null;
                size--;
                return dataRemoved.getData();
            } else {
                head = head.getNext();
                head.setPrevious(null);
            }
            size--;
            return dataRemoved.getData();
        }
    }

    /**
     * Removes and returns the last element of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (head == null) {
            throw new NoSuchElementException("List is empty.");
        } else {
            DoublyLinkedListNode<T> removed = tail;
            if (head == tail) {
                head = null;
                tail = null;
                size--;
                return removed.getData();
            } else {
                tail = tail.getPrevious();
                tail.setNext(null);
            }
            size--;
            return removed.getData();
        }

    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     * <p>
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        } else if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            DoublyLinkedListNode<T> node = head;
            int i = 0;
            while (i != index) {
                node = node.getNext();
                i++;
            }
            return node.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     * <p>
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (head == null);
    }


    /**
     * Clears the list.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     * <p>
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        DoublyLinkedListNode<T> curr = tail;
        int currIndex = size;
        T dataRemoved = null;
        boolean dataFound = false;
        while (curr.getPrevious() != null && dataFound == false) {
            if (curr.getData().equals(data)) {
                dataRemoved = removeAtIndex(currIndex - 1);
                dataFound = true;
            } else {
                curr = curr.getPrevious();
                currIndex--;
            }
        }
        if (dataFound == false) {
            throw new NoSuchElementException("Data not found.");
        } else {
            return dataRemoved;
        }

    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     * <p>
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        int i = 0;
        DoublyLinkedListNode<T> curr = head;
        Object[] arr = new Object[size];
        while (i != size) {
            arr[i] = curr.getData();
            curr = curr.getNext();
            i++;
        }
        return arr;

    }

    /**
     * Returns the head node of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}

