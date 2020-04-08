package edu.unl.cse.csce361.petting_zoo.controller.animalHandler;

import edu.unl.cse.csce361.petting_zoo.controller.Command;

public class FeedAnimals implements Command {
    @Override
    public String toString() {
        return "Feed the animals";
    }
}

