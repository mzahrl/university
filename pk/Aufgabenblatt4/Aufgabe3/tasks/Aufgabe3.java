/*
    Aufgabe 3, 4, 5,) Zweidimensionale Arrays -- Vier Gewinnt

    Beim Spiel "Vier gewinnt" gewinnt der Spieler, der als erstes vier Steine
    in eine Reihe bringt (horizontal, vertikal, oder diagonal). Das Spielfeld
    steht senkrecht und ist 7 Spalten breit und 6 Reihen hoch. Steine können nur
    im untersten Feld einer Spalte platziert werden, das noch nicht von einem
    anderen Stein besetzt ist.

    "Vier gewinnt" wird in mehreren, aufeinander aufbauenden Aufgaben in
    mehreren Aufgabenblättern verwendet, Sie sollten daher diese Aufgaben
    unbedingt lösen. In diesem Aufgabenblatt deckt das Spiel "Vier gewinnt"
    3 Aufgaben ab. Bitte kreuzen Sie diese separat in TUWEL an.
    
    Hinweis: Sie können Hilfsmethoden implementieren, dürfen aber vorgegebene
             Methoden und deren Signaturen nicht verändern.
    
    *****************************  Aufgabe 3  **********************************
    Für Aufgabe 3 schreiben Sie folgende statische Methoden:

    1) public static int[][] spielfeld()
    
    Diese Methode erzeugt ein leeres Vier-Gewinnt-Spielfeld. Das Spielfeld soll
    als zweidimensionales Array von int-Werten dargestellt werden, wobei auf
    ein Feld in Reihe r und Spalte s im Feld f mit f[r][s] zugegriffen werden
    soll. Ein leeres Feld wird mit 0 repraesentiert, ein Stein auf einem Feld
    durch 1 für einen Stein des Spielers 1 bzw. 2 für einen Stein des
    Spielers 2.

    2) public static void spielstand(int[][] f)
    
    Diese Methode gibt den Spielstand f in folgender Form aus:
    
    |       |
    |       |
    |       |   Definition: Die linke unterste Ecke ist als Koordinate [0][0]
    |       |               definiert und stellt den Ausgangspunkt des
    |  xo   |               Spielbrettes dar.
    |  ox   |
    +-------+
    
    wobei für ein leeres Feld ein Leerzeichen ausgegeben wird, für einen Stein
    von Spieler 1 ein x, und für einen Stein von Spieler 2 ein o.
    
    Zusatzfragen:
    1. Welche anderen Möglichkeiten neben der von Ihnen gewählten gibt es, um
    von der Spielernummer auf x bzw. o zu kommen?
    ****************************************************************************
    
    *****************************  Aufgabe 4  **********************************
    Für Aufgabe 4 schreiben Sie folgende statische Methoden:

    1) public static int[][] zug(int[][] f, int spieler, int spalte)

    Diese Methode führt einen Zug des Spielers "spieler" in Spalte
    "spalte" (0-6 für legale Züge) durch und gibt die neue Stellung
    (das Spielfeld nach dem Zug) zurück.  Wenn in Spalte "spalte" kein
    Zug möglich ist (weil die Spalte voll ist oder nicht im erlaubten
    Bereich), soll zug() null zurückgeben.  Das vom Parameter f
    referenzierte Feld darf verändert werden oder unverändert bleiben.

    2) public static boolean sieg(int[][] f, int spieler)

    Diese Methode liefert true, wenn "spieler" vier Steine in einer Reihe hat,
    sonst false.

    Zusatzfragen:
    1. Welche Vor- und Nachteile hat es für dieses Beispiel und in
    Hinblick auf Aufgabe 5, den Parameter f von zug() zu verändern.
    ****************************************************************************

    *****************************  Aufgabe 5  **********************************
    Für Aufgabe 5 schreiben Sie folgende statische Methode:

    1) public static void spiel()

    Diese Methode führt ein Vier-Gewinnt-Spiel zwischen zwei Spielern durch:
    Beginnend mit einem leeren Spielfeld werden abwechselnd Spieler 1 und
    Spieler 2 zur Eingabe eines Spielzuges aufgefordert, der Spielzug
    durchgeführt, und der aktuelle Spielstand ausgegeben, solange bis ein
    Spieler gewonnen hat oder das Spielfeld voll ist. Überlegen Sie sich
    eine sinnvolle Behandlung von ungültigen Eingaben. In "main" wird am Ende
    nur noch die Methode spiel() aufgerufen. Testen Sie spiel() selbst, auch
    den Fall, dass das Spielfeld voll wird, ohne dass ein Spieler gewonnen hat.

    Zusatzfragen:
    1. Was machen Sie bei ungültigen Eingaben?
    ****************************************************************************
*/
import java.util.Scanner;
public class Aufgabe3 {

