package entities.cpu;

import entities.Entity;

public class CPU extends Entity {
    public CPU(String name, double mean, double average, int numInputPorts, double[] probabilityPorts) {
        super(name, mean, average, numInputPorts, probabilityPorts);
    }
}
