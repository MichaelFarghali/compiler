
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
        // Check that it's not already in the function
        if(table.containsKey(name))
            return false;
        
        DataStruct data = new DataStruct();
        data.lexeme = name;
        data.kind = IdKind.VAR_NAME;
        table.put(name, data);
      
      return true;
    }
    public void addFunctionName()
    {
        //stub
    }
    public void addProcName()
    {
        //
    }
    private class DataStruct {
        
        private String lexeme;
        private IdKind kind;
        private boolean array;
        private int startIndex;
        private int endIndex;
        private IdKind integerType;
        
    }//end DataStruct
    
    private enum IdKind {
    
        PROGRAM_NAME,
        FUNC_NAME,
        PROC_NAME,
        VAR_NAME,
        ARRAY_NAME,
        
    }
    
}
