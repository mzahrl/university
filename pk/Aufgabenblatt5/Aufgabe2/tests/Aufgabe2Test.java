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
    public void testAufgabe2() throws Exception {
        Method check = Aufgabe2.class.getDeclaredMethod("check", String.class);
        assertEquals(true, check.getReturnType().equals(boolean.class));
        check.setAccessible(true);
        assertEquals(true, check.invoke(null, ""));
        assertEquals(true, check.invoke(null, "a*[a+12]"));
        assertEquals(true, check.invoke(null, "a+(b)-c"));
        assertEquals(true, check.invoke(null, "a+{b+8+(b+c)}/a"));
        assertEquals(false, check.invoke(null, "["));
        assertEquals(false, check.invoke(null, ")"));
        assertEquals(false, check.invoke(null, "[}"));
        assertEquals(false, check.invoke(null, "()}"));
        assertEquals(false, check.invoke(null, "([)]"));
        assertEquals(false, check.invoke(null, "]["));
    }
}
