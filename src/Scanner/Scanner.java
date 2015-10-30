package Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

/**
 *
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
    private final int REAL_COMPLETE = 105;
    private final int IN_GREATER_THAN_EQUALS = 2;
    private final int IN_LESS_THAN_EQUALS = 3; // Also handles the <> operator
    private final int IN_CURLY_BRACKETS = 4;
    private final int IN_ASSIGN_OP = 5;
    private final int IN_DIGIT = 6;

    //// Instance Variables
    private TokenType type;
    private String lexeme;
    private PushbackReader input;
    private LookupTable lookup = new LookupTable();

    //Constructor    
    public Scanner(File inputFile) {

        FileReader fr = null;
        try {
            fr = new FileReader(inputFile);
        } catch (FileNotFoundException fnfe) {
            System.out.println("Can't find file " + inputFile + ".");
            System.exit(1);
        }
        this.input = new PushbackReader(fr);
    }//end constructor    

    /**
     * Reads a single character using PushBackReader from input file and returns
     * it as an integer. It checks for IOException in try/catch block
     *
     * @return int
     */
    public int getNextChar() {
        int currentChar = 0;

        try {
            currentChar = input.read();
        } catch (IOException ioe) {
            // FIXME
        }
        return currentChar;
    } // end getNextChar()

    /**
     * Unreads a single character using PushBackReader. Checks for IOException
     * in a try/catch block
     * @param aChar A single character to be pushed back into the file stream
     */
    public void pushBackChar(int aChar) {
        try {
            input.unread(aChar);
        } catch (IOException ioe) {
            // FIXME
        }
    }

    /**
     * The nextToken() method reads in a string from a file one character at a
     * time.
     *
     * @return True if string is a valid token in LookUpTable. False otherwise
     */
    public boolean nextToken() {
        int stateNumber = 0;
        String currentLexeme = "";
        int currentCharacter = 0;
        boolean realNumFound = false;
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
                        return (false);
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
                    // If lexeme is '+' or '-' set state to SYMBOL_COMPLETE
                    else if (currentCharacter == '+'
                            || currentCharacter == '-') {
                        stateNumber = SYMBOL_COMPLETE;
                        currentLexeme += (char) currentCharacter;
                    } 
                    // Check for =, *, or / SYMBOL_COMPLETE
                    else if (currentCharacter == '='
                            || currentCharacter == '*'
                            || currentCharacter == '/') {
                        stateNumber = SYMBOL_COMPLETE;
                        currentLexeme += (char) currentCharacter;
                    } 
                    // Check for '.' ',' ';'
                    else if (currentCharacter == '.'
                            || currentCharacter == ','
                            || currentCharacter == ';') {
                        stateNumber = SYMBOL_COMPLETE;
                        currentLexeme += (char) currentCharacter;
                    } 
                    // Check for parentheses and brackets
                    else if (currentCharacter == '('
                            || currentCharacter == ')'
                            || currentCharacter == '['
                            || currentCharacter == ']') {
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
                    // If lexeme is '{' set state to 3 to check  for '}'
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
                // Read in digits until ID is complete or an invalid number is 
                // found such as "1." "10E", "10E+", "123VariableName"
                case IN_DIGIT:
                    // If EOF is reached the number  is complete
                    
                    if (currentCharacter == -1) {
                        stateNumber = INTEGER_COMPLETE;
                    }
                    if (currentCharacter == '.' && symbolAlreadyFound){
                        stateNumber = ERROR;
                    }
                    if (currentCharacter == 'e' && symbolAlreadyFound){
                        stateNumber = ERROR;
                    }
                        
                    if (currentCharacter == '.'){
                        currentLexeme += (char) currentCharacter;
                        realNumFound = true;
                        symbolAlreadyFound = true;
                        currentCharacter = getNextChar();
                        if (!Character.isDigit(currentCharacter)){
                            stateNumber = ERROR;
                        }              
                        else {
                            currentLexeme += (char) currentCharacter;
                        }
                    }
                    //else if (currentCharacter == '.' )
                    if (currentCharacter == 'e'){
                        currentLexeme += (char) currentCharacter;
                        realNumFound = true;
                        symbolAlreadyFound = true;
                        currentCharacter = getNextChar();
                        if (currentCharacter == '+' ||
                            currentCharacter == '-'){
                            currentLexeme += (char) currentCharacter;
                            currentCharacter = getNextChar();
                            if (!Character.isDigit(currentCharacter)){
                                stateNumber = ERROR;
                            }
                            else {
                                currentLexeme += (char) currentCharacter;
                            }
                        }  
                        else if(Character.isDigit(currentCharacter))
                            currentLexeme += (char) currentCharacter;
                        else
                            stateNumber = ERROR;
                    }
                    else if (Character.isLetter(currentCharacter)){
                        stateNumber = ERROR;
                    }
                    else if (Character.isDigit(currentCharacter)){
                        currentLexeme += (char) currentCharacter;
                    }
                    
                    else {
                        pushBackChar(currentCharacter);
                        if (realNumFound){
                            stateNumber = REAL_COMPLETE;
                            realNumFound = false; }
                        else{
                            stateNumber = INTEGER_COMPLETE;}
                        
                    }
                    break;

            } // end switch 
        } // end while

        this.lexeme = currentLexeme;
        if (stateNumber == ERROR) {
            this.type = null;
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
            this.type = lookup.get("integer");
            return (true);
        }
        else if(stateNumber == REAL_COMPLETE){
            this.type = lookup.get("real");
            return (true);
        }

        return (false);
    } //end nextToken

    public TokenType getToken() {
        return this.type;
    }

    public String getLexeme() {
        return this.lexeme;
    }

}
