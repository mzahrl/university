package ad1.ss17.pa;

import java.util.*;

public class Graph {
    ArrayList<List<Integer>> adjazensListe;
    boolean top=false;
    boolean changet=true;
    boolean sC=false;
    boolean changeSC=true;
    int[] rangarray;
    HashMap<String, Boolean> comp = new HashMap<String, Boolean>();

    public Graph(int n) {
        this.adjazensListe = new ArrayList<List<Integer>>();
        rangarray=new int[n];
        for(int i=0;i<n;i++){
            List<Integer> l = new LinkedList<>();
            this.adjazensListe.add(l);
        }
    }

    public int numberOfNodes() {
        return this.adjazensListe.size();
    }

    public int numberOfEdges() {
        int result=0;
        for(int i=0;i<this.adjazensListe.size();i++){
            result+=this.adjazensListe.get(i).size();
        }
        return result;
    }

    public void addEdge(int v, int w) {
        if(v!=w){
            if(!this.adjazensListe.get(v).contains(w)) {
                int i=0;
                //Kanten am richtige Index einfuegen, fuer Top-Sortierung wichtig.
                while(i<this.adjazensListe.get(v).size() &&  w>this.adjazensListe.get(v).get(i)){
                    i++;
                }
                this.adjazensListe.get(v).add(i,w);
                this.changet=true;
                this.changeSC=true;
                this.comp.clear();
            }
        }
    }

    public void addAllEdges(int v) {
        if(this.adjazensListe.get(v).size()!=this.adjazensListe.size()-1) {
            this.adjazensListe.get(v).clear();
            for (int i = 0; i < this.adjazensListe.size(); i++) {
                if (i != v) {
                    this.adjazensListe.get(v).add(i);
                }
                this.changet = true;
                this.changeSC = true;
                this.comp.clear();
            }
        }
    }

    public void deleteEdge(int v, int w) {
        if(this.adjazensListe.get(v).contains(w)){
            this.adjazensListe.get(v).remove(new Integer(w));
            this.changet=true;
            this.changeSC=true;
            this.comp.clear();
        }
    }

    public void deleteAllEdges(int v) {
        if(!this.adjazensListe.get(v).isEmpty()){
            this.adjazensListe.get(v).clear();
            this.changet=true;
            this.changeSC=true;
            this.comp.clear();
        }
    }


    public Iterable<Integer> getReachableNodes(int v) {
        List<Integer> nodes = new LinkedList<Integer>();
        Queue<Integer> q = new LinkedList<>();
        boolean[] discovered = new boolean[this.adjazensListe.size()];
        q.add(v);
        discovered[v]=true;
        //BFS
        while(!q.isEmpty()){
            int knoten=q.poll();
            for(int i=0;i<this.adjazensListe.get(knoten).size();i++){
                //Geht aufsteigend die Kanten durch und fuegt einen Knoten mit InGrad 0 in die Queue
                if(!discovered[this.adjazensListe.get(knoten).get(i)]) {
                    discovered[this.adjazensListe.get(knoten).get(i)]=true;
                    q.add(this.adjazensListe.get(knoten).get(i));
                }
            }
        }
        for(int i=0;i<discovered.length;i++){
            if(discovered[i]==true && i!=v){
                nodes.add(i);
            }
        }
        return nodes;
    }


    public boolean isDAG() {
        //Ist Top-sortierung noch gueltig? Wenn nicht fuehre sie durch.
        if(!this.changet){
            return this.top;
        }
        rankInOrder(0);
        return this.top;
    }

    public int rankInOrder(int i) {
        //Ist das letzte Ergebnis noch gueltig.
        if(!this.changet){
            return this.rangarray[i];
        }
        LinkedList<Integer> liste = new LinkedList<>();//Eine Queue
        int[] count = new int[this.adjazensListe.size()];
        //Erstell Array mit Knotengrad von jeder Kante.
        for(int knoten=0;knoten<this.adjazensListe.size();knoten++){
            for(int kante=0;kante<this.adjazensListe.get(knoten).size();kante++){
                count[this.adjazensListe.get(knoten).get(kante)]++;
            }
        }
        //Fuegt Knoten mit grad 0 in die Liste ein.
        for(int knoten=0;knoten<this.adjazensListe.size();knoten++){
            if(count[knoten]==0){
                liste.addFirst(knoten);
            }
        }
        int rang=-1;
        while(!liste.isEmpty()){
            rang++;
            int knoten = liste.pollFirst();
            this.rangarray[knoten]=rang;
            //Entfernt alle ausgehenden Kanten vom Knoten, aufsteigen.
            for(int a=0;a<this.adjazensListe.get(knoten).size();a++)
            {
                count[this.adjazensListe.get(knoten).get(a)]--;
                if (count[this.adjazensListe.get(knoten).get(a)]==0) {
                    //Fuegt Knoten an den Anfang der Liste.
                    liste.addFirst(this.adjazensListe.get(knoten).get(a));
                }
            }
        }
        if(rang==this.adjazensListe.size()-1){
            this.top=true;
            this.changet=false;
        }
        else{
            this.top=false;
            this.changet=false;
            Arrays.fill(this.rangarray,-1);
        }
        return this.rangarray[i];
    }


