// Name: Ruihui Lu
// USC NetID: ruihuilu
// CSCI 455 PA5
// Fall 2018


#include <iostream>

#include <cassert>

#include "listFuncs.h"

using namespace std;

Node::Node(const string &theKey, int theValue) {
    key = theKey;
    value = theValue;
    next = NULL;
}

Node::Node(const string &theKey, int theValue, Node *n) {
    key = theKey;
    value = theValue;
    next = n;
}




//*************************************************************************
// put the function definitions for your list functions below

// Checks whether the list contains the target. If contains, return true; ow, return false.
bool listContain(ListType & list, string target) {
    
    Node * curr = list;
    
    while (curr != NULL) {
        
        if (curr -> key == target){
            return true;
        }
        
        curr = curr -> next;
    }
    
    return false;
    
}

// Adds one new Node with given string and value to the front of the list. If target exists,
// we do nothing and return false; otherwise, add it and return true.
bool listAdd(ListType & list, string target, int value){
    
    if (listContain(list, target)) {
        return false;
    }
    else {
        // Adds the new Node to the front of the list.
        list = new Node(target, value, list);
        return true;
    }
    
}

// Removes the node with the given string in the list. If target exists, we remove it and its
// corresponding value, and return true; ow, return false and do nothing.
bool listRemove(ListType & list, string target) {
    
    Node * curr = list;   // pointer which points to the current node ready to check.
    Node * old = NULL;    // pointer which points to the previous node of the current
    // node. At first, it is NULL.
    
    while (curr != NULL) {
        
        // If target exists, removes it and its corresponding value.
        if (curr -> key == target) {
            
            // If pointer old is NULL, the target is the first node of the list, and then remove it
            // directly; ow, we should remove the target, and connect the previous node of the target
            // with the next node of the target.
            if (old != NULL) {
                old -> next = curr -> next;
                curr = NULL;
            }
            else {
                list = list -> next;
            }
            
            return true;
        }
        
        // If the current node is not the target, keeps checking next node.
        old = curr;
        curr = curr -> next;
    }
    
    return false;
    
}

// Updates the value of given target. If target exists in the list, update its value and return
// true; ow, do nothing and return false.
bool listUpdate(ListType & list, string target, int newValue) {
    
    Node * curr = list;
    
    while (curr != NULL) {
        
        // If target exists, updates its value.
        if (curr -> key == target) {
            curr -> value = newValue;
            return true;
        }
        
        curr = curr -> next;
    }
    
    return false;
    
}

// Prints all nodes in the list.
void printList(ListType list) {
    
    // If list has no nodes, prints <empty>.
    if (list == NULL) {
        cout << "<empty>";
    }
    
    // If list has node(s), prints each node from the front to the end following
    // the specified form.
    // key_1 value_1
    // key_2 value_2
    // ...
    // key_last value_last
    Node * curr = list;
    
    while (curr != NULL) {
        
        cout << "    " << curr -> key << " " << curr -> value << endl;
        
        curr = curr -> next;
    }
    
}

// Gets the size of the list.
int getSize(ListType list) {
    
    int size = 0;
    
    for (Node * curr = list ; curr != NULL ; curr = curr -> next) {
        size ++;
    }
    
    return size;
}
