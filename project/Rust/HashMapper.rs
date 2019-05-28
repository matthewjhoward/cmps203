use std::collections::hash_map::DefaultHasher;
use std::hash::{Hash, Hasher};

#[derive(Hash)]
pub struct HashMapper<K,V> {
    pub size: u64,
    pub nBuckets: u64,
    pub buckets: Vec,
    
    // adding null?
}

fn calculate_hash<T: Hash>(t: &T) -> u64 {
    let mut s = DefaultHasher::new();
    t.hash(&mut s);
    s.finish()
}

impl<K, V> HashMapper<K, V> {
    pub fn size(self) -> int {
        return self.size;
    }

    pub bucketIndex(self, key : K) -> int {
        let mut hash = calculate_hash(key); //most likely wrong
        let mut idx = hash % self.nBuckets;
        return idx;
    }

    pub put(self, key : K, value : V) -> () {
        let mut idx = self.bucketIndex(key);
        let mut head = self.buckets.get(idx);

        
    }
}