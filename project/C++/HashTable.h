#ifndef _HASH_TABLE
#define _HASH_TABLE

#include <list>
#include <array>

#include "HashInterface.h"
#include "HashEntry.h"

using namespace std;

template <class ItemType>
class HashTable : public HashInterface<ItemType> {
    private:
        const static int TABLE_SIZE = 256;
        array<list<HashEntry<string, ItemType>>, TABLE_SIZE>* ht;

    public:
        // Constructor
        HashTable();

        // Setter
        void insert(string key, ItemType item);

        // Getter
        ItemType get(string key);

        // Remover
        void remove(string key);

        // Hash function
        int hash(string key);

        // Deconstructor
        ~HashTable();
};

template <class ItemType>
HashTable<ItemType>::HashTable() {
    // TO-DO
}

template <class ItemType>
void HashTable<ItemType>::insert(string key, ItemType item) {
    int index = hash(key);

    // Below may need tweaking
    list<HashEntry<string, ItemType>>* indexBucket = ht[index]; // find the list at the index (probably wrong)
    HashEntry<string, ItemType>* he = new HashEntry<string, ItemType>(key, item);
    indexBucket.push_back(he); 
}

template <class ItemType>
ItemType HashTable<ItemType>::get(string key) {
    int index = hash(key);

    // Below may need tweaking
    list<HashEntry<string, ItemType>>* indexBucket = ht[index]; // find the list at the index (probably wrong)
    while(indexBucket->current != NULL) { // checks if current item in list is null (probably wrong)
        if(indexBucket->current->key == key) { // checks if current item's key is same as key you are getting (probably wrong)
            return indexBucket->current->item; // returns item at index (probably wrong)
        }
        else{
            indexBucket = indexBucket->next;
        }
    }

    cout << "Key " << key << " not found in Hash Table!" << endl;
    return 0; 
}

template <class ItemType>
void HashTable<ItemType>::remove(string key) {
    int index = hash(key);

    // Below may need tweaking
    list<HashEntry<string, ItemType>>* indexBucket = ht[index]; // find the list at the index (probably wrong)
    while(indexBucket->current != NULL) { // checks if current item in list is null (probably wrong)
        if(indexBucket->value->key == key) { // checks if current item's key is same as key you are getting (probably wrong)
            indexBucket->current->remove(); // removes item at index (probably wrong)
        }
        else{
            indexBucket = indexBucket->next;
        }
    }

    cout << "Key " << key << " not found in Hash Table!" << endl;
}

template <class ItemType>
int HashTable<ItemType>::hash(string key) {
    int currentVal = 0;
    int keySum = 0;

    for(int i = 0; i < key.size(); i++) {
        currentVal = key[i];
        keySum += currentVal;
    }

    int modValue = keySum % TABLE_SIZE;
    
    return modValue;
}

template <class ItemType>
HashTable<ItemType>::~HashTable() {
    // TO-DO
}

#endif