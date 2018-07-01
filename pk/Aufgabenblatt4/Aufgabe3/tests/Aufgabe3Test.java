import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.rules.Timeout;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Aufgabe3Test {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog();
    
    @Test
    public void testSpielfeld() throws Exception {
        int[][] f=Aufgabe3.spielfeld();
        assertEquals("Zeilen im Spielfeld",6,f.length);
        for (int f1[] : f) {
            assertEquals("Spalten in einer Zeile",7,f1.length);
            for (int f2 : f1) {
                assertEquals("Wert eines Elements",0,f2);
            }
        }
    }
    
    @Test
    public void testSpielstand() throws Exception {
        String[] testValues = new String[]{
            "|       |",
            "|       |",
            "|       |",
            "|       |",
            "|  xo   |",
            "|  ox   |",
            "+-------+"};
        int[][] f=Aufgabe3.spielfeld();
        f[0][2]=2;
        f[0][3]=1;
        f[1][2]=1;
        f[1][3]=2;
        StringBuilder testString = new StringBuilder();
        for(String i: testValues) {
            testString.append(i).append(System.lineSeparator());
        }
        Aufgabe3.spielstand(f);
        assertEquals(testString.toString(), log.getLog());
    }
    
    @Test
    public void testZug() throws Exception {
        int[][] f=Aufgabe3.spielfeld();
        int[][] f1=Aufgabe3.zug(f,1,2);
        assertEquals(1,f1[0][2]);
        int[][] f2=Aufgabe3.zug(f1,2,2);
        assertEquals(2,f2[1][2]);
        int[][] f3=Aufgabe3.zug(f2,1,2);
        assertEquals(1,f3[2][2]);
        int[][] f4=Aufgabe3.zug(f3,2,2);
        assertEquals(2,f4[3][2]);
        int[][] f5=Aufgabe3.zug(f4,1,2);
        assertEquals(1,f5[4][2]);
        int[][] f6=Aufgabe3.zug(f5,2,2);
        assertEquals(2,f6[5][2]);
        int[][] f7=Aufgabe3.zug(f6,1,2);
        assertArrayEquals(null,f7);
        int[][] f8=Aufgabe3.zug(f6,1,7);
        assertArrayEquals(null,f8);
        int[][] f9=Aufgabe3.zug(f6,1,3);
        assertEquals(1,f9[0][3]);
    }
    
    @Test
    public void testSieg() throws Exception {
        int[][] f=Aufgabe3.spielfeld();
        // waagrecht
        f = Aufgabe3.zug(f,1,2);
        f = Aufgabe3.zug(f,2,2);
        f = Aufgabe3.zug(f,1,3);
        f = Aufgabe3.zug(f,2,3);
        f = Aufgabe3.zug(f,1,4);
        f = Aufgabe3.zug(f,2,4);
        f = Aufgabe3.zug(f,1,5);
        assertEquals(true,Aufgabe3.sieg(f,1));
        assertEquals(false,Aufgabe3.sieg(f,2));
        f = Aufgabe3.zug(f,2,5);
        assertEquals(true,Aufgabe3.sieg(f,2));

        // senkrecht
        f = Aufgabe3.spielfeld();
        f = Aufgabe3.zug(f,1,2);
        f = Aufgabe3.zug(f,2,2);
        f = Aufgabe3.zug(f,2,2);
        f = Aufgabe3.zug(f,2,2);
        f = Aufgabe3.zug(f,2,2);
        assertEquals(false,Aufgabe3.sieg(f,1));
        assertEquals(true,Aufgabe3.sieg(f,2));

        // diagonal /
        f = Aufgabe3.spielfeld();
        f = Aufgabe3.zug(f,1,2);
        f = Aufgabe3.zug(f,2,3);
        f = Aufgabe3.zug(f,1,3);
        f = Aufgabe3.zug(f,2,4);
        f = Aufgabe3.zug(f,1,4);
        f = Aufgabe3.zug(f,2,5);
        f = Aufgabe3.zug(f,1,4);
        f = Aufgabe3.zug(f,2,5);
        f = Aufgabe3.zug(f,1,5);
        f = Aufgabe3.zug(f,1,5);
        assertEquals(false,Aufgabe3.sieg(f,2));
        assertEquals(true,Aufgabe3.sieg(f,1));

        // diagonal \
        f = Aufgabe3.spielfeld();
        f = Aufgabe3.zug(f,1,6);
        f = Aufgabe3.zug(f,2,5);
        f = Aufgabe3.zug(f,1,5);
        f = Aufgabe3.zug(f,2,4);
        f = Aufgabe3.zug(f,1,4);
        f = Aufgabe3.zug(f,2,3);
        f = Aufgabe3.zug(f,1,4);
        f = Aufgabe3.zug(f,2,3);
        f = Aufgabe3.zug(f,1,3);
        f = Aufgabe3.zug(f,1,3);
        assertEquals(false,Aufgabe3.sieg(f,2));
        assertEquals(true,Aufgabe3.sieg(f,1));

    }
}

