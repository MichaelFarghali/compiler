
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
     * Tests that a correct syntax tree is created by manually creating one
     */
    @Test
    public void testIndentedToString() {
        System.out.println("indentedToString");
        
        // Create 3 VariableNodes
        VariableNode dollar = new VariableNode("dollars");
        VariableNode yen = new VariableNode("yen");
        VariableNode bitcoin = new VariableNode("bitcoins");
        
        // Create an operation node for multiply and divide
        OperationNode mul = new OperationNode(TokenType.MULTIPLY);
        OperationNode div = new OperationNode(TokenType.DIVIDE);
        
        div.setLeft(dollar);
        div.setRight(new ValueNode("400"));
        
        mul.setLeft(dollar);
        mul.setRight(new ValueNode("102"));
        
        //Create instance of DeclarationsNode and add 3 VariableNodes to it
        DeclarationsNode decsNode = new DeclarationsNode();        
        decsNode.addVars(dollar);
        decsNode.addVars(yen);
        decsNode.addVars(bitcoin);      
        
        //Create instance of SubProgramDeclarationsNode 
        SubProgramDeclarationsNode subNode = new SubProgramDeclarationsNode();
        
         //Create instance of CompoundStatementNode
        CompoundStatementNode csNode = new CompoundStatementNode();       
        
        //Create instance of AssignmentNode and set left value and expression
        AssignmentStatementNode assignNode = new AssignmentStatementNode();
        assignNode.setLvalue(dollar);
        assignNode.setExpression(new ValueNode("1000000"));
        
        //Create instance of AssignmentNode and set left value and expression
         AssignmentStatementNode assignDivOP = new AssignmentStatementNode();
        assignDivOP.setLvalue(bitcoin);
        assignDivOP.setExpression(div);
        
        //Create instance of AssignmentNode and set left value and expression
         AssignmentStatementNode assignMulOP = new AssignmentStatementNode();
        assignMulOP.setLvalue(yen);
        assignMulOP.setExpression(mul);
        
        csNode.addStatements(assignNode);
        csNode.addStatements(assignDivOP);
        csNode.addStatements(assignMulOP);
       
        
        //Create ProgramNode and set Variables, Main, and Functions
        ProgramNode pNode = new ProgramNode("sample");
        pNode.setVariables(decsNode);
        pNode.setFunctions(subNode);
        pNode.setMain(csNode);
       
        
        String expected = "Program: sample\n" +
                          "|-- DeclarationNode: \n" + 
                          "|-- --- Variable: dollars\n" +
                          "|-- --- Variable: yen\n" +
                          "|-- --- Variable: bitcoins\n" +
                          "|-- SubProgramDeclarationsNode: \n" +
                          "|-- CompoundStatementNode: \n" +
                          "|-- --- AssignmentNode: \n" +
                          "|-- --- --- Variable: dollars\n" + 
                          "|-- --- --- Value: 1000000\n" + 
                          "|-- --- AssignmentNode: \n" +
                          "|-- --- --- Variable: bitcoins\n" +
                          "|-- --- --- Operation: DIVIDE\n" +
                          "|-- --- --- --- Variable: dollars\n" +
                          "|-- --- --- --- Value: 400\n" + 
                          "|-- --- AssignmentNode: \n" +
                          "|-- --- --- Variable: yen\n" +
                          "|-- --- --- Operation: MULTIPLY\n" +
                          "|-- --- --- --- Variable: dollars\n" +
                          "|-- --- --- --- Value: 102\n";
        
        String actual = pNode.indentedToString(0);
        System.out.println(actual);
        
        assertEquals( expected, actual);
        
    }

    public class SyntaxTreeNodeImpl extends SyntaxTreeNode {
    }
    
}
