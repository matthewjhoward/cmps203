pub struct HashEntry<K, V> {
    pub key: K,
    pub value: V,
    pub next: HashEntry, //HashEntry<K,V>?
}