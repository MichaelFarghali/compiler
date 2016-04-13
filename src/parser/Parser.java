package parser;

import scanner.Scanner;
import scanner.TokenType;
import java.util.ArrayList;
import symbol.table.SymbolTable;
import syntaxtree.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * The Parser class will eventually process a source file and create a parse
 * tree or reject the source as invalid using top down recursive descent
 * parsing. For now it only recognizes valid tokens according to the production
 * rules
 *
 * @author Michael Farghali
 */
public class Parser {

    private Scanner scanner;  // The Scanner
    private TokenType currentToken;  // The TokenType variable 
    private SymbolTable st; //The symboltable

    /**
     * The Parser constructor creates a File variable which it passes to a new
     * instance of the Scanner class. It then loads the first token to be 
     * processed. 
     * @param filename The name of the input file to be parsed.
     */
    public Parser(String filename) {
        // Create a file variable and an instance of scanner with specified file
        File input = new File(filename);
        st = new SymbolTable();
        scanner = new Scanner(input);
        // Load in the first token as a look ahead token and assign to current
        scanner.nextToken();
        currentToken = scanner.getToken();
    }
    /**
     * The match function checks that the currentToken matches the expectedToken
     * if not it goes to error function. If the two match it then loads the next
     * token or if no token available it displays that the end of file has been
     * reached or that the scanner stopped on an invalid token. 
     * 
     * @param expectedToken The TokenType that is expected according to 
     * production rules
     */
    public void match(TokenType expectedToken) {
        System.out.println("match " + expectedToken + " with current " + 
               currentToken + ":" + scanner.getLexeme());
        if (currentToken == expectedToken) {
            boolean scanResult = scanner.nextToken();
            // If there's a next token assigne to currentToken
            if (scanResult) {
                currentToken = scanner.getToken();
            } 
            //If scanner.nextToken returns false check for EOF or invalid token
            else {
                System.out.println("No Token Available");
                String lexeme = scanner.getLexeme(); //Check if string is null
                if (lexeme == null) {
                    System.out.println("Parser: End of file");
                    // If String is null set currentToken to null
                    currentToken = null; 
                } else {
                    System.out.println("Scanner barfed on " + lexeme);
                }
            }
        } 
        else {
            error();  // We don't match!
        }

    }//end match

    /**
     * Implements program -&gt; program id ; declarations subprogram_declarations
     *                       compound_statement .
     * The program function also checks that there is no data after the period
     * @return The completed syntax tree processed by parser
     */
    public ProgramNode program() throws IOException{
        match(TokenType.PROGRAM);
        
        //add program name to symbol table
        String pName = scanner.getLexeme();
        st.addProgramName(pName);
               
        //Create a new ProgramNode
        ProgramNode pNode = new ProgramNode(pName);     
        
        match(TokenType.ID);
        match(TokenType.SEMICOLON);
        //Call declarations() and set syntax tree variables
        pNode.setVariables( declarations() );
        //Call subprogram_declarations and set syntax tree functions
        pNode.setFunctions( subprogram_declarations() );
        //Call compound_statement and set syntax tree assignment statements
        pNode.setMain( compound_statement() );       
        
        match(TokenType.PERIOD);
        System.out.println(st.myToString());
        
        BufferedWriter writer = null;
        // Write the symbol table to file 'symboltable.txt'
        try {
            File file = new File("symboltable.txt");
            FileWriter fwriter = new FileWriter(file);
            writer = new BufferedWriter(fwriter);
            
            writer.write(st.myToString());
        }
        catch (IOException e) {
            System.out.println("Failed writing to file.");
        }
        finally {
            if (writer != null)
                writer.close();
        }
        // Write the syntax tree to file 'syntax.tree'
        try {
            String fileName = "syntax.tree";
            File file = new File(fileName);
            FileWriter fwriter = new FileWriter(file);
            writer = new BufferedWriter(fwriter);
            writer.write(pNode.indentedToString(0));
        }
        catch (IOException e){
            System.out.println("Failed writing to file: syntax.tree");
        }
        finally {
            if (writer != null)
                writer.close();
        }
        
        //If there's still data in the file stream go to error
        if( currentToken != null){
            System.out.println("File not empty");
            error();        
        }
        
        return pNode;
    }//end program
    
