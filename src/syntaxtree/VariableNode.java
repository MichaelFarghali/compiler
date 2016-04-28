
package syntaxtree;
import scanner.TokenType;

/**
 * Represents a variable in syntax tree
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
    
    public void setType(TokenType token) { type = token; }
    
    /**
     * Gets the name of the VariableNode
     * @return The name of the VariableNode
     */
    @Override
    public String toString() {
        return(name);
    }
    /**
     * Adds a VariableNode to the syntax tree string
     * @param level The level of the syntax tree
     * @return A string representation of the syntax tree
     */
     @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "Variable: " + this.name + ": " + type + "\n";
        return answer;
    }
}
