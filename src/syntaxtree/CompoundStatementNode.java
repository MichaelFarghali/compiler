
package syntaxtree;
import java.util.ArrayList;

/**
 *
 * @author Michael Farghali
 */
public class CompoundStatementNode extends StatementNode{
    
    private ArrayList<StatementNode> statements;
    
    /**
     * Creates a CompoundStatementNode with the given attribute.
     * @param states The list of statements for this CompoundStatementNode.
     */
    public CompoundStatementNode (){
        this.statements = new ArrayList<StatementNode>();
    }
    
    public void addStatements(StatementNode states){
        statements.add(states);
    }
        
     @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "CompoundStatementNode: \n";
        
        for(StatementNode i: statements){
            answer += super.indentedToString(level +1);
            answer += "StatementNode: " + i + "\n";
        }
        return answer;
    }
}