    //***************************  Aufgabe 3  **********************************
    public static int[][] spielfeld(){
        // TODO: Implementieren Sie hier die Angabe
        int[][]spielfeld = new int[6][7];
        for(int r=0;r<spielfeld.length;r++){
            for(int i=0;i<7;i++){
                spielfeld[r][i]=0;
            }
        }
        return spielfeld; //diese Anweisung ändern oder löschen.
    }
    
    public static void spielstand(int[][] f){
        // TODO: Implementieren Sie hier die Angabe

        for(int r=f.length-1;r>=0;r--){
            System.out.print("|");
            for(int i=0;i<7;i++){
                if(f[r][i]==0){
                    System.out.print(" ");
                }
                else if(f[r][i]==1){
                    System.out.print("x");
                }
                else{
                    System.out.print("o");
                }
            }
            System.out.println("|");
        }
        System.out.println("+-------+");
    }
    //**************************************************************************
    
    
    //***************************  Aufgabe 4  **********************************
    public static int[][] zug(int[][] f, int spieler, int spalte){
        // TODO: Implementieren Sie hier die Angabe
        // Fehler beim Test, weil angenommen wird Spieler sagt Spalte 1, dann ist es Spalte2 im Array => Spalte 0 ist daher gültig.
        if(spalte>=0 && spalte<=6){
            for(int r=0;r<f.length;r++){
                if(f[r][spalte]==0){
                    f[r][spalte]=spieler;
                    return f;
                }
            }
        }
        return null; //diese Anweisung ändern oder löschen.
    }
    public static boolean sieg(int[][] f, int spieler) {
        // TODO: Implementieren Sie hier die Angabe
        int steinAnz;
        //Waggrecht
        for(int r=0;r<f.length;r++){
            steinAnz=0;
            for(int s=0;s<7;s++){
                if(f[r][s]==spieler){
                    steinAnz++;
                }
                else{
                    steinAnz=0;
                }
                if(steinAnz==4){
                    return true;
                }
            }
        }
        //Senkrecht
        for(int s=0;s<7;s++){
            steinAnz=0;
            for(int r=0;r<f.length;r++){
                if(f[r][s]==spieler){
                    steinAnz++;
                }
                else{
                    steinAnz=0;
                }
                if(steinAnz==4){
                    return true;
                }
            }
        }
        //Diagonal start links unten->ende rechts oben
        int r=0;
        int s=0;
        while((6-r)>=4) {
            if (f[r][s] == spieler && f[r + 1][s + 1] == spieler && f[r + 2][s + 2] == spieler && f[r + 3][s + 3] == spieler) {
                return true;
            }
            s++;
            if((7-s)<4){
                s=0;
                r++;
            }
        }
        // Diagonal start recht oben -> ende links unten
        r=5;
        s=0;
        while(r>=3){
            if(f[r][s] == spieler && f[r - 1][s + 1] == spieler && f[r - 2][s + 2] == spieler && f[r - 3][s + 3] == spieler){
                return true;
            }
            s++;
            if((7-s)<4){
                s=0;
                r--;
            }
        }
        return false; //diese Anweisung ändern oder löschen.
    }
    //**************************************************************************
    
    
    //***************************  Aufgabe 5  **********************************
    public static void spiel(){
        // TODO: Implementieren Sie hier die Angabe
        int[][] spielfeld=spielfeld();
        Scanner sc=new Scanner(System.in);
        int counter=0;
        int spalte;
        int spieler=1;
        String iwas="";
        boolean ende=false;
        boolean ungueltig=false;
        while(!ende) {
            spielstand(spielfeld);
            System.out.println("Spieler "+spieler+" ist am Zug: ");
            if (sc.hasNextInt()) {
                spalte = sc.nextInt();
                if (spalte < 0 || spalte > 6) {
                    System.out.println("Bitte eine gültige Spalte eingeben.");
                    ungueltig = true;
                } else {
                    if(zug(spielfeld, spieler, spalte)==null){
                        System.out.println("Diese Spalte ist bereits Voll.");
                        ungueltig=true;
                    }
                }
            } else {
                System.out.println("Bitte eine Zahl von 0 bis 6 eingeben.");
                ungueltig = true;
                iwas=sc.next();
            }
            if (ungueltig) {
                ungueltig = false;
            }
            else {
                if (sieg(spielfeld, 1) == true) {
                    spielstand(spielfeld);
                    System.out.println("Spieler 1 hat gewonnen.");
                    ende = true;
                }
                if (sieg(spielfeld, 2) == true) {
                    spielstand(spielfeld);
                    System.out.println("Spieler 2 hat gewonnen.");
                    ende = true;
                }
                if (spieler == 1) {
                    spieler = 2;
                }
                else {
                    spieler = 1;
                }
                counter++;
            }
            if (counter == (7 * 6)) {
                spielstand(spielfeld);
                System.out.println("Das Spielfeld ist voll.");
                ende = true;
            }

        }
    }
    //**************************************************************************
    
    public static void main(String[] args) {
        spiel();
    }

}

