import classifiers.Classifier;
import classifiers.KNN;
import evaluators.Evaluator;
import evaluators.Prequential;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import util.InstanceParser;

import java.util.Properties;
import java.util.Vector;

/**
 * Created by loezerl-fworks on 18/10/17.
 */
public class Experimenter {
    public static long finishTime;
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("zookeeper.connect", "localhost:2181");
        props.put("group.id", "testflink");

        KNN myClassifier = new KNN(7, 1000, "euclidean");
        Classifier.setInstance(myClassifier);

        Prequential myEvaluator = new Prequential();
        Evaluator.setInstance(myEvaluator);

        env.enableCheckpointing(5000);

        DataStream<String> messages = env.addSource(new FlinkKafkaConsumer010("instances", new SimpleStringSchema(), props));
////        DataStream<Tuple2<String, String>>
//        DataStream<String> instances = messages.map(s-> new String(s.f1));
//        instances.print();

        DataStream<Vector<Double>> instances = messages.map(
                new MapFunction<String, Vector<Double>>() {
                    @Override
                    public Vector<Double> map(String s) throws Exception {
                        return InstanceParser.Parser(s);
                    }
                }
        );


        DataStream<Tuple2<Boolean, Integer>> answers = instances.map(
                new MapFunction<Vector<Double>, Tuple2<Boolean, Integer>>() {
                    @Override
                    public Tuple2<Boolean, Integer> map(Vector<Double> instance) throws Exception {
                        return new Tuple2<>(PrequentialMap.run(instance), 1);
                    }
                }
        );

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    System.out.println("\n\n=============================\nRunning shutdownhook..");
                    myEvaluator.PrintResults();
                }catch (Exception e){}
            }
        });


        env.setParallelism(8);
        finishTime=System.currentTimeMillis() + 50000;
        env.execute("Kafka 0.10 Example");
    }

    public static class PrequentialMap{

        public static boolean run(Vector<Double> instance){

            //if(System.currentTimeMillis() > finishTime){System.exit(1);}

            Classifier classifier = Classifier.getInstance();
            Evaluator evaluator = Evaluator.getInstance();

            Boolean answer = classifier.test(instance);
            classifier.train(instance);

            evaluator.setValue(answer);

            return answer;
        }
    }

}
