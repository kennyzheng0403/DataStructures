import java.util.*;

/**
 * Your implementation of a BST.
 *
 * @author Kenny Zheng
 * @version 1.0
 * @userid kzheng73 (i.e. gburdell3)
 * @GTID 903529640 (i.e. 900000000)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Data or any element in data is null.");
        }
            size = 0;
            for (T temp : data) {
                add(temp);
            }

        }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null){
            throw new IllegalArgumentException("Data is Null.");
        }
        root = addH(data, root);

    }

    /**
     * helper method for add.
     * @param data data being added
     * @param curr curr node pointer
     * @return current node being added
     */
    private BSTNode<T> addH(T data, BSTNode<T> curr){
        if (curr == null){
            size++;
            return new BSTNode<>(data);
        }
        else if(data.compareTo(curr.getData()) < 0){
            curr.setLeft(addH(data, curr.getLeft()));

        }
        else if(data.compareTo(curr.getData()) > 0){
            curr.setRight(addH(data, curr.getRight()));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null){
            throw new IllegalArgumentException("Data is null.");
            }
        BSTNode<T> removed = new BSTNode<>(null);
        root = removeH(data, root, removed);
        return removed.getData();
        }

    /**
     * helper method for remove.
     * @param data data being removed
     * @param curr current node
     * @param removed node being removed
     * @return current node
     */
        private BSTNode<T> removeH(T data, BSTNode<T> curr, BSTNode<T> removed){
        if (curr == null){
            throw new NoSuchElementException("Data not found");
        }else{
            if(data.compareTo(curr.getData()) < 0){
                curr.setLeft(removeH(data, curr.getLeft(), removed));
            } else if(data.compareTo(curr.getData()) > 0){
                curr.setRight(removeH(data, curr.getRight(), removed));
            }else{
                removed.setData(curr.getData());
                size--;
                if(curr.getLeft() == null){
                    return curr.getRight();
                } else if(curr.getRight() == null){
                    return curr.getLeft();
                } else{
                    BSTNode<T> child = new BSTNode<>(null);
                    curr.setLeft(predecessor(curr.getLeft(), child));
                    curr.setData(child.getData());

                }
            }
        }
        return curr;
        }

    /**
     * helper method for remove using predecessor.
     * @param curr current node
     * @param child child node
     * @return current predecessor
     */
        private BSTNode<T> predecessor(BSTNode<T> curr, BSTNode<T> child){
        if (curr.getRight() == null){
            child.setData(curr.getData());
            return curr.getLeft();
        }
            curr.setRight(predecessor(curr.getRight(), child));
            return curr;
        }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null){
            throw new IllegalArgumentException("Data is null.");
        }
        return getH(data, root);

    }

    /**
     * helper for get method.
     * @param data data being gotten
     * @param curr current node
     * @return data trying to get
     */
    private T getH(T data, BSTNode<T> curr){
        if(curr == null){
            throw new NoSuchElementException("Data not found.");
        }
        if (data.equals(curr.getData())) {
            return curr.getData();
        } else if (data.compareTo(curr.getData()) < 0) {
            return getH(data, curr.getLeft());
        } else if (data.compareTo(curr.getData()) > 0) {
            return getH(data, curr.getRight());
        }
        return null;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        try {
            get(data);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;

    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        List<T> data = new ArrayList<>();
        helperPreorder(root, data);
        return data;

    }

    /**
     * helper method for the preorder method.
     *
     * @param node current node
     * @param list list that keeps the nodes
     */
    private void helperPreorder(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        list.add(node.getData());
        helperPreorder(node.getLeft(), list);
        helperPreorder(node.getRight(), list);
    }


    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        List<T> data = new ArrayList<>();
        helperInorder(root, data);
        return data;

    }

    /**
     * helper method for the inorder method.
     *
     * @param node current node
     * @param list list that keeps nodes
     */
    private void helperInorder(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        helperInorder(node.getLeft(), list);
        list.add(node.getData());
        helperInorder(node.getRight(), list);

    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        List<T> data = new ArrayList<>();
        helperPostorder(root, data);
        return data;

    }

    /**
     * helper method for the PostOrder method.
     *
     * @param node current node
     * @param list list that keeps the nodes
     */
    private void helperPostorder(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        helperPostorder(node.getLeft(), list);
        helperPostorder(node.getRight(), list);
        list.add(node.getData());

    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> data = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> temp = queue.poll();
            data.add(temp.getData());
            if (temp.getLeft() != null) {
                queue.add(temp.getLeft());
            }
            if (temp.getRight() != null) {
                queue.add(temp.getRight());
            }
        }
        return data;

    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return helperHeight(root);

    }

    /**
     * helper method for height.
     *
     * @param node current node
     * @return height
     */
    private int helperHeight(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return Math.max(helperHeight(node.getLeft()), helperHeight(node.getRight())) + 1;
        }


    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;

    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     * 
     * This must be done recursively.
     *
     * This list should contain the last node of each level.
     *
     * If the tree is empty, an empty list should be returned.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * Ex:
     * Given the following BST composed of Integers
     *               50
     *           /        \
     *         25         75
     *       /    \
     *      12    37
     *     /  \    \
     *   11   15   40
     *  /
     * 10
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 10] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     *
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        List<T> data = new ArrayList<>();
        getMaxH(data, root, 0);
        return data;
 
    }

    /**
     * helper for getMaxPerLevel.
     * @param list list being returned with final max values
     * @param curr current node
     * @param level level refers to the level of the BST
     */
    private void getMaxH(List<T> list, BSTNode<T> curr, int level){
        if (curr == null) {
            return;
        }
        if (level == list.size()) {
            list.add(curr.getData());
        } else {
            if (list.get(level).compareTo(curr.getData()) < 0) {
                list.set(level, curr.getData());
            } else {
                list.set(level, list.get(level));
            }
        }
        getMaxH(list, curr.getRight(),level + 1);
        getMaxH(list, curr.getLeft(),level + 1);
            //getMaxH(list, root.getLeft(),level + 1);
            //getMaxH(list, root.getRight(),level + 1);
        //getMaxH(list, root.getLeft(),level + 1);
        //getMaxH(list, root.getRight(),level + 1);

        //only add when list size is equal to data
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
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
