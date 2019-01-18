// Name: Ruihui Lu
// USC NetID: ruihuilu
// CSCI 455 PA5
// Fall 2018

/*
 * grades.cpp
 * A program to test the Table class.
 * How to run it:
 *      grades [hashSize]
 *
 * the optional argument hashSize is the size of hash table to use.
 * if it's not given, the program uses default size (Table::HASH_SIZE)
 *
 */

#include "Table.h"

// cstdlib needed for call to atoi
#include <cstdlib>

using namespace std;

void execute(Table * & grades);
void add(Table * & grades, string name, int score);
void change(Table * & grades, string name, int newScore);
void del(Table * & grades, string name);
void find(Table * & grades, string name);
void print(Table * & grades);
void size(Table * & grades);
void status(Table * & grades);
void help();


int main(int argc, char * argv[]) {
    
    // gets the hash table size from the command line
    
    int hashSize = Table::HASH_SIZE;
    
    Table * grades;  // Table is dynamically allocated below, so we can call
    // different constructors depending on input from the user.
    
    if (argc > 1) {
        hashSize = atoi(argv[1]);  // atoi converts c-string to int
        
        if (hashSize < 1) {
            cout << "Command line argument (hashSize) must be a positive number"
            << endl;
            return 1;
        }
        
        grades = new Table(hashSize);
        
    }
    else {   // no command line args given -- use default table size
        grades = new Table();
    }
    
    
    grades->hashStats(cout);
    
    // add more code here
    // Reminder: use -> when calling Table methods, since grades is type Table*
    cout << "Please input \"help\" after the following \"cmd>\" if first time." << endl;
    execute(grades);
    
    return 0;
}

// <Added functions' definations here>
void execute(Table * & grades) {
    
    bool isDone = true;
    string command, name;
    int score, cm;
    
    while (isDone) {
        
        cout << "cmd> ";
        
        cin >> command;
        
        if (command == "insert") {cm = 1; cin >> name; cin >> score;}
        else if (command == "change") {cm = 2; cin >> name; cin >> score;}
        else if (command == "lookup") {cm = 3; cin >> name;}
        else if (command == "remove") {cm = 4; cin >> name;}
        else if (command == "print") {cm = 5;}
        else if (command == "size") {cm = 6;}
        else if (command == "stats") {cm = 7;}
        else if (command == "help") {cm = 8;}
        else if (command == "quit") {cm = 9;}
        else {cm = 10;}
        
        switch (cm) {
            case 1: add(grades, name, score); break;
            case 2: change(grades, name, score); break;
            case 3: find(grades, name); break;
            case 4: del(grades, name); break;
            case 5: print(grades); break;
            case 6: size(grades); break;
            case 7: status(grades); break;
            case 8: help(); break;
            case 9: isDone = false; break;
            default: cout << "ERROR: invalid command" << endl; break;
        }
        
    }
    
}

void add(Table * & grades, string name, int score) {
    
    if (grades -> insert(name, score)) {
        cout << "Add successfully." << endl;
    }
    else {
        cout << "Add failed: " << name << " has been existed." << endl;
        cout << "This student's score is " << score << endl;
    }
    
}

void change(Table * & grades, string name, int newScore) {
    
    int * value = grades -> lookup(name);
    int oldScore = 0;
    
    if (value == NULL){
        cout << "Change failed: " << name << " does not exist." << endl;
    }
    else {
        oldScore = *value;
        *value = newScore;
        
        cout << "Change successfully." << endl;
        cout << name << " exists and the old score is " << oldScore << endl;
    }
    
}

void del(Table * & grades, string name) {
    
    if (grades -> remove(name)) {
        cout << "Remove successfully." << endl;
    }
    else {
        cout << "Remove failed: " << name << " does not exist." << endl;
    }
    
}

void find(Table * & grades, string name) {
    
    int * value = grades -> lookup(name);
    
    if (value == NULL){
        cout << name << " does not exist." << endl;
    }
    else {
        cout << name << " exists and score is " << *value << endl;
    }
    
}

void print(Table * & grades) {
    
    grades -> printAll();
    
}

void size(Table * & grades) {
    cout << "The number of students is " << grades -> numEntries() << endl;
}

void status(Table * & grades){
    grades -> hashStats(cout);
}

void help(){
    cout << "-------------COMMAND SUMMARY-------------" << endl;
    cout << "-- insert name score (insert this student and score in the grade table.)" << endl;
    cout << "-- change name newscore (change the score for the student.)" << endl;
    cout << "-- lookup name (lookup the student.)" << endl;
    cout << "-- remove name (remove this student.)" << endl;
    cout << "-- print (print out all names and scores.)" << endl;
    cout << "-- size (print out the number of students.)" << endl;
    cout << "-- stats (print out statistics about the hash table at this point.)" << endl;
    cout << "-- help (print out a brief command summary.)" << endl;
    cout << "-- quit (exit.)" << endl;
    cout << "-------------------END-------------------" << endl;
}
