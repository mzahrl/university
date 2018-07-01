/*
    Aufgabe 2) Verzweigungen und while-Schleife

    Erweitern Sie die main-Methode um folgende Funktionalität:
    - Schreiben Sie eine while-Schleife, die alle ungeraden, durch 7 teilbaren
      Zahlen von 0-100 (inklusive) aufsummiert.
    - Geben Sie das Ergebnis mit "System.out.println()" aus.

    Zusatzfragen:
    1) Die Schleife summiert Werte zwischen 0 und 100 (inklusive). Wie könnten
       Sie das Programm umbauen damit die Schleife beliebige Intervalle
       aufsummiert?
       Mann müsste nur den Start und das Ende des Intervalles ändern, wobei i der Start und die Bedingung in der while schleife das Ende makieren.
*/
public class Aufgabe2{

    public static void main(String[] args) {
        int i=0;
        int summe=0;
        while(i<=100){
            if(i%2==1 && i%7==0){
                summe+=i;
            }
            i++;
        }
        System.out.println(summe);
    }
}

