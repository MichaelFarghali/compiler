
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
    
    
}
