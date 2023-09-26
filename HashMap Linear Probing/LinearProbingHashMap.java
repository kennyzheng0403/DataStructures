import java.util.*;

/**
 * Your implementation of a LinearProbingHashMap.
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
public class LinearProbingHashMap<K, V> {

    /**
     * The initial capacity of the LinearProbingHashMap when created with the
     * default constructor.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the LinearProbingHashMap
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private LinearProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new LinearProbingHashMap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * <p>
     * Use constructor chaining.
     */
    public LinearProbingHashMap() {
        this(INITIAL_CAPACITY);

    }

    /**
     * Constructs a new LinearProbingHashMap.
     * <p>
     * The backing array should have an initial capacity of initialCapacity.
     * <p>
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public LinearProbingHashMap(int initialCapacity) {
        table = new LinearProbingMapEntry[initialCapacity];
        size = 0;

    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     * <p>
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     * <p>
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     * <p>
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     * <p>
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null.");
        }
        if (size + 1 / table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }
        int index = Math.abs(key.hashCode() % table.length);
        V temp = null;
        if (table[index] == null || table[index].isRemoved()) {
            table[index] = new LinearProbingMapEntry<>(key, value);
            size++;
        } else {
            int probes = 0;
            int finalInd = (index + probes) % table.length;
            while (probes < size && (table[finalInd] != null && !table[finalInd].isRemoved() &&
                    !table[finalInd].getKey().equals(key))) {
                probes++;
                finalInd++;
                finalInd = finalInd % table.length;
            }
            size++;
            if (table[finalInd].getKey().equals(key)) {
                temp = table[finalInd].getValue();
                table[finalInd] = new LinearProbingMapEntry<>(key, value);
            } else {
                table[finalInd] = new LinearProbingMapEntry<>(key, value);
                return null;
            }

        }
        return temp;
    }

    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null");
        }
        LinearProbingMapEntry<K, V> entry = getEntry(key);
        if (entry != null) {
            size--;
            entry.setRemoved(true);
            return entry.getValue();
        }
        throw new NoSuchElementException("Key is not in the map.");

    }

    /**
     * helper method to get the entry.
     *
     * @param key key associated with the hashmap
     * @return entry
     */
    private LinearProbingMapEntry<K, V> getEntry(K key) {
        int index = Math.abs(key.hashCode() % table.length);
        int probes = 0;
        while (probes < size && table[index] != null && !table[index].getKey().equals(key)) {
            if (!table[index].isRemoved()) {
                probes++;

            }
            index = (index + 1) % table.length;
        }
        if (table[index] != null && !table[index].isRemoved() && table[index].getKey().equals(key)) {
            return table[index];
        }
        return null;
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null.");
        }
        LinearProbingMapEntry<K, V> entry = getEntry(key);
        if (entry != null) {
            return entry.getValue();

        }
        throw new NoSuchElementException("Key is not in map.");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null.");
        }
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null || table[i].isRemoved()) {
                continue;
            }
            if (table[i].getKey().equals(key)) {
                return true;
            }
        }
        return false;

    }

    /**
     * Returns a Set view of the keys contained in this map.
     * <p>
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null || table[i].isRemoved()) {
                continue;
            } else {
                keys.add(table[i].getKey());
            }

        }

        return keys;

    }

    /**
     * Returns a List view of the values contained in this map.
     * <p>
     * Use java.util.ArrayList or java.util.LinkedList.
     * <p>
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> values = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null || table[i].isRemoved()) {
                continue;
            } else {
                values.add(table[i].getValue());
            }
        }

        return values;

    }

    /**
     * Resize the backing table to length.
     * <p>
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     * <p>
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     * You should NOT copy over removed elements to the resized backing table.
     * <p>
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     * <p>
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException
                    ("Length is less than the number of items in the hash map.");
        }

        LinearProbingMapEntry<K, V>[] tempTable =
                new LinearProbingMapEntry[length];
        int tableSize = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !(table[i].isRemoved())) {
                K key = table[i].getKey();
                int index = Math.abs(key.hashCode() % tempTable.length);

                for (int j = 0; j < tempTable.length; j++) {
                    int newIndex = (index + j) % tempTable.length;
                    if (tempTable[newIndex] == null) {
                        tempTable[newIndex] = table[i];
                        tableSize++;
                        break;
                    }
                }
            } else if (tableSize == size) {
                break;
            }
        }

        table = tempTable;
    }

    /**
     * Clears the map.
     * <p>
     * Resets the table to a new array of the INITIAL_CAPACITY and resets the
     * size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        table = new LinearProbingMapEntry[INITIAL_CAPACITY];
        size = 0;

    }

    /**
     * Returns the table of the map.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public LinearProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
