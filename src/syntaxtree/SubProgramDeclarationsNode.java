
package syntaxtree;
import java.util.ArrayList;
/**
 *
 * @author Michael Farghali
 */
public class SubProgramDeclarationsNode extends SyntaxTreeNode{
    
    private ArrayList<SubProgramNode> procs;
    
    public SubProgramDeclarationsNode(ArrayList<SubProgramNode> prodecure){
        procs = new ArrayList<>(procs);    
    }
    
    public ArrayList getSubProgramDecs(){ return (this.procs);    }
    
    @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "Procedure: " + this.procs + "\n";
        return answer;
    }
}
