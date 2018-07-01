import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;

public class Aufgabe2Test {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog();

    @Test
    public void testOutput() throws Exception {

        Aufgabe2.main(new String[]{});
        assertEquals("343" + System.lineSeparator(), log.getLog());
    }
}
