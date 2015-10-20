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
        Scanner instance = null;
        boolean expResult = false;
        boolean result = instance.nextToken();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getToken method, of class Scanner.
     */
    @Test
    public void testGetToken() {
        System.out.println("getToken");
        Scanner instance = null;
        TokenType expResult = null;
        TokenType result = instance.getToken();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLexeme method, of class Scanner.
     */
    @Test
    public void testGetLexeme() {
        System.out.println("getLexeme");
        Scanner instance = null;
        String expResult = "";
        String result = instance.getLexeme();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
