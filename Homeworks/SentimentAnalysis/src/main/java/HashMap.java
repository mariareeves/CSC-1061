public class HashMap<K, V> {

    // node for each key/value in a bucket list
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;
        Node(K k, V v, Node<K, V> n) {
            key = k;
            value = v;
            next = n;
        }
    }

    // array of bucket heads
    private Node<K, V>[] buckets;
    // number of key/value pairs stored
    private int size;

    private static final double LOAD_FACTOR_THRESHOLD = 0.5;

    //start with a small bucket array
    @SuppressWarnings("unchecked")
    public HashMap() {
        buckets = (Node<K, V>[]) new Node[16];
        size = 0;
    }

    // keep track of how many pairs are in the map
    public int size() {
        return size;
    }

    // get a value by key or null if not found
    public V get(K key) {
        int idx = indexFor(key, buckets.length);
        Node<K, V> cur = buckets[idx];
        // walk the list looking for our key
        while (cur != null) {
            if (equalsKey(cur.key, key)) {
                return cur.value; // found it
            }
            cur = cur.next;
        }
        return null; // not found
    }

    // true if the key is present
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    // adding a key/value
    public void put(K key, V value) {
        // if adding one more would exceed 0.5, rehash first
        if ((size + 1) > (int) (buckets.length * LOAD_FACTOR_THRESHOLD)) {
            rehash();
        }

        int idx = indexFor(key, buckets.length);
        Node<K, V> cur = buckets[idx];

        // check if the key already exists -> update value
        while (cur != null) {
            if (equalsKey(cur.key, key)) {
                cur.value = value; // replace old value
                return;
            }
            cur = cur.next;
        }

        // key not found -> insert new node at head
        Node<K, V> newNode = new Node<>(key, value, buckets[idx]);
        buckets[idx] = newNode;
        size++; // one more pair stored
    }

    // then compute a non-negative bucket index
    private int indexFor(Object key, int mod) {
        int h = (key == null) ? 0 : key.hashCode();
        h = h & 0x7fffffff;
        return h % mod;
    }

    // check keys for equality
    private boolean equalsKey(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    // double the bucket array
    @SuppressWarnings("unchecked")
    private void rehash() {
        Node<K, V>[] old = buckets;
        buckets = (Node<K, V>[]) new Node[old.length * 2];

        // move each node from old table to new table
        for (Node<K, V> head : old) {
            Node<K, V> cur = head;
            while (cur != null) {
                Node<K, V> next = cur.next;
                int idx = indexFor(cur.key, buckets.length);
                cur.next = buckets[idx];    // insert at head of new bucket
                buckets[idx] = cur;
                cur = next;
            }
        }

    }
}
