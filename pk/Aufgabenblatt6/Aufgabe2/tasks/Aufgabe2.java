/*
    Aufgabe 2) Listen

    In der Klasse IntList haben Sie eine Listenimplementierung gegeben.
    Dazu sollen Sie folgende zusätzliche Methoden implementieren:

    - reverseI: (iterativ) Dreht die Liste um. Die Methode muss iterativ
                implementiert werden. Bei dieser Methode werden keine (!) neuen
                Knoten erzeugt und die Werte (elem-Felder) der Knoten dürfen
                nicht überschrieben werden. Die Umkehrung der Liste wird nur
                durch die Neuverkettung der vorhandenen Knoten erreicht!

    - reverseR: (rekursiv) Dreht die Liste um. Die Methode muss rekursiv
                implementiert werden. Die eigentliche Rekursion sollte in der
                Klasse ListNode durchgeführt werden. Bei dieser Methode werden
                keine (!) neuen Knoten erzeugt und die Werte der Knoten dürfen
                nicht überschrieben werden. Die Umkehrung der Liste wird nur
                durch die Neuverkettung der vorhandenen Knoten erreicht!
                
    - insertIdx:(iterativ) Fügt einen Knoten entsprechend dem übergebenen
                Index ein. Das Head-Element hat den Index 1. Hängen Sie an
                entsprechender Stelle die Liste so um, damit diese nach
                Einfügen wieder eine korrekte Verkettung aufweist. Ist der Index
                größer als die Länge der Liste wird false zurückgegeben.
                Bei erfolgreichem Einfügen wird true retourniert.
                
    - remove:   (iterativ) Entfernt aus der Liste alle Knoten mit dem
                übergebenen Wert (elem), falls Knoten mit diesem Element
                vorhanden sind. Bei erfolgreichem Entfernen wird true
                zurückgegeben, ansonsten false.

    Zusatzfragen:
    1. Wie entsteht die Ausgabe beim Aufruf der Methode
       System.out.println(list);
    2. Warum ist es sinnvoll beim Iterieren durch Listen sich eine Kopie des
       Zeigers auf den "head"-Knoten zu erstellen?
    3. Erläutern Sie die Vor- und Nachteile von Listen gegenüber Arrays.
*/
class IntList {
    
    private class ListNode {
        int elem;
        ListNode next = null;
        
        ListNode(int elem, ListNode next) {
            this.elem = elem;
            this.next = next;
        }
        
        int getElem() {
            return this.elem;
        }
        
        ListNode getNext() {
            return this.next;
        }
        
        void add(int elem) {
            if (this.next == null) {
                this.next = new ListNode(elem, null);
            } else {
                this.next.add(elem);
            }
        }
        
        ListNode reverseR() {
            //TODO Implementieren Sie hier die Angabe
            return null; //diese Anweisung ändern oder löschen.
        }
        
        public String toString() {
            return this.elem + ((this.next == null) ? "-|" : "->" + this.next);
        }
        
    }
    
    private ListNode head = null;
    
    public boolean empty() {
        return this.head == null;
    }
    
    public void add(int elem) {
        if (this.empty()) {
            this.head = new ListNode(elem, null);
        } else {
            this.head.add(elem);
        }
    }
    
    public int first() {
        return this.head.getElem();
    }
    
    public void reverseI() {
        //TODO Implementieren Sie hier die Angabe
    }
    
    public void reverseR() {
        this.head = this.head.reverseR();
    }
    
    public boolean insertIdx(int value, int index) {
        //TODO Implementieren Sie hier die Angabe
        return false; //diese Anweisung ändern oder löschen.
    }
    
    public boolean remove(int value) {
        //TODO Implementieren Sie hier die Angabe
        return false; //diese Anweisung ändern oder löschen.
    }
    
    public String toString() {
        return "[" + this.head + "]";
    }
}

public class Aufgabe2{
    
    public static void main(String[] args) {
        // Inhalt von "main" wird für die Unittests verwendet!
        IntList myList = new IntList();
        for(int i = 1; i < 6; i++){
            myList.add(i);
        }
        myList.add(25);
        myList.add(15);
        myList.add(1);
        myList.add(5);
        System.out.println(myList);
        myList.reverseI();
        System.out.println(myList);
        myList.reverseR();
        System.out.println(myList);
        myList.insertIdx(0,1);
        myList.insertIdx(2,7);
        myList.insertIdx(18,11);
        myList.insertIdx(41,20);
        myList.insertIdx(23,12);
        myList.insertIdx(344,0);
        myList.insertIdx(1,2);
        myList.insertIdx(5,14);
        myList.insertIdx(4,6);
        System.out.println(myList);
        myList.remove(0);
        myList.remove(1);
        myList.remove(7);
        myList.remove(5);
        myList.remove(25);
        myList.remove(4);
        System.out.println(myList);
    }
}


