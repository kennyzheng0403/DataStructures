import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     * <p>
     * This constructor should initialize an empty AVL.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     * <p>
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }

        for (T datum : data) {
            if (data.contains(null)) {
                throw new IllegalArgumentException("Data is null");
            }
            add(datum);
        }

    }

    /**
     * Adds the element to the tree.
     * <p>
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     * <p>
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * <p>
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }

        root = addHelper(root, data);

    }

    /**
     * @param node current node.
     * @param data data that needs to be added
     * @return root node
     */
    private AVLNode<T> addHelper(AVLNode<T> node, T data) {
        if (node == null) {
            size++;
            return new AVLNode<T>(data);
        } else {
            if (node.getData().equals(data)) {
                return node;
            } else if (data.compareTo(node.getData()) > 0) {
                node.setRight(addHelper(node.getRight(), data));
            } else {
                node.setLeft(addHelper(node.getLeft(), data));
            }
            return updateAndBalance(node);
        }
    }


    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     * <p>
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("no data");
        }
        boolean predeces = true;
        AVLNode<T> dummy = new AVLNode<T>(null);
        root = removeHelper(root, data, dummy, predeces);
        return dummy.getData();
    }


    /**
     * Removes data while maintaining order.
     * <p>
     * Removes based on three conditions
     * 1. If the node is a leaf node
     * 2. If the node has one child
     * 3. If the node has more than one child
     * In this case, another helper method removeSuccessor()
     * called
     *
     * @param current     the node used to find the node to remove
     * @param data        the data to remove
     * @param dummy       the node to store the removed node to return
     * @param predecessor wether to use it or not
     * @return the current node that updates root with the node removed
     */
    private AVLNode<T> removeHelper(AVLNode<T> current, T data, AVLNode<T> dummy, boolean predecessor) {
        if (current == null) {
            throw new java.util.NoSuchElementException("The data does not exist");
        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(removeHelper(current.getLeft(), data, dummy, predecessor));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(removeHelper(current.getRight(), data, dummy, predecessor));
        } else {
            dummy.setData(current.getData());
            size--;
            if (current.getLeft() == null) {
                return current.getRight();
            } else if (current.getRight() == null) {
                return current.getLeft();
            } else {
                AVLNode<T> dummy2 = new AVLNode<T>(null);
                if (predecessor) {
                    current.setLeft(removePredeces(current.getLeft(), dummy2));
                } else {
                    current.setRight(removeSuccessor(current.getRight(), dummy2));
                }
                current.setData(dummy2.getData());
            }
        }
        updateHeightandBF(current);
        return checkBalance(current);
    }

    /**
     * Removes the successor and adds it at the root
     * This is done to maintain the order of the BST
     * It also updates the balance after removing successor
     *
     * @param current the node that stores all the left and right subtrees
     * @param dummy   the node that stores the specific node being removed
     * @return the subtree after removing the specified node.
     */
    private AVLNode<T> removeSuccessor(AVLNode<T> current, AVLNode<T> dummy) {
        if (current.getLeft() == null) {
            dummy.setData(current.getData());
            return current.getRight();
        } else {
            current.setLeft(removeSuccessor(current.getLeft(), dummy));
        }
        updateHeightandBF(current);
        return checkBalance(current);
    }

    /**
     * Removes the predescssor and adds it at the root
     * This is done to maintain the order of the BST
     * It also updates the balance after removing successor
     *
     * @param current the node that stores all the left and right subtrees
     * @param dummy   the node that stores the specific node being removed
     * @return the subtree after removing the specified node.
     */
    private AVLNode<T> removePredeces(AVLNode<T> current, AVLNode<T> dummy) {
        if (current.getRight() == null) {
            dummy.setData(current.getData());
            return current.getLeft();
        } else {
            current.setRight(removePredeces(current.getRight(), dummy));
        }
        updateHeightandBF(current);
        return checkBalance(current);
    }

    /**
     * Updates the height and balance factor
     * of a specific node
     *
     * @param node the node to update the height and
     *             balance factor for
     */
    private void updateHeightandBF(AVLNode<T> node) {
        int leftHeight;
        int rightHeight;
        if (node.getLeft() == null) {
            leftHeight = -1;
        } else {
            leftHeight = node.getLeft().getHeight();
        }
        if (node.getRight() == null) {
            rightHeight = -1;
        } else {
            rightHeight = node.getRight().getHeight();
        }
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
        node.setBalanceFactor(leftHeight - rightHeight);
    }


    /**
     * Checks if the node is off balance
     * <p>
     * If it is not in balance then it balances it
     * depending on whether it is right or left heavy
     *
     * @param current the node to check for balance
     * @return the node that is balanced
     */
    private AVLNode<T> checkBalance(AVLNode<T> current) {
        if (Math.abs(current.getBalanceFactor()) > 1) {
            if (current.getBalanceFactor() > 0) {
                if (current.getLeft().getBalanceFactor() < 0) {
                    current.setLeft(leftRotate(current.getLeft()));
                }
                current = rightRotate(current);
            } else if (current.getBalanceFactor() < 0) {
                if (current.getRight().getBalanceFactor() > 0) {
                    current.setRight(rightRotate(current.getRight()));
                }
                current = leftRotate(current);
            }
        }
        return current;
    }

    /**
     * @param node current node.
     * @return node after balancing
     */
    private AVLNode<T> updateAndBalance(AVLNode<T> node) {
        update(node);
        int balanceFactor = node.getBalanceFactor();
        if (balanceFactor >= -1 && balanceFactor <= 1) {
            return node;
        } else if (balanceFactor == 2) {
            if (node.getLeft().getBalanceFactor() == 1
                    || node.getLeft().getBalanceFactor() == 0) {
                return rightRotate(node);
            } else {
                return leftRightRotate(node);
            }
        } else {
            if (node.getRight().getBalanceFactor() == -1
                    || node.getRight().getBalanceFactor() == 0) {
                return leftRotate(node);
            } else {
                return rightLeftRotate(node);
            }
        }
    }

    /**
     * @param a a node that has the balance factor of 2.
     * @return node after right rotation
     */
    private AVLNode<T> rightRotate(AVLNode<T> a) {
        AVLNode<T> b = a.getLeft();
        a.setLeft(b.getRight());
        b.setRight(a);
        update(a);
        update(b);
        return b;
    }

    /**
     * @param a a node that has the balance factor of -2.
     * @return node after left rotation
     */
    private AVLNode<T> leftRotate(AVLNode<T> a) {
        AVLNode<T> b = a.getRight();
        a.setRight(b.getLeft());
        b.setLeft(a);
        update(a);
        update(b);
        return b;
    }

    /**
     * @param a a node that has the balance factor of -2.
     * @return node after the right-left rotation
     */
    private AVLNode<T> rightLeftRotate(AVLNode<T> a) {
        a.setRight(rightRotate(a.getRight()));
        return leftRotate(a);
    }

    /**
     * @param a a node that has the balance factor of 2.
     * @return node after the left-right rotation
     */
    private AVLNode<T> leftRightRotate(AVLNode<T> a) {
        a.setLeft(leftRotate(a.getLeft()));
        return rightRotate(a);
    }

    /**
     * @param node current node
     */
    private void update(AVLNode<T> node) {
        int left = 0;
        int right = 0;
        if (node.getLeft() == null) {
            left = -1;
        } else {
            left = node.getLeft().getHeight();
        }

        if (node.getRight() == null) {
            right = -1;
        } else {
            right = node.getRight().getHeight();
        }

        int height = Math.max(left, right) + 1;
        node.setHeight(height);
        node.setBalanceFactor(left - right);
    }


    /**
     * Returns the element from the tree matching the given parameter.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        return getHelper(data, root);

    }

    /**
     * @param data data that we are trying to get.
     * @param node current node
     * @return data
     */
    private T getHelper(T data, AVLNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("The data is "
                    + "not in the tree");
        } else {
            if (node.getData().compareTo(data) == 0) {
                return node.getData();
            } else if (data.compareTo(node.getData()) > 0) {
                T value = getHelper(data, node.getRight());
                return value;
            } else {
                T value = getHelper(data, node.getLeft());
                return value;
            }
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }

        return containsHelper(data, root);
    }

    /**
     * @param data data we are trying to get.
     * @param node current node
     * @return true or false
     */
    private boolean containsHelper(T data, AVLNode<T> node) {
        if (node == null) {
            return false;
        } else {
            if (data.compareTo(node.getData()) == 0) {
                return true;
            } else if (data.compareTo(node.getData()) > 0) {
                boolean value = containsHelper(data, node.getRight());
                return value;
            } else {
                boolean value = containsHelper(data, node.getLeft());
                return value;
            }
        }
    }


    /**
     * Returns the height of the root of the tree.
     * <p>
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();

    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     */
    public void clear() {
        size = 0;
        root = null;

    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with
     * the deepest depth.
     * <p>
     * Should be recursive.
     * <p>
     * Must run in O(log n) for all cases.
     * <p>
     * Example
     * Tree:
     * 2
     * /    \
     * 0      3
     * \
     * 1
     * Max Deepest Node:
     * 1 because it is the deepest node
     * <p>
     * Example
     * Tree:
     * 2
     * /    \
     * 0      4
     * \    /
     * 1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        return maxDeepestNodeH(root);

    }

    private T maxDeepestNodeH(AVLNode<T> curr) {
        if (curr == null) {
            return null;
        } else if (curr.getBalanceFactor() > 0) {
            return maxDeepestNodeH(curr.getLeft());
        } else if (curr.getBalanceFactor() < 0) {
            return maxDeepestNodeH(curr.getRight());
        } else {
            if (curr.getHeight() == 0) {
                return curr.getData();
            }
            return maxDeepestNodeH(curr.getRight());
        }
    }


    /**
     * In BSTs, you learned about the concept of the successor: the
     * smallest data that is larger than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     * <p>
     * This method should retrieve (but not remove) the successor of the data
     * passed in. There are 2 cases to consider:
     * 1: The right subtree is non-empty. In this case, the successor is the
     * leftmost node of the right subtree.
     * 2: The right subtree is empty. In this case, the successor is the lowest
     * ancestor of the node whose left subtree contains the data.
     * <p>
     * The second case means the successor node will be one of the node(s) we
     * traversed left from to find data. Since the successor is the SMALLEST element
     * greater than data, the successor node is the lowest/last node
     * we traversed left from on the path to the data node.
     * <p>
     * This should NOT be used in the remove method.
     * <p>
     * Should be recursive.
     * <p>
     * Ex:
     * Given the following AVL composed of Integers
     * 76
     * /    \
     * 34      90
     * \    /
     * 40  81
     * successor(76) should return 81
     * successor(81) should return 90
     * successor(40) should return 76
     *
     * @param data the data to find the successor of
     * @return the successor of data. If there is no larger data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T successor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        return successorHelper(root, data);
    }

    /**
     * @param node current node
     * @param data data given
     * @return successor of the data
     */
    private T successorHelper(AVLNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("The data is not in the tree.");
        } else if (data.compareTo(node.getData()) > 0) {
            T value = successorHelper(node.getLeft(), data);
            if (value == null) {
                return node.getData();
            } else {
                return value;
            }
        } else if (data.compareTo(node.getData()) < 0) {
            T value = successorHelper(node.getRight(), data);
            return value;
        } else {
            if (node.getLeft() != null) {
                return getSuccessor(node.getRight());
            } else {
                return null;
            }
        }
    }

    /**
     * @param node current node
     * @return successor
     */
    private T getSuccessor(AVLNode<T> node) {
        if (node.getRight() == null) {
            return node.getData();
        } else {
            T value = getSuccessor(node.getRight());
            return value;
        }
    }


    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
