#ifndef _HASH_MAPPER
#define _HASH_MAPPER

#include <list>
#include <array>
#include <bits/stdc++.h>

#include "User.h"
// #include "HashInterface.h"
#include "LinkedStacker.h"
#include "HashEntry.h"

using namespace std;

template <class K, class V>
class HashMapper {
    private:
        vector<HashEntry<K,V>>* buckets;
        // LinkedStacker<V>* undoStack = new LinkedStacker<>(); //could still be this one, or either of them
        LinkedStacker<V>* undoStack;

        int nBuckets;
        int size;

    public:
        // Constructor
        HashMapper();

        int Size(); //had to capitalize because it conflicted witht the vaiable called size

        int bucketIndex(K key);

        void put(K key, V value);

        V get(K key);

        V remove(K key);

        bool undoRemove();

        void resizeTable();

        // Deconstructor
        ~HashMapper();
};

template<class K, class V>
HashMapper<K, V>::HashMapper(){
    size = 0;
    nBuckets = 20;
    
    for (int i = 0; i < nBuckets; i++) {
        buckets->push_back(NULL);
    }
}

template<class K, class V>
int HashMapper<K, V>::Size(){
    return size;
}

template<class K, class V>
int HashMapper<K, V>::bucketIndex(K key){
    int hash = key.hashCode();
    int idx = hash % nBuckets;
    return idx;
}

template<class K, class V>
void HashMapper<K, V>::put(K key, V value){
    int idx = bucketIndex(key);
    HashEntry<K, V>* head = buckets->at(idx);

    while (head != NULL) {
        if (head->key == key) {
            head->value = value;
            return;
        }
        head = head->next;  
    }

    size++;
    head = buckets->at(idx);
    HashEntry<K,V>* add;
    add->next = head;
    buckets->at(idx) = add; // may not work -> use replace instead

    resizeTable();
}

template<class K, class V>
V HashMapper<K, V>::get(K key){
    int idx = bucketIndex(key);
    HashEntry<K,V>* head = buckets->at(idx);
    while (head != NULL) {
        if (head->key == key) {
            return head->value;
        }
        head = head->next;
    }
    return NULL;
}

template<class K, class V>
V HashMapper<K, V>::remove(K key){
    int idx = bucketIndex(key);
    HashEntry<K,V>* head = buckets->at(idx);
    HashEntry<K,V>* prev = NULL;
    while (head != NULL) {
        if (head->key == key) {
            break;
        }
        prev = head;
        head = head->next;
    }

    if (head == NULL) {
        return NULL;
    }
    undoStack->push(head->value);
    size--;

    if (prev != NULL) {
        prev->next = head->next;
    }
    else {
        buckets->at(idx) = head->next;
    }
    return head->value;
}

template<class K, class V>
bool HashMapper<K, V>::undoRemove(){
    if (undoStack->isEmpty() == true) {
        cout << "There are no removals to undo!" << endl;
        return false;
    }
    else {
        // V user = (V) undoStack.peek();
        // User userNormal = (User) user;
        // K username = (K) userNormal.getUsername();
        // put(username, user);

        // cout << "Removal of user " << username << " is undone!" << endl;

        undoStack->pop();
        return true;
    }
}

template<class K, class V>
void HashMapper<K, V>::resizeTable(){
    if ((size * 1.0) / nBuckets >= 0.6) {
        vector<HashEntry<K,V>>* tempBuckets;
        tempBuckets = buckets;
        vector<HashEntry<K,V>>* emptyBuckets;
        buckets = emptyBuckets;
        nBuckets = 2 * nBuckets;
        size = 0;
        for (int i = 0; i < nBuckets; i++) {
            buckets->push_back(NULL);
        }
        for (HashEntry<K,V>* entry : *tempBuckets)  {//syntax could be wrong here for for loop (also do we need dereference?) -> entry needs to not be a pointer?
            while (entry != NULL) {
                put(entry->key, entry->value);
                entry = entry->next;
            }
        }
    }
}

template<class K, class V>
HashMapper<K, V>::~HashMapper() {
    // TO-DO
}

#endif