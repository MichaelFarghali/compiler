package scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;


/**
 * The Scanner class reads through an input file and attempts to match every 
 * string of symbols, letters, and digits to a specific token type from 
 * TokenType class using the LookUpTable class
 * @author Michael Farghali
 */
public class Scanner {

     //// Class constants
    private final int START = 0;
    private final int IN_ID_OR_KEYWORD = 1;
    private final int ERROR = 100;
    private final int ID_COMPLETE = 101;
    private final int SYMBOL_COMPLETE = 102;
    private final int SHORT_SYMBOL_COMPLETE = 103;
    private final int INTEGER_COMPLETE = 104;
    private final int IN_GREATER_THAN_EQUALS = 2;
    private final int IN_LESS_THAN_EQUALS = 3; // Also handles the <> operator
    private final int IN_CURLY_BRACKETS = 4;
    private final int IN_ASSIGN_OP = 5;
    private final int IN_DIGIT = 6;
    private final int IN_DECIMAL = 7;
    private final int IN_SCI_NOTATION = 8;

    //// Instance Variables
    private TokenType type;
    private String lexeme;
    private PushbackReader input;
    private LookupTable lookup = new LookupTable();
    private int countLine;  // Used to count the number of lines

    /**
     * The Scanner constructor opens up the specified file. If input file not
     * found it exits with an error message. An instance of Java's built-in 
     * PushBackReader is initialized with the FileReader
     * @param inputFile The program file to be scanned of type File.
     */
    public Scanner(File inputFile) {

        FileReader fr = null;
        try {
            fr = new FileReader(inputFile);
        } catch (FileNotFoundException fnfe) {
            System.out.println("Can't find file " + inputFile + ".");
            System.exit(1);
        }
        this.input = new PushbackReader(fr);
        countLine = 1; // initialize the line count to 1
    }//end constructor    
    /**
     * Gets the the current value of countLine
     * @return int The number of lines the source file
     */
    public int getCount()
    {
        return countLine;
    }

    /**
     * Reads a single character using PushBackReader from input file and returns
     * it as an integer. It checks for IOException in try/catch block
     *
     * @return int The next character in input stream.
     */
    private int getNextChar() {
        int currentChar = 0;
        // Check for IOException
        try {
            currentChar = input.read();
        } catch (IOException ioe) {
            System.out.println("There was a problem reading from file");
            System.exit(1);
        }
        return currentChar;
    } // end getNextChar()

    /**
     * Unreads a single character using PushBackReader. Checks for IOException
     * in a try/catch block
     * @param aChar A single character to be pushed back into the file stream
     */
    private void pushBackChar(int aChar) {
        // Check for IOException
        try {
            input.unread(aChar);
        } catch (IOException ioe) {
            System.out.println("There was an error pushing " + aChar + 
                    " back into the file stream.");
            System.exit(1);
        }
    }

