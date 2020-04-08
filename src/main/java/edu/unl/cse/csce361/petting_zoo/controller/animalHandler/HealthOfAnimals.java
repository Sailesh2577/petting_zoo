package edu.unl.cse.csce361.petting_zoo.controller.animalHandler;

import edu.unl.cse.csce361.petting_zoo.controller.Command;

public class HealthOfAnimals implements Command {
    @Override
    public String toString() {
        return "Check health of the animals";
    }
}

