import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.rules.Timeout;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Aufgabe1Test {

    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog();
    
    @Test
    public void testSort() throws Exception {
        int n = 100000;
        int[] array1 = new int[n];
        for (int i = 0; i < n; i++) {
            array1[i] = (int) (Math.random() * (n - i));
        }
        //Method method = Aufgabe1.class.getDeclaredMethod("sort", int[].class);
        //method.setAccessible(true);
        Aufgabe1.sort(array1);
        for (int i : array1) {
            int res = Arrays.binarySearch(array1, i);
            assertTrue("Sequence not sorted!", res >= 0);
        }
    }
    
    @Test
    public void testBinSearch() throws Exception {
        int n = 100000;
        int[] array1 = new int[n];
        for (int i = 0; i < n; i++) {
            array1[i] = (int) (Math.random() * (n - i));
        }
        //Method method = Aufgabe1.class.getDeclaredMethod("binSearch", int[].class, int.class);
        //method.setAccessible(true);
        Arrays.sort(array1);
        for (int i : array1) {
            assertEquals(true, Aufgabe1.binSearch(array1, i));
        }
        int[] array2 = {};
        assertEquals(false, Aufgabe1.binSearch(array2, 99));
    }
}


