

package syntaxtree;
import java.util.ArrayList;
/**
 *
 * @author user
 */
public class DeclarationsNode extends SyntaxTreeNode{
    
    private ArrayList<VariableNode> vars;
    
    public DeclarationsNode(ArrayList<VariableNode> var){
        this.vars = new ArrayList<VariableNode>(var);
    }
    
      /** 
     * Returns the ArrayList of declarations for this node.
     * @return vars The declarations of this DeclarationNode.
     */
    public ArrayList getDeclarations() { return( this.vars);}
    
        
     @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "Declaration: " + this.vars + "\n";
        return answer;
    }
    
}
