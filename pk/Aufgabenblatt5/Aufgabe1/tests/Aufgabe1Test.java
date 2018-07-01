import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.rules.Timeout;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class Aufgabe1Test {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog();

    @Test
    public void testAufgabe1() throws Exception {
        Class Period = Class.forName("Period");
        Method pfrom = Period.getDeclaredMethod("from");
        assertEquals(true, pfrom.getReturnType().equals(String.class));
        Method pto = Period.getDeclaredMethod("to");
        assertEquals(true, pto.getReturnType().equals(String.class));
        Constructor cons = Period.getConstructor(String.class, String.class);
        Method from = Aufgabe1.class.getDeclaredMethod("from", String.class);
        assertEquals(true, from.getReturnType().equals(String.class));
        Method to = Aufgabe1.class.getDeclaredMethod("to", String.class);
        assertEquals(true, to.getReturnType().equals(String.class));
        Method add = Aufgabe1.class.getDeclaredMethod("add", String.class, Period);
        assertEquals(true, add.getReturnType().equals(boolean.class));
    
        Aufgabe1 plan1 = new Aufgabe1();
        Aufgabe1 plan2 = new Aufgabe1();
        Object period = cons.newInstance("0.0.2", "0.0.3");
        assertEquals(false, add.invoke(plan1, "Sonnenaufgang betrachten", cons.newInstance("1.95.0", "2.5.0")));
        assertEquals(false, add.invoke(plan1, "Frühstück", cons.newInstance("2.15.0", "2.30.0")));
        assertEquals(false, add.invoke(plan1, "Muschelsammeln", cons.newInstance("3.0.0", "3.50.0")));
        assertEquals(false, add.invoke(plan1, "Sonnenhut tragen", cons.newInstance("3.0.0", "6.0.0")));
        assertEquals(true, add.invoke(plan1, "Sonnenhut tragen", cons.newInstance("2.0.0", "6.0.0")));
        assertEquals(false, add.invoke(plan2, "A", cons.newInstance("0.0.0", "0.0.1")));
        assertEquals(false, add.invoke(plan2, "B", period));
        assertEquals(false, add.invoke(plan2, "Sonnenhut tragen", period));
        assertEquals("0.0.2", pfrom.invoke(period));
        assertEquals("0.0.3", pto.invoke(period));
        assertEquals(null, from.invoke(plan1, "A"));
        assertEquals(null, to.invoke(plan1, "B"));
        assertEquals("2.0.0", from.invoke(plan1, "Sonnenhut tragen"));
        assertEquals("2.15.0", from.invoke(plan1, "Frühstück"));
        assertEquals("6.0.0", to.invoke(plan1, "Sonnenhut tragen"));
        assertEquals("0.0.3", to.invoke(plan2, "Sonnenhut tragen"));

    }
}

