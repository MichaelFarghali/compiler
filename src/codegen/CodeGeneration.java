
package codegen;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import syntaxtree.*;
import java.util.ArrayList;

/**
 *
 * @author Michael Farghali
 */
public class CodeGeneration {
    
    private int currentTRegister;
    private ProgramNode program;
    private String outputFile; 
    
    
    public CodeGeneration(ProgramNode pNode){
        program = pNode;
        currentTRegister = 0;
        outputFile = program.getName() + ".asm";
    }
    
    public void writeFile() throws IOException{
        String code;
        code = ".data\n";
        //Constant and variable declarations
        code += writeDeclarations(program);
        
        // Assembly instructions
        code += "\n \n.text\nmain: \n";
        
        // End of file
        code += "li   $v0,    10\n";
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
    
    public String writeCode(StatementNode node, String reg)
    {
        String code = null;
        
        if(node instanceof AssignmentStatementNode){
            code += writeCode((AssignmentStatementNode)node);
        }
        if(node instanceof CompoundStatementNode){
            code += writeCode((CompoundStatementNode)node);
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
        String code = null;
        String assignment = node.getLvalue().getName();
        String resultReg = "$t" + currentTRegister++;
        
        code += writeCode(node.getExpression(), resultReg );
        code += "sw\t" + resultReg + ", \t" + assignment + "\n";
        
        currentTRegister = 0;
        return code;
    }
    
    public String writeCode(ExpressionNode node, String reg)
    {
        String code = null;
        
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
    
    private String writeCode(ValueNode node, String resultReg){
        String value = node.getAttribute();
        String code = "addi \t" + resultReg + ", \t$zero, \t" + value + "\n";
        
        return code;
    }
    
    private String writeCode(VariableNode node, String resultReg){
        String code = null;
        String var = node.getName();
        
        code += "lw \t" + resultReg + ",    " + var + "\n";
        return code;
    }  
      
    public String writeCode(CompoundStatementNode node){
        String code = null;
        
        return null;
    }
    
    public String writeCode(IfStatementNode node){
        
        return null;
    }
    
    public String writeCode(WhileStatementNode node){
        
        return null;
    }
}
