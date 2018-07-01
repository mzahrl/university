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
    public void testAufgabe1() throws Exception {
        String[] testValues = new String[]{
                "1",
                "1",
                "3",
                "5",
                "9",
                "15",
                "25",
                "41",
                "67",
                "109",
                "177",
                "287",
                "465",
                "753",
                "1219",
                "1973",
                "3193",
                "5167",
                "8361",
                "13529",
                "21891",
                "35421",
                "57313",
                "92735",
                "150049",
                "242785",
                "392835",
                "635621",
                "1028457",
                "1664079",
                "2692537",
                "1",
                "1",
                "3",
                "5",
                "9",
                "15",
                "25",
                "41",
                "67",
                "109",
                "177",
                "287",
                "465",
                "753",
                "1219",
                "1973",
                "3193",
                "5167",
                "8361",
                "13529",
                "21891",
                "35421",
                "57313",
                "92735",
                "150049",
                "242785",
                "392835",
                "635621",
                "1028457",
                "1664079",
                "2692537"
        };
        StringBuilder testString = new StringBuilder();
        for(String i: testValues) {
            testString.append(i).append(System.lineSeparator());
        }
    
        Aufgabe1.main(new String[]{});
        assertEquals(testString.toString(), log.getLog());
    }
}

