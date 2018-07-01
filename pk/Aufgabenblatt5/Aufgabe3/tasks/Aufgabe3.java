/*
    Aufgabe 3, 4, 5) Zweidimensionale Arrays -- Vier Gewinnt

    Fortsetzung des "Vier gewinnt" Spiels aus Aufgabenblatt 4. Kopieren Sie
    sich dazu die Methoden spielfeld(), spielstand(), zug(), und sieg() aus
    Aufgabenblatt 4, um die weiteren Funktionalitäten in diesem Aufgabenblatt
    zu lösen.

    Auch auf diese Aufgaben werden spätere Aufgabenblätter aufbauen, Sie sollten
    sie daher unbedingt lösen. In diesem Aufgabenblatt deckt das Spiel
    "Vier gewinnt" 3 Aufgaben ab. Bitte kreuzen Sie diese separat in TUWEL an.
    
    Hinweis: Sie können Hilfsmethoden implementieren.
    
    *****************************  Aufgabe 3  **********************************
    Für Aufgabe 3 schreiben Sie folgende statische Methoden:

    1) public static int wert1(int[][] f, int spieler)
    
    Diese Methode nimmt eine naive* Stellungsbewertung der Position von Spieler
    "spieler" vor: Es zählt die Zweier-Reihen, Dreier-Reihen, und Vierer-Reihen
    aus Steinen des Spielers "spieler". Der zurückgegebene Wert ist
    1*z+100*d+10000*v, wobei z die Zahl der Zweier-Reihen, d die Zahl der
    Dreier-Reihen, und v die Zahl der Vierer-Reihen ist.
    
    *) Eine einigermassen gute Stellungsbewertung würde den Rahmen der
    Übung sprengen.
    
    Für die Stellung
    
    |       |
    |       |
    |       |
    |  o    |
    |  xo   |
    |  oxoxx|
    +-------+
    
    ist die Bewertung für Spieler 1 (x) 1*2=2 und fuer Spieler 2 (o)
    1*3+100*1=103.  Eine Dreier-Reihe zählt also auch noch als
    zwei Zweier-Reihen.

    2) public static int wert(int[][] f, int spieler)
    
    Die Methode bezieht den wert1() des Gegners in die Bewertung mit ein: Vier
    gewinnt ist (wie die meisten Brettspiele) ein Null-Summen-Spiel
    (Spieler 1 gewinnt, wenn Spieler 2 verliert, und umgekehrt), daher
    soll wert() die Differenz von wert1() des Spielers und wert1() des
    Gegners zurückgeben, im obigen Beispiel also -101 für Spieler 1 (oder
    101 für Spieler 2).
    ****************************************************************************
    
    *****************************  Aufgabe 4  **********************************
    Für Aufgabe 4 schreiben Sie folgende statische Methoden:

    1) public static int negamax(int[][] f, int spieler, int tiefe)
    
    Eine bessere Stellungsbewertung kann man aus wert() ableiten, indem
    man einige Halbzüge vorausschaut. Bei einem Halbzug Vorausschau
    bewertet man die Stellung, die sich bei jedem der 7 möglichen Züge
    ergibt, wie folgt: Der Spieler, der am Zug ist, wird den für ihn
    besten Zug auswählen, der Wert der ursprünglichen Stellung ist also
    das Maximum der Werte der sieben möglichen Folgestellungen. Wenn man
    das für mehrere Halbzüge verallgemeinert, muss man nach jedem Halbzug
    die Seite wechseln, und für den Spieler, der dann am Zug ist, das
    Maximum berechnen. Um diesen Wert dann als Bewertung fuer den anderen
    (vorherigen) Spieler zu verwenden, muß man ihn negieren. Dieser
    Algorithmus heißt "Negamax".

    Diese rekursive Methode "negamax" führt eine Stellungsbewertung für Spieler
    "spieler" mit "tiefe" Zügen Vorausschau durch. Bei 0 Zügen Vorausschau soll
    die Bewertung wert() verwendet werden.
    
    Für diese Methode benötigen Sie eine Möglichkeit, Züge zu probieren,
    ohne sich darauf festzulegen, entweder indem Sie den alten Wert von f
    erhalten, oder indem Sie nach dem Probieren den Zug wieder
    zurücknehmen.  Wenn nötig, modifizieren Sie existierende Methoden
    und/oder implementieren Sie Hilfsmethoden, um das zu erreichen.

    2) public static int bester(int[][] f, int spieler, int tiefe)

    Diese Methode wählt den besten Zug aus. Alle 7 möglichen Züge
    werden durchprobiert, und die sich dadurch ergebende Stellung f1 wird mit
    Hilfe von negamax() bewertet (beachten Sie, welcher Spieler am Zug
    ist). Der Rückgabewert ist ein Zug mit maximaler Bewertung.
    
    Zusatzfragen:
    1. Was sind die Vor- und Nachteile der von Ihnen gewählten
       Art, nach dem Probieren wieder zum vorherigen Zug zu kommen?
    2. Ermitteln Sie durch Ausprobieren und ungefähre Zeitmessung, wie der
       Zeitaufwand von bester() mit der Tiefe zusammenhängt.
    3. Wieviele Aufrufe von wert() werden höchstens ausgeführt, wenn man
       bester() mit Tiefe 0, 1, 2, 8 und n aufruft?
    ****************************************************************************

    *****************************  Aufgabe 5  **********************************
    Für Aufgabe 5 schreiben Sie folgende statische Methode:

    1) public static void spiel1(int tiefe)

    Diese Methode führt ein Vier-Gewinnt-Spiel Spieler gegen Computer durch:
    Zunächst sucht sich der Spieler aus, ob er beginnt oder der Computer. Wenn
    der Computer am Zug ist ist, wählt er den nächsten Zug mit bester(...,
    tiefe) aus und führt ihn durch.  Abgesehen davon macht spiel1() das
    gleiche wie spiel(). Probieren Sie verschiedene Werte für tiefe aus,
    und wählen Sie einen, bei dem der Computer im Normalfall zwischen 0.1s
    und 1s zur Auswahl des besten Zugs braucht. Testen Sie die Methode,
    indem Sie gegen den Computer spielen.
    ****************************************************************************
*/
import java.util.Scanner;
import java.lang.Math;
public class Aufgabe3 {


