/*
    Aufgabe 3) Verzweigungen und while-Schleife

    Erweitern Sie die main-Methode um folgende Funktionalität:
    - Schreiben Sie eine while-Schleife, die alle Zahlen zwischen "start"
     (inklusive) und "ende" (inklusive) aufsummiert und in einer Variablen "sum"
      abspeichert.
    - Nachdem Sie alle Zahlen innerhalb des beschriebenen Intervalls aufsummiert
      haben, dividieren Sie die Summe "sum" durch den Divisor "div".
    - Geben Sie "TRUE" aus falls die Division (sum/div) den Rest 0 ergibt,
      anderenfalls "FALSE".
    - Verwenden Sie für die Ausgabe "System.out.println()".
*/
public class Aufgabe3{

    public static void main(String[] args) {
        int div = 14;
        int start = 42;
        int end = 678;
        int sum = 0;
        while(start<=end){
            sum+=start;
            start++;
        }
        System.out.println(sum % div == 0 ? "TRUE" : "FALSE");
    }
}

