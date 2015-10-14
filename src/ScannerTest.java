/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplescan;

import java.io.File;

public class ScannerTest {
    
    public static void main(String[] args){
        
        Scanner s = new Scanner( new File( "TestScannerTest.txt"));
        
        boolean hasToken = s.nextToken();
        TokenType token = s.getToken();
        String lexeme = s.getLexeme();
        
        //Test to make sure these values are correct
        assert hasToken : "No token for >";
        assert token == TokenType.GREATER_THAN: "No token type for >";
        assert lexeme.equals(">");
        
        hasToken = s.nextToken();
        token = s.getToken();
        lexeme = s.getLexeme();
        
        assert hasToken : "No token for program";
        assert token == TokenType.GREATER_THAN: "No token type for program";
        assert lexeme.equals("program"): "No token for program";
        
        hasToken = s.nextToken();
        token = s.getToken();
        lexeme = s.getLexeme();
        
        assert hasToken : "No token for program";
        assert token == TokenType.ID: "No token type for program";
        assert lexeme.equals("account"): "No token for program";     
        
        
        
        //what happens when we hit the end of the file
        assert hasToken == false : "No token for program";
        assert token == TokenType.ID : "No token type for program";
        assert lexeme.equals("account") : "No token for program";
        
        
 
            
    }
    
}
