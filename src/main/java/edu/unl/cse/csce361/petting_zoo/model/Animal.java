package edu.unl.cse.csce361.petting_zoo.model;

public interface Animal {
    int getTagNumber();

    String getName();

    int getMassInKg();

    String getSpecies();

    boolean isMale();

    boolean isFemale();

    boolean isCarnivore();

    boolean isHerbivore();

    Location getLocation();

    String pet();

    String feed();

    String watch();

    void moveTo(Location newLocation);
}
