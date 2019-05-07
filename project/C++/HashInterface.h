#ifndef _HASH_INTERFACE
#define _HASH_INTERFACE

#include <string>

template<class ItemType>
class HashInterface {
    public:
        virtual void insert(string key, ItemType item);
        virtual ItemType get(string key);
        virtual void remove(string key);
        virtual int hash(string key);
};
#endif