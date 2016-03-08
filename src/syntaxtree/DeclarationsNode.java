

package syntaxtree;
import java.util.ArrayList;
/**
 * Represents one or more variable declarations
 * @author Michael Farghali
 */
public class DeclarationsNode extends SyntaxTreeNode{
    
    private ArrayList<VariableNode> vars;
    
    public DeclarationsNode(){
        this.vars = new ArrayList<VariableNode>();
    }
    /**
     * Adds a VariableNode to ArrayList of variables
     * @param var The name of the VariableNode
     */
    public void addVars(VariableNode var){
        vars.add(var);
    }    
        
     @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "DeclarationNode: \n";
        // For every variable in ArrayList call VariableNodes indentedToString
        for(VariableNode i : vars){  
            answer += i.indentedToString(level +1);
        }
        return answer;
    }
    
}
