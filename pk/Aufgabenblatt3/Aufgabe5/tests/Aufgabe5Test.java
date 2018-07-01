import org.junit.Rule;
import org.junit.Test;
import java.lang.reflect.Method;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;

public class Aufgabe5Test {
    
    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);
    
    @Test
    public void testLargestRemainder() throws Exception {
        Method method = Aufgabe5.class.getDeclaredMethod("largestRemainder", int.class, int.class);
        assertEquals(true, method.getReturnType().equals(Integer.TYPE));
        method.setAccessible(true);
        assertEquals(0, method.invoke(null, 20, 0));
        assertEquals(0, method.invoke(null, 20, -1));
        assertEquals(0, method.invoke(null, 0, 20));
        assertEquals(0, method.invoke(null, -1, 20));
        assertEquals(0, method.invoke(null, 0, 0));
        assertEquals(0, method.invoke(null, -1, -2));
        assertEquals(4, method.invoke(null, 20, 5));
        assertEquals(5, method.invoke(null, 5, 20));
        assertEquals(348, method.invoke(null, 1000, 349));
    }
    
    @Test
    public void testToThePower() throws Exception{
        Method method = Aufgabe5.class.getDeclaredMethod("toThePower", int.class, int.class);
        assertEquals(true, method.getReturnType().equals(Integer.TYPE));
        method.setAccessible(true);
        assertEquals(2, method.invoke(null, 2, 0));
        assertEquals(4, method.invoke(null, 2, 1));
        assertEquals(8, method.invoke(null, 2, 2));
        assertEquals(16, method.invoke(null, 2, 3));
        assertEquals(-2, method.invoke(null, -2, 0));
        assertEquals(-4, method.invoke(null, -2, 1));
        assertEquals(-8, method.invoke(null, -2, 2));
        assertEquals(500, method.invoke(null, 1000, -1));
        assertEquals(250, method.invoke(null, 1000, -2));
        assertEquals(125, method.invoke(null, 1000, -3));
        assertEquals(-62, method.invoke(null, -1000, -4));
    }
    
    @Test
    public void testFact() throws Exception{
        Method method = Aufgabe5.class.getDeclaredMethod("fact", int.class);
        assertEquals(true, method.getReturnType().equals(Long.TYPE));
        method.setAccessible(true);
        assertEquals(-1L, method.invoke(null, -1));
        assertEquals(0L, method.invoke(null, 0));
        assertEquals(1L, method.invoke(null, 1));
        assertEquals(2L, method.invoke(null, 2));
        assertEquals(6L, method.invoke(null, 3));
        assertEquals(24L, method.invoke(null, 4));
        assertEquals(3628800L, method.invoke(null, 10));
        assertEquals(2432902008176640000L, method.invoke(null, 20));
    }
    
    @Test
    public void testRecString() throws Exception{
        Method method = Aufgabe5.class.getDeclaredMethod("recString", int.class);
        assertEquals(true, method.getReturnType().equals(String.class));
        method.setAccessible(true);
        assertEquals("....", method.invoke(null, -4));
        assertEquals("...", method.invoke(null, -3));
        assertEquals("..", method.invoke(null, -2));
        assertEquals(".", method.invoke(null, -1));
        assertEquals("", method.invoke(null, 0));
        assertEquals("1.", method.invoke(null, 1));
        assertEquals("2..", method.invoke(null, 2));
        assertEquals("3...", method.invoke(null, 3));
        assertEquals("4....", method.invoke(null, 4));
        assertEquals("15...............", method.invoke(null, 15));
    }
    
    @Test
    public void testOddSum() throws Exception{
        Method method = Aufgabe5.class.getDeclaredMethod("oddSum", int.class, int.class);
        assertEquals(true, method.getReturnType().equals(Integer.TYPE));
        method.setAccessible(true);
        assertEquals(4, method.invoke(null, 1, 4));
        assertEquals(9, method.invoke(null, 1, 5));
        assertEquals(0, method.invoke(null, -4, 4));
        assertEquals(-3, method.invoke(null, -4, -3));
        assertEquals(-3, method.invoke(null, -3, -3));
        assertEquals(2500, method.invoke(null, 0, 100));
        assertEquals(0, method.invoke(null, 0, 0));
        assertEquals(0, method.invoke(null, 0, -5));
    }
}




