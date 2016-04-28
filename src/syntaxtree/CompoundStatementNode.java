
package syntaxtree;
import java.util.ArrayList;

/**
 * Represents a list of one or more compound statements
 * @author Michael Farghali
 */
public class CompoundStatementNode extends StatementNode{
    
    private ArrayList<StatementNode> statements;
    
    /**
     * Creates an empty ArrayList of type StatementNode
     */
    public CompoundStatementNode (){
        this.statements = new ArrayList<StatementNode>();
    }
    /**
     * Adds statements to an ArrayList of StatementNodes
     * @param states A VariableNode or ExpressionNode
     */
    public void addStatements(StatementNode states){
        statements.add(states);
    }
    /**
     * Returns arraylist of statements
     * @return Arraylist of statement nodes children
     */
    public ArrayList<StatementNode> getStatements(){
        return this.statements;
    }
    /**
     * Adds a CompoundStatementNode to the syntax tree string
     * @param level The level of the syntax tree
     * @return A string representation of the syntax tree
     */
     @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "CompoundStatementNode: \n";
     //For every statement in ArrayList add to syntax tree with indentedToString
        for(StatementNode i: statements){
            answer += i.indentedToString(level + 1);
        }
        return answer;
    }
}
