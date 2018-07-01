import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.rules.Timeout;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class Aufgabe3Test {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog();

    @Test
    public void testFeld() {
        Spielfeld f = new Spielfeld();
        f = f.zug(1,3);
        f = f.zug(2,2);
        f = f.zug(1,2);
        f = f.zug(2,3);
        assertEquals(1,f.feld(1,2));
        assertEquals(2,f.feld(1,3));
        assertEquals(0,f.feld(1,4));
    }

    @Test
    public void testSpielstand() throws Exception {
        String[] testValues = new String[]{
            "|       |",
            "|       |",
            "|       |",
            "|       |",
            "|  xo   |",
            "|  ox   |",
            "+-------+"};
        StringBuilder testString = new StringBuilder();
        for(String i: testValues) {
            testString.append(i).append(System.lineSeparator());
        }
        Spielfeld f = new Spielfeld();
        f = f.zug(1,3);
        f = f.zug(2,2);
        f = f.zug(1,2);
        f = f.zug(2,3);
        f.spielstand();
        assertEquals(testString.toString(), log.getLog());
    }

    @Test
    public void testSieg() throws Exception {
        Spielfeld f = new Spielfeld();
        // waagrecht
        f = f.zug(1,2);
        f = f.zug(2,2);
        f = f.zug(1,3);
        f = f.zug(2,3);
        f = f.zug(1,4);
        f = f.zug(2,4);
        f = f.zug(1,5);
        assertEquals(true,f.sieg(1));
        assertEquals(false,f.sieg(2));
        f = f.zug(2,5);
        assertEquals(true,f.sieg(2));

        // senkrecht
        f = new Spielfeld();
        f = f.zug(1,2);
        f = f.zug(2,2);
        f = f.zug(2,2);
        f = f.zug(2,2);
        f = f.zug(2,2);
        assertEquals(false,f.sieg(1));
        assertEquals(true,f.sieg(2));
        
        // diagonal /
        f = new Spielfeld();
        f = f.zug(1,2);
        f = f.zug(2,3);
        f = f.zug(1,3);
        f = f.zug(2,4);
        f = f.zug(1,4);
        f = f.zug(2,5);
        f = f.zug(1,4);
        f = f.zug(2,5);
        f = f.zug(1,5);
        f = f.zug(1,5);
        assertEquals(false,f.sieg(2));
        assertEquals(true,f.sieg(1));

        // diagonal \
        f = new Spielfeld();
        f = f.zug(1,6);
        f = f.zug(2,5);
        f = f.zug(1,5);
        f = f.zug(2,4);
        f = f.zug(1,4);
        f = f.zug(2,3);
        f = f.zug(1,4);
        f = f.zug(2,3);
        f = f.zug(1,3);
        f = f.zug(1,3);
        assertEquals(false,f.sieg(2));
        assertEquals(true,f.sieg(1));
    }

    @Test
    public void testWert1() throws Exception {
        Spielfeld f = new Spielfeld();
        f = f.zug(2,2);
        f = f.zug(1,3);
        f = f.zug(2,4);
        f = f.zug(1,5);
        f = f.zug(1,6);
        f = f.zug(1,2);
        f = f.zug(2,3);
        f = f.zug(2,2);
        f = f.zug(1,6);
        assertEquals(4,f.wert1(1));
        assertEquals(103,f.wert1(2));
    }
    
    @Test
    public void testWert() throws Exception {
        Spielfeld f = new Spielfeld();
        f = f.zug(2,2);
        f = f.zug(1,3);
        f = f.zug(2,4);
        f = f.zug(1,5);
        f = f.zug(1,6);
        f = f.zug(1,2);
        f = f.zug(2,3);
        f = f.zug(2,2);
        f = f.zug(1,6);
        assertEquals(-99,f.wert(1));
        assertEquals(99,f.wert(2));
    }
    
    @Test
    public void testNegamax() throws Exception {
        Spielfeld f = new Spielfeld();
        f = f.zug(2,2);
        f = f.zug(1,3);
        f = f.zug(2,4);
        f = f.zug(1,5);
        f = f.zug(1,6);
        f = f.zug(1,2);
        f = f.zug(2,3);
        f = f.zug(2,2);
        assertEquals(-101,f.negamax(1,0));
        assertEquals( 101,f.negamax(2,0));
        assertEquals( -99,f.negamax(1,1));
        assertEquals( 103,f.negamax(2,1));
        assertEquals(-101,f.negamax(1,2));
        assertEquals( 101,f.negamax(2,2));
    }
    
    @Test
    public void testBester() throws Exception {
        Spielfeld f = new Spielfeld();
        f = f.zug(2,2);
        f = f.zug(1,3);
        f = f.zug(2,4);
        f = f.zug(1,5);
        f = f.zug(1,6);
        f = f.zug(1,2);
        f = f.zug(2,3);
        f = f.zug(2,2);
        assertEquals(3,f.bester(1,2));
        assertEquals(4,f.bester(2,2));
    }
}

