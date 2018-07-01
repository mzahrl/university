import java.util.ArrayDeque;
import java.util.Deque;

/*
    Aufgabe 2) Deque - Klammerung

    Implementieren Sie in Aufgabe2 eine statische Methode check, die einen
    String als Parameter hat und genau dann true zurückgibt, wenn der String
    einen korrekt geklammerten Ausdruck enthält. Ein Ausdruck ist dann korrekt
    geklammert, wenn es zu jeder öffnenden Klammer '(', '[' oder '{' eine
    entsprechende schließende Klammer ')', ']' oder '}' gibt. Zwischen diesen
    Zeichen können beliebige andere Zeichen vorkommen.

    Beispiele für korrekt geklammerte Ausdrücke:
    "", "a*[a+12]", "a+(b)-c", "a+{b+8+(b+c)}/a"
    Beispiele für nicht korrekt geklammerte Ausdrücke:
    "[", "(}", "a)[]", "([)]", "]["

    Verwenden Sie einen Stack zur Überprüfung der Klammerung: Durchlaufen Sie
    die Zeichen im String von vorne nach hinten und legen Sie jede öffnende
    Klammer, die Sie dabei finden, auf den Stack. Wenn Sie auf eine schließende
    Klammer stoßen, nehmen Sie das oberste Element vom Stack; bei korrekter
    Klammerung muss die schließende Klammer mit der Klammer vom Stack
    zusammenpassen. Andere Zeichen werden einfach ignoriert. Bei korrekter
    Klammerung muss der Stack am Ende leer sein.

    Hinweis: Sie können als Stack z.B. ein Objekt des Typs Deque<String>
    verwenden.

    Zusatzfragen:
    1. Was unterscheidet Queue von Map?
    1. Was ist der Unteschied zwischen LIFO und FIFO Prinzip?
    2. Wie könnte man diese Aufgabe auch mit einem Array statt einem Stack
       lösen? Welche Nachteile würden sich daraus ergeben?
*/
public class Aufgabe2{
    
    //TODO Implementieren Sie hier die Methode "check"
    public static boolean check(String s){
        Deque<String> c = new ArrayDeque<String>();
        String l;
        String r;
        int index=0;
        String[] k={"}",")","]"};
        boolean such=false;
        for(int i=0;i<s.length();i++){
            l=""+s.charAt(i);
            c.add(l);
        }
        while(!c.isEmpty()){
            l = c.removeFirst();
            if(l.equals(k[0]) || l.equals(k[1]) || l.equals(k[2])){
                return false;
            }
            if(l.equals("{")) {
                index=0;
                such = true;
            }
            else if(l.equals("(")){
                index=1;
                such = true;
            }
            else if(l.equals("[")){
                index=2;
                such = true;
            }
            while(such){
                if(c.isEmpty() && such==true){
                    return false;
                }
                r=c.removeLast();
                if(r.equals("{") || r.equals("(") || r.equals("[")){
                    return false;
                }
                if(r.equals(k[0]) || r.equals(k[1]) || r.equals(k[2])){
                    if(r.equals(k[index])){
                        such=false;
                    }
                    else{
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public static void main(String[] args) {
    }
}

