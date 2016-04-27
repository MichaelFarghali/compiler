
package codegen;
import syntaxtree.*;
import scanner.TokenType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Michael Farghali
 */
public class CodeGeneration {
    
    private int currentTRegister;
    private ProgramNode program;
    private String outputFile; 
    private int labelNumber;
    
    
    public CodeGeneration(ProgramNode pNode){
        program = pNode;
        currentTRegister = 0;
        outputFile = program.getName() + ".asm";
        labelNumber = 0;
    }
    
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
        // Write the code to "program name".asm
        try {
            File file = new File(outputFile);
            FileWriter fwriter = new FileWriter(file);
            writer = new BufferedWriter(fwriter);
            
            writer.write(code);
        }
        catch (IOException e) {
            System.out.println("Failed writing to file.");
        }
        finally {
            if (writer != null)
                writer.close();
        }
    }
    
    public String writeDeclarations(ProgramNode pNode) {
        String code = "";
        DeclarationsNode decNode = pNode.getVariables();
        ArrayList<VariableNode> list = decNode.getVars();
        
        for(VariableNode v : list){
            code += v.getName() + ":\t.word\t0\n";
        }        
        return code;
    }
    
    public String writeCode(StatementNode node)
    {
        String code = "";
        
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

    public String writeCode(AssignmentStatementNode node){
        String code = "";
        String assignment = node.getLvalue().getName();
        String resultReg = "$t" + currentTRegister++;
        
        code += writeCode(node.getExpression(), resultReg );
        code += "sw\t" + resultReg + ", \t" + assignment + "\n";
        
        currentTRegister = 0;
        return code;
    }
     
    private String writeCode(IfStatementNode node){
	String code = "";
	ExpressionNode condition = node.getCondition();
	StatementNode thenStatement = node.getStatement();
	StatementNode elseStatement = node.getElseStatement();
	String conditionRegister = "$t" + currentTRegister++;
        String elseLabel = "elseif"+labelNumber;
        String endIfLabel = "endif"+labelNumber++;                
                
        code += writeCode(condition, conditionRegister);
	code += "beq\t" + conditionRegister + ",    $0,    " + elseLabel + "\n";
	code += writeCode(thenStatement);
	code += "j\t"+ endIfLabel + "\n";
	code += elseLabel + ":\n";
	code += writeCode(elseStatement);
        code += endIfLabel + ":\n";
                
	return code;
    }

    public String writeCode(WhileStatementNode node){
        String code = "";
        ExpressionNode whileCondition = node.getCondition();
        StatementNode whileStatement = node.getStatement();
        String whileStart = "whilestart" + labelNumber;
        String whileEnd = "whileend" + labelNumber++;
        String conditionRegister = "$t" + currentTRegister;
        
        code += whileStart + "\n";
        code += writeCode(whileCondition, conditionRegister);
        code += "beq\t" + conditionRegister + ",    $0,    " + whileEnd + "\n";
        code += writeCode(whileStatement);
        code += "j\t" + whileStart + "\n";
        code += whileEnd + "\n";

        return code;
    }
    
    public String writeCode(ExpressionNode node, String reg)
    {
        String code = "";
        
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
    private String writeCode(ValueNode node, String resultReg){
        String value = node.getAttribute();
        String code = "addi \t" + resultReg + ", \t$zero, \t" + value + "\n";
        
        return code;
    }
    
    private String writeCode(VariableNode node, String resultReg){
        String code;
        String var = node.getName();
        
        code = "lw \t" + resultReg + ",    " + var + "\n";
        return code;
    }       
    
}
