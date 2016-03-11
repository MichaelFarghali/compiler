

package syntaxtree;

/**
 * ValueNode represents a number in the syntax tree. 
 */
public class ValueNode extends ExpressionNode{
    
    private String attribute;
    
    /**
     * Creates a ValueNode with the given attribute.
     * @param attr The attribute for this value node.
     */
    public ValueNode(String attr){
        this.attribute = attr;
    }
    
      /** 
     * Returns the attribute of this node.
     * @return The attribute of this ValueNode.
     */
    public String getAttribute() { return( this.attribute);}
    
    @Override
    public String toString() {
        return( attribute);
    }
    /**
     * Adds a ValueNode to the syntax tree string
     * @param level The level of the syntax tree
     * @return A string representation of the syntax tree
     */
     @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "Value: " + this.attribute + "\n";
        return answer;
    }
}
