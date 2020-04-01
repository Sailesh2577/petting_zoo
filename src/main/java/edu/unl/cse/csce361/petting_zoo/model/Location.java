package edu.unl.cse.csce361.petting_zoo.model;

import java.util.Set;

public interface Location {
    String getName();

    LocationType getType();

    Set<Animal> getAnimals();
}
