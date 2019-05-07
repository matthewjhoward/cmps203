#ifndef _HASH_ENTRY
#define _HASH_ENTRY

#include <string>

template<class ItemType>
class HashEntry {
    private:
        string key;
	    ItemType item;

    public:
        HashEntry();								    
        HashEntry(string key, ItemType item);				
        void setKey();		
        void setItem();	
        string getKey();					
        ItemType getItem();				
};

HashEntry::HashEntry() {
    this->key = NULL;
    this->item = NULL;
}

HashEntry::HashEntry(string key, ItemType item) {
    this->key = key;
    this->item = item;
}

void HashEntry::setKey() {
    this->key = key;
}

void HashEntry::setItem() {
    this->item = item;
}

string HashEntry::getKey() {
    return key;
}

ItemType HashEntry::getItem() {
    return item;
}

#endif
