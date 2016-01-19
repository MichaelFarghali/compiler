
package symbol.table;


import java.util.Hashtable;
/**
 *
 * @author Michael Farghali
 */
public class SymbolTable {
    
    Hashtable table = new Hashtable<String, DataStruct>();
    
    public boolean addProgramName(String name)
    {
        // Check that the program name is not already in table
        if(table.containsKey(name))
            return false;
        //If not being used create instance of DataStruct and add data
        DataStruct data = new DataStruct();
        data.lexeme = name;
        data.kind = IdKind.PROGRAM;
        table.put(data.lexeme, data);
      
      return true;
    }
    public boolean addFunctionName(String name)
    {
        if(table.containsKey(name))
            return false;
        DataStruct data = new DataStruct();
        data.lexeme = name;
        data.kind = IdKind.FUNC;
        table.put(data.lexeme, data);
        return true;
    }
    public boolean addProcName(String name)
    {
        if(table.containsKey(name))
            return false;
        DataStruct data = new DataStruct();
        data.lexeme = name;
        data.kind = IdKind.PROC;
        table.put(data.lexeme, data);
        
        return true;
    }
    
    public boolean addVarName(String name){
        if(table.containsKey(name))
            return false;
        DataStruct data = new DataStruct();
        data.lexeme = name;
        data.kind = IdKind.VAR;
        table.put(data.lexeme, data);
        
        return true;
    }
    public boolean addArrayName(String name){
        if(table.containsKey(name))
            return false;
        DataStruct data = new DataStruct();
        data.lexeme = name;
        data.kind = IdKind.ARRAY;
        table.put(data.lexeme, data);
        
        return true;
    }
    private class DataStruct {
        
        private String lexeme;
        private IdKind kind;
        private boolean array;
        private int startIndex;
        private int endIndex;
                
    }//end DataStruct
    
    private enum IdKind {
    
        PROGRAM,
        FUNC,
        PROC,
        VAR,
        ARRAY,        
    }
    
}
