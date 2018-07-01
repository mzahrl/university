/*
    Aufgabe2) Eingabe (Scanner)

    Erweitern Sie die main-Methode so, dass wiederholt Werte eingelesen werden,
    bis die Zahl 0 eingegeben wird. Dann wird das Programm beendet. Zuvor wird
    noch der Durchschnittswert aller eingelesenen Zahlen als ganzzahliger Wert
    ausgegeben (Nachkommastellen gehen verloren),  falls alle eingelesenen Werte
    ganze Zahlen waren. In allen anderen Fällen wird nichts ausgegeben.

    Zusatzfragen:
    1. Warum muss eine ungültige Eingabe (keine ganze Zahl) aus dem
    Input-Stream entfernt werden?
    2. Woran kann man erkennen, dass ein eingelesener Wert eine ganze Zahl ist?
    3. Woran kann man feststellen, ob eine frühere Eingabe ungültig war?
*/
import java.util.Scanner;
public class Aufgabe2{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int gesamt=0;
        int counter=0;
        int zahl;
        boolean nuller=false;
        while (nuller==false && scanner.hasNextInt()) {
            zahl=scanner.nextInt();
            if(zahl==0 && counter!=0){
                nuller=true;
                System.out.println(gesamt/counter);
            }
            else {
                gesamt += zahl;
                counter++;
            }
        }
    }
}

