
package syntaxtree;


/**
 * Represents a program in the syntax tree
 * @Michael Farghali
 */
public class ProgramNode extends SyntaxTreeNode {
    
    private String name;
    
    private DeclarationsNode variables;
    
    private CompoundStatementNode main;
    
    private SubProgramDeclarationsNode functions;
    /**
     * Sets the name of the program
     * @param nom 
     */
    public ProgramNode(String nom){
        this.name = nom;
    }
    
    /**
     * Gets the program name
     * @return The name of the program
     */
    public String getName() {return name;    }
    
    /**
     * Gets the arraylist of VariableNodes
     * @return An arraylist of VariableNodes
     */
    public DeclarationsNode getVariables() {return (this.variables);    }
    
    /**
     * Gets the arraylist of statements
     * @return An arraylist of statements
     */
    public CompoundStatementNode getMain() {return (this.main);    }
    
    /**
     * Gets an arraylist of functions
     * @return An arraylist of SubProgramNodes
     */
    public SubProgramDeclarationsNode getFunctions() {return (this.functions);}

    /**
     * Sets the name of the program
     * @param name The name of the program
     */
    public void setName(String name) {this.name = name;    }
    
    /**
     * Sets the arraylist of DeclarationsNodes
     * @param variables An arraylist of DeclarationsNodes
     */
    public void setVariables(DeclarationsNode variables) {
        this.variables = variables;   }
    
    /**
     * Sets the CompoundStatementNode
     * @param main A CompoundStatementNode
     */
    public void setMain(CompoundStatementNode main) {this.main = main;  }
    
    /**
     * Sets an ArrayList of SubProgramDeclarationsNodes
     * @param functions An ArrayList of SubProgramNodes
     */
    public void setFunctions(SubProgramDeclarationsNode functions) {
        this.functions = functions;    }
       
    
    @Override
    public String toString() {
        return( name);
    }
    /**
     * Adds the program to the syntax tree
     * @param level The level of the syntax tree
     * @return A string representation of the syntax tree
     */
     @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "Program: " + this.name + "\n";
        answer += this.variables.indentedToString(level + 1);
        answer += this.functions.indentedToString(level + 1);
        answer += this.main.indentedToString(level + 1);
        return answer;
    }
    
    
  
    
}
