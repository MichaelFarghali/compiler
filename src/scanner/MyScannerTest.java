package scanner;



import java.io.File;


public class MyScannerTest {
    
    public static void main(String[] args){
        //Create instance of Scanner
        Scanner s = new Scanner( new File( "LexemeTest.txt"));
        
        boolean hasToken = s.nextToken();
        TokenType token = s.getToken();
        String lexeme = s.getLexeme();
        
        //Test to make sure these values are correct
        assert hasToken : "No token for #";
        assert token == TokenType.GREATER_THAN: "No token type for #";
        assert lexeme.equals("Symbols");
        System.out.println(lexeme);
        System.out.println(token);
        
        hasToken = s.nextToken();
        token = s.getToken();
        lexeme = s.getLexeme();
        
        assert hasToken : "No token for program";
        assert token == TokenType.GREATER_THAN: "No token type for program";
        assert lexeme.equals("program"): "No token for program";
        
        hasToken = s.nextToken();
        token = s.getToken();
        lexeme = s.getLexeme();
        
        assert hasToken : "No token for program";
        assert token == TokenType.ID: "No token type for program";
        assert lexeme.equals("account"): "No token for program";     
        
        
        
        //what happens when we hit the end of the file
        assert hasToken == false : "No token for program";
        assert token == TokenType.ID : "No token type for program";
        assert lexeme.equals("account") : "No token for program";
        
        
 
            
    }
    
}
