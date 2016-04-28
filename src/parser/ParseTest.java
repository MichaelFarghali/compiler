
package parser;
import syntaxtree.ProgramNode;
import codegen.CodeGeneration;
import java.io.IOException;


/**
 * The ParseTest class checks that Parse class is working as expected and calls
 * the CodeGeneration class so that the assembly source file can be written.
 * @author Michael Farghali
 */
public class ParseTest {
    
    public static void main(String args[]) throws IOException
    {
        String filename = args[0];
        Parser parse = new Parser(filename);  
        ProgramNode tree;        
        //Get the syntax tree
        tree = parse.program();    
        System.out.println(tree.indentedToString(0));    
        // Write assembly source file
        CodeGeneration codegen = new CodeGeneration(tree);
        codegen.writeFile();
            
       
    }
    
    
}
