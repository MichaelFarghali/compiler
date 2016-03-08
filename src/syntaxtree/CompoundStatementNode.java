
package syntaxtree;
import java.util.ArrayList;

/**
 *  Represents a Compound Statement node. Ex. 3 + 4*5
 * @author Michael Farghali
 */
public class CompoundStatementNode extends StatementNode{
    
    private ArrayList<StatementNode> statements;
    
    /**
     * Creates a CompoundStatementNode with empty ArrayList.
     * @param states The list of statements for this CompoundStatementNode.
     */
    public CompoundStatementNode (){
        this.statements = new ArrayList<StatementNode>();
    }
    // Add statements to arraylist 
    public void addStatements(StatementNode states){
        statements.add(states);
    }
    
     @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "CompoundStatementNode: \n";
        // For every statement in ArrayList print out with indentedToString
        for(StatementNode i: statements){
            answer += i.indentedToString(level + 1);
        }
        return answer;
    }
}