    /**
     * Implements identifier_list -&gt; id | id, identifier_list
     * @return An ArrayList of variable names of type String
     */
    public ArrayList<String> identifier_list() {
        String var;
        ArrayList<String> variables = new ArrayList<>();
        //Get the name of variable and add to symbol table
        var = scanner.getLexeme();
        //st.addVarName(var);
        //Add variable name to ArrayList
        variables.add(var);
        
        match(TokenType.ID);
       //While there's still commas match IDs and add to symbol table, ArrayList
        while (currentToken == TokenType.COMMA) {
            match(TokenType.COMMA);
            var = scanner.getLexeme();
            //st.addVarName(var);            
            variables.add(var);            
            match(TokenType.ID);            
        }
        return variables;
    }//end identifier_list

    /**
     * Implements declarations -&gt; var identifier_list : type ; declarations |
     *                            lambda
     * @return A DeclarationNode containing any declared variables
     */
    public DeclarationsNode declarations() {
        
        DeclarationsNode decNode = new DeclarationsNode();
        ArrayList<String> varList = new ArrayList<>();
        ArrayList<VariableNode> moreVars= new ArrayList<>();
        TokenType token;
        
        //If there is a variable declaration process it. Else do nothing
        if (currentToken == TokenType.VAR) {
            match(TokenType.VAR);
            //Get arraylist of variable names (Strings)
            varList = identifier_list();
            
             
                        
            match(TokenType.COLON);
            //for every variable name add a new VariableNode to declarationsNode
            token = type();
            for (String s: varList){
                VariableNode var = new VariableNode(s);
                var.setType(token);
                decNode.addVars(var);                
                st.addVarName(s, token.toString());  
            }
            match(TokenType.SEMICOLON);
            //Call declarations() and get the next declarationNode if any
            DeclarationsNode moreDecs = declarations();
            //Get new VariableNode(s) and add to decNode
            moreVars = moreDecs.getVars();
            for (VariableNode v: moreVars){
                decNode.addVars(v);
            }                      
        }        
       
        return decNode;
    }//end declarations

    /**
     * Implements type -&gt; standard_type |
     *                    array[num : num] of standard_type
     */
    public TokenType type() {
        TokenType type;
        // Check if there is an array being declared
        if ( currentToken == TokenType.ARRAY){
            //Add array name to symbol table
            //st.addArrayName(scanner.getLexeme());
           
            match(TokenType.ARRAY);
            match(TokenType.L_BRACKET);
            match(TokenType.NUM);
            match(TokenType.COLON);
            match(TokenType.NUM);
            match(TokenType.R_BRACKET);
            match(TokenType.OF);
            type = standard_type();
        }
        else
            type = standard_type();        
        return type;
    }//end type
    
    /**
     * Implements standard_type -&gt; integer | real
     */
    public TokenType standard_type() {
        TokenType type;
        if (currentToken == TokenType.INTEGER) {
            type = currentToken;
            match(TokenType.INTEGER);
        } else {
            type = currentToken;
            match(TokenType.REAL);
        }
        return type;
    }//end standard_type

    /**
     * Implements subprogram_declarations -&gt; subprogram_declaration;
     *                                       subprogram_declarations | lambda
     */
    public SubProgramDeclarationsNode subprogram_declarations() {
        
        SubProgramDeclarationsNode subNode = new SubProgramDeclarationsNode();
            //If a function or procedure is declared go to subprogram_declaration
            if ( currentToken == TokenType.FUNCTION ||
                 currentToken == TokenType.PROCEDURE){
                
                subprogram_declaration();
                match(TokenType.SEMICOLON);
                subprogram_declarations();
            }           
            return subNode;
    }//end subprogram_declaration
    
    /**
     * Implements subprogram_declaration -&gt; subprogram_head declarations
     *                                      subprogram_declarations
     *                                      compound_statement
     */
    public void subprogram_declaration() {
        subprogram_head();
        declarations();
        subprogram_declarations();
        compound_statement();
    }//end subprogram_declaration
    
    /**
     * Implements subprogram_head -&gt; function id arguments : standard_type ; |
     *                               procedure id arguments
     */
    public void subprogram_head() {
        if (currentToken == TokenType.FUNCTION){
            match(TokenType.FUNCTION);
            //Add the function name to symbol table
            st.addFunctionName(scanner.getLexeme());
            match(TokenType.ID);
            arguments();
            match(TokenType.COLON);
            standard_type();
            match(TokenType.SEMICOLON);
            }
        else if(currentToken == TokenType.PROCEDURE){
            match(TokenType.PROCEDURE);
            //Add procedure name to symbol table
            st.addProcName(scanner.getLexeme());
            match(TokenType.ID);
            arguments();
            match(TokenType.SEMICOLON);
        }
    
    }//end subprogram_head
    
