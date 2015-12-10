
package parser;

/**
 * The ParseTest class checks that Parse class is working as expected
 * @Michael Farghali
 */
public class ParseTest {
    
    public static void main(String args[])
    {
        
        Parser parse = new Parser("functionTest.txt");
        
        parse.program();
        
        //TODO Check for remaining tokens here
        
        
    }
    
    
}
