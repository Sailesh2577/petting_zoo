package edu.unl.cse.csce361.petting_zoo.controller.owner;

import edu.unl.cse.csce361.petting_zoo.controller.Command;

public class SellAnimalCommand implements Command {
    @Override
    public String toString() {
        return "Sell an animal";
    }
}
