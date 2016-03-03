
package syntaxtree;
import java.util.ArrayList;
/**
 *
 * @author Michael Farghali
 */
public class SubProgramDeclarationsNode extends SyntaxTreeNode{
    
    private ArrayList<SubProgramNode> procs;
    
    public SubProgramDeclarationsNode(){
        this.procs = new ArrayList<SubProgramNode>();    
    }
    
    public void addProcs(SubProgramNode procedure){
        procs.add(procedure);
    }
    
    @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "SubProgramDeclarationsNode: \n";
        
        for( SubProgramNode i: procs){
            answer += super.indentedToString(level+1);
            answer += "SubProgramNode: " + i + "\n";
        }       
        return answer;
    }
}
