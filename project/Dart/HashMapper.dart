import 'LinkedStacker.dart';
import 'HashEntry.dart';
import 'User.dart';
// import 'package:crypto/crypto.dart';

class HashMapper<K,V>{
    var buckets = [];
    var undoStack = new LinkedStacker();
    
    int nBuckets;
    int size;

    HashMapper(){
        size = 0;
        nBuckets = 20;
        buckets = [];

        for(var i=0; i< nBuckets; i++){
            buckets.add(null);
        }
    }

    int sizer() {
        return size;
    }

    int bucketIndex(K key){
        int hash = key.hashCode;
        int idx = hash % nBuckets;
        return idx;
    }

    void put(K key, V value){
        int idx = bucketIndex(key);
        var head = buckets[idx];

        while (head != null){
            if(head.key == key){
                head.value = value;
                return;
            }
            head = head.next;
        }

        size++;
        head = buckets[idx];
        var add = new HashEntry<K,V>(key, value);
        add.next = head;
        buckets[idx] = add;

        resizeTable();
    }

    V get(K key){
        int idx = bucketIndex(key);
        var head = buckets[idx];
        while (head != null){
            if (head.key == key){
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

    V remove(K key){
        int idx = bucketIndex(key);
        var head = buckets[idx];
        var prev = null;
        while(head != null){
            if (head.key == key){
                break;
            }
            prev = head;
            head = head.next;
        }

        if(head == null){
            return null;
        }
        undoStack.push(head.value);
        size--;

        if (prev != null){
            prev.next = head.next;
        }else{
            buckets[idx] =  head.next;
        }
        return head.value;
    }

    bool undoRemove(){
        if (undoStack.isEmpty() == true) {
            print("There are no removals to undo!");
            return false;
        }
        else {
            var user = undoStack.peek();
            var userNormal = undoStack.peek() as User;
            var username = userNormal.getUsername() as K;
            put(username, user);

            // print("Removal of user " + (username as String) + " is undone!");

            undoStack.pop();
            return true;
        }
    }

    void resizeTable(){
        if( (size*1.0)/nBuckets >= 0.6){
            var tempBuckets = buckets;
            buckets = [];
            nBuckets = 2*nBuckets;
            size = 0;
            for (int i=0; i< nBuckets; i++){
                buckets.add(null);
            }
            for(var entry in tempBuckets){
                while(entry != null){
                    put(entry.key, entry.value);
                    entry = entry.next;
                }
            }
        }
    }
}