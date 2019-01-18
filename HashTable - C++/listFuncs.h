// Name: Ruihui Lu
// USC NetID: ruihuilu
// CSCI 455 PA5
// Fall 2018


//*************************************************************************
// Node class definition
// and declarations for functions on ListType

// Note: we don't need Node in Table.h
// because it's used by the Table class; not by any Table client code.

// Note2: it's good practice to not put "using" statement in header files.  Thus
// here, things from std libary appear as, for example, std::string

#ifndef LIST_FUNCS_H
#define LIST_FUNCS_H


struct Node {
    std::string key;
    int value;
    
    Node *next;
    
    Node(const std::string &theKey, int theValue);
    
    Node(const std::string &theKey, int theValue, Node *n);
};


typedef Node * ListType;

//*************************************************************************
//add function headers (aka, function prototypes) for your functions
//that operate on a list here (i.e., each includes a parameter of type
//ListType or ListType&).  No function definitions go in this file.


// Adds one new Node with given string and value to the front of the list. If target exists,
// we do nothing and return false; otherwise, add it and return true.
bool listAdd(ListType & list, std::string target, int value);

// Removes the node with the given string in the list. If target exists, we remove it and its
// corresponding value, and return true; ow, return false and do nothing.
bool listRemove(ListType & list, std::string target);

// Checks whether the list contains the target. If contains, return true; ow, return false.
bool listContain(ListType & list, std::string target);

// Updates the value of given target. If target exists in the list, update its value and return
// true; ow, do nothing and return false.
bool listUpdate(ListType & list, std::string target, int newValue);

// Prints all nodes in the list.
void printList(ListType list);

// Gets the size of the list.
int getSize(ListType list);


// keep the following line at the end of the file
#endif
