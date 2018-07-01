import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Aufgabe4Test {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog();
    
    @Test
    public void testSum1() throws Exception {
        assertTrue(Aufgabe4.sum1(9) == 45);
    }
    
    @Test
    public void testSum2() throws Exception {
        assertTrue(Aufgabe4.sum2(10) == 55);
    }
    
    @Test
    public void testSumNMinusOne() throws Exception {
        assertTrue(Aufgabe4.sum3(10) == 55 && Aufgabe4.sum3(9) == Aufgabe4.sum1(9) && Aufgabe4.sum3(0) == 0);
    }
    
    @Test
    public void testSum3() throws Exception {
        assertTrue(Aufgabe4.sum3(10) == 55 && Aufgabe4.sum3(9) == Aufgabe4.sum1(9) && Aufgabe4.sum3(0) == 0);
    }
    
    @Test
    public void testSum4() throws Exception {
        assertTrue(Aufgabe4.sum4(10) == 55 && Aufgabe4.sum4(9) == Aufgabe4.sum1(9) && Aufgabe4.sum4(0) == 0);
    }
    
    @Test
    public void testSum5() throws Exception {
        assertTrue(Aufgabe4.sum5(10) == 55 && Aufgabe4.sum5(9) == Aufgabe4.sum1(9) && Aufgabe4.sum5(0) == 0);
    }
}