    /**
     * Implements arguments -&gt; ( parameter_list ) | lambda
     */
    public void arguments() {
        if ( currentToken == TokenType.L_PARENTHESES){
            match(TokenType.L_PARENTHESES);
            parameter_list();
            match(TokenType.R_PARENTHESES);
        }
    }//end arguments
    
    /**
     * Implements EBNF production rule 
     * parameter_list -&gt; identifier_list : type { ; identifier_list : type}
     */
    public void parameter_list(){
        identifier_list();
        match(TokenType.COLON);
        type();
        // If there's a semicolon after a varaible eat and get next variable
        while (currentToken == TokenType.SEMICOLON){
            match(TokenType.SEMICOLON);
            parameter_list();
        }
    }//end parameter_list

    /**
     * Implements compound_statement -&gt; begin optional_statements end
     */
    public CompoundStatementNode compound_statement() {
        System.out.println("In compound statement");
        ArrayList<StatementNode> statements = new ArrayList<>();
        CompoundStatementNode csNode = new CompoundStatementNode();
        match(TokenType.BEGIN);
        statements = optional_statements();
        for (StatementNode s: statements){
            csNode.addStatements(s);
//            System.out.println(s.toString());
        }
        match(TokenType.END);
        
        return csNode;
    }//end compound_statement

    /**
     * Implements optional_statements -&gt; statement_list | lambda
     * lambda.
     */
    public ArrayList<StatementNode> optional_statements() {
        System.out.println("In optional statements");
        ArrayList<StatementNode> list = new ArrayList<>();
        //If currentToken is a variable id, if, while, read, or write statement
        //go to statement_list
        if ( currentToken == TokenType.ID ||
             currentToken == TokenType.IF||
             currentToken == TokenType.WHILE ||
             currentToken == TokenType.READ ||
             currentToken == TokenType.WRITE){
            
            list = statement_list();
        }
        return list;
    }//end optional_statements
    
    /**
     * Implements statement_list -&gt; statement | statement ; statement_list
     */
    public ArrayList<StatementNode> statement_list(){
        System.out.println("In statement_list");
        ArrayList<StatementNode> state = new ArrayList<>();
        ArrayList<StatementNode> statements = new ArrayList<>();
        statements.add( statement() );
        //If there's a semicolon eat token and get next statement
        while (currentToken == TokenType.SEMICOLON){
            match(TokenType.SEMICOLON);
            state = statement_list();
            for(StatementNode s: state){
                statements.add(s);
            }
            
        }
        return statements;
    }//end statement_list
    
    /**
     * Implements statement -&gt; variable assignop expression |
     *                         procedure_statement (not currently working) |
     *                         compound_statement |
     *                         if expression then statement else statement |
     *                         while expression do statement |
     *                         read | write (read, write currently treated as id)
     */
    public StatementNode statement(){
        System.out.println("In statement");
        AssignmentStatementNode statement = new AssignmentStatementNode();
        //If TokenType is type ID then check SymbolTable if it's a variable 
        //or a procedure ID 
        if(currentToken == TokenType.ID) {
            if (st.isVarName( scanner.getLexeme()) ){
                statement.setLvalue( variable() ); // Process variable
                match(TokenType.ASSIGN);
                statement.setExpression( expression() );
            } // Process procedure
            else if(st.isProcName(scanner.getLexeme())){
                procedure_statement();
            }
        }
        //if currentToken is begin go to compound_statement
        else if (currentToken == TokenType.BEGIN){
            compound_statement();
        }//if currentToken is IF process if then else statement block
        else if (currentToken == TokenType.IF){
            IfStatementNode state = new IfStatementNode();
            match(TokenType.IF);
            state.setCondition( expression() );
            match(TokenType.THEN);
            state.setStatements(statement() );
            match(TokenType.ELSE);
            state.setElseStatment( statement() );
            return state;
        }//if currentToken is while process while do statements
        else if (currentToken == TokenType.WHILE){
            WhileStatementNode state = new WhileStatementNode();
            match(TokenType.WHILE);
            state.setCondition( expression() );
            match(TokenType.DO);
            state.setStatements( statement() );
            return state;
        }
        else if (currentToken == TokenType.READ){
            match(TokenType.READ); // Treating as keyword for now
        }
        else if (currentToken == TokenType.WRITE){
            match(TokenType.WRITE); // Treating as keyword for now
        }//Go to error for anything else
        else
            error();
        return statement;
    }//end statement
    
