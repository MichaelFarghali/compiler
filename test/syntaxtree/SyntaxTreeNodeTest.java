
package syntaxtree;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author user
 */
public class SyntaxTreeNodeTest {
    
    public SyntaxTreeNodeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of indentedToString method, of class SyntaxTreeNode.
     */
    @Test
    public void testIndentedToString() {
        System.out.println("indentedToString");
        int level = 0;
        SyntaxTreeNode instance = new SyntaxTreeNodeImpl();
        String expResult = "";
        String result = instance.indentedToString(level);
        assertEquals(expResult, result);
        
    }

    public class SyntaxTreeNodeImpl extends SyntaxTreeNode {
    }
    
}
