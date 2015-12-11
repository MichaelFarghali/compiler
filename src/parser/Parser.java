package parser;

import scanner.Scanner;
import scanner.TokenType;
import java.io.File;

/**
 * The Parser class will eventually process a source file and create a parse
 * tree or reject the source as invalid using top down recursive descent
 * parsing.
 *
 * @Michael Farghali
 */
public class Parser {

    private Scanner scanner;  // The Scanner
    private TokenType currentToken;  // The TokenType variable 

    public Parser(String filename) {
        // Create a file variable and an instance of scanner with specified file
        File input = new File(filename);
        scanner = new Scanner(input);
        // Load in the first token as a lookahead token and assign to current
        scanner.nextToken();
        currentToken = scanner.getToken();
    }

    public void match(TokenType expectedToken) {
        System.out.println("match " + expectedToken + " with current " + currentToken + ":" + scanner.getLexeme());
        if (currentToken == expectedToken) {
            boolean scanResult = scanner.nextToken();
            if (scanResult) {
                currentToken = scanner.getToken();
            } else {
                System.out.println("No Token Available");
                String lexeme = scanner.getLexeme();
                if (lexeme == null) {
                    System.out.println("End of file");
                } else {
                    System.out.println("Scanner barfed on " + lexeme);
                }
            }
            //System.out.println("   next token is now " + currentToken);
            //System.out.println("   next attri is now " + scanner.getAttribute());
        } else {
            error();  // We don't match!
        }
    }

    /**
     * Implements program -> program id ; declarations subprogram_declarations
     * compound_statement .
     */
    public void program() {
        System.out.println("program");
        match(TokenType.PROGRAM);
        match(TokenType.ID);
        match(TokenType.SEMICOLON);
        declarations();
        subprogram_declarations();
        compound_statement();
        System.out.println("Current Token is " +currentToken);
        match(TokenType.PERIOD);
        //TODO
        //Check if the filestream is empty
    }

    public void identifier_list() {
        match(TokenType.ID);
        while (currentToken == TokenType.COMMA) {
            match(TokenType.COMMA);
            match(TokenType.ID);
        }
    }

    /**
     * Implements part of declarations that is declarations -> lambda.
     */
    public void declarations() {
        //TODO CORRRECT mulitiple variable type issue
        System.out.println("declarations");
        if (currentToken == TokenType.VAR) {
            match(TokenType.VAR);
            identifier_list();
            match(TokenType.COLON);
            type();
            match(TokenType.SEMICOLON);
            declarations();
        }

    }

    /**
     * Implements type -> standard_type
     */
    public void type() {
        standard_type();
    }

    public void standard_type() {
        if (currentToken == TokenType.INTEGER) {
            match(TokenType.INTEGER);
        } else {
            match(TokenType.REAL);
        }
    }

    /**
     * Implements the part of subprogram_declarations that is
     * subprogram_declarations -> lambda.
     */
    public void subprogram_declarations() {
        System.out.println("subprogram_declarations" + currentToken);
            if ( currentToken == TokenType.FUNCTION ||
                 currentToken == TokenType.PROCEDURE){
                
                subprogram_declaration();
                match(TokenType.SEMICOLON);
                subprogram_declarations();
            }
           
    }

    public void subprogram_declaration() {
        System.out.println("subprogram_declaration");
        subprogram_head();
        declarations();
        subprogram_declarations();
        compound_statement();
    }

    public void subprogram_head() {
        System.out.println("subprogram_head");
        match(TokenType.FUNCTION);
        match(TokenType.ID);
        arguments();
        match(TokenType.COLON);
        standard_type();
        match(TokenType.SEMICOLON);
    }
    
    public void arguments() {
        System.out.println("arguements");
        if ( currentToken == TokenType.L_PARENTHESES){
            match(TokenType.L_PARENTHESES);
            parameter_list();
            match(TokenType.R_PARENTHESES);
        }
    }
    /**
     * Implements EBNF production rule 
     * parameter_list -> identifier_list : type { ; identifier_list : type}
     */
    public void parameter_list(){
        identifier_list();
        match(TokenType.COLON);
        type();
        while (currentToken == TokenType.SEMICOLON){
            match(TokenType.SEMICOLON);
            parameter_list();
        }
    }

    /**
     * Implements compound_statement -> begin optional_statements end
     */
    public void compound_statement() {
        System.out.println("compound_statement");
        match(TokenType.BEGIN);
        optional_statements();
        match(TokenType.END);
    }

    /**
     * Implements the part of optional_statements that is optional_statements ->
     * lambda.
     */
    public void optional_statements() {
        System.out.println("optional_statements");
        if (currentToken == TokenType.ID){
            statement_list();
        }
    }
    
    public void statement_list(){
        System.out.println("optional_statements");
        statement();
        while (currentToken == TokenType.SEMICOLON){
            match(TokenType.SEMICOLON);
            statement_list();
        }
    }
    
    public void statement(){
        if(currentToken == TokenType.ID) {
            variable();
            match(TokenType.ASSIGN);
            expression();
        }
        else if (currentToken == TokenType.BEGIN){
            System.out.println("in statement() Token:" );
            compound_statement();
        }
        else if (currentToken == TokenType.IF){
            match(TokenType.IF);
            expression();
            match(TokenType.THEN);
            statement();
            match(TokenType.ELSE);
            statement();
        }
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
        }
        else
            error();
    }
    
    public void variable(){
        match(TokenType.ID);
        if (currentToken == TokenType.L_BRACKET){
            expression();
            match(TokenType.R_BRACKET);
        }
    }
    
    public void expression(){
        //STUB
    }

    /**
     * Handles an error. Prints out the existence of an error and then exits.
     */
    public void error() {
        System.out.println("error: Line " + scanner.getCount() + " No match found "
                + "for: " + scanner.getLexeme());
        System.exit(1);
    }

}//end parser
