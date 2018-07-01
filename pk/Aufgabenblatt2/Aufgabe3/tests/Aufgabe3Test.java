import org.junit.Rule;
import org.junit.Test;
import java.lang.reflect.Method;
import org.junit.rules.Timeout;
import static org.junit.Assert.assertEquals;

public class Aufgabe3Test {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    @Test
    public void testNuller() throws Exception{
        Method method = Aufgabe3.class.getDeclaredMethod("nuller", long.class);
        assertEquals(true, method.getReturnType().equals(Integer.TYPE));
        method.setAccessible(true);
        assertEquals(1, method.invoke(null, 0L));
        assertEquals(0, method.invoke(null, 1L));
        assertEquals(0, method.invoke(null, -1L));
        assertEquals(0, method.invoke(null, 3L));
        assertEquals(23, method.invoke(null, 456345612128L));
        assertEquals(8, method.invoke(null, -3456));
    }

    @Test
    public void testEinser() throws Exception {
        Method method = Aufgabe3.class.getDeclaredMethod("einser", long.class);
        assertEquals(true, method.getReturnType().equals(Integer.TYPE));
        method.setAccessible(true);
        assertEquals(0, method.invoke(null, 0L));
        assertEquals(1, method.invoke(null, 1L));
        assertEquals(1, method.invoke(null, -1L));
        assertEquals(2, method.invoke(null, 3L));
        assertEquals(16, method.invoke(null, 456345612128L));
        assertEquals(4, method.invoke(null, -3456));
    }
}
