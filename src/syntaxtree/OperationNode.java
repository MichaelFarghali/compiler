package syntaxtree;

import scanner.TokenType;

/**
 * Represents an Operation (+, -, *, or /) in the syntax tree.
 */
public class OperationNode extends ExpressionNode {
    
    /** The left operator of this operation. */
    private ExpressionNode left;
    
    /** The right operator of this operation. */
    private ExpressionNode right;
    
    /** The kind of operation. */
    private TokenType operation;
    
    /**
     * Creates an operation node given an operation token.
     * @param op The token representing this node's math operation.
     */
    public OperationNode ( TokenType op) {
        this.operation = op;
    }
    
    public OperationNode()
    {
        
    }
    
    
    /**
     * Gets the left hand side of an operation
     * @return The left hand side of an operation
     */
    public ExpressionNode getLeft() { return( this.left);}
    /**
     * Gets the right hand side of an operation
     * @return The right hand side of an operation
     */
    public ExpressionNode getRight() { return( this.right);}
    /**
     * Gets the kind of operation to be performed
     * @return The operation to be performed of type TokenType
     */
    public TokenType getOperation() { return( this.operation);}
    
    /**
     * Sets the left hand side of an expression
     * @param node Either a ValueNode or VariableNode
     */
    public void setLeft( ExpressionNode node) { this.left = node;}
    /**
     * Sets the right hand side of an expression
     * @param node Either a ValueNode or VariableNode
     */
    public void setRight( ExpressionNode node) { this.right = node;}
    /**
     * Sets the type of operation to be performed
     * @param op A TokenType of the kind of operation to be performed
     */
    public void setOperation( TokenType op) { this.operation = op;}
    
        
    /**
     * Returns the operation token as a String.
     * @return The String version of the operation token.
     */
    @Override
    public String toString() {
        return operation.toString();
    }
  /**
     * Adds an operation to the syntax tree string
     * @param level The level of the syntax tree
     * @return A string representation of the syntax tree
     */
    @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "Operation: " + this.operation + "\n";
        answer += left.indentedToString(level + 1);
        answer += right.indentedToString(level + 1);
        return( answer);
    }

}