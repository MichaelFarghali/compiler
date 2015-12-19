package parser;

import scanner.Scanner;
import scanner.TokenType;
import java.io.File;

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
    /**
     * The Parser constructor creates a File variable which it passes to a new
     * instance of the Scanner class. It then loads the first token to be 
     * processed. 
     * @param filename The name of the input file to be parsed.
     */
    public Parser(String filename) {
        // Create a file variable and an instance of scanner with specified file
        File input = new File(filename);
        scanner = new Scanner(input);
        // Load in the first token as a lookahead token and assign to current
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
                    System.out.println("End of file");
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
     */
    public void program() {
        match(TokenType.PROGRAM);
        match(TokenType.ID);
        match(TokenType.SEMICOLON);
        declarations();
        subprogram_declarations();
        compound_statement();
        match(TokenType.PERIOD);
        //If there's still data in the file stream go to error
        if( currentToken != null){
            System.out.println("File not empty");
            error();
        }
    }//end program
    
    /**
     * Implements identifier_list -&gt; id | id, identifier_list
     */
    public void identifier_list() {
        match(TokenType.ID);
        while (currentToken == TokenType.COMMA) {
            match(TokenType.COMMA);
            match(TokenType.ID);
        }
    }//end identifier_list

    /**
     * Implements declarations -&gt; var identifier_list : type ; declarations |
     *                            lambda
     */
    public void declarations() {
        //If there is a variable declaration process it. Else do nothing
        if (currentToken == TokenType.VAR) {
            match(TokenType.VAR);
            identifier_list();
            match(TokenType.COLON);
            type();
            match(TokenType.SEMICOLON);
            declarations();
        }
    }//end declarations

    /**
     * Implements type -&gt; standard_type |
     *                    array[num : num] of standard_type
     */
    public void type() {
        // Check if there is an array being declared
        if ( currentToken == TokenType.ARRAY){
            match(TokenType.ARRAY);
            match(TokenType.L_BRACKET);
            match(TokenType.NUM);
            match(TokenType.COLON);
            match(TokenType.NUM);
            match(TokenType.R_BRACKET);
            match(TokenType.OF);
            standard_type();
        }
        else
            standard_type();        
    }//end type
    
    /**
     * Implements standard_type -&gt; integer | real
     */
    public void standard_type() {
        if (currentToken == TokenType.INTEGER) {
            match(TokenType.INTEGER);
        } else {
            match(TokenType.REAL);
        }
    }//end standard_type

    /**
     * Implements subprogram_declarations -&gt; subprogram_declaration;
     *                                       subprogram_declarations | lambda
     */
    public void subprogram_declarations() {
        // If a function or procedure is declared go to subprogram_declaration
            if ( currentToken == TokenType.FUNCTION ||
                 currentToken == TokenType.PROCEDURE){
                
                subprogram_declaration();
                match(TokenType.SEMICOLON);
                subprogram_declarations();
            }           
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
        match(TokenType.FUNCTION);
        match(TokenType.ID);
        arguments();
        match(TokenType.COLON);
        standard_type();
        match(TokenType.SEMICOLON);
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
        // If there's a semicolon after a varaible eat semicolon and get next
        // variable
        while (currentToken == TokenType.SEMICOLON){
            match(TokenType.SEMICOLON);
            parameter_list();
        }
    }//end parameter_list

    /**
     * Implements compound_statement -&gt; begin optional_statements end
     */
    public void compound_statement() {
        match(TokenType.BEGIN);
        optional_statements();
        match(TokenType.END);
    }//end compound_statement

    /**
     * Implements optional_statements -&gt; statement_list | lambda
     * lambda.
     */
    public void optional_statements() {
        //If currentToken is a variable id, if, while, read, or write statement
        //go to statement_list
        if ( currentToken == TokenType.ID ||
             currentToken == TokenType.IF||
             currentToken == TokenType.WHILE ||
             currentToken == TokenType.READ ||
             currentToken == TokenType.WRITE){
            
            statement_list();
        }
    }//end optional_statements
    
    /**
     * Implements statement_list -&gt; statement | statement ; statement_list
     */
    public void statement_list(){
        statement();
        //If there's a semicolon eat token and get next statement
        while (currentToken == TokenType.SEMICOLON){
            match(TokenType.SEMICOLON);
            statement_list();
        }
    }//end statement_list
    
    /**
     * Implements statement -&gt; variable assignop expression |
     *                         procedure_statement (not currently working) |
     *                         compound_statement |
     *                         if expression then statement else statement |
     *                         while expression do statement |
     *                         read | write (read, write currently treated as id)
     */
    public void statement(){
        //If currentToken is variable process match variable statement
        if(currentToken == TokenType.ID) {
            variable();
            match(TokenType.ASSIGN);
            expression();
        }
        //if currentToken is begin go to compound_statement
        else if (currentToken == TokenType.BEGIN){
            compound_statement();
        }//if currentToken is IF process if then else statement block
        else if (currentToken == TokenType.IF){
            match(TokenType.IF);
            expression();
            match(TokenType.THEN);
            statement();
            match(TokenType.ELSE);
            statement();
        }//if currentToken is while process while do statements
        else if (currentToken == TokenType.WHILE){
            match(TokenType.WHILE);
            expression();
            match(TokenType.DO);
            statement();
        }
        else if (currentToken == TokenType.READ){
            match(TokenType.READ); // Treating as keyword for now
        }
        else if (currentToken == TokenType.WRITE){
            match(TokenType.WRITE); // Treating as keyword for now
        }//Go to error for anything else
        else
            error();
    }//end statement
    
    /**
     * Implements variable -&gt; id | id [expression]
     */
    public void variable(){
        match(TokenType.ID);
        if (currentToken == TokenType.L_BRACKET){
            expression();
            match(TokenType.R_BRACKET);
        }
    }//end variable
    
    /**
     * Implements expression -&gt; simple_expression | 
     *                          simple_expression relop simple_expression
     */
    public void expression(){
        simple_expression(); 
        //Check for relop's <, >, <=, >=, <>, =. If found match and get next
        //simple expression
        if ( currentToken == TokenType.EQUALS){
            match(TokenType.EQUALS);
            simple_expression();
        }
        else if ( currentToken == TokenType.NOT_EQUAL){
            match(TokenType.NOT_EQUAL);
            simple_expression();
        }
        else if ( currentToken == TokenType.LESS_THAN){
            match(TokenType.LESS_THAN);
            simple_expression();
        }
        else if ( currentToken == TokenType.LESS_THAN_EQUALS){
            match(TokenType.LESS_THAN_EQUALS);
            simple_expression();
        }
        else if ( currentToken == TokenType.GREATER_THAN_EQUALS){
            match(TokenType.GREATER_THAN_EQUALS);
            simple_expression();
        }
        else if ( currentToken == TokenType.GREATER_THAN){
            match(TokenType.GREATER_THAN);
            simple_expression();
        }                   
    }//end expression
    
    /**
     * Implements simple_expression -&gt; term simple_part | lambda
     */
    public void simple_expression(){
        //If the number is signed go to sign then call term, simple_part
        if( currentToken == TokenType.PLUS ||
            currentToken == TokenType.MINUS){
            
            sign();
            term();
            simple_part();
        }
        else {
            term();
            simple_part();
        }        
    }//end simple_expression
    
    /**
     * Implements simple_part -&gt; addop term simple_part | lambda
     */
    public void simple_part(){
        //Match one of the addops +, -, OR 
        if (currentToken == TokenType.PLUS) {
            match(TokenType.PLUS);
            term();
            simple_part();
        }
        else if (currentToken == TokenType.MINUS) {
            match(TokenType.MINUS);
            term();
            simple_part();
        }
        else if (currentToken == TokenType.OR) {
            match(TokenType.OR);
            term();
            simple_part();
        }
    }//end simple_part
    
    /**
     * Implements term -&gt; factor term_part
     */
    public void term(){
        factor();
        term_part();
    }//end term
    
    /**
     * Implements term_part -&gt; mulop factor term_part | lambda
     */
    public void term_part(){
        //While there is a mulop ( *,/,div, mod, and) go to mulop then factor
        //and term_part
        while( currentToken == TokenType.MULTIPLY ||
               currentToken == TokenType.DIVIDE ||
               currentToken == TokenType.DIV ||
               currentToken == TokenType.MOD ||
               currentToken == TokenType.AND ) {
            
            mulop();
            factor();
            term_part();
        }          
    }//end term_part
    
    /**
     * Implements factor -&gt; id | id[expression] | id(expression_list) | num |
     *                      (expression) | not factor
     */
    public void factor(){
        //If ID or ID[expression] or ID(expression_list) then match accordingly
        if( currentToken == TokenType.ID){
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
        }//If num then match the number
        else if( currentToken == TokenType.NUM){
            match(TokenType.NUM);
        }//If (expression) match parenthesis and go to expression
        else if( currentToken == TokenType.L_PARENTHESES){
            match(TokenType.L_PARENTHESES);
            expression();
            match(TokenType.R_PARENTHESES);
        }//if not match and make recursive call to factor
        else if( currentToken == TokenType.NOT){
            match(TokenType.NOT);
            factor();
        }//For anything else go to error
        else
            error();
    }//end factor
    
    /**
     * Implements -&gt; expression | expression, expression_list
     */
    public void expression_list(){
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
        if ( currentToken == TokenType.PLUS)
            match(TokenType.PLUS);
        else
            match(TokenType.MINUS);
    }//end sign
    
    /**
     * The mulop function matches the currentToken to one of the mulop's
     * ( *, /, mod, div, and )
     */
    public void mulop(){
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
    }//end mulop

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
