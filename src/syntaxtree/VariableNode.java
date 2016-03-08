
package syntaxtree;

/**
 * Represents a variable in syntax tree
 * @author Michael Farghali
 */
public class VariableNode extends ExpressionNode{
    
    private String name;
    
    /**
     * Creates a VariableNode with the given name.
     * @param nom The name for this variable node.
     */
    public VariableNode(String nom){
        this.name = nom;
    }
    
      /** 
     * Returns the name of this node.
     * @return The name of this VariableNode.
     */
    public String getName() { return( this.name);}
    
    @Override
    public String toString() {
        return(name);
    }
    
     @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "Variable: " + this.name + "\n";
        return answer;
    }
}
