import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;

public class Aufgabe1Test {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog();

    @Test
    public void testOutput() throws Exception {
        String[] testValues = new String[]{"F, 15, 56, 70, 7.7, 7.7, 77.7, 77.7", "1195784436000", "381", "379"};
        StringBuilder testString = new StringBuilder();
        for(String i: testValues) {
            testString.append(i).append(System.lineSeparator());
        }
        Aufgabe1.main(new String[]{});
        assertEquals(testString.toString(), log.getLog());
    }
}

