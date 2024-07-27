import java.util.LinkedList;


public class HashTable<K, V> {
    private LinkedList<HashNode<K, V>>[] buckets;
    private int numBuckets;
    private int size;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    public HashTable(int initialCapacity) {
        numBuckets = initialCapacity;
        buckets = new LinkedList[numBuckets];
        size = 0;

        for (int i = 0; i < numBuckets; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    private int hashFunction1(K key) {
        return Math.abs(key.hashCode()) % numBuckets;
    }

    private int hashFunction2(K key) {
        int hashCode = key.hashCode();
        return Math.abs((hashCode * 31 + 7) % numBuckets);
    }

    private void rehash() {
        LinkedList<HashNode<K, V>>[] oldBuckets = buckets;
        numBuckets *= 2;
        buckets = new LinkedList[numBuckets];
        size = 0;

        for (int i = 0; i < numBuckets; i++) {
            buckets[i] = new LinkedList<>();
        }

        for (LinkedList<HashNode<K, V>> bucket : oldBuckets) {
            for (HashNode<K, V> node : bucket) {
                put(node.key, node.value, 1); // Reinsert using hashFunction1
            }
        }
    }

    public void put(K key, V value, int hashFunc) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("La clave o el valor no puede ser null");
        }

        if ((1.0 * size) / numBuckets >= LOAD_FACTOR_THRESHOLD) {
            rehash();
        }

        int bucketIndex = (hashFunc == 1) ? hashFunction1(key) : hashFunction2(key);
        LinkedList<HashNode<K, V>> bucket = buckets[bucketIndex];

        for (HashNode<K, V> node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        size++;
        bucket.add(new HashNode<>(key, value));
    }

    public V get(K key, int hashFunc) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int bucketIndex = (hashFunc == 1) ? hashFunction1(key) : hashFunction2(key);
        LinkedList<HashNode<K, V>> bucket = buckets[bucketIndex];

        for (HashNode<K, V> node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }

        return null;
    }

    public V remove(K key, int hashFunc) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int bucketIndex = (hashFunc == 1) ? hashFunction1(key) : hashFunction2(key);
        LinkedList<HashNode<K, V>> bucket = buckets[bucketIndex];

        for (HashNode<K, V> node : bucket) {
            if (node.key.equals(key)) {
                V value = node.value;
                bucket.remove(node);
                size--;
                return value;
            }
        }

        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
