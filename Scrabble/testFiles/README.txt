This directory has test files and expected output for these tests.
This data/output involves running your program with Linux input/output
redirection. Because of that, the .out files do not show the word
typed by the user (since that's in the input stream, not part of the
program output), just the prompt directly followed by the start of the
output for the word that was in the corresponding .in file.

* Two example commands to get this type of output (when run from your home directory):

   java WordFinder < testFiles/test1.in > test1.out
   java WordFinder testFiles/tinyDictionary.txt < testFiles/tiny.in > tiny.out

* The files here:

   tinyDictionary.txt -- a small dictionary you can use to test your program on
   tiny.in -- a small test data file you can use with the tiny dictionary
   tiny.out -- reference output for the tiny dictionary run with tiny.in
   tinyDictionaryUpper.txt -- the small dictionary in all upper-case form
   tinyUpper.in -- a small test data file you can use with the tinyDictionaryUpper.txt
   tinyUpper.out -- reference output for the tinyDictinoaryUpper.txt run with tinyUpper.in

  The other files are data and corresponding reference output for runs
  using the default dictionary (sowpods.txt)

   *.in -- input data can be used with input redirection
   *.out -- corresponding output data generated from our solution with 
            output redir (e.g. test1.out is the output when run with test1.in)
