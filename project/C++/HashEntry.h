#ifndef _HASH_ENTRY
#define _HASH_ENTRY

#include <string>

template<class ItemType>
class HashEntry {
    private:
        string key;
	    ItemType item;

    public:
        HashEntry()                            {this->key = ""; this-item = "";}                         				        				    
        HashEntry(string key, ItemType item)   {this->key = key; this->item = item;}				
        void setKey()	                       {this->key = key;}	
        void setItem()	                       {this->item = item;}
        string getKey()				           {return key;}	
        ItemType getItem()				       {return item;}
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
