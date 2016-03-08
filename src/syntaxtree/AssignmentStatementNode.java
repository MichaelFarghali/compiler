
package syntaxtree;

/**
 *
 * @author Michael Farghali
 */
public class AssignmentStatementNode extends StatementNode{
    
    private VariableNode lvalue;
    
    private ExpressionNode expression;
    
    public AssignmentStatementNode(){
        
    }
    public void setLvalue(VariableNode lvalue) {
        this.lvalue = lvalue;
    }

    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }
    
    @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "AssignmentNode: \n";
        answer += lvalue.indentedToString(level + 1);
        answer += expression.indentedToString(level + 1);
        return( answer);
    }    
    
}
