package entities.application;

import eduni.simjava.*;
import eduni.simjava.distributions.*;

public class Application extends Sim_entity{

    private Sim_port input;
    private Sim_port output;
    private Sim_normal_obj delay;
    private Sim_stat stat;

    public Application (String s, double mean, double var) {
        super(s);
        this.input = new Sim_port("IN");
        this.output = new Sim_port("OUT");
        add_port(this.output);
        add_port(this.input);
        this.delay = new Sim_normal_obj("Delay", mean, var);
        add_generator(delay);
        this.stat = new Sim_stat();
        stat.add_measure(Sim_stat.ARRIVAL_RATE);
//        stat.add_measure(Sim_stat.QUEUE_LENGTH);
//        stat.add_measure(Sim_stat.THROUGHPUT);
//        stat.add_measure(Sim_stat.UTILISATION);
//        stat.add_measure(Sim_stat.WAITING_TIME);
//        stat.add_measure(Sim_stat.SERVICE_TIME);
//        stat.add_measure(Sim_stat.RESIDENCE_TIME);
        set_stat(stat);
    }
}
