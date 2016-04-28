
package codegen;
import syntaxtree.*;
import scanner.TokenType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class creates and writes assembly code to file based on reduced version 
 * of Pascal 
 * @author Michael Farghali
 */
public class CodeGeneration {
    
    private int currentTRegister;
    private ProgramNode program;
    private String outputFile; 
    private int labelNumber;
    
    /**
     * The CodeGeneration constructor initializes the global variables in this
     * class
     * @param pNode A program node representing syntax tree of source code
     */
    public CodeGeneration(ProgramNode pNode){
        program = pNode;
        currentTRegister = 0;
        outputFile = program.getName() + ".asm";
        labelNumber = 0;
    }
    
    /**
     * The writeFile() method without parameters represents the 
     * ProgramNode in the syntax tree. It builds the assembly source file and 
     * makes calls to it's child nodes for declarations and assembly instructions.
     * @throws IOException 
     */
    public void writeFile() throws IOException{
        String code;
        code = ".data\n";
        //Constant and variable declarations
        code += writeDeclarations(program);
        
        // Assembly instructions
        code += "\n \n.text\nmain: \n";
        CompoundStatementNode cs = program.getMain();
        for(StatementNode s : cs.getStatements()){
            code += writeCode(s);
        }
        
        // End of file
        code += "addi\t$v0,   $zero,    10\n";
        code += "syscall\n";
        
        //Write to file
        BufferedWriter writer = null;
        // Write the code to "yourProgramName".asm
        try {
            File file = new File(outputFile);
            FileWriter fwriter = new FileWriter(file);
            writer = new BufferedWriter(fwriter);
            
            writer.write(code);
        }
        catch (IOException e) {
            System.out.println("Failed writing to file: " + outputFile);
        }
        finally {
            if (writer != null)
                writer.close();
        }
    }
    
    /**
     * Writes code for the variable declarations in the .data section of 
     * assembly file
     * @param pNode The ProgramNode passed into the class constructor
     * @return The code for the declarations
     */
    public String writeDeclarations(ProgramNode pNode) {
        String code = "";
        DeclarationsNode decNode = pNode.getVariables();
        ArrayList<VariableNode> list = decNode.getVars();
        // For each VariableNode in declarations add it's name to the code
        for(VariableNode v : list){
            code += v.getName() + ":\t.word\t0\n";
        }        
        return code;
    }
    
    /**
     * Writes code for the statements in the program. 
     * It calls the matching child nodes AssignmentStatementNode, CompoundStatement
     * IfStatement, or WhileStatement and returns the corresponding code
     * @param node A child of abstract StatementNode class 
     * @return The code for assembly instructions in the Pascal programs body
     */
    public String writeCode(StatementNode node) {
        String code = "";
        // Check which kind of child node
        if(node instanceof AssignmentStatementNode){
            code += writeCode((AssignmentStatementNode)node);
        }
        if(node instanceof CompoundStatementNode){
            //Go through arraylist of statements and process each
            for(StatementNode s : ((CompoundStatementNode)node).getStatements())
            {
                code += writeCode(s);
            }
        }
        if(node instanceof IfStatementNode){
            code += writeCode((IfStatementNode)node);
        }
        if(node instanceof WhileStatementNode){
            code += writeCode((WhileStatementNode)node);
        }        
        return code;
    }

    /**
     * Gets the name of the left hand variable and calls one of ExpressionNodes
     * children's writeCode() method. It then sets the current $t register back 
     * to zero
     * @param node An AssignmentStatementNode
     * @return Code for an assignment statement
     */
    public String writeCode(AssignmentStatementNode node){
        String code = "";
        String assignment = node.getLvalue().getName();
        String resultReg = "$t" + currentTRegister++;
        // Call writeCode with the appropriate child node
        code += writeCode(node.getExpression(), resultReg );
        code += "sw\t" + resultReg + ",\t" + assignment + "\n";
        
        currentTRegister = 0;
        return code;
    }
    
    /**
     * Writes code for an if statement. It gets the OperationNode which is the
     * condition and the if then and else statements. Calls the writeCode for 
     * OperationNode which returns the necessary code for the relational op
     * @param node An IfStatementNode
     * @return The code which executes 'if then else' conditional block
     */
    private String writeCode(IfStatementNode node){
	String code = "";
	ExpressionNode condition = node.getCondition();
	StatementNode thenStatement = node.getStatement();
	StatementNode elseStatement = node.getElseStatement();
	String conditionRegister = "$t" + currentTRegister++;
        String elseLabel = "elseif"+labelNumber;
        String endIfLabel = "endif"+labelNumber++;                
        // If then else statement execution code        
        code += writeCode(condition, conditionRegister);
	code += "beq\t" + conditionRegister + ",    $0,    " + elseLabel + "\n";
	code += writeCode(thenStatement);
	code += "j\t"+ endIfLabel + "\n";
	code += elseLabel + ":\n";
	code += writeCode(elseStatement);
        code += endIfLabel + ":\n";
                
	return code;
    }

