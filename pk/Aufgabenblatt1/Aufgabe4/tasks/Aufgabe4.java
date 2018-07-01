/*
    Aufgabe 4) while-Schleife

    Erweitern Sie die main-Methode um folgende Funktionalität:
    - Schreiben Sie eine while-Schleife, die die Quersumme der gegebenen
      Variable "value" berechnet. (z.B. Quersumme der Zahl 37489 ist gleich 31)
    - Nachdem Sie die Quersumme berechnet haben geben Sie das Ergebnis mit
      "System.out.println()" aus.

    Zusatzfragen:
    1) Welcher Wert (postitiv und ganzzahlig) muss der Varibalen "value"
       zugewiesen werden damit die höchstmögliche Quersumme berechnet wird?
       int: -2147483648....2147483647
       int value=1999999999;

*/
public class Aufgabe4{

    public static void main(String[] args) {
        int value = 8487304;
        int i = 1;
        int quersumme=0;
        while(i<=value){
            quersumme+=value%10;
            value/=i*10;
        }
        System.out.println(quersumme);
    }
}

