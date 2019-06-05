import entities.Entity;

public class Source extends Entity {
    public Source(String s, double mean, double average){
        super(s, mean, average, 0, new double[] { 1.0 });
    }
    @Override
    public void body() {
        for (int i = 0; i < 10000; i++) {
            sim_schedule(outputPorts[0], 0.0, 0);
            sim_pause(sample());
        }
    }
}
