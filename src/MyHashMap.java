import java.util.HashMap;

public class MyHashMap<K, V> {
    private Entry<K, V>[] buckets;
    private static int ENTRY_CAPACITY = 10;
    private int size = 0;
    public int size() {
        return this.size;
    }
    public MyHashMap() {
        this.buckets = new Entry[ENTRY_CAPACITY];
    }
    public void clear() {
        this.buckets = new Entry[ENTRY_CAPACITY];
        size = 0;
    }

    public void put(K key, V value) {
        int hash = key.hashCode();
        int bucketNumber = hash % buckets.length;
        Entry<K, V> kvEntry = new Entry<>();
        kvEntry.setKey(key);
        kvEntry.setValue(value);
        if (buckets[bucketNumber] == null) {
            buckets[bucketNumber] = kvEntry;
        } else {
            kvEntry.setNextEntry(buckets[bucketNumber]);
            buckets[bucketNumber] = kvEntry;
//            Entry<K, V> currentEntry = buckets[bucketNumber];
//            while (currentEntry.getNextEntry() != null) {
//                currentEntry = currentEntry.getNextEntry();
//            }
        }
        size++;
    }
    public void remove(K key) {
        if (get(key) == null) return;
        int hash = key.hashCode();
        int bucketNumber = hash % buckets.length;
        Entry<K, V> currentEntry = buckets[bucketNumber];
        Entry<K, V> nextEntry = currentEntry.getNextEntry();
        for (int i=0; i<buckets.length; i++) {
            K prevKey = buckets[i].getKey();
            if (prevKey == key) {
                int prevhash = prevKey.hashCode();
                int prevNumber = prevhash % buckets.length;
                Entry<K, V> prevEntry = buckets[prevNumber];
                buckets[prevNumber].setNextEntry(nextEntry);
            }
        }
        buckets[bucketNumber] = null;
        size--;
    }
    public V get(K key) {
        int hash = key.hashCode();
        int bucketNumber = hash % buckets.length;
        Entry<K, V> currentEntry = buckets[bucketNumber];
        if (currentEntry == null) return null;
        while (currentEntry.getNextEntry() != null) {
            V v = checkEquals(currentEntry, key);
            if (v != null) return v;
            currentEntry = currentEntry.getNextEntry();
        }
        return checkEquals(currentEntry, key);
    }
    private V checkEquals(Entry<K, V> e, K key) {
        if (e.getKey().equals(key))
            return e.getValue();
        else
            return null;
    }
}
class Entry<K, V> {
    private K key;
    private V value;
    private Entry<K, V> nextEntry;
    public Entry<K, V> getNextEntry() {
        return nextEntry;
    }
    public void setNextEntry(Entry<K, V> nextEntry) {
        this.nextEntry = nextEntry;
    }
    public K getKey() {
        return key;
    }
    public void setKey(K key) {
        this.key = key;
    }
    public V getValue() {
        return value;
    }
    public void setValue(V value) {
        this.value = value;
    }
}

class MyHashMapTest {
    public static void main(String[] args) {
        MyHashMap<String, Integer> myHashMap = new MyHashMap();
        myHashMap.put("aaa1", 1);
        myHashMap.put("aaa2", 2);
        myHashMap.put("aaa3", 3);
        myHashMap.put("aaa4", 4);
        myHashMap.put("aaa5", 5);
        myHashMap.put("aaa6", 6);
        myHashMap.put("aaa7", 7);
        myHashMap.put("aaa8", 8);
        myHashMap.put("aaa9", 9);
        myHashMap.put("aaa10", 10);
        myHashMap.put("aaa11", 11);
        System.out.println("myHashMap.size = " + myHashMap.size());
        System.out.println("myHashMap.get(\"aaa3\") = "+ myHashMap.get("aaa3"));
        myHashMap.remove("aaa10");
        System.out.println("myHashMap.get(\"aaa11\") = "+ myHashMap.get("aaa11"));
        System.out.println("myHashMap.get(\"aaa10\") = "+ myHashMap.get("aaa10"));
        System.out.println("myHashMap.size = " + myHashMap.size());
        myHashMap.clear();
        System.out.println("myHashMap.size = " + myHashMap.size());
    }
}