/*
    Aufgabe 5) Verzweigungen und while-Schleife

    Erweitern Sie die main-Methode um folgende Funktionalität:
    - Schreiben Sie eine while-Schleife, die alle Zahlen zwischen
      10-1000 (inklusive) aufsummiert, die sowohl durch 7 als auch und durch 13
      teilbar sind.
    - Geben Sie das Ergebnis mit "System.out.println()" aus.

*/
public class Aufgabe5{

    public static void main(String[] args) {
        // TODO: Implementieren Sie hier die Angabe
        int i=10;
        int summe=0;
        while(i<=1000){
            if(i%7==0 && i%13==0){
                summe+=i;
            }
            i++;
        }
        System.out.println(summe);
    }
}

