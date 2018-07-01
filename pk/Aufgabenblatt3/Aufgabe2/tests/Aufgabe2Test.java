import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.rules.Timeout;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class Aufgabe2Test {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog();
    
    @Test
    public void testSpecialSum() throws Exception{
        Method method = Aufgabe2.class.getDeclaredMethod("specialSum", int.class);
        assertEquals(true, method.getReturnType().equals(Integer.TYPE));
        method.setAccessible(true);
        assertEquals(0, method.invoke(null, -1));
        assertEquals(0, method.invoke(null, 0));
        assertEquals(0, method.invoke(null, 1));
        assertEquals(2, method.invoke(null, 2));
        assertEquals(2, method.invoke(null, 3));
        assertEquals(6, method.invoke(null, 4));
        assertEquals(6, method.invoke(null, 5));
        assertEquals(12, method.invoke(null, 7));
        assertEquals(42, method.invoke(null, 12));
        assertEquals(42, method.invoke(null, 13));
        assertEquals(42, method.invoke(null, 14));
        assertEquals(42, method.invoke(null, 15));
        assertEquals(58, method.invoke(null, 16));
        assertEquals(13862, method.invoke(null, 255));
        assertEquals(214716, method.invoke(null, 1000));
    }
}




