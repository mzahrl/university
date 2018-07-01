import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.rules.Timeout;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static sun.nio.cs.Surrogate.is;

public class Aufgabe2Test {
    
    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);
    
    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog();
    
    @Test
    public void testReverseI() throws Exception {
        IntList il = new IntList();
        for (int i = 0; i < 10; i++) {
            il.add(i);
        }
        il.reverseI();
        assertEquals(9, il.first());
        il.add(20);
        il.reverseI();
        assertEquals(20, il.first());
    }
    
    @Test
    public void testReverseR() throws Exception {
        IntList il = new IntList();
        for (int i = 0; i < 10; i++) {
            il.add(i);
        }
        il.reverseR();
        assertEquals(9, il.first());
        il.add(20);
        il.reverseR();
        assertEquals(20, il.first());
    }
    
    
    @Test
    public void testInsert() throws Exception {
        IntList il = new IntList();
        for (int i = 1; i < 11; i++) {
            il.add(i);
        }
        il.add(15);
        il.add(20);
        assertEquals(true, il.insertIdx(0,1));
        assertEquals(true, il.insertIdx(12,3));
        assertEquals(false, il.insertIdx(18,56));
        assertEquals(false, il.insertIdx(25,0));
    }
    
    @Test
    public void testRemove() throws Exception {
        IntList il = new IntList();
        for (int i = 0; i < 10; i++) {
            il.add(i);
        }
        il.add(9);
        assertEquals(true, il.remove(3));
        assertEquals(true, il.remove(0));
        assertEquals(true, il.remove(9));
        assertEquals(false, il.remove(9));
        assertEquals(false, il.remove(10));
        assertEquals(false, il.remove(20));
    }
    
    @Test
    public void testAll() throws Exception {
        String[] testValues = new String[]{
                "[1->2->3->4->5->25->15->1->5-|]",
                "[5->1->15->25->5->4->3->2->1-|]",
                "[1->2->3->4->5->25->15->1->5-|]",
                "[0->1->1->2->3->4->4->5->2->25->15->1->18->23->5->5-|]",
                "[2->3->2->15->18->23-|]"
        };
        StringBuilder testString = new StringBuilder();
        for(String i: testValues) {
            testString.append(i).append(System.lineSeparator());
        }
        
        Aufgabe2.main(new String[]{});
        assertEquals(testString.toString(), log.getLog());
    }
}

