package ad2.ss17.cflp;

import java.util.*;

/**
 * Klasse zum Berechnen der L&ouml;sung mittels Branch-and-Bound.
 * Hier sollen Sie Ihre L&ouml;sung implementieren.
 */
public class CFLP extends AbstractCFLP {

    CFLPInstance instance;
    int globaleSchranke;
    int benoetigtebandbreite;
    int minUpgrade;
    int minKosten;
    int[][] distanzKosten;
    Integer[][] minKostenKunden;
    List<List<Integer>> facKosten;

    public CFLP(CFLPInstance instance) {
        // TODO: Hier ist der richtige Platz fuer Initialisierungen

        this.instance = instance;
        this.distanzKosten = new int[this.instance.getNumCustomers()][this.instance.getNumFacilities()];
        this.minKostenKunden = new Integer[this.instance.getNumCustomers()][this.instance.getNumFacilities()];
        this.facKosten = new ArrayList<List<Integer>>();
        for(int f = 0; f < this.instance.getNumFacilities(); f++){
            int basisKosten = instance.baseOpeningCostsOf(f);
            this.facKosten.add(new ArrayList<Integer>());
            this.facKosten.get(f).add(0);
            this.facKosten.get(f).add(basisKosten);
            this.facKosten.get(f).add((int) Math.ceil(basisKosten * 1.5));
        }
        int maxBFac = 0;
        int minKFac = -1;
        int bandbreite;
        int kosten;
        //Fac mit max Bandbreite
        for(int f = 0; f < this.instance.getNumFacilities(); f++){
            bandbreite = this.instance.maxBandwidthOf(f);
            if(bandbreite>maxBFac){
                maxBFac = bandbreite;
            }
            kosten = this.instance.baseOpeningCostsOf(f);
            if(kosten<minKFac || minKFac==-1){
                minKFac = kosten;
            }
        }
        //Bestimmt die Distanzkosten von jedem Kunden zu jeder Facility
        for(int i=0; i<this.instance.getNumCustomers();i++){
            Integer[] hilfe = new Integer[this.instance.getNumFacilities()];

            for(int f=0; f<this.instance.getNumFacilities();f++){
                this.distanzKosten[i][f]=this.instance.distanceCosts*instance.distance(f, i);
                hilfe[f]=f;
            }

            int kunde = i;
            Arrays.sort(hilfe, new Comparator<Integer>() {
                @Override
                public int compare(Integer fac1, Integer fac2){
                    return Integer.compare(distanzKosten[kunde][fac1], distanzKosten[kunde][fac2]);
                }
            });
            this.minKostenKunden[i] = hilfe.clone();
            this.benoetigtebandbreite += instance.bandwidthOf(i);
        }
        this.minUpgrade = (int) Math.ceil(this.benoetigtebandbreite / (double) maxBFac);
        this.minKosten = (int) Math.ceil(minKFac * 0.5);
    }

    /**
     * Diese Methode bekommt vom Framework maximal 30 Sekunden Zeit zur
     * Verf&uuml;gung gestellt um eine g&uuml;ltige L&ouml;sung
     * zu finden.
     * <p>
     * <p>
     * F&uuml;gen Sie hier Ihre Implementierung des Branch-and-Bound-Algorithmus
     * ein.
     * </p>
     */
    @Override
    public void run() {
        // TODO: Diese Methode ist von Ihnen zu implementieren

        int[] solution = new int[this.instance.getNumCustomers()];
        Arrays.fill(solution, -1);
        int[] gSolution = berechneObereSchranke(solution.clone(),0);
        this.globaleSchranke = this.kostenBerechnen(gSolution);
        super.setSolution(this.globaleSchranke, gSolution);
        this.branch(solution, 0);
    }

