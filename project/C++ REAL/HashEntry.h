#ifndef _HASH_ENTRY
#define _HASH_ENTRY

#include <string>

template<class K, class V>
class HashEntry {
    private:
        K key;
	    V value;

        HashEntry<K, V>* next;

    public:                    				        				    
        HashEntry(K key, V value)   {this->key = key; this->value = value;}				
};

// template<class ItemType>
// HashEntry::HashEntry() {
//     this->key = NULL;
//     this->item = NULL;
// }

// template<class ItemType>
// HashEntry::HashEntry(string key, ItemType item) {
//     this->key = key;
//     this->item = item;
// }

// template<class ItemType>
// void HashEntry::setKey() {
//     this->key = key;
// }

// template<class ItemType>
// void HashEntry::setItem() {
//     this->item = item;
// }

// template<class ItemType>
// string HashEntry::getKey() {
//     return key;
// }

// template<class ItemType>
// ItemType HashEntry::getItem() {
//     return item;
// }

#endif