    public boolean isStronglyConnected(){
        if(this.changeSC=false){
            return sC;
        }
        this.changeSC=false;
        int startknoten=0;
        //1 Knoten ist ein DAG
        if(this.adjazensListe.size()==1){
            sC=true;
            return true;
        }
        boolean[] discovered = new boolean[this.adjazensListe.size()];
        discovered[startknoten]=true;
        int knotenerreicht = 1;
        Queue<Integer> q = new LinkedList<>();
        q.add(startknoten);
        //Anhand des Startknotens wird ueberprueft wie viele andere Knoten erreicht werden koennen.
        while(!q.isEmpty()){
            int knoten = q.poll();
            for(int i=0;i<this.adjazensListe.get(knoten).size();i++){
                if(!discovered[this.adjazensListe.get(knoten).get(i)]){
                    discovered[this.adjazensListe.get(knoten).get(i)]=true;
                    knotenerreicht++;
                    q.add(this.adjazensListe.get(knoten).get(i));
                    if(knotenerreicht==this.adjazensListe.size()){
                        q.clear();
                    }
                }
            }
        }
        if(knotenerreicht!=this.adjazensListe.size()){
            sC=false;
            return false;
        }
        //Adjazensliste vom Graphen mit umgedrehter Kantenrichtung.
        ArrayList<List<Integer>> grev = new ArrayList<List<Integer>>(4);
        for(int i=0;i<adjazensListe.size();i++){
            List<Integer> l = new LinkedList<>();
            grev.add(l);
        }
        for(int i=0;i<this.adjazensListe.size();i++){
            for(int e=0;e<this.adjazensListe.get(i).size();e++){
                grev.get(this.adjazensListe.get(i).get(e)).add(i);
            }
        }
        Arrays.fill(discovered,false);
        discovered[startknoten]=true;
        knotenerreicht=1;
        q.add(startknoten);
        //Anhand des Startknotens wird ueberprueft wie viele andere Knoten erreicht werden koennen. Diesesmal mit umgedrehter Kantenrichtung
        while(!q.isEmpty()){
            int knoten = q.poll();
            for(int i=0;i<grev.get(knoten).size();i++){
                if(!discovered[grev.get(knoten).get(i)]){
                    discovered[grev.get(knoten).get(i)]=true;
                    knotenerreicht++;
                    q.add(grev.get(knoten).get(i));
                    if(knotenerreicht==grev.size()){
                        this.sC=true;
                        return true;
                    }
                }
            }
        }
        this.sC=false;
        return false;
    }




    public boolean checkComponent(int i, int j) {
        if(i>j){
            int zwischenablage=j;
            j=i;
            i=zwischenablage;
        }
        if(this.comp.containsKey(i+"-"+j)){
            return this.comp.get(i+"-"+j);
        }
        if(breitenSuche(i,j)){
            if(breitenSuche(j,i)){
                comp.put(i+"-"+j,true);
                return true;
            }
        }
        comp.put(i+"-"+j,false);
        return false;
    }



    public boolean breitenSuche(int s, int e){
        // Breitensuche wie in der VO besprochen.
        boolean[] discovered = new boolean[this.adjazensListe.size()];
        discovered[s]=true;
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        while(!q.isEmpty()){
            int knoten = q.poll();
            for(int i=0;i<this.adjazensListe.get(knoten).size();i++){
                if(this.adjazensListe.get(knoten).get(i)==e){
                    return true;
                }
                if(!discovered[this.adjazensListe.get(knoten).get(i)]){
                    discovered[this.adjazensListe.get(knoten).get(i)]=true;
                    q.add(this.adjazensListe.get(knoten).get(i));
                }
            }
        }
        return false;
    }


    public static void main(String[] args) {

    }
}