    /**
     * Reads in a string from a file one character at a
     * time. It then creates strings of words, numbers, or symbols and attempts 
     * to match the string to a token type using the LookUpTable class.
     *
     * @return True if string is a valid token in LookUpTable. False otherwise
     */
    public boolean nextToken() {
        int stateNumber = 0;
        String currentLexeme = "";
        int currentCharacter = 0;
        boolean digitRead = false;
        boolean symbolAlreadyFound = false;

        while (stateNumber < ERROR) {
            // Get a character
            currentCharacter = getNextChar();
            switch (stateNumber) {
                // Begin reading in the file
                case START:
                    // Exit if file is empty
                    if (currentCharacter == -1) {
                        this.lexeme = "";
                        this.type = null;
                        System.out.println("Scanner: End of file reached.");
                        System.exit(0);    
                    } 
                    // If a new line increment the line counter
                    else if (currentCharacter == '\n'){
                        countLine++;                        
                    }
                    // If reads in digit go to IN_DIGIT state
                    else if (Character.isDigit(currentCharacter)) {
                        stateNumber = IN_DIGIT;
                        currentLexeme += (char) currentCharacter;
                    } 
                    // If the lexeme is a letter go to ID/Keyword state
                    else if (Character.isLetter(currentCharacter)) {
                        stateNumber = IN_ID_OR_KEYWORD;
                        currentLexeme += (char) currentCharacter;
                    } 
                    // If whitespace, do nothing and stay in Start state
                    else if (Character.isWhitespace(currentCharacter)) {

                    } 
                    // If lexeme is any of the short symbols
                    // set state to SYMBOL_COMPLETE
                    else if (currentCharacter == '+'
                            || currentCharacter == '-'
                            || currentCharacter == '*'
                            || currentCharacter == '/'
                            || currentCharacter == '.'                            
                            || currentCharacter == ','
                            || currentCharacter == ';'
                            || currentCharacter == '['
                            || currentCharacter == ']'
                            || currentCharacter == '('
                            || currentCharacter == ')') {
                        stateNumber = SYMBOL_COMPLETE;
                        currentLexeme += (char) currentCharacter;
                    } 
                 
                    // If lexeme '>' set state to IN_GREATER_THAN_EQUALS state
                    else if (currentCharacter == '>') {
                        stateNumber = IN_GREATER_THAN_EQUALS;
                        currentLexeme += (char) currentCharacter;
                    } 
                    // If lexeme '<' set state to IN_LESS_THAN_EQUALS
                    else if (currentCharacter == '<') {
                        stateNumber = IN_LESS_THAN_EQUALS;
                        currentLexeme += (char) currentCharacter;
                    } 
                    // If lexeme ':' set state to IN_ASSIGN_OP
                    else if (currentCharacter == ':') {
                        stateNumber = IN_ASSIGN_OP;
                        currentLexeme += (char) currentCharacter;
                    } 
                    // If lexeme'{' set state IN_CURLY_BRACKETS check  for '}'
                    else if (currentCharacter == '{') {
                        stateNumber = IN_CURLY_BRACKETS;
                    }                    
                    // For anything else go to ERROR state
                    else {
                        currentLexeme += (char) currentCharacter;
                        stateNumber = ERROR;
                    }
                    break;
                // Read in additional characters or digits until a string has
                // been read in and then go to ID_COMPLETE state
                case IN_ID_OR_KEYWORD:
                    // If EOF is reached the ID is complete
                    if (currentCharacter == -1) {
                        pushBackChar(currentCharacter);
                        stateNumber = ID_COMPLETE;
                    } 
                    // Read in more letters or digits
                    else if (Character.isLetterOrDigit(currentCharacter)) {
                        currentLexeme += (char) currentCharacter;
                    }
                    // Pushback character when it is not number or digit
                    else {
                        pushBackChar(currentCharacter);                       
                        stateNumber = ID_COMPLETE;
                    }
                    break;
                // When '>' is read check if next symbol is '='
                // If '>=' go to SYMBOL_COMPLETE state. If not '>=' unread
                // current lexeme and go to short symbol complete state as '>'
                case IN_GREATER_THAN_EQUALS:
                    if (currentCharacter == '=') {
                        stateNumber = SYMBOL_COMPLETE;
                        currentLexeme += (char) currentCharacter;
                    } else {
                        pushBackChar(currentCharacter);
                        stateNumber = SHORT_SYMBOL_COMPLETE;
                    }
                    break;
                // When '<' is read check if next symbol is '=' or '>'
                // If '<=' or '<>' go to SYMBOL_COMPLETE state. If not unread
                // current lexeme and go to short symbol complete state as '<'
                case IN_LESS_THAN_EQUALS:
                    if (currentCharacter == '=') {
                        stateNumber = SYMBOL_COMPLETE;
                        currentLexeme += (char) currentCharacter;
                    } 
                    else if (currentCharacter == '>') {
                        stateNumber = SYMBOL_COMPLETE;
                        currentLexeme += (char) currentCharacter;
                    }
                    else {
                        pushBackChar(currentCharacter);                       
                        stateNumber = SHORT_SYMBOL_COMPLETE;
                    }
                    break;
                // If a ':' was read in check if next lexeme is an '='
                // If ':=' is read go to complete state. If any other lexeme
                // unread the character and go to short symbol complete state
                case IN_ASSIGN_OP:
                    if (currentCharacter == '=') {
                        stateNumber = SYMBOL_COMPLETE;
                        currentLexeme += (char) currentCharacter;
                    } 
                    else {
                        pushBackChar(currentCharacter);
                        stateNumber = SHORT_SYMBOL_COMPLETE;
                    }
                    break;
                // For comments in code using {}. If {} are not balanced go
                // to ERROR state. 
                case IN_CURLY_BRACKETS:
                    //Check that there aren't two open '{'
                    if (currentCharacter == '{') {
                        currentLexeme += (char) currentCharacter;
                        stateNumber = ERROR;
                    } else if (currentCharacter == '}') {
                        stateNumber = 0;
                    } else {
                        // Stay in the comment state 3
                    }
                    break;
                // Read in digits until int is complete or go to state for
                // float or scientific notation numbers
                case IN_DIGIT:
                    // If EOF is reached the number  is complete                    
                    if (currentCharacter == -1) {
                        stateNumber = INTEGER_COMPLETE;
                    }
                    // If '.' append and go to IN_DECIMAL state 
                    if (currentCharacter == '.'){
                        currentLexeme += (char) currentCharacter;
                        stateNumber = IN_DECIMAL;
                        break;
                    }
                    // Append 'e' append and go to IN_SCI_NOTATION state
                    if (currentCharacter == 'e'){
                        currentLexeme += (char) currentCharacter;
                        stateNumber = IN_SCI_NOTATION;
                        break;
                    }
                    // Check for ID names that illegaly start with number
                    else if (Character.isLetter(currentCharacter)){
                        currentLexeme += (char) currentCharacter;
                        stateNumber = ERROR;
                    }
                    // Keep reading in more digits for int
                    else if (Character.isDigit(currentCharacter)){
                        currentLexeme += (char) currentCharacter;
                    }
                    // For anything else push it back into file stream and 
                    // go to real or integer complete state
                    else {
                        pushBackChar(currentCharacter);
                        stateNumber = INTEGER_COMPLETE;
                        
                    }
                    break;
                case IN_DECIMAL:
                    // If EOF is reached the number  is complete     
                    if (currentCharacter == -1) {
                        stateNumber = INTEGER_COMPLETE;
                    }
                    // Check that there is at least one digit following the '.'
                    // using the digitRead flag
                    if (!Character.isDigit((char)currentCharacter) && 
                            !digitRead){
                        currentLexeme += (char) currentCharacter;
                        stateNumber = ERROR;            
                    }
                    // Keep reading in digits and set digitRead to true
                    else if (Character.isDigit((char)currentCharacter)){
                        currentLexeme += (char) currentCharacter;
                        digitRead = true;
                    } // Check that there are no letters in number   
                    else if (Character.isLetter(currentCharacter)){
                        currentLexeme += (char) currentCharacter;
                        stateNumber = ERROR;
                    }// Push back last character and return digitRead to false
                    else {
                        pushBackChar(currentCharacter);
                        stateNumber = INTEGER_COMPLETE;
                        digitRead = false;                                           
                    }
                break;
                case IN_SCI_NOTATION:
                    // If EOF is reached the number  is complete     
                    if (currentCharacter == -1) {
                        stateNumber = INTEGER_COMPLETE;
                    }
                    // Check for '+' or '-' and that they haven't been found 
                    // already with symbolAlreadyFound flag
                    if ((currentCharacter == '+' ||
                        currentCharacter == '-') && !symbolAlreadyFound){
                        currentLexeme += (char) currentCharacter;
                        symbolAlreadyFound = true;   
                    }
                    // Check that there is at least one digit following the 'e'
                    // using the digitRead flag
                    else if (!Character.isDigit((char)currentCharacter) && 
                            !digitRead){
                        currentLexeme += (char) currentCharacter;
                        stateNumber = ERROR;            
                    }
                    // Keep reading in digits and set digitRead to true
                    else if (Character.isDigit((char)currentCharacter)){
                        currentLexeme += (char) currentCharacter;
                        digitRead = true;
                    } // Check that there are no letters in number   
                    else if (Character.isLetter(currentCharacter)){
                        currentLexeme += (char) currentCharacter;
                        stateNumber = ERROR;
                    }// Push back last character and return digitRead to false
                    else {
                        pushBackChar(currentCharacter);
                        stateNumber = INTEGER_COMPLETE;
                        digitRead = false;                                           
                    }
                    break;

            } // end switch 
        } // end while
        // Get the completed string and check the final state number to 
        // get the correct Token identifier
        this.lexeme = currentLexeme;
        if (stateNumber == ERROR) {
            this.type = null;
            //Eat any trailing digits or letters in error
            currentCharacter = getNextChar();
            while( Character.isDigit((char)currentCharacter) ||
                   Character.isLetter((char)currentCharacter)){
                this.lexeme += (char)currentCharacter;
                currentCharacter = getNextChar();
            }
            return (false);
        } else if (stateNumber == ID_COMPLETE) {
            this.type = lookup.get(this.lexeme);
            if (this.type == null) {
                this.type = TokenType.ID;
            }
            return (true);
        } else if (stateNumber == SYMBOL_COMPLETE) {
            this.type = lookup.get(this.lexeme);
            return (true);
        } else if (stateNumber == SHORT_SYMBOL_COMPLETE) {
            this.type = lookup.get(this.lexeme);
            return (true);
        }
        else if(stateNumber == INTEGER_COMPLETE){
            this.type = TokenType.NUM;
            return (true);
        }

        return (false);
    } //end nextToken

    /**
     * Gets the token type.
     * @return an object of type TokenType
     */
    public TokenType getToken() {
        return this.type;
    }

    /**
     * Returns the current string. 
     * @return the current string formed by one or more lexemes
     */
    public String getLexeme() {
        return this.lexeme;
    }

}