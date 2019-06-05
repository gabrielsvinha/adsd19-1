package entities.registry;

import entities.Entity;

public class Registry extends Entity {
    public Registry(String name, double mean, double average) {
        super(name, mean, average, 1, new double[]{1.0});
    }
}
