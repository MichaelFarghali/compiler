
package syntaxtree;

/**
 * Represents a while statement in the syntax tree
 * @author Michael Farghali
 */
public class WhileStatementNode extends StatementNode{
    
    // The condition to be evaluated 
    private ExpressionNode condition;
    // The statement(s) to be performed
    private StatementNode statements;
    
    /**
     * Sets the condition for a while loop
     * @param exp An expression such as, while ( variable <= 100 )
     */
    public void setCondition(ExpressionNode exp){
        this.condition = exp;
    }
    /**
     * Sets the code to be evaluated inside a while loop
     * @param body The statements to be performed in while loop
     */
    public void setStatements(StatementNode body){
        this.statements = body;
    }   
    
     /**
     * Adds a while statement node to the syntax tree string
     * @param level The level of the syntax tree
     * @return A string representation of the syntax tree
     */
    @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "WhileStatementNode: \n";
        answer += condition.indentedToString(level + 1);
        answer += super.indentedToString(level) + "Do: \n";
        answer += statements.indentedToString(level + 1);
        return( answer );
    }
    
}
