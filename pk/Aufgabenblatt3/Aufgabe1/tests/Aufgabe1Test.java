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
    public void testDrawNumDiamondOutput1() throws Exception {
        String[] testValues = new String[]{
                "       1       ",
                "      222      ",
                "     33333     ",
                "    4444444    ",
                "   555555555   ",
                "  66666666666  ",
                " 7777777777777 ",
                "888888888888888",
                " 7777777777777 ",
                "  66666666666  ",
                "   555555555   ",
                "    4444444    ",
                "     33333     ",
                "      222      ",
                "       1       "};
        StringBuilder testString = new StringBuilder();
        for(String i: testValues) {
            testString.append(i).append(System.lineSeparator());
        }
        Aufgabe1.drawNumDiamond(15);
        assertEquals(testString.toString(), log.getLog());
    }
    
    @Test
    public void testDrawNumDiamondOutput2() throws Exception {
        Aufgabe1.drawNumDiamond(1);
        assertEquals("1"+System.lineSeparator(), log.getLog());
    }
    
    @Test
    public void testDrawNumDiamondOutput3() throws Exception {
        Aufgabe1.drawNumDiamond(0);
        assertEquals("", log.getLog());
    }
    
    @Test
    public void testDrawNumDiamondOutput4() throws Exception {
        Aufgabe1.drawNumDiamond(6);
        assertEquals("NO VALID INPUT" + System.lineSeparator(), log.getLog());
    }
}





