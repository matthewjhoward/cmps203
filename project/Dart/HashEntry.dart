class HashEntry<K, V>{
    K key;
    V value; 

    HashEntry<K,V> next;

    HashEntry(K key, V value){
        this.key = key;
        this.value = value;
    }
}