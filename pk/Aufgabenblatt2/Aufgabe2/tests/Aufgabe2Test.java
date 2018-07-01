import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.rules.Timeout;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.Assert.assertEquals;

public class Aufgabe2Test {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog();

    @Test
    public void testOutput() throws Exception {

        String[] testData = new String[]{"2 4 6 9 0", "2 5 -1 0", "5, 6, a, 0"};
        String[] testValues = new String[]{"5", "2", ""};
        StringBuilder testString = new StringBuilder();

        testString.append(testValues[0]).append(System.lineSeparator());
        testString.append(testValues[1]).append(System.lineSeparator());
        testString.append(testValues[2]);

        InputStream stdin = System.in;
        for(String d: testData) {
            System.setIn(new ByteArrayInputStream(d.getBytes()));
            Aufgabe2.main(new String[]{});
        }
        System.setIn(stdin);
        assertEquals(testString.toString(), log.getLog());
    }
}