    private int[] berechneObereSchranke(int[] solution, int zugewieseneKunden){

        for (int x=zugewieseneKunden;x<this.instance.getNumCustomers();x++){
            solution[x] = besteFac(this.distanzKosten[x]);

        }
        return solution;
    }
    private int berechneUntereSchranke(int[] solution, int zugewieseneKunden){
        int schranke=kostenZuZeitpunkt(solution.clone(),zugewieseneKunden);
        return schranke;
    }
    private void branch(int[] solution, int tiefe){
        int[] gSolution = this.berechneObereSchranke(solution.clone(), tiefe);
        int obereSchranke = this.kostenBerechnen(gSolution);
        if(obereSchranke < this.globaleSchranke) {
            this.globaleSchranke = obereSchranke;
        }
        int untereSchranke = this.berechneUntereSchranke(solution.clone(), tiefe);
        if(untereSchranke>this.globaleSchranke){
            return;
        }
        if(untereSchranke==obereSchranke){
            super.setSolution(obereSchranke, gSolution);
            return;
        }
        //Fuer jede Facility wird ein neuer Branch erzeugt.
        for(int i=0; i<this.instance.getNumFacilities();i++){
            int[] newSolution=solution.clone();
            newSolution[tiefe]=this.minKostenKunden[tiefe][i];
            branch(newSolution,tiefe+1);
        }
    }
    private int kostenBerechnen(int[] solution){
        int gesamtKosten=0;
        int[] benoetigteBandbreite = new int[this.instance.getNumFacilities()];
        for(int i=0;i<this.instance.getNumCustomers();i++){
            if(solution[i] >= 0){
                benoetigteBandbreite[solution[i]]+= this.instance.bandwidthOf(i);
                gesamtKosten+=this.distanzKosten[i][solution[i]];
            }

        }
        for(int f=0;f<this.instance.getNumFacilities(); f++){
            gesamtKosten+=this.facKosten((int) Math.ceil(benoetigteBandbreite[f]/ (double) this.instance.maxBandwidthOf(f)),f);
        }
        return gesamtKosten;
    }
    public int kostenZuZeitpunkt(int[] solution, int zKunden){
        int kosten=this.kostenBerechnen(solution);
        int[] benoetigteBandbreite =  new int[this.instance.getNumFacilities()];
        //Rechnet die benoetigte Bandbreite per Facility aus oder die Gesamtbandbreite von den Kunden die noch keine Facility zugeordnet sind.
        for (int i=0;i<zKunden;i++) {
            benoetigteBandbreite[solution[i]] += this.instance.bandwidthOf(i);
        }
        for (int x=zKunden;x<this.instance.getNumCustomers();x++){
            kosten+=besteDistanz(this.distanzKosten[x]);
        }
        int benoetigteUpgrades=0;
        for(int f=0;f<this.instance.getNumFacilities();f++){
            benoetigteUpgrades+= (int) (Math.ceil(benoetigteBandbreite[f] / (double) this.instance.maxBandwidthOf(f)));
            /*
            kosten+=facilityKosten(benoetigteBandbreite[f]/this.instance.maxBandwidthOf(f),f);
            if (this.instance.maxBandwidthOf(f)>bestBandbreite){
                bestBandbreite=f;
            }
            */
        }
        kosten+= Math.max(this.minUpgrade - benoetigteUpgrades, 0) * this.minKosten;
        return kosten;
    }
    public int besteDistanz(int[]distanz){
        int d=-1;
        for (int i=0;i<this.instance.getNumFacilities();i++){
            if (distanz[i]<d || d==-1){
                d=distanz[i];
            }
        }
        return d;
    }
    public int besteFac(int[] distanz){
        int f=-1;
        int bestValue=-1;
        for (int i=0;i<this.instance.getNumFacilities();i++){
            if (distanz[i]<bestValue || bestValue==-1){
                f=i;
            }
        }
        return f;
    }
    //Berechnet Kosten mit hilfe des Fibbonaci trick
    private int facKosten(int k, int f){
        int facLevel = this.facKosten.get(f).size() - 1;
        int facKosten = this.instance.baseOpeningCostsOf(f);
        if(facLevel < k) {
            for(int i=facLevel+1;i<=k;i++){
                this.facKosten.get(f).add(Math.addExact(Math.addExact(this.facKosten.get(f).get(i - 1), this.facKosten.get(f).get(i - 2)), (4 - i) * facKosten));
            }
        }
        return this.facKosten.get(f).get(k);
    }

}
