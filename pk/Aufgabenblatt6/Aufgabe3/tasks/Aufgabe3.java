/*
    Aufgabe 3) Klassen und Objekte -- Vier Gewinnt

    Fortsetzung des "Vier gewinnt" Spiels aus Aufgabenblatt 5.
     
    Auch auf diese Aufgabe werden spätere Aufgabenblätter aufbauen, Sie sollten
    sie daher unbedingt lösen.
    
    Definieren Sie eine Klasse Spielfeld mit einem Konstruktor, der ein leeres
    Spielfeld erzeugt, und folgenden (nicht-statischen) Methoden:

    - int feld(int reihe, int spalte) gibt 0, 1, oder 2 zurück, je nachdem, ob
      das Feld besetzt ist und von wem.

    Folgende Methoden sind nicht-statische Varianten für Spielfeld der früher
    definierten statischen Methoden:

    - void spielstand()
    - Spielfeld zug(int spieler, int Spalte)
    - boolean sieg(int spieler)
    - int wert1(int spieler)
    - int wert(int spieler)
    - int negamax(int spieler, int tiefe)
    - int bester(int spieler, int tiefe)

    Das Spielfeld wird dabei noch immer so repräsentiert wie zuvor.

    Schreiben Sie eine Klasse Viergewinnt mit den Methoden

    - public static void spiel()
    - public static void spiel1(int tiefe)

    die für das Spielfeld und die Methoden dazu die Klasse Spielfeld benutzen.

    Zusatzfrage: Ist spiel1() bei gleicher Tiefe durch diese Änderung
    schneller oder langsamer geworden? Um wieviel?
    ****************************************************************************
*/
// TODO: Implementieren Sie hier die Klasse "Spielfeld"

// TODO: Implementieren Sie hier die Klasse "Viergewinnt"

class Spielfeld{
    private int[][] spielarray;
    public Spielfeld(){
        spielarray=new int[7][6];
    }
    void spielstand(){
        // TODO: Implementieren Sie hier die Angabe

        for(int r=spielarray.length-1;r>=0;r--){
            System.out.print("|");
            for(int i=0;i<7;i++){
                if(spielarray[r][i]==0){
                    System.out.print(" ");
                }
                else if(spielarray[r][i]==1){
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
    Spielfeld zug(int spieler, int spalte){
        // TODO: Implementieren Sie hier die Angabe
        // Fehler beim Test, weil angenommen wird Spieler sagt Spalte 1, dann ist es Spalte2 im Array => Spalte 0 ist daher gültig.
        if(spalte>=0 && spalte<=6){
            for(int r=0;r<spielarray.length;r++){
                if(spielarray[r][spalte]==0){
                    spielarray[r][spalte]=spieler;
                }
            }
        }
    }
    boolean sieg(int spieler) {
        // TODO: Implementieren Sie hier die Angabe
        int steinAnz;
        //Waggrecht
        for(int r=0;r<spielarray.length;r++){
            steinAnz=0;
            for(int s=0;s<7;s++){
                if(spielarray[r][s]==spieler){
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
            for(int r=0;r<spielarray.length;r++){
                if(spielarray[r][s]==spieler){
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
            if (spielarray[r][s] == spieler && spielarray[r + 1][s + 1] == spieler && spielarray[r + 2][s + 2] == spieler && spielarray[r + 3][s + 3] == spieler) {
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
            if(spielarray[r][s] == spieler && spielarray[r - 1][s + 1] == spieler && spielarray[r - 2][s + 2] == spieler && spielarray[r - 3][s + 3] == spieler){
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
}

public class Aufgabe3 {





    public static void main(String[] args) {
    }
}


/*
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
 */
/*
public static int wert1(int[][] f, int spieler){
	int result = 0;
	for (int r = 0; r <= 5; ++r) {
		for(int c = 0; c <= 6; ++c) {
			for (int len = 2, weight = 1; len <=4; len++, weight*=100) {
				boolean isRow = true, isColumn = true, isDiagP = true, isDiagN = true;
				for(int i = 0; i < len; i++) {
					if (c+i > 6 || f[r][c+i] != spieler) isRow = false;
					if (r+i > 5 || f[r+i][c] != spieler) isColumn = false;
					if (r+i > 5 || c+i > 6 || f[r+i][c+i] != spieler) isDiagP = false;
					if (r-i < 0 || c+i > 6 || f[r-i][c+i] != spieler) isDiagN = false;
				}
				if (isRow) result += weight;
				if (isColumn) result += weight;
				if (isDiagP) result += weight;
				if (isDiagN) result += weight;
			}
		}
	}
	return result;
}

public static int wert(int[][] f, int spieler){
	int opponent = spieler == 1 ? 2 : 1;
	return wert1(f, spieler) - wert1(f, opponent);
}

public static int negamax(int[][] f, int spieler, int tiefe){
	if(tiefe == 0){
		return wert(f,spieler);
	}
	int opponent = spieler == 1 ? 2 : 1;
	int bestMaxValue = Integer.MIN_VALUE;
	for(int i = 0; i <= 6; i++){
		if(zug(f,spieler,i) != null){
			int currentMaxValue;
			if(sieg(f,spieler)){
				currentMaxValue = Integer.MAX_VALUE;
			}
			else{
				currentMaxValue = -negamax(f,opponent,tiefe-1);
			}
			if(currentMaxValue > bestMaxValue){
				bestMaxValue = currentMaxValue;
			}
			unzug(f,i);
		}
	}
	return bestMaxValue;
}

// Hilfsmethode
private static int[][] unzug(int[][] f, int spalte){
	if(spalte < 0 || spalte > 6){
		return null;
	}
	if(f[0][spalte] == 0){
		return null;
	}
	int r = 0;
	while(r <= 5 && f[r][spalte] != 0){
		r++;
	}
	f[r-1][spalte] = 0;
	return f;
}

public static int bester(int[][] f, int spieler, int tiefe){
	int bestIndex = -1;
	int opponent = spieler == 1 ? 2 : 1;
	int bestMaxValue = Integer.MIN_VALUE;
	for(int i = 0; i <= 6; i++){
		if(zug(f,spieler,i) != null){
			int currentMaxValue;
			if(sieg(f,spieler)){
				currentMaxValue = Integer.MAX_VALUE;
			}
			else{
				currentMaxValue = -negamax(f,opponent,tiefe);
			}
			if(currentMaxValue > bestMaxValue){
				bestMaxValue = currentMaxValue;
				bestIndex = i;
			}
			unzug(f,i);
		}
	}
	return bestIndex;
}
 */

