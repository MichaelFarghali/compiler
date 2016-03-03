
package syntaxtree;


/**
 *
 * @Michael Farghali
 */
public class ProgramNode extends SyntaxTreeNode {
    
    private String name;
    
    private DeclarationsNode variables;
    
    private CompoundStatementNode main;
    
    private SubProgramDeclarationsNode functions;
    
    public ProgramNode(String nom){
        this.name = nom;
    }

    public String getName() {return name;    }
    public DeclarationsNode getVariables() {return (this.variables);    }
    public CompoundStatementNode getMain() {return (this.main);    }
    public SubProgramDeclarationsNode getFunctions() {return (this.functions);}

    public void setName(String name) {this.name = name;    }
    
    public void setVariables(DeclarationsNode variables) {
        this.variables = variables;   }
    
    public void setMain(CompoundStatementNode main) {this.main = main;  }
    
    public void setFunctions(SubProgramDeclarationsNode functions) {
        this.functions = functions;    }
       
    
    @Override
    public String toString() {
        return( name);
    }
    
     @Override
    public String indentedToString( int level) {
        String answer = super.indentedToString(level);
        answer += "Program: " + this.name + "\n";
        answer += this.variables.indentedToString(level + 1);
        return answer;
    }
    
    
  
    
}
