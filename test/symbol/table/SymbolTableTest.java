/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package symbol.table;

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
public class SymbolTableTest {
    
    public SymbolTableTest() {
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
     * Test of addProgramName method, of class SymbolTable.
     */
    @Test
    public void testAddProgramName() {
        System.out.println("Testing addProgramName method");
        String name = "foo";
        SymbolTable instance = new SymbolTable();
        boolean expResult = true;
        boolean result = instance.addProgramName(name);
        assertEquals(expResult, result);
        // Test that you can't add a program ID that already exists in table
        expResult = false;
        result = instance.addProgramName(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of addFunctionName method, of class SymbolTable.
     */
    @Test
    public void testAddFunctionName() {
        System.out.println("Testing addFunctionName method");
        String name = "someFunction";
        SymbolTable instance = new SymbolTable();
        boolean expResult = true;
        boolean result = instance.addFunctionName(name);
        assertEquals(expResult, result);        
        // Test that you can't add a function ID that already exists in table
        expResult = false;
        result = instance.addFunctionName(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of addProcName method, of class SymbolTable.
     */
    @Test
    public void testAddProcName() {
        System.out.println("Testing addProcName method");
        String name = "proc";
        SymbolTable instance = new SymbolTable();
        boolean expResult = true;
        boolean result = instance.addProcName(name);
        assertEquals(expResult, result);        
       // Test that you can't add a procedure ID that already exists in table
        expResult = false;
        result = instance.addProcName(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of addVarName method, of class SymbolTable.
     */
    @Test
    public void testAddVarName() {
        System.out.println("Testing addVarName method");
        String name = "var";
        SymbolTable instance = new SymbolTable();
        boolean expResult = true;
        boolean result = instance.addVarName(name);
        assertEquals(expResult, result);
        // Test that you can't add a variable ID that already exists in table
        expResult = false;
        result = instance.addVarName(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of addArrayName method, of class SymbolTable.
     */
    @Test
    public void testAddArrayName() {
        System.out.println("Testing addArrayName method");
        String name = "array";
        SymbolTable instance = new SymbolTable();
        boolean expResult = true;
        boolean result = instance.addArrayName(name);
        assertEquals(expResult, result);
        // Test that you can't add an array ID that already exists in table
        expResult = false;
        result = instance.addArrayName(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of isProgName method, of class SymbolTable.
     */
    @Test
    public void testIsProgName() {
        System.out.println("Testing isProgName method");
        String name = "prog";
        String wrong = "wrong";
        SymbolTable instance = new SymbolTable();
        
        //Check that there is no program name "wrong"
        instance.addProgramName(name);
        boolean expResult = false;
        boolean result = instance.isProgName(wrong); 
        assertEquals(expResult, result);
        
        //Check that returns true for program name "prog"
        expResult = true;
        result = instance.isProgName(name); 
        assertEquals(expResult, result);
    }

    /**
     * Test of isFuncName method, of class SymbolTable.
     */
    @Test
    public void testIsFuncName() {
        System.out.println("Testing isFuncName method");
       String name = "fucn";
        String wrong = "wrong";
        SymbolTable instance = new SymbolTable();
        
        //Check that there is no function name "wrong"
        instance.addFunctionName(name);
        boolean expResult = false;
        boolean result = instance.isFuncName(wrong); 
        assertEquals(expResult, result);
        
        //Check that returns true for function name "func"
        expResult = true;
        result = instance.isFuncName(name); 
        assertEquals(expResult, result);
    }

    /**
     * Test of isProcName method, of class SymbolTable.
     */
    @Test
    public void testIsProcName() {
        System.out.println("Testing isProcName method");
        String name = "proc";
        String wrong = "wrong";
        SymbolTable instance = new SymbolTable();
        
        //Check that there is no procedure name "wrong"
        instance.addProcName(name);
        boolean expResult = false;
        boolean result = instance.isProcName(wrong); 
        assertEquals(expResult, result);
        
        //Check that returns true for procedure name "proc"
        expResult = true;
        result = instance.isProcName(name); 
        assertEquals(expResult, result);
    }

    /**
     * Test of isVarName method, of class SymbolTable.
     */
    @Test
    public void testIsVarName() {
        System.out.println("Testing isVarName method");
        String name = "var";
        String wrong = "wrong";
        SymbolTable instance = new SymbolTable();
        
        //Check that there is no variable name "wrong"
        instance.addVarName(name);
        boolean expResult = false;
        boolean result = instance.isVarName(wrong); 
        assertEquals(expResult, result);
        
        //Check returns true for variable name "var"
        expResult = true;
        result = instance.isVarName(name); 
        assertEquals(expResult, result);
    }

    /**
     * Test of isArrayName method, of class SymbolTable.
     */
    @Test
    public void testIsArrayName() {
        System.out.println("Testing isArrayName method");
        String name = "array";
        String wrong = "wrong";
        SymbolTable instance = new SymbolTable();
        //Check that there is no array name "wrong"
        instance.addArrayName(name);
        boolean expResult = false;
        boolean result = instance.isArrayName(wrong); 
        assertEquals(expResult, result);
        
        //Check that returns true for array name "array"
        expResult = true;
        result = instance.isArrayName(name); 
        assertEquals(expResult, result);
    }
    @Test
    public void testMyToString(){
        System.out.println("Testing myToString method");
        SymbolTable instance = new SymbolTable();
        String expected = String.format("%-20s %-20s %-15s %-15s %15s", "Name", 
                "Kind", "Array", "Start Index", "End Index") + "\n";
        String result = instance.myToString();
        //System.out.println(instance.myToString());
        assertEquals(expected, result);
        
              
    }
    
}
