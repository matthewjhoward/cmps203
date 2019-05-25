#ifndef _HASH_TABLE
#define _HASH_TABLE

#include <list>
#include <array>

#include "User.h"
#include "HashInterface.h"
#include "HashEntry.h"

using namespace std;

template <class ItemType>
class HashTable : public HashInterface<ItemType> {
    private:
        const static int TABLE_SIZE = 256;
        array<list<HashEntry<ItemType>>*, TABLE_SIZE>* ht;

    public:
        // Constructor
        HashTable();

        // Setter
        void insert(string key, ItemType item);

        // Setter for undo
        void insert(User user);

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
    list<HashEntry<ItemType>>* indexBucket = ht->at(index); // find the list at the index (probably wrong)
    HashEntry<ItemType>* he = new HashEntry<ItemType>(key, item);
    indexBucket->push_back(he); 
}

template <class ItemType>
ItemType HashTable<ItemType>::get(string key) {
    int index = hash(key);

    // Below may need tweaking
    list<HashEntry<ItemType>>* indexBucket = ht->at(index); // find the list at the index (probably wrong)

    for (auto const& i : *indexBucket) {  // Probably will break if list is empty? Not sure actually
        if(i->key == key) { // checks if current item's key is same as key you are getting (probably wrong)
            return i->item; // returns item at index (probably wrong)
        }
    }

    // while(indexBucket->current != NULL) { // checks if current item in list is null (probably wrong)
    //     if(indexBucket->current->key == key) { // checks if current item's key is same as key you are getting (probably wrong)
    //         return indexBucket->current->item; // returns item at index (probably wrong)
    //     }
    //     else{
    //         indexBucket = indexBucket->next;
    //     }
    // }

    cout << "Key " << key << " not found in Hash Table!" << endl;
    return 0; 
}

template <class ItemType>
void HashTable<ItemType>::remove(string key) {
    int index = hash(key);

    // Below may need tweaking
    list<HashEntry<ItemType>>* indexBucket = ht->at(index); // find the list at the index (probably wrong)

    for (auto const& i : *indexBucket) {  // Probably will break if list is empty? Not sure actually
        if(i->key == key) { // checks if current item's key is same as key you are getting (probably wrong)
            indexBucket->erase(i); 
        }
    }

    // while(indexBucket->current != NULL) { // checks if current item in list is null (probably wrong)
    //     if(indexBucket->value->key == key) { // checks if current item's key is same as key you are getting (probably wrong)
    //         indexBucket->current->remove(); // removes item at index (probably wrong)
    //     }
    //     else{
    //         indexBucket = indexBucket->next;
    //     }
    // }

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