    /**
     * Implements procedure_statement id | id expression_list
     */
    public void procedure_statement(){
        System.out.println("In procedure_statement");
        match(TokenType.ID);
        if(currentToken == TokenType.L_PARENTHESES){
            match(TokenType.L_PARENTHESES);
            expression_list();
            match(TokenType.R_PARENTHESES);
        }
    }
    
    /**
     * Implements variable -&gt; id | id [expression]
     */
    public VariableNode variable(){
        System.out.println("In variable");
        VariableNode var;
        var = new VariableNode( scanner.getLexeme() );
        match(TokenType.ID);
        if (currentToken == TokenType.L_BRACKET){
            match(TokenType.L_BRACKET);
            expression();
            match(TokenType.R_BRACKET);
        }
        return var;
    }//end variable
    
    /**
     * Implements expression -&gt; simple_expression | 
     *                          simple_expression relop simple_expression
     */
    public ExpressionNode expression(){
        System.out.println("In expression");
        ExpressionNode exp; 
        exp = simple_expression(); 
       
        //Check for relop's <, >, <=, >=, <>, =. If found match and get next
        //simple expression
        if ( currentToken == TokenType.EQUALS){
            OperationNode op = new OperationNode(currentToken);
            op.setLeft(exp);
            match(TokenType.EQUALS);
            op.setRight( simple_expression() );
            return op;
        }
        else if ( currentToken == TokenType.NOT_EQUAL){
            OperationNode op = new OperationNode(currentToken);
            op.setLeft(exp);
            match(TokenType.NOT_EQUAL);
            op.setRight( simple_expression() );
            return op;
        }
        else if ( currentToken == TokenType.LESS_THAN){
            OperationNode op = new OperationNode(currentToken);
            op.setLeft(exp);
            match(TokenType.LESS_THAN);
            op.setRight( simple_expression() );
            return op;
        }
        else if ( currentToken == TokenType.LESS_THAN_EQUALS){
            OperationNode op = new OperationNode(currentToken);
            op.setLeft(exp);
            match(TokenType.LESS_THAN_EQUALS);
            op.setRight( simple_expression() );
            return op;
        }
        else if ( currentToken == TokenType.GREATER_THAN_EQUALS){
            OperationNode op = new OperationNode(currentToken);
            op.setLeft(exp);
            match(TokenType.GREATER_THAN_EQUALS);
            op.setRight( simple_expression() );
            return op;
        }
        else if ( currentToken == TokenType.GREATER_THAN){
            OperationNode op = new OperationNode(currentToken);
            op.setLeft(exp);
            match(TokenType.GREATER_THAN);
            op.setRight( simple_expression() );
            return op;
        }              
        return exp;
    }//end expression
    
    /**
     * Implements simple_expression -&gt; term simple_part | lambda
     */
    public ExpressionNode simple_expression(){
        System.out.println("In simple_expression");
        ExpressionNode exp;
        
         if( currentToken == TokenType.PLUS ||
             currentToken == TokenType.MINUS){
            
            sign();
            exp = term();
            while ( currentToken == TokenType.PLUS ||
                    currentToken == TokenType.MINUS ||
                    currentToken == TokenType.OR){
                
                ExpressionNode left = exp;
                OperationNode op = addop();
                ExpressionNode right = term();
                op.setLeft(left);
                op.setRight(right);
                exp = op;
            } 
            
        }
        else {
            exp = term();
            while ( currentToken == TokenType.PLUS ||
                    currentToken == TokenType.MINUS ||
                    currentToken == TokenType.OR){
                
                ExpressionNode left = exp;
                OperationNode op = addop();
                ExpressionNode right = term();
                op.setLeft(left);
                op.setRight(right);
                exp = op;
            }            
        }        
    
    return exp;
    }//end simple_expression
    
     /**
     * The addop function matches the currentToken to one of the addops
     * ( +, -, OR ) and then creates an OperationNode with that
     * operation
     * @return An OperationNode representing +, -, OR
     */
    public OperationNode addop(){
        System.out.println("In addop");
        OperationNode op = new OperationNode(currentToken);
        //Match one of the addops +, -, OR 
        if (currentToken == TokenType.PLUS) {
            match(TokenType.PLUS);
        }
        else if (currentToken == TokenType.MINUS) {
            match(TokenType.MINUS);
        }
        else if (currentToken == TokenType.OR) {
            match(TokenType.OR);
        }
        else {
            System.out.println("Error in addop "+currentToken+"not expected.");
            error();
        }
        return op;
    }//end simple_part
    