    //Aus Aufgabenblatt 4
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

    //***************************  Aufgabe 3  **********************************
    public static int wert1(int[][] f, int spieler) {
        // TODO: Implementieren Sie hier die Angabe
        int steinAnz;
        int z = 0;
        int d = 0;
        int v = 0;
        //Waggrecht
        for (int r = 0; r < f.length; r++) {
            steinAnz = 0;
            for (int s = 0; s < 7; s++) {
                if (f[r][s] == spieler) {
                    steinAnz++;
                } else {
                    steinAnz = 0;
                }
                if (steinAnz >= 2) {
                    z++;
                }
                if (steinAnz >= 3) {
                    d++;
                }
                if (steinAnz >= 4) {
                    v++;
                }
            }
        }
        //Senkrecht
        for (int s = 0; s < 7; s++) {
            steinAnz = 0;
            for (int r = 0; r < f.length; r++) {
                if (f[r][s] == spieler) {
                    steinAnz++;
                } else {
                    steinAnz = 0;
                }
                if (steinAnz >= 2) {
                    z++;
                }
                if (steinAnz >= 3) {
                    d++;
                }
                if (steinAnz >= 4) {
                    v++;
                }
            }
        }
        //Diagonal  \
        int r=1;
        int s=0;
        int smitte=0;
        int maxs=1;
        int rf;
        while(smitte!=6){
            steinAnz=0;
            rf=0;
            while(s<=maxs){
                if(f[r-rf][s]==spieler){
                    steinAnz++;
                }
                else{
                    steinAnz=0;
                }
                if(steinAnz >=4){
                    z++;
                    d++;
                    v++;
                }
                else if(steinAnz==3){
                    z++;
                    d++;
                }
                else if(steinAnz==2){
                    z++;
                }
                s++;
                rf++;
            }
            if(r==5){
                if(maxs<6){
                    maxs++;
                }
                smitte++;
                s=smitte;
            }
            else{
                r++;
                s=0;
                maxs++;
            }
        }
        // Diagonal /

        r=1;
        s=6;
        smitte=6;
        maxs=5;
        while(smitte!=0){
            steinAnz=0;
            rf=0;
            while(s>=maxs){
                if(f[r-rf][s]==spieler){
                    steinAnz++;
                }
                else{
                    steinAnz=0;
                }
                if(steinAnz >=4){
                    z++;
                    d++;
                    v++;
                }
                else if(steinAnz==3){
                    z++;
                    d++;
                }
                else if(steinAnz==2){
                    z++;
                }
                s--;
                rf++;
            }
            if(r==5){
                if(maxs>0){
                    maxs--;
                }
                smitte--;
                s=smitte;
            }
            else{
                r++;
                s=6;
                maxs--;
            }
        }
        return 1*z+100*d+10000*v; //diese Anweisung ändern oder löschen.

    }
    
    public static int wert(int[][] f, int spieler){
        // TODO: Implementieren Sie hier die Angabe
        int result;
        if(spieler==1){
            result=wert1(f,spieler)-wert1(f,spieler+1);
        }
        else{
            result=wert1(f,spieler)-wert1(f,spieler-1);
        }
        return result; //diese Anweisung ändern oder löschen.
    }
    //**************************************************************************
    
    
    //***************************  Aufgabe 4  **********************************
    public static int negamax(int[][] f, int spieler, int tiefe){
        // TODO: Implementieren Sie hier die Angabe
        if(tiefe<0){
            return wert(f,spieler);
        }
        else{
            if(tiefe==1){
                wert(zug(resetArray(f),spieler,1),spieler);
            }
        }
        return -1; //diese Anweisung ändern oder löschen.
    }
    
    
    public static int bester(int[][] f, int spieler, int tiefe){
        // TODO: Implementieren Sie hier die Angabe
        return -1; //diese Anweisung ändern oder löschen.
    }
    //**************************************************************************
    
    
    //***************************  Aufgabe 5  **********************************
    public static void spiel1(int tiefe){
        // TODO: Implementieren Sie hier die Angabe
    }
    //**************************************************************************
    public static int[][] resetArray(int[][]ar){
        int[][] newAr = ar.clone();
        return newAr;
    }
    public static void main(String[] args) {

    }
    
}

