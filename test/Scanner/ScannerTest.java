/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author user
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
     * Test of nextToken method, of class Scanner.
     */
    @Test
    public void testNextToken() {
        System.out.println("nextToken");
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
        
        // Test that 10.123.456 is not valid
        expResult = false;
        result = numTest.nextToken();
        assertEquals(expResult, result); 
        System.out.println(numTest.getLexeme());
        /*
        // Test that 10e++5 is not valid
        // Need to change code in nextToken because it's currently valid
        expResult = true;
        result = numTest.nextToken();
        assertEquals(expResult, result); 
        System.out.println(numTest.getLexeme());
        
        // Test that 10e--5 is not valid
        // Need to change code. Currently works
        expResult = false;
        result = numTest.nextToken();
        assertEquals(expResult, result); 
        System.out.println(numTest.getLexeme());
        */
    }
    /**
     * Test of getToken method, of class Scanner.
     */
    @Test
    public void testGetToken() {
        System.out.println("getToken");
        Scanner instance = new Scanner(new File("input.txt"));
        TokenType expResult = TokenType.PROGRAM;
        System.out.println(instance.getToken());
        TokenType result = instance.getToken();
        
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getLexeme method, of class Scanner.
     */
    @Test
    public void testGetLexeme() {
        System.out.println("getLexeme");
        Scanner instance = new Scanner(new File("input.txt"));
        instance.nextToken();
        String expResult = "program";
        
        String result = instance.getLexeme();
        System.out.println(expResult);
        System.out.println(result);
        assertEquals(expResult, result);
        
    }
   
}
