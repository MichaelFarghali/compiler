
package syntaxtree;

/**
 * An abstract class that builds the syntax tree
 * @author Michael Farghali
 */
public abstract class SyntaxTreeNode {
    
    
     /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    public String indentedToString( int level) {
        String answer = "";
        if( level > 0) {
            answer = "|-- ";
        }
        for( int indent = 1; indent < level; indent++) answer += "--- ";
        return( answer);
    }    
}
