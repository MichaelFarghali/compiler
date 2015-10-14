/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplescan;

import java.io.File;

/**
 *
 * @author steinmee
 */
public class ScanTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create a Scanner and point it at a file
        Scanner s = new Scanner( new File( "input.txt"));
        // Call nextToken() once
        boolean thereIsAToken = true;
        // While nextToken is true, print the tokens
        while( thereIsAToken) {
            thereIsAToken = s.nextToken();
            if( thereIsAToken) {
                System.out.println("Found " + s.getToken() + " with lexeme " + s.getLexeme());
            }
            else {
                System.out.println("Didn't find token with lexeme " + s.getLexeme());
            }
        }
        
    }
    
}
