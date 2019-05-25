import java.util.ArrayList;

class HashMapper<K,V>{
    ArrayList<HashEntry<K,V>> buckets;
    LinkedStacker<V> undoStack = new LinkedStacker<>();
    
    int nBuckets;
    int size;

    public HashMapper(){
        size = 0;
        nBuckets = 20;
        buckets = new ArrayList<>();

        for(int i=0; i< nBuckets; i++){
            buckets.add(null);
        }
    }

    public int size() {
        return size;
    }

    public int bucketIndex(K key){
        int hash = key.hashCode();
        int idx = hash % nBuckets;
        return idx;
    }

    public void put(K key, V value){
        int idx = bucketIndex(key);
        HashEntry<K,V> head = buckets.get(idx);

        while (head != null){
            if(head.key.equals(key)){
                head.value = value;
                return;
            }
            head = head.next;
        }

        size++;
        head = buckets.get(idx);
        HashEntry<K,V> add = new HashEntry<K,V>(key, value);
        add.next = head;
        buckets.set(idx, add);

        resizeTable();
    }

    public V get(K key){
        int idx = bucketIndex(key);
        HashEntry<K,V> head = buckets.get(idx);
        while (head != null){
            if (head.key.equals(key)){
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

    public V remove(K key){
        int idx = bucketIndex(key);
        HashEntry<K,V> head = buckets.get(idx);
        HashEntry<K,V> prev = null;
        while(head != null){
            if (head.key.equals(key)){
                break;
            }
            prev = head;
            head = head.next;
        }

        if(head == null){
            return null;
        }
        // System.out.println(head.value);
        undoStack.push(head.value);
        size--;

        if (prev != null){
            prev.next = head.next;
        }else{
            buckets.set(idx, head.next);
        }
        return head.value;
    }

    public boolean undoRemove(){
        if (undoStack.isEmpty() == true) {
            System.out.println("There are no removals to undo!");
            return false;
        }
        else {
            V user = (V) undoStack.peek();
            User userNormal = (User) user;
            K username = (K) userNormal.getUsername();
            put(username, user);

            System.out.println("Removal of user " + username + " is undone!");

            undoStack.pop();
            return true;
        }
    }

    public void resizeTable(){
        if( (size*1.0)/nBuckets >= 0.6){
            ArrayList<HashEntry<K,V>> tempBuckets = buckets;
            buckets = new ArrayList<>();
            nBuckets = 2*nBuckets;
            size = 0;
            for (int i=0; i< nBuckets; i++){
                buckets.add(null);
            }
            for(HashEntry<K,V> entry : tempBuckets){
                while(entry != null){
                    put(entry.key, entry.value);
                    entry = entry.next;
                }
            }
        }
    }
}