    /**
     * Implements term -&gt; factor | factor mulop factor
     */
    public ExpressionNode term(){
        System.out.println("In term");
        
        ExpressionNode exp = factor();
        //While there is another mulop mulitplication create new OperationNode
        while( currentToken == TokenType.MULTIPLY ||
               currentToken == TokenType.DIVIDE ||
               currentToken == TokenType.DIV ||
               currentToken == TokenType.MOD ||
               currentToken == TokenType.AND ){
           
            ExpressionNode left = exp;
            OperationNode op = mulop();
            ExpressionNode right = factor();
            op.setLeft(left);
            op.setRight(right);            
            exp = op;
        }
        //If no operation return the Value or VariableNode
        return exp;
    }//end term
    
     /**
     * The mulop function matches the currentToken to one of the mulop's
     * ( *, /, mod, div, and ) and then creates an OperationNode with that
     * operation
     * @return An OperationNode representing *, /, mod, DIV, AND
     */
    public OperationNode mulop(){
        System.out.println("In mulop");
        OperationNode op = new OperationNode(currentToken);
        //Create the operation node to be returned
        if ( currentToken == TokenType.MULTIPLY) {
            match(TokenType.MULTIPLY);
        }
        else if (currentToken == TokenType.DIVIDE) {
            match(TokenType.DIVIDE);
        }
        else if (currentToken == TokenType.MOD) {
            match(TokenType.MOD);
        }
        else if (currentToken == TokenType.DIV) {
            match(TokenType.DIV);
        }
        else if (currentToken == TokenType.AND) {
            match(TokenType.AND);
        }
        else{
            System.out.println("Error in mulop " +currentToken+" not expected.");
        }
        return op;
    }//end mulop   
    
    /**
     * Implements factor -&gt; id | id[expression] | id(expression_list) | num |
     *                      (expression) | not factor
     */
    public ExpressionNode factor(){
        System.out.println("In factor");
        //If ID or ID[expression] or ID(expression_list) then match accordingly
        if( currentToken == TokenType.ID){
            VariableNode var = new VariableNode( scanner.getLexeme() );
            match(TokenType.ID);
            //If id[expression] match brackets and go to expression
            if( currentToken == TokenType.L_BRACKET){
                match(TokenType.L_BRACKET);
                expression();
                match(TokenType.R_BRACKET);
            }//If id(expression) match parenthesis and go to expression_list
            else if ( currentToken == TokenType.L_PARENTHESES){
                match(TokenType.L_PARENTHESES);
                expression_list();
                match(TokenType.R_PARENTHESES);
            }
            return var;
        }//If num then match the number
        else if( currentToken == TokenType.NUM){
            ValueNode value = new ValueNode( scanner.getLexeme() );
            match(TokenType.NUM);
            return value;
        }//If (expression) match parenthesis and go to expression
        else if( currentToken == TokenType.L_PARENTHESES){
            match(TokenType.L_PARENTHESES);
            ExpressionNode exp = expression();
            match(TokenType.R_PARENTHESES);
            return exp;
        }//if not match and make recursive call to factor
        else if( currentToken == TokenType.NOT){
            match(TokenType.NOT);
            factor();
            return null;
        }//For anything else go to error
        else{
            error();
            return null;
        }
    }//end factor
    
    /**
     * Implements -&gt; expression | expression, expression_list
     */
    public void expression_list(){
        System.out.println("In expression_list");
        expression();
        //If theres a comma after expression call expression again
        if (currentToken == TokenType.COMMA){
            match(TokenType.COMMA);
            expression_list();
        }
    }//end expression_list
    
    /**
     * Implements sign - &gt; + | -
     */
    public void sign(){
        System.out.println("In sign");
        if ( currentToken == TokenType.PLUS)
            match(TokenType.PLUS);
        else
            match(TokenType.MINUS);
    }//end sign
    
    /**
     * The error function displays the line of code where an error was found 
     * and the string that is related to the error. It then exits the program
     * with parameter 1
     */
    public void error() {
        System.out.println("error: Line " + scanner.getCount()+" No match found"
                + " for: " + scanner.getLexeme() );
        System.exit(1);
    }//end error

}//end parser
