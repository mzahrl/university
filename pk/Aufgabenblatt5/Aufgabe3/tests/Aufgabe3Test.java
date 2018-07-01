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
    public void testWert1() throws Exception {
        int[][] f=Aufgabe3.spielfeld();
        f[0][2]=2;
        f[0][3]=1;
        f[0][4]=2;
        f[0][5]=1;
        f[0][6]=1;
        f[1][2]=1;
        f[1][3]=2;
        f[2][2]=2;
        f[1][6]=1;
        assertEquals(4,Aufgabe3.wert1(f,1));
        assertEquals(103,Aufgabe3.wert1(f,2));
    }
    
    @Test
    public void testWert() throws Exception {
        int[][] f=Aufgabe3.spielfeld();
        f[0][2]=2;
        f[0][3]=1;
        f[0][4]=2;
        f[0][5]=1;
        f[0][6]=1;
        f[1][2]=1;
        f[1][3]=2;
        f[2][2]=2;
        f[1][6]=1;
        assertEquals(-99,Aufgabe3.wert(f,1));
        assertEquals(99,Aufgabe3.wert(f,2));
    }
    
    @Test
    public void testNegamax() throws Exception {
        int[][] f=Aufgabe3.spielfeld();
        f[0][2]=2;
        f[0][3]=1;
        f[0][4]=2;
        f[0][5]=1;
        f[0][6]=1;
        f[1][2]=1;
        f[1][3]=2;
        f[2][2]=2;
        assertEquals(-101,Aufgabe3.negamax(f,1,0));
        assertEquals( 101,Aufgabe3.negamax(f,2,0));
        assertEquals( -99,Aufgabe3.negamax(f,1,1));
        assertEquals( 103,Aufgabe3.negamax(f,2,1));
        assertEquals(-101,Aufgabe3.negamax(f,1,2));
        assertEquals( 101,Aufgabe3.negamax(f,2,2));
    }
    
    @Test
    public void testBester() throws Exception {
        int[][] f=Aufgabe3.spielfeld();
        f[0][2]=2;
        f[0][3]=1;
        f[0][4]=2;
        f[0][5]=1;
        f[0][6]=1;
        f[1][2]=1;
        f[1][3]=2;
        f[2][2]=2;
        assertEquals(3,Aufgabe3.bester(f,1,2));
        assertEquals(4,Aufgabe3.bester(f,2,2));
    }
}

