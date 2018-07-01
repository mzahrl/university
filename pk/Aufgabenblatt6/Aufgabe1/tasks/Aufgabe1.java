import java.util.Arrays;
import java.math.*;

/*******************************************************************************

 AUFGABENBLATT 6 - Allgemeine Informationen

 Achten Sie bei der Implementierung auf folgende Punkte:

 - Ihr Programm sollte den dazugehörenden Test (z.B. enthält Aufgabe1Test den
 Test zu Aufgabe1) bestehen.

 - Bei jeder Aufgabe finden Sie Zusatzfragen. Diese Zusatzfragen beziehen sich
 thematisch auf das erstellte Programm.  Sie müssen diese Zusatzfragen in der
 Übung beantworten können.

 - Verwenden Sie bei allen Ausgaben immer System.out.println().

 - Verwenden Sie für die Lösung der Aufgaben keine speziellen Aufrufe aus der
 Java-API, die die Aufgaben verkürzen würden.

 Abgabe: Die Abgabe erfolgt in TUWEL. Bitte laden Sie Ihr IntelliJ-Projekt
 bis spätestens Montag 12.12.2016 08:00 Uhr in TUWEL hoch. Zusätzlich
 müssen Sie in TUWEL ankreuzen welche Aufgaben Sie gelöst haben und während
 der Übung präsentieren können.

 ******************************************************************************/
/*
    Aufgabe 1) Sortieren & Suchen

    Implementieren Sie in dieser Aufgabe in der gegebenen Klasse Aufgabe1
    folgende statische Methoden:

    - sort:       Diese Methode soll den Sortieralgorihtmus "QuickSort"
                  implementieren. Sie müssen den Sortieralgorithmus selbst
                  ausimplementieren und dürfen keinen entsprechenden Aufruf aus
                  der Java-API verwenden.

    - binSearch:  Dieser Methode wird ein sortiertes Array übergeben.
                  Zusätzlich erhält die Methode einen Wert nach dem gesucht
                  werden soll. Es soll eine binäre Suche implementiert werden,
                  die true zurückliefert falls das Element enthalten ist,
                  ansonsten false.
                  
    Hinweis: Sie dürfen zusätzliche Hilfsmethoden implementieren und verwenden!

    Zusatzfragen:
    1. Welche API-Aufrufe bietet Java für das Sortieren von Arrays an?
    2. Welcher Sortieralgorithmus wird in der Java (1.8) für das Sortieren von
       Arrays verwendet?
    3. Warum ist die Wahl des Pivot-Elements entscheidend für die Performance
       des Quicksort Algorithmus?
    4. Warum muss das Array für die binäre Suche sortiert sein?
    5. Wie geht man vor wenn man in einem absteigend sortierten Array die
       Binärsuche anwenden will?
*/
public class Aufgabe1 {
    
    public static void sort(int[] array) {
        //TODO Implementieren Sie hier die Methode "sort"
        sortmultiple(array,0,array.length-1);

    }
    public static void sortmultiple(int[] array, int least, int highest){
        if(least>=highest){
            return;
        }
        int l=least;
        int r=highest;
        int pivot=array[l+(r-l)/2];
        int help;
        while(l<=r){
            while(array[l]<pivot){
                l++;
            }
            while(array[r]>pivot){
                r--;
            }
            if(l<=r){
                help=array[l];
                array[l]=array[r];
                array[r]=help;
                l++;
                r--;
            }
        }
        if(least<r){
            sortmultiple(array,least,r);
        }
        if(highest>l){
            sortmultiple(array,l,highest);
        }
    }
    
    
    public static boolean binSearch(int[] array, int elem) {
        //TODO Implementieren Sie hier die Methode "binSearch"
        int half;
        int least=0;
        int biggest=array.length-1;
        while(least<=biggest){
            half=least+(biggest-least)/2;
            if(elem==array[half]) return true;
            if(elem> array[half]) least=half+1;
            else biggest=half-1;
        }
        return false; //diese Anweisung ändern oder löschen.
        
    }
    
    
    public static void main(String[] args) {
        int[] ar = {1,2,7,4,9,3};
        sort(ar);
        System.out.println(Arrays.toString(ar));
        System.out.println(binSearch(ar,9));
    }
}



