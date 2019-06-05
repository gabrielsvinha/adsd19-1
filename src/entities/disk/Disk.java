package entities.disk;

import entities.Entity;

public class Disk extends Entity {
    public Disk(String name, double mean, double average, int numInputPorts) {
        super(name, mean, average, numInputPorts, new double[]{1.0});
    }
}
