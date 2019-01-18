// Name: Ruihui Lu
// USC NetID: ruihuilu
// CSCI 455 PA5
// Fall 2018

// Table.cpp  Table class implementation


#include "Table.h"

#include <iostream>
#include <string>
#include <cassert>

using namespace std;


// listFuncs.h has the definition of Node and its methods.  -- when
// you complete it it will also have the function prototypes for your
// list functions.  With this #include, you can use Node type (and
// Node*, and ListType), and call those list functions from inside
// your Table methods, below.

#include "listFuncs.h"


//*************************************************************************


Table::Table() {
    hashSize = HASH_SIZE;
    chains = new ListType[hashSize]();
    numEntry = 0;
}


Table::Table(unsigned int hSize) {
    hashSize = hSize;
    chains = new ListType[hashSize]();
    numEntry = 0;
}


int * Table::lookup(const string &key) {
    
    unsigned int hCode = hashCode(key);
    
    if (listContain(chains[hCode], key)) {
        
        Node * curr = chains[hCode];
        
        for ( ; curr -> key != key ; curr = curr -> next) {
        }
        
        return & (curr -> value);
    }
    else{
        return NULL;
    }
}

bool Table::remove(const string &key) {
    
    unsigned int hCode = hashCode(key);
    
    if (listRemove(chains[hCode], key)) {
        numEntry --;
        return true;
    }
    else {
        return false;
    }
    
}

bool Table::insert(const string &key, int value) {
    
    unsigned int hCode = hashCode(key);
    
    if (listAdd(chains[hCode], key, value)) {
        numEntry ++;
        return true;
    }
    else {
        return false;
    }
}

int Table::numEntries() const {
    return numEntry;
}


void Table::printAll() const {
    
    for (int i = 0 ; i < hashSize ; i ++) {
        if (chains[i] != NULL) {
            printList(chains[i]);
        }
    }
    
}

void Table::hashStats(ostream &out) const {
    
    out << "    number of buckets: " << hashSize << endl;
    out << "    number of entries: " << numEntry << endl;
    out << "    number of non-empty buckets: " << numNonEmpty(chains) << endl;
    out << "    longest chain: " << longestChain(chains) << endl;
    
}


// add definitions for your private methods here
int Table::longestChain(ListType * data) const {
    
    int longestLen = 0;
    int size = 0;
    
    for (int i = 0 ; i < hashSize ; i ++) {
        if (data[i] != NULL) {
            size = getSize(data[i]);
            if (size > longestLen){
                longestLen = size;
            }
        }
    }
    
    return longestLen;
    
}


int Table::numNonEmpty(ListType * data) const {
    
    int numNonEm = 0;
    
    for (int i = 0 ; i < hashSize ; i ++) {
        if (data[i] != NULL) {
            numNonEm ++;
        }
    }
    
    return numNonEm;
    
}
