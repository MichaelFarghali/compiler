package Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
    
    //// Instance Variables
    
    private TokenType type;
    
    private String lexeme;
    
    private PushbackReader input;
    
    private LookupTable lookup = new LookupTable();
    
    /////  Constructor
    
    
    public Scanner( File inputFile) {
        
        FileReader fr = null;
        try {
            fr = new FileReader(inputFile);
        }
        catch( FileNotFoundException fnfe) {
            System.out.println("Can't find file " + inputFile + ".");
            System.exit(1);
        }
        this.input = new PushbackReader( fr);
    }
    
    
    //// Methods
    
    public boolean nextToken() {
        int stateNumber = 0;
        String currentLexeme = "";
        int currentCharacter = 0;
        
        while( stateNumber < ERROR) {
            try {
                currentCharacter = input.read();
            }
            catch( IOException ioe) {
                // FIXME
            }
            switch( stateNumber) {
            // Begin reading in the file, exit if file empty (-1)
                case START:
                    if( currentCharacter == -1) {
                        this.lexeme = "";
                        this.type = null;
                        return( false);
                    }
                    else if(Character.isLetter(currentCharacter)) {
                        stateNumber = IN_ID_OR_KEYWORD;
                        currentLexeme += (char)currentCharacter;
                    }
                    else if ( Character.isWhitespace( currentCharacter)) {
                        
                    }
                    else if( currentCharacter == '+' ||
                             currentCharacter == '-') {
                        stateNumber = SYMBOL_COMPLETE;
                        currentLexeme += (char)currentCharacter;
                    }
                    else if( currentCharacter == '>') {
                        stateNumber = 2;
                        currentLexeme += (char)currentCharacter;
                    }
                    else if( currentCharacter == '{') {
                        stateNumber = 3;
                    }
                    else {
                        currentLexeme += (char)currentCharacter;
                        stateNumber = ERROR;
                    }
                    break;
                case IN_ID_OR_KEYWORD:
                    if( currentCharacter == -1) {
                        stateNumber = ID_COMPLETE;                        
                    }
                    else if( Character.isLetterOrDigit( currentCharacter)) {
                        currentLexeme += (char)currentCharacter;                        
                    }
                    else {
                        try {
                            input.unread( currentCharacter);
                        }
                        catch( IOException ioe){
                            // FIXME
                        }
                        stateNumber = ID_COMPLETE;
                    }
                    break;
                case 2:
                    if( currentCharacter == '=') {
                        stateNumber = SYMBOL_COMPLETE;
                        currentLexeme += (char)currentCharacter;                        
                    }
                    else {
                        try {
                            input.unread( currentCharacter);
                        }
                        catch( IOException ioe){
                            // FIXME
                        }
                        stateNumber = SHORT_SYMBOL_COMPLETE;
                    }
                    break;
                case 3:
                    if( currentCharacter == '{') {
                        currentLexeme += (char)currentCharacter;
                        stateNumber = ERROR;
                    }
                    else if(currentCharacter == '}') {
                        stateNumber = 0;
                    }
                    else {
                        // Stay in the comment state 3
                    }
                    break;
                    
            } // end switch 
        } // end while
        this.lexeme = currentLexeme;
        if( stateNumber == ERROR) {
            this.type = null;
            return( false);
        }
        else if( stateNumber == ID_COMPLETE) {
            this.type = lookup.get( this.lexeme);
            if( this.type == null) {
                this.type = TokenType.ID;
            }
            return( true);
        }
        else if(stateNumber == SYMBOL_COMPLETE) {
            this.type = lookup.get( this.lexeme);
            return( true);
        }
        else if( stateNumber == SHORT_SYMBOL_COMPLETE) {
            this.type = lookup.get( this.lexeme);
            return( true);
        }
       
        return( false);
    }
    
    public TokenType getToken() { return this.type;}
    public String getLexeme() { return this.lexeme;}
    
    
    
}
