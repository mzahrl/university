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
    public void testIter() throws Exception {
        Method method = Aufgabe3.class.getDeclaredMethod("iter", int.class, int.class);
        assertEquals(true, method.getReturnType().equals(Integer.TYPE));
        method.setAccessible(true);
        assertEquals(10, method.invoke(null, -100, -10));
        assertEquals(10, method.invoke(null, -100, 10));
        assertEquals(1, method.invoke(null, 512, -9));
        assertEquals(3, method.invoke(null, 24, 9));
        assertEquals(3, method.invoke(null, 9, 24));
        assertEquals(0, method.invoke(null, 100, 0));
        assertEquals(0, method.invoke(null, 0, -10));
    }
}




