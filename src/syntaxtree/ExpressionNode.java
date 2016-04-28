
package syntaxtree;
import scanner.TokenType;

/**
 * ExpressionNode is an abstract parent class expressions, immediate values, and 
 * variables. The TokenType type field will be used by child nodes to store
 * the data type of operations and variables
 * @author Michael Fargahli
 */
public abstract class ExpressionNode extends SyntaxTreeNode {
    
    TokenType type;
}
