
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
     
    public String writeCode(IfStatementNode node){
        
        return null;
    }
    
    public String writeCode(WhileStatementNode node){
        
        return null;
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
