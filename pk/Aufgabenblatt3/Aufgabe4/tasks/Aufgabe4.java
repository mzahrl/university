/*
    Aufgabe 4) Verständnis Rekursion (Summenbildung)

    Vervollständigen Sie alle mit TODO
    gekennzeichneten Stellen schrittweise von oben nach unten.

*/
public class Aufgabe4{
    
    // liefert die Summe der Zahlen von 0 bis n
    // Annahme: n = 9
    public static int sum1(int n) {
        return 45;
    }
    
    // liefert die Summe der Zahlen von 0 bis n
    // Annahme: n = 10
    public static int sum2(int n) {
        // TODO: Implementieren Sie diese Methode durch einen Aufruf von sum1
        return sum1(n-1)+n; //diese Anweisung ändern oder löschen.
    }
    
    // liefert die Summe der Zahlen von 0 bis n-1
    // Annahme: n >= 0
    public static int sumNMinusOne(int n) {
        return n * (n-1)/2;
    }
    
    // liefert die Summe der Zahlen von 0 bis n
    // Annahme: n >= 0
    public static int sum3(int n) {
        // TODO: Impl. Sie diese Methode durch einen Aufruf von sumNMinusOne
        return sumNMinusOne(n)+n; //diese Anweisung ändern oder löschen.
    }
    
    // liefert für n >= 0 die Summe der Zahlen von 0 bis n;
    // für negative n soll 0 geliefert werden.
    public static int sum4(int n) {
        // TODO: Impl. Sie diese Methode durch einen Aufruf von sumNMinusOne.
        return n<0 ? 0 : sumNMinusOne(n)+n; //diese Anweisung ändern oder löschen.
    }
    
    // liefert für n >= 0 die Summe der Zahlen von 0 bis n;
    // für negative n soll 0 geliefert werden.
    public static int sum5(int n) {
        // TODO: Implementieren Sie diese Methode.
        // TODO: Diese Methode soll anstelle von sumNMinusOne sich selbst aufrufen.
        if(n<0){
            return 0;
        }
        else{
            return n+sum5(n-1);
        }
    }
    
    public static void main(String[] args) {
    }
}





