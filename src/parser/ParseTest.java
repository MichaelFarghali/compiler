
package parser;

/**
 * The ParseTest class checks that Parse class is working as expected 
 * @author Michael Farghali
 */
public class ParseTest {
    
    public static void main(String args[])
    {
        
        Parser parse = new Parser("equalsError.txt");
        
        parse.program();        
            
        
    }
    
    
}
