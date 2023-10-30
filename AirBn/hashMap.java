/**
 * 
  If you want to code a library that is similar to HashMap in Java, you need to create a class that implements the Map interface. 
  The Map interface defines the methods that a map data structure should have, such as put, get, remove, containsKey, containsValue, size, clear, keySet, values, and entrySet. You can use the HashMap class as a reference for how to implement these methods,
  but you need to write your own code and logic.

  One way to implement a map data structure is to use an array of linked lists. 
  Each element of the array is a linked list that stores the key-value pairs in nodes. 
  The array index is computed by applying a hash function to the key and modding it by the array length. 
  This way, keys that have the same hash value are stored in the same linked list. 
  To handle collisions, you need to traverse the linked list and compare the keys using the equals method. 
  To avoid too many collisions, you need to resize the array when the load factor exceeds a certain threshold.

Here is an example of how to code a library that is similar to HashMap in Java:
 */

// Import the LinkedList class
import java.util.LinkedList;

// Create a class called MyMap that implements the Map interface
public class MyMap<K,V> implements Map<K,V> {

  // Define some constants for the initial capacity and load factor
  private static final int INITIAL_CAPACITY = 16;
  private static final double LOAD_FACTOR = 0.75;

  // Create an array of LinkedLists to store the key-value pairs
  private LinkedList<Node<K,V>>[] table;

  // Create a variable to store the number of key-value pairs
  private int size;

  // Create a constructor that initializes the table and size
  public MyMap() {
    table = new LinkedList[INITIAL_CAPACITY];
    size = 0;
  }

  // Create a nested class called Node that represents a key-value pair
  private static class Node<K,V> {
    K key;
    V value;

    public Node(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  // Implement the put method that inserts or updates a key-value pair
  public V put(K key, V value) {
    // Check if the key is null
    if (key == null) {
      throw new NullPointerException("Key cannot be null");
    }

    // Compute the hash code and index of the key
    int hashCode = key.hashCode();
    int index = Math.abs(hashCode) % table.length;

    // Get the linked list at the index
    LinkedList<Node<K,V>> list = table[index];

    // If the list is null, create a new list and add it to the table
    if (list == null) {
      list = new LinkedList<Node<K,V>>();
      table[index] = list;
    }

    // Traverse the list and look for the node with the same key
    for (Node<K,V> node : list) {
      if (node.key.equals(key)) {
        // If found, update the value and return the old value
        V oldValue = node.value;
        node.value = value;
        return oldValue;
      }
    }

    // If not found, create a new node and add it to the list
    Node<K,V> newNode = new Node<K,V>(key, value);
    list.add(newNode);

    // Increment the size
    size++;

    // Check if the load factor is exceeded and resize if needed
    if (size > table.length * LOAD_FACTOR) {
      resize();
    }

    // Return null as there was no previous value
    return null;
  }

  // Implement the get method that returns the value associated with a key
  public V get(Object key) {
    // Check if the key is null
    if (key == null) {
      throw new NullPointerException("Key cannot be null");
    }

    // Compute the hash code and index of the key
    int hashCode = key.hashCode();
    int index = Math.abs(hashCode) % table.length;

    // Get the linked list at the index
    LinkedList<Node<K,V>> list = table[index];

    // If the list is not null, traverse it and look for the node with the same key
    if (list != null) {
      for (Node<K,V> node : list) {
        if (node.key.equals(key)) {
          // If found, return the value
          return node.value;
        }
      }
    }

    // If not found, return null
    return null;
  }

  // Implement the remove method that removes and returns the value associated with a key
  public V remove(Object key) {
     // Check if the key is null
     if (key == null) {
       throw new NullPointerException("Key cannot be null");
     }

     // Compute the hash code and index of the key
     int hashCode = key.hashCode();
     int index = Math.abs(hashCode) % table.length;

     // Get the linked list at the index
     LinkedList<Node<K,V>> list = table[index];

     // If the list is not null, traverse it and look for the node with the same key
     if (list != null) {
       for (int i = 0; i < list.size(); i++) {
         Node<K,V> node = list.get(i);
         if (node.key.equals(key)) {
           // If found, remove the node from the list and return the value
           list.remove(i);
           size--;
           return node.value;
         }
       }
     }

     // If not found, return null
     return null;
  }

  // Implement the containsKey method that returns true if the map contains a key
  public boolean containsKey(Object key) {
    // Check if the key is null
    if (key == null) {
      throw new NullPointerException("Key cannot be null");
    }

    // Compute the hash code and index of the key
    int hashCode = key.hashCode();
    int index = Math.abs(hashCode) % table.length;

    // Get the linked list at the index
    LinkedList<Node<K,V>> list = table[index];

    // If the list is not null, traverse it and look for the node with the same key
    if (list != null) {
      for (Node<K,V> node : list) {
        if (node.key.equals(key)) {
          // If found, return true
          return true;
        }
      }
    }

    // If not found, return false
    return false;
  }

  // Implement the containsValue method that returns true if the map contains a value
  public boolean containsValue(Object value) {
    // Traverse the table and look for a node with the same value
    for (int i = 0; i < table.length; i++) {
      LinkedList<Node<K,V>> list = table[i];
      if (list != null) {
        for (Node<K,V> node : list) {
          if (node.value.equals(value)) {
            // If found, return true
            return true;
          }
        }
      }
    }

    // If not found, return false
    return false;
  }

  // Implement the size method that returns the number of key-value pairs in the map
  public int size() {
    return size;
  }

  // Implement the clear method that removes all the key-value pairs from the map
  public void clear() {
    // Traverse the table and clear each linked list
    for (int i = 0; i < table.length; i++) {
      LinkedList<Node<K,V>> list = table[i];
      if (list != null) {
        list.clear();
      }
    }

    // Reset the size to zero
    size = 0;
  }

  // Implement the keySet method that returns a Set view of the keys in the map
  public Set<K> keySet() {
    // Create a HashSet to store the keys
    HashSet<K> keys = new HashSet<K>();

    // Traverse the table and add each key to the set
    for (int i = 0; i < table.length; i++) {
      LinkedList<Node<K,V>> list = table[i];
      if (list != null) {
        for (Node<K,V> node : list) {
          keys.add(node.key);
        }
      }
    }

    // Return the set of keys
    return keys;
  }

  // Implement the values method that returns a Collection view of the values in the map
  public Collection<V> values() {
    // Create an ArrayList to store the values
    ArrayList<V> values = new ArrayList<V>();

    // Traverse the table and add each value to the list
    for (int i = 0; i < table.length; i++) {
      LinkedList<Node<K,V>> list = table[i];
      if (list != null) {
        for (Node<K,V> node : list) {
          values.add(node.value);
        }
      }
    }

    // Return the list of values
    return values;
  }

  // Implement the entrySet method that returns a Set view of the key-value pairs in the map
  public Set<Map.Entry<K,V>> entrySet() {
     // Create a HashSet to store the entries
     HashSet<Map.Entry<K,V>> entries = new HashSet<Map.Entry<K,V>>();

     // Traverse the table and add each entry to the set
     for (int i = 0; i < table.length; i++) {
       LinkedList<Node<K,V>> list = table[i];
       if (list != null) {
         for (Node<K,V> node : list) {
           entries.add(node);
         }
       }
     }

     // Return the set of entries
     return entries;
   }
}

   // Create a helper method that resizes the