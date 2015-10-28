package Scanner;


import java.io.File;

/**
 * The ScanTest class reads in a file with my Scanner class and prints to the 
 * screen the tokens found and what token type they match to in the LookUpTable
 
 * @author Michael Farghali
 */
public class ScanTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Create a Scanner and point it at a file
        Scanner s = new Scanner(new File("input.txt"));

        // Call nextToken() once to establish if there are any tokens
        boolean thereIsAToken = s.nextToken();

        // While nextToken is true, print the tokens
        while (thereIsAToken) {
            thereIsAToken = s.nextToken();
            if (thereIsAToken) {
                System.out.println("Found " + s.getToken() + " with lexeme " + s.getLexeme());
            } else {
                System.out.println("Didn't find token with lexeme " + s.getLexeme());
            }
            
        } //end while
    } //end main
} //end ScanTest
