
package parser;
import syntaxtree.ProgramNode;
import java.io.IOException;

/**
 * The ParseTest class checks that Parse class is working as expected 
 * @author Michael Farghali
 */
public class ParseTest {
    
    public static void main(String args[]) throws IOException
    {
        String filename = args[0];
        Parser parse = new Parser(filename);  
        ProgramNode tree;
        
        tree = parse.program();    
        System.out.println(tree.indentedToString(0));    
       
            
       
    }
    
    
}
