package classifiers;

import util.InstanceDouble;
import util.Similarity;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by loezerl-fworks on 18/10/17.
 */
public class KNN extends Classifier{
    private int K;
    private int WindowSize;
    private String DistanceFunction;
    private List<Vector<Double>> Window;

    public KNN(int kneighbours, int wsize, String function) {
        K = kneighbours;
        WindowSize = wsize;
        if(function.equals("euclidean")){
            DistanceFunction = "euclidean";
        }
        else{
            System.err.println("Distancias disponiveis: euclidean");
            System.exit(1);
        }
        Window = new ArrayList<>(wsize);
    }

    @Override
    public synchronized boolean test(Vector<Double> instance){

        if(Window.size() == 0){return false;}

        List<InstanceDouble> distances_ = new ArrayList<>();

        Window.forEach(s -> distances_.add(new InstanceDouble(s, Similarity.EuclideanDistance(s, instance))));


        List<InstanceDouble> K_neighbours = new ArrayList<>(K);

        int i=0;
        int index=0;
        for(InstanceDouble el : distances_){
            if(i < K){
                i++;
                K_neighbours.add(el);
            }
            else{
                index=0;
                for(InstanceDouble el_k:K_neighbours){
                    if(el.value < el_k.value){
                        K_neighbours.remove(index);
                        K_neighbours.add(index, el);
                        break;
                    }
                    index++;
                }
            }
        }

        Map majorvote = new HashMap<Double, Integer>();

        for(InstanceDouble x: K_neighbours){
            if(majorvote.containsKey(x.key.get(x.key.size()-1))){
                Integer aux = (Integer)majorvote.get(x.key.get(x.key.size()-1));
                majorvote.put(x.key.get(x.key.size()-1), aux + 1);
            }else{
                majorvote.put(x.key.get(x.key.size()-1), 1);
            }
        }

        Integer bestclass_vote = -600;
        Double bestclass_label = -600.0;

        Iterator<Map.Entry<Double, Integer>> it = majorvote.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry<Double, Integer> pair = it.next();
            if(pair.getValue() > bestclass_vote){
                bestclass_label = pair.getKey();
                bestclass_vote = pair.getValue();
            }
        }

        Double targetclass = instance.get(instance.size()-1);

        if(targetclass.equals(bestclass_label))
            return true;

        return false;
    }

    @Override
    public synchronized void train(Vector<Double> instance){
        if (Window.size() < WindowSize) {
            Window.add(instance);
        }
        else{
            Window.remove(0);
            Window.add(instance);
        }
    }
}
