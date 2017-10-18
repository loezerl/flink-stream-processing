package evaluators;

/**
 * Created by loezerl-fworks on 18/10/17.
 */
public class Prequential extends Evaluator {

    private long miss=0;
    private long hits=0;

    public Prequential(){
    }

    @Override
    public synchronized void setValue(Boolean val){
        if(val)
            this.hits++;
        else
            this.miss++;
    }

    public void PrintResults(){
        long instances = this.miss + this.hits;
        System.out.println("Acertos: " + this.hits);
        System.out.println("Erros: " + this.miss);
        System.out.println("Instancias: " + instances);
        System.out.println("=============================");
        System.out.println("Acuracia: " + (this.hits*100.0)/instances);
    }
}
