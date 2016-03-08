
package syntaxtree;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import scanner.TokenType;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class SyntaxTreeNodeTest {
    
    public SyntaxTreeNodeTest() {
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
     * Test of indentedToString method, of class SyntaxTreeNode.
     */
    @Test
    public void testIndentedToString() {
        System.out.println("indentedToString");
        
        //Create instance of DeclarationsNode and add 3 VariableNodes to it
        DeclarationsNode decsNode = new DeclarationsNode();        
        decsNode.addVars(new VariableNode("dollars"));
        decsNode.addVars(new VariableNode("yen"));
        decsNode.addVars(new VariableNode("bitcoin"));      
        
        //Create instance of SubProgramDeclarationsNode 
        SubProgramDeclarationsNode subNode = new SubProgramDeclarationsNode();
        
         //Create instance of CompoundStatementNode
        CompoundStatementNode csNode = new CompoundStatementNode();       
        
        //Create instance of StatementNode
        StatementNode stateNode = new StatementNode();
        
        //Create instance of AssignmentNode and set left value and expression
        AssignmentStatementNode assignNode = new AssignmentStatementNode();
        assignNode.setLvalue(new VariableNode("dollars"));
        assignNode.setExpression(new ValueNode("1000000"));
        
        csNode.addStatements(stateNode);
        //System.out.println(assignNode.indentedToString(0));
        
        //Create ProgramNode and set Variables, Main, and Functions
        ProgramNode pNode = new ProgramNode("sample");
        pNode.setVariables(decsNode);
        pNode.setFunctions(subNode);
        pNode.setMain(csNode);
        
        assignNode.indentedToString(0);
        
       
        
              
        String expected = "Program: sample\n" +
                          "|-- DeclarationNode: \n" + 
                          "|-- --- VariableNode: dollars\n" +
                          "|-- --- VariableNode: yen\n" +
                          "|-- --- VariableNode: bitcoin\n" +
                          "|-- SubProgramDeclarationsNode: \n" +
                          "|-- CompoundStatementNode: \n" +
                          "|-- --- AssignmentNode: \n";
        
        String actual = pNode.indentedToString(0);
        System.out.println(actual);
        
        assertEquals( expected, actual);
        
    }

    public class SyntaxTreeNodeImpl extends SyntaxTreeNode {
    }
    
}
