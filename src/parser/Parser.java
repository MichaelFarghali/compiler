
package parser;
import scanner.Scanner;
import scanner.TokenType;
import java.io.File;

/**
 * The Parser class will eventually process a source file and create a parse tree
 * or reject the source as invalid using recursive descent parsing.  
 * @Michael Farghali
 */
public class Parser {
    
    private Scanner scanner;  // The Scanner
    private TokenType currentToken;  // The TokenType variable 
    
    public Parser(String filename){
        // Create a file variable and an instance of scanner with specified file
        File input = new File(filename);        
        scanner = new Scanner(input);
        // Load in the first token as a lookahead token and assign to current
        scanner.nextToken();
        currentToken = scanner.getToken();        
    }
    
    public void match( TokenType expectedToken) {
        System.out.println("match " + expectedToken + " with current " + currentToken + ":" + scanner.getLexeme());
        if( currentToken == expectedToken) {
            boolean scanResult = scanner.nextToken();
            if( scanResult) {
                currentToken = scanner.getToken();
            }
            else {
                System.out.println("No Token Available");
                String lexeme = scanner.getLexeme();
                if( lexeme == null) {
                    System.out.println("End of file");
                }
                else {
                    System.out.println("Scanner barfed on " + lexeme);
                }
            }
            //System.out.println("   next token is now " + currentToken);
            //System.out.println("   next attri is now " + scanner.getAttribute());
        }
        else {
            error();  // We don't match!
        }
    }
    
    /**
     * Implements program -> program id ;
     *                       declarations
     *                       subprogram_declarations
     *                       compound_statement
     *                       .
     */
    public void program() {
        System.out.println("program");
        match( TokenType.PROGRAM);
        match( TokenType.ID);
        match( TokenType.SEMICOLON);
        declarations();
        subprogram_declarations();
        compound_statement();
        match( TokenType.PERIOD);
    }
    
    public void identifier_list()
    {
        
    }
    
    /**
     * Implements  part of declarations that is declarations -> lambda.
     */
    public void declarations() {
        System.out.println("declarations");
        if( currentToken == TokenType.VAR){
            identifier_list();
            type();
            declarations();
        }
        
        
    }
    
    /**
     * Implements type -> standard_type
     */
    public void type()
    {
        standard_type();
    }
    
    public void standard_type()
    {
        if( currentToken == TokenType.INTEGER)
            match( TokenType.INTEGER);
        else
            match( TokenType.REAL);
    }
    
    /**
     * Implements the part of subprogram_declarations that is 
     * subprogram_declarations -> lambda.
     */
   public void subprogram_declarations() {
        System.out.println("subprogram_declarations");
}


 /**
     * Implements
     *   compound_statement -> begin optional_statements end
     */
    public void compound_statement() {
        System.out.println("compound_statement");
        match( TokenType.BEGIN);
        optional_statements();
        match( TokenType.END);
    }
    
    /**
     * Implements the part of optional_statements that is 
     * optional_statements -> lambda.
     */
    public void optional_statements() {
        System.out.println("optional_statements");
        
    }
    
    /**
     * Handles an error.
     * Prints out the existence of an error and then exits.
     */
    public void error() {
        System.out.println("Error");
        System.exit( 1);
    }

}//end parser