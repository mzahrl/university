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
    public void testAufgabe2() throws Exception {
        String[] testValues = new String[]{
                "Ergebnisse für f:",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "Ergebnisse für g:",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "Ergebnisse für h:",
                "1",
                "1",
                "1",
                "1",
                "1",
                "1",
                "1",
                "2",
                "2",
                "2",
                "2",
                "1",
                "1",
                "1",
                "1",
                "1",
                "1",
                "1",
                "Ergebnisse für p:",
                "7",
                "5",
                "3",
                "1",
                "0",
                "2",
                "4",
                "6",
                "Ergebnisse für q:",
                "0",
                "1",
                "0",
                "1",
                "0",
                "1",
                "0",
                "1",
                "0",
                "1",
                "0",
                "1",
                "0",
                "1"
        };
        StringBuilder testString = new StringBuilder();
        for(String i: testValues) {
            testString.append(i).append(System.lineSeparator());
        }
        Aufgabe2.main(new String[]{});
        assertEquals(testString.toString(), log.getLog());
    }
}
