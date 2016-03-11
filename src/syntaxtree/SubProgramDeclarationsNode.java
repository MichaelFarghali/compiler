
package syntaxtree;
import java.util.ArrayList;
/**
 * Represents a list of functions and procedures in the syntax tree
 * @author Michael Farghali
 */
public class SubProgramDeclarationsNode extends SyntaxTreeNode{
    
    private ArrayList<SubProgramNode> procs;
    
    /**
     * Creates and empty ArrayList of type SubProgramNode
     */
    public SubProgramDeclarationsNode(){
        this.procs = new ArrayList<SubProgramNode>();    
    }
    
    /**
     * Adds functions and procedures (SubProgramNode) to the ArrayList
     * @param procedure A SubProgramNode representing a function or procedure
     */
    public void addProcs(SubProgramNode procedure){
        procs.add(procedure);
    }
    /**
     * Adds one or more SubProgramNodes to the syntax tree string
     * @param level The level of the syntax tree
     * @return A string representation of the syntax tree
     */
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
