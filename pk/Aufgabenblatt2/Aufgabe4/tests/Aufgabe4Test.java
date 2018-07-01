import org.junit.Rule;
import org.junit.Test;
import java.lang.reflect.Method;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;

public class Aufgabe4Test {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    @Test
    public void testSimpler() throws Exception{
        Method method = Aufgabe4.class.getDeclaredMethod("simpler", short.class);
        assertEquals(true, method.getReturnType().equals(Long.TYPE));
        method.setAccessible(true);
        assertEquals(0L, method.invoke(null, (short)0));
        assertEquals(10L, method.invoke(null, (short)1));
        assertEquals(3000L, method.invoke(null, (short)333));
        assertEquals(4000000000L, method.invoke(null, (short)9999));
        assertEquals(111110L, method.invoke(null, (short)12345));
    }
}
