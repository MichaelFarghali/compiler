
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
    public CompoundStatementNode(ArrayList<StatementNode> states){
        this.statements = new ArrayList<StatementNode>(states);
    }
    
      /** 
     * Returns the attribute of this node.
     * @return The attribute of this ValueNode.
     */
    public ArrayList getStatements() { return( this.statements);}
    
        
     @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "Statement: " + this.statements + "\n";
        return answer;
    }
}
