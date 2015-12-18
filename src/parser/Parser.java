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
        match(TokenType.PERIOD);
        if( currentToken != null){
            System.out.println("File not empty");
            error();
        }
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
        System.out.println("In type: currentToken = " + currentToken);
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
        System.out.println("subprogram_declarations");
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
        if ( currentToken == TokenType.ID ||
             currentToken == TokenType.IF||
             currentToken == TokenType.WHILE ||
             currentToken == TokenType.READ ||
             currentToken == TokenType.WRITE){
            
            statement_list();
        }
    }
    
    public void statement_list(){
        System.out.println("statement_list");
        statement();
        while (currentToken == TokenType.SEMICOLON){
            match(TokenType.SEMICOLON);
            statement_list();
        }
    }
    
    public void statement(){
        System.out.println("En statement");
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
        System.out.println("En Variable");
        match(TokenType.ID);
        if (currentToken == TokenType.L_BRACKET){
            expression();
            match(TokenType.R_BRACKET);
        }
    }
    
    public void expression(){
        System.out.println("En expression");
        simple_expression();
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
        
    }
    
    public void simple_expression(){
        System.out.println("En simple expression");
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
    }
    
    public void simple_part(){
        System.out.println("En simple_part");
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
    }
    
    public void term(){
        System.out.println("En term");
        factor();
        term_part();
    }
    
    public void term_part(){
        System.out.println("En term_part");
        while( currentToken == TokenType.MULTIPLY ||
               currentToken == TokenType.DIVIDE ||
               currentToken == TokenType.DIV ||
               currentToken == TokenType.MOD ||
               currentToken == TokenType.AND ) {
            
            mulop();
            factor();
            term_part();
        }          
    }
    
    public void factor(){
        System.out.println("In Factor: ");
        if( currentToken == TokenType.ID){
            match(TokenType.ID);
            if( currentToken == TokenType.L_BRACKET){
                match(TokenType.L_BRACKET);
                expression();
                match(TokenType.R_BRACKET);
            }
            else if ( currentToken == TokenType.L_PARENTHESES){
                match(TokenType.L_PARENTHESES);
                expression_list();
                match(TokenType.R_PARENTHESES);
            }
        }
        else if( currentToken == TokenType.NUM){
            match(TokenType.NUM);
        }
        else if( currentToken == TokenType.L_PARENTHESES){
            match(TokenType.L_PARENTHESES);
            expression();
            match(TokenType.R_PARENTHESES);
        }
        else if( currentToken == TokenType.NOT){
            match(TokenType.NOT);
            factor();
        }
        else
            error();
    }
    
    public void expression_list(){
        System.out.println("in expression list ");
        expression();
        if (currentToken == TokenType.COMMA){
            match(TokenType.COMMA);
            expression_list();
        }
    }
    
    public void sign(){
        if ( currentToken == TokenType.PLUS)
            match(TokenType.PLUS);
        else
            match(TokenType.MINUS);
    }    
    
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
    }

    /**
     * Handles an error. Prints out the existence of an error and then exits.
     */
    public void error() {
        System.out.println("error: Line " + scanner.getCount()+" No match found"
                + " for: " + scanner.getLexeme() );
        System.exit(1);
    }

}//end parser
