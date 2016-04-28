
package symbol.table;


import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
/**
 * The Symbol Table class creates a Hashtable which accepts objects of type
 * (String, DataStruct). The IDs (Program ID, variable ID,..etc.) are the 
 * keywords and the inner class DataStruct is the value. For every ID type Program,
 * function, procedure, variable, and array there is a add function and type
 * check function. 
 * @author Michael Farghali
 */
public class SymbolTable {
    
    Hashtable<String, DataStruct> table;
    
    public SymbolTable(){
        table = new Hashtable<String, DataStruct>();
    }
    /**
     * The addProgramName method checks if the ID name already exists as a Program
     * ID if so it returns false. If it doesn't already exist then it is added
     * to the Hashtable along with it's corresponding data from DataStuct
     * @param name The name of the Program id
     * @return boolean True if add successful, False otherwise
     */
    public boolean addProgramName(String name)
    {
        // Check that the program name is not already in table
        if(this.table.containsKey(name))
            return false;
        //If not being used create instance of DataStruct and add data
        DataStruct data = new DataStruct();
        data.lexeme = name;
        data.kind = IdKind.PROGRAM;
        this.table.put(data.lexeme, data);
      
        return true;
    }
    /**
     * The addFunctionName method checks if the ID name already exists as a 
     * function ID if so it returns false. If it doesn't already exist then it 
     * is added to the Hashtable along with it's corresponding data from DataStuct
     * @param name The name of the function id
     * @return boolean True if add successful, False otherwise
     */
    public boolean addFunctionName(String name)
    {
        //If the name already exists return false
        if(table.containsKey(name))
            return false;
        //Else create instance of DataStruct with the data and put function
        //name into Hashtable
        DataStruct data = new DataStruct();
        data.lexeme = name;
        data.kind = IdKind.FUNC;
        table.put(data.lexeme, data);
        return true;
    }
    /**
     * The addProcName method checks if the ID name already exists as a Proc
     * ID if so it returns false. If it doesn't already exist then it is added
     * to the Hashtable along with it's corresponding data from DataStuct
     * @param name the name of the procedure name
     * @return boolean True if add successful, False otherwise
     */
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
    /**
     * The addVarName method checks if the ID name already exists as a variable
     * ID if so it returns false. If it doesn't already exist then it is added
     * to the Hashtable along with it's corresponding data from DataStuct
     * @param name The name of the variable id
     * @return boolean True if add successful, False otherwise
     */
    public boolean addVarName(String name, String type){
        //Check if variable name alredy exists, if so return false
        if(this.table.containsKey(name))
            return false;
        //If doesn't exist, create instance of DataStruct with data then add
        //name and data to Hashtable
        DataStruct data = new DataStruct();
        data.lexeme = name;
        data.type = type;
        data.kind = IdKind.VAR;
        this.table.put(data.lexeme, data);
        
        return true;
    }
    /**
     * The addArrayName method checks if the ID name already exists as an array
     * ID if so it returns false. If it doesn't already exist then it is added
     * to the Hashtable along with it's corresponding data from DataStuct
     * @param name The name of the Array id 
     * @return boolean True if add successful, False otherwise
     */
    public boolean addArrayName(String name, String type){
        //Check if array name alredy exists, if so return false
        if(table.containsKey(name))
            return false;
        //If doesn't exist, create instance of DataStruct with data then add
        //name and data to Hashtable
        DataStruct data = new DataStruct();
        data.lexeme = name;
        data.type = type;
        data.kind = IdKind.ARRAY;
        table.put(data.lexeme, data);
        
        return true;
    }
    /**
     * The isProgName checks if the kind of ID matches enum type PROGRAM.
     * @param name The program id name that is being checked.
     * @return True if data kind matches IdKind PROGRAM
     */
    public boolean isProgName(String name){
        // Create instance of DataStruct using records from Hashtable
        DataStruct data = (DataStruct) table.get(name);
        // If no data found with associated name then return false
        if(data == null)
            return false;
        // If the data type matches Program return true
        if(data.kind == IdKind.PROGRAM)
            return true;
        return false; // All else return false
    }
    /**
     * The isFuncName checks if the kind of ID matches enum type FUNC.
     * @param name The function id name that is being checked if it already exists
     * @return True if data kind matches IdKind FUNC
     */
    public boolean isFuncName(String name){
        // Create instance of DataStruct using records from Hashtable
        DataStruct data = (DataStruct) table.get(name);
        // If no data found with associated name then return false
        if(data == null)
            return false;
        // If the data type matches Func return true
        if(data.kind == IdKind.FUNC)
            return true;
        return false; // All else return false
    }
    /**
     * The isProcName checks if the kind of ID matches enum type PROC.
     * @param name The procedure id name being checked. 
     * @return True if data kind matches IdKind PROC
     */
    public boolean isProcName(String name){
        // Create instance of DataStruct using records from Hashtable
        DataStruct data = (DataStruct) table.get(name);
        // If no data found with associated name then return false
        if(data == null)
            return false;
        // If the data type matches type PROC return true
        if(data.kind == IdKind.PROC)
            return true;
        return false; // All else return false
    }
    /**
     * The isVarName checks if the kind of ID matches enum type VAR.
     * @param name The variable id name being checked in the Hashtable
     * @return True if data kind matches IdKind VAR
     */
    public boolean isVarName(String name){
        // Create instance of DataStruct using records from Hashtable
        DataStruct data = (DataStruct) table.get(name);
        // If no data found with associated name then return false
        if(data == null)
            return false;
        // If the data type matches VAR return true
        if(data.kind == IdKind.VAR)
            return true;
        return false; // All else return false
    }
    /**
     * The isArrayName checks if the kind of ID matches enum type ARRAY.
     * @param name The array id name being checked for in the Hashtable
     * @return True if data kind matches IdKind ARRAY
     */
    public boolean isArrayName(String name){
        // Create instance of DataStruct using records from Hashtable
        DataStruct data = (DataStruct) table.get(name);
        // If no data found with associated name then return false
        if(data == null)
            return false;
        // If the data type matches ARRAY return true
        if(data.kind == IdKind.ARRAY)
            return true;
        return false; // All else return false
    }
   
   
    public String myToString()
    {
        String ans = String.format("%-20s %-20s %-15s %-15s %-15s %15s", "Name", 
                "Kind", "Type", "Array", "Start Index", "End Index") + "\n";
        Set<String> keys = table.keySet();
        for (String key: keys)
        {
            ans += String.format("%-20s %-20s %-15s %-15s %-15s", 
                    key, table.get(key).kind, table.get(key).type, table.get(key).array, 
                    table.get(key).startIndex, table.get(key).endIndex) + "\n";
            
        }
        return ans;
    }
    /**
     * The DataStruct inner class holds the attributes of the different id types.
     * It includes the name of the ID, the kind of ID of type enum, a boolean
     * whether its an array or not, and if so the start and ending index of the 
     * array. 
     */
    private class DataStruct {
        
        private String lexeme;
        private String type;
        private IdKind kind;
        private boolean array;
        private int startIndex;
        private int endIndex;
                
    }//end DataStruct
    /**
     * IdKind lists the different types of ID's in our mini pascal. They include
     * PROGRAM, FUNC for function, PROC for procedure, VAR for variable, and ARRAY. 
     */
    private enum IdKind {
    
        PROGRAM,
        FUNC,
        PROC,
        VAR,
        ARRAY,        
    }//end IdKind
    
}//end SymbolTable 
