// Name: Ruihui Lu
// USC NetID: ruihuilu
// CS 455 PA5
// Fall 2018

// pa5list.cpp
// a program to test the linked list code necessary for a hash table chain

// You are not required to submit this program for pa5.

// We gave you this starter file for it so you don't have to figure
// out the #include stuff.  The code that's being tested will be in
// listFuncs.cpp, which uses the header file listFuncs.h

// The pa5 Makefile includes a rule that compiles these two modules
// into one executable.

#include <iostream>
#include <string>
#include <cassert>

using namespace std;

#include "listFuncs.h"

void add(ListType & list, string name, int value) {
    
    if (listAdd(list, name, value)) {
        cout << "add successfully" << endl;
        printList(list);
        cout << "list has " << getSize(list) << endl;
        cout << "------------------------" << endl;
    }
    else {
        cout << "add fails" << endl;
        cout << "list has " << getSize(list) << endl;
        cout << "------------------------" << endl;
    }
    
}

void remove(ListType & list, string name) {
    
    if (listRemove(list, name)) {
        cout << "remove successfully" << endl;
        printList(list);
        cout << "list has " << getSize(list) << endl;
        cout << "------------------------" << endl;
    }
    else {
        cout << "remove fails" << endl;
        cout << "list has " << getSize(list) << endl;
        cout << "------------------------" << endl;
    }
    
}

void update(ListType & list, string name, int newValue) {
    
    if (listUpdate(list, name, newValue)) {
        cout << "update successfully" << endl;
        printList(list);
        cout << "list has " << getSize(list) << endl;
        cout << "------------------------" << endl;
    }
    else {
        cout << "update fails" << endl;
        cout << "list has " << getSize(list) << endl;
        cout << "------------------------" << endl;
    }
    
}

void contain(ListType & list, string name) {
    
    if (listContain(list, name)) {
        cout << "contain" << endl;
        printList(list);
        cout << "list has " << getSize(list) << endl;
        cout << "------------------------" << endl;
    }
    else {
        cout << "contain fails" << endl;
        printList(list);
        cout << "list has " << getSize(list) << endl;
        cout << "------------------------" << endl;
    }
    
}


int main() {
    
    ListType thelist = NULL;
    char command;
    bool done = true;
    string name;
    int score;
    
    while (done) {
        
        cout << "Please choose command: add, remove, update, contain, quit:" << endl;
        
        cin >> command;
        
        switch (command){
            case 'q': done = false;
                break;
            case 'a': cout << "name: ";
                cin >> name;
                cout << "score: ";
                cin >> score;
                add(thelist, name, score);
                break;
            case 'r':cout << "name: ";
                cin >> name;
                remove(thelist, name);
                break;
            case 'u': cout << "name: ";
                cin >> name;
                cout << "score: ";
                cin >> score;
                update(thelist, name, score);
                break;
            case 'c':cout << "name: ";
                cin >> name;
                contain(thelist, name);
                break;
            default:break;
        }
        
    }
    
    
    return 0;
}


