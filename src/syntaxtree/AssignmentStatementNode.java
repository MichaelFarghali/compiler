
package syntaxtree;

/**
 * Represents an assignment (left value and expression)
 * @author Michael Farghali
 */
public class AssignmentStatementNode extends StatementNode{
    
    private VariableNode lvalue;
    
    private ExpressionNode expression;
    
    public AssignmentStatementNode(){
        
    }
    /**
     * The setLvalue function sets the the VariableNode on left hand side
     * @param lvalue The variable on the left-handed side of an 
     * assignment
     */
    public void setLvalue(VariableNode lvalue) {
        this.lvalue = lvalue;
    }
    /**
     * Sets the expression to either a value or an operation of type TokenType
     * @param expression Either an OperationNode or a ValueNode
     */
    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }
    /**
     * Adds an AssignmentNode to the syntax tree string
     * @param level The level of the syntax tree
     * @return A string representation of the syntax tree
     */
    @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "AssignmentNode: \n";
        answer += lvalue.indentedToString(level + 1);
        answer += expression.indentedToString(level + 1);
        return( answer);
    }    
    
}
