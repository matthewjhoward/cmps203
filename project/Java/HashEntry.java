class HashEntry<K, V>{
    K key;
    V value; 

    HashEntry<K,V> next;

    public HashEntry(K key, V value){
        this.key = key;
        this.value = value;
    }
}