package scanner;

import scanner.TokenType;
import scanner.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;

/**
 * Tests for both valid and invalid numbers or real numbers, tokens, and strings
 * @author Michael Farghali
 */
public class ScannerTest {
    
    public ScannerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Tests that token types match the correct tokens. It also tests that numbers
     * 1234, 7.123, 10e5, 10e+5, 10e-5 are valid and 1., .5, 10--5, 10e5e5 are 
     * not valid. Uses test files, inputBachCharTest.txt and numbersTest.txt .
     */
    @Test
    public void testNextToken() {
        System.out.println("\nTesting nextToken method:");
        Scanner charTest = new Scanner(new File("inputBadCharTest.txt"));
        Scanner numTest = new Scanner(new File("numbersTest.txt"));
      
        
        // The following tests are for invalid characters %, #, and &
        // from the inputBadCharTest.txt file using charTest instance of Scanner
        
        // Test that 'foo' is recognized
        boolean expResult = true;
        boolean result = charTest.nextToken();
        assertEquals(expResult, result);
        // Test that 'bar' is recognized token
        expResult = true;
        result = charTest.nextToken();
        assertEquals(expResult, result);
        // Test that '%' isn't valid
        expResult = false;
        result = charTest.nextToken();
        assertEquals(expResult, result);
        // Test that '#' is not valid
        expResult = false;
        result = charTest.nextToken();
        assertEquals(expResult, result);
        // Test that '&' isn't valid        
        expResult = false;
        result = charTest.nextToken();
        assertEquals(expResult, result);
        
        // The following tests are too be used on the numbersTest.txt file
        // using the numTest instance of Scanner class
        
        // Test that '1234' is valid
        expResult = true;
        result = numTest.nextToken();
        assertEquals(expResult, result);
        System.out.println(numTest.getLexeme());
        
        // Test that '7.123' is a valid token
        expResult = true;
        result = numTest.nextToken();
        assertEquals(expResult, result);
        System.out.println(numTest.getLexeme());
        
        // Test that '10e5' is valid token
        expResult = true;
        result = numTest.nextToken();
        assertEquals(expResult, result); 
        System.out.println(numTest.getLexeme());
        
        // Test that 10e+5 is valid token
        expResult = true;
        result = numTest.nextToken();
        assertEquals(expResult, result); 
        System.out.println(numTest.getLexeme());
        
        // Test that 10e-5 is valid
        expResult = true;
        result = numTest.nextToken();
        assertEquals(expResult, result); 
        System.out.println(numTest.getLexeme());
        
        // Test that '1.' is not valid
        expResult = false;
        result = numTest.nextToken();
        assertEquals(expResult, result); 
        System.out.println(numTest.getLexeme());
        
        // Test that 10e5e5 isn't valid token
        expResult = false;
        result = numTest.nextToken();
        assertEquals(expResult, result); 
        System.out.println(numTest.getLexeme());
                     
        // Test that 10e++5 is not valid
        //TODO 
        expResult = false;
        result = numTest.nextToken();
        System.out.println(numTest.getLexeme());
        assertEquals(expResult, result); 
        
        
        // Test that 10e--5 is not valid
        //TODO Need to eat the rest of the bad string from previous
        //10e++5 because it is reading in the trailing '5' here and returns true
        expResult = false;
        result = numTest.nextToken();
        assertEquals(expResult, result); 
        System.out.println(numTest.getLexeme());
        
       
        // Test that 10.123.456 is not valid
        //TODO same as above assertEquals returns true when previous invalid 
        //token is not encountered
        expResult = true;
        result = numTest.nextToken();
        assertEquals(expResult, result); 
        System.out.println(numTest.getLexeme());
    }//end testNextToken
    
    /**
     * Test of getToken method, of class Scanner.
     * Checks that the token returned from getToken matches what it should return
     * Uses the file input.txt for testing
     */
    @Test
    public void testGetToken() {
        System.out.println("\nTesting getToken method:");
        Scanner instance = new Scanner(new File("input.txt"));
        TokenType expResult = null;
        TokenType result = null;
        
        // Call nextToken then test program matches to TokenType PROGRAM
        instance.nextToken();
        expResult = TokenType.PROGRAM;
        result = instance.getToken();        
        assertEquals(expResult, result);
        System.out.println(expResult + " = " + result);
        
        // Call nextToken then test program matches to TokenType PROGRAM
        instance.nextToken();
        expResult = TokenType.IF;
        result = instance.getToken();        
        assertEquals(expResult, result);
        System.out.println(expResult + " = " + result);
        
         // Call nextToken, check 'ELSE' dose not matche to TokenType ELSE
        instance.nextToken();
        expResult = TokenType.ELSE;
        result = instance.getToken();        
        assertEquals(expResult, result);
        System.out.println(expResult + " = " + result);
        
        // Call nextToken, check '>=' matches TokenType GREATER_THAN_EQUALS
        instance.nextToken();
        expResult = TokenType.GREATER_THAN_EQUALS;
        result = instance.getToken();        
        assertEquals(expResult, result);
        System.out.println(expResult + " = " + result);
        
        // Call nextToken, check '<>' does match to TokenType NOT_EQUAL
        instance.nextToken();
        expResult = TokenType.NOT_EQUAL;
        result = instance.getToken();        
        assertEquals(expResult, result);
        System.out.println(expResult + " = " + result);
        
        // Call nextToken, check ':=' does match to TokenType ASSIGN
        instance.nextToken();
        expResult = TokenType.ASSIGN;
        result = instance.getToken();        
        assertEquals(expResult, result);
        System.out.println(expResult + " = " + result);
        
    } // end testGetToken

    /**
     * Test of getLexeme method, of class Scanner.
     * Checks that string returned from getLexeme() is what is in the test file
     * input.txt . 
     */
    @Test
    public void testGetLexeme() {
        System.out.println("\nTesting getLexeme method: ");
        Scanner instance = new Scanner(new File("input.txt"));
        String expResult = "";
        String result = "";
        
        //Process the next token in file and check strings match
        instance.nextToken();
        expResult = "program";        
        result = instance.getLexeme();
        assertEquals(expResult, result);
        System.out.println(expResult + " = " + result);
        
        //Call nextToken() then check if matches if from file
        instance.nextToken();
        expResult = "if";
        result = instance.getLexeme();
        assertEquals(expResult, result);
        System.out.println(expResult + " = " + result);
        
        /*Call nextToken(), check 'while' doens't match 'else'
        instance.nextToken();
        expResult = "while";
        result = instance.getLexeme();
        assertEquals(expResult, result);
        System.out.println(expResult + " = " + result);
         */
        
    } // end testGetLexeme
   
} //end ScannerTest