

package syntaxtree;
import java.util.ArrayList;
/**
 *
 * @author user
 */
public class DeclarationsNode extends SyntaxTreeNode{
    
    private ArrayList<VariableNode> vars;
    
    public DeclarationsNode(){
        this.vars = new ArrayList<VariableNode>();
    }
   
    public void addVars(VariableNode var){
        vars.add(var);
    }    
        
     @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "DeclarationNode: \n";
        
        for(VariableNode i : vars){
            answer += super.indentedToString(level +1);
            answer += "VariableNode: " + i + "\n";
        }
        return answer;
    }
    
}
