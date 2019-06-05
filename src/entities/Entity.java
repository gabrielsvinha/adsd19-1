package entities;

import eduni.simjava.*;
import eduni.simjava.distributions.Sim_normal_obj;
import eduni.simjava.distributions.Sim_random_obj;

public class Entity extends Sim_entity {
    private Sim_stat status;
    private Sim_normal_obj delay_obj;

    private double[] probability;
    private int numInputPorts;

    protected Sim_port[] inputPorts;
    protected Sim_port[] outputPorts;

    private String name;

    private Tuple<Double, Double>[] probabilityRanges;

    protected final Sim_random_obj randomProbability;

    public Entity(String name, double mean, double average, int numInputPorts, double[] probabilityPorts) {
        super(name);

        status = new Sim_stat();
        status.add_measure(Sim_stat.UTILISATION);
        status.add_measure(Sim_stat.SERVICE_TIME);
        status.add_measure(Sim_stat.WAITING_TIME);
        status.add_measure(Sim_stat.QUEUE_LENGTH);
        set_stat(status);
        this.delay_obj = new Sim_normal_obj(name.concat("Delay"), mean, average);
        add_generator(delay_obj);

        this.randomProbability = new Sim_random_obj(name.concat("RandomProbability"));
        add_generator(delay_obj);

        this.name = name;
        this.numInputPorts = numInputPorts;
        this.probability = probabilityPorts;

        initPorts();
        defineRanges();
    }

    public double sample(){
        return this.delay_obj.sample();
    }

    private void initPorts(){
        this.inputPorts = createPorts(this.name.concat("_IN_"), numInputPorts);
        this.outputPorts = createPorts(this.name.concat("_OUT_"), probability.length);
    }

    private Sim_port[] createPorts(String s, int num) {
        Sim_port[] ports = new Sim_port[num];
        for (int i = 0; i < num; i++) {
            ports[i] = new Sim_port(this.name.concat(Integer.toString(i + 1)));
            add_port(ports[i]);
        }
        return ports;
    }

    private void defineRanges(){
        this.probabilityRanges= (Tuple<Double, Double>[]) new Tuple[this.probability.length];

        double acc = 0.0;
        for (int i = 0; i < this.probability.length; i++) {
            Tuple<Double, Double> probRange = new Tuple<Double, Double>(acc, acc + this.probability[i]);
            acc += this.probability[i];
            this.probabilityRanges[i] = probRange;
        }
    }

    class Tuple<X, Y> {
        public X a;  // first
        public Y b;  // second

        public Tuple(X a, Y b) {
            this.a = a;
            this.b = b;
        }
    }

    public void bodyInsideLoop() {
        Sim_event e = new Sim_event();
        sim_get_next(e);
        sim_process(sample());
        sim_completed(e);

        double p = this.randomProbability.sample();

        Tuple<Double, Double>[] probRanges = getProbRanges();
        for (int i = 0; i < probRanges.length; i++) {
            if (p > probRanges[i].f && p <= probRanges[i].s) {
                sim_schedule(outputPorts[i], 0.0, 0);
            }
        }
    }

    @Override
    public void body() {
        while(Sim_system.running()){
            bodyInsideLoop();
        }
    }

    public double[] getProbRanges(){
        return this.probability;
    }
}
