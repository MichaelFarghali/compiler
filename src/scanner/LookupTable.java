package scanner;



import java.util.Hashtable;

/**
 * The class LookUpTable is an extension of the built in Hashtable.
 * It maps the keywords and symbols my mini pascal language will recognize to
 * their respective token types found in the enum TokenType
 * @author Michael Farghali
 */
public class LookupTable extends Hashtable<String,TokenType> {
    
    public LookupTable()  {
        
        // Keywords
        this.put("program", TokenType.PROGRAM);
        this.put("id", TokenType.ID);
        this.put("div", TokenType.DIV);
        this.put("mod", TokenType.MOD);
        this.put("and", TokenType.AND);
        this.put("var", TokenType.VAR);
        this.put("array", TokenType.ARRAY);
        this.put("integer", TokenType.INTEGER);
        this.put("real", TokenType.REAL);
        this.put("function", TokenType.FUNCTION);
        this.put("procedure", TokenType.PROCEDURE);
        this.put("begin", TokenType.BEGIN);
        this.put("end", TokenType.END);
        this.put("if", TokenType.IF);
        this.put("then", TokenType.THEN);
        this.put("else", TokenType.ELSE);
        this.put("do", TokenType.DO);
        this.put("while", TokenType.WHILE);
        this.put("read", TokenType.READ);
        this.put("write", TokenType.WRITE);
        this.put("or", TokenType.OR);
        // Symbols
        this.put( "+", TokenType.PLUS);
        this.put( "-", TokenType.MINUS);        
        this.put( ">", TokenType.GREATER_THAN);
        this.put( ">=", TokenType.GREATER_THAN_EQUALS);
        this.put( "<", TokenType.LESS_THAN);
        this.put( "<=", TokenType.LESS_THAN_EQUALS);
        this.put( "<>", TokenType.NOT_EQUAL);
        this.put( ":", TokenType.COLON);
        this.put( ":=", TokenType.ASSIGN);
        this.put( ".", TokenType.PERIOD);
        this.put( ",", TokenType.COMMA);
        this.put( ";", TokenType.SEMICOLON);
        this.put( "(", TokenType.L_PARENTHESES);
        this.put( ")", TokenType.R_PARENTHESES);
        this.put( "[", TokenType.L_BRACKET);
        this.put( "]", TokenType.R_BRACKET);
        this.put( "*", TokenType.MULTIPLY);
        this.put( "/", TokenType.DIVIDE);
        this.put( "{", TokenType.R_CURLY_BRACE);
        this.put( "}", TokenType.L_CURLY_BRACE);
        this.put( "=", TokenType.EQUALS);
               
        
    }
}
