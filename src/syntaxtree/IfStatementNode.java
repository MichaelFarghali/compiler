
package syntaxtree;

/**
 * Represents an If-Then-Else statement block in syntax tree
 * @author Michael Farghali
 */
public class IfStatementNode extends StatementNode{
    
    // The condition to be checked 
    private ExpressionNode condition;
    
    // The statement to be performed
    private StatementNode statements;
    
    // The else statement
    private StatementNode elseStatement;
    
    /**
     * Sets the condition of if statement node
     * @param exp 
     */
    public void setCondition(ExpressionNode exp){
        this.condition = exp;
    }
    /**
     * Sets the statements after 'then' in if statement block
     * @param statements 
     */
    public void setStatements(StatementNode statements){
        this.statements = statements;
    }
    
    /**
     * Sets the else statement of if-then-else statement block
     * @param statements 
     */
    public void setElseStatment(StatementNode statements){
        this.elseStatement = statements;        
    }
    
    public ExpressionNode getCondition(){
        return this.condition;
    }
    
    public StatementNode getStatement(){
        return this.statements;
    }
    
    public StatementNode getElseStatement(){
        return this.elseStatement;
    }
    /**
     * Adds an If-Then-Else block to the syntax tree string
     * @param level The level of the syntax tree
     * @return A string representation of the syntax tree
     */
    @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "IfStatementNode: \n";
        answer += condition.indentedToString(level + 1);
        answer += super.indentedToString(level) + "Then: \n";
        answer += statements.indentedToString(level + 1);
        answer += super.indentedToString(level) + "Else: \n";
        answer += elseStatement.indentedToString(level + 1);
        return( answer);
    }
    
}
