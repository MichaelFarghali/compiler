
package syntaxtree;
import scanner.TokenType;

/**
 * For now an empty parent node for ValueNode, VariableNode, and OperationNode
 * @author Michael Fargahli
 */
public abstract class ExpressionNode extends SyntaxTreeNode {
    
    TokenType type;
}