    /**
     * Writes code for a while statement. It gets the OperationNode which is the
     * condition and the statements to be executed if condition is true. Calls 
     * writeCode for OperationNode which returns the necessary code for relational op
     * @param node A WhileStatementNode
     * @return The code which executes a while statement
     */
    public String writeCode(WhileStatementNode node){
        String code = "";
        ExpressionNode whileCondition = node.getCondition();
        StatementNode whileStatement = node.getStatement();
        String whileStart = "whilestart" + labelNumber;
        String whileEnd = "whileend" + labelNumber++;
        String conditionRegister = "$t" + currentTRegister;
        // while statement execution
        code += whileStart + "\n";
        code += writeCode(whileCondition, conditionRegister);
        code += "beq\t" + conditionRegister + ",    $0,    " + whileEnd + "\n";
        code += writeCode(whileStatement);
        code += "j\t" + whileStart + "\n";
        code += whileEnd + "\n";

        return code;
    }
    
    /**
     * Writes code for an ExpressionNode. It calls the matching child node's 
     * writeCode method which then returns the code that executes the particular
     * expression.
     * @param node A child of abstract ExpressionNode 
     * @param reg The $t register where the results will be stored
     * @return Code that executes an expression
     */
    public String writeCode(ExpressionNode node, String reg)
    {
        String code = "";
        // Check which child of ExpressionNode abstract class 
        if(node instanceof OperationNode){
            code += writeCode((OperationNode)node, reg);
        }
        if(node instanceof VariableNode){
            code += writeCode((VariableNode)node, reg);
        }
        if(node instanceof ValueNode){
            code += writeCode((ValueNode)node, reg);
        }        
        return code;
    }
    
    /**
     * Writes code for an operation. It collects it's child nodes putting them 
     * into registers and then performing the operation on them storing the 
     * result into the specified result register 
     * @param node An OperationNode
     * @param resultReg The register where the result should be stored
     * @return Code that executes the operation
     */
    private String writeCode(OperationNode node, String resultReg){
        String code = "";
        
        // Set the left and right registers
        ExpressionNode left = node.getLeft();
        ExpressionNode right = node.getRight();
        String leftReg = resultReg;
        String rightReg = "$t" + currentTRegister++;
        code += writeCode(left, leftReg);
        code += writeCode(right, rightReg);
        
        // Set the approiate operation
        TokenType op = node.getOperation();
        if(op == TokenType.PLUS) { 
            code += "add    " + resultReg + ",   " + leftReg +  ",   " 
                    + rightReg + "\n";
        }
        if(op == TokenType.MINUS) {
            code += "sub    " + resultReg + ",   " + leftReg +
                    ",   " + rightReg + "\n";
        }        
        if(op == TokenType.MULTIPLY) {
            code += "mult   " + leftReg + ",   " + rightReg + "\n";
            code += "mflo   " + resultReg + "\n";
        }
        if(op == TokenType.DIVIDE || op == TokenType.DIV) {
            code += "div    " + leftReg + ",   " + rightReg + "\n";
            code += "mflo   " + resultReg + "\n";
        }
        if(op == TokenType.MOD){
            code += "div    " + leftReg + ",   " + rightReg + "\n";
            code += "mfhi   " + resultReg + "\n";
        }
        if(op == TokenType.LESS_THAN){
            code += "slt\t" + resultReg + ",\t" + leftReg + ",\t"+ rightReg+"\n";
	}
        if(op == TokenType.GREATER_THAN){
            code += "sgt\t" + resultReg + ",\t" + leftReg + ",\t" + rightReg+"\n";
        }
        if(op == TokenType.EQUALS){
            code += "seq\t" + resultReg + ",\t" + leftReg + ",\t" +rightReg+"\n";
        }
        if(op == TokenType.NOT_EQUAL){
            code += "sne\t" + resultReg + ",\t" + leftReg + ",\t" +rightReg+"\n";
        }
        if(op == TokenType.GREATER_THAN_EQUALS){
            code += "sge\t" + resultReg + ",\t" + leftReg + ",\t"+rightReg+"\n";
        }
        if(op == TokenType.LESS_THAN_EQUALS){
            code += "sle\t" + resultReg + ",\t" + leftReg + ",\t" + rightReg+"\n";
        }
        return code;
    }
    
    /**
     * 
     * @param node A ValueNode containing the value
     * @param resultReg The register to store the value
     * @return Code that executes the value node
     */
    private String writeCode(ValueNode node, String resultReg){
        String value = node.getAttribute();
        String code = "addi \t" + resultReg + ", \t$zero, \t" + value + "\n";
        
        return code;
    }
    
    /**
     * Writes code for a variable. It loads 32 word into the current $t register
     * @param node A VariableNode
     * @param resultReg The register to store the variable
     * @return Code that executes the loading of a variable into a register
     */
    private String writeCode(VariableNode node, String resultReg){
        String code;
        String var = node.getName();
        
        code = "lw \t" + resultReg + ",    " + var + "\n";
        return code;
    }       
    
}
