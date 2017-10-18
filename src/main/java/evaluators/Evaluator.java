package evaluators;

/**
 * Created by loezerl-fworks on 18/10/17.
 */
public class Evaluator {

    private static Evaluator instance;


    public static synchronized Evaluator getInstance(){
        if(instance == null){
            instance = new Evaluator();
        }
        return instance;
    }
    public static synchronized void setInstance(Evaluator obj){
        instance = obj;
    }

    public synchronized void setValue(Boolean val){}
}
