
package codegen;
import syntaxtree.*;

/**
 *
 * @author Michael Farghali
 */
public class CodeGeneration {
    
    public String writeCodeForRoot(ProgramNode root) 
    {
        StringBuilder code = new StringBuilder();
        code.append( ".data\n");
        code.append( "answer:   .word   0\n\n\n");
        code.append( ".text\n");
        code.append( "main:\n");
        
       
        return( code.toString() );
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
    
    
    public String writeCode(AssignmentStatementNode stateNode){
        String code = null;
        
        return null;
    }
    
    public String writeCode(DeclarationsNode expNode, String reg)
    {
        String nodeCode;
        
        return null;
